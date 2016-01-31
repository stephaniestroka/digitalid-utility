package net.digitalid.utility.validation.math;

import net.digitalid.utility.testing.LoggerSetup;
import net.digitalid.utility.validation.annotations.math.NonNegative;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class NonNegativeShortTest extends LoggerSetup {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassNonNegativeShortField {
        
        @NonNegative
        public final short nonNegative;
        
        public TestClassNonNegativeShortField(short nonNegative) {
            this.nonNegative = nonNegative;
        }
        
    }
    
//    @Test
//    public void isNonNegative() throws Exception {
//        TestClassNonNegativeShortField testClassNonNegativeShortField = new TestClassNonNegativeShortField((short) 0);
//        AnnotationValidator.validate(testClassNonNegativeShortField);
//    }
//    
//    @Test
//    public void isNotNonNegative() throws Exception {
//        expectedException.expect(ValidationFailedException.class);
//        expectedException.expectMessage("-1 is not non-negative.");
//        TestClassNonNegativeShortField testClassNonNegativeShortField = new TestClassNonNegativeShortField((short) -1);
//        AnnotationValidator.validate(testClassNonNegativeShortField);
//    }
    
}
