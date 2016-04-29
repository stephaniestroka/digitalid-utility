package net.digitalid.utility.conversion.converter;

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
    
    BOOLEAN(typeMirror -> typeMirror.toString().equals(boolean.class.getCanonicalName()) || typeMirror.toString().equals(Boolean.class.getCanonicalName())),
    
    INTEGER08(typeMirror -> typeMirror.toString().equals(byte.class.getCanonicalName()) || typeMirror.toString().equals(Byte.class.getCanonicalName())),
    
    INTEGER16(typeMirror -> typeMirror.toString().equals(short.class.getCanonicalName()) || typeMirror.toString().equals(Short.class.getCanonicalName())),
    
    INTEGER32(typeMirror -> typeMirror.toString().equals(int.class.getCanonicalName()) || typeMirror.toString().equals(Integer.class.getCanonicalName())),
    
    INTEGER64(typeMirror -> typeMirror.toString().equals(long.class.getCanonicalName()) || typeMirror.toString().equals(Long.class.getCanonicalName())),
    
    INTEGER(typeMirror -> typeMirror.toString().equals(BigInteger.class.getCanonicalName())),
    
    DECIMAL32(typeMirror -> typeMirror.toString().equals(float.class.getCanonicalName()) || typeMirror.toString().equals(Float.class.getCanonicalName())),
    
    DECIMAL64(typeMirror -> typeMirror.toString().equals(double.class.getCanonicalName()) || typeMirror.toString().equals(Double.class.getCanonicalName())),
    
    STRING01(typeMirror -> typeMirror.toString().equals(char.class.getCanonicalName()) || typeMirror.toString().equals(Character.class.getCanonicalName())),
    
    STRING64(typeMirror -> typeMirror.toString().equals(String.class.getCanonicalName()) && typeMirror.getAnnotation(MaxSize.class) != null && typeMirror.getAnnotation(MaxSize.class).value() <= 64),
    
    STRING(typeMirror -> typeMirror.toString().equals(String.class.getCanonicalName()) && (typeMirror.getAnnotation(MaxSize.class) == null || typeMirror.getAnnotation(MaxSize.class).value() > 64)),
    
    BINARY128(typeMirror -> (typeMirror.toString().equals(byte[].class.getCanonicalName()) || typeMirror.toString().equals(Byte[].class.getCanonicalName())) && typeMirror.getAnnotation(MaxSize.class) != null && typeMirror.getAnnotation(MaxSize.class).value() <= 128),
    
    BINARY256(typeMirror -> (typeMirror.toString().equals(byte[].class.getCanonicalName()) || typeMirror.toString().equals(Byte[].class.getCanonicalName())) && typeMirror.getAnnotation(MaxSize.class) != null && typeMirror.getAnnotation(MaxSize.class).value() <= 256),
    
    BINARY(typeMirror -> (typeMirror.toString().equals(byte[].class.getCanonicalName()) || typeMirror.toString().equals(Byte[].class.getCanonicalName())) && (typeMirror.getAnnotation(MaxSize.class) == null || typeMirror.getAnnotation(MaxSize.class).value() > 256)),
    
    // TODO: Consider ReadOnlyList and co.
    LIST(typeMirror -> ProcessingUtility.isAssignable(typeMirror, List.class)),
    
    TUPLE(typeMirror -> ProcessingUtility.isAssignable(typeMirror, Tuple.class));
    
    private final @Nonnull Predicate<TypeMirror> predicate;
    
    CustomType(@Nonnull Predicate<TypeMirror> predicate) {
        this.predicate = predicate;
    }
    
    private static @Nullable CustomType get(TypeMirror typeMirror) {
        return FiniteIterable.of(CustomType.values()).findFirst(customType -> customType.predicate.evaluate(typeMirror));
    }
    
    public static CustomType of(@Nonnull TypeMirror typeMirror) {
        if (typeMirror instanceof Type.ArrayType) {
            final @Nonnull TypeMirror unannotatedComponentType;
            final int maxsize;
            final @Nonnull TypeMirror componentType = ((Type.ArrayType) typeMirror).getComponentType();
            if (componentType instanceof Type.AnnotatedType) {
                unannotatedComponentType = ((Type.AnnotatedType) componentType).unannotatedType();
                final Type.AnnotatedType annotatedType = (Type.AnnotatedType) componentType;
                maxsize = annotatedType.getAnnotation(MaxSize.class).value();
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
            return get(unannotatedType);
        }
    }
    
}
