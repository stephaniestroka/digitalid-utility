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
public class Integer08Converter implements LeafConverter<Byte> {
    
    public static final @Nonnull Integer08Converter INSTANCE = new Integer08Converter();
    
    @Override
    public void convert(@NonCaptured @Unmodified Byte object, @NonCaptured @Modified ValueCollector valueCollector) {
        
    }
    
}
