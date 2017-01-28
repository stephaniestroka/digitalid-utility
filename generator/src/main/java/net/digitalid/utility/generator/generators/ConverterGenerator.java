package net.digitalid.utility.generator.generators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.circumfixes.Brackets;
import net.digitalid.utility.circumfixes.Quotes;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.conversion.collectors.ArrayCollector;
import net.digitalid.utility.conversion.collectors.CollectionCollector;
import net.digitalid.utility.conversion.enumerations.Representation;
import net.digitalid.utility.conversion.exceptions.ConnectionException;
import net.digitalid.utility.conversion.exceptions.RecoveryException;
import net.digitalid.utility.conversion.interfaces.Converter;
import net.digitalid.utility.conversion.interfaces.Decoder;
import net.digitalid.utility.conversion.interfaces.Encoder;
import net.digitalid.utility.conversion.model.CustomAnnotation;
import net.digitalid.utility.conversion.model.CustomField;
import net.digitalid.utility.conversion.model.CustomType;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.GeneratorProcessor;
import net.digitalid.utility.generator.exceptions.FailedClassGenerationException;
import net.digitalid.utility.generator.information.field.FieldInformation;
import net.digitalid.utility.generator.information.type.EnumInformation;
import net.digitalid.utility.generator.information.type.TypeInformation;
import net.digitalid.utility.immutable.ImmutableList;
import net.digitalid.utility.immutable.ImmutableMap;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processing.utility.StaticProcessingEnvironment;
import net.digitalid.utility.processor.generator.JavaFileGenerator;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.tuples.Tuple;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.generation.Provide;
import net.digitalid.utility.validation.annotations.generation.Provided;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class generates a converter with the provided type information.
 * 
 * @see GeneratorProcessor
 * @see TypeInformation
 */
@Mutable
public class ConverterGenerator extends JavaFileGenerator {
    
    /* -------------------------------------------------- Converter Import -------------------------------------------------- */
    
    /**
     * Returns the imported name (if possible) of the converter of the given type.
     */
    @Impure
    public @Nonnull String importConverterType(@Nonnull TypeMirror type) {
        @Nonnull String qualifiedName = ProcessingUtility.getQualifiedName(type);
        if (!qualifiedName.startsWith("net.digitalid")) { qualifiedName = qualifiedName.replace(Strings.substringUntilLast(qualifiedName, '.'), "net.digitalid.utility.conversion.converters"); }
        return importIfPossible(qualifiedName + "Converter") + ".INSTANCE";
    }
    
    /* -------------------------------------------------- Type Information -------------------------------------------------- */
    
    /**
     * The type information for which a converter is generated.
     */
    protected final @Nonnull TypeInformation typeInformation;
    
    /* -------------------------------------------------- Externally and Non-Externally Provided Fields -------------------------------------------------- */
    
    /**
     * Returns all field information objects that do not have a @Provided annotation.
     */
    @Pure
    private @Nonnull FiniteIterable<@Nonnull FieldInformation> filterNonExternallyProvidedFields(@Nonnull FiniteIterable<@Nonnull FieldInformation> fieldInformation) {
        return fieldInformation.filter(field -> !field.hasAnnotation(Provided.class));
    }
    
    /**
     * Returns all field information objects that do have a @Provided annotation.
     */
    @Pure
    private @Nonnull FiniteIterable<@Nonnull FieldInformation> filterExternallyProvidedFields(@Nonnull FiniteIterable<@Nonnull FieldInformation> fieldInformation) {
        return fieldInformation.filter(field -> field.hasAnnotation(Provided.class));
    }
    
