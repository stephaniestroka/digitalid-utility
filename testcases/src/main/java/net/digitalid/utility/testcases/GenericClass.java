package net.digitalid.utility.testcases;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * Description.
 */
public class GenericClass<G extends GenericClass<G>> {
    
    public <T> T test(@Nonnull T object) {
        return object;
    }
    
    @Pure
    public static @Nonnull <G extends GenericClass<G>> GenericClass<G> with() {
        return new GeneratedGenericClass<>();
    }
    
    public GenericClass() {
        
    }
    
}
