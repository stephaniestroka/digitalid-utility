package net.digitalid.utility.conversion.converter;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;

/**
 *
 */
public interface LeafConverter<T> {
    
    @Pure
    public void convert(@NonCaptured @Unmodified @Nonnull T object, @NonCaptured @Modified @Nonnull ValueCollector valueCollector);
    
//    @Pure
//    public @Capturable @Nonnull T recover(@Nonnull ResultSet resultSet);
    
}
