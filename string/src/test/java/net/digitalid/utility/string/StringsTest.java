package net.digitalid.utility.string;

import org.junit.Assert;
import org.junit.Test;

public class StringsTest {
    
    @Test
    public void testFormat() {
        Assert.assertEquals("The result should have been '42' but was '41'.", Strings.format("The result should have been $ but was $.", 42, 41));
        Assert.assertEquals("The result should have been '42' but was '41'. ['too many']", Strings.format("The result should have been $ but was $.", 42, 41, "too many"));
        Assert.assertEquals("The result should have been '42' but was $.", Strings.format("The result should have been $ but was $.", 42));
    }
    
    @Test
    public void testLongestCommonPrefix() {
        Assert.assertEquals("Hello", Strings.longestCommonPrefix("Hello world.", "Hello?", "Hello there!"));
        Assert.assertEquals("net.digitalid.", Strings.longestCommonPrefix("net.digitalid.utility", "net.digitalid.database", "net.digitalid.core"));
    }
    
    @Test
    public void testCapitalizeFirstLetters() {
        Assert.assertEquals("Hello World", Strings.capitalizeFirstLetters("hello world"));
    }
    
    @Test
    public void testCapitalizeFirstLettersOnEmptyString() {
        Assert.assertEquals("", Strings.capitalizeFirstLetters(""));
    }
    
}
