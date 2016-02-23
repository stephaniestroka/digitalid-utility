package net.digitalid.utility.generator;

import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ExecutableType;

import net.digitalid.utility.contracts.Ensure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.generator.information.field.FieldInformation;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.generator.information.type.TypeInformation;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.logging.processing.AnnotationProcessing;
import net.digitalid.utility.processor.ProcessingUtility;
import net.digitalid.utility.processor.generator.JavaFileGenerator;
import net.digitalid.utility.string.QuoteString;
import net.digitalid.utility.string.StringCase;
import net.digitalid.utility.functional.string.Brackets;
import net.digitalid.utility.functional.string.IterableConverter;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.validator.AnnotationValidator;
import net.digitalid.utility.validation.validator.GeneratedContract;

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
        for (@Nonnull FieldInformation field : typeInformation.representingFields) {
            if (field.generated) {
                AnnotationLog.verbose("Generating the field " + QuoteString.inSingle(field.name));
                addSection(StringCase.capitalizeFirstLetters(StringCase.decamelize(field.name)));
                addField("private " + (field.isMutable() ? "" : "final ") + importIfPossible(field.type) + " " + field.name);
                
                final @Nullable MethodInformation getter = field.getter;
                assert getter != null : "If the field is generated, the getter was provided.";
                // TODO: Support method interceptors!
                addAnnotation("@Override");
                beginMethod(getter.getModifiersForOverridingMethod() + importIfPossible(getter.type) + " " + getter.name + "()");
                addStatement(importIfPossible(Log.class) + ".verbose(" + QuoteString.inDouble("The method " + getter + " was called.") + ")");
                addStatement("final " + importIfPossible(getter.type.getReturnType()) + " result = " + field.name);
                for (@Nonnull Map.Entry<AnnotationMirror, AnnotationValidator> entry : getter.validators.entrySet()) {
                    final @Nonnull GeneratedContract generatedContract = entry.getValue().generateContract(getter.element, entry.getKey());
                    addStatement(importIfPossible(Ensure.class) + ".that(" + generatedContract.getCondition() + ").orThrow(" + generatedContract.getMessageInDoubleQuotes() + ")");
                }
                addStatement("return result");
                endMethod();
                
                final @Nullable MethodInformation setter = field.setter;
                // TODO: Support method interceptors!
                if (setter != null) {
                    addAnnotation("@Override");
                    beginMethod(setter.getModifiersForOverridingMethod() + "void " + setter.name + importingTypeVisitor.reduceParametersDeclarationToString(setter.type, setter.element));
                    addStatement(importIfPossible(Log.class) + ".verbose(" + QuoteString.inDouble("The method " + setter + " was called.") + ")");
                    for (@Nonnull VariableElement parameter : setter.element.getParameters()) {
                        for (@Nonnull Map.Entry<AnnotationMirror, AnnotationValidator> entry : ProcessingUtility.getAnnotationValidators(parameter).entrySet()) {
                            final @Nonnull GeneratedContract generatedContract = entry.getValue().generateContract(parameter, entry.getKey());
                            addStatement(importIfPossible(Require.class) + ".that(" + generatedContract.getCondition() + ").orThrow(" + generatedContract.getMessageInDoubleQuotes() + ")");
                        }
                        addStatement("this." + field.name + " = " + parameter.getSimpleName());
                    }
                    endMethod();
                }
            }
        }
    }
    
    protected void generateConstructors() {
        addSection("Constructors");
        for (@Nonnull ExecutableElement constructor : typeInformation.constructors) {
            final @Nonnull @NonNullableElements List<? extends VariableElement> parameters = constructor.getParameters();
            final @Nonnull ExecutableType type = (ExecutableType) AnnotationProcessing.getTypeUtils().asMemberOf(typeInformation.type, constructor);
            final @Nonnull @NonNullableElements List<String> parameterDeclarations = importingTypeVisitor.mapParametersDeclarationToStrings(type, constructor);
            for (@Nonnull FieldInformation field : typeInformation.representingFields) {
                if (field.generated) {
                    parameterDeclarations.add(importIfPossible(field.type) + " " + field.name);
                }
            }
            beginConstructor("protected " + typeInformation.getSimpleNameOfGeneratedSubclass() + IterableConverter.toString(parameterDeclarations, Brackets.ROUND) + (constructor.getThrownTypes().isEmpty() ? "" : " throws " + IterableConverter.toString(constructor.getThrownTypes(), importingTypeVisitor.TYPE_MAPPER)));
            addStatement("super" + IterableConverter.toString(parameters, ProcessingUtility.CALL_CONVERTER, Brackets.ROUND));
            addEmptyLine();
            for (@Nonnull FieldInformation field : typeInformation.representingFields) {
                if (field.generated) {
                    addStatement("this." + field.name + " = " + field.name);
                }
            }
            endConstructor();
        }
    }
    
    protected void overrideMethods() {
        if (!typeInformation.overriddenMethods.isEmpty()) { addSection("Overridden Methods"); }
        for (@Nonnull MethodInformation method : typeInformation.overriddenMethods) {
            AnnotationLog.verbose("Overriding the method " + QuoteString.inSingle(method.name));
            addAnnotation("@Override");
            beginMethod(method.getModifiersForOverridingMethod() + importIfPossible(method.type) + " " + method.name + importingTypeVisitor.reduceParametersDeclarationToString(method.type, method.element) + (method.element.getThrownTypes().isEmpty() ? "" : " throws " + IterableConverter.toString(method.element.getThrownTypes(), importingTypeVisitor.TYPE_MAPPER)));
            addStatement(importIfPossible(Log.class) + ".verbose(" + QuoteString.inDouble("The method " + method.name + " was called.") + ")");
            addStatement((method.hasReturnType() ? "return " : "") + "super." + method.name + IterableConverter.toString(method.element.getParameters(), ProcessingUtility.CALL_CONVERTER, Brackets.ROUND));
            endMethod();
        }
    }
    
    protected void generateMethods() {
        addSection("Generated Methods");
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected SubclassGenerator(@Nonnull TypeInformation typeInformation) {
        super(typeInformation.getQualifiedNameOfGeneratedSubclass(), typeInformation.element);
        
        this.typeInformation = typeInformation;
        
        beginClass("class " + typeInformation.getSimpleNameOfGeneratedSubclass() + importingTypeVisitor.reduceTypeVariablesWithBoundsToString(typeInformation.type.getTypeArguments()) + (typeInformation.element.getKind() == ElementKind.CLASS ? " extends " : " implements ") + importIfPossible(typeInformation.type));
        
        generateFields();
        generateConstructors();
        overrideMethods();
        generateMethods();
        
        endClass();
    }
    
    /**
     * Generates a subclass (the target) of the given type (the source).
     */
    @Pure
    public static void generateSubclassOf(@Nonnull TypeInformation typeInformation) {
        Require.that(typeInformation.generatable).orThrow("No subclass can be generated for " + typeInformation);
        
        new SubclassGenerator(typeInformation).write();
    }
    
}
