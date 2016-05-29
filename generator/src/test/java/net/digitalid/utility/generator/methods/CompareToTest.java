package net.digitalid.utility.generator.methods;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.rootclass.RootClass;

/**
 *
 */
@GenerateBuilder
@GenerateSubclass
public abstract class CompareToTest extends RootClass implements Comparable<CompareToTest> {
    
    @Pure
    public abstract Integer getNumber();
    
}
