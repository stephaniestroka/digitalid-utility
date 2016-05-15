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
public class String64Converter implements LeafConverter<String> {
    
    public static final @Nonnull String64Converter INSTANCE = new String64Converter();
    
    @Override
    public void convert(@NonCaptured @Unmodified String object, @NonCaptured @Modified ValueCollector valueCollector) {
        
    }
    
}
