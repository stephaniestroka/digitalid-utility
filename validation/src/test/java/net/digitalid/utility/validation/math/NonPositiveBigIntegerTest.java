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
public class NonPositiveBigIntegerTest extends TestBase {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassNonPositiveBigIntegerField {
        
        @NonPositive
        public final BigInteger nonPositive;
        
        public TestClassNonPositiveBigIntegerField(BigInteger nonPositive) {
            this.nonPositive = nonPositive;
        }
        
    }
    
    @Test
    public void isNonPositive() throws Exception {
        TestClassNonPositiveBigIntegerField testClassNonPositiveBigIntegerField = new TestClassNonPositiveBigIntegerField(BigInteger.ZERO);
        Validator.validate(testClassNonPositiveBigIntegerField);
    }
    
    @Test
    public void isNotNonPositive() throws Exception {
        expectedException.expect(ValidationFailedException.class);
        expectedException.expectMessage("1 is not non-positive.");
        TestClassNonPositiveBigIntegerField testClassNonPositiveBigIntegerField = new TestClassNonPositiveBigIntegerField(BigInteger.ONE);
        Validator.validate(testClassNonPositiveBigIntegerField);
    }
    
    @Test
    public void isNullIgnored() throws Exception {
        TestClassNonPositiveBigIntegerField testClassNonPositiveBigIntegerField = new TestClassNonPositiveBigIntegerField(null);
        Validator.validate(testClassNonPositiveBigIntegerField);
    }
    
}
