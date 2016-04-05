package net.digitalid.utility.generator.classes;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.rootclass.RootClass;

/**
 *
 */
public abstract class ComparableClass extends RootClass implements Comparable<ComparableClass> {
    
    @Pure
    public abstract Integer getNumber();
    
}
