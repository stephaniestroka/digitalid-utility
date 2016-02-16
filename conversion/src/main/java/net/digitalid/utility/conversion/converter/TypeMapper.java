package net.digitalid.utility.conversion.converter;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

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
     * @param object the object, which is converted to the target type T.
     * 
     * @return the converted object of type T. 
     */
    @Pure
    public @Nonnull T convert(@Nonnull F object);
    
    /**
     * Recovers an object to its original type F. 
     * 
     * @param object the object which has previously been mapped to T and is now recovered to its type F.
     * 
     * @return the recovered object of type F.
     */
    @Pure
    public @Nonnull F recover(@Nonnull T object);
    
    /**
     * Returns the type to which objects are converted.
     * 
     * @return the type to which objects are converted.
     */
    @Pure
    public @Nonnull Class<T> getMapType();
    
}
