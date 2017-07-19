package net.digitalid.utility.storage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.PureWithSideEffects;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * A simpler interface to visit a {@link Storage}.
 */
@Stateless
@Functional
public interface SimpleStorageVisitor<@Unspecifiable EXCEPTION extends Exception> extends StorageVisitor<@Nullable Void, @Nullable Void, EXCEPTION> {
    
    /**
     * Visits the given table.
     */
    @PureWithSideEffects
    public void visit(@Nonnull Table<?, ?> table) throws EXCEPTION;
    
    @Override
    @PureWithSideEffects
    public default @Nullable Void visit(@Nonnull Table<?, ?> table, @Nullable Void parameter) throws EXCEPTION {
        visit(table);
        return null;
    }
    
}
