package net.digitalid.utility.conversion.model;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.reference.NonRawRecipient;
import net.digitalid.utility.circumfixes.Brackets;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.conversion.interfaces.Converter;
import net.digitalid.utility.conversion.model.utility.FieldTypeSignature;
import net.digitalid.utility.functional.interfaces.Predicate;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processing.utility.TypeImporter;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.tuples.Tuple;

/**
 * Instances of the custom type represent the types used in Digital ID.
 * A custom type can either be a primitive type, a composite type or an object type.
 */
public class CustomType {
    
    /* -------------------------------------------------- Composite Type Subclass -------------------------------------------------- */
    
    /**
     * A custom type that holds a composite type.
     */
    public static class IterableType extends CustomType {
        
        /**
         * Creates a custom type.
         */
        private IterableType(@Nonnull Predicate<@Nonnull FieldTypeSignature> predicate, @Nonnull String typeName) {
            super(predicate, typeName);
        }
        
        /**
         * Creates a new instance of a composite type with the given custom type.
         */
        @NonRawRecipient
        public @Nonnull IterableType of(@Nonnull CustomType compositeType) {
            return new CompositeType(super.predicate, super.typeName, compositeType);
        }
        
        /* -------------------------------------------------- Composite Type -------------------------------------------------- */
        
        /**
         * Returns true, because the type is a composite type.
         */
        @Override
        public boolean isCompositeType() {
            return true;
        }
        
    }
    
    public static class CompositeType extends IterableType {
        
        /**
         * The composite type.
         */
        private final @Nonnull CustomType compositeType;
        
        /**
         * Returns the composite type.
         */
        public @Nonnull CustomType getCompositeType() {
            return compositeType;
        }
    
        private CompositeType(@Nonnull Predicate<@Nonnull FieldTypeSignature> predicate, @Nonnull String typeName, @Nonnull CustomType compositeType) {
            super(predicate, typeName);
            this.compositeType = compositeType;
        }
        
    }
    
    /* -------------------------------------------------- Map Type Subclass -------------------------------------------------- */
    
    /**
     * A custom type that maps a type to another type.
     */
    public static class MapType extends CustomType {
        
        /**
         * Creates a custom type.
         */
        private MapType(@Nonnull Predicate<@Nonnull FieldTypeSignature> predicate, @Nonnull String typeName) {
            super(predicate, typeName);
        }
        
        /**
         * Creates a new instance of a composite type with the given custom type.
         */
        @NonRawRecipient
        public @Nonnull MapType of(@Nonnull CustomType keyType, @Nonnull CustomType valueType) {
            return new KeyValueType(super.predicate, super.typeName, keyType, valueType);
        }
        
        /* -------------------------------------------------- Composite Type -------------------------------------------------- */
        
        /**
         * Returns true, because the type is a map type.
         */
        @Override
        public boolean isMapType() {
            return true;
        }
        
    }
    
    public static class KeyValueType extends MapType {
        
        /**
         * The key type.
         */
        private final @Nonnull CustomType keyType;
        
        /**
         * The value type.
         */
        private final @Nonnull CustomType valueType;
        
        /**
         * Returns the key type.
         */
        public @Nonnull CustomType getKeyType() {
            return keyType;
        }
        
        /**
         * Returns the value type.
         */
        public @Nonnull CustomType getValueType() {
            return valueType;
        }
        
        private KeyValueType(@Nonnull Predicate<@Nonnull FieldTypeSignature> predicate, @Nonnull String typeName, @Nonnull CustomType keyType, @Nonnull CustomType valueType) {
            super(predicate, typeName);
            this.keyType = keyType;
            this.valueType = valueType;
        }
        
    }
    
    /* -------------------------------------------------- TupleType Subclass -------------------------------------------------- */
    
    /**
     * A custom type that is non-primitive.
     */
    public static class TupleType extends CustomType {
        
        /**
         * Creates a tuple type.
         */
        private TupleType(@Nonnull Predicate<@Nonnull FieldTypeSignature> predicate, @Nonnull String typeName) {
            super(predicate, typeName);
        }
        
        /**
         * Creates a new instance of a custom converter type with the given converter.
         */
        @NonRawRecipient
        public @Nonnull CustomConverterType of(@Nonnull Converter<?, ?> converter) {
            return new CustomConverterType(super.predicate, super.typeName, converter);
        }
        
