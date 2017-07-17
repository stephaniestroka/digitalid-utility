package net.digitalid.utility.storage;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.method.PureWithSideEffects;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.rootclass.RootClass;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class models an inner node in the storage tree.
 */
@Mutable
public abstract class Module extends RootClass implements Storage {
    
    /* -------------------------------------------------- Substorages -------------------------------------------------- */
    
    private final @Nonnull @NonNullableElements Set<Storage> childStorages = new LinkedHashSet<>();
    
    /**
     * Returns the child storages of this storage.
     */
    @Pure
    public @Nonnull @NonNullableElements FiniteIterable<Storage> getChildStorages() {
        return FiniteIterable.of(childStorages);
    }
    
    /**
     * Registers the given child storage at this storage.
     */
    @Impure
    void addChildStorage(@Nonnull Storage childStorage) {
        childStorages.add(childStorage);
    }
    
    /* -------------------------------------------------- Visitor -------------------------------------------------- */
    
    @Override
    @PureWithSideEffects
    public <@Specifiable RESULT, @Specifiable PARAMETER> RESULT accept(@Nonnull StorageVisitor<RESULT, PARAMETER> visitor, PARAMETER parameter) {
        return visitor.visit(this, parameter);
    }
    
}
