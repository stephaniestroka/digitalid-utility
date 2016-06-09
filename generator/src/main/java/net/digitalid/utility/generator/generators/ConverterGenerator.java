package net.digitalid.utility.generator.generators;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.circumfixes.Brackets;
import net.digitalid.utility.circumfixes.Quotes;
import net.digitalid.utility.conversion.converter.CustomField;
import net.digitalid.utility.conversion.converter.NodeConverter;
import net.digitalid.utility.conversion.converter.ResultSet;
import net.digitalid.utility.conversion.converter.ValueCollector;
import net.digitalid.utility.conversion.converter.types.CustomType;
import net.digitalid.utility.conversion.converter.types.ListConverter;
import net.digitalid.utility.conversion.converter.types.SetConverter;
import net.digitalid.utility.conversion.converter.types.TupleConverter;
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

import com.sun.tools.javac.code.Type;

/**
 * This class generates a converter with the provided type information.
 * 
 * @see GeneratorProcessor
 * @see TypeInformation
 */
public class ConverterGenerator extends JavaFileGenerator {
    
    /* -------------------------------------------------- Type Information -------------------------------------------------- */
    
    protected final @Nonnull TypeInformation typeInformation;
    
    /* -------------------------------------------------- Value Collector -------------------------------------------------- */
    
    /**
     * Returns a generated statement that adds the field value to the value collector.
     */
    private @Nonnull String generateValueCollectorCall(@Nonnull String fieldAccess, @Nonnull TypeMirror fieldType, boolean isArray) {
        if (isArray) {
            TypeMirror componentType = ((Type.ArrayType) fieldType).getComponentType();
            if (componentType instanceof Type.AnnotatedType) {
                componentType = ((Type.AnnotatedType) componentType).unannotatedType();
            }
            return "valueCollector.setArray(" + fieldAccess + ", (entry) -> " + generateValueCollectorCall("entry", componentType, componentType.getKind() == TypeKind.ARRAY) + ")";
        } else if (ProcessingUtility.isRawlyAssignable(fieldType, List.class)) {
            DeclaredType declaredType = (DeclaredType) fieldType;
            return "valueCollector.setList(" + fieldAccess + ", (entry) -> " + generateValueCollectorCall("entry", declaredType.getTypeArguments().get(0), declaredType.getTypeArguments().get(0).getKind() == TypeKind.ARRAY) + ")";
        } else if (ProcessingUtility.isRawlyAssignable(fieldType, Set.class)) {
            DeclaredType declaredType = (DeclaredType) fieldType;
            return "valueCollector.setSet(" + fieldAccess + ", (entry) -> " + generateValueCollectorCall("entry", declaredType.getTypeArguments().get(0), declaredType.getTypeArguments().get(0).getKind() == TypeKind.ARRAY) + ")";
        } else if (ProcessingUtility.isRawlyAssignable(fieldType, Map.class)) {
            DeclaredType declaredType = (DeclaredType) fieldType;
            return "valueCollector.setMap(" + fieldAccess + ", (entry) -> " + generateValueCollectorCall("entry", declaredType.getTypeArguments().get(0), declaredType.getTypeArguments().get(0).getKind() == TypeKind.ARRAY) + ")";
        } else if (fieldType.toString().equals(byte.class.getCanonicalName()) || fieldType.toString().equals(Byte.class.getCanonicalName())) {
            return "valueCollector.setInteger08(" + fieldAccess + ")";
        } else if (fieldType.toString().equals(short.class.getCanonicalName()) || fieldType.toString().equals(Short.class.getCanonicalName())) {
            return "valueCollector.setInteger16(" + fieldAccess + ")";
        } else if (fieldType.toString().equals(int.class.getCanonicalName()) || fieldType.toString().equals(Integer.class.getCanonicalName())) {
            return "valueCollector.setInteger32(" + fieldAccess + ")";
        } else if (fieldType.toString().equals(long.class.getCanonicalName()) || fieldType.toString().equals(Long.class.getCanonicalName())) {
            return "valueCollector.setInteger64(" + fieldAccess + ")";
        } else if (fieldType.toString().equals(BigInteger.class.getCanonicalName())) {
            return "valueCollector.setInteger(" + fieldAccess + ")";
        } else if (fieldType.toString().equals(char.class.getCanonicalName()) || fieldType.toString().equals(Character.class.getCanonicalName())) {
            return "valueCollector.setString01(" + fieldAccess + ")";
        } else if (fieldType.toString().equals(String.class.getCanonicalName())) {
            return "valueCollector.setString(" + fieldAccess + ")";
        } else {
            final @Nonnull String converterInstance = importIfPossible(ProcessingUtility.getQualifiedName(fieldType) + "Converter") + ".INSTANCE";
            return converterInstance + ".convert(" + fieldAccess + ", valueCollector)";
        }
    }
    
