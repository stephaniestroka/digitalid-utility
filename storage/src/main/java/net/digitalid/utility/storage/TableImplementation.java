package net.digitalid.utility.storage;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.PureWithSideEffects;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class implements the {@link Table} interface.
 */
@Immutable
public abstract class TableImplementation<@Unspecifiable ENTRY, @Specifiable PROVIDED> extends StorageImplementation implements Table<ENTRY, PROVIDED> {
    
    /* -------------------------------------------------- Visitor -------------------------------------------------- */
    
    @Override
    @PureWithSideEffects
    public <@Specifiable RESULT, @Specifiable PARAMETER, @Unspecifiable EXCEPTION extends Exception> RESULT accept(@Nonnull StorageVisitor<RESULT, PARAMETER, EXCEPTION> visitor, PARAMETER parameter) throws EXCEPTION {
        return visitor.visit(this, parameter);
    }
    
}
