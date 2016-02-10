package net.digitalid.utility.validation.math;

import java.math.BigInteger;

import net.digitalid.utility.testing.CustomTest;
import net.digitalid.utility.validation.annotations.math.NonPositive;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class NonPositiveBigIntegerTest extends CustomTest {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassNonPositiveBigIntegerField {
        
        @NonPositive
        public final BigInteger nonPositive;
        
        public TestClassNonPositiveBigIntegerField(BigInteger nonPositive) {
            this.nonPositive = nonPositive;
        }
        
    }
    
//    @Test
//    public void isNonPositive() throws Exception {
//        TestClassNonPositiveBigIntegerField testClassNonPositiveBigIntegerField = new TestClassNonPositiveBigIntegerField(BigInteger.ZERO);
//        AnnotationValidator.validate(testClassNonPositiveBigIntegerField);
//    }
//    
//    @Test
//    public void isNotNonPositive() throws Exception {
//        expectedException.expect(ValidationFailedException.class);
//        expectedException.expectMessage("1 is not non-positive.");
//        TestClassNonPositiveBigIntegerField testClassNonPositiveBigIntegerField = new TestClassNonPositiveBigIntegerField(BigInteger.ONE);
//        AnnotationValidator.validate(testClassNonPositiveBigIntegerField);
//    }
//    
//    @Test
//    public void isNullIgnored() throws Exception {
//        TestClassNonPositiveBigIntegerField testClassNonPositiveBigIntegerField = new TestClassNonPositiveBigIntegerField(null);
//        AnnotationValidator.validate(testClassNonPositiveBigIntegerField);
//    }
    
}
