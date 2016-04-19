package net.digitalid.utility.collections.set;

import java.util.Objects;
import java.util.Set;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Referenced;
import net.digitalid.utility.collections.collection.BackedFreezableCollection;
import net.digitalid.utility.freezable.FreezableInterface;
import net.digitalid.utility.freezable.annotations.Freezable;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.functional.fixes.Brackets;
import net.digitalid.utility.generator.annotations.GenerateNoBuilder;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class implements a {@link Set set} that can be {@link FreezableInterface frozen}.
 * It is recommended to use only {@link ReadOnly} or {@link Immutable} types for the elements.
 * The implementation is backed by an ordinary {@link Set set}. 
 */
@GenerateNoBuilder
@Freezable(ReadOnlySet.class)
public class BackedFreezableSet<E> extends BackedFreezableCollection<E> implements FreezableSet<E> {
    
    /* -------------------------------------------------- Fields -------------------------------------------------- */
    
    /**
     * Stores the underlying set.
     */
    private final @Nonnull Set<E> set;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected BackedFreezableSet(@Nonnull FreezableInterface freezable, @Nonnull Set<E> set) {
        super(freezable, set);
        
        this.set = Objects.requireNonNull(set);
    }
    
    /**
     * Returns a new freezable set backed by the given freezable and set.
     */
    @Pure
    public static <E> @Capturable @Nonnull BackedFreezableSet<E> with(@Referenced @Modified @Nonnull FreezableInterface freezable, @Captured @Nonnull Set<E> set) {
        return new BackedFreezableSet<>(freezable, set);
    }
    
    /* -------------------------------------------------- Freezable -------------------------------------------------- */
    
    @Impure
    @Override
    public @Nonnull @Frozen ReadOnlySet<E> freeze() {
        super.freeze();
        return this;
    }
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableSet<E> clone() {
        return FreezableHashSet.with(set);
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return join(Brackets.CURLY);
    }
    
}
