package net.digitalid.utility.validation.math;

import net.digitalid.utility.testing.LoggerSetup;
import net.digitalid.utility.validation.annotations.math.Positive;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class PositiveLongTest extends LoggerSetup {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassPositiveLongField {
        
        @Positive
        public final long positive;
        
        public TestClassPositiveLongField(long positive) {
            this.positive = positive;
        }
        
    }
    
//    @Test
//    public void isPositive() throws Exception {
//        TestClassPositiveLongField testClassPositiveLongField = new TestClassPositiveLongField(1);
//        AnnotationValidator.validate(testClassPositiveLongField);
//    }
//    
//    @Test
//    public void isNotPositive() throws Exception {
//        expectedException.expect(ValidationFailedException.class);
//        expectedException.expectMessage("0 is not positive.");
//        TestClassPositiveLongField testClassPositiveLongField = new TestClassPositiveLongField(0);
//        AnnotationValidator.validate(testClassPositiveLongField);
//    }
    
}
