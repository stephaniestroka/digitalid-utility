package net.digitalid.utility.string;

import org.junit.Assert;
import org.junit.Test;

public class FormatStringTest {
    
    @Test
    public void testFormat() {
        Assert.assertEquals("The result should have been '42' but was '41'.", FormatString.format("The result should have been $ but was $.", 42, 41));
        Assert.assertEquals("The result should have been '42' but was '41'. ['too many']", FormatString.format("The result should have been $ but was $.", 42, 41, "too many"));
        Assert.assertEquals("The result should have been '42' but was $.", FormatString.format("The result should have been $ but was $.", 42));
    }
    
}
