package net.digitalid.utility.testcases;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.math.Positive;

/**
 *
 */
public interface Interface {
    
    @Pure
    public @Positive int getNumber();
    
}
