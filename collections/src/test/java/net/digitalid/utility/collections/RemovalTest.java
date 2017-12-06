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
package net.digitalid.utility.collections;


import net.digitalid.utility.collections.list.FreezableArrayList;
import net.digitalid.utility.collections.list.FreezableLinkedList;
import net.digitalid.utility.collections.set.FreezableHashSet;
import net.digitalid.utility.collections.set.FreezableLinkedHashSet;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.testing.UtilityTest;

import org.junit.Test;

/**
 * Tests that the implementations of the tested methods do not call remove on the (read-only) iterator.
 */
public class RemovalTest extends UtilityTest {
    
    /* -------------------------------------------------- Remove -------------------------------------------------- */
    
    @Test
    public void testFreezableArrayListRemove() {
        FreezableArrayList.withElements(1, 2, 3, 4).remove(3);
    }
    
    @Test
    public void testFreezableLinkedListRemove() {
        FreezableLinkedList.withElements(1, 2, 3, 4).remove(3);
    }
    
    @Test
    public void testFreezableHashSetRemove() {
        FreezableHashSet.withElements(1, 2, 3, 4).remove(3);
    }
    
    @Test
    public void testFreezableLinkedHashSetRemove() {
        FreezableLinkedHashSet.withElements(1, 2, 3, 4).remove(3);
    }
    
    /* -------------------------------------------------- Remove All -------------------------------------------------- */
    
    @Test
    public void testFreezableArrayListRemoveAll() {
        FreezableArrayList.withElements(1, 2, 3, 4).removeAll(FiniteIterable.of(3, 4, 5).toList());
    }
    
    @Test
    public void testFreezableLinkedListRemoveAll() {
        FreezableLinkedList.withElements(1, 2, 3, 4).removeAll(FiniteIterable.of(3, 4, 5).toList());
    }
    
    @Test
    public void testFreezableHashSetRemoveAll() {
        FreezableHashSet.withElements(1, 2, 3, 4).removeAll(FiniteIterable.of(3, 4, 5).toList());
    }
    
    @Test
    public void testFreezableLinkedHashSetRemoveAll() {
        FreezableLinkedHashSet.withElements(1, 2, 3, 4).removeAll(FiniteIterable.of(3, 4, 5).toList());
    }
    
    /* -------------------------------------------------- Retain All -------------------------------------------------- */
    
    @Test
    public void testFreezableArrayListRetainAll() {
        FreezableArrayList.withElements(1, 2, 3, 4).retainAll(FiniteIterable.of(3, 4, 5).toList());
    }
    
    @Test
    public void testFreezableLinkedListRetainAll() {
        FreezableLinkedList.withElements(1, 2, 3, 4).retainAll(FiniteIterable.of(3, 4, 5).toList());
    }
    
    @Test
    public void testFreezableHashSetRetainAll() {
        FreezableHashSet.withElements(1, 2, 3, 4).retainAll(FiniteIterable.of(3, 4, 5).toList());
    }
    
    @Test
    public void testFreezableLinkedHashSetRetainAll() {
        FreezableLinkedHashSet.withElements(1, 2, 3, 4).retainAll(FiniteIterable.of(3, 4, 5).toList());
    }
    
    /* -------------------------------------------------- Clear -------------------------------------------------- */
    
    @Test
    public void testFreezableArrayListClear() {
        FreezableArrayList.withElements(1, 2, 3, 4).clear();
    }
    
    @Test
    public void testFreezableLinkedListClear() {
        FreezableLinkedList.withElements(1, 2, 3, 4).clear();
    }
    
    @Test
    public void testFreezableHashSetClear() {
        FreezableHashSet.withElements(1, 2, 3, 4).clear();
    }
    
    @Test
    public void testFreezableLinkedHashSetClear() {
        FreezableLinkedHashSet.withElements(1, 2, 3, 4).clear();
    }
    
}
