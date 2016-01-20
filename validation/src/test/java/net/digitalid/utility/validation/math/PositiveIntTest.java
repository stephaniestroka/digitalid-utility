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
public class PositiveIntTest extends TestBase {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassPositiveIntField {
        
        @Positive
        public final int positive;
        
        public TestClassPositiveIntField(int positive) {
            this.positive = positive;
        }
        
    }
    
    @Test
    public void isPositive() throws Exception {
        TestClassPositiveIntField testClassPositiveIntField = new TestClassPositiveIntField(1);
        Validator.validate(testClassPositiveIntField);
    }
    
    @Test
    public void isNotPositive() throws Exception {
        expectedException.expect(ValidationFailedException.class);
        expectedException.expectMessage("0 is not positive.");
        TestClassPositiveIntField testClassPositiveIntField = new TestClassPositiveIntField(0);
        Validator.validate(testClassPositiveIntField);
    }
    
}
