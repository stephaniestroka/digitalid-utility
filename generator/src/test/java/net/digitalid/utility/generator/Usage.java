package net.digitalid.utility.generator;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.testing.IncorrectUsage;
import net.digitalid.utility.validation.annotations.type.Immutable;

@IncorrectUsage(Immutable.Validator.class)
public class Usage {
    
    @Pure
    public void pureMethod() {}
    
    @Impure
    public void impureMethod() {}
    
}