    /**
     * Returns a list of externally provided parameter declarations as comma-separated string.
     */
    @Pure
    @SuppressWarnings("AssignmentToMethodParameter")
    private @Nonnull String getExternallyProvidedParameterDeclarationsAsString(@Nonnull String postFix) {
        postFix = postFix.isEmpty() ? postFix : " " + postFix;
        final @Nonnull FiniteIterable<@Nonnull FieldInformation> externallyProvidedFields = filterExternallyProvidedFields(typeInformation.getRepresentingFieldInformation());
        final @Nonnull String parameterTypes = externallyProvidedFields.map(field -> importIfPossible(field.getType())).join();
        final @Nullable Class<?> tupleType = Tuple.getTupleType(externallyProvidedFields.size());
        if (tupleType == null) {
            switch (externallyProvidedFields.size()) {
                case 0:
                    return importIfPossible(Void.class) + postFix;
                case 1:
                    return importIfPossible(externallyProvidedFields.getFirst().getType()) + postFix;
                default:
                    throw FailedClassGenerationException.with("Cannot accept more than 8 externally provided parameters.");
            }
        }
        return importIfPossible(tupleType) + Brackets.inPointy(parameterTypes) + postFix;
    }
    
    /**
     * Returns a list of externally provided parameter declarations as comma-separated string.
     */
    @Pure
    private @Nonnull String getExternallyProvidedParameterNameAsString() {
        final @Nonnull FiniteIterable<@Nonnull FieldInformation> externallyProvidedFields = filterExternallyProvidedFields(typeInformation.getRepresentingFieldInformation());
        switch (externallyProvidedFields.size()) {
            case 0:
                return "none";
            case 1:
                return externallyProvidedFields.getFirst().getName();
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                return "provided";
            default:
                throw FailedClassGenerationException.with("Cannot accept more than 8 externally provided parameters.");
        }
    }
    
    /* -------------------------------------------------- Value Collector -------------------------------------------------- */
    
    /**
     * Returns the name of the variable that is used in the {@link Converter#convert(java.lang.Object, net.digitalid.utility.conversion.interfaces.Encoder)} method.
     */
    @Pure
    private final @Nonnull String getObjectVariableName() {
        return Strings.lowercaseFirstCharacter(typeInformation.getName());
    }
    
//    /**
//     * Returns a generated call to the value collector from the given type and field access strings and the optionally given value collector call for composite types.
//     */
//    @Pure
//    private @Nonnull String getValueCollectorStatement(@Nonnull TypeMirror fieldType, @Nonnull String fieldAccess, @Nullable String encoderCall, @Nullable String iterableItemName) {
//        final @Nonnull CustomType customType = CustomType.of(fieldType);
//        final @Nonnull String customTypePrefix = Strings.uppercaseFirstCharacter(customType.getTypeName().toLowerCase());
//        
//        Require.that(encoderCall == null && iterableItemName == null || encoderCall != null && iterableItemName != null).orThrow("The parameters encoderCall and iterableItemName must either both be null or non-null.");
//        return "encoder.encode" + customTypePrefix + "(" + fieldAccess + (encoderCall == null ? "" : ", (" + iterableItemName + ") -> " + encoderCall) + ")";
//    }
//    
//    /**
//     * Returns a generated call to the value collector from the given type and field access strings.
//     */
//    private @Nonnull String getValueCollectorStatement(@Nonnull TypeMirror fieldType, @Nonnull String fieldAccess) {
//        return getValueCollectorStatement(fieldType, fieldAccess, null, null);
//    }
//    
//    /**
//     * Returns a generated statement that adds the field value to the value collector.
//     */
//    @SuppressWarnings("AssignmentToMethodParameter")
//    private @Nonnull String generateValueCollectorCall(@Nonnull String fieldAccess, @Nonnull TypeMirror fieldType, int round) {
//        final @Nonnull CustomType customType = CustomType.of(fieldType);
//        
//        if (customType.isCompositeType()) {
//            final @Nullable TypeMirror componentType = ProcessingUtility.getComponentType(fieldType);
//            Require.that(componentType != null).orThrow("The field type is not an array or list");
//            final @Nonnull String entryName = "entry" + round;
//            return getValueCollectorStatement(fieldType, fieldAccess, generateValueCollectorCall(entryName, componentType, ++round), entryName);
//        } else if (customType.isObjectType()) {
//            if (StaticProcessingEnvironment.getTypeUtils().isAssignable(fieldType, typeInformation.getType()) && ProcessingUtility.getTypeElement(fieldType).getKind() == ElementKind.ENUM) {
//                return getValueCollectorStatement(ProcessingUtility.getTypeMirror(String.class), fieldAccess);
//            } else {
//                final @Nonnull String converterInstance = importConverterType(fieldType);
//                return converterInstance + ".convert(" + Strings.lowercaseFirstCharacter(typeInformation.getName()) + " == null ? null : " + fieldAccess + ", encoder)";
//            }
//        } else {
//            return getValueCollectorStatement(fieldType, fieldAccess);
//        }
//    }
    
