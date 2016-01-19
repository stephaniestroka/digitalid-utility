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
public class NonNegativeLongTest extends TestBase {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassNonNegativeLongField {
        
        @NonNegative
        public final long nonNegative;
        
        public TestClassNonNegativeLongField(long nonNegative) {
            this.nonNegative = nonNegative;
        }
        
    }
    
    @Test
    public void isNonNegative() throws Exception {
        TestClassNonNegativeLongField testClassNonNegativeLongField = new TestClassNonNegativeLongField(0);
        Validator.validate(testClassNonNegativeLongField);
    }
    
    @Test
    public void isNotNonNegative() throws Exception {
        expectedException.expect(ValidationFailedException.class);
        expectedException.expectMessage("-1 is not non-negative.");
        TestClassNonNegativeLongField testClassNonNegativeLongField = new TestClassNonNegativeLongField(-1);
        Validator.validate(testClassNonNegativeLongField);
    }
    
}
