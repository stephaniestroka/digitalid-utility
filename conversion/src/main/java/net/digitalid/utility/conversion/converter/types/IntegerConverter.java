package net.digitalid.utility.conversion.converter.types;

import java.math.BigInteger;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.conversion.converter.LeafConverter;
import net.digitalid.utility.conversion.converter.ValueCollector;

/**
 *
 */
public class IntegerConverter implements LeafConverter<BigInteger> {
    
    public static final @Nonnull IntegerConverter INSTANCE = new IntegerConverter();
    
    @Override
    public void convert(@NonCaptured @Unmodified BigInteger object, @NonCaptured @Modified ValueCollector valueCollector) {
        
    }
    
}
