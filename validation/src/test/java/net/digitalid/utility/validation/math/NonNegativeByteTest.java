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
public class NonNegativeByteTest extends TestBase {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassNonNegativeByteField {
        
        @NonNegative
        public final byte nonNegative;
        
        public TestClassNonNegativeByteField(byte nonNegative) {
            this.nonNegative = nonNegative;
        }
        
    }
    
    @Test
    public void isNonNegative() throws Exception {
        TestClassNonNegativeByteField testClassNonNegativeByteField = new TestClassNonNegativeByteField((byte) 0);
        Validator.validate(testClassNonNegativeByteField);
    }
    
    @Test
    public void isNotNonNegative() throws Exception {
        expectedException.expect(ValidationFailedException.class);
        expectedException.expectMessage("-1 is not non-negative.");
        TestClassNonNegativeByteField testClassNonNegativeByteField = new TestClassNonNegativeByteField((byte) -1);
        Validator.validate(testClassNonNegativeByteField);
    }
    
}
