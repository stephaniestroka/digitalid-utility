package net.digitalid.utility.cryptography;

import javax.annotation.Nonnull;

import net.digitalid.utility.collections.freezable.FreezableLinkedList;
import net.digitalid.utility.collections.readonly.ReadOnlyList;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.time.Time;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.size.NonEmpty;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class models a {@link KeyChain key chain} of {@link PublicKey public keys}.
 */
@Immutable
public final class PublicKeyChain extends KeyChain<PublicKeyChain, PublicKey> {
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */

    /**
     * Creates a new key chain with the given items.
     * 
     * @param items the items of the new key chain.
     * 
     * @require items.isStrictlyDescending() : "The list is strictly descending.";
     */
    private PublicKeyChain(@Nonnull @Frozen @NonEmpty @NonNullableElements ReadOnlyList<Pair<Time, PublicKey>> items) {
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
    public static @Nonnull PublicKeyChain get(@Nonnull @Frozen @NonEmpty @NonNullableElements ReadOnlyList<Pair<Time, PublicKey>> items) {
        return new PublicKeyChain(items);
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
    public static @Nonnull PublicKeyChain get(@Nonnull Time time, @Nonnull PublicKey key) {
        Require.that(time.isLessThanOrEqualTo(Time.getCurrent())).orThrow("The time lies in the past.");
        
        final @Nonnull FreezableLinkedList<Pair<Time, PublicKey>> items = FreezableLinkedList.get();
        items.add(Pair.get(time, key));
        return new PublicKeyChain(items.freeze());
    }

    @Override
    protected KeyChainCreator<PublicKeyChain, PublicKey> getKeyChainCreator() {
        return new KeyChainCreator<PublicKeyChain, PublicKey>() {

            @Override
            protected @Nonnull PublicKeyChain createKeyChain(@Nonnull @Frozen @NonEmpty @NonNullableElements ReadOnlyList<Pair<Time, PublicKey>> items) {
                return new PublicKeyChain(items);
            }

        };
    }

}
