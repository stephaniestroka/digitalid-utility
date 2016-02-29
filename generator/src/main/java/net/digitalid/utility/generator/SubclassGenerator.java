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
import net.digitalid.utility.functional.string.Brackets;
import net.digitalid.utility.functional.string.IterableConverter;
import net.digitalid.utility.generator.information.field.RepresentingFieldInformation;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.generator.information.type.TypeInformation;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.logging.processing.AnnotationProcessingEnvironment;
import net.digitalid.utility.processor.ProcessingUtility;
import net.digitalid.utility.processor.generator.JavaFileGenerator;
import net.digitalid.utility.string.QuoteString;
import net.digitalid.utility.string.StringCase;
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
        for (@Nonnull RepresentingFieldInformation field : typeInformation.getRepresentingFields()) {
            AnnotationLog.verbose("Generating the field " + QuoteString.inSingle(field.getName()));
            addSection(StringCase.capitalizeFirstLetters(StringCase.decamelize(field.getName())));
            addField("private " + (field.isMutable() ? "" : "final ") + importIfPossible(field.getType()) + " " + field.getName());
            
            final @Nullable MethodInformation getter = field.getter;
            assert getter != null : "If the field is generated, the getter was provided.";
            // TODO: Support method interceptors!
            addAnnotation("@Override");
            beginMethod(getter.getModifiersForOverridingMethod() + importIfPossible(getter.getType()) + " " + getter.getName() + "()");
            addStatement(importIfPossible(Log.class) + ".verbose(" + QuoteString.inDouble("The method " + getter + " was called.") + ")");
            addStatement("final " + importIfPossible(getter.getType().getReturnType()) + " result = " + field.getName());
            for (@Nonnull Map.Entry<AnnotationMirror, AnnotationValidator> entry : getter.getValidators().entrySet()) {
                final @Nonnull GeneratedContract generatedContract = entry.getValue().generateContract(getter.getElement(), entry.getKey());
                addStatement(importIfPossible(Ensure.class) + ".that(" + generatedContract.getCondition() + ").orThrow(" + generatedContract.getMessageInDoubleQuotes() + ")");
            }
            addStatement("return result");
            endMethod();
            
            final @Nullable MethodInformation setter = field.setter;
            // TODO: Support method interceptors!
            if (setter != null) {
                addAnnotation("@Override");
                beginMethod(setter.getModifiersForOverridingMethod() + "void " + setter.getName() + importingTypeVisitor.reduceParametersDeclarationToString(setter.getType(), setter.getElement()));
                addStatement(importIfPossible(Log.class) + ".verbose(" + QuoteString.inDouble("The method " + setter + " was called.") + ")");
                for (@Nonnull VariableElement parameter : setter.getElement().getParameters()) {
                    for (@Nonnull Map.Entry<AnnotationMirror, AnnotationValidator> entry : ProcessingUtility.getAnnotationValidators(parameter).entrySet()) {
                        final @Nonnull GeneratedContract generatedContract = entry.getValue().generateContract(parameter, entry.getKey());
                        addStatement(importIfPossible(Require.class) + ".that(" + generatedContract.getCondition() + ").orThrow(" + generatedContract.getMessageInDoubleQuotes() + ")");
                    }
                    addStatement("this." + field.getName() + " = " + parameter.getSimpleName());
                }
                endMethod();
            }
        }
    }
    
    protected void generateConstructors() {
        addSection("Constructors");
        for (@Nonnull ExecutableElement constructor : typeInformation.getConstructors()) {
            final @Nonnull @NonNullableElements List<? extends VariableElement> parameters = constructor.getParameters();
            final @Nonnull ExecutableType type = (ExecutableType) AnnotationProcessingEnvironment.getTypeUtils().asMemberOf(typeInformation.getType(), constructor);
            final @Nonnull @NonNullableElements List<String> parameterDeclarations = importingTypeVisitor.mapParametersDeclarationToStrings(type, constructor);
            for (@Nonnull RepresentingFieldInformation field : typeInformation.getRepresentingFields()) {
                parameterDeclarations.add(importIfPossible(field.getType()) + " " + field.getName());
            }
            beginConstructor("protected " + typeInformation.getSimpleNameOfGeneratedSubclass() + IterableConverter.toString(parameterDeclarations, Brackets.ROUND) + (constructor.getThrownTypes().isEmpty() ? "" : " throws " + IterableConverter.toString(constructor.getThrownTypes(), importingTypeVisitor.TYPE_MAPPER)));
            addStatement("super" + IterableConverter.toString(parameters, ProcessingUtility.CALL_CONVERTER, Brackets.ROUND));
            addEmptyLine();
            for (@Nonnull RepresentingFieldInformation field : typeInformation.getRepresentingFields()) {
                addStatement("this." + field.getName() + " = " + field.getName());
            }
            endConstructor();
        }
    }
    
    protected void overrideMethods() {
        if (!typeInformation.getOverridenMethods().isEmpty()) { addSection("Overridden Methods"); }
        for (@Nonnull MethodInformation method : typeInformation.getOverridenMethods()) {
            AnnotationLog.verbose("Overriding the method " + QuoteString.inSingle(method.getName()));
            addAnnotation("@Override");
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
        
        beginClass("class " + typeInformation.getSimpleNameOfGeneratedSubclass() + importingTypeVisitor.reduceTypeVariablesWithBoundsToString(typeInformation.getType().getTypeArguments()) + (typeInformation.getElement().getKind() == ElementKind.CLASS ? " extends " : " implements ") + importIfPossible(typeInformation.getType()));
        
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
        Require.that(typeInformation.isGeneratable()).orThrow("No subclass can be generated for " + typeInformation);
        
        new SubclassGenerator(typeInformation).write();
    }
    
}
