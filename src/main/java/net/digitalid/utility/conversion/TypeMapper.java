package net.digitalid.utility.conversion;

import javax.annotation.Nonnull;
import net.digitalid.utility.annotations.state.Pure;
import net.digitalid.utility.annotations.state.Stateless;

/**
 * This interface allows to map a type to and from another type.
 * 
 * @param <F> the type from which the object is mapped.
 * @param <T> the type to which the object is mapped.
 */
@Stateless
public interface TypeMapper<F, T> {
    
    /**
     * Maps the object to the target type.
     * 
     * @param from
     * @return 
     */
    @Pure
    public @Nonnull T mapTo(@Nonnull F from);
    
    /**
     * 
     * 
     * @param to
     * @return 
     */
    @Pure
    public @Nonnull F mapFrom(@Nonnull T to);
    
    /**
     * 
     * 
     * @return 
     */
    @Pure
    public @Nonnull Class<T> getMapType();
    
}
