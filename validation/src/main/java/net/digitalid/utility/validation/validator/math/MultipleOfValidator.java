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
    
    private MultipleOfValidator() {
    }
    
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
     * Checks whether the value of a given field is a multiple of a number specified in the annotation {@link MultipleOf}. 
     */
    @Override
    public void validate(@Nullable Object fieldValue, @Nonnull MultipleOf multipleOf) throws ValidationFailedException {
        if (fieldValue == null) {
            return;
        }
        convertAndAssertModuloRemainderIsZero(fieldValue, multipleOf.value());
    }
    
    public static @Nonnull MultipleOfValidator get() {
        return new MultipleOfValidator();
    }
    
}
