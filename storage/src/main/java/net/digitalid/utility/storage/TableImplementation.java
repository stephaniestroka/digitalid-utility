package net.digitalid.utility.storage;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.method.PureWithSideEffects;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.string.CodeIdentifier;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class implements the {@link Table} interface.
 */
@Immutable
public abstract class TableImplementation<@Unspecifiable ENTRY, @Specifiable PROVIDED> extends StorageImplementation implements Table<ENTRY, PROVIDED> {
    
    /* -------------------------------------------------- Name -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull @CodeIdentifier @MaxSize(63) String getName() {
        return getTypeName();
    }
    
    /* -------------------------------------------------- Visitor -------------------------------------------------- */
    
    @Override
    @PureWithSideEffects
    public <@Specifiable RESULT, @Specifiable PARAMETER> RESULT accept(@Nonnull StorageVisitor<RESULT, PARAMETER> visitor, PARAMETER parameter) {
        return visitor.visit(this, parameter);
    }
    
}
