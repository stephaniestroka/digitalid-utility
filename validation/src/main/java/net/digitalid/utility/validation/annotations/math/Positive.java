package net.digitalid.utility.validation.annotations.math;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigInteger;

import net.digitalid.utility.validation.annotations.meta.TargetTypes;
import net.digitalid.utility.validation.annotations.meta.Validator;
import net.digitalid.utility.validation.validator.AnnotationValidator;

/**
 * This annotation indicates that a numeric value is positive.
 * 
 * @see NonPositive
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Validator(Positive.Validator.class)
@TargetTypes({long.class, int.class, short.class, byte.class, BigInteger.class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Positive {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    public static class Validator extends AnnotationValidator {
        // TODO: Generate the contract.
    }
    
//    /**
//     * Throws a validation-failed exception for {@link Positive @Positive} annotated fields if the field value is not positive or has an unsupported type.
//     * Null values are ignored.
//     */
//    @Override
//    public void validate(@Nullable Object object, @Nonnull Positive annotation) throws ValidationFailedException {
//        if (object == null) {
//            return;
//        }
//        if (Long.class.isInstance(object)) {
//            @Nonnull Long value = (Long) object;
//            assertTrue(value > 0, value + " is not positive.");
//        } else if (Byte.class.isInstance(object)) {
//            @Nonnull Byte value = (Byte) object;
//            assertTrue(value > 0, value + " is not positive.");
//        } else if (Short.class.isInstance(object)) {
//            @Nonnull Short value = (Short) object;
//            assertTrue(value > 0, value + " is not positive.");
//        } else if (Integer.class.isInstance(object)) {
//            @Nonnull Integer value = (Integer) object;
//            assertTrue(value > 0, value + " is not positive.");
//        } else if (BigInteger.class.isInstance(object)) {
//            @Nonnull BigInteger value = (BigInteger) object;
//            assertTrue(value.compareTo(BigInteger.ZERO) > 0, value + " is not positive.");
//        } else {
//            throw ValidationFailedException.get(object.getClass().getSimpleName() + " is not a supported type for @Positive validation.");
//        }
//    }
}
