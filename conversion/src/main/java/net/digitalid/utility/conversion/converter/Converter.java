package net.digitalid.utility.conversion.converter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.conversion.exceptions.FailedValueConversionException;
import net.digitalid.utility.conversion.exceptions.FailedValueRecoveryException;
import net.digitalid.utility.logging.exceptions.ExternalException;

/**
 *
 */
public interface Converter<T> {
    
    @Pure
    public void declare(@Nonnull @Modified @NonCaptured Declaration declaration);
    
    @Pure
    public <R> void convert(@NonCaptured @Unmodified @Nullable T object, @NonCaptured @Modified @Nonnull ValueCollector<R> valueCollector);
    
    @Pure
    public @Nonnull @Capturable T recover(@NonCaptured @Nonnull SelectionResult selectionResult);
    
}
