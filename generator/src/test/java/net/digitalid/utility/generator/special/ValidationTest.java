package net.digitalid.utility.generator.special;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.substring.Regex;
import net.digitalid.utility.validation.annotations.type.Immutable;

@Immutable
@GenerateBuilder
@GenerateSubclass
public abstract class ValidationTest {
    
    // Test whether the input to the builder and subclass are properly validated.
    
    // Test whether the validate() method is called at the end of the constructor.
    
    @Pure
    public abstract @Regex("Foo*") String getValue();
    
}