    @Impure
    private void addEncodingStatement(@Nonnull FieldInformation field) {
        final @Nonnull TypeMirror type = field.getType();
        final @Nonnull String access = getObjectVariableName() + "." + field.getAccessCode();
        
        if ((type.getKind() == TypeKind.ARRAY || ProcessingUtility.isRawSubtype(type, Iterable.class) || ProcessingUtility.isRawSubtype(type, Map.class)) && !field.hasAnnotation(Nonnull.class)) {
            ProcessingLog.error("Cannot convert an array, iterable or map that is nullable.", SourcePosition.of(field.getElement()));
        }
        
        if (type.getKind().isPrimitive()) {
            addStatement("encoder.encode" + Strings.uppercaseFirstCharacter(CustomType.of(type).getTypeName().toLowerCase()) + "(" + access + ")");
        } else if (ProcessingUtility.isRawSubtype(type, Map.class)) {
            final @Nullable DeclaredType supertype = ProcessingUtility.getSupertype((DeclaredType) type, Map.class);
            if (supertype != null) {
                final boolean nullable = !field.hasAnnotation(NonNullableElements.class);
                final @Nonnull List<@Nonnull ? extends TypeMirror> typeArguments = supertype.getTypeArguments();
                addStatement("encoder.encodeMap" + (nullable ? "WithNullableValues" : "") + "(" + importConverterType(typeArguments.get(0)) + ", " + importConverterType(typeArguments.get(1)) + ", " + access + ")");
            }
        } else if (type.getKind() == TypeKind.ARRAY || ProcessingUtility.isRawSubtype(type, Iterable.class)) {
            final @Nullable TypeMirror componentType = ProcessingUtility.getComponentType(type);
            if (componentType != null) {
                if (componentType.getKind() == TypeKind.BYTE) {
                    addStatement("encoder.encodeBinary(" + access + ")");
                } else {
                    final boolean unordered = ProcessingUtility.isRawSubtype(type, Set.class);
                    final boolean nullable = !field.hasAnnotation(NonNullableElements.class);
                    addStatement("encoder.encode" + (unordered ? "Unordered" : "Ordered") + "Iterable" + (nullable ? "WithNullableElements" : "") + "(" + importConverterType(componentType) + ", " + importIfPossible(FiniteIterable.class) + ".of(" + access + "))");
                }
            }
        } else if (ProcessingUtility.getTypeElement(type).getKind() == ElementKind.ENUM && StaticProcessingEnvironment.getTypeUtils().isAssignable(type, typeInformation.getType())) {
            addStatement("encoder.encodeObject(" + importIfPossible("net.digitalid.utility.conversion.converters.StringConverter") + ".INSTANCE, " + access + ")" );
        } else {
            addStatement("encoder.encode" + (field.hasAnnotation(Nullable.class) ? "Nullable" : "") + "Object(" + importConverterType(type) + ", " + access + ")" );
        }
    }
    
