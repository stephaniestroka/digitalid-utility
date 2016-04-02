package net.digitalid.utility.generator;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.type.Mutable;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.exceptions.UnexpectedFailureException;
import net.digitalid.utility.functional.fixes.Brackets;
import net.digitalid.utility.functional.fixes.Quotes;
import net.digitalid.utility.functional.interfaces.UnaryFunction;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.information.ElementInformation;
import net.digitalid.utility.generator.information.field.DirectlyAccessibleFieldInformation;
import net.digitalid.utility.generator.information.field.FieldInformation;
import net.digitalid.utility.generator.information.field.GeneratedFieldInformation;
import net.digitalid.utility.generator.information.field.RepresentingFieldInformation;
import net.digitalid.utility.generator.information.method.ConstructorInformation;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.generator.information.type.ClassInformation;
import net.digitalid.utility.generator.information.type.InterfaceInformation;
import net.digitalid.utility.generator.information.type.TypeInformation;
import net.digitalid.utility.generator.information.type.exceptions.UnsupportedTypeException;
import net.digitalid.utility.generator.interceptor.MethodInterceptor;
import net.digitalid.utility.generator.interceptor.MethodUtility;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processor.generator.JavaFileGenerator;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.tuples.Triplet;
import net.digitalid.utility.validation.processing.ValidatorProcessingUtility;
import net.digitalid.utility.validation.validator.MethodAnnotationValidator;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 * This class generates a subclass with the provided type information.
 * 
 * @see GeneratorProcessor
 * @see TypeInformation
 */
// TODO: generate validate() method
@Mutable
public class SubclassGenerator extends JavaFileGenerator {
    
    /* -------------------------------------------------- Type Information -------------------------------------------------- */
    
    protected final @Nonnull TypeInformation typeInformation;
    
    /* -------------------------------------------------- Generating Methods -------------------------------------------------- */
    
