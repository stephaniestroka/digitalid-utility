package net.digitalid.utility.conversion.converter.types;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.functional.interfaces.Predicate;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.tuples.Tuple;
import net.digitalid.utility.validation.annotations.size.MaxSize;

import com.sun.tools.javac.code.Type;

/**
 *
 */
public enum CustomType {
    
    BOOLEAN(typeMirror -> typeMirror.toString().equals(boolean.class.getCanonicalName()) || typeMirror.toString().equals(Boolean.class.getCanonicalName()), BooleanConverter.class.getName()),
    
    INTEGER08(typeMirror -> typeMirror.toString().equals(byte.class.getCanonicalName()) || typeMirror.toString().equals(Byte.class.getCanonicalName()), Integer08Converter.class.getName()),
    
    INTEGER16(typeMirror -> typeMirror.toString().equals(short.class.getCanonicalName()) || typeMirror.toString().equals(Short.class.getCanonicalName()), Integer16Converter.class.getName()),
    
    INTEGER32(typeMirror -> typeMirror.toString().equals(int.class.getCanonicalName()) || typeMirror.toString().equals(Integer.class.getCanonicalName()), Integer32Converter.class.getName()),
    
    INTEGER64(typeMirror -> typeMirror.toString().equals(long.class.getCanonicalName()) || typeMirror.toString().equals(Long.class.getCanonicalName()), Integer64Converter.class.getName()),
    
    INTEGER(typeMirror -> typeMirror.toString().equals(BigInteger.class.getCanonicalName()), IntegerConverter.class.getName()),
    
    DECIMAL32(typeMirror -> typeMirror.toString().equals(float.class.getCanonicalName()) || typeMirror.toString().equals(Float.class.getCanonicalName()), Decimal32Converter.class.getName()),
    
    DECIMAL64(typeMirror -> typeMirror.toString().equals(double.class.getCanonicalName()) || typeMirror.toString().equals(Double.class.getCanonicalName()), Decimal64Converter.class.getName()),
    
    STRING01(typeMirror -> typeMirror.toString().equals(char.class.getCanonicalName()) || typeMirror.toString().equals(Character.class.getCanonicalName()), String01Converter.class.getName()),
    
    STRING64(typeMirror -> typeMirror.toString().equals(String.class.getCanonicalName()) && typeMirror.getAnnotation(MaxSize.class) != null && typeMirror.getAnnotation(MaxSize.class).value() <= 64, String64Converter.class.getName()),
    
    STRING(typeMirror -> typeMirror.toString().equals(String.class.getCanonicalName()) && (typeMirror.getAnnotation(MaxSize.class) == null || typeMirror.getAnnotation(MaxSize.class).value() > 64), StringConverter.class.getName()),
    
    BINARY128(typeMirror -> (typeMirror.toString().equals(byte[].class.getCanonicalName()) || typeMirror.toString().equals(Byte[].class.getCanonicalName())) && typeMirror.getAnnotation(MaxSize.class) != null && typeMirror.getAnnotation(MaxSize.class).value() <= 128, Binary128Converter.class.getName()),
    
    BINARY256(typeMirror -> (typeMirror.toString().equals(byte[].class.getCanonicalName()) || typeMirror.toString().equals(Byte[].class.getCanonicalName())) && typeMirror.getAnnotation(MaxSize.class) != null && typeMirror.getAnnotation(MaxSize.class).value() <= 256, Binary256Converter.class.getName()),
    
    BINARY(typeMirror -> (typeMirror.toString().equals(byte[].class.getCanonicalName()) || typeMirror.toString().equals(Byte[].class.getCanonicalName())) && (typeMirror.getAnnotation(MaxSize.class) == null || typeMirror.getAnnotation(MaxSize.class).value() > 256), BinaryConverter.class.getName()),
    
    // TODO: Consider ReadOnlyList and co.
    LIST(typeMirror -> ProcessingUtility.isRawSubtype(typeMirror, List.class), ListConverter.class.getName()),
    
    TUPLE(typeMirror -> ProcessingUtility.isRawSubtype(typeMirror, Tuple.class), TupleConverter.class.getName());
    
    private final @Nonnull Predicate<TypeMirror> predicate;
    
    public final @Nonnull String converterName;
    
    CustomType(@Nonnull Predicate<TypeMirror> predicate, @Nonnull String converterName) {
        this.predicate = predicate;
        this.converterName = converterName;
    }
    
    private static @Nullable CustomType get(TypeMirror typeMirror) {
        return FiniteIterable.of(values()).findFirst(customType -> customType.predicate.evaluate(typeMirror));
    }
        
    public static CustomType of(@Nonnull TypeMirror typeMirror) {
        if (typeMirror instanceof Type.ArrayType) {
            final @Nonnull TypeMirror unannotatedComponentType;
            final int maxsize;
            final @Nonnull TypeMirror componentType = ((Type.ArrayType) typeMirror).getComponentType();
            if (componentType instanceof Type.AnnotatedType) {
                unannotatedComponentType = ((Type.AnnotatedType) componentType).unannotatedType();
                final Type.AnnotatedType annotatedType = (Type.AnnotatedType) componentType;
                if (annotatedType.getAnnotation(MaxSize.class) != null) {
                    maxsize = annotatedType.getAnnotation(MaxSize.class).value();
                } else {
                    maxsize = -1;
                }
            } else {
                unannotatedComponentType = componentType;
                maxsize = -1;
            }
            if (unannotatedComponentType.toString().equals(Byte.class.getCanonicalName())) {
                if (maxsize >= 0) {
                    if (maxsize <= 128) {
                        return BINARY128;
                    } else if (maxsize <= 256) {
                        return BINARY256;
                    }
                }
                return BINARY;
            } else {
                return LIST;
            }
        } else {
            final @Nonnull TypeMirror unannotatedType;
            if (typeMirror instanceof Type.AnnotatedType) {
                unannotatedType = ((Type.AnnotatedType) typeMirror).unannotatedType();
            } else {
                unannotatedType = typeMirror;
            }
            final @Nullable CustomType syntacticType = get(unannotatedType);
            return syntacticType != null ? syntacticType : CustomType.TUPLE;
        }
    }
    
}