    /**
     * Generates the convert method. Every representing field value is collected by a value collector.
     */
    @Impure
    private void generateConvertMethod() {
        addAnnotation(Pure.class);
        addAnnotation(Override.class);
        beginMethod("public " + Brackets.inPointy("EXCEPTION extends " + importIfPossible(ConnectionException.class)) + " void convert(@" + importIfPossible(NonCaptured.class) + " @" + importIfPossible(Unmodified.class) + " @" + importIfPossible(Nonnull.class) + " " + typeInformation.getName() + " " + getObjectVariableName() + ", @" + importIfPossible(Nonnull.class) + " @" + importIfPossible(NonCaptured.class) + " @" + importIfPossible(Modified.class) + " " + importIfPossible(Encoder.class) + Brackets.inPointy("EXCEPTION") + " encoder) throws EXCEPTION");
        filterNonExternallyProvidedFields(typeInformation.getRepresentingFieldInformation()).doForEach(this::addEncodingStatement);
//        for (@Nonnull FieldInformation field : fields) {
//            final @Nonnull String fieldAccess = objectVariableName + "." + field.getAccessCode();
//            addStatement("i *= " + generateValueCollectorCall(fieldAccess, field.getType(), 1));
//        }
        endMethod();
    }
    
    /* -------------------------------------------------- Recovery -------------------------------------------------- */
    
    /**
     * Returns a generated call to the selection result from the given type string and the optionally given selection result call for composite types.
     */
    @Pure
    private @Nonnull String getSelectionResultStatement(@Nonnull String typeAsString, @Nullable String decoderCall) {
        return "decoder.decode" + typeAsString + "(" + (decoderCall == null ? "" : "() -> " + decoderCall) + ")";
    }
    
    /**
     * Returns a generated call to the selection result from the given type string.
     */
    @Pure
    private @Nonnull String getSelectionResultStatement(@Nonnull String typeAsString) {
        return getSelectionResultStatement(typeAsString, null);
    }
    
    /**
     * Returns a generated statement that reads the field value from the selection result.
     */
    @Impure
    private @Nonnull String generateSelectionResultCall(@Nonnull TypeMirror fieldType, @Nonnull String provideParameterName) {
        final @Nonnull CustomType customType = CustomType.of(fieldType);
        final @Nonnull String customTypeName = Strings.capitalizeFirstLetters(customType.getTypeName().toLowerCase());
        
        if (customType.isCompositeType()) {
            final @Nullable TypeMirror componentType = ProcessingUtility.getComponentType(fieldType);
            Require.that(componentType != null).orThrow("The field type is not an array or list");
            return getAssignmentPrefix(fieldType) + getSelectionResultStatement(customTypeName, generateSelectionResultCall(componentType, provideParameterName)) + getAssignmentPostfix(fieldType);
        } else if (customType.isObjectType()) {
            if (StaticProcessingEnvironment.getTypeUtils().isAssignable(fieldType, typeInformation.getType()) && ProcessingUtility.getTypeElement(fieldType).getKind() == ElementKind.ENUM) {
                return getSelectionResultStatement("String");
            } else {
                final @Nonnull String converterInstance = importConverterType(fieldType);
                return converterInstance + ".recover(decoder, " + provideParameterName + ")";
            }
        } else {
            return getSelectionResultStatement(customTypeName);
        }
    }
    
    @Pure
    private @Nonnull String getAssignmentPrefix(@Nonnull TypeMirror fieldType) {
        if (CustomType.of(fieldType).isCompositeType() && ProcessingUtility.getQualifiedName(fieldType).startsWith("net.digitalid.utility.collections.")) {
            return ProcessingUtility.getSimpleName(fieldType) + ".withElementsOf(";
        }
        return "";
    }
    
    @Pure
    private @Nonnull String getAssignmentPostfix(@Nonnull TypeMirror fieldType) {
        if (CustomType.of(fieldType).isCompositeType() && ProcessingUtility.getQualifiedName(fieldType).startsWith("net.digitalid.utility.collections.")) {
            return ")";
        } else {
            return "";
        }
    }
    
