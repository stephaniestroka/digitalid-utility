package net.digitalid.utility.validation.math;

import net.digitalid.utility.testing.LoggerSetup;
import net.digitalid.utility.validation.annotations.math.NonPositive;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class NonPositiveIntTest extends LoggerSetup {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassNonPositiveIntField {
        
        @NonPositive
        public final int nonPositive;
        
        public TestClassNonPositiveIntField(int nonPositive) {
            this.nonPositive = nonPositive;
        }
        
    }
    
//    @Test
//    public void isNonPositive() throws Exception {
//        TestClassNonPositiveIntField testClassNonPositiveIntField = new TestClassNonPositiveIntField(0);
//        AnnotationValidator.validate(testClassNonPositiveIntField);
//    }
//    
//    @Test
//    public void isNotNonPositive() throws Exception {
//        expectedException.expect(ValidationFailedException.class);
//        expectedException.expectMessage("1 is not non-positive.");
//        TestClassNonPositiveIntField testClassNonPositiveIntField = new TestClassNonPositiveIntField(1);
//        AnnotationValidator.validate(testClassNonPositiveIntField);
//    }
    
}
