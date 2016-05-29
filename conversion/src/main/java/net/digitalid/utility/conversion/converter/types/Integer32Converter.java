package net.digitalid.utility.conversion.converter.types;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.conversion.converter.LeafConverter;
import net.digitalid.utility.conversion.converter.ResultSet;
import net.digitalid.utility.conversion.converter.ValueCollector;

// TODO: implement
/**
 *
 */
public class Integer32Converter implements LeafConverter<Integer> {
    
    public static final @Nonnull Integer32Converter INSTANCE = new Integer32Converter();
    
    @Override
    public void convert(@NonCaptured @Unmodified Integer integer, @NonCaptured @Modified ValueCollector valueCollector) {
        
    }
    
    @Nonnull @Override public Integer recover(@NonCaptured ResultSet resultSet) {
        return null;
    }
    
}
