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
package net.digitalid.utility.functional.iterables;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.functional.iterators.ReadOnlyIterator;
import net.digitalid.utility.validation.annotations.elements.NullableElements;

import org.junit.Assert;

public class FunctionalIterableTest {
    
    @Pure
    protected void assertElements(@Nonnull FunctionalIterable<?> iterable, @NonCaptured @Unmodified @Nonnull @NullableElements Object... elements) {
        final @Nonnull ReadOnlyIterator<?> iterator = iterable.iterator();
        for (@Nullable Object element : elements) {
            Assert.assertTrue(iterator.hasNext());
            Assert.assertEquals(element, iterator.next());
        }
        Assert.assertFalse(iterator.hasNext());
    }
    
}
