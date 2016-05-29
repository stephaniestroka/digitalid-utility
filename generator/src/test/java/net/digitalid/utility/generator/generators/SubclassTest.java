package net.digitalid.utility.generator.generators;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.math.Positive;

interface Interface {
    
    @Pure
    public @Positive int getNumber();
    
}

public class SubclassTest {
    
    // Test that fields and constructors are generated correctly (also for interfaces).
    
}
