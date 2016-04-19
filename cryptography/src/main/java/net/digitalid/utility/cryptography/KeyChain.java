package net.digitalid.utility.cryptography;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.collections.list.FreezableList;
import net.digitalid.utility.collections.list.ReadOnlyList;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.cryptography.exceptions.InvalidParameterValueCombinationException;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.time.Time;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.size.NonEmpty;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * A key chain contains several items to support the rotation of host keys.
 * 
 * @see PublicKeyChain
 * @see PrivateKeyChain
 */
@Immutable
public abstract class KeyChain<C extends KeyChain<C, K>, K> {
    
    /* -------------------------------------------------- Items -------------------------------------------------- */
    
    /**
     * Stores the items of this key chain in chronological order with the newest one first.
     * 
     * @invariant items.isStrictlyDescending() : "The list is strictly descending.";
     */
    private final @NonNullableElements @NonEmpty @Frozen @Nonnull ReadOnlyList<Pair<Time, K>> items;
    
    /**
     * Returns the items of this key chain in chronological order with the newest one first.
     * 
     * @return the items of this key chain in chronological order with the newest one first.
     * 
     * @ensure items.isStrictlyDescending() : "The list is strictly descending.";
     */
    @Pure
    public final @Nonnull @Frozen @NonEmpty @NonNullableElements ReadOnlyList<Pair<Time, K>> getItems() {
        return items;
    }
    
    /**
     * Returns the key in use at the given time.
     * 
     * @param time the time for which the key returned.
     * 
     * @return the key in use at the given time.
     * 
     * @throws InvalidParameterValueCombinationException if there is no key for the given time.
     */
    @Pure
    public final @Nonnull K getKey(@Nonnull Time time) throws InvalidParameterValueCombinationException {
        for (final @Nonnull Pair<Time, K> item : items) {
            if (time.isGreaterThanOrEqualTo(item.getNonNullableElement0())) { return item.getNonNullableElement1(); }
        }
        throw InvalidParameterValueCombinationException.get("There is no key for the given time (" + time + ") in this key chain " + this + ".");
    }
    
    /**
     * Returns the newest time of this key chain.
     * 
     * @return the newest time of this key chain.
     */
    @Pure
    public final @Nonnull Time getNewestTime() {
        return items.getNonNullable(0).getNonNullableElement0();
    }
    
    /**
     * Adds a new key to this key chain by returning a new instance.
     * 
     * @param time the time from when on the given key is valid.
     * @param key the key that is valid from the given time on.
     * 
     * @return a new key chain with the given key added and expired ones removed.
     */
    @Pure
    public final @Nonnull KeyChain<C, K> add(@Nonnull Time time, @Nonnull K key) {
        Require.that(time.isGreaterThan(getNewestTime())).orThrow("The time is greater than the newest time of this key chain.");
        Require.that(time.isGreaterThan(Time.getCurrent().add(Time.TROPICAL_YEAR))).orThrow("The time lies at least one year in the future.");
        
        final @Nonnull FreezableList<Pair<Time, K>> copy = items.clone();
        final @Nonnull Pair<Time, K> pair = Pair.get(time, key);
        copy.add(0, pair);
        
        final @Nonnull Time cutoff = Time.TWO_YEARS.ago();
        final @Nonnull FreezableIterator<Pair<Time, K>> iterator = copy.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getNonNullableElement0().isLessThan(cutoff)) {
                while (iterator.hasNext()) {
                    iterator.next();
                    iterator.remove();
                }
                break;
            }
        }
        return getKeyChainCreator().createKeyChain(copy.freeze());
    }
    
    protected abstract class KeyChainCreator<C, K> {
        
        protected abstract @Nonnull C createKeyChain(@Nonnull @Frozen @NonEmpty @NonNullableElements ReadOnlyList<Pair<Time, K>> items);
        
    }
    
    @Pure
    protected abstract KeyChainCreator<C, K> getKeyChainCreator();
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new key chain with the given items.
     * 
     * @param items the items of the new key chain.
     * 
     * @require items.isStrictlyDescending() : "The list is strictly descending.";
     */
    protected KeyChain(@Nonnull @Frozen @NonEmpty @NonNullableElements ReadOnlyList<Pair<Time, K>> items) {
        this.items = items;
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public final @Nonnull String toString() {
        return items.toString();
    }
    
}
