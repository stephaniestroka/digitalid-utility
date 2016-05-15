package net.digitalid.utility.immutable;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.iterators.ReadOnlyIterator;

import org.junit.Test;

import static org.junit.Assert.*;

public class ImmutableSetTest {
    
    @Test
    public void testImmutableSet() {
        final @Nonnull ImmutableSet<@Nonnull String> set = ImmutableSet.with("hello", "world");
        final @Nonnull ReadOnlyIterator<@Nonnull String> iterator = set.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("hello", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("world", iterator.next());
        assertFalse(iterator.hasNext());
    }
    
}
