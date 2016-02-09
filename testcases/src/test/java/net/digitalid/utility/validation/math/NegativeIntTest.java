package net.digitalid.utility.validation.math;

import net.digitalid.utility.testing.CustomTest;
import net.digitalid.utility.validation.annotations.math.Negative;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class NegativeIntTest extends CustomTest {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassNegativeIntField {
        
        @Negative
        public final int negative;
        
        public TestClassNegativeIntField(int negative) {
            this.negative = negative;
        }
        
    }
    
//    @Test
//    public void isNegative() throws Exception {
//        TestClassNegativeIntField testClassNegativeIntField = new TestClassNegativeIntField(-1);
//        AnnotationValidator.validate(testClassNegativeIntField);
//    }
//    
//    @Test
//    public void isNotNegative() throws Exception {
//        expectedException.expect(ValidationFailedException.class);
//        expectedException.expectMessage("0 is not negative.");
//        TestClassNegativeIntField testClassNegativeIntField = new TestClassNegativeIntField(0);
//        AnnotationValidator.validate(testClassNegativeIntField);
//    }
    
}
