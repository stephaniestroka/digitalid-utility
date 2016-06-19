package net.digitalid.utility.conversion.converter.types;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.conversion.converter.Converter;
import net.digitalid.utility.functional.interfaces.Predicate;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.tuples.Tuple;
import net.digitalid.utility.validation.annotations.size.MaxSize;

/**
 * Instances of the custom type represent the types used in Digital ID.
 * A custom type can either be a primitive type, a composite type or an object type.
 */
public class CustomType {
    
    /* -------------------------------------------------- Composite Type Subclass -------------------------------------------------- */
    
    /**
     * A custom type that holds a composite type.
     */
    public static class CustomCompositeType extends CustomType {
    
        /**
         * Creates a custom type.
         */
        private CustomCompositeType(@Nonnull Predicate<TypeMirror> predicate, @Nonnull String typeName) {
            super(predicate, typeName);
        }
    
        /**
         * The composite type.
         */
        private @Nullable CustomType compositeType;
    
        /**
         * Returns the composite type.
         */
        public @Nonnull CustomType getCompositeType() {
            Require.that(compositeType != null).orThrow("Expected non-null composite type for $", getTypeName());
            return compositeType;
        }
    
        /**
         * Sets the composite type.
         */
        public @Nonnull CustomCompositeType of(@Nonnull CustomType compositeType) {
            this.compositeType = compositeType;
            return this;
        }
    
        /**
         * Returns true, because the type is a composite type.
         */
        @Override
        public boolean isCompositeType() {
            return true;
        }
        
    }
    
    /* -------------------------------------------------- Object Type Subclass -------------------------------------------------- */
    
    /**
     * A custom type that is non-primitive.
     */
    public static class CustomObjectType extends CustomType {
    
        /**
         * Creates a custom type.
         */
        private CustomObjectType(@Nonnull Predicate<TypeMirror> predicate, @Nonnull String typeName) {
            super(predicate, typeName);
        }
    
        /**
         * The object converter.
         */
        private @Nullable Converter<?> converter;
    
        /**
         * Returns the object converter.
         */
        public @Nonnull Converter<?> getConverter() {
            Require.that(converter != null).orThrow("Expected non-null converter in object type $", getTypeName());
            
            return converter;
        }
    
        /**
         * Sets the converter for the non-primitive custom type.
         */
        public CustomObjectType of(@Nonnull Converter<?> converter) {
            this.converter = converter;
            return this;
        }
    
        /**
         * Returns true, because the type is an object type.
         */
        @Override
        public boolean isObjectType() {
            return true;
        }
        
    }
    
    /* -------------------------------------------------- Static Instances -------------------------------------------------- */
    
    public static final CustomType BOOLEAN = new CustomType(typeMirror -> ProcessingUtility.isRawlyAssignable(typeMirror, boolean.class) || ProcessingUtility.isRawlyAssignable(typeMirror, Boolean.class), "BOOLEAN");
    
    public static final CustomType INTEGER08 = new CustomType(typeMirror -> ProcessingUtility.isRawlyAssignable(typeMirror, byte.class) || ProcessingUtility.isRawlyAssignable(typeMirror, Byte.class), "INTEGER08");
    
    public static final CustomType INTEGER16 = new CustomType(typeMirror -> ProcessingUtility.isRawlyAssignable(typeMirror, short.class) || ProcessingUtility.isRawlyAssignable(typeMirror, Short.class), "INTEGER16");
    
    public static final CustomType INTEGER32 = new CustomType(typeMirror -> ProcessingUtility.isRawlyAssignable(typeMirror, int.class) || ProcessingUtility.isRawlyAssignable(typeMirror, Integer.class), "INTEGER32");
    
    public static final CustomType INTEGER64 = new CustomType(typeMirror -> ProcessingUtility.isRawlyAssignable(typeMirror, long.class) || ProcessingUtility.isRawlyAssignable(typeMirror, Long.class), "INTEGER64");
    
    public static final CustomType INTEGER = new CustomType(typeMirror -> ProcessingUtility.isRawlyAssignable(typeMirror, BigInteger.class), "INTEGER");
    
    public static final CustomType DECIMAL32 = new CustomType(typeMirror -> ProcessingUtility.isRawlyAssignable(typeMirror, float.class) || ProcessingUtility.isRawlyAssignable(typeMirror, Float.class), "DECIMAL32");
    
    public static final CustomType DECIMAL64 = new CustomType(typeMirror -> ProcessingUtility.isRawlyAssignable(typeMirror, double.class) || ProcessingUtility.isRawlyAssignable(typeMirror, Double.class), "DECIMAL64");
    
