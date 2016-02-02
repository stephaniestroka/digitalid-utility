package net.digitalid.utility.cryptography;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.size.NonEmpty;
import net.digitalid.utility.collections.freezable.FreezableLinkedList;
import net.digitalid.utility.collections.readonly.ReadOnlyList;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.time.Time;
import net.digitalid.utility.tuples.FreezablePair;
import net.digitalid.utility.tuples.ReadOnlyPair;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * This class models a {@link KeyChain key chain} of {@link PrivateKey private keys}.
 */
@Immutable
public final class PrivateKeyChain extends KeyChain<PrivateKeyChain, PrivateKey> {
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */


    /**
     * Creates a new key chain with the given items.
     * 
     * @param items the items of the new key chain.
     * 
     * @require items.isStrictlyDescending() : "The list is strictly descending.";
     */
    private PrivateKeyChain(@Nonnull @Frozen @NonEmpty @NonNullableElements ReadOnlyList<ReadOnlyPair<Time, PrivateKey>> items) {
        super(items);
    }
    
    /**
     * Creates a new key chain with the given items.
     * 
     * @param items the items of the new key chain.
     * 
     * @return a new key chain with the given items.
     * 
     * @require items.isStrictlyDescending() : "The list is strictly descending.";
     */
    @Pure
    public static @Nonnull PrivateKeyChain get(@Nonnull @Frozen @NonEmpty @NonNullableElements ReadOnlyList<ReadOnlyPair<Time, PrivateKey>> items) {
        return new PrivateKeyChain(items);
    }
    
    /**
     * Creates a new key chain with the given time and key.
     * 
     * @param time the time from when on the given key is valid.
     * @param key the key that is valid from the given time on.
     * 
     * @return a new key chain with the given time and key.
     * 
     * @require time.isLessThanOrEqualTo(Time.getCurrent()) : "The time lies in the past.";
     */
    @Pure
    public static @Nonnull PrivateKeyChain get(@Nonnull Time time, @Nonnull PrivateKey key) {
        assert time.isLessThanOrEqualTo(Time.getCurrent()) : "The time lies in the past.";

        final @Nonnull FreezableLinkedList<ReadOnlyPair<Time, PrivateKey>> items = FreezableLinkedList.get();
        items.add(FreezablePair.get(time, key).freeze());
        return new PrivateKeyChain(items.freeze());
    }
    
    @Override
    protected KeyChainCreator<PrivateKeyChain, PrivateKey> getKeyChainCreator() {
        return new KeyChainCreator<PrivateKeyChain, PrivateKey>() {

            @Override
            protected @Nonnull PrivateKeyChain createKeyChain(@Nonnull @Frozen @NonEmpty @NonNullableElements ReadOnlyList<ReadOnlyPair<Time, PrivateKey>> items) {
                return new PrivateKeyChain(items);
            }

        };
    }
    
}
