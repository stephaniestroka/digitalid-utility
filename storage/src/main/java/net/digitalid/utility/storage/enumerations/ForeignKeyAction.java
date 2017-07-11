package net.digitalid.utility.storage.enumerations;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This type enumerates the various actions that can be specified for when a referenced entry is updated or deleted.
 * (Once the storage artifact can be moved to the database project again, move this enum to a new enumerations artifact because the SQLReferenceOption interface of the dialect also depends on this.)
 */
@Immutable
public enum ForeignKeyAction {
    
    /* -------------------------------------------------- Constants -------------------------------------------------- */
    
    /**
     * Rejects the delete or update operation for the parent table.
     */
    RESTRICT,
    
    /**
     * Propagates the delete or update operation to the child table.
     */
    CASCADE,
    
    /**
     * Sets the foreign key column or columns in the child table to NULL.
     */
    SET_NULL,
    
    /**
     * The same as {@link #RESTRICT} (at least in case of MySQL).
     */
    NO_ACTION;
    
    /* -------------------------------------------------- String -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return name().replace("_", " ");
    }
    
}
