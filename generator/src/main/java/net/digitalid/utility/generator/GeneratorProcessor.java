package net.digitalid.utility.generator;

import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.QualifiedNameable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.ElementFilter;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.generator.information.TypeInformation;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.logging.processing.AnnotationProcessing;
import net.digitalid.utility.processor.CustomProcessor;
import net.digitalid.utility.processor.ProcessingUtility;
import net.digitalid.utility.processor.annotations.SupportedAnnotations;
import net.digitalid.utility.processor.files.JavaSourceFile;
import net.digitalid.utility.string.PrefixString;
import net.digitalid.utility.string.QuoteString;
import net.digitalid.utility.string.iterable.Brackets;
import net.digitalid.utility.string.iterable.IterableConverter;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;

/**
 * This annotation processor generates a subclass for each non-final type to implement common methods and aspects.
 * 
 * Useful aspects to intercept method calls:
 * - log with timing and parameters
 * - commit database transactions
 * - run method on the GUI thread (or just asynchronously)
 * - cleanup/closing of used resources
 * - validate parameters and return value
 * - ensure method is called from host or client (or recipient is configured)
 * 
 * Useful methods generated from fields:
 * - equals()
 * - hashCode()
 * - toString()
 * - validate() [would be declared in the RootClass and then overridden]
 * - compareTo()
 * - clone()?
 * 
 * Useful helper classes:
 * - object builder with default values
 * - argument builder with default values
 * 
 * If only the getters instead of the final fields are declared, the constructor (including a static method) could also be generated.
 * Setters on immutable objects could return a new object with the changed value.
 * Setters could/should be made chainable by returning this.
 * Delegate all interface methods to an instance (e.g. stored in a field).
 */
@SupportedOptions({"release"})
@SupportedAnnotations(prefix = "")
public class GeneratorProcessor extends CustomProcessor {
    
    /* -------------------------------------------------- Processing -------------------------------------------------- */
    
