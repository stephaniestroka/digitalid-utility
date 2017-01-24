package net.digitalid.utility.conversion.model;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;

/**
 *
 */
public interface Declaration {
    
    @Impure
    public void setField(@Nonnull CustomField customField);
    
}
