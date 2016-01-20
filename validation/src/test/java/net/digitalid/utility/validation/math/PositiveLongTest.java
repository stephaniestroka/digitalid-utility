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
public class PositiveLongTest extends TestBase {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassPositiveLongField {
        
        @Positive
        public final long positive;
        
        public TestClassPositiveLongField(long positive) {
            this.positive = positive;
        }
        
    }
    
    @Test
    public void isPositive() throws Exception {
        TestClassPositiveLongField testClassPositiveLongField = new TestClassPositiveLongField(1);
        Validator.validate(testClassPositiveLongField);
    }
    
    @Test
    public void isNotPositive() throws Exception {
        expectedException.expect(ValidationFailedException.class);
        expectedException.expectMessage("0 is not positive.");
        TestClassPositiveLongField testClassPositiveLongField = new TestClassPositiveLongField(0);
        Validator.validate(testClassPositiveLongField);
    }
    
}
