package net.digitalid.utility.initialization;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.configuration.Initializer;
import net.digitalid.utility.functional.fixes.Quotes;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processor.CustomProcessor;
import net.digitalid.utility.processor.annotations.SupportedAnnotations;
import net.digitalid.utility.processor.generator.JavaFileGenerator;
import net.digitalid.utility.processor.generator.ServiceFileGenerator;

/**
 * This annotation processor generates a subclass of {@link Initializer} for each static method
 * annotated with {@link Initialize} and registers it in a {@link ServiceFileGenerator}.
 */
@SupportedAnnotations(Initialize.class)
public class InitializationProcessor extends CustomProcessor {
    
    /* -------------------------------------------------- Processing -------------------------------------------------- */
    
    /**
     * Checks that the given annotated method fulfills all requirements.
     * 
     * @return {@code null} if the given annotated method fulfills all requirements and a suitable error message otherwise.
     */
    @Pure
    protected @Nullable String checkRequirements(@Nonnull ExecutableElement annotatedMethod) {
        if (annotatedMethod.getModifiers().contains(Modifier.PRIVATE)) { return "The annotated method may not be private:"; }
        if (!annotatedMethod.getModifiers().contains(Modifier.STATIC)) { return "The annotated method has to be static:"; }
        if (!annotatedMethod.getParameters().isEmpty()) { return "The annotated method may not have parameters:"; }
        if (annotatedMethod.getReturnType().getKind() != TypeKind.VOID) { return "The annotated method may not have a return type:"; }
        if (annotatedMethod.getEnclosingElement().getEnclosingElement().getKind() != ElementKind.PACKAGE) { return "The annotated method has to be in a top-level class:"; }
        return null;
    }
    
    /**
     * Returns the configuration field of the declared type in the given annotation value or null if no field fulfilling the criteria is found.
     */
    @Pure
    protected @Nullable VariableElement getConfigurationField(@Nonnull AnnotationValue annotationValue) {
        final @Nonnull DeclaredType declaredType = (DeclaredType) annotationValue.getValue();
        ProcessingLog.debugging("The declared type is " + Quotes.inSingle(declaredType));
        final @Nonnull TypeElement typeElement = (TypeElement) declaredType.asElement();
        return ProcessingUtility.getUniquePublicStaticFieldOfType(typeElement, Configuration.class);
    }
    
    /**
     * Generates the initializer for the given annotated method with the given configuration fields.
     * 
     * @return the qualified name of the generated initializer.
     */
    @Impure
    protected @Nonnull String generateInitializer(@Nonnull ExecutableElement annotatedMethod, @Nonnull FiniteIterable<@Nonnull VariableElement> configurationFields) {
        final @Nonnull TypeElement sourceClassElement = (TypeElement) annotatedMethod.getEnclosingElement();
        
        final @Nonnull String qualifiedSourceClassName = sourceClassElement.getQualifiedName().toString();
        final @Nonnull String qualifiedGeneratedClassName = qualifiedSourceClassName + "$" + annotatedMethod.getSimpleName();
        final @Nonnull String simpleSourceClassName = sourceClassElement.getSimpleName().toString();
        final @Nonnull String simpleGeneratedClassName = simpleSourceClassName + "$" + annotatedMethod.getSimpleName();
        
        final @Nonnull JavaFileGenerator javaSourceFile = JavaFileGenerator.forClass(qualifiedGeneratedClassName, sourceClassElement);
        javaSourceFile.beginClass("public class " + simpleGeneratedClassName + " extends " + javaSourceFile.importIfPossible(LoggingInitializer.class));
        
        javaSourceFile.beginJavadoc();
        javaSourceFile.addJavadoc("This default constructor is called by the service loader.");
        javaSourceFile.addJavadoc("It registers this initializer at the given configuration.");
        javaSourceFile.endJavadoc();
        
        javaSourceFile.beginConstructor("public " + simpleGeneratedClassName + "()");
        javaSourceFile.addStatement(configurationFields.map(field -> javaSourceFile.importIfPossible(field.getEnclosingElement()) + "." + field.getSimpleName()).join("super(", ")"));
        javaSourceFile.endConstructor();
        
        javaSourceFile.addAnnotation(Override.class);
        javaSourceFile.beginMethod("protected void executeWithoutLogging() throws Exception");
        javaSourceFile.addStatement(simpleSourceClassName + "." + annotatedMethod);
        javaSourceFile.endMethod();
        
        javaSourceFile.endClass();
        javaSourceFile.write();
        
        return qualifiedGeneratedClassName;
    }
    
