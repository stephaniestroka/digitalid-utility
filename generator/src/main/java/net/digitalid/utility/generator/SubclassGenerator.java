package net.digitalid.utility.generator;

import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.functional.function.unary.NonNullToNonNullUnaryFunction;
import net.digitalid.utility.functional.iterable.NonNullableIterable;
import net.digitalid.utility.functional.iterable.NullableIterable;
import net.digitalid.utility.functional.string.Brackets;
import net.digitalid.utility.functional.string.IterableConverter;
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
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.logging.processing.ProcessingLog;
import net.digitalid.utility.processor.generator.JavaFileGenerator;
import net.digitalid.utility.string.QuoteString;
import net.digitalid.utility.string.StringCase;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.processing.ProcessingUtility;
import net.digitalid.utility.validation.validator.MethodAnnotationValidator;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 * This class generates a subclass with the provided type information.
 * 
 * @see GeneratorProcessor
 * @see TypeInformation
 */
@Mutable
public class SubclassGenerator extends JavaFileGenerator {
    
    /* -------------------------------------------------- Type Information -------------------------------------------------- */
    
    protected final @Nonnull TypeInformation typeInformation;
    
    /* -------------------------------------------------- Generating Methods -------------------------------------------------- */
    
    protected void generateFields() {
        for (@Nonnull GeneratedFieldInformation field : typeInformation.generatedFieldInformation) {
            ProcessingLog.verbose("Generating the field $.", field.getName());
            addSection(StringCase.capitalizeFirstLetters(StringCase.decamelize(field.getName())));
            addField("private " + (field.isMutable() ? "" : "final ") + importIfPossible(field.getElement()) + " " + field.getName());
            
            final @Nonnull MethodInformation getter = field.getGetter();
            // TODO: Support method interceptors!
            addAnnotation(Override.class);
            beginMethod(getter.getModifiersForOverridingMethod() + importIfPossible(getter.getType()) + " " + getter.getName() + "()");
            addStatement(importIfPossible(Log.class) + ".verbose(" + QuoteString.inDouble("The method " + getter + " was called.") + ")");
            addStatement("final " + importIfPossible(getter.getType().getReturnType()) + " result = " + field.getName());
            for (@Nonnull Map.Entry<AnnotationMirror, MethodAnnotationValidator> entry : getter.getMethodValidators().entrySet()) {
                addPostcondition(entry.getValue().generateContract(getter.getElement(), entry.getKey(), this));
            }
            addStatement("return result");
            endMethod();
            
            if (field.hasSetter()) {
                final @Nullable MethodInformation setter = field.getSetter();
                // TODO: Support method interceptors!
                addAnnotation(Override.class);
                beginMethod(setter.getModifiersForOverridingMethod() + "void " + setter.getName() + importingTypeVisitor.reduceParametersDeclarationToString(setter.getType(), setter.getElement()));
                addStatement(importIfPossible(Log.class) + ".verbose(" + QuoteString.inDouble("The method " + setter + " was called.") + ")");
                for (@Nonnull VariableElement parameter : setter.getElement().getParameters()) {
                    for (@Nonnull Map.Entry<AnnotationMirror, ValueAnnotationValidator> entry : ProcessingUtility.getValueValidators(parameter).entrySet()) {
                        addPrecondition(entry.getValue().generateContract(parameter, entry.getKey(), this));
                    }
                    addStatement("this." + field.getName() + " = " + parameter.getSimpleName());
                }
                endMethod();
            }
        }
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    private static NonNullToNonNullUnaryFunction<ElementInformation, String> elementInformationToStringFunction = new NonNullToNonNullUnaryFunction<ElementInformation, String>() {
        
        @Override
        public @Nonnull String apply(@Nonnull ElementInformation element) {
            return element.getName();
        }
        
    };
    
    private void generateConstructor(@Nullable List<? extends TypeMirror> throwTypes, @Nullable String superStatement) throws UnsupportedTypeException {
         final @Nonnull NonNullableIterable<RepresentingFieldInformation> representingFieldInformation = typeInformation.getRepresentingFieldInformation();
        
        beginConstructor("protected " + typeInformation.getSimpleNameOfGeneratedSubclass() + IterableConverter.toString(representingFieldInformation.map(elementInformationToStringFunction), Brackets.ROUND) + (throwTypes == null || throwTypes.isEmpty() ? "" : " throws " + IterableConverter.toString(throwTypes, importingTypeVisitor.TYPE_MAPPER)));

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
                generateConstructor(constructor.getElement().getThrownTypes(), "super" + IterableConverter.toString(classInformation.parameterBasedFieldInformation.map(elementInformationToStringFunction), Brackets.ROUND));
            }
        } else if (typeInformation instanceof InterfaceInformation) {
            generateConstructor(null, null);
        }
    }
    
    /* -------------------------------------------------- Overridden Methods -------------------------------------------------- */
    
    private static NonNullToNonNullUnaryFunction<VariableElement, String> parameterToStringFunction = new NonNullToNonNullUnaryFunction<VariableElement, String>() {
        
        @Override
        public @Nonnull String apply(@Nonnull VariableElement element) {
            return element.getSimpleName().toString();
        }
        
    };
    
    protected void overrideMethods() {
        if (!typeInformation.getOverriddenMethods().isEmpty()) { addSection("Overridden Methods"); }
        for (@Nonnull MethodInformation method : typeInformation.getOverriddenMethods()) {
            ProcessingLog.verbose("Overriding the method " + QuoteString.inSingle(method.getName()));
            addAnnotation(Override.class);
            beginMethod(method.getModifiersForOverridingMethod() + importIfPossible(method.getType()) + " " + method.getName() + importingTypeVisitor.reduceParametersDeclarationToString(method.getType(), method.getElement()) + (method.getElement().getThrownTypes().isEmpty() ? "" : " throws " + IterableConverter.toString(method.getElement().getThrownTypes(), importingTypeVisitor.TYPE_MAPPER)));
            addStatement(importIfPossible(Log.class) + ".verbose(" + QuoteString.inDouble("The method " + method.getName() + " was called.") + ")");
            addStatement((method.hasReturnType() ? "return " : "") + "super." + method.getName() + IterableConverter.toString(NullableIterable.ofNonNullableElements(method.getElement().getParameters()).map(parameterToStringFunction), Brackets.ROUND));
            endMethod();
        }
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
            ProcessingLog.information("Failed to generate subclass for type '" + typeInformation.getName() + "'");
        }
    }
    
    /**
     * Generates a subclass (the target) of the given type (the source).
     */
    @Pure
    public static void generateSubclassOf(@Nonnull TypeInformation typeInformation) {
        Require.that(typeInformation.isGeneratable()).orThrow("No subclass can be generated for " + typeInformation);
        
        new SubclassGenerator(typeInformation).write();
    }
    
}