        /**
         * Returns true, because the type is an object type.
         */
        @Override
        public boolean isObjectType() {
            return true;
        }
        
    }
    
    public static class CustomConverterType extends TupleType {
        
        /**
         * The object converter.
         */
        private final @Nonnull Converter<?, ?> converter;
        
        /**
         * Returns the object converter.
         */
        public @Nonnull Converter<?, ?> getConverter() {
            return converter;
        }
        
        /**
         * Creates a new custom converter type.
         */
        private CustomConverterType(@Nonnull Predicate<@Nonnull FieldTypeSignature> predicate, @Nonnull String typeName, @Nonnull Converter<?, ?> converter) {
            super(predicate, typeName);
            this.converter = converter;
        }
    }
    
    /* -------------------------------------------------- Static Instances -------------------------------------------------- */
    
    public static final CustomType BOOLEAN = new CustomType(fieldTypeSignature -> fieldTypeSignature.isAssignable(boolean.class), "BOOLEAN");
    
    public static final CustomType INTEGER08 = new CustomType(fieldTypeSignature -> fieldTypeSignature.isAssignable(byte.class), "INTEGER08");
    
    public static final CustomType INTEGER16 = new CustomType(fieldTypeSignature -> fieldTypeSignature.isAssignable(short.class), "INTEGER16");
    
    public static final CustomType INTEGER32 = new CustomType(fieldTypeSignature -> fieldTypeSignature.isAssignable(int.class), "INTEGER32");
    
    public static final CustomType INTEGER64 = new CustomType(fieldTypeSignature -> fieldTypeSignature.isAssignable(long.class), "INTEGER64");
    
    public static final CustomType INTEGER = new CustomType(fieldTypeSignature -> fieldTypeSignature.isAssignable(BigInteger.class), "INTEGER");
    
    public static final CustomType DECIMAL32 = new CustomType(fieldTypeSignature -> fieldTypeSignature.isAssignable(float.class), "DECIMAL32");
    
    public static final CustomType DECIMAL64 = new CustomType(fieldTypeSignature -> fieldTypeSignature.isAssignable(double.class), "DECIMAL64");
    
    public static final CustomType STRING1 = new CustomType(fieldTypeSignature -> fieldTypeSignature.isAssignable(char.class), "STRING01");
    
    public static final CustomType STRING64 = new CustomType(fieldTypeSignature -> fieldTypeSignature.isAssignable(String.class) && fieldTypeSignature.hasMaxSize(64), "STRING64");
    
    public static final CustomType STRING128 = new CustomType(fieldTypeSignature -> fieldTypeSignature.isAssignable(String.class) && fieldTypeSignature.hasMaxSize(128), "STRING128");
    
    public static final CustomType STRING = new CustomType(fieldTypeSignature -> fieldTypeSignature.isAssignable(String.class) && !fieldTypeSignature.hasMaxSize(128), "STRING");
    
    public static final CustomType BINARY128 = new CustomType(fieldTypeSignature -> fieldTypeSignature.isAssignable(byte[].class) && fieldTypeSignature.hasMaxSize(128), "BINARY128");
    
    public static final CustomType BINARY256 = new CustomType(fieldTypeSignature -> fieldTypeSignature.isAssignable(byte[].class) && fieldTypeSignature.hasMaxSize(256), "BINARY256");
    
    public static final CustomType BINARY = new CustomType(fieldTypeSignature -> fieldTypeSignature.isAssignable(byte[].class) && !fieldTypeSignature.hasMaxSize(256), "BINARY");
    
    public static final CustomType BINARYSTREAM = new CustomType(fieldTypeSignature -> fieldTypeSignature.isAssignable(InputStream.class), "BINARYSTREAM");
    
    public static final IterableType SET = new IterableType(fieldTypeSignature -> ProcessingUtility.isRawSubtype(fieldTypeSignature.getTypeMirror(), Set.class), "SET");
    
    public static final IterableType LIST = new IterableType(fieldTypeSignature -> ProcessingUtility.isRawSubtype(fieldTypeSignature.getTypeMirror(), List.class) || ProcessingUtility.isRawSubtype(fieldTypeSignature.getTypeMirror(), FiniteIterable.class), "LIST");
    
