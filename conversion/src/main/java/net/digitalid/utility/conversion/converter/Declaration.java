package net.digitalid.utility.conversion.converter;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;

/**
 *
 */
public interface Declaration {
    
    @Impure
    public void setField(@Nonnull CustomField customField);
    
}
