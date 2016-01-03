package net.digitalid.utility.conversion;

import javax.annotation.Nonnull;
import net.digitalid.utility.annotations.state.Pure;

public interface TypeToTypeMapper<T, F> {
    
    @Pure
    @Nonnull T mapTo(@Nonnull F from);

    @Pure
    @Nonnull F mapFrom(@Nonnull T to);

    @Pure
    @Nonnull Class<T> getMapType();
    
}
