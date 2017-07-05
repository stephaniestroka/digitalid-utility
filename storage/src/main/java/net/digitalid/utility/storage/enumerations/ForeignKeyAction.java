package net.digitalid.utility.storage.enumerations;

import javax.annotation.Nonnull;

import net.digitalid.utility.collaboration.annotations.TODO;
import net.digitalid.utility.collaboration.enumerations.Author;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This type enumerates the various actions that can be specified for when a referenced entry is updated or deleted.
 */
@Immutable
@TODO(task = "There is also an enumeration called SQLReferenceOption. One of them is likely redundant.", date = "2017-04-16", author = Author.KASPAR_ETTER)
public enum ForeignKeyAction {
    
    /* -------------------------------------------------- Constants -------------------------------------------------- */
    
    RESTRICT("RESTRICT"),
    
    CASCADE("CASCADE"),
    
    SET_NULL("SET NULL"),
    
    NO_ACTION("NO ACTION");
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    public final @Nonnull String value;
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    private ForeignKeyAction(@Nonnull String value) {
        this.value = value;
    }
    
}
