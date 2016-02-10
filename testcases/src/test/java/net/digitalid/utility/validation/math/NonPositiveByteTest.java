package net.digitalid.utility.validation.math;

import net.digitalid.utility.testing.CustomTest;
import net.digitalid.utility.validation.annotations.math.NonPositive;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class NonPositiveByteTest extends CustomTest {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassNonPositiveByteField {
        
        @NonPositive
        public final byte nonPositive;
        
        public TestClassNonPositiveByteField(byte nonPositive) {
            this.nonPositive = nonPositive;
        }
        
    }
    
//    @Test
//    public void isNonPositive() throws Exception {
//        TestClassNonPositiveByteField testClassNonPositiveByteField = new TestClassNonPositiveByteField((byte) 0);
//        AnnotationValidator.validate(testClassNonPositiveByteField);
//    }
//    
//    @Test
//    public void isNotNonPositive() throws Exception {
//        expectedException.expect(ValidationFailedException.class);
//        expectedException.expectMessage("1 is not non-positive.");
//        TestClassNonPositiveByteField testClassNonPositiveByteField = new TestClassNonPositiveByteField((byte) 1);
//        AnnotationValidator.validate(testClassNonPositiveByteField);
//    }
    
}
