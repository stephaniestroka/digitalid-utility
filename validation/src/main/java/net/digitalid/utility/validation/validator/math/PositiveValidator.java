package net.digitalid.utility.validation.validator.math;

import java.math.BigInteger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.validation.math.Positive;
import net.digitalid.utility.validation.validator.Validator;
import net.digitalid.utility.validation.validator.exceptions.ValidationFailedException;

/**
 * Validates the {@link Positive @Positive} annotation by checking that the field value is a positive number of type byte, short, int, long or BigInteger.
 */
public class PositiveValidator extends Validator<Positive> {
    
    /* -------------------------------------------------- Validation -------------------------------------------------- */
    
    /**
     * Throws a validation-failed exception for {@link Positive @Positive} annotated fields if the field value is not positive or has an unsupported type.
     * Null values are ignored.
     */
    @Override
    public void validate(@Nullable Object object, @Nonnull Positive annotation) throws ValidationFailedException {
        if (object == null) {
            return;
        }
        if (Long.class.isInstance(object)) {
            @Nonnull Long value = (Long) object;
            assertTrue(value > 0, value + " is not positive.");
        } else if (Byte.class.isInstance(object)) {
            @Nonnull Byte value = (Byte) object;
            assertTrue(value > 0, value + " is not positive.");
        } else if (Short.class.isInstance(object)) {
            @Nonnull Short value = (Short) object;
            assertTrue(value > 0, value + " is not positive.");
        } else if (Integer.class.isInstance(object)) {
            @Nonnull Integer value = (Integer) object;
            assertTrue(value > 0, value + " is not positive.");
        } else if (BigInteger.class.isInstance(object)) {
            @Nonnull BigInteger value = (BigInteger) object;
            assertTrue(value.compareTo(BigInteger.ZERO) > 0, value + " is not positive.");
        } else {
            throw ValidationFailedException.get(object.getClass().getSimpleName() + " is not a supported type for @Positive validation.");
        }
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Constructs the {@link Positive @Positive} validator.
     */
    private PositiveValidator() {}
    
    /**
     * Returns a {@link Positive @Positive} validator.
     */
    public static @Nonnull PositiveValidator get() {
        return new PositiveValidator();
    }
    
}
