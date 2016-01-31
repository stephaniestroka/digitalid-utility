package net.digitalid.utility.validation.annotations.math;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigInteger;

import net.digitalid.utility.validation.annotations.meta.TargetTypes;
import net.digitalid.utility.validation.annotations.meta.ValidateWith;
import net.digitalid.utility.validation.validator.AnnotationValidator;

/**
 * This annotation indicates that a numeric value is a multiple of the given value.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@ValidateWith(MultipleOf.Validator.class)
@TargetTypes({long.class, int.class, short.class, byte.class, BigInteger.class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface MultipleOf {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the value of which the annotated numeric value is a multiple of.
     */
    long value();
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    public static class Validator extends AnnotationValidator {
        // TODO: Generate the contract.
    }
    
//    /**
//     * Casts the given object to the type of the field and asserts that the modulo of the field value with the value of the 
//     * {@link MultipleOf @MultipleOf} annotation evaluates to 0. A validation-failed exception is throws if this is not the case.
//     */
//    private void convertAndAssertModuloRemainderIsZero(@Nonnull Object object, long value) throws ValidationFailedException {
//        if (Long.class.isInstance(object)) {
//            @Nonnull Long multipleOfValue = (Long) object;
//            assertTrue(multipleOfValue % value == 0, multipleOfValue + " is not a multiple of " + value + ".");
//        } else if (Byte.class.isInstance(object)) {
//            @Nonnull Byte multipleOfValue = (Byte) object;
//            assertTrue(multipleOfValue % value == 0, multipleOfValue + " is not a multiple of " + value + ".");
//        } else if (Short.class.isInstance(object)) {
//            @Nonnull Short multipleOfValue = (Short) object;
//            assertTrue(multipleOfValue % value == 0, multipleOfValue + " is not a multiple of " + value + ".");
//        } else if (Integer.class.isInstance(object)) {
//            @Nonnull Integer multipleOfValue = (Integer) object;
//            assertTrue(multipleOfValue % value == 0, multipleOfValue + " is not a multiple of " + value + ".");
//        } else if (BigInteger.class.isInstance(object)) {
//            @Nonnull BigInteger multipleOfValue = (BigInteger) object;
//            assertTrue(multipleOfValue.mod(BigInteger.valueOf(value )).equals(BigInteger.ZERO), multipleOfValue + " is not a multiple of " + value + ".");
//        } else {
//            throw ValidationFailedException.get(object.getClass().getSimpleName() + " is not a supported type for @MultipleOf validation.");
//        }
//    }
//    
//    /**
//     * Checks whether the value of a given field is a multiple of a number specified in the annotation {@link MultipleOf @MultipleOf}. 
//     * Throws a validation-failed exception if the field value is not a multiple of the value specified in the {@link MultipleOf @MultipleOf} annotation.
//     * Null values are ignored.
//     */
//    @Override
//    public void validate(@Nullable Object fieldValue, @Nonnull MultipleOf multipleOf) throws ValidationFailedException {
//        if (fieldValue == null) {
//            return;
//        }
//        convertAndAssertModuloRemainderIsZero(fieldValue, multipleOf.value());
//    }
}
