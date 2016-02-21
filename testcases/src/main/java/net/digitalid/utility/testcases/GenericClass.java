package net.digitalid.utility.testcases;

import java.util.List;

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
    public G getGenericField() {
        return null;
    }
    
    @Pure
    public <T extends GenericClass<?>> T getFieldWithUpperBound() {
        return null;
    }
    
    public void addNumbers(List<? super Integer> list) {
        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }
    }
    
    @Pure
    public static @Nonnull <G extends GenericClass<G>> GenericClass<G> with() {
        return new GeneratedGenericClass<>();
    }
    
    public GenericClass() {
        
    }
    
}
