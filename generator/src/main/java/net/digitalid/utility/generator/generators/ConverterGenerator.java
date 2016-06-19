package net.digitalid.utility.generator.generators;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.circumfixes.Brackets;
import net.digitalid.utility.circumfixes.Quotes;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.conversion.converter.Converter;
import net.digitalid.utility.conversion.converter.CustomField;
import net.digitalid.utility.conversion.converter.Declaration;
import net.digitalid.utility.conversion.converter.SelectionResult;
import net.digitalid.utility.conversion.converter.ValueCollector;
import net.digitalid.utility.conversion.converter.types.CustomType;
import net.digitalid.utility.exceptions.UnexpectedFailureException;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.GeneratorProcessor;
import net.digitalid.utility.generator.information.field.FieldInformation;
import net.digitalid.utility.generator.information.type.TypeInformation;
import net.digitalid.utility.immutable.ImmutableList;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processor.generator.JavaFileGenerator;
import net.digitalid.utility.string.Strings;

/**
 * This class generates a converter with the provided type information.
 * 
 * @see GeneratorProcessor
 * @see TypeInformation
 */
public class ConverterGenerator extends JavaFileGenerator {
    
    /* -------------------------------------------------- Type Information -------------------------------------------------- */
    
    /**
     * The type information for which a converter is generated.
     */
    protected final @Nonnull TypeInformation typeInformation;
    
    /* -------------------------------------------------- Value Collector -------------------------------------------------- */
    
    /**
     * Returns a generated call to the value collector from the given type and field access strings.
     */
    private @Nonnull String getValueCollectorStatement(@Nonnull String typeAsString, @Nonnull String fieldAccess) {
        return getValueCollectorStatement(typeAsString, fieldAccess, null);
    }
    
    /**
     * Returns a generated call to the value collector from the given type and field access strings and the optionally given value collector call for composite types.
     */
    private @Nonnull String getValueCollectorStatement(@Nonnull String typeAsString, @Nonnull String fieldAccess, @Nullable String valueCollectorCall) {
        if (fieldAccess.equals("null") && valueCollectorCall == null) {
            return "valueCollector.setNull()";
        }
        return "valueCollector.set" + typeAsString + "(" + fieldAccess + (valueCollectorCall == null ? "" : ", (entry) -> " + valueCollectorCall) + ")";
    }
    
    /**
     * Returns a generated statement that adds the field value to the value collector.
     */
    private @Nonnull String generateValueCollectorCall(@Nonnull String fieldAccess, @Nonnull TypeMirror fieldType) {
        final @Nonnull CustomType customType = CustomType.of(fieldType);
        final @Nonnull String customTypeName = Strings.capitalizeFirstLetters(customType.getTypeName().toLowerCase());
        
        if (customType.isCompositeType()) {
            final @Nullable TypeMirror componentType = ProcessingUtility.getComponentType(fieldType);
            Require.that(componentType != null).orThrow("The field type is not an array or list");
            final @Nonnull String entryName;
            if (fieldAccess.equals("null")) {
                entryName = "null";
            } else {
                entryName = "entry";
            }
            return getValueCollectorStatement(customTypeName, fieldAccess, generateValueCollectorCall(entryName, componentType));
        } else if (customType.isObjectType()) {
            final @Nonnull String converterInstance = importIfPossible(ProcessingUtility.getQualifiedName(fieldType) + "Converter") + ".INSTANCE";
            return converterInstance + ".convert(" + fieldAccess + ", valueCollector)";
        } else {
            return getValueCollectorStatement(customTypeName, fieldAccess);
        }
    }
    
