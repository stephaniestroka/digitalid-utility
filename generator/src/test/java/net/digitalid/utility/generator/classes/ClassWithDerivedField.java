package net.digitalid.utility.generator.classes;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.Derive;
import net.digitalid.utility.generator.annotations.GenerateBuilder;
import net.digitalid.utility.generator.annotations.GenerateSubclass;

/**
 *
 */
@GenerateBuilder
@GenerateSubclass
public abstract class ClassWithDerivedField {
    
    @Pure
    public abstract int getValue();
    
    @Pure
    @Derive("2 * value")
    public abstract int getDoubleValue();
    
}
