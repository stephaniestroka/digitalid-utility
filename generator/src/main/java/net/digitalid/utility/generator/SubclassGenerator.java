package net.digitalid.utility.generator;

import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.annotations.type.Mutable;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.exceptions.UnexpectedFailureException;
import net.digitalid.utility.functional.fixes.Brackets;
import net.digitalid.utility.functional.interfaces.UnaryFunction;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.information.ElementInformation;
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
import net.digitalid.utility.string.QuoteString;
import net.digitalid.utility.string.StringCase;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.processing.utility.ProcessingUtility;
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
            addSection(StringCase.capitalizeFirstLetters(StringCase.decamelize(field.getName())));
            addField("private " + (field.isMutable() ? "" : "final ") + importIfPossible(field.getType()) + " " + field.getName());
            
            {
                final @Nonnull MethodInformation getter = field.getGetter();
                ProcessingLog.verbose("Implementing the getter " + QuoteString.inSingle(getter.getName()));
                final @Nonnull String statement = "result = this." + field.getName();
                generateMethodWithStatement(getter, statement, "result");
            }
            
            if (field.hasSetter()) {
                final @Nonnull MethodInformation setter = field.getNonNullSetter();
                ProcessingLog.verbose("Implementing the setter " + QuoteString.inSingle(setter.getName()));
                final @Nonnull List<? extends VariableElement> parameters = setter.getElement().getParameters();
                Require.that(parameters.size() == 1).orThrow("Found a setter with " + (parameters.size() == 0 ? "zero " : "more than one ") + "parameters.");
                final @Nonnull VariableElement parameter = parameters.get(0);
                final @Nonnull String statement = ("this." + field.getName() + " = " + parameter.getSimpleName());
                generateMethodWithStatement(setter, statement);
            }
        }
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
     
    private static UnaryFunction<@Nonnull Pair<@Nonnull RepresentingFieldInformation, @Nonnull SubclassGenerator>, @Nonnull String> elementInformationToDeclarationFunction = new UnaryFunction<@Nonnull Pair<RepresentingFieldInformation, SubclassGenerator>, @Nonnull String>() {
        
        @Override
        public @Nonnull String evaluate(@Nonnull Pair<RepresentingFieldInformation, SubclassGenerator> pair) {
            final @Nonnull ElementInformation element = pair.get0();
            final @Nonnull SubclassGenerator subclassGenerator = pair.get1();
            return subclassGenerator.importIfPossible(element.getType()) + " " + element.getName();
        }
        
    };
    
    private static UnaryFunction<@Nonnull ElementInformation, @Nonnull String> elementInformationToStringFunction = new UnaryFunction<@Nonnull ElementInformation, @Nonnull String>() {
        
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
            ProcessingLog.verbose("Overriding the method " + QuoteString.inSingle(method.getName()));
            
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
    
    protected void generateMethods() {
        addSection("Generated Methods");
    
    }
    
    /* Copied from the former root class. */
    
//    /**
//     * Returns false if the given object is not equal to the current object, using the heuristics that fields of a class,
//     * which are handed to the class via the recovery method or the constructor, must be equal to each other so that the objects are considered equal.
//     */
//    @Pure
//    @Override
//    public boolean equals(@Nullable Object other) {
//        if (other == null || !other.getClass().equals(this.getClass())) {
//            return false;
//        }
//        try {
//            for (@Nonnull Field field : classFields) {
//                field.setAccessible(true);
//                @Nullable Object fieldValueThis = field.get(this);
//                @Nullable Object fieldValueOther = field.get(other);
//                if (fieldValueThis == null && fieldValueOther == null) {
//                    continue;
//                } else if (fieldValueThis == null || fieldValueOther == null) {
//                    return false;
//                } else if (!fieldValueThis.equals(fieldValueOther)) {
//                    return false;
//                }
//            }
//            return true;
//        } catch (IllegalAccessException e) {
//            return super.equals(other);
//        }
//    }
//    
//    /**
//     * Computes and returns the hash code of this object, using the fields of the class which are handed to the class via the recovery method or the constructor.
//     */
//    @Pure
//    @Override
//    public int hashCode() {
//        try {
//            int prime = 92821;
//            int result = 46411;
//            for (@Nonnull Field field : classFields) {
//                     field.setAccessible(true);
//                int c = 0;
//                @Nullable Object fieldValue = field.get(this);
//                if (fieldValue != null) {
//                    c = fieldValue.hashCode();
//                }
//                result = prime * result + c;
//            }
//            return result;
//        } catch (IllegalAccessException e) {
//            return super.hashCode();
//        }
//    }
    
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
