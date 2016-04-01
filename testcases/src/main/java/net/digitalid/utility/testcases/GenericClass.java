package net.digitalid.utility.testcases;

import java.util.List;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.rootclass.RootClass;

/**
 * Description.
 */
public abstract class GenericClass<G extends GenericClass<G>> extends RootClass {
    
    public <T> T test(@Nonnull T object) {
        return object;
    }
    
    @Pure
    public G[] getGenericFields() {
        return null;
    }
    
    public void setGenericField(@Nonnull G genericClass) {}
    
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
// TODO:       return new GeneratedGenericClass<>();
        return null;
    }
    
    public GenericClass() {
        
    }
    
}