    @Impure
    private void addDecodingStatement(@Nonnull FieldInformation field) {
        final @Nonnull TypeMirror type = field.getType();
        final @Nonnull String provided = field.hasAnnotation(Provide.class) ? field.getAnnotation(Provide.class).value() : "null";
        
        if (type.getKind().isPrimitive()) {
            addStatement("final " + importIfPossible(field.getType()) + " " + field.getName() + " = decoder.decode" + Strings.uppercaseFirstCharacter(CustomType.of(type).getTypeName().toLowerCase()) + "()");
        } else if (ProcessingUtility.isRawSubtype(type, Map.class)) {
            final @Nullable DeclaredType supertype = ProcessingUtility.getSupertype((DeclaredType) type, Map.class);
            if (supertype != null) {
                final boolean nullable = !field.hasAnnotation(NonNullableElements.class);
                final @Nonnull List<@Nonnull ? extends TypeMirror> typeArguments = supertype.getTypeArguments();
                addStatement("final " + importIfPossible(field.getType()) + " " + field.getName() + " = decoder.decodeMap" + (nullable ? "WithNullableValues" : "") + "(" + importConverterType(typeArguments.get(0)) + ", " + provided + ", " + importConverterType(typeArguments.get(1)) + ", " + provided + ", new " + importIfPossible(LinkedHashMap.class) + "<>())"); // TODO: Find a way to be more flexible than hard-coding LinkedHashMap.
            }
        } else if (type.getKind() == TypeKind.ARRAY || ProcessingUtility.isRawSubtype(type, Iterable.class)) {
            final @Nullable TypeMirror componentType = ProcessingUtility.getComponentType(type);
            if (componentType != null) {
                if (componentType.getKind() == TypeKind.BYTE) {
                    addStatement("final @" + importIfPossible(Nonnull.class) + " byte[] " + field.getName() + " = decoder.decodeBinary()");
                } else {
                    final boolean unordered = ProcessingUtility.isRawSubtype(type, Set.class);
                    final boolean nullable = !field.hasAnnotation(NonNullableElements.class);
                    final @Nonnull String collector;
                    final @Nonnull String typeName = ProcessingUtility.getSimpleName(type);
                    if (type.getKind() == TypeKind.ARRAY) { collector = importIfPossible(ArrayCollector.class) + "::with"; }
                    else if (ProcessingUtility.isRawSubtype(type, List.class)) {
                        final @Nonnull String collection;
                        if (!typeName.startsWith("Freezable") && !typeName.startsWith("ReadOnly")) { collection = "new " + importIfPossible(ArrayList.class) + "<>(size)"; }
                        else { collection = importIfPossible("net.digitalid.utility.collections.list.FreezableArrayList") + ".withInitialCapacity(size)"; }
                        collector = "size -> " + importIfPossible(CollectionCollector.class) + ".with(" + collection + ")";
                    } else if (ProcessingUtility.isRawSubtype(type, Set.class)) {
                        final @Nonnull String collection;
                        if (!typeName.startsWith("Freezable") && !typeName.startsWith("ReadOnly")) { collection = "new " + importIfPossible(LinkedHashSet.class) + "<>(size)"; }
                        else { collection = importIfPossible("net.digitalid.utility.collections.set.FreezableLinkedHashSet") + ".withInitialCapacity(size)"; }
                        collector = "size -> " + importIfPossible(CollectionCollector.class) + ".with(" + collection + ")";
                    } else { collector = "null"; }
                    addStatement("final " + importIfPossible(field.getType()) + " " + field.getName() + " = decoder.decode" + (unordered ? "Unordered" : "Ordered") + "Iterable" + (nullable ? "WithNullableElements" : "") + "(" + importConverterType(componentType) + ", " + provided + ", " + collector + ")");
                }
            }
        } else if (ProcessingUtility.getTypeElement(type).getKind() == ElementKind.ENUM && StaticProcessingEnvironment.getTypeUtils().isAssignable(type, typeInformation.getType())) {
            addStatement("final @" + importIfPossible(Nonnull.class) + " " + importIfPossible(String.class) + " value = decoder.decodeObject(" + importIfPossible("net.digitalid.utility.conversion.converters.StringConverter") + ".INSTANCE, " + provided + ")" );
        } else {
            addStatement("final " + importIfPossible(field.getType()) + " " + field.getName() + " = decoder.decode" + (field.hasAnnotation(Nullable.class) ? "Nullable" : "") + "Object(" + importConverterType(type) + ", " + provided + ")" );
        }
    }
    
