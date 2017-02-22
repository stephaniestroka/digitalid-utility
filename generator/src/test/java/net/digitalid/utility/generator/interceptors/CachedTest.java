package net.digitalid.utility.generator.interceptors;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.generator.annotations.interceptors.Cached;
import net.digitalid.utility.testing.UtilityTest;
import net.digitalid.utility.tuples.Pair;
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
    
    @Pure
    @Cached
    double method(double input) {
        return Math.random();
    }
    
    @Pure
    @Cached
    double method(String input1, int input2) {
        return Math.random();
    }
    
    @Pure
    @Cached
    double method(Pair<String, Integer> input) {
        return Math.random();
    }
    
}

public class CachedTest extends UtilityTest {
    
    @Test
    public void testCaching() {
        final @Nonnull CachedMethodSubclass object = new CachedMethodSubclass();
        assertThat(object.method(0) == object.method(0)).isTrue();
        assertThat(object.method(1) == object.method(1)).isTrue();
        assertThat(object.method(0) == object.method(1)).isFalse();
    }
    
}