    /**
     * Generates the convert method. Every representing field value is collected by a value collector.
     */
    private void generateConvert() {
        addAnnotation(Pure.class);
        addAnnotation(Override.class);
        beginMethod("public void convert(@" + importIfPossible(Nullable.class) + " @" + importIfPossible(NonCaptured.class) + " @" + importIfPossible(Unmodified.class) + " " + typeInformation.getName() + " " + Strings.lowercaseFirstCharacter(typeInformation.getName()) + ", @" + importIfPossible(Nonnull.class) + " @" + importIfPossible(NonCaptured.class) + " @" + importIfPossible(Modified.class) + " " + importIfPossible(ValueCollector.class) + " valueCollector)");
        final @Nonnull FiniteIterable<FieldInformation> representingFieldInformation = typeInformation.getRepresentingFieldInformation();
        beginIf(Strings.lowercaseFirstCharacter(typeInformation.getName()) + " == null");
        for (@Nonnull FieldInformation field : representingFieldInformation) {
            addStatement(generateValueCollectorCall("null", field.getType()));
        }
        endIfBeginElse();
        for (@Nonnull FieldInformation field : representingFieldInformation) {
            final @Nonnull String fieldAccess = Strings.lowercaseFirstCharacter(typeInformation.getName()) + "." + field.getAccessCode();
            addStatement(generateValueCollectorCall(fieldAccess, field.getType()));
        }
        endElse();
        endMethod();
    }
    
    /* -------------------------------------------------- Recovery -------------------------------------------------- */
    
    /**
     * Returns a generated call to the selection result from the given type string.
     */
    private @Nonnull String getSelectionResultStatement(@Nonnull String typeAsString) {
        return getSelectionResultStatement(typeAsString, null);
    }
    
    /**
     * Returns a generated call to the selection result from the given type string and the optionally given selection result call for composite types.
     */
    private @Nonnull String getSelectionResultStatement(@Nonnull String typeAsString, @Nullable String selectionResultCall) {
        return "selectionResult.get" + typeAsString + "(" + (selectionResultCall == null ? "" : "() -> " + selectionResultCall) + ")";
    }
    
    /**
     * Returns a generated statement that reads the field value from the selection result.
     */
    private @Nonnull String generateSelectionResultCall(@Nonnull TypeMirror fieldType) {
        final @Nonnull CustomType customType = CustomType.of(fieldType);
        final @Nonnull String customTypeName = Strings.capitalizeFirstLetters(customType.getTypeName().toLowerCase());
        
        if (customType.isCompositeType()) {
            final @Nullable TypeMirror componentType = ProcessingUtility.getComponentType(fieldType);
            Require.that(componentType != null).orThrow("The field type is not an array or list");
            return getSelectionResultStatement(customTypeName, generateSelectionResultCall(componentType));
        } else if (customType.isObjectType()) {
            final @Nonnull String converterInstance = importIfPossible(ProcessingUtility.getQualifiedName(fieldType) + "Converter") + ".INSTANCE";
            return converterInstance + ".recover(selectionResult)";
        } else {
            return getSelectionResultStatement(customTypeName);
        }
    }
    
    /**
     * Generates a recover method for every representing field of the type and calls the builder.
     */
    protected void generateRecoverMethod() {
        addAnnotation(Pure.class);
        addAnnotation(Override.class);
        beginMethod("public @" + importIfPossible(Nonnull.class) + " @" + importIfPossible(Capturable.class) + " " + typeInformation.getName() + " recover(@" + importIfPossible(Nonnull.class) + " @" + importIfPossible(NonCaptured.class) + " " + importIfPossible(SelectionResult.class) + " selectionResult)");
        final @Nonnull FiniteIterable<FieldInformation> representingFieldInformation = typeInformation.getRepresentingFieldInformation();
        final @Nonnull StringBuilder assignedParameters = new StringBuilder();
        for (@Nonnull FieldInformation field : representingFieldInformation) {
            addStatement(field.getFieldType(this) + " " + field.getName() + " = " + generateSelectionResultCall(field.getType()));
    
            assignedParameters.append(".with").append(Strings.capitalizeFirstLetters(field.getName())).append("(").append(field.getName()).append(")");
        }
        addStatement("return " + typeInformation.getSimpleNameOfGeneratedBuilder() + assignedParameters.append(".build()"));
        endMethod();
    }
    
    /* -------------------------------------------------- Instance -------------------------------------------------- */
    
