package net.digitalid.utility.validation.math;

import net.digitalid.utility.testing.LoggerSetup;
import net.digitalid.utility.validation.validator.Validator;
import net.digitalid.utility.validation.validator.exceptions.ValidationFailedException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class NonPositiveLongTest extends LoggerSetup {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassNonPositiveLongField {
        
        @NonPositive
        public final long nonPositive;
        
        public TestClassNonPositiveLongField(long nonPositive) {
            this.nonPositive = nonPositive;
        }
        
    }
    
    @Test
    public void isNonPositive() throws Exception {
        TestClassNonPositiveLongField testClassNonPositiveLongField = new TestClassNonPositiveLongField(0);
        Validator.validate(testClassNonPositiveLongField);
    }
    
    @Test
    public void isNotNonPositive() throws Exception {
        expectedException.expect(ValidationFailedException.class);
        expectedException.expectMessage("1 is not non-positive.");
        TestClassNonPositiveLongField testClassNonPositiveLongField = new TestClassNonPositiveLongField(1);
        Validator.validate(testClassNonPositiveLongField);
    }
    
}
