package net.digitalid.utility.validation.validator.math;

import java.lang.annotation.Annotation;
import java.math.BigInteger;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.math.MultipleOf;
import net.digitalid.utility.validation.validator.Validator;
import net.digitalid.utility.validation.validator.exceptions.ValidationFailedException;

/**
 * Validates that a field value is a number and is a multiple of another number, which is specified in the field's {@link MultipleOf} annotation.
 */
public class MultipleOfValidator extends Validator<MultipleOf> {
    
    public final static @Nonnull Class<? extends Annotation> ANNOTATION = MultipleOf.class;
    
    private MultipleOfValidator() {
    }
    
    private void convertAndAssertModuloRemainderIsZero(Object object, long value) throws ValidationFailedException {
        if (Byte.class.isInstance(object) || Short.class.isInstance(object) || Integer.class.isInstance(object) || Long.class.isInstance(object)) {
            Long multipleOfValue = (Long) object;
            if (multipleOfValue % value != 0) {
                throw ValidationFailedException.get(multipleOfValue + " is not a multiple of " + value);
            }
        } else if (BigInteger.class.isInstance(object)) {
            BigInteger multipleOfValue = (BigInteger) object;
            if (multipleOfValue.mod(BigInteger.valueOf(value )) != BigInteger.ZERO) {
                throw ValidationFailedException.get(multipleOfValue + " is not a multiple of " + value);
            }
        }
    }

    /**
     * Checks whether the value of a given field is a multiple of a number specified in the annotation {@link MultipleOf}. 
     */
    @Override
    public void validate(@Nonnull Object fieldValue, @Nonnull MultipleOf multipleOf) throws ValidationFailedException {
        if (fieldValue == null) {
            throw ValidationFailedException.get("NULL is not a number.");
        }
        convertAndAssertModuloRemainderIsZero(fieldValue, multipleOf.value());
    }
    
    public static @Nonnull MultipleOfValidator get() {
        return new MultipleOfValidator();
    }
    
}
