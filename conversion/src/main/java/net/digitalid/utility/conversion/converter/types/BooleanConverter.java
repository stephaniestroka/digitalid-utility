package net.digitalid.utility.conversion.converter.types;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.conversion.converter.LeafConverter;
import net.digitalid.utility.conversion.converter.ValueCollector;

/**
 *
 */
public class BooleanConverter implements LeafConverter<Boolean> {
    
    public static final @Nonnull BooleanConverter INSTANCE = new BooleanConverter();
    
    @Override
    public void convert(@NonCaptured @Unmodified Boolean object, @NonCaptured @Modified ValueCollector valueCollector) {
    }
    
}
