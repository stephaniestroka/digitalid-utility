package net.digitalid.utility.generator.classes;

import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateConverter;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.rootclass.RootClass;

/**
 *
 */
@GenerateBuilder
@GenerateSubclass
@GenerateConverter
public abstract class ComplexClass extends RootClass {
    
    public @Nonnull String text;
    
    @Pure
    public abstract @Nonnull SimpleClass getSimpleClass();
    
    @Pure
    public abstract @Nonnull List<Integer> getListOfIntegers();
    
    @Pure
    public abstract @Nullable Set<SimpleClass> getSetOfSimpleClass();
    
    @Pure
    public abstract @Nonnull String[] getArrayOfStrings();
    
    public ComplexClass(@Nonnull String text) {
        this.text = text;
    }
    
    public void corruptMethod() {
        text = null;
    }
    
}