    public static final IterableType ARRAY = new IterableType(fieldTypeSignature -> fieldTypeSignature.getTypeMirror().getKind() == TypeKind.ARRAY, "ARRAY");
    
    public static final MapType MAP = new MapType(fieldTypeSignature -> ProcessingUtility.isRawSubtype(fieldTypeSignature.getTypeMirror(), Map.class), "MAP");
    
    public static final TupleType TUPLE = new TupleType(fieldTypeSignature -> ProcessingUtility.isRawSubtype(fieldTypeSignature.getTypeMirror(), Tuple.class), "TUPLE");
    
    /**
     * A list of custom types that are statically defined in this class.
     */
    private static final @Nonnull FiniteIterable<@Nonnull CustomType> customTypes = FiniteIterable.of(BOOLEAN, INTEGER08, INTEGER16, INTEGER32, INTEGER64, INTEGER, DECIMAL32, DECIMAL64, STRING1, STRING64, STRING128, STRING, BINARY128, BINARY256, BINARY, SET, LIST, ARRAY, MAP, TUPLE);
    
    /* -------------------------------------------------- Predicate -------------------------------------------------- */
    
    /**
     * Indicates whether the custom type is an appropriate representation for the given type mirror.
     * The predicate evaluates to true iff the type mirror fits the instance.
     */
    private final @Nonnull Predicate<@Nonnull FieldTypeSignature> predicate;
    
    /* -------------------------------------------------- Type Name -------------------------------------------------- */
    
    /**
     * The string representation of the custom type for code generation.
     */
    private final @Nonnull String typeName;
    
    /**
     * Returns the string representation of the custom type for code generation.
     */
    public @Nonnull String getTypeName() {
        return typeName;
    }
    
    /* -------------------------------------------------- Custom Type Checks -------------------------------------------------- */
    
    /**
     * Always returns false, unless the custom type is a {@link IterableType}.
     */
    public boolean isCompositeType() {
        return false;
    }
    
    /**
     * Always returns false, unless the custom type is a {@link TupleType}.
     */
    public boolean isObjectType() {
        return false;
    }
    
