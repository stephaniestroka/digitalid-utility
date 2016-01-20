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
public class NonPositiveShortTest extends TestBase {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassNonPositiveLongField {
        
        @NonPositive
        public final short nonPositive;
        
        public TestClassNonPositiveLongField(short nonPositive) {
            this.nonPositive = nonPositive;
        }
        
    }
    
    @Test
    public void isNonPositive() throws Exception {
        TestClassNonPositiveLongField testClassNonPositiveLongField = new TestClassNonPositiveLongField((short) 0);
        Validator.validate(testClassNonPositiveLongField);
    }
    
    @Test
    public void isNotNonPositive() throws Exception {
        expectedException.expect(ValidationFailedException.class);
        expectedException.expectMessage("1 is not non-positive.");
        TestClassNonPositiveLongField testClassNonPositiveLongField = new TestClassNonPositiveLongField((short) 1);
        Validator.validate(testClassNonPositiveLongField);
    }
    
}
