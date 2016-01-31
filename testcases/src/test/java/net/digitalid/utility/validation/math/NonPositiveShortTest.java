package net.digitalid.utility.validation.math;

import net.digitalid.utility.testing.LoggerSetup;
import net.digitalid.utility.validation.annotations.math.NonPositive;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class NonPositiveShortTest extends LoggerSetup {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassNonPositiveLongField {
        
        @NonPositive
        public final short nonPositive;
        
        public TestClassNonPositiveLongField(short nonPositive) {
            this.nonPositive = nonPositive;
        }
        
    }
    
//    @Test
//    public void isNonPositive() throws Exception {
//        TestClassNonPositiveLongField testClassNonPositiveLongField = new TestClassNonPositiveLongField((short) 0);
//        AnnotationValidator.validate(testClassNonPositiveLongField);
//    }
//    
//    @Test
//    public void isNotNonPositive() throws Exception {
//        expectedException.expect(ValidationFailedException.class);
//        expectedException.expectMessage("1 is not non-positive.");
//        TestClassNonPositiveLongField testClassNonPositiveLongField = new TestClassNonPositiveLongField((short) 1);
//        AnnotationValidator.validate(testClassNonPositiveLongField);
//    }
    
}
