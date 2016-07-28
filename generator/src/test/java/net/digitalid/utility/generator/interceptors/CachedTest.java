package net.digitalid.utility.generator.interceptors;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.generator.annotations.interceptors.Cached;
import net.digitalid.utility.testing.CustomTest;
import net.digitalid.utility.validation.annotations.type.Immutable;

import org.junit.Test;

@Immutable
@GenerateSubclass
class CachedMethod {
    
    @Pure
    @Cached
    double method(int input) {
        return Math.random();
    }
    
}

public class CachedTest extends CustomTest {
    
    @Test
    public void testCaching() {
        final @Nonnull CachedMethodSubclass object = new CachedMethodSubclass();
        assertTrue(object.method(0) == object.method(0));
        assertTrue(object.method(1) == object.method(1));
        assertTrue(object.method(0) != object.method(1));
    }
    
}
