package net.digitalid.utility.validation.validator.nonnull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.validation.validator.Validator;
import net.digitalid.utility.validation.validator.exceptions.ValidationFailedException;

/**
 * Validates that a given field value is not null.
 */
public class NonNullValidator extends Validator<Nonnull> {
    
    /* -------------------------------------------------- Validation -------------------------------------------------- */
    
    /**
     * Throws a validation-failed exception for {@link Nonnull @Nonnull} annotated fields if the field value is null.
     */
    @Override
    public void validate(@Nullable Object fieldValue, @Nonnull Nonnull annotation) throws ValidationFailedException {
        if (fieldValue == null) {
            throw ValidationFailedException.get("Non-null value expected for field annotated with @Nonnull.");
        }
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Constructs the non-null validator.
     */
    private NonNullValidator() {}
    
    /**
     * Returns a non-null validator.
     */
    public static @Nonnull NonNullValidator get() {
        return new NonNullValidator();
    }
    
}