    /**
     * Generates a recover method for every representing field of the type and calls the builder.
     */
    @Impure
    protected void generateRecoverMethod() {
        addAnnotation(Pure.class);
        addAnnotation(Override.class);
        beginMethod("public @" + importIfPossible(Capturable.class) + " " + Brackets.inPointy("EXCEPTION extends " + importIfPossible(ConnectionException.class)) + " @" + importIfPossible(Nonnull.class) + " " + typeInformation.getName() + " recover(@" + importIfPossible(Nonnull.class) + " @" + importIfPossible(NonCaptured.class) + " " + importIfPossible(Decoder.class) + Brackets.inPointy("EXCEPTION") + " decoder, " + getExternallyProvidedParameterDeclarationsAsString(getExternallyProvidedParameterNameAsString()) + ") throws EXCEPTION, " + importIfPossible(RecoveryException.class));
        final @Nonnull FiniteIterable<@Nonnull FieldInformation> externallyProvidedFields = filterExternallyProvidedFields(typeInformation.getRepresentingFieldInformation());
        
        if (externallyProvidedFields.size() > 1) {
            int i = 0;
            for (@Nonnull FieldInformation externallyProvidedField : externallyProvidedFields) {
                addStatement("final " + importIfPossible(externallyProvidedField.getType()) + " " + externallyProvidedField.getName() + " = provided.get" + i++ + "()");
            }
        }
        
        final @Nonnull FiniteIterable<@Nonnull FieldInformation> fields = filterNonExternallyProvidedFields(typeInformation.getRepresentingFieldInformation());
        
//        for (@Nonnull FieldInformation constructorParameter : fields) {
//            final @Nonnull String provide = constructorParameter.hasAnnotation(Provide.class) ? constructorParameter.getAnnotation(Provide.class).value() : "null";
//            if (constructorParameter instanceof EnumValueInformation) {
//                addStatement("final @" + importIfPossible(Nonnull.class) + " " + importIfPossible(String.class) + " value = " + generateSelectionResultCall(constructorParameter.getType(), provide));
//            } else {
//                addStatement("final " + importIfPossible(constructorParameter.getType()) + " " + constructorParameter.getName() + " = " + generateSelectionResultCall(constructorParameter.getType(), provide));
//            }
//        }
        
        fields.doForEach(this::addDecodingStatement);
        addStatement(typeInformation.getInstantiationCode(true, true, true, externallyProvidedFields.combine(fields)));
        endMethod();
    }
    
    /* -------------------------------------------------- Instance -------------------------------------------------- */
    
    /**
     * Generates a static final instance field for this converter.
     */
    @Impure
    private void generateInstanceField() {
        addField("public static final @" + importIfPossible(Nonnull.class) + " " + typeInformation.getSimpleNameOfGeneratedConverter() + " INSTANCE = new " + typeInformation.getSimpleNameOfGeneratedConverter() + "()");
    }
    
    /* -------------------------------------------------- Class Fields -------------------------------------------------- */
    
