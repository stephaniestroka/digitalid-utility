package net.digitalid.utility.immutable;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.iterators.ReadOnlyIterator;
import net.digitalid.utility.functional.iterators.ReadOnlyListIterator;

import org.junit.Test;

import static org.junit.Assert.*;

public class ImmutableListTest {
    
    @Test
    public void testImmutableList() {
        final @Nonnull ImmutableList<@Nonnull String> list = ImmutableList.with("hello", "world");
        
        final @Nonnull ReadOnlyIterator<@Nonnull String> iterator = list.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("hello", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("world", iterator.next());
        assertFalse(iterator.hasNext());
        
        final @Nonnull ReadOnlyListIterator<@Nonnull String> listIterator = list.listIterator();
        assertFalse(listIterator.hasPrevious());
        assertTrue(listIterator.hasNext());
        assertEquals("hello", listIterator.next());
        assertTrue(listIterator.hasPrevious());
        assertTrue(listIterator.hasNext());
        assertEquals("world", listIterator.next());
        assertTrue(listIterator.hasPrevious());
        assertFalse(listIterator.hasNext());
        assertEquals("world", listIterator.previous());
        assertTrue(listIterator.hasPrevious());
        assertTrue(listIterator.hasNext());
        assertEquals("hello", listIterator.previous());
        assertFalse(listIterator.hasPrevious());
        assertTrue(listIterator.hasNext());
    }
    
}
