package net.digitalid.utility.time;

import javax.annotation.Nonnull;

import org.junit.Assert;
import org.junit.Test;

public class TimeTest {
    
    @Test
    public void testSomeMethod() {
        final long value = 1_413_423;
        final @Nonnull Time time = Time.get(value);
        Assert.assertEquals(value, time.getValue());
    }
    
}
