package net.digitalid.utility.validation.math;

import java.math.BigInteger;

import net.digitalid.utility.testing.LoggerSetup;
import net.digitalid.utility.validation.annotations.math.Positive;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class PositiveBigIntegerTest extends LoggerSetup {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassNegativeBigIntegerField {
        
        @Positive
        public final BigInteger positive;
        
        public TestClassNegativeBigIntegerField(BigInteger positive) {
            this.positive = positive;
        }
        
    }
    
//    @Test
//    public void isPositive() throws Exception {
//        TestClassNegativeBigIntegerField testClassNegativeBigIntegerField = new TestClassNegativeBigIntegerField(BigInteger.ONE);
//        AnnotationValidator.validate(testClassNegativeBigIntegerField);
//    }
//    
//    @Test
//    public void isNotPositive() throws Exception {
//        expectedException.expect(ValidationFailedException.class);
//        expectedException.expectMessage("0 is not positive.");
//        TestClassNegativeBigIntegerField testClassNegativeBigIntegerField = new TestClassNegativeBigIntegerField(BigInteger.ZERO);
//        AnnotationValidator.validate(testClassNegativeBigIntegerField);
//    }
//    
//    @Test
//    public void isNullIgnored() throws Exception {
//        TestClassNegativeBigIntegerField testClassNegativeBigIntegerField = new TestClassNegativeBigIntegerField(null);
//        AnnotationValidator.validate(testClassNegativeBigIntegerField);
//    }
    
}
