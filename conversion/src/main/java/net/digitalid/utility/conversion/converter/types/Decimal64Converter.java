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
public class Decimal64Converter implements LeafConverter<Double> {
    
    public static final @Nonnull Decimal64Converter INSTANCE = new Decimal64Converter();
    
    @Override
    public void convert(@NonCaptured @Unmodified Double object, @NonCaptured @Modified ValueCollector valueCollector) {
        
    }
    
}