    /**
     * Generates a subclass (the target) for the given class element (the source).
     */
    protected void generateSubclass(@Nonnull TypeElement sourceTypeElement) {
        final @Nonnull String simpleTargetClassName = "Generated" + sourceTypeElement.getSimpleName();
        final @Nonnull String qualifiedTargetClassName = ((QualifiedNameable) sourceTypeElement.getEnclosingElement()).getQualifiedName() + "." + simpleTargetClassName;
        final @Nonnull JavaSourceFile javaSourceFile = JavaSourceFile.forClass(qualifiedTargetClassName, sourceTypeElement);
        
        Require.that(sourceTypeElement.asType().getKind() == TypeKind.DECLARED).orThrow(sourceTypeElement.asType().getKind() + " should have been a declared type.");
        final @Nonnull DeclaredType sourceDeclaredType = (DeclaredType) sourceTypeElement.asType();
        final @Nonnull @NonNullableElements List<? extends TypeMirror> sourceTypeArguments = sourceDeclaredType.getTypeArguments();
        for (@Nonnull TypeMirror typeArgument : sourceTypeArguments) {
            AnnotationLog.debugging("typeArgument.toString(): " + typeArgument.toString());
            final @Nonnull TypeVariable typeVariable = (TypeVariable) typeArgument;
            AnnotationLog.debugging("typeVariable.toString(): " + typeVariable.toString());
            AnnotationLog.debugging("typeVariable.getLowerBound(): " + typeVariable.getLowerBound());
            AnnotationLog.debugging("typeVariable.getUpperBound(): " + typeVariable.getUpperBound());
        }
        
        final @Nonnull String type = sourceTypeElement.asType().toString();
        final @Nonnull @NonNullableElements List<? extends TypeParameterElement> typeParameters = sourceTypeElement.getTypeParameters();
        javaSourceFile.beginClass((sourceTypeElement.getKind().isInterface() || sourceTypeElement.getModifiers().contains(Modifier.ABSTRACT) ? "abstract " : "") + "class " + simpleTargetClassName + (typeParameters.isEmpty() ? "" : IterableConverter.toString(typeParameters, ProcessingUtility.TYPE_CONVERTER, Brackets.POINTY)) + (sourceTypeElement.getKind() == ElementKind.CLASS ? " extends " : " implements ") + type);
        
        final @Nonnull @NonNullableElements List<ExecutableElement> constructors = ElementFilter.constructorsIn(sourceTypeElement.getEnclosedElements());
        for (@Nonnull ExecutableElement constructor : constructors) {
            final @Nonnull @NonNullableElements List<? extends VariableElement> parameters = constructor.getParameters();
            
            // TODO: Also do thrown types.
            
            javaSourceFile.beginConstructor(simpleTargetClassName + IterableConverter.toString(parameters, ProcessingUtility.DECLARATION_CONVERTER, Brackets.ROUND));
            javaSourceFile.addStatement("super" + IterableConverter.toString(parameters, ProcessingUtility.CALL_CONVERTER, Brackets.ROUND));
            javaSourceFile.endConstructor();
        }
        
        for (@Nonnull Element member : AnnotationProcessing.getElementUtils().getAllMembers(sourceTypeElement)) {
            if (member.getKind() == ElementKind.METHOD) {
                final @Nonnull ExecutableElement method = (ExecutableElement) member;
                final @Nonnull Set<Modifier> modifiers = method.getModifiers();
                // TODO: Maybe use Collections.disjoint(A, B) instead?
                if (!modifiers.contains(Modifier.ABSTRACT) && !modifiers.contains(Modifier.FINAL) && !modifiers.contains(Modifier.PRIVATE) && !modifiers.contains(Modifier.STATIC) && !modifiers.contains(Modifier.NATIVE)) {
//                    javaSourceFile.addComment("method.asType(): " + method.asType());
//                    javaSourceFile.addComment("method.toString(): " + method.toString());
//                    javaSourceFile.addComment("method.getReturnType(): " + method.getReturnType());
//                    javaSourceFile.addComment("method.asType().getKind(): " + method.asType().getKind());
//                    javaSourceFile.addComment("((ExecutableType) method.asType()).getTypeVariables(): " + ((ExecutableType) method.asType()).getTypeVariables());
                    
                    javaSourceFile.addAnnotation("@Override");
                    final @Nonnull ExecutableType executableType = (ExecutableType) method.asType();
                    javaSourceFile.addComment("executableType: " + executableType);
                    final @Nonnull @NonNullableElements List<? extends TypeVariable> typeVariables = executableType.getTypeVariables();
                    javaSourceFile.beginMethod(IterableConverter.toString(modifiers, " ") + (typeVariables.isEmpty() ? "" : " <" + IterableConverter.toString(typeVariables) + ">") + " " + executableType.getReturnType() + " " + method.getSimpleName() + IterableConverter.toString(method.getParameters(), ProcessingUtility.DECLARATION_CONVERTER, Brackets.ROUND) + (method.getThrownTypes().isEmpty() ? "" : " throws " + IterableConverter.toString(method.getThrownTypes())));
                    javaSourceFile.addStatement(javaSourceFile.importIfPossible(Log.class) + ".verbose(" + QuoteString.inDouble("The method " + method.getSimpleName() + " was called.") + ")");
                    javaSourceFile.addStatement((method.getReturnType().getKind() == TypeKind.VOID ? "" : "return ") + "super." + method.getSimpleName() + IterableConverter.toString(method.getParameters(), ProcessingUtility.CALL_CONVERTER, Brackets.ROUND));
                    javaSourceFile.endMethod();
                }
            }
        }
        
//        javaSourceFile.addAnnotation("@Override");
//        javaSourceFile.beginMethod("public " + subclassName + " clone()");
//        javaSourceFile.addStatement("return null");
//        javaSourceFile.endMethod();
        
        javaSourceFile.endClass();
        
        javaSourceFile.write();
    }
    
    protected void generateBuilder() {
        
    }
    
    protected void generateClasses(@Nonnull TypeElement typeElement) {
        // TODO: Determine if the source type has abstract methods, whose implementations cannot be generated by this processor. If yes, skip the rest.
        final @Nonnull TypeInformation typeInformation = TypeInformation.forType(typeElement);
        // TODO: Determine which fields exist, which of them represent the instances of the source type, which of them are required or have a default value, which of them are mutable and which of them have to be generated.
        // TODO: Generate the subclass with the information about the fields.
        // TODO: Generate the builder with the information about the fields.
    }
    
    @Override
    public void processFirstRound(@Nonnull @NonNullableElements Set<? extends TypeElement> annotations, @Nonnull RoundEnvironment roundEnvironment) {
        for (@Nonnull Element rootElement : roundEnvironment.getRootElements()) {
            if (rootElement.getKind() == ElementKind.CLASS || rootElement.getKind() == ElementKind.INTERFACE) {
                if (!rootElement.getModifiers().contains(Modifier.FINAL) && !PrefixString.startsWithAny(rootElement.getSimpleName().toString(), "ReadOnly", "Freezable") && !rootElement.getSimpleName().toString().endsWith("Test") && !rootElement.getSimpleName().toString().equals("ConverterAnnotations")) {
                    AnnotationLog.debugging("Generate a subclass for  " + QuoteString.inSingle(rootElement.getSimpleName()));
                    final long start = System.currentTimeMillis();
                    generateClasses((TypeElement) rootElement);
                    final long end = System.currentTimeMillis();
                    AnnotationLog.debugging("Generated a subclass for " + QuoteString.inSingle(rootElement.getSimpleName()) + " in " + (end - start) + " ms.\n");
                }
            }
        }
    }
    
