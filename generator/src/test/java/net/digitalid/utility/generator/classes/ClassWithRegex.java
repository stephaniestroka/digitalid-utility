package net.digitalid.utility.generator.classes;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.string.Regex;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 *
 */
@Immutable
public abstract class ClassWithRegex {
    
    @Pure
    public abstract @Regex("Foo*") String getValue();
    
}
