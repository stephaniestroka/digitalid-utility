package net.digitalid.utility.conversion.converter.types;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.conversion.converter.CustomField;
import net.digitalid.utility.conversion.converter.NodeConverter;
import net.digitalid.utility.conversion.converter.ValueCollector;
import net.digitalid.utility.immutable.ImmutableList;

/**
 *
 */
public class TupleConverter implements NodeConverter<Object> {
    
    private final @Nonnull NodeConverter<?> converter;
    
    TupleConverter(@Nonnull NodeConverter<?> converter) {
        this.converter = converter;
    }
    
    public static @Nonnull TupleConverter of(@Nonnull NodeConverter<?> converter) {
        return new TupleConverter(converter);
    }
    
    @Override
    public @Nonnull ImmutableList<CustomField> getFields() {
        return converter.getFields();
    }
    
    @Override
    public void convert(@NonCaptured @Unmodified Object object, @NonCaptured @Modified ValueCollector valueCollector) {
        
    }
    
}
