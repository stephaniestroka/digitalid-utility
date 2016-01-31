package net.digitalid.utility.validation.math;

import net.digitalid.utility.testing.LoggerSetup;
import net.digitalid.utility.validation.annotations.math.Negative;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class NegativeShortTest extends LoggerSetup {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassNegativeShortField {
        
        @Negative
        public final short negative;
        
        public TestClassNegativeShortField(short negative) {
            this.negative = negative;
        }
        
    }
    
//    @Test
//    public void isNegative() throws Exception {
//        TestClassNegativeShortField testClassNegativeShortField = new TestClassNegativeShortField((short) -1);
//        AnnotationValidator.validate(testClassNegativeShortField);
//    }
//    
//    @Test
//    public void isNotNegative() throws Exception {
//        expectedException.expect(ValidationFailedException.class);
//        expectedException.expectMessage("0 is not negative.");
//        TestClassNegativeShortField testClassNegativeShortField = new TestClassNegativeShortField((short) 0);
//        AnnotationValidator.validate(testClassNegativeShortField);
//    }
    
}
