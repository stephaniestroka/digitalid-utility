package net.digitalid.utility.validation.math;

import net.digitalid.utility.testing.LoggerSetup;
import net.digitalid.utility.validation.annotations.math.NonNegative;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class NonNegativeIntTest extends LoggerSetup {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassNegativeIntField {
        
        @NonNegative
        public final int nonNegative;
        
        public TestClassNegativeIntField(int nonNegative) {
            this.nonNegative = nonNegative;
        }
        
    }
    
//    @Test
//    public void isNonNegative() throws Exception {
//        TestClassNegativeIntField testClassNegativeIntField = new TestClassNegativeIntField(0);
//        AnnotationValidator.validate(testClassNegativeIntField);
//    }
//    
//    @Test
//    public void isNotNonNegative() throws Exception {
//        expectedException.expect(ValidationFailedException.class);
//        expectedException.expectMessage("-1 is not non-negative.");
//        TestClassNegativeIntField testClassNegativeIntField = new TestClassNegativeIntField(-1);
//        AnnotationValidator.validate(testClassNegativeIntField);
//    }
    
}
