package net.digitalid.utility.generator.classes;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.GenerateBuilder;
import net.digitalid.utility.generator.annotations.GenerateSubclass;
import net.digitalid.utility.rootclass.RootClass;

/**
 *
 */
@GenerateBuilder
@GenerateSubclass
public abstract class SimpleClass extends RootClass {

    @Pure
    public abstract int getNumber();
    
    SimpleClass() {
        
    }
    
}
