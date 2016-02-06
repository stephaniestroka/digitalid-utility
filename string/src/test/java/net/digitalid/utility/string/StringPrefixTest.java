package net.digitalid.utility.string;

import org.junit.Assert;
import org.junit.Test;

public class StringPrefixTest {
    
    @Test
    public void testLongestCommonPrefix() {
        Assert.assertEquals("Hello", PrefixString.longestCommonPrefix("Hello world.", "Hello?", "Hello there!"));
        Assert.assertEquals("net.digitalid.", PrefixString.longestCommonPrefix("net.digitalid.utility", "net.digitalid.database", "net.digitalid.core"));
    }
    
}
