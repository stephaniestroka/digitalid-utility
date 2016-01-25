package net.digitalid.testing.conversion;

import javax.annotation.Nonnull;

import net.digitalid.utility.conversion.TypeMapper;
import net.digitalid.utility.validation.state.Pure;
import net.digitalid.utility.validation.state.Stateless;

/**
 * This class maps ints to longs and vice versa.
 */
@Stateless
public class IntLongMapper implements TypeMapper<Integer, Long> {
    
    @Pure
    @Override
    public @Nonnull Long convert(@Nonnull Integer from) {
        return from.longValue();
    }
    
    @Pure
    @Override
    public @Nonnull Integer recover(@Nonnull Long to) {
        return to.intValue();
    }
    
    @Pure
    @Override
    public @Nonnull Class<Long> getMapType() {
        return Long.class;
    }
    
}
