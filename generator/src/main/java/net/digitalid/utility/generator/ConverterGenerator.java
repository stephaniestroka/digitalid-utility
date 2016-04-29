package net.digitalid.utility.generator;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.annotations.state.Unmodifiable;
import net.digitalid.utility.conversion.converter.Converter;
import net.digitalid.utility.conversion.converter.CustomField;
import net.digitalid.utility.conversion.converter.CustomType;
import net.digitalid.utility.conversion.converter.ResultSet;
import net.digitalid.utility.conversion.converter.ValueCollector;
import net.digitalid.utility.exceptions.UnexpectedFailureException;
import net.digitalid.utility.functional.fixes.Quotes;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.information.field.FieldInformation;
import net.digitalid.utility.generator.information.type.TypeInformation;
import net.digitalid.utility.immutable.ImmutableList;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processor.generator.JavaFileGenerator;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.tuples.Pair;

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
            if (field.isArray()) {
                TypeMirror componentType = ((Type.ArrayType) fieldType).getComponentType();
                if (componentType instanceof Type.AnnotatedType) {
                    componentType = ((Type.AnnotatedType) componentType).unannotatedType();
                }
                addStatement("valueCollector.setArray(" + fieldAccess + ", " + importIfPossible(componentType.toString()) + ".class)");
            } else if (ProcessingUtility.isAssignable(fieldType, List.class)) {
                DeclaredType declaredType = (DeclaredType) fieldType;
                final @Nonnull String typeArgumentsAsClasses = FiniteIterable.of(declaredType.getTypeArguments()).map(ProcessingUtility::getQualifiedName).map(string -> importIfPossible(string) + ".class").join();
                addStatement("valueCollector.setList(" + fieldAccess + ", " + typeArgumentsAsClasses + ")");
            } else if (ProcessingUtility.isAssignable(fieldType, Set.class)) {
                DeclaredType declaredType = (DeclaredType) fieldType;
                final @Nonnull String typeArgumentsAsClasses = FiniteIterable.of(declaredType.getTypeArguments()).map(ProcessingUtility::getQualifiedName).map(string -> importIfPossible(string) + ".class").join();
                addStatement("valueCollector.setSet(" + fieldAccess + ", " + typeArgumentsAsClasses + ")");
            } else if (ProcessingUtility.isAssignable(fieldType, Map.class)) {
                DeclaredType declaredType = (DeclaredType) fieldType;
                final @Nonnull String typeArgumentsAsClasses = FiniteIterable.of(declaredType.getTypeArguments()).map(ProcessingUtility::getQualifiedName).map(string -> importIfPossible(string) + ".class").join();
                addStatement("valueCollector.setMap(" + fieldAccess + ", " + typeArgumentsAsClasses + ")");
            } else if (fieldType.toString().equals(byte.class.getCanonicalName()) || fieldType.toString().equals(Byte.class.getCanonicalName())) {
                addStatement("valueCollector.setInteger08(" + fieldAccess + ")");
            } else if (fieldType.toString().equals(short.class.getCanonicalName()) || fieldType.toString().equals(Short.class.getCanonicalName())) {
                addStatement("valueCollector.setInteger16(" + fieldAccess + ")");
            } else if (fieldType.toString().equals(int.class.getCanonicalName()) || fieldType.toString().equals(Integer.class.getCanonicalName())) {
                addStatement("valueCollector.setInteger32(" + fieldAccess + ")");
            } else if (fieldType.toString().equals(long.class.getCanonicalName()) || fieldType.toString().equals(Long.class.getCanonicalName())) {
                addStatement("valueCollector.setInteger64(" + fieldAccess + ")");
            } else if (fieldType.toString().equals(BigInteger.class.getCanonicalName())) {
                addStatement("valueCollector.setInteger(" + fieldAccess + ")");
            } else if (fieldType.toString().equals(char.class.getCanonicalName()) || fieldType.toString().equals(Character.class.getCanonicalName())) {
                addStatement("valueCollector.setString01(" + fieldAccess + ")");
            } else if (fieldType.toString().equals(String.class.getCanonicalName())) {
                addStatement("valueCollector.setString(" + fieldAccess + ")");
            } else {
                addStatement(fieldAccess + ".collectValues(valueCollector)");
            }
        }
        endMethod();
    }
    
    /* -------------------------------------------------- Recovery -------------------------------------------------- */
    
    protected void generateRestoreMethod(@Nonnull String nameOfBuilder) {
        beginMethod("public static " + nameOfBuilder + " restore(@" + importIfPossible(Nonnull.class) + " " + importIfPossible(ResultSet.class) + " resultSet)");
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
            if (field.isArray()) {
                Type.ArrayType arrayType = (Type.ArrayType) fieldType;
                final @Nonnull String typeArgumentsAsClasses = FiniteIterable.of(arrayType.getComponentType()).map(ProcessingUtility::getQualifiedName).map(string -> importIfPossible(string) + ".class").join();
                addStatement(field.getFieldType(this) + " " + field.getName() + " = resultSet.getArray(" + typeArgumentsAsClasses + ")");
            } else if (ProcessingUtility.isAssignable(fieldType, List.class)) {
                DeclaredType declaredType = (DeclaredType) fieldType;
                final @Nonnull String typeArgumentsAsClasses = FiniteIterable.of(declaredType.getTypeArguments()).map(ProcessingUtility::getQualifiedName).map(string -> importIfPossible(string) + ".class").join();
                addStatement(field.getFieldType(this) + " " + field.getName() + " = resultSet.getList(" + typeArgumentsAsClasses + ")");
            } else if (ProcessingUtility.isAssignable(fieldType, Set.class)) {
                DeclaredType declaredType = (DeclaredType) fieldType;
                final @Nonnull String typeArgumentsAsClasses = FiniteIterable.of(declaredType.getTypeArguments()).map(ProcessingUtility::getQualifiedName).map(string -> importIfPossible(string) + ".class").join();
                addStatement(field.getFieldType(this) + " " + field.getName() + " = resultSet.getSet(" + typeArgumentsAsClasses + ")");
            } else if (ProcessingUtility.isAssignable(fieldType, Map.class)) {
                DeclaredType declaredType = (DeclaredType) fieldType;
                final @Nonnull String typeArgumentsAsClasses = FiniteIterable.of(declaredType.getTypeArguments()).map(ProcessingUtility::getQualifiedName).map(string -> importIfPossible(string) + ".class").join();
                addStatement(field.getFieldType(this) + " " + field.getName() + " = resultSet.getMap(" + typeArgumentsAsClasses + ")");
            } else if (fieldType.toString().equals(byte.class.getCanonicalName()) || fieldType.toString().equals(Byte.class.getCanonicalName())) {
                addStatement(field.getFieldType(this) + " " + field.getName() + " = resultSet.getInteger08()");
            } else if (fieldType.toString().equals(short.class.getCanonicalName()) || fieldType.toString().equals(Short.class.getCanonicalName())) {
                addStatement(field.getFieldType(this) + " " + field.getName() + " = resultSet.getInteger16()");
            } else if (fieldType.toString().equals(int.class.getCanonicalName()) || fieldType.toString().equals(Integer.class.getCanonicalName())) {
                addStatement(field.getFieldType(this) + " " + field.getName() + " = resultSet.getInteger32()");
            } else if (fieldType.toString().equals(long.class.getCanonicalName()) || fieldType.toString().equals(Long.class.getCanonicalName())) {
                addStatement(field.getFieldType(this) + " " + field.getName() + " = resultSet.getInteger64()");
            } else if (fieldType.toString().equals(BigInteger.class.getCanonicalName())) {
                addStatement(field.getFieldType(this) + " " + field.getName() + " = resultSet.getInteger()");
            } else if (fieldType.toString().equals(char.class.getCanonicalName()) || fieldType.toString().equals(Character.class.getCanonicalName())) {
                addStatement(field.getFieldType(this) + " " + field.getName() + " = resultSet.getString01()");
            } else if (fieldType.toString().equals(String.class.getCanonicalName())) {
                addStatement(field.getFieldType(this) + " " + field.getName() + " = resultSet.getString()");
            } else {
                addStatement(field.getFieldType(this) + " " + field.getName() + " = " + importIfPossible(fieldType.toString()) + "Builder.restore(resultSet).build()");
            }
            assignedParameters.append(".with").append(Strings.capitalizeFirstLetters(field.getName())).append("(").append(field.getName()).append(")");
        }
        
        // TODO: check if all constructor parameters are representative.
    
        addStatement("return new " + nameOfBuilder + "()" + assignedParameters);
        endMethod();
    }
    
    /* -------------------------------------------------- Instance -------------------------------------------------- */
    
    private void generateInstanceField() {
        addField("public static final @" + importIfPossible(Nonnull.class) + " " + typeInformation.getSimpleNameOfGeneratedConverter() + " INSTANCE = new " + typeInformation.getSimpleNameOfGeneratedConverter() + "()");
    }
    
    /* -------------------------------------------------- Class Fields -------------------------------------------------- */
    
    private void generateFields() {
        final @Nonnull StringBuilder fieldsString = new StringBuilder();
        for (@Nonnull FieldInformation representingField : typeInformation.getRepresentingFieldInformation()) {
            representingField.getName();
            
            final @Nonnull CustomType customType = CustomType.of(representingField.getType());
            final @Nonnull String converterInstance;
            if (customType == CustomType.TUPLE) {
                converterInstance = importIfPossible(ProcessingUtility.getSimpleName(representingField.getType()) + "Converter") + ".INSTANCE";
            } else {
                converterInstance = "null";
            }
            fieldsString.append(importIfPossible(CustomField.class) + ".with(" + importIfPossible(Pair.class) + ".of(" + importIfPossible(CustomType.class) + "." + customType + ", " + converterInstance + "), " + Quotes.inDouble(representingField.getName()) + ", null)");
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
            beginClass("public class " + typeInformation.getSimpleNameOfGeneratedConverter() + importWithBounds(typeInformation.getTypeArguments()) + " implements " + importIfPossible(Converter.class) + "<" + typeInformation.getName() + ">");
            
            generateInstanceField();
            generateFields();
            generateConvert();
            // TODO: generate restore method
            
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
