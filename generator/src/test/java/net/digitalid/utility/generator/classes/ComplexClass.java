package net.digitalid.utility.generator.classes;

import java.util.List;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.GenerateBuilder;
import net.digitalid.utility.generator.annotations.GenerateSubclass;
import net.digitalid.utility.rootclass.RootClass;

/**
 *
 */
@GenerateBuilder
@GenerateSubclass
public abstract class ComplexClass extends RootClass {
    
    public @Nonnull String text;
    
    @Pure
    public abstract @Nonnull SimpleClass getSimpleClass();
    
    @Pure
    public abstract @Nonnull List<Integer> getListOfIntegers();
    
    @Pure
    public abstract @Nonnull String[] getArrayOfStrings();
    
    public ComplexClass(@Nonnull String text) {
        this.text = text;
    }
    
    public void corruptMethod() {
        text = null;
    }
    
}