    private void generateConvert() {
        addAnnotation(Pure.class);
        addAnnotation(Override.class);
        beginMethod("public void convert(@" + importIfPossible(Nonnull.class) + " @" + importIfPossible(NonCaptured.class) + " @" + importIfPossible(Unmodified.class) + " " + typeInformation.getName() + " " + Strings.lowercaseFirstCharacter(typeInformation.getName()) + ", @" + importIfPossible(Nonnull.class) + " @" + importIfPossible(NonCaptured.class) + " @" + importIfPossible(Modified.class) + " " + importIfPossible(ValueCollector.class) + " valueCollector)");
        final @Nonnull FiniteIterable<FieldInformation> representingFieldInformation = typeInformation.getRepresentingFieldInformation();
        for (@Nonnull FieldInformation field : representingFieldInformation) {
            final @Nonnull TypeMirror fieldType;
            if (field.getType() instanceof Type.AnnotatedType) {
                fieldType = ((Type.AnnotatedType) field.getType()).unannotatedType();
            } else {
                fieldType = field.getType();
            }
            final @Nonnull String fieldAccess = Strings.lowercaseFirstCharacter(typeInformation.getName()) + "." + field.getAccessCode();
            // TODO: handle annotated fields that have a type-to-type converter.
            addStatement(generateValueCollectorCall(fieldAccess, fieldType, field.isArray()));
        }
        endMethod();
    }
    
    /* -------------------------------------------------- Recovery -------------------------------------------------- */
    
    /**
     * Returns a generated statement that adds the field value to the value collector.
     */
    private @Nonnull String generateResultSetCall(@Nonnull TypeMirror fieldType, boolean isArray) {
        if (isArray) {
            TypeMirror componentType = ((Type.ArrayType) fieldType).getComponentType();
            if (componentType instanceof Type.AnnotatedType) {
                componentType = ((Type.AnnotatedType) componentType).unannotatedType();
            }
            return "resultSet.getArray(() -> " + generateResultSetCall(componentType, componentType.getKind() == TypeKind.ARRAY) + ")";
        } else if (ProcessingUtility.isRawlyAssignable(fieldType, List.class)) {
            DeclaredType declaredType = (DeclaredType) fieldType;
            return "resultSet.getList(() -> " + generateResultSetCall(declaredType.getTypeArguments().get(0), declaredType.getTypeArguments().get(0).getKind() == TypeKind.ARRAY) + ")";
        } else if (ProcessingUtility.isRawlyAssignable(fieldType, Set.class)) {
            DeclaredType declaredType = (DeclaredType) fieldType;
            return "resultSet.getSet(() -> " + generateResultSetCall(declaredType.getTypeArguments().get(0), declaredType.getTypeArguments().get(0).getKind() == TypeKind.ARRAY) + ")";
        } else if (ProcessingUtility.isRawlyAssignable(fieldType, Map.class)) {
            DeclaredType declaredType = (DeclaredType) fieldType;
            return "resultSet.getMap(() -> " + generateValueCollectorCall("entry", declaredType.getTypeArguments().get(0), declaredType.getTypeArguments().get(0).getKind() == TypeKind.ARRAY) + ")";
        } else if (fieldType.toString().equals(byte.class.getCanonicalName()) || fieldType.toString().equals(Byte.class.getCanonicalName())) {
            return "resultSet.getInteger08()";
        } else if (fieldType.toString().equals(short.class.getCanonicalName()) || fieldType.toString().equals(Short.class.getCanonicalName())) {
            return "resultSet.getInteger16()";
        } else if (fieldType.toString().equals(int.class.getCanonicalName()) || fieldType.toString().equals(Integer.class.getCanonicalName())) {
            return "resultSet.getInteger32()";
        } else if (fieldType.toString().equals(long.class.getCanonicalName()) || fieldType.toString().equals(Long.class.getCanonicalName())) {
            return "resultSet.getInteger64()";
        } else if (fieldType.toString().equals(BigInteger.class.getCanonicalName())) {
            return "resultSet.getInteger()";
        } else if (fieldType.toString().equals(char.class.getCanonicalName()) || fieldType.toString().equals(Character.class.getCanonicalName())) {
            return "resultSet.getString01()";
        } else if (fieldType.toString().equals(String.class.getCanonicalName())) {
            return "resultSet.getString()";
        } else {
            final @Nonnull String converterInstance = importIfPossible(ProcessingUtility.getQualifiedName(fieldType) + "Converter") + ".INSTANCE";
            return converterInstance + ".recover(resultSet)";
        }
    }
    