    protected void generateFields() {
        for (@Nonnull GeneratedFieldInformation field : typeInformation.generatedFieldInformation) {
            ProcessingLog.verbose("Generating the field $.", field.getName());
            addSection(Strings.capitalizeFirstLetters(Strings.decamelize(field.getName())));
            addField("private " + (field.isMutable() ? "" : "final ") + importIfPossible(field.getType()) + " " + field.getName());
            
            {
                final @Nonnull MethodInformation getter = field.getGetter();
                ProcessingLog.verbose("Implementing the getter " + Quotes.inSingle(getter.getName()));
                final @Nonnull String statement = "result = this." + field.getName();
                generateMethodWithStatement(getter, statement, "result");
            }
            
            if (field.hasSetter()) {
                final @Nonnull MethodInformation setter = field.getNonNullSetter();
                ProcessingLog.verbose("Implementing the setter " + Quotes.inSingle(setter.getName()));
                final @Nonnull List<? extends VariableElement> parameters = setter.getElement().getParameters();
                Require.that(parameters.size() == 1).orThrow("Found a setter with " + (parameters.size() == 0 ? "zero " : "more than one ") + "parameters.");
                final @Nonnull VariableElement parameter = parameters.get(0);
                final @Nonnull String statement = ("this." + field.getName() + " = " + parameter.getSimpleName());
                generateMethodWithStatement(setter, statement);
            }
        }
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    // NetBeans 8.1 crashes if you use type annotations on anonymous classes and lambda expressions!
    private static UnaryFunction<@Nonnull Pair<@Nonnull RepresentingFieldInformation, @Nonnull SubclassGenerator>, @Nonnull String> elementInformationToDeclarationFunction = new UnaryFunction<Pair<RepresentingFieldInformation, SubclassGenerator>, String>() {
        
        @Override
        public @Nonnull String evaluate(@Nonnull Pair<RepresentingFieldInformation, SubclassGenerator> pair) {
            final @Nonnull ElementInformation element = pair.get0();
            final @Nonnull SubclassGenerator subclassGenerator = pair.get1();
            return subclassGenerator.importIfPossible(element.getType()) + " " + element.getName();
        }
        
    };
    
    // NetBeans 8.1 crashes if you use type annotations on anonymous classes and lambda expressions!
    private static UnaryFunction<@Nonnull ElementInformation, @Nonnull String> elementInformationToStringFunction = new UnaryFunction<ElementInformation, String>() {
        
        @Override
        public @Nonnull String evaluate(@Nonnull ElementInformation element) {
            return element.getName();
        }
        
    };
    
    private void generateConstructor(@Nullable List<? extends TypeMirror> throwTypes, @Nullable String superStatement) throws UnsupportedTypeException {
         final @Nonnull FiniteIterable<RepresentingFieldInformation> representingFieldInformation = typeInformation.getRepresentingFieldInformation();
        
        beginConstructor("protected " + typeInformation.getSimpleNameOfGeneratedSubclass() + representingFieldInformation.map(element -> this.importIfPossible(element.getType()) + " " + element.getName()).join(Brackets.ROUND) + (throwTypes == null || throwTypes.isEmpty() ? "" : " throws " + FiniteIterable.of(throwTypes).map(type -> importingTypeVisitor.visit(type).toString()).join()));

        if (superStatement != null) {
            addStatement(superStatement);
            addEmptyLine();
        }
        for (@Nonnull FieldInformation field : typeInformation.generatedFieldInformation) {
            addStatement("this." + field.getName() + " = " + field.getName());
        }
        endConstructor();       
    }
    
    protected void generateConstructors() throws UnsupportedTypeException {
        addSection("Constructors");
        if (typeInformation instanceof ClassInformation) {
            for (@Nonnull ConstructorInformation constructor : typeInformation.getConstructors()) {
                ClassInformation classInformation = (ClassInformation) typeInformation;
                generateConstructor(constructor.getElement().getThrownTypes(), "super" + classInformation.parameterBasedFieldInformation.map(elementInformationToStringFunction).join(Brackets.ROUND));
            }
        } else if (typeInformation instanceof InterfaceInformation) {
            generateConstructor(null, null);
        }
    }
    
    protected @Nonnull String implementCallToMethodInterceptors(@Nonnull MethodInformation method, @Nonnull String lastStatement, @Nullable String returnedValue) {
        for (@Nonnull Map.Entry<AnnotationMirror, MethodInterceptor> annotationMirrorMethodInterceptorEntry : method.getInterceptors().entrySet()) {
            final @Nonnull MethodInterceptor methodInterceptor = annotationMirrorMethodInterceptorEntry.getValue();
            lastStatement = methodInterceptor.generateInterceptorMethod(this, method, lastStatement, returnedValue);
        }
        return lastStatement;
    }
    /* -------------------------------------------------- Overridden Methods -------------------------------------------------- */
    
    protected void overrideMethods() {
        if (!typeInformation.getOverriddenMethods().isEmpty()) { addSection("Overridden Methods"); }
        for (final @Nonnull MethodInformation method : typeInformation.getOverriddenMethods()) {
            ProcessingLog.verbose("Overriding the method " + Quotes.inSingle(method.getName()));
            
            final @Nonnull String callToSuperMethod = MethodUtility.createSuperCall(method, "result");
            final @Nonnull String firstMethodCall = implementCallToMethodInterceptors(method, callToSuperMethod, "result"); 
            addAnnotation(Override.class);
            MethodUtility.generateBeginMethod(this, method, null, "result");
            addStatement(firstMethodCall);
            if (method.hasReturnType()) {
                addStatement("return result");
            }
            endMethod();
        }
    }
    
    private void generateMethodWithStatement(@Nonnull MethodInformation method, @Nonnull String statement) {
        generateMethodWithStatement(method, statement, null);
    }
    
    private void generateMethodWithStatement(@Nonnull MethodInformation method, @Nonnull String statement, @Nullable String returnedValue) {
        final @Nonnull String firstMethodCall = implementCallToMethodInterceptors(method, statement, returnedValue);
        addAnnotation(Override.class);
        MethodUtility.generateBeginMethod(this, method, null, returnedValue);
        for (@Nonnull VariableElement parameter : method.getElement().getParameters()) {
            for (@Nonnull Map.Entry<AnnotationMirror, ValueAnnotationValidator> entry : ValidatorProcessingUtility.getValueValidators(parameter).entrySet()) {
                addPrecondition(entry.getValue().generateContract(parameter, entry.getKey(), this));
            }
        }
        addStatement(firstMethodCall);
        for (@Nonnull Map.Entry<AnnotationMirror, MethodAnnotationValidator> entry : method.getMethodValidators().entrySet()) {
            addPostcondition(entry.getValue().generateContract(method.getElement(), entry.getKey(), this));
        }
        if (returnedValue != null) {
            addStatement("return " + returnedValue);
        }
        endMethod();
    }
    
    private static @Nonnull GenerateComparisonTypeVisitor generateComparisonTypeVisitor = new GenerateComparisonTypeVisitor();
    
    /**
     * Generates a hashCode method that generate a hashCode from all representing fields.
     */
    protected void generateToStringMethod() {
        if (typeInformation instanceof ClassInformation && ((ClassInformation) typeInformation).toStringMethod != null) {
            return;
        }
        addAnnotation(Pure.class);
        addAnnotation(Override.class);
        beginMethod("public String toString()");
        addStatement("return \"" + typeInformation.getRepresentingFieldInformation().map(field -> field.getName() + " : \" + " + importIfPossible(Objects.class) + ".toString(" + field.getAccessCode() + ") + \"").join(Brackets.JSON) + "\"");
        endMethod();
    }
    
    private void generateValidateMethod() {
        ProcessingLog.debugging("generateValidateMethod()");
        addAnnotation(Pure.class);
        addAnnotation(Override.class);
        beginMethod("public void validate()");
        final @Nonnull ClassInformation classInformation = (ClassInformation) typeInformation; 
        for (@Nonnull DirectlyAccessibleFieldInformation field : classInformation.writableAccessibleFields) {
            for (@Nonnull Map.Entry<AnnotationMirror, ValueAnnotationValidator> entry : ValidatorProcessingUtility.getValueValidators(field.getElement()).entrySet()) {
                addInvariant(entry.getValue().generateContract(field.getElement(), entry.getKey(), this));
            }
        }
        endMethod();
    }
    
    /**
     * Generates a hashCode method that generate a hashCode from all representing fields.
     */
    protected void generateHashCodeMethod() {
        if (typeInformation instanceof ClassInformation && ((ClassInformation) typeInformation).hashCodeMethod != null) {
            return;
        }
        beginJavadoc();
        addJavadoc("Computes and returns the hash code of this object, using the fields of the class which are handed to the class via the recovery method or the constructor.");
        endJavadoc();
        addAnnotation(Pure.class);
        addAnnotation(Override.class);
        beginMethod("public int hashCode()");
        addStatement("int prime = 92821");
        addStatement("int result = 46411");
        final @Nonnull FiniteIterable<RepresentingFieldInformation> representingFieldInformation = typeInformation.getRepresentingFieldInformation();
        for (@Nonnull RepresentingFieldInformation field : representingFieldInformation) {
            addStatement("result = prime * result + " + importIfPossible(Objects.class) + ".hashCode(" + field.getAccessCode() + ")");
        }
        addStatement("return result");
        endMethod();
    }
    
    /**
     * Generates an equal method that compares all representing fields.
     */
    protected void generateEqualsMethod() {
        if (typeInformation instanceof ClassInformation && ((ClassInformation) typeInformation).equalsMethod != null) {
            return;
        }
        beginJavadoc();
        addJavadoc("Returns false if the given object is not equal to the current object, using the heuristics that fields of a class, which are handed to the class via the recovery method or the constructor, must be equal to each other, so that the objects are considered equal.");
        endJavadoc();
        addAnnotation(Pure.class);
        addAnnotation(Override.class);
        beginMethod("public boolean equals(@" + importIfPossible(Nullable.class) + " Object other)");
        beginIf("other == null || !other.getClass().equals(this.getClass())");
        addStatement("return false");
        endIf();
        addStatement("@" + importIfPossible(Nonnull.class)+ " " + typeInformation.getName() + " otherObject = (" + typeInformation.getName() + ") other");
        addStatement("boolean result = true");
        final @Nonnull FiniteIterable<RepresentingFieldInformation> representingFieldInformation = typeInformation.getRepresentingFieldInformation();
        ProcessingLog.debugging("generating equals method...");
        for (@Nonnull RepresentingFieldInformation field : representingFieldInformation) {
            ProcessingLog.debugging("...with representing field/method $", field.getAccessCode());
            final @Nonnull String accessCode = field.getAccessCode();
            generateComparisonTypeVisitor.visit(field.getType(), Triplet.of("this." + accessCode, "otherObject." + accessCode, this));
        }
        addStatement("return result");
        endMethod();
    }
    
    protected void generateMethods() {
        addSection("Generated Methods");
        generateEqualsMethod();
        generateHashCodeMethod();
        generateToStringMethod();
        generateValidateMethod();
    }
    
    /* Copied from the former root class. */
    
//    /**
//     * 
//     */
//    
//    /**
//     * Computes and returns the hash code of this object, using the fields of the class which are handed to the class via the recovery method or the constructor.
//     */

    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected SubclassGenerator(@Nonnull TypeInformation typeInformation) {
        super(typeInformation.getQualifiedNameOfGeneratedSubclass(), typeInformation.getElement());
        
        this.typeInformation = typeInformation;
        try {
        
            beginClass("class " + typeInformation.getSimpleNameOfGeneratedSubclass() + importingTypeVisitor.reduceTypeVariablesWithBoundsToString(typeInformation.getType().getTypeArguments()) + (typeInformation.getElement().getKind() == ElementKind.CLASS ? " extends " : " implements ") + importIfPossible(typeInformation.getType()));
            
            generateFields();
            generateConstructors();
            overrideMethods();
            generateMethods();
            
            endClass();
        } catch (Exception e) {
            throw UnexpectedFailureException.with(e.getMessage(), e);
        }
    }
    
    /**
     * Generates a subclass (the target) of the given type (the source).
     */
    @Pure
    public static void generateSubclassOf(@Nonnull TypeInformation typeInformation) {
        new SubclassGenerator(typeInformation).write();
    }
    
}
