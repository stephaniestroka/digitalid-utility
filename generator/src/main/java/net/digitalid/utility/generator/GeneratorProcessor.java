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
import net.digitalid.utility.fixes.Quotes;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateConverter;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.generator.exceptions.FailedClassGenerationException;
import net.digitalid.utility.generator.information.type.ClassInformation;
import net.digitalid.utility.generator.information.type.InterfaceInformation;
import net.digitalid.utility.generator.information.type.TypeInformation;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.processing.logging.ProcessingLog;
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
 */
@Mutable
@SupportedOptions({"release"})
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
            } else {
                assert typeElement.getKind() == ElementKind.INTERFACE;
                
                typeInformation = InterfaceInformation.of(typeElement, containingType);
            }
            ProcessingLog.debugging("Type $ is generatable", typeInformation);
            if (typeInformation.hasAnnotation(GenerateSubclass.class)) {
                SubclassGenerator.generateSubclassOf(typeInformation);
            }
            if (typeInformation.hasAnnotation(GenerateBuilder.class)) {
                BuilderGenerator.generateBuilderFor(typeInformation);
            }
            if (typeInformation.hasAnnotation(GenerateConverter.class)) {
                ConverterGenerator.generateConverterFor(typeInformation);
            }
            return true;
        } catch (FailedClassGenerationException e) {
            Log.information("The compilation failed due to the following problem:", e);
            ProcessingLog.information("Type $ is NOT generatable: ", e.getSourcePosition(), typeInformation == null ? typeElement : typeInformation, e.getMessage());
        }
        return false;
    }
    
    @Impure
    @Override
    public void processFirstRound(@Nonnull FiniteIterable<@Nonnull ? extends TypeElement> annotations, @Nonnull RoundEnvironment roundEnvironment) {
        for (@Nonnull Element rootElement : roundEnvironment.getRootElements()) {
            if (rootElement.getKind() == ElementKind.CLASS || rootElement.getKind() == ElementKind.INTERFACE) {
                // TODO: In order to just generate a builder, the class can actually be final.
                if (!rootElement.getModifiers().contains(Modifier.FINAL) && !rootElement.getSimpleName().toString().endsWith("Test") && !rootElement.getSimpleName().toString().equals("ConverterAnnotations")) {
                    ProcessingLog.debugging("Generate the classes for  " + Quotes.inSingle(rootElement.getSimpleName()));
                    final long start = System.currentTimeMillis();
                    final boolean generated = generateClasses((TypeElement) rootElement, (DeclaredType) rootElement.asType());
                    final long end = System.currentTimeMillis();
                    ProcessingLog.debugging("Generated " + (generated ? "the" : "no") + " classes for " + Quotes.inSingle(rootElement.getSimpleName()) + " in " + (end - start) + " ms.\n");
                }
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
