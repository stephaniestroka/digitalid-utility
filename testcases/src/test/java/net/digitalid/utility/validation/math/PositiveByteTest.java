package net.digitalid.utility.validation.math;

import net.digitalid.utility.testing.LoggerSetup;
import net.digitalid.utility.validation.annotations.math.Positive;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class PositiveByteTest extends LoggerSetup {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassPositiveByteField {
        
        @Positive
        public final byte positive;
        
        public TestClassPositiveByteField(byte positive) {
            this.positive = positive;
        }
        
    }
    
//    @Test
//    public void isPositive() throws Exception {
//        TestClassPositiveByteField testClassPositiveByteField = new TestClassPositiveByteField((byte) 1);
//        AnnotationValidator.validate(testClassPositiveByteField);
//    }
//    
//    @Test
//    public void isNotPositive() throws Exception {
//        expectedException.expect(ValidationFailedException.class);
//        expectedException.expectMessage("0 is not positive.");
//        TestClassPositiveByteField testClassPositiveByteField = new TestClassPositiveByteField((byte) 0);
//        AnnotationValidator.validate(testClassPositiveByteField);
//    }
    
}
