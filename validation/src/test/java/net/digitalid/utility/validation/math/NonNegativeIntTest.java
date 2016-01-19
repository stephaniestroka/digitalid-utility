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
public class NonNegativeIntTest extends TestBase {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassNegativeIntField {
        
        @NonNegative
        public final int nonNegative;
        
        public TestClassNegativeIntField(int nonNegative) {
            this.nonNegative = nonNegative;
        }
        
    }
    
    @Test
    public void isNonNegative() throws Exception {
        TestClassNegativeIntField testClassNegativeIntField = new TestClassNegativeIntField(0);
        Validator.validate(testClassNegativeIntField);
    }
    
    @Test
    public void isNotNonNegative() throws Exception {
        expectedException.expect(ValidationFailedException.class);
        expectedException.expectMessage("-1 is not non-negative.");
        TestClassNegativeIntField testClassNegativeIntField = new TestClassNegativeIntField(-1);
        Validator.validate(testClassNegativeIntField);
    }
    
}
