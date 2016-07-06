package net.digitalid.utility.conversion.exceptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.collaboration.annotations.TODO;
import net.digitalid.utility.collaboration.enumerations.Author;
import net.digitalid.utility.exceptions.InternalException;
import net.digitalid.utility.validation.annotations.elements.NullableElements;

@TODO(task = "Why is this an internal exception?", date = "2016-06-24", author = Author.KASPAR_ETTER, assignee = Author.STEPHANIE_STROKA)
public class FailedValueConversionException extends InternalException {
    
    protected FailedValueConversionException(@Nullable String message, @Nullable Exception cause, @Captured @Nonnull @NullableElements Object... arguments) {
        super(message, cause, arguments);
    }
    
}