    /**
     * Generates the custom fields from the representing fields of the type.
     */
    @Impure
    private void generateFields() {
        final @Nonnull StringBuilder fieldsString = new StringBuilder();
        final @Nonnull List<@Nonnull String> statements = new ArrayList<>();
        boolean addedMap = false;
        for (@Nonnull FieldInformation representingField : filterNonExternallyProvidedFields(typeInformation.getRepresentingFieldInformation())) {
            final @Nonnull StringBuilder customAnnotations = new StringBuilder();
            final @Nonnull FiniteIterable<@Nonnull AnnotationMirror> annotations = representingField.getAnnotations();
            final @Nonnull String fieldName = representingField.getName();
            // the alreadyProcessedAnnotations field is used to check whether annotations are listed multiple times, which is possible, for example if they have ElementType.TypeUse and ElementType.Field defined as Target
            final @Nonnull Set<Class> alreadyProcessedAnnotations = new HashSet<>();
            // annotations are only collected if it isn't an enum we're processing. Enum values cannot have annotations.
            if (!(typeInformation instanceof EnumInformation)) {
                for (@Nonnull AnnotationMirror annotation : annotations) {
                    final @Nullable Class<@Nonnull ?> annotationClass = ProcessingUtility.getClass(annotation.getAnnotationType());
                    Require.that(annotationClass != null).orThrow("The annotation class {} should not resolve into a null type.");
                    if (alreadyProcessedAnnotations.add(annotationClass)) {
                        // only add the annotation if it was not already processed before.
                        final @Nonnull String annotationName = ProcessingUtility.getSimpleName(annotation);
                        final @Nonnull String qualifiedAnnotationName = ProcessingUtility.getQualifiedName(annotation.getAnnotationType());
                        final @Nonnull String annotationValuesMap = fieldName + Strings.capitalizeFirstLetters(annotationName);
                        if (customAnnotations.length() != 0) {
                            customAnnotations.append(", ");
                        }
                        customAnnotations.append(importIfPossible(CustomAnnotation.class)).append(".with").append(Brackets.inRound(importIfPossible(qualifiedAnnotationName) + ".class, " + importIfPossible(ImmutableMap.class) + ".withMappingsOf" + Brackets.inRound(annotationValuesMap)));
                        statements.add("final @" + importIfPossible(Nonnull.class) + " " + importIfPossible(Map.class) + Brackets.inPointy("@" + importIfPossible(Nonnull.class) + " " + importIfPossible(String.class) + ", @" + importIfPossible(Nullable.class) + " " + importIfPossible(Object.class)) + " " + annotationValuesMap + " = new " + importIfPossible(HashMap.class) + Brackets.inPointy("") + Brackets.inRound(""));
                        addedMap = true;
                        final @Nonnull Map<@Nonnull String, @Nonnull AnnotationValue> annotationValues = ProcessingUtility.getAnnotationValues(annotation);
                        for (Map.Entry<@Nonnull String, @Nonnull AnnotationValue> entry : annotationValues.entrySet()) {
                            @Nonnull String printValue = ProcessingUtility.getAnnotationValueAsString(entry.getValue(), this);
                            if (printValue.startsWith("{") && printValue.contains(".class")) {
                                final @Nonnull String nameOfVariable = annotationValuesMap + Strings.capitalizeFirstLetters(entry.getKey()) + "Classes";
                                statements.add("Class[] " + nameOfVariable + " = " + printValue);
                                statements.add(annotationValuesMap + ".put" + Brackets.inRound(Quotes.inDouble(entry.getKey()) + ", " + nameOfVariable));
                            } else {
                                statements.add(annotationValuesMap + ".put" + Brackets.inRound(Quotes.inDouble(entry.getKey()) + ", " + printValue));
                            }
                        }
                    }
                }
            }
            if (fieldsString.length() != 0) {
                fieldsString.append(", ");
            }
            fieldsString.append(importIfPossible(CustomField.class)).append(".with(").append(CustomType.getTypeName(representingField.getType(), this)).append(", ").append(Quotes.inDouble(fieldName)).append(", ImmutableList.withElements(").append(customAnnotations.toString()).append("))");
        }
        addField("private static final @" + importIfPossible(Nonnull.class) + " " + importIfPossible(ImmutableList.class) + "<@" + importIfPossible(Nonnull.class) + " " + importIfPossible(CustomField.class) + "> fields");
        beginBlock(true);
        for (@Nonnull String statement : statements) {
            addStatement(statement);
        }
        if (addedMap) {
            addEmptyLine();
        }
        addStatement("fields = " + importIfPossible(ImmutableList.class) + ".withElements(" + fieldsString + ")");
        endBlock();
        addEmptyLine();
    }
    
