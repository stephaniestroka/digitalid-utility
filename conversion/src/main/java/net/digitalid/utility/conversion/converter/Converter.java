package net.digitalid.utility.conversion.converter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.immutable.ImmutableList;
import net.digitalid.utility.logging.exceptions.ExternalException;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.string.CodeIdentifier;

public interface Converter<T, E> {
    
    @Pure
    public @Nonnull @CodeIdentifier @MaxSize(63) String getName();
    
    @Pure
    public @Nonnull ImmutableList<@Nonnull CustomField> getFields();
    
    @Pure
    public <X extends ExternalException> int convert(@NonCaptured @Unmodified @Nullable T object, @NonCaptured @Modified @Nonnull ValueCollector<X> valueCollector) throws X;
    
    @Pure
    public @Capturable <X extends ExternalException> @Nullable T recover(@NonCaptured @Modified @Nonnull SelectionResult<X> selectionResult, E externallyProvided) throws X;
    
}
