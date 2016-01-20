package net.digitalid.utility.validation.validator.math;

import java.math.BigInteger;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.digitalid.utility.validation.math.MultipleOf;
import net.digitalid.utility.validation.validator.Validator;
import net.digitalid.utility.validation.validator.exceptions.ValidationFailedException;

/**
 * Validates that a field value is a number and is a multiple of another number, which is specified in the field's {@link MultipleOf} annotation.
 */
public class MultipleOfValidator extends Validator<MultipleOf> {
    
    /* -------------------------------------------------- Validation -------------------------------------------------- */
    
    /**
     * Casts the given object to the type of the field and asserts that the modulo of the field value with the value of the 
     * {@link MultipleOf @MultipleOf} annotation evaluates to 0. A validation-failed exception is throws if this is not the case.
     */
    private void convertAndAssertModuloRemainderIsZero(@Nonnull Object object, long value) throws ValidationFailedException {
        if (Long.class.isInstance(object)) {
            @Nonnull Long multipleOfValue = (Long) object;
            assertTrue(multipleOfValue % value == 0, multipleOfValue + " is not a multiple of " + value + ".");
        } else if (Byte.class.isInstance(object)) {
            @Nonnull Byte multipleOfValue = (Byte) object;
            assertTrue(multipleOfValue % value == 0, multipleOfValue + " is not a multiple of " + value + ".");
        } else if (Short.class.isInstance(object)) {
            @Nonnull Short multipleOfValue = (Short) object;
            assertTrue(multipleOfValue % value == 0, multipleOfValue + " is not a multiple of " + value + ".");
        } else if (Integer.class.isInstance(object)) {
            @Nonnull Integer multipleOfValue = (Integer) object;
            assertTrue(multipleOfValue % value == 0, multipleOfValue + " is not a multiple of " + value + ".");
        } else if (BigInteger.class.isInstance(object)) {
            @Nonnull BigInteger multipleOfValue = (BigInteger) object;
            assertTrue(multipleOfValue.mod(BigInteger.valueOf(value )).equals(BigInteger.ZERO), multipleOfValue + " is not a multiple of " + value + ".");
        } else {
            throw ValidationFailedException.get(object.getClass().getSimpleName() + " is not a supported type for @MultipleOf validation.");
        }
    }
    
    /**
     * Checks whether the value of a given field is a multiple of a number specified in the annotation {@link MultipleOf @MultipleOf}. 
     * Throws a validation-failed exception if the field value is not a multiple of the value specified in the {@link MultipleOf @MultipleOf} annotation.
     * Null values are ignored.
     */
    @Override
    public void validate(@Nullable Object fieldValue, @Nonnull MultipleOf multipleOf) throws ValidationFailedException {
        if (fieldValue == null) {
            return;
        }
        convertAndAssertModuloRemainderIsZero(fieldValue, multipleOf.value());
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Constructs the {@link MultipleOf @MultipleOf} validator.
     */
    private MultipleOfValidator() {}
    
    /**
     * Returns a {@link MultipleOf @MultipleOf} validator.
     */
    public static @Nonnull MultipleOfValidator get() {
        return new MultipleOfValidator();
    }
    
}
