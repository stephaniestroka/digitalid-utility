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
    
    private void verifyNoRemainder(Number remainder, Number multipleOfValue, long value) throws ValidationFailedException {
        if (remainder.longValue() != 0) {
            throw ValidationFailedException.get(multipleOfValue + " is not a multiple of " + value + ".");
        }
    }
    
    private void convertAndAssertModuloRemainderIsZero(@Nonnull Object object, long value) throws ValidationFailedException {
        if (Long.class.isInstance(object)) {
            @Nonnull Long multipleOfValue = (Long) object;
            verifyNoRemainder(multipleOfValue % value, multipleOfValue, value); 
        } else if (Byte.class.isInstance(object)) {
            @Nonnull Byte multipleOfValue = (Byte) object;
            verifyNoRemainder(multipleOfValue % value, multipleOfValue, value); 
        } else if (Short.class.isInstance(object)) {
            @Nonnull Short multipleOfValue = (Short) object;
            verifyNoRemainder(multipleOfValue % value, multipleOfValue, value); 
        } else if (Integer.class.isInstance(object)) {
            @Nonnull Integer multipleOfValue = (Integer) object;
            verifyNoRemainder(multipleOfValue % value, multipleOfValue, value); 
        } else if (BigInteger.class.isInstance(object)) {
            @Nonnull BigInteger multipleOfValue = (BigInteger) object;
            if (multipleOfValue.mod(BigInteger.valueOf(value )) != BigInteger.ZERO) {
                throw ValidationFailedException.get(multipleOfValue + " is not a multiple of " + value + ".");
            }
        } else {
            throw ValidationFailedException.get(object.getClass().getSimpleName() + " is not a supported type.");
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
