package net.digitalid.utility.validation.math;

import net.digitalid.utility.testing.CustomTest;
import net.digitalid.utility.validation.annotations.math.Negative;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class NegativeLongTest extends CustomTest {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassNegativeLongField {
        
        @Negative
        public final long negative;
        
        public TestClassNegativeLongField(long negative) {
            this.negative = negative;
        }
        
    }
    
//    @Test
//    public void isNegative() throws Exception {
//        TestClassNegativeLongField testClassNegativeLongField = new TestClassNegativeLongField(-1);
//        AnnotationValidator.validate(testClassNegativeLongField);
//    }
//    
//    @Test
//    public void isNotNegative() throws Exception {
//        expectedException.expect(ValidationFailedException.class);
//        expectedException.expectMessage("0 is not negative.");
//        TestClassNegativeLongField testClassNegativeLongField = new TestClassNegativeLongField(0);
//        AnnotationValidator.validate(testClassNegativeLongField);
//    }
    
}
