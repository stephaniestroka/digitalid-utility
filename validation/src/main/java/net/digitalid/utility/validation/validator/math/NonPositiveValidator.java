package net.digitalid.utility.validation.validator.math;

import java.math.BigInteger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.validation.math.NonPositive;
import net.digitalid.utility.validation.validator.Validator;
import net.digitalid.utility.validation.validator.exceptions.ValidationFailedException;

/**
 * Validates the {@link NonPositive @NonPositive} annotation by checking that the field value is a non-positive number of type byte, short, int, long or BigInteger.
 */
public class NonPositiveValidator extends Validator<NonPositive> {
    
    /* -------------------------------------------------- Validation -------------------------------------------------- */
    
    /**
     * Throws a validation-failed exception for {@link NonPositive @NonPositive} annotated fields if the field value is not non-positive or has an unsupported type.
     * Null values are ignored.
     */
    @Override
    public void validate(@Nullable Object object, @Nonnull NonPositive annotation) throws ValidationFailedException {
        if (object == null) {
            return;
        }
        if (Long.class.isInstance(object)) {
            @Nonnull Long value = (Long) object;
            assertTrue(value <= 0, value + " is not non-positive.");
        } else if (Byte.class.isInstance(object)) {
            @Nonnull Byte value = (Byte) object;
            assertTrue(value <= 0, value + " is not non-positive.");
        } else if (Short.class.isInstance(object)) {
            @Nonnull Short value = (Short) object;
            assertTrue(value <= 0, value + " is not non-positive.");
        } else if (Integer.class.isInstance(object)) {
            @Nonnull Integer value = (Integer) object;
            assertTrue(value <= 0, value + " is not non-positive.");
        } else if (BigInteger.class.isInstance(object)) {
            @Nonnull BigInteger value = (BigInteger) object;
            assertTrue(value.compareTo(BigInteger.ZERO) <= 0, value + " is not non-positive.");
        } else {
            throw ValidationFailedException.get(object.getClass().getSimpleName() + " is not a supported type for @NonPositive validation.");
        }
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Constructs the {@link NonPositive @NonPositive} validator.
     */
    private NonPositiveValidator() {}
    
    /**
     * Returns a {@link NonPositive @NonPositive} validator.
     */
    public static @Nonnull
    NonPositiveValidator get() {
        return new NonPositiveValidator();
    }
    
}
