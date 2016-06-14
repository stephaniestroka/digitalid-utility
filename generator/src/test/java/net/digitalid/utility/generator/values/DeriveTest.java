package net.digitalid.utility.generator.values;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.generation.Derive;

@GenerateBuilder
@GenerateSubclass
public abstract class DeriveTest {
    
    @Pure
    public abstract int getValue();
    
    @Pure
    @Derive("2 * value")
    public abstract int getDoubleValue();
    
}
