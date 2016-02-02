package net.digitalid.utility.generator.processor;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;

import net.digitalid.utility.processor.CustomProcessor;

/**
 * Description.
 * 
 * Is it worth to generate subclasses with annotation processors?
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
@SupportedAnnotationTypes("*")
public class GeneratorProcessor extends CustomProcessor {
    
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
