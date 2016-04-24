package net.digitalid.utility.generator.classes;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.GenerateBuilder;
import net.digitalid.utility.generator.annotations.GenerateSubclass;
import net.digitalid.utility.validation.annotations.string.Regex;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 *
 */
@Immutable
@GenerateBuilder
@GenerateSubclass
public abstract class ClassWithRegex {
    
    @Pure
    public abstract @Regex("Foo*") String getValue();
    
}
