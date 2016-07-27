package net.digitalid.utility.conversion.converter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;

public interface Converter<T, E> {
    
    @Pure
    public void declare(@Nonnull @NonCaptured @Modified Declaration declaration);
    
    @Pure
    public <R> void convert(@Nullable @NonCaptured @Unmodified T object, @Nonnull @NonCaptured @Modified ValueCollector<R> valueCollector);
    
    @Pure
    public @Nonnull @Capturable T recover(@Nonnull @NonCaptured @Modified SelectionResult selectionResult, E externallyProvided);
    
}
