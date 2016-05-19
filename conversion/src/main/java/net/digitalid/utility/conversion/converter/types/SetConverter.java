package net.digitalid.utility.conversion.converter.types;

import java.util.Collection;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.conversion.converter.CustomField;
import net.digitalid.utility.conversion.converter.LeafConverter;
import net.digitalid.utility.conversion.converter.NodeConverter;
import net.digitalid.utility.conversion.converter.ValueCollector;
import net.digitalid.utility.immutable.ImmutableList;

/**
 *
 */
public class SetConverter<T extends Collection<?>> implements NodeConverter<T> {
    
    @Override
    public @Nonnull ImmutableList<CustomField> getFields() {
        return null;
    }
    
    @Override
    public void convert(@NonCaptured @Unmodified T object, @NonCaptured @Modified ValueCollector valueCollector) {
        
    }
    
    private @Nonnull LeafConverter<?> converter;
    
    SetConverter(@Nonnull LeafConverter<?> converter) {
        this.converter = converter;
    }
    
    public static @Nonnull <T extends Collection<?>> SetConverter<T> of(@Nonnull LeafConverter<?> converter) {
        return new SetConverter<>(converter);
    }
    
}
