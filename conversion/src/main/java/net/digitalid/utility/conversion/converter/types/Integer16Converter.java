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
public class Integer16Converter implements LeafConverter<Short> {
    
    public static final @Nonnull Integer16Converter INSTANCE = new Integer16Converter();
    
    @Override
    public void convert(@NonCaptured @Unmodified Short object, @NonCaptured @Modified ValueCollector valueCollector) {
        
    }
    
}