    protected void generateRestoreMethod() {
        addAnnotation(Pure.class);
        addAnnotation(Override.class);
        beginMethod("public @" + importIfPossible(Nonnull.class) + " @" + importIfPossible(Capturable.class) + " " + typeInformation.getName() + " recover(@" + importIfPossible(Nonnull.class) + " @" + importIfPossible(NonCaptured.class) + " " + importIfPossible(ResultSet.class) + " resultSet)");
        final @Nonnull FiniteIterable<FieldInformation> representingFieldInformation = typeInformation.getRepresentingFieldInformation();
        final @Nonnull StringBuilder assignedParameters = new StringBuilder();
        for (@Nonnull FieldInformation field : representingFieldInformation) {
            final @Nonnull TypeMirror fieldType;
            if (field.getType() instanceof Type.AnnotatedType) {
                fieldType = ((Type.AnnotatedType) field.getType()).unannotatedType();
            } else {
                fieldType = field.getType();
            }
            // TODO: handle annotated fields that have a type-to-type converter.
            addStatement(field.getFieldType(this) + " " + field.getName() + " = " + generateResultSetCall(fieldType, field.isArray()));
    
            assignedParameters.append(".with").append(Strings.capitalizeFirstLetters(field.getName())).append("(").append(field.getName()).append(")");
        }
        addStatement("return " + typeInformation.getSimpleNameOfGeneratedBuilder() + assignedParameters.append(".build()"));
        endMethod();
    }
    
    /* -------------------------------------------------- Instance -------------------------------------------------- */
    
    private void generateInstanceField() {
        addField("public static final @" + importIfPossible(Nonnull.class) + " " + typeInformation.getSimpleNameOfGeneratedConverter() + " INSTANCE = new " + typeInformation.getSimpleNameOfGeneratedConverter() + "()");
    }
    
    /* -------------------------------------------------- Class Fields -------------------------------------------------- */
    
    private @Nonnull String getConverterName(@Nonnull TypeMirror representingFieldType) {
        final @Nonnull CustomType customType = CustomType.of(representingFieldType);
        if (customType == CustomType.TUPLE) {
            return importIfPossible(TupleConverter.class) + ".of" + Brackets.inRound(importIfPossible(ProcessingUtility.getQualifiedName(representingFieldType) + "Converter") + ".INSTANCE");
        } else if (customType == CustomType.SET) {
            final @Nonnull TypeMirror componentType = ProcessingUtility.getComponentType(representingFieldType);
            return importIfPossible(SetConverter.class) + ".of" + Brackets.inRound(getConverterName(componentType));
        } else if (customType == CustomType.LIST) {
            final @Nonnull TypeMirror componentType = ProcessingUtility.getComponentType(representingFieldType);
            return importIfPossible(ListConverter.class) + ".of" + Brackets.inRound(getConverterName(componentType));
        } else {
            return importIfPossible(customType.converterName) + ".INSTANCE";
        }
    }
    
    private void generateFields() {
        final @Nonnull StringBuilder fieldsString = new StringBuilder();
        for (@Nonnull FieldInformation representingField : typeInformation.getRepresentingFieldInformation()) {
            if (fieldsString.length() != 0) {
                fieldsString.append(", ");
            }
            representingField.getName();
            
            fieldsString.append(importIfPossible(CustomField.class) + ".with(" + getConverterName(representingField.getType()) + ", " + Quotes.inDouble(representingField.getName()) + ", null)");
        }
        addField("private static " + importIfPossible(ImmutableList.class) + "<" + importIfPossible(CustomField.class) + "> fields = " + importIfPossible(ImmutableList.class) + ".with(" + fieldsString + ")");
    
        addAnnotation(Pure.class);
        addAnnotation(Override.class);
        beginMethod("public @" + importIfPossible(Nonnull.class) + " " + importIfPossible(ImmutableList.class) + "<@" + importIfPossible(Nonnull.class) + " " + importIfPossible(CustomField.class) + "> getFields()");
        addStatement("return fields");
        endMethod();
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ConverterGenerator(@Nonnull TypeInformation typeInformation) {
        super(typeInformation.getQualifiedNameOfGeneratedConverter(), typeInformation.getElement());
    
        this.typeInformation = typeInformation;
    
        try {
            beginClass("public class " + typeInformation.getSimpleNameOfGeneratedConverter() + importWithBounds(typeInformation.getTypeArguments()) + " implements " + importIfPossible(NodeConverter.class) + "<" + typeInformation.getName() + ">");
            
            generateInstanceField();
            generateFields();
            generateConvert();
            generateRestoreMethod();
            
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
