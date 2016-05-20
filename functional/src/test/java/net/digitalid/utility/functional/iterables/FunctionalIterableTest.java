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
