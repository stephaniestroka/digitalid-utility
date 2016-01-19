package net.digitalid.utility.validation.validator.math;

import java.math.BigInteger;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.digitalid.utility.validation.math.Negative;
import net.digitalid.utility.validation.validator.Validator;
import net.digitalid.utility.validation.validator.exceptions.ValidationFailedException;

/**
 *
 */
public class NegativeValidator extends Validator<Negative> {
    
    @Override
    public void validate(@Nullable Object object, @Nonnull Negative annotation) throws ValidationFailedException {
        if (object == null) {
            return;
        }
        if (Long.class.isInstance(object)) {
            @Nonnull Long value = (Long) object;
            assertTrue(value < 0, value + " is not negative.");
        } else if (Byte.class.isInstance(object)) {
            @Nonnull Byte value = (Byte) object;
            assertTrue(value < 0, value + " is not negative.");
        } else if (Short.class.isInstance(object)) {
            @Nonnull Short value = (Short) object;
            assertTrue(value < 0, value + " is not negative.");
        } else if (Integer.class.isInstance(object)) {
            @Nonnull Integer value = (Integer) object;
            assertTrue(value < 0, value + " is not negative.");
        } else if (BigInteger.class.isInstance(object)) {
            @Nonnull BigInteger value = (BigInteger) object;
            assertTrue(value.compareTo(BigInteger.ZERO) < 0, value + " is not negative.");
        } else {
            throw ValidationFailedException.get(object.getClass().getSimpleName() + " is not a supported type.");
        }
    }
    
    public static @Nonnull NegativeValidator get() {
        return new NegativeValidator();
    }
    
}
