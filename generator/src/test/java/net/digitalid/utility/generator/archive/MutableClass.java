package net.digitalid.utility.generator.archive;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.rootclass.RootClass;

@GenerateBuilder
@GenerateSubclass
public abstract class MutableClass extends RootClass {
    
    @Pure
    public abstract int getValue();
    
    @Impure
    public abstract void setValue(int value);
    
}
