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
public class PositiveByteTest extends TestBase {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassPositiveByteField {
        
        @Positive
        public final byte positive;
        
        public TestClassPositiveByteField(byte positive) {
            this.positive = positive;
        }
        
    }
    
    @Test
    public void isPositive() throws Exception {
        TestClassPositiveByteField testClassPositiveByteField = new TestClassPositiveByteField((byte) 1);
        Validator.validate(testClassPositiveByteField);
    }
    
    @Test
    public void isNotPositive() throws Exception {
        expectedException.expect(ValidationFailedException.class);
        expectedException.expectMessage("0 is not positive.");
        TestClassPositiveByteField testClassPositiveByteField = new TestClassPositiveByteField((byte) 0);
        Validator.validate(testClassPositiveByteField);
    }
    
}
