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
public class PositiveShortTest extends LoggerSetup {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassPositiveShortField {
        
        @Positive
        public final short positive;
        
        public TestClassPositiveShortField(short positive) {
            this.positive = positive;
        }
        
    }
    
    @Test
    public void isPositive() throws Exception {
        TestClassPositiveShortField testClassPositiveShortField = new TestClassPositiveShortField((short) 1);
        Validator.validate(testClassPositiveShortField);
    }
    
    @Test
    public void isNotPositive() throws Exception {
        expectedException.expect(ValidationFailedException.class);
        expectedException.expectMessage("0 is not positive.");
        TestClassPositiveShortField testClassPositiveShortField = new TestClassPositiveShortField((short) 0);
        Validator.validate(testClassPositiveShortField);
    }
    
}
