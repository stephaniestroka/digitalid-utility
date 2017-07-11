package net.digitalid.utility.generator.generators;

import java.lang.annotation.Annotation;
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
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;

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
import net.digitalid.utility.conversion.exceptions.RecoveryExceptionBuilder;
import net.digitalid.utility.conversion.interfaces.Converter;
import net.digitalid.utility.conversion.interfaces.Decoder;
import net.digitalid.utility.conversion.interfaces.Encoder;
import net.digitalid.utility.conversion.model.CustomAnnotation;
import net.digitalid.utility.conversion.model.CustomField;
import net.digitalid.utility.conversion.model.CustomType;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.GeneratorProcessor;
import net.digitalid.utility.generator.annotations.generators.GenerateTableConverter;
import net.digitalid.utility.generator.exceptions.FailedClassGenerationException;
import net.digitalid.utility.generator.information.field.FieldInformation;
import net.digitalid.utility.generator.information.filter.MethodSignatureMatcher;
import net.digitalid.utility.generator.information.method.ExecutableInformation;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.generator.information.type.EnumInformation;
import net.digitalid.utility.generator.information.type.TypeInformation;
import net.digitalid.utility.immutable.ImmutableList;
import net.digitalid.utility.immutable.ImmutableMap;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processing.utility.StaticProcessingEnvironment;
import net.digitalid.utility.processor.generator.JavaFileGenerator;
import net.digitalid.utility.storage.Module;
import net.digitalid.utility.storage.Table;
import net.digitalid.utility.storage.enumerations.ForeignKeyAction;
import net.digitalid.utility.storage.interfaces.Unit;
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
    
    private String importConverterType(@Nonnull TypeMirror typeMirror, @Nonnull FiniteIterable<@Nonnull AnnotationMirror> annotationMirrors) {
        return CustomType.importConverterType(typeMirror, annotationMirrors, this);
    }
    
    /* -------------------------------------------------- Type Information -------------------------------------------------- */
    
    /**
     * The type information for which a converter is generated.
     */
    protected final @Nonnull TypeInformation typeInformation;
    
    
    /* -------------------------------------------------- Java Version -------------------------------------------------- */
    
    /**
     * The major version number of Java during compilation.
     */
    private final int javaVersion;
    
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
            addStatement("encoder.encode" + Strings.uppercaseFirstCharacter(CustomType.of(type, field.getAnnotations()).getTypeName().toLowerCase()) + "(" + access + ")");
        } else if (ProcessingUtility.isRawSubtype(type, Map.class)) {
            final @Nullable DeclaredType supertype = ProcessingUtility.getSupertype((DeclaredType) type, Map.class);
            if (supertype != null) {
                final boolean nullable = !field.hasAnnotation(NonNullableElements.class);
                final @Nonnull List<@Nonnull ? extends TypeMirror> typeArguments = supertype.getTypeArguments();
                addStatement("encoder.encodeMap" + (nullable ? "WithNullableValues" : "") + "(" + importConverterType(typeArguments.get(0), FiniteIterable.of()) + ", " + importConverterType(typeArguments.get(1), FiniteIterable.of()) + ", " + access + ")");
            }
        } else if (type.getKind() == TypeKind.ARRAY || ProcessingUtility.isRawSubtype(type, Iterable.class)) {
            final @Nullable TypeMirror componentType = ProcessingUtility.getComponentType(type);
            if (componentType != null) {
                if (componentType.getKind() == TypeKind.BYTE) {
                    addStatement("encoder.encodeBinary(" + access + ")");
                } else {
                    final boolean unordered = ProcessingUtility.isRawSubtype(type, Set.class);
                    final boolean nullable = !field.hasAnnotation(NonNullableElements.class);
                    addStatement("encoder.encode" + (unordered ? "Unordered" : "Ordered") + "Iterable" + (nullable ? "WithNullableElements" : "") + "(" + importConverterType(componentType, FiniteIterable.of()) + ", " + importIfPossible(FiniteIterable.class) + ".of(" + access + "))");
                }
            }
        } else if (ProcessingUtility.getTypeElement(type).getKind() == ElementKind.ENUM && StaticProcessingEnvironment.getTypeUtils().isAssignable(type, typeInformation.getType())) {
            addStatement("encoder.encodeObject(" + importIfPossible("net.digitalid.utility.conversion.converters.String64Converter") + ".INSTANCE, " + access + ")" );
        } else {
            addStatement("encoder.encode" + (field.hasAnnotation(Nullable.class) ? "Nullable" : "") + "Object(" + importConverterType(type, field.getAnnotations()) + ", " + access + ")" );
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
    private @Nonnull String generateSelectionResultCall(@Nonnull TypeMirror fieldType, @Nonnull FiniteIterable<AnnotationMirror> annotationMirrors, @Nonnull String provideParameterName) {
        final @Nonnull CustomType customType = CustomType.of(fieldType, annotationMirrors);
        final @Nonnull String customTypeName = Strings.capitalizeFirstLetters(customType.getTypeName().toLowerCase());
        
        if (customType.isCompositeType()) {
            final @Nullable TypeMirror componentType = ProcessingUtility.getComponentType(fieldType);
            Require.that(componentType != null).orThrow("The field type is not an array or list");
            // TODO: for composite types, we cannot simply pass the annotations, because they apply to the collection. If we cannot access the type argument annotations, we should introduce a new set of annotations (something along NonNullableElemennts, MaxSizeElements, etc).
            return getAssignmentPrefix(fieldType, FiniteIterable.of()) + getSelectionResultStatement(customTypeName, generateSelectionResultCall(componentType, FiniteIterable.of(), provideParameterName)) + getAssignmentPostfix(fieldType, FiniteIterable.of());
        } else if (customType.isObjectType()) {
            if (StaticProcessingEnvironment.getTypeUtils().isAssignable(fieldType, typeInformation.getType()) && ProcessingUtility.getTypeElement(fieldType).getKind() == ElementKind.ENUM) {
                return getSelectionResultStatement("String");
            } else {
                final @Nonnull String converterInstance = importConverterType(fieldType, annotationMirrors);
                return converterInstance + ".recover(decoder, " + provideParameterName + ")";
            }
        } else {
            return getSelectionResultStatement(customTypeName);
        }
    }
    
    @Pure
    private @Nonnull String getAssignmentPrefix(@Nonnull TypeMirror fieldType, @Nonnull FiniteIterable<@Nonnull AnnotationMirror> annotationMirrors) {
        if (CustomType.of(fieldType, annotationMirrors).isCompositeType() && ProcessingUtility.getQualifiedName(fieldType).startsWith("net.digitalid.utility.collections.")) {
            return ProcessingUtility.getSimpleName(fieldType) + ".withElementsOf(";
        }
        return "";
    }
    
    @Pure
    private @Nonnull String getAssignmentPostfix(@Nonnull TypeMirror fieldType, @Nonnull FiniteIterable<@Nonnull AnnotationMirror> annotationMirrors) {
        if (CustomType.of(fieldType, annotationMirrors).isCompositeType() && ProcessingUtility.getQualifiedName(fieldType).startsWith("net.digitalid.utility.collections.")) {
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
            addStatement("final " + importIfPossible(field.getType()) + " " + field.getName() + " = decoder.decode" + Strings.uppercaseFirstCharacter(CustomType.of(type, field.getAnnotations()).getTypeName().toLowerCase()) + "()");
        } else if (ProcessingUtility.isRawSubtype(type, Map.class)) {
            final @Nullable DeclaredType supertype = ProcessingUtility.getSupertype((DeclaredType) type, Map.class);
            if (supertype != null) {
                final boolean nullable = !field.hasAnnotation(NonNullableElements.class);
                final @Nonnull List<@Nonnull ? extends TypeMirror> typeArguments = supertype.getTypeArguments();
                addStatement("final " + importIfPossible(field.getType()) + " " + field.getName() + " = decoder.decodeMap" + (nullable ? "WithNullableValues" : "") + "(" + importConverterType(typeArguments.get(0), FiniteIterable.of()) + ", " + provided + ", " + importConverterType(typeArguments.get(1), FiniteIterable.of()) + ", " + provided + ", new " + importIfPossible(LinkedHashMap.class) + "<>())"); // TODO: Find a way to be more flexible than hard-coding LinkedHashMap.
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
                        else { collection = importIfPossible("net.digitalid.utility.collections.set.FreezableLinkedHashSetBuilder") + ".buildWithInitialCapacity(size)"; }
                        collector = "size -> " + importIfPossible(CollectionCollector.class) + ".with(" + collection + ")";
                    } else { collector = "null"; }
                    addStatement("final " + importIfPossible(field.getType()) + " " + field.getName() + " = decoder.decode" + (unordered ? "Unordered" : "Ordered") + "Iterable" + (nullable ? "WithNullableElements" : "") + "(" + importConverterType(componentType, FiniteIterable.of()) + ", " + provided + ", " + collector + ")");
                }
            }
        } else if (ProcessingUtility.getTypeElement(type).getKind() == ElementKind.ENUM && StaticProcessingEnvironment.getTypeUtils().isAssignable(type, typeInformation.getType())) {
            addStatement("final @" + importIfPossible(Nonnull.class) + " " + importIfPossible(String.class) + " value = decoder.decodeObject(" + importIfPossible("net.digitalid.utility.conversion.converters.String64Converter") + ".INSTANCE, " + provided + ")" );
        } else {
            addStatement("final " + importIfPossible(field.getType()) + " " + field.getName() + " = decoder.decode" + (field.hasAnnotation(Nullable.class) ? "Nullable" : "") + "Object(" + importConverterType(type, field.getAnnotations()) + ", " + provided + ")" );
        }
    }
    
    private @Nonnull FiniteIterable<@Nonnull ? extends TypeMirror> getThrownTypesOfInitializeMethod() {
        final @Nullable MethodInformation initializeMethod = typeInformation.getOverriddenMethods().findFirst(MethodSignatureMatcher.of("initialize"));
        if (initializeMethod != null) {
            return initializeMethod.getThrownTypes();
        } else {
            return FiniteIterable.of();
        }
    }
    
    /**
     * Generates a recover method for every representing field of the type and calls the builder.
     */
    @Impure
    protected void generateRecoverMethod() {
        addAnnotation(Pure.class);
        addAnnotation(Override.class);
        beginMethod("public @" + importIfPossible(Capturable.class) + " @" + importIfPossible(Nonnull.class) + " " + Brackets.inPointy("EXCEPTION extends " + importIfPossible(ConnectionException.class)) + " " + typeInformation.getName() + " recover(@" + importIfPossible(Nonnull.class) + " @" + importIfPossible(NonCaptured.class) + " " + importIfPossible(Decoder.class) + Brackets.inPointy("EXCEPTION") + " decoder, " + getExternallyProvidedParameterDeclarationsAsString(getExternallyProvidedParameterNameAsString()) + ") throws EXCEPTION, " + importIfPossible(RecoveryException.class));
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
        final @Nullable ExecutableInformation recoverConstructorOrMethod = typeInformation.getRecoverConstructorOrMethod();
        if (!getThrownTypesOfInitializeMethod().isEmpty() || recoverConstructorOrMethod != null && recoverConstructorOrMethod.throwsExceptions()) { beginTry(); }
        addStatement(typeInformation.getInstantiationCode(true, true, true, externallyProvidedFields.combine(fields)));
        if (!getThrownTypesOfInitializeMethod().isEmpty() || recoverConstructorOrMethod != null && recoverConstructorOrMethod.throwsExceptions()) {
            if (recoverConstructorOrMethod != null && recoverConstructorOrMethod.throwsExceptions()) {
                endTryOrCatchBeginCatch(recoverConstructorOrMethod.getElement().getThrownTypes());
            } else {
                endTryOrCatchBeginCatch(getThrownTypesOfInitializeMethod().toList());
            }
            addStatement("throw " + importIfPossible(RecoveryExceptionBuilder.class) + ".withMessage(\"Could not recover " + Strings.prependWithIndefiniteArticle(Strings.decamelize(typeInformation.getName())) + ".\").withCause(exception).build()");
            endCatch();
        }
        endMethod();
    }
    
    /* -------------------------------------------------- Instance -------------------------------------------------- */
    
    /**
     * Generates a static final instance field for this converter.
     */
    @Impure
    private void generateInstanceField() {
        final @Nonnull @NonNullableElements FiniteIterable<TypeVariable> typeArguments = typeInformation.getTypeArguments();
        addField("public static final @" + importIfPossible(Nonnull.class) + " " + typeInformation.getSimpleNameOfGeneratedConverter() + typeArguments.map(typeVariable -> "?").join(Brackets.POINTY, "") + " INSTANCE = new " + typeInformation.getSimpleNameOfGeneratedConverter() + (typeArguments.isEmpty() ? "" : "<>") + "()");
    }
    
    /* -------------------------------------------------- Class Fields -------------------------------------------------- */
    
    @Impure
    private @Nonnull String importTypeAnnotationIfPossible(Class<? extends Annotation> annotation) {
        if (javaVersion >= 8) {
            return "@" + importIfPossible(annotation) + " ";
        } else {
            return "";
        }
    }
    
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
            fieldsString.append(importIfPossible(CustomField.class)).append(".with(").append(CustomType.getTypeName(representingField.getType(), representingField.getAnnotations(), this)).append(", ").append(Quotes.inDouble(fieldName)).append(", ImmutableList.").append(Brackets.inPointy(importIfPossible(CustomAnnotation.class))).append("withElements(").append(customAnnotations.toString()).append("))");
        }
        addField("private static final @" + importIfPossible(Nonnull.class) + " " + importIfPossible(ImmutableList.class) + Brackets.inPointy(importTypeAnnotationIfPossible(Nonnull.class) + importIfPossible(CustomField.class)) + " fields");
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
        beginMethod("public @" + importIfPossible(Nonnull.class) + " " + importIfPossible(ImmutableList.class) + Brackets.inPointy(importTypeAnnotationIfPossible(Nonnull.class) + " " + importIfPossible(CustomField.class)) + " getFields(@" + importIfPossible(Nonnull.class) + " " + importIfPossible(Representation.class) + " representation)");
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
    
    /* -------------------------------------------------- Default Interface Methods -------------------------------------------------- */
    
    @Impure
    private void generateIsPrimitiveConverterMethod() {
        addAnnotation(Pure.class);
        addAnnotation(Override.class);
        beginMethod("public boolean isPrimitiveConverter()");
        addStatement("return false");
        endMethod();
    }
    
    @Impure
    private void generateGetSupertypeConverterMethod() {
        addAnnotation(Pure.class);
        addAnnotation(Override.class);
        beginMethod("public @" + importIfPossible(Nullable.class) + " " + importIfPossible(Converter.class) + Brackets.inPointy("? super " + importIfPossible(StaticProcessingEnvironment.getTypeUtils().erasure(typeInformation.getType())) + ", " + getExternallyProvidedParameterDeclarationsAsString("")) + " getSupertypeConverter()");
        addStatement("return null");
        endMethod();
    }
    
    @Impure
    private void generateGetSubtypeConvertersMethod() {
        addAnnotation(Pure.class);
        addAnnotation(Override.class);
        beginMethod("public @" + importIfPossible(Nullable.class) + " " + importIfPossible(ImmutableList.class) + Brackets.inPointy(importIfPossible(Converter.class) + Brackets.inPointy("? extends " + importIfPossible(StaticProcessingEnvironment.getTypeUtils().erasure(typeInformation.getType())) + ", " + getExternallyProvidedParameterDeclarationsAsString(""))) + " getSubtypeConverters()");
        addStatement("return null");
        endMethod();
    }
    
    @Impure
    private void generateDefaultInterfaceMethods() {
        generateIsPrimitiveConverterMethod();
        generateGetSupertypeConverterMethod();
        generateGetSubtypeConvertersMethod();
    }
    
    /* -------------------------------------------------- Table -------------------------------------------------- */
    
    @Impure
    private void generateGetParentModule(@Nonnull GenerateTableConverter generateTableConverterAnnotation) {
        final @Nonnull String module = generateTableConverterAnnotation.module();
        if (!module.isEmpty()) {
            addAnnotation(Pure.class);
            addAnnotation(Override.class);
            beginMethod("public @" + importIfPossible(Nonnull.class) + " " + importIfPossible(Module.class) + " getParentModule()");
            addStatement("return " + module);
            endMethod();
        }
    }
    
    @Impure
    private void generateGetName(@Nonnull GenerateTableConverter generateTableConverterAnnotation) {
        final @Nonnull String name = generateTableConverterAnnotation.name();
        if (!name.isEmpty()) {
            addAnnotation(Pure.class);
            addAnnotation(Override.class);
            beginMethod("public @" + importIfPossible(Nonnull.class) + " " + importIfPossible(String.class) + " getName()");
            addStatement("return " + Quotes.inDouble(name));
            endMethod();
        }
    }
    
    @Impure
    private void generateGetSchemaName(@Nonnull GenerateTableConverter generateTableConverterAnnotation) {
        final @Nonnull String schema = generateTableConverterAnnotation.schema();
        if (!schema.isEmpty()) {
            addAnnotation(Pure.class);
            addAnnotation(Override.class);
            beginMethod("public @" + importIfPossible(Nonnull.class) + " " + importIfPossible(String.class) + " getSchemaName(@" + importIfPossible(Nonnull.class) + " " + importIfPossible(Unit.class) + " unit)");
            addStatement("return " + Quotes.inDouble(schema));
            endMethod();
        }
    }
    
    @Impure
    private void generateGetTableName(@Nonnull GenerateTableConverter generateTableConverterAnnotation) {
        final @Nonnull String table = generateTableConverterAnnotation.table();
        if (!table.isEmpty()) {
            addAnnotation(Pure.class);
            addAnnotation(Override.class);
            beginMethod("public @" + importIfPossible(Nonnull.class) + " " + importIfPossible(String.class) + " getTableName(@" + importIfPossible(Nonnull.class) + " " + importIfPossible(Unit.class) + " unit)");
            addStatement("return " + Quotes.inDouble(table));
            endMethod();
        }
    }
    
    @Impure
    private void generateGetColumnNames(@Nonnull GenerateTableConverter generateTableConverterAnnotation) {
        final @Nonnull FiniteIterable<String> columns = FiniteIterable.of(generateTableConverterAnnotation.columns());
        if (!columns.isEmpty()) {
            addAnnotation(Pure.class);
            addAnnotation(Override.class);
            beginMethod("public @" + importIfPossible(Nonnull.class) + " @" + importIfPossible(NonNullableElements.class) + " " + importIfPossible(ImmutableList.class) + Brackets.inPointy(importIfPossible(String.class)) + " getColumnNames(@" + importIfPossible(Nonnull.class) + " " + importIfPossible(Unit.class) + " unit)");
            addStatement("return " + importIfPossible(ImmutableList.class) + ".withElements(" + columns.map(Quotes::inDouble).join() + ")");
            endMethod();
        }
    }
    
    @Impure
    private void generateGetOnDeleteAction(@Nonnull GenerateTableConverter generateTableConverterAnnotation) {
        final @Nonnull ForeignKeyAction onDelete = generateTableConverterAnnotation.onDelete();
        if (onDelete != ForeignKeyAction.CASCADE) {
            addAnnotation(Pure.class);
            addAnnotation(Override.class);
            beginMethod("public @" + importIfPossible(Nonnull.class) + " " + importIfPossible(ForeignKeyAction.class) + " getOnDeleteAction()");
            addStatement("return " + importIfPossible(ForeignKeyAction.class) + "." + onDelete.name());
            endMethod();
        }
    }
    
    @Impure
    private void generateGetOnUpdateAction(@Nonnull GenerateTableConverter generateTableConverterAnnotation) {
        final @Nonnull ForeignKeyAction onUpdate = generateTableConverterAnnotation.onUpdate();
        if (onUpdate != ForeignKeyAction.CASCADE) {
            addAnnotation(Pure.class);
            addAnnotation(Override.class);
            beginMethod("public @" + importIfPossible(Nonnull.class) + " " + importIfPossible(ForeignKeyAction.class) + " getOnUpdateAction()");
            addStatement("return " + importIfPossible(ForeignKeyAction.class) + "." + onUpdate.name());
            endMethod();
        }
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a new converter generator instance.
     */
    protected ConverterGenerator(@Nonnull TypeInformation typeInformation) {
        super(typeInformation.getQualifiedNameOfGeneratedConverter(), typeInformation.getElement());
        
        this.typeInformation = typeInformation;
        
        final @Nullable GenerateTableConverter generateTableConverterAnnotation = typeInformation.getAnnotationOrNull(GenerateTableConverter.class);
        final @Nonnull String superType = generateTableConverterAnnotation != null ? " extends " + importIfPossible(Table.class) : " implements " + importIfPossible(Converter.class);
        beginClass("public class " + typeInformation.getSimpleNameOfGeneratedConverter() + importWithBounds(typeInformation.getTypeArguments()) + superType + "<" + typeInformation.getName() + ", " + getExternallyProvidedParameterDeclarationsAsString("") + ">");
        
        if (generateTableConverterAnnotation != null) {
            generateGetParentModule(generateTableConverterAnnotation);
            generateGetName(generateTableConverterAnnotation);
            generateGetSchemaName(generateTableConverterAnnotation);
            generateGetTableName(generateTableConverterAnnotation);
            generateGetColumnNames(generateTableConverterAnnotation);
            generateGetOnDeleteAction(generateTableConverterAnnotation);
            generateGetOnUpdateAction(generateTableConverterAnnotation);
        }
        
        generateInstanceField();
        generateGetType();
        generateGetTypeName();
        generateGetTypePackage();
        generateFields();
        generateGetFields();
        generateConvertMethod();
        generateRecoverMethod();
    
        final @Nonnull ProcessingEnvironment processingEnvironment = StaticProcessingEnvironment.environment.get();
        this.javaVersion = processingEnvironment.getSourceVersion().ordinal();
        if (javaVersion < 8) {
            generateDefaultInterfaceMethods();
        }
        
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
