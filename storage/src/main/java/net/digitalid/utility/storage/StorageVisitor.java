package net.digitalid.utility.storage;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.PureWithSideEffects;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * The interface to visit a {@link Storage}.
 */
@Stateless
@Functional
public interface StorageVisitor<@Specifiable RESULT, @Specifiable PARAMETER> {
    
    /**
     * Visits the given module with the given parameter and returns a result.
     */
    @PureWithSideEffects
    public default RESULT visit(@Nonnull Module module, PARAMETER parameter) {
        for (final @Nonnull Storage storage : module.getChildStorages()) {
            storage.accept(this, parameter);
        }
        return null;
    }
    
    /**
     * Visits the given table with the given parameter and returns a result.
     */
    @PureWithSideEffects
    public RESULT visit(@Nonnull Table<?, ?> table, PARAMETER parameter);
    
}
