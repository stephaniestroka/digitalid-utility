package net.digitalid.utility.cryptography.key.chain;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.collections.list.FreezableList;
import net.digitalid.utility.collections.list.ReadOnlyList;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.contracts.exceptions.PreconditionViolationException;
import net.digitalid.utility.cryptography.key.AsymmetricKey;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.time.Time;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.validation.annotations.order.StrictlyDescending;
import net.digitalid.utility.validation.annotations.size.NonEmpty;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * A key chain contains several items to support the rotation of host keys.
 * 
 * @see PublicKeyChain
 * @see PrivateKeyChain
 */
@Immutable
public abstract class KeyChain<K extends AsymmetricKey> {
    
    /* -------------------------------------------------- Items -------------------------------------------------- */
    
    /**
     * Returns the items of this key chain in chronological order with the newest one first.
     */
    @Pure
    public abstract @Nonnull @Frozen @NonEmpty @StrictlyDescending ReadOnlyList<@Nonnull Pair<@Nonnull Time, @Nonnull K>> getItems();
    
    /**
     * Returns the key in use at the given time.
     * 
     * @param time the time for which the key returned.
     * 
     * @return the key in use at the given time.
     * 
     * @throws PreconditionViolationException if there is no key for the given time.
     */
    @Pure
    public final @Nonnull K getKey(@Nonnull Time time) {
        for (final @Nonnull Pair<Time, K> item : getItems()) {
            if (time.isGreaterThanOrEqualTo(item.get0())) { return item.get1(); }
        }
        // TODO:
        throw PreconditionViolationException.with("There is no key for the given time (" + time + ") in this key chain " + this + ".");
    }
    
    /**
     * Returns the newest time of this key chain.
     */
    @Pure
    public final @Nonnull Time getNewestTime() {
        return getItems().get(0).get0();
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
    public final @Nonnull KeyChain<K> add(@Nonnull Time time, @Nonnull K key) {
        Require.that(time.isGreaterThan(getNewestTime())).orThrow("The time is greater than the newest time of this key chain.");
        Require.that(time.isGreaterThan(Time.CURRENT_TIME.add(Time.TROPICAL_YEAR))).orThrow("The time lies at least one year in the future.");
        
        final @Nonnull FreezableList<Pair<Time, K>> copy = getItems().clone();
        final @Nonnull Pair<Time, K> pair = Pair.of(time, key);
        copy.add(0, pair);
        
        final @Nonnull Time cutoff = Time.TWO_YEARS.ago();
        final @Nonnull Iterator<Pair<Time, K>> iterator = copy.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().get0().isLessThan(cutoff)) {
                while (iterator.hasNext()) {
                    iterator.next();
                    iterator.remove();
                }
                break;
            }
        }
        return createKeyChain(copy.freeze());
    }
    
    @Pure
    protected abstract KeyChain<K> createKeyChain(@Nonnull ReadOnlyList<Pair<Time, K>> list);
    
}
