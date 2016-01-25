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
public class NonNegativeShortTest extends LoggerSetup {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassNonNegativeShortField {
        
        @NonNegative
        public final short nonNegative;
        
        public TestClassNonNegativeShortField(short nonNegative) {
            this.nonNegative = nonNegative;
        }
        
    }
    
    @Test
    public void isNonNegative() throws Exception {
        TestClassNonNegativeShortField testClassNonNegativeShortField = new TestClassNonNegativeShortField((short) 0);
        Validator.validate(testClassNonNegativeShortField);
    }
    
    @Test
    public void isNotNonNegative() throws Exception {
        expectedException.expect(ValidationFailedException.class);
        expectedException.expectMessage("-1 is not non-negative.");
        TestClassNonNegativeShortField testClassNonNegativeShortField = new TestClassNonNegativeShortField((short) -1);
        Validator.validate(testClassNonNegativeShortField);
    }
    
}