    /**
     * Generates the declare method that iterates through the representing fields and sets the field in the declaration object.
     */
    @Impure
    private void generateGetFields() {
        addAnnotation(Pure.class);
        addAnnotation(Override.class);
        beginMethod("public @" + importIfPossible(Nonnull.class) + " " + importIfPossible(ImmutableList.class) + Brackets.inPointy("@" + importIfPossible(Nonnull.class) + " " + importIfPossible(CustomField.class)) + " getFields(@" + importIfPossible(Nonnull.class) + " " + importIfPossible(Representation.class) + " representation)");
        addStatement("return fields");
        endMethod();
    }
    
    /* -------------------------------------------------- Type -------------------------------------------------- */
    
    @Impure
    private void generateGetType() {
        addAnnotation(Pure.class);
        addAnnotation(Override.class);
        beginMethod("public @" + importIfPossible(Nonnull.class) + " Class<" + importIfPossible(StaticProcessingEnvironment.getTypeUtils().erasure(typeInformation.getType())) + "> getType()");
        addStatement("return " + importIfPossible(StaticProcessingEnvironment.getTypeUtils().erasure(typeInformation.getType())) + ".class");
        endMethod();
    }
    
    /* -------------------------------------------------- Name -------------------------------------------------- */
    
    @Impure
    private void generateGetTypeName() {
        addAnnotation(Pure.class);
        addAnnotation(Override.class);
        beginMethod("public @" + importIfPossible(Nonnull.class) + " " + importIfPossible(String.class) + " getTypeName()");
        addStatement("return " + Quotes.inDouble(typeInformation.getName()));
        endMethod();
    }
    
    /* -------------------------------------------------- Package -------------------------------------------------- */
    
    @Impure
    private void generateGetTypePackage() {
        addAnnotation(Pure.class);
        addAnnotation(Override.class);
        beginMethod("public @" + importIfPossible(Nonnull.class) + " " + importIfPossible(String.class) + " getTypePackage()");
        addStatement("return " + Quotes.inDouble(typeInformation.getQualifiedPackageName()));
        endMethod();
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a new converter generator instance.
     */
    protected ConverterGenerator(@Nonnull TypeInformation typeInformation) {
        super(typeInformation.getQualifiedNameOfGeneratedConverter(), typeInformation.getElement());
    
        this.typeInformation = typeInformation;
    
        beginClass("public class " + typeInformation.getSimpleNameOfGeneratedConverter() + importWithBounds(typeInformation.getTypeArguments()) + " implements " + importIfPossible(Converter.class) + "<" + typeInformation.getName() + ", " + getExternallyProvidedParameterDeclarationsAsString("") + ">");
        
        generateInstanceField();
        generateGetType();
        generateGetTypeName();
        generateGetTypePackage();
        generateFields();
        generateGetFields();
        generateConvertMethod();
        generateRecoverMethod();
        
        endClass();
    }
    
    /**
     * Generates a new converter class for the given type information.
     */
    @Impure
    public static void generateConverterFor(@Nonnull TypeInformation typeInformation) {
        ProcessingLog.debugging("generateConverterFor(" + typeInformation + ")");
        new ConverterGenerator(typeInformation).write();
    }
    
}
