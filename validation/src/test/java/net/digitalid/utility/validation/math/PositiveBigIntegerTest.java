package net.digitalid.utility.validation.math;

import java.math.BigInteger;
import net.digitalid.testing.base.TestBase;
import net.digitalid.utility.validation.validator.Validator;
import net.digitalid.utility.validation.validator.exceptions.ValidationFailedException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class PositiveBigIntegerTest extends TestBase {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassNegativeBigIntegerField {
        
        @Positive
        public final BigInteger positive;
        
        public TestClassNegativeBigIntegerField(BigInteger positive) {
            this.positive = positive;
        }
        
    }
    
    @Test
    public void isPositive() throws Exception {
        TestClassNegativeBigIntegerField testClassNegativeBigIntegerField = new TestClassNegativeBigIntegerField(BigInteger.ONE);
        Validator.validate(testClassNegativeBigIntegerField);
    }
    
    @Test
    public void isNotPositive() throws Exception {
        expectedException.expect(ValidationFailedException.class);
        expectedException.expectMessage("0 is not positive.");
        TestClassNegativeBigIntegerField testClassNegativeBigIntegerField = new TestClassNegativeBigIntegerField(BigInteger.ZERO);
        Validator.validate(testClassNegativeBigIntegerField);
    }
    
    @Test
    public void isNullIgnored() throws Exception {
        TestClassNegativeBigIntegerField testClassNegativeBigIntegerField = new TestClassNegativeBigIntegerField(null);
        Validator.validate(testClassNegativeBigIntegerField);
    }
    
}