    /**
     * Generates a static final instance field for this converter.
     */
    private void generateInstanceField() {
        addField("public static final @" + importIfPossible(Nonnull.class) + " " + typeInformation.getSimpleNameOfGeneratedConverter() + " INSTANCE = new " + typeInformation.getSimpleNameOfGeneratedConverter() + "()");
    }
    
    /* -------------------------------------------------- Class Fields -------------------------------------------------- */
    
    /**
     * Returns the custom type for the given representing field.
     */
    private @Nonnull String getTypeName(@Nonnull TypeMirror representingFieldType) {
        final @Nonnull CustomType customType = CustomType.of(representingFieldType);
        if (customType == CustomType.TUPLE) {
            return importStaticallyIfPossible(CustomType.class.getCanonicalName() + "." + customType.getTypeName()) + ".of" + Brackets.inRound(importIfPossible(ProcessingUtility.getQualifiedName(representingFieldType) + "Converter") + ".INSTANCE");
        } else if (customType == CustomType.SET) {
            final @Nonnull TypeMirror componentType = ProcessingUtility.getComponentType(representingFieldType);
            return importStaticallyIfPossible(CustomType.class.getCanonicalName() + "." + customType.getTypeName()) + ".of" + Brackets.inRound(getTypeName(componentType));
        } else if (customType == CustomType.LIST) {
            final @Nonnull TypeMirror componentType = ProcessingUtility.getComponentType(representingFieldType);
            return importStaticallyIfPossible(CustomType.class.getCanonicalName() + "." + customType.getTypeName()) + ".of" + Brackets.inRound(getTypeName(componentType));
        } else {
            return importStaticallyIfPossible(CustomType.class.getCanonicalName() + "." + customType.getTypeName());
        }
    }
    
    /**
     * Generates the custom fields from the representing fields of the type.
     */
    private void generateFields() {
        final @Nonnull StringBuilder fieldsString = new StringBuilder();
        for (@Nonnull FieldInformation representingField : typeInformation.getRepresentingFieldInformation()) {
            if (fieldsString.length() != 0) {
                fieldsString.append(", ");
            }
            representingField.getName();
            
            fieldsString.append(importIfPossible(CustomField.class) + ".with(" + getTypeName(representingField.getType()) + ", " + Quotes.inDouble(representingField.getName()) + ", null)");
        }
        addField("private static " + importIfPossible(ImmutableList.class) + "<" + importIfPossible(CustomField.class) + "> fields = " + importIfPossible(ImmutableList.class) + ".with(" + fieldsString + ")");
    }
    
    /**
     * Generates the declare method that iterates through the representing fields and sets the field in the declaration object.
     */
    private void generateDeclare() {
        addAnnotation(Pure.class);
        addAnnotation(Override.class);
        beginMethod("public void declare(@" + importIfPossible(Nonnull.class) + " @" + importIfPossible(Modified.class) + " @" + importIfPossible(NonCaptured.class) + " " + importIfPossible(Declaration.class) + " declaration)");
        beginForLoop("@Nonnull CustomField field : fields");
        addStatement("declaration.setField(field)");
        endForLoop();
        endMethod();
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a new converter generator instance.
     */
    protected ConverterGenerator(@Nonnull TypeInformation typeInformation) {
        super(typeInformation.getQualifiedNameOfGeneratedConverter(), typeInformation.getElement());
    
        this.typeInformation = typeInformation;
    
        try {
            beginClass("public class " + typeInformation.getSimpleNameOfGeneratedConverter() + importWithBounds(typeInformation.getTypeArguments()) + " implements " + importIfPossible(Converter.class) + "<" + typeInformation.getName() + ">");
            
            generateInstanceField();
            generateFields();
            generateDeclare();
            generateConvert();
            generateRecoverMethod();
            
            endClass();
        } catch (Exception e) {
            throw UnexpectedFailureException.with(e.getMessage(), e);
        }
    }
    
    /**
     * Generates a new converter class for the given type information.
     */
    public static void generateConverterFor(@Nonnull TypeInformation typeInformation) {
        ProcessingLog.debugging("generateConverterFor(" + typeInformation + ")");
        new ConverterGenerator(typeInformation).write();
    }
    
}
