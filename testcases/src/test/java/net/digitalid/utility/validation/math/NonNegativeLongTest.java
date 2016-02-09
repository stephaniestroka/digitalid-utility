package net.digitalid.utility.validation.math;

import net.digitalid.utility.testing.CustomTest;
import net.digitalid.utility.validation.annotations.math.NonNegative;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class NonNegativeLongTest extends CustomTest {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassNonNegativeLongField {
        
        @NonNegative
        public final long nonNegative;
        
        public TestClassNonNegativeLongField(long nonNegative) {
            this.nonNegative = nonNegative;
        }
        
    }
    
//    @Test
//    public void isNonNegative() throws Exception {
//        TestClassNonNegativeLongField testClassNonNegativeLongField = new TestClassNonNegativeLongField(0);
//        AnnotationValidator.validate(testClassNonNegativeLongField);
//    }
//    
//    @Test
//    public void isNotNonNegative() throws Exception {
//        expectedException.expect(ValidationFailedException.class);
//        expectedException.expectMessage("-1 is not non-negative.");
//        TestClassNonNegativeLongField testClassNonNegativeLongField = new TestClassNonNegativeLongField(-1);
//        AnnotationValidator.validate(testClassNonNegativeLongField);
//    }
    
}
