package net.digitalid.utility.conversion.model;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.collaboration.annotations.TODO;
import net.digitalid.utility.collaboration.enumerations.Author;

/**
 *
 */
@TODO(task="Do we really need this interface?", date="2017-02-17", author = Author.STEPHANIE_STROKA)
public interface Declaration {
    
    @Impure
    public void setField(@Nonnull CustomField customField);
    
}