    public static final CustomType STRING01 = new CustomType(typeMirror -> ProcessingUtility.isRawlyAssignable(typeMirror, char.class) || ProcessingUtility.isRawlyAssignable(typeMirror, Character.class), "STRING01");
    
    public static final CustomType STRING64 = new CustomType(typeMirror -> ProcessingUtility.isRawlyAssignable(typeMirror, String.class) && typeMirror.getAnnotation(MaxSize.class) != null && typeMirror.getAnnotation(MaxSize.class).value() <= 64, "STRING64");
    
    public static final CustomType STRING = new CustomType(typeMirror -> ProcessingUtility.isRawlyAssignable(typeMirror, String.class) && (typeMirror.getAnnotation(MaxSize.class) == null || typeMirror.getAnnotation(MaxSize.class).value() > 64), "STRING");
    
    public static final CustomType BINARY128 = new CustomType(typeMirror -> (ProcessingUtility.isRawlyAssignable(typeMirror, byte[].class) || ProcessingUtility.isRawlyAssignable(typeMirror, Byte[].class)) && typeMirror.getAnnotation(MaxSize.class) != null && typeMirror.getAnnotation(MaxSize.class).value() <= 128, "BINARY128");
    
    public static final CustomType BINARY256 = new CustomType(typeMirror -> (ProcessingUtility.isRawlyAssignable(typeMirror, byte[].class) || ProcessingUtility.isRawlyAssignable(typeMirror, Byte[].class)) && typeMirror.getAnnotation(MaxSize.class) != null && typeMirror.getAnnotation(MaxSize.class).value() <= 256, "BINARY256");
    
    public static final CustomType BINARY = new CustomType(typeMirror -> (ProcessingUtility.isRawlyAssignable(typeMirror, byte[].class) || ProcessingUtility.isRawlyAssignable(typeMirror, Byte[].class)) && (typeMirror.getAnnotation(MaxSize.class) == null || typeMirror.getAnnotation(MaxSize.class).value() > 256), "BINARY");
    
    public static final CustomCompositeType SET = new CustomCompositeType(typeMirror -> ProcessingUtility.isRawSubtype(typeMirror, Set.class), "SET");
    
    // TODO: Consider ReadOnlyList and co.
    public static final CustomCompositeType LIST = new CustomCompositeType(typeMirror -> ProcessingUtility.isRawSubtype(typeMirror, List.class), "LIST");
    
    public static final CustomCompositeType ARRAY = new CustomCompositeType(typeMirror -> typeMirror.getKind() == TypeKind.ARRAY, "ARRAY");
    
    public static final CustomObjectType TUPLE = new CustomObjectType(typeMirror -> ProcessingUtility.isRawSubtype(typeMirror, Tuple.class), "TUPLE");
    
    /**
     * A list of custom types that are statically defined in this class.
     */
    private static final CustomType[] customTypes = {BOOLEAN, INTEGER08, INTEGER16, INTEGER32, INTEGER64, INTEGER, DECIMAL32, DECIMAL64, STRING01, STRING64, STRING, BINARY128, BINARY256, BINARY, SET, LIST, ARRAY, TUPLE};
    
    /* -------------------------------------------------- Predicate -------------------------------------------------- */
    
    /**
     * Indicates whether the custom type is an appropriate representation for the given type mirror.
     * The predicate evaluates to true iff the type mirror fits the instance.
     */
    private final @Nonnull Predicate<TypeMirror> predicate;
    
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
     * Always returns false, unless the custom type is a {@link CustomCompositeType}.
     */
    public boolean isCompositeType() {
        return false;
    }
    
    /**
     * Always returns false, unless the custom type is a {@link CustomObjectType}.
     */
    public boolean isObjectType() {
        return false;
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new custom type object and sets the predicate and the type name.
     */
    private CustomType(@Nonnull Predicate<TypeMirror> predicate, @Nonnull String typeName) {
        this.predicate = predicate;
        this.typeName = typeName;
    }
    
    /**
     * Returns the custom type that fits the given type mirror by iterating through all custom types and returning the
     * one which predicate evaluates to true.
     * If no custom type is found, null may be returned.
     */
    private static @Nullable CustomType get(TypeMirror typeMirror) {
        return FiniteIterable.of(customTypes).findFirst(customType -> customType.predicate.evaluate(typeMirror));
    }
    
    /**
     * Returns the custom type that fits the given type mirror by iterating through all custom types and returning the
     * one which predicate evaluates to true.
     * If no custom type is found, tuple will be returned.
     */
    public static @Nonnull CustomType of(@Nonnull TypeMirror typeMirror) {
        final @Nullable CustomType syntacticType = get(typeMirror);
        return syntacticType != null ? syntacticType : CustomType.TUPLE;
    }
    
}
