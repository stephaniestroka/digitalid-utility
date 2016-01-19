package net.digitalid.utility.validation.math;

import net.digitalid.testing.base.TestBase;
import net.digitalid.utility.validation.validator.Validator;
import net.digitalid.utility.validation.validator.exceptions.ValidationFailedException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class NegativeLongTest extends TestBase {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassNegativeLongField {
        
        @Negative
        public final long negative;
        
        public TestClassNegativeLongField(long negative) {
            this.negative = negative;
        }
        
    }
    
    @Test
    public void isNegative() throws Exception {
        TestClassNegativeLongField testClassNegativeLongField = new TestClassNegativeLongField(-1);
        Validator.validate(testClassNegativeLongField);
    }
    
    @Test
    public void isNotNegative() throws Exception {
        expectedException.expect(ValidationFailedException.class);
        expectedException.expectMessage("0 is not negative.");
        TestClassNegativeLongField testClassNegativeLongField = new TestClassNegativeLongField(0);
        Validator.validate(testClassNegativeLongField);
    }
    
}
