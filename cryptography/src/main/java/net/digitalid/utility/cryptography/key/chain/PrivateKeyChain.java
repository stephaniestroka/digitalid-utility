package net.digitalid.utility.cryptography.key.chain;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.collections.list.FreezableLinkedList;
import net.digitalid.utility.collections.list.ReadOnlyList;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.cryptography.key.PrivateKey;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.time.Time;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.size.NonEmpty;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class models a {@link KeyChain key chain} of {@link PrivateKey private keys}.
 */
@Immutable
public abstract class PrivateKeyChain extends KeyChain<PrivateKey> {
    
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
        Require.that(time.isLessThanOrEqualTo(Time.CURRENT_TIME)).orThrow("The time lies in the past.");

        final @Nonnull FreezableLinkedList<Pair<Time, PrivateKey>> items = FreezableLinkedList.with();
        items.add(Pair.of(time, key));
        return new PrivateKeyChainSubclass(items.freeze());
    }
    
    @Override
    protected @Nonnull PrivateKeyChain createKeyChain(@Nonnull @Frozen @NonEmpty @NonNullableElements ReadOnlyList<Pair<Time, PrivateKey>> items) {
        return new PrivateKeyChainSubclass(items);
    }
    
}