    @Impure
    @Override
    public void processFirstRound(@Nonnull FiniteIterable<@Nonnull ? extends TypeElement> annotations, @Nonnull RoundEnvironment roundEnvironment) {
        final @Nonnull ServiceFileGenerator serviceLoaderFile = ServiceFileGenerator.forService(Initializer.class);
        for (@Nonnull Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(Initialize.class)) {
            // Enforced by the compiler due to the '@Target' meta-annotation:
            final @Nonnull ExecutableElement annotatedMethod = (ExecutableElement) annotatedElement;
            
            final @Nullable String errorMessage = checkRequirements(annotatedMethod);
            if (errorMessage != null) { ProcessingLog.error(errorMessage, SourcePosition.of(annotatedMethod)); continue; }
            
            final @Nullable AnnotationMirror annotationMirror = ProcessingUtility.getAnnotationMirror(annotatedMethod, Initialize.class);
            if (annotationMirror == null) { ProcessingLog.error("Found no annotation '@Initialize' on", SourcePosition.of(annotatedMethod)); continue; }
            
            // TODO: It should be possible to ensure during compile-time that there are no cyclic dependencies.
            
            @Nullable VariableElement targetConfigurationField = null;
            @Nullable List<@Nonnull VariableElement> dependencyConfigurationFields = null;
            
            for (Map.@Nonnull Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror.getElementValues().entrySet()) {
                if (entry.getKey().getSimpleName().contentEquals("target")) {
                    targetConfigurationField = getConfigurationField(entry.getValue());
                    if (targetConfigurationField == null) {
                        ProcessingLog.error("The referenced class does not have a unique, public and static configuration field:", SourcePosition.of(annotatedMethod, annotationMirror, entry.getValue()));
                    }
                } else if (entry.getKey().getSimpleName().contentEquals("dependencies")) {
                    @SuppressWarnings("unchecked") final @Nonnull List<? extends AnnotationValue> dependencyClassValues = (List<? extends AnnotationValue>) entry.getValue().getValue();
                    dependencyConfigurationFields = new ArrayList<>(dependencyClassValues.size());
                    for (@Nonnull AnnotationValue dependencyClassValue : dependencyClassValues) {
                        final @Nullable VariableElement dependencyConfigurationField = getConfigurationField(dependencyClassValue);
                        if (dependencyConfigurationField == null) {
                            ProcessingLog.error("The referenced class does not have a unique, public and static configuration field:", SourcePosition.of(annotatedMethod, annotationMirror, dependencyClassValue));
                        } else {
                            dependencyConfigurationFields.add(dependencyConfigurationField);
                        }
                    }
                }
            }
            
            if (targetConfigurationField == null) { continue; }
            if (dependencyConfigurationFields == null) { dependencyConfigurationFields = new LinkedList<>(); }
            
            final @Nonnull String qualifiedGeneratedClassName = generateInitializer(annotatedMethod, FiniteIterable.of(targetConfigurationField).combine(FiniteIterable.of(dependencyConfigurationFields)));
            serviceLoaderFile.addProvider(qualifiedGeneratedClassName);
        }
        serviceLoaderFile.write();
    }
    
    @Pure
    @Override
    protected boolean consumeAnnotations(@Nonnull FiniteIterable<@Nonnull ? extends TypeElement> annotations, @Nonnull RoundEnvironment roundEnvironment) {
        return true;
    }
    
}
