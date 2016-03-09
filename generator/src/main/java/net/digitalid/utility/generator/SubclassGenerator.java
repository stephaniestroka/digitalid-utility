package net.digitalid.utility.generator;

import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.VariableElement;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.functional.string.Brackets;
import net.digitalid.utility.functional.string.IterableConverter;
import net.digitalid.utility.generator.information.exceptions.UnexpectedTypeContentException;
import net.digitalid.utility.generator.information.field.FieldInformation;
import net.digitalid.utility.generator.information.field.GeneratedFieldInformation;
import net.digitalid.utility.generator.information.field.RepresentingFieldInformation;
import net.digitalid.utility.generator.information.method.ConstructorInformation;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.generator.information.type.ClassInformation;
import net.digitalid.utility.generator.information.type.TypeInformation;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.logging.processing.ProcessingLog;
import net.digitalid.utility.processor.generator.JavaFileGenerator;
import net.digitalid.utility.string.QuoteString;
import net.digitalid.utility.string.StringCase;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.generator.ContractGenerator;
import net.digitalid.utility.validation.processing.ProcessingUtility;

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
            for (@Nonnull Map.Entry<AnnotationMirror, ContractGenerator> entry : getter.getValidators().entrySet()) {
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
                    for (@Nonnull Map.Entry<AnnotationMirror, ContractGenerator> entry : ProcessingUtility.getContractGenerators(parameter).entrySet()) {
                        addPrecondition(entry.getValue().generateContract(parameter, entry.getKey(), this));
                    }
                    addStatement("this." + field.getName() + " = " + parameter.getSimpleName());
                }
                endMethod();
            }
        }
    }
    
    protected void generateConstructors() throws UnexpectedTypeContentException {
        addSection("Constructors");
        for (@Nonnull ConstructorInformation constructor : typeInformation.getConstructors()) {
            final @Nonnull @NonNullableElements List<RepresentingFieldInformation> representingFieldInformation = typeInformation.getRepresentingFieldInformation();
            
            beginConstructor("protected " + typeInformation.getSimpleNameOfGeneratedSubclass() + IterableConverter.toString(representingFieldInformation, Brackets.ROUND) + (constructor.getElement().getThrownTypes().isEmpty() ? "" : " throws " + IterableConverter.toString(constructor.getElement().getThrownTypes(), importingTypeVisitor.TYPE_MAPPER)));
    
            if (typeInformation instanceof ClassInformation) {
                final @Nonnull ClassInformation classInformation = (ClassInformation) typeInformation;
                addStatement("super" + IterableConverter.toString(classInformation.parameterBasedFieldInformation, ProcessingUtility.CALL_CONVERTER, Brackets.ROUND));
            }
            addEmptyLine();
            for (@Nonnull FieldInformation field : typeInformation.generatedFieldInformation) {
                addStatement("this." + field.getName() + " = " + field.getName());
            }
            endConstructor();
        }
    }
    
    protected void overrideMethods() {
        if (!typeInformation.getOverriddenMethods().isEmpty()) { addSection("Overridden Methods"); }
        for (@Nonnull MethodInformation method : typeInformation.getOverriddenMethods()) {
            ProcessingLog.verbose("Overriding the method " + QuoteString.inSingle(method.getName()));
            addAnnotation(Override.class);
            beginMethod(method.getModifiersForOverridingMethod() + importIfPossible(method.getType()) + " " + method.getName() + importingTypeVisitor.reduceParametersDeclarationToString(method.getType(), method.getElement()) + (method.getElement().getThrownTypes().isEmpty() ? "" : " throws " + IterableConverter.toString(method.getElement().getThrownTypes(), importingTypeVisitor.TYPE_MAPPER)));
            addStatement(importIfPossible(Log.class) + ".verbose(" + QuoteString.inDouble("The method " + method.getName() + " was called.") + ")");
            addStatement((method.hasReturnType() ? "return " : "") + "super." + method.getName() + IterableConverter.toString(method.getElement().getParameters(), ProcessingUtility.CALL_CONVERTER, Brackets.ROUND));
            endMethod();
        }
    }
    
    protected void generateMethods() {
        addSection("Generated Methods");
    }
    
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
