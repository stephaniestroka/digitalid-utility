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
public class Integer64Converter implements LeafConverter<Long> {
    
    public static final @Nonnull Integer64Converter INSTANCE = new Integer64Converter();
    
    @Override
    public void convert(@NonCaptured @Unmodified Long object, @NonCaptured @Modified ValueCollector valueCollector) {
        
    }
    
}
