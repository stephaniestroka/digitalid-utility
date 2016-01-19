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
public class NegativeBigIntegerTest extends TestBase {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassNegativeBigIntegerField {
        
        @Negative
        public final BigInteger negative;
        
        public TestClassNegativeBigIntegerField(BigInteger negative) {
            this.negative = negative;
        }
        
    }
    
    @Test
    public void isNegative() throws Exception {
        TestClassNegativeBigIntegerField testClassNegativeBigIntegerField = new TestClassNegativeBigIntegerField(BigInteger.valueOf(-1));
        Validator.validate(testClassNegativeBigIntegerField);
    }
    
    @Test
    public void isNotNegative() throws Exception {
        expectedException.expect(ValidationFailedException.class);
        expectedException.expectMessage("0 is not negative.");
        TestClassNegativeBigIntegerField testClassNegativeBigIntegerField = new TestClassNegativeBigIntegerField(BigInteger.ZERO);
        Validator.validate(testClassNegativeBigIntegerField);
    }
    
    @Test
    public void isNullIgnored() throws Exception {
        TestClassNegativeBigIntegerField testClassNegativeBigIntegerField = new TestClassNegativeBigIntegerField(null);
        Validator.validate(testClassNegativeBigIntegerField);
    }
    
}
