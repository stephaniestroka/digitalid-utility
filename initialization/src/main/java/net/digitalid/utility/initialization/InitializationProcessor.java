package net.digitalid.utility.initialization;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.QualifiedNameable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.tools.JavaFileObject;

import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.configuration.Initializer;
import net.digitalid.utility.exceptions.UnexpectedValueException;
import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.logging.processing.AnnotationProcessing;
import net.digitalid.utility.logging.processing.SourcePosition;
import net.digitalid.utility.processor.CustomProcessor;
import net.digitalid.utility.processor.ProcessingUtility;
import net.digitalid.utility.processor.files.ServiceLoaderFile;
import net.digitalid.utility.string.QuoteString;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * This annotation processor generates a subclass of {@link Initializer} for each static method annotated with {@link Initialize} and registers it in a {@link ServiceLoaderFile}.
 */
@SupportedAnnotationTypes("net.digitalid.utility.initialization.Initialize")
public class InitializationProcessor extends CustomProcessor {
    
    /* -------------------------------------------------- Processing -------------------------------------------------- */
    
    @Override
    public void processFirstRound(@Nonnull @NonNullableElements Set<? extends TypeElement> annotations, @Nonnull RoundEnvironment roundEnvironment) {
        final @Nonnull ServiceLoaderFile serviceLoaderFile = ServiceLoaderFile.forService(Initializer.class);
        annotatedElement: for (@Nonnull Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(Initialize.class)) {
            // Enforced by the compiler due to the '@Target' meta-annotation:
            final @Nonnull ExecutableElement annotatedMethod = (ExecutableElement) annotatedElement;
            
            @Nullable String errorMessage = null;
            if (!annotatedMethod.getModifiers().contains(Modifier.STATIC)) { errorMessage = "The annotated method has to be static:"; }
            else if (!annotatedMethod.getParameters().isEmpty()) { errorMessage = "The annotated method may not have parameters:"; }
            else if (annotatedMethod.getReturnType().getKind() != TypeKind.VOID) { errorMessage = "The annotated method may not have a return type:"; }
            else if (annotatedMethod.getEnclosingElement().getEnclosingElement().getKind() != ElementKind.PACKAGE) { errorMessage = "The annotated method has to be in a top-level class:"; }
            if (errorMessage != null) { AnnotationLog.error(errorMessage, SourcePosition.of(annotatedMethod)); continue; }
            
            final @Nullable AnnotationMirror annotationMirror = ProcessingUtility.getAnnotationMirror(annotatedMethod, Initialize.class);
            if (annotationMirror == null) { continue; }
            
            @Nullable VariableElement targetFieldElement = null;
            @Nullable @NonNullableElements List<VariableElement> dependencyFieldElements = null;
            final @Nonnull Map<? extends ExecutableElement, ? extends AnnotationValue> annotationValues = annotationMirror.getElementValues();
            for (@Nonnull Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationValues.entrySet()) {
                if (entry.getKey().getSimpleName().contentEquals("target")) {
                    final @Nonnull AnnotationValue targetClassValue = entry.getValue();
                    final @Nonnull DeclaredType targetClassMirror = (DeclaredType) targetClassValue.getValue();
                    AnnotationLog.debugging("The type mirror of the target class is " + QuoteString.inSingle(targetClassMirror));
                    final @Nonnull TypeElement targetClassElement = (TypeElement) targetClassMirror.asElement();
                    targetFieldElement = ProcessingUtility.getUniquePublicStaticFieldOfType(targetClassElement, Configuration.class);
                    if (targetFieldElement == null) {
                        AnnotationLog.error("The referenced class does not have a unique, public and static configuration field:", SourcePosition.of(annotatedMethod, annotationMirror, targetClassValue));
                        continue annotatedElement;
                    }
                } else if (entry.getKey().getSimpleName().contentEquals("dependencies")) {
                    @SuppressWarnings("unchecked") final @Nonnull List<? extends AnnotationValue> dependencyClassValues = (List<? extends AnnotationValue>) entry.getValue().getValue();
                    dependencyFieldElements = new ArrayList<>(dependencyClassValues.size());
                    for (@Nonnull AnnotationValue dependencyClassValue : dependencyClassValues) {
                        final @Nonnull DeclaredType dependencyClassMirror = (DeclaredType) dependencyClassValue.getValue();
                        AnnotationLog.debugging("The type mirror of the dependency class is " + QuoteString.inSingle(dependencyClassMirror));
                        final @Nonnull TypeElement dependencyClassElement = (TypeElement) dependencyClassMirror.asElement();
                        final @Nullable VariableElement dependencyFieldElement = ProcessingUtility.getUniquePublicStaticFieldOfType(dependencyClassElement, Configuration.class);
                        if (dependencyFieldElement == null) {
                            AnnotationLog.error("The referenced class does not have a unique, public and static configuration field:", SourcePosition.of(annotatedMethod, annotationMirror, dependencyClassValue));
                        } else {
                            dependencyFieldElements.add(dependencyFieldElement);
                        }
                    }
                }
            }
            
            // The target field element should never be null because specifying the target class of the '@Initialize' annotation is mandatory and
            // this code is skipped due to the continue statement if no unique, public and static configuration field is found in the target class.
            if (targetFieldElement == null) { throw UnexpectedValueException.with("targetFieldElement", targetFieldElement); }
            if (dependencyFieldElements == null) { dependencyFieldElements = new LinkedList<>(); }
            
            final @Nonnull String qualifiedSourceClassName = ((QualifiedNameable) annotatedMethod.getEnclosingElement()).getQualifiedName().toString();
            final @Nonnull String qualifiedGeneratedClassName = qualifiedSourceClassName + "$" + annotatedMethod.getSimpleName();
            final @Nonnull String sourceClassName = annotatedMethod.getEnclosingElement().getSimpleName().toString();
            final @Nonnull String generatedClassName = sourceClassName + "$" + annotatedMethod.getSimpleName();
            AnnotationLog.debugging("Generating the class " + QuoteString.inSingle(qualifiedGeneratedClassName));
            try {
                final @Nonnull JavaFileObject javaFileObject = AnnotationProcessing.environment.get().getFiler().createSourceFile(qualifiedGeneratedClassName); // TODO: Also provide the originating elements!
                try (@Nonnull Writer writer = javaFileObject.openWriter(); @Nonnull PrintWriter printWriter = new PrintWriter(writer)) {
                    printWriter.println("package " + AnnotationProcessing.getElementUtils().getPackageOf(annotatedElement).getQualifiedName() + ";");
                    printWriter.println();
                    printWriter.println("import javax.annotation.Generated;");
                    printWriter.println();
                    printWriter.println("import net.digitalid.utility.configuration.Initializer;");
                    printWriter.println();
                    printWriter.println("@Generated(value = {" + QuoteString.inDouble(getClass().getCanonicalName()) + "}, date = " + QuoteString.inDouble(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date())) + ")");
                    printWriter.println("public class " + generatedClassName + " extends Initializer {");
                    printWriter.println("    ");
                    printWriter.println("    public " + generatedClassName + "() {");
                    printWriter.print("        super(" + ((QualifiedNameable) targetFieldElement.getEnclosingElement()).getQualifiedName() + "." + targetFieldElement);
                    if (!dependencyFieldElements.isEmpty()) {
                        for (@Nonnull VariableElement dependencyFieldElement : dependencyFieldElements) {
                            printWriter.print(", " + ((QualifiedNameable) dependencyFieldElement.getEnclosingElement()).getQualifiedName() + "." + dependencyFieldElement);
                        }
                    }
                    printWriter.println(");");
                    printWriter.println("    }");
                    printWriter.println("    ");
                    printWriter.println("    protected void execute() throws Exception {");
                    printWriter.println("        " + sourceClassName + "." + annotatedMethod + ";");
                    printWriter.println("    }");
                    printWriter.println("    ");
                    printWriter.println("}");
                    printWriter.flush();
                }
            } catch (@Nonnull IOException exception) {
                AnnotationLog.error("An exception occurred while generating the class " + QuoteString.inSingle(qualifiedGeneratedClassName) + ": " + exception);
            }
            
            serviceLoaderFile.addProvider(qualifiedGeneratedClassName);
        }
        serviceLoaderFile.write();
    }
    
    @Pure
    @Override
    protected boolean consumeAnnotations(@Nonnull @NonNullableElements Set<? extends TypeElement> annotations, @Nonnull RoundEnvironment roundEnvironment) {
        return true;
    }
    
}
