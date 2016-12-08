package net.digitalid.utility.generator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.circumfixes.Quotes;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateConverter;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.generator.exceptions.FailedClassGenerationException;
import net.digitalid.utility.generator.generators.BuilderGenerator;
import net.digitalid.utility.generator.generators.ConverterGenerator;
import net.digitalid.utility.generator.generators.SubclassGenerator;
import net.digitalid.utility.generator.information.type.ClassInformation;
import net.digitalid.utility.generator.information.type.EnumInformation;
import net.digitalid.utility.generator.information.type.InterfaceInformation;
import net.digitalid.utility.generator.information.type.TypeInformation;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.utility.StaticProcessingEnvironment;
import net.digitalid.utility.processor.CustomProcessor;
import net.digitalid.utility.processor.annotations.SupportedAnnotations;
import net.digitalid.utility.validation.annotations.type.Mutable;

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
 * 
 * Potential improvements:
 * - If the class is not public, the builder and its method should also not be public.
 * - Also validate nested classes. Maybe even allow to generate from static nested classes. (No!)
 * - Allow interceptors to generate the implementation of abstract methods.
 * - @GenerateBuilder without a @GenerateSubclass should not be allowed on interfaces.
 */
@Mutable
@SupportedOptions("production")
@SupportedAnnotations(prefix = "")
public class GeneratorProcessor extends CustomProcessor {
    
    /* -------------------------------------------------- Processing -------------------------------------------------- */
    
    /**
     * Generates a subclass and a builder for the given type if possible.
     * 
     * @return whether these classes can be generated for the given type.
     */
    protected boolean generateClasses(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType) {
        @Nullable TypeInformation typeInformation = null;
        try {
            if (typeElement.getKind() == ElementKind.CLASS) {
                typeInformation = ClassInformation.of(typeElement, containingType);
            } else if (typeElement.getKind() == ElementKind.INTERFACE) {
                typeInformation = InterfaceInformation.of(typeElement, containingType);
            } else  {
                assert (typeElement.getKind() == ElementKind.ENUM);
                
                typeInformation = EnumInformation.of(typeElement, containingType);
            }
            ProcessingLog.debugging("Type $ is generatable", typeInformation);
            if (!typeElement.getModifiers().contains(Modifier.FINAL)) {
                if (typeInformation.hasAnnotation(GenerateSubclass.class)) {
                    assert (!(typeElement.getKind() == ElementKind.ENUM)) : "Enums cannot have subclasses";
    
                    SubclassGenerator.generateSubclassOf(typeInformation);
                }
            } else if (typeInformation.hasAnnotation(GenerateSubclass.class)) {
                ProcessingLog.warning("Cannot generate subclass for final class $", typeElement.getSimpleName());
            }
            if (typeInformation.hasAnnotation(GenerateBuilder.class)) {
                assert (!(typeElement.getKind() == ElementKind.ENUM)) : "Enums cannot have builders";
                
                BuilderGenerator.generateBuilderFor(typeInformation);
            }
            if (typeInformation.hasAnnotation(GenerateConverter.class)) {
                ConverterGenerator.generateConverterFor(typeInformation);
            }
            return true;
        } catch (@Nonnull FailedClassGenerationException exception) {
            ProcessingLog.error("The type $ is NOT generatable:", exception.getSourcePosition(), typeInformation == null ? typeElement : typeInformation, exception.getMessage());
            Log.error("The compilation failed due to the following problem:", exception);
        }
        return false;
    }
    
    @Impure
    @Override
    public void processFirstRound(@Nonnull FiniteIterable<@Nonnull ? extends TypeElement> annotations, @Nonnull RoundEnvironment roundEnvironment) {
        ProcessingLog.information("The code is generated in " + (StaticProcessingEnvironment.environment.get().getOptions().containsKey("production") ? "production" : "development" ) + " mode.");
        for (@Nonnull Element rootElement : roundEnvironment.getRootElements()) {
            ProcessingLog.information("Processing element $ of kind $", rootElement.getSimpleName(), rootElement.getKind());
            if (rootElement.getKind() == ElementKind.CLASS || rootElement.getKind() == ElementKind.INTERFACE || rootElement.getKind() == ElementKind.ENUM) {
                ProcessingLog.debugging("Generate the classes for " + Quotes.inSingle(rootElement.getSimpleName()));
                final long start = System.currentTimeMillis();
                final boolean generated = generateClasses((TypeElement) rootElement, (DeclaredType) rootElement.asType());
                final long end = System.currentTimeMillis();
                ProcessingLog.debugging("Generated " + (generated ? "the" : "no") + " classes for " + Quotes.inSingle(rootElement.getSimpleName()) + " in " + (end - start) + " ms.\n");
            } else {
                ProcessingLog.information("Not processing element $ of kind $", rootElement.getSimpleName(), rootElement.getKind());
            }
        }
    }
    
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
