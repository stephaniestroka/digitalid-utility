package net.digitalid.testing.conversion;

import javax.annotation.Nonnull;

import net.digitalid.utility.collections.freezable.FreezableArrayList;

/**
 * Stores sample subclass objects.
 */
public class Objects {
    
    public final @Nonnull Subclass object = Subclass.get("Whatever", 0, 1, FreezableArrayList.get("first", "second", "third", "fourth", "fifth", "sixth"));
    
}
