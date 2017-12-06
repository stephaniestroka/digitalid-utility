/*
 * Copyright (C) 2017 Synacts GmbH, Switzerland (info@synacts.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.digitalid.utility.immutable;

import javax.annotation.Nonnull;

import net.digitalid.utility.immutable.entry.ReadOnlyEntry;
import net.digitalid.utility.immutable.entry.ReadOnlyEntrySet;
import net.digitalid.utility.immutable.entry.ReadOnlyEntrySetIterator;

import org.junit.Test;

import static org.junit.Assert.*;

public class ImmutableMapTest {
    
    @Test
    public void testImmutableMap() {
        assertTrue(ImmutableMap.withNoEntries().isEmpty());
        
        final @Nonnull ImmutableMap<@Nonnull Character, @Nonnull String> map = ImmutableMap.with('h', "hello").with('w', "world").build();
        assertEquals("hello", map.get('h'));
        assertEquals("world", map.get('w'));
        assertEquals(2, map.size());
        
        final @Nonnull ReadOnlyEntrySet<@Nonnull Character, @Nonnull String> entrySet = map.entrySet();
        final @Nonnull ReadOnlyEntrySetIterator<@Nonnull Character, @Nonnull String> iterator = entrySet.iterator();
        @Nonnull ReadOnlyEntry<@Nonnull Character, @Nonnull String> entry;
        assertTrue(iterator.hasNext());
        entry = iterator.next();
        assertEquals('h', (char) entry.getKey());
        assertEquals("hello", entry.getValue());
        assertTrue(iterator.hasNext());
        entry = iterator.next();
        assertEquals('w', (char) entry.getKey());
        assertEquals("world", entry.getValue());
        assertFalse(iterator.hasNext());
    }
    
}
