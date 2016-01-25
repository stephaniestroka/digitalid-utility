package net.digitalid.utility.validation.math;

import java.math.BigInteger;

import net.digitalid.utility.testing.LoggerSetup;
import net.digitalid.utility.validation.validator.Validator;
import net.digitalid.utility.validation.validator.exceptions.ValidationFailedException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class NonNegativeBigIntegerTest extends LoggerSetup {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassNonNegativeBigIntegerField {
        
        @NonNegative
        public final BigInteger nonNegative;
        
        public TestClassNonNegativeBigIntegerField(BigInteger nonNegative) {
            this.nonNegative = nonNegative;
        }
        
    }
    
    @Test
    public void isNonNegative() throws Exception {
        TestClassNonNegativeBigIntegerField testClassNonNegativeBigIntegerField = new TestClassNonNegativeBigIntegerField(BigInteger.ZERO);
        Validator.validate(testClassNonNegativeBigIntegerField);
    }
    
    @Test
    public void isNotNonNegative() throws Exception {
        expectedException.expect(ValidationFailedException.class);
        expectedException.expectMessage("-1 is not non-negative.");
        TestClassNonNegativeBigIntegerField testClassNonNegativeBigIntegerField = new TestClassNonNegativeBigIntegerField(BigInteger.valueOf(-1));
        Validator.validate(testClassNonNegativeBigIntegerField);
    }
    
    @Test
    public void isNullIgnored() throws Exception {
        TestClassNonNegativeBigIntegerField testClassNonNegativeBigIntegerField = new TestClassNonNegativeBigIntegerField(null);
        Validator.validate(testClassNonNegativeBigIntegerField);
    }
    
}