    /* -------------------------------------------------- Validator lookup -------------------------------------------------- */
    
//    /**
//     * The map of validators registered for validator annotations.
//     */
//    private static final @Nonnull Map<Class<? extends Annotation>, AnnotationValidator<?>> validators = new HashMap<>();
//    
//    public static @Nonnull AnnotationValidator<? extends Annotation> getOrCreateValidator(@Nonnull Class<? extends Annotation> annotationClass) throws ValidationFailedException {
//        @Nullable AnnotationValidator<? extends Annotation> validator = validators.get(annotationClass);
//        if (validator == null) {
//            synchronized (AnnotationValidator.class) {
//                validator = validators.get(annotationClass);
//                if (validator == null) {
//                    final @Nonnull ValidateWith validateWith = annotationClass.getAnnotation(ValidateWith.class);
//                    final @Nonnull Class<? extends AnnotationValidator<? extends Annotation>> validatorClass = validateWith.value();
//                    try {
//                        final @Nonnull Method validatorGetter = validatorClass.getMethod("get");
//                        validator = (AnnotationValidator<? extends Annotation>) validatorGetter.invoke(null);
//                        validators.put(annotationClass, validator);
//                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
//                        throw ValidationFailedException.get("Failed to initiate validator for annotation '" + annotationClass.getSimpleName() + "'", e);
//                    }
//                }
//            }
//        }
//        return validator;
//    }
//    
//    /**
//     * Checks whether the given annotation can be validated.
//     */
//    private static boolean canBeValidated(Class<? extends Annotation> annotationClass) {
//        return annotationClass.isAnnotationPresent(ValidateWith.class);
//    }
    
}


//@GenerateImplementation
//public abstract class Test {
//    
//    @Logging
//    @Asynchronous
//    public @Positive Integer method(@Positive Integer number) {
//        return number * 2;
//    }
//    
//    public static Test withNoArguments() {
//        return new GeneratedTest1();
//    }
//    
//}
//
//class GeneratedTest1 extends Test {
//    
//    @Override
//    private @Positive Integer asynchronousMethod(@Positive Integer number) {
//        
//        class B {
//            
//        }
//        
//        B b = new B();
//        
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                super.method(number);
//            }
//        }).start();
//    }
//    
//    @Override
//    private @Positive Integer loggingMethod(@Positive Integer number) {
//        final long start = System.currentTimeMillis();
//        Integer result = asynchronousMethod(number);
//        Log.debugging("doubleN(" + number + "): " + result + " on " + this + " executed in " + (System.currentTimeMillis() - start) + " ms.");
//        return result;
//    }
//    
//    @Override
//    private @Positive Integer validatingMethod(@Positive Integer number) {
//        PositiveValidator.validate(number);
//        Integer result = asynchronousMethod(number);
//        PositiveValidator.validate(result);
//        return result;
//    }
//    
//    @Override
//    public @Positive Integer method(@Positive Integer number) {
//        return validatingMethod(number);
//    }
//    
//}
//
//class GeneratedTest2 extends Test {
//    
//    private static final @Nonnull Method method;
//    
//    static {
//        try {
//            method = Test.class.getMethod("method", Integer.class);
//        } catch (NoSuchMethodException | SecurityException ex) {
//            method = null;
//        }
//    }
//    
//    private @Positive Integer loggingMethod(@Positive Integer number, Method method) {
//        final Object[] arguments = {number};
//        final Method method = GeneratedTest2.method;
//        
//        final long start = System.currentTimeMillis();
//        Integer result = super.method(number);
//        Log.debugging("doubleN(" + number + "): " + result + " on " + this + " executed in " + (System.currentTimeMillis() - start) + " ms.");
//        return result;
//    }
//    
//    @Override
//    public @Positive Integer method(@Positive Integer number) {
//        final @Nonnull MethodInvocation methodInvocation = new MethodInvocation(method, this, arguments);
//        return loggingMethodInterceptor.invoke(methodInvocation); // TODO: Implement the chaining of method interceptors with a subclass of MethodInvocation that has an array of all interceptors and an index to the next one which is to be called on proceed().
////        return loggingMethod(number, method);
//    }
//    
//}
//
