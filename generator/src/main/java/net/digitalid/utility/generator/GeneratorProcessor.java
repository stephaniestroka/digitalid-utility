package net.digitalid.utility.generator;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import net.digitalid.utility.generator.information.TypeInformation;
import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.processor.CustomProcessor;
import net.digitalid.utility.processor.annotations.SupportedAnnotations;
import net.digitalid.utility.string.PrefixString;
import net.digitalid.utility.string.QuoteString;
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
     * Generates a subclass and a builder for the given type if possible.
     * 
     * @return whether these classes can be generated for the given type.
     */
    protected boolean generateClasses(@Nonnull TypeElement typeElement) {
        final @Nonnull TypeInformation typeInformation = TypeInformation.forType(typeElement);
        if (typeInformation.generatable) {
            SubclassGenerator.generateSubclassOf(typeInformation);
            BuilderGenerator.generateBuilderFor(typeInformation);
        }
        return typeInformation.generatable;
    }
    
    @Override
    public void processFirstRound(@Nonnull @NonNullableElements Set<? extends TypeElement> annotations, @Nonnull RoundEnvironment roundEnvironment) {
        for (@Nonnull Element rootElement : roundEnvironment.getRootElements()) {
            if (rootElement.getKind() == ElementKind.CLASS || rootElement.getKind() == ElementKind.INTERFACE) {
                // TODO: In order to just generate a builder, the class can actually be final.
                if (!rootElement.getModifiers().contains(Modifier.FINAL) && !PrefixString.startsWithAny(rootElement.getSimpleName().toString(), "ReadOnly", "Freezable") && !rootElement.getSimpleName().toString().endsWith("Test") && !rootElement.getSimpleName().toString().equals("ConverterAnnotations")) {
                    AnnotationLog.debugging("Generate the classes for  " + QuoteString.inSingle(rootElement.getSimpleName()));
                    final long start = System.currentTimeMillis();
                    final boolean generated = generateClasses((TypeElement) rootElement);
                    final long end = System.currentTimeMillis();
                    AnnotationLog.debugging("Generated " + (generated ? "the" : "no") + " classes for " + QuoteString.inSingle(rootElement.getSimpleName()) + " in " + (end - start) + " ms.\n");
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
