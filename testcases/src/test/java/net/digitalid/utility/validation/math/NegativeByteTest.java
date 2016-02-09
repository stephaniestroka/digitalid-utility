package net.digitalid.utility.validation.math;

import net.digitalid.utility.testing.CustomTest;
import net.digitalid.utility.validation.annotations.math.Negative;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class NegativeByteTest extends CustomTest {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassNegativeByteField {
        
        @Negative
        public final byte negative;
        
        public TestClassNegativeByteField(byte negative) {
            this.negative = negative;
        }
        
    }
    
//    @Test
//    public void isNegative() throws Exception {
//        TestClassNegativeByteField testClassNegativeByteField = new TestClassNegativeByteField((byte) -1);
//        AnnotationValidator.validate(testClassNegativeByteField);
//    }
//    
//    @Test
//    public void isNotNegative() throws Exception {
//        expectedException.expect(ValidationFailedException.class);
//        expectedException.expectMessage("0 is not negative.");
//        TestClassNegativeByteField testClassNegativeByteField = new TestClassNegativeByteField((byte) 0);
//        AnnotationValidator.validate(testClassNegativeByteField);
//    }
    
}
