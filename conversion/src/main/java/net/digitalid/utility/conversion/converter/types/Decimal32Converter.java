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
public class Decimal32Converter implements LeafConverter<Float> {
    
    public static final @Nonnull Decimal32Converter INSTANCE = new Decimal32Converter();
    
    @Override
    public void convert(@NonCaptured @Unmodified Float object, @NonCaptured @Modified ValueCollector valueCollector) {
        
    }
    
}
