package net.digitalid.utility.generator.classes;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.rootclass.RootClass;

/**
 *
 */
public abstract class SimpleClass extends RootClass {

    @Pure
    public abstract int getNumber();
    
    SimpleClass() {
        
    }
    
}