    /**
     * Always returns false, unless the custom type is a {@link MapType}.
     */
    public boolean isMapType() {
        return false;
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new custom type object and sets the predicate and the type name.
     */
    private CustomType(@Nonnull Predicate<@Nonnull FieldTypeSignature> predicate, @Nonnull String typeName) {
        this.predicate = predicate;
        this.typeName = typeName;
    }
    
    /**
     * Returns the custom type that fits the given type mirror by iterating through all custom types and returning the
     * one which predicate evaluates to true.
     * If no custom type is found, null may be returned.
     */
    private static @Nullable CustomType get(TypeMirror typeMirror, @Nonnull FiniteIterable<AnnotationMirror> annotations) {
        return customTypes.findFirst(customType -> customType.predicate.evaluate(FieldTypeSignature.of(typeMirror, annotations)));
    }
    
    /**
     * Returns the custom type that fits the given type mirror by iterating through all custom types and returning the
     * one which predicate evaluates to true.
     * If no custom type is found, a type is inferred: It is either a tuple (for classes and interfaces) or a string (for enums).
     */
    public static @Nonnull CustomType of(@Nonnull TypeMirror typeMirror, @Nonnull FiniteIterable<AnnotationMirror> annotations) {
        final @Nullable CustomType syntacticType = get(typeMirror, annotations);
        return syntacticType != null ? syntacticType : CustomType.TUPLE;
    }
    
    /* -------------------------------------------------- Equals -------------------------------------------------- */
    
    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof CustomType)) {
            return false;
        } else {
            CustomType otherType = (CustomType) object;
            return typeName.equals(otherType.typeName);
        }
    }
    
    @Override
    public int hashCode() {
        return typeName.hashCode();
    }
    
    /* -------------------------------------------------- Type Name -------------------------------------------------- */
    
    /**
     * Returns the imported name (if possible) of the converter of the given type.
     */
    @Pure
    public static @Nonnull String importConverterType(@Nonnull TypeMirror type, @Nonnull FiniteIterable<@Nonnull AnnotationMirror> annotations, @Nonnull TypeImporter typeImporter) {
        // The following if-block is a suboptimal fix for generic types and should be solved differently (e.g. by storing the built converters).
        if (type instanceof DeclaredType) {
            final @Nonnull DeclaredType declaredType = (DeclaredType) type;
            final @Nonnull List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
            if (!typeArguments.isEmpty()) {
                ProcessingLog.debugging("The type $ has the following type arguments:", type, typeArguments);
                final @Nonnull TypeMirror firstArgument = typeArguments.get(0);
                if (firstArgument.getKind() != TypeKind.WILDCARD) {
                    final @Nonnull String converterBuilder = typeImporter.importIfPossible(ProcessingUtility.getQualifiedName(type) + "ConverterBuilder");
                    final @Nonnull String typeArgumentConverter = importConverterType(firstArgument, FiniteIterable.of(), typeImporter);
                    return converterBuilder + ".withObjectConverter(" + typeArgumentConverter + ").build()";
                }
            }
        }
        
        final @Nullable CustomType customType = get(type, annotations);
        final @Nonnull String qualifiedName;
        if (customType == null || customType.isObjectType()) {
            qualifiedName = ProcessingUtility.getQualifiedName(type);
        } else {
            qualifiedName = "net.digitalid.utility.conversion.converters." + Strings.uppercaseFirstCharacter(customType.getTypeName().toLowerCase());
        }
        return typeImporter.importIfPossible(qualifiedName + "Converter") + ".INSTANCE";
    }
    
    /**
     * Returns the custom type for the given representing field.
     */
    public static String getTypeName(@Nonnull TypeMirror representingFieldType, @Nonnull FiniteIterable<@Nonnull AnnotationMirror> annotations, @Nonnull TypeImporter typeImporter) {
        @Nonnull CustomType customType = CustomType.of(representingFieldType, annotations);
        // TODO: ProcessingUtility.getTypeElement(representingFieldType) is null for generic types but checking this only here leads to new problems (namely "The name 'TConverter' has to be qualified.").
        if (customType == CustomType.TUPLE && ProcessingUtility.getTypeElement(representingFieldType).getKind() == ElementKind.ENUM) {
            return typeImporter.importStaticallyIfPossible(CustomType.class.getCanonicalName() + "." + customType.getTypeName()) + ".of" + Brackets.inRound(typeImporter.importIfPossible("net.digitalid.utility.conversion.converters.StringConverter") + ".INSTANCE");
        } else if (customType == CustomType.SET || customType == CustomType.LIST || customType == CustomType.ARRAY) {
            final @Nonnull TypeMirror componentType = ProcessingUtility.getComponentType(representingFieldType);
            return typeImporter.importStaticallyIfPossible(CustomType.class.getCanonicalName() + "." + customType.getTypeName()) + ".of" + Brackets.inRound(getTypeName(componentType, FiniteIterable.of(), typeImporter));
        } else if (customType == CustomType.MAP) {
            final @Nullable List<TypeMirror> componentTypes = ProcessingUtility.getComponentTypes(representingFieldType);
            Require.that(componentTypes.size() == 2).orThrow("Map type does not have 2 component types.");
            return typeImporter.importStaticallyIfPossible(CustomType.class.getCanonicalName() + "." + customType.getTypeName()) + ".of" + Brackets.inRound(getTypeName(componentTypes.get(0), FiniteIterable.of(), typeImporter) + ", " + getTypeName(componentTypes.get(1), FiniteIterable.of(), typeImporter));
        } else if (representingFieldType.getKind().isPrimitive() || customType == BINARY || customType == BINARY128 || customType == BINARY256) {
            return typeImporter.importStaticallyIfPossible(CustomType.class.getCanonicalName() + "." + customType.getTypeName());
        } else {
            @Nonnull String typeName = customType.getTypeName();
            @Nonnull String qualifiedName = ProcessingUtility.getQualifiedName(representingFieldType);
            if (!qualifiedName.startsWith("net.digitalid")) {
                typeName = TUPLE.getTypeName();
            }
            return typeImporter.importStaticallyIfPossible(CustomType.class.getCanonicalName() + "." + typeName) + ".of" + Brackets.inRound(importConverterType(representingFieldType, annotations, typeImporter));
        }
    }
    
}
