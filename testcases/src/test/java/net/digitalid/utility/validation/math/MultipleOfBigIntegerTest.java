package net.digitalid.utility.validation.math;

import java.math.BigInteger;

import net.digitalid.utility.testing.LoggerSetup;
import net.digitalid.utility.validation.annotations.math.MultipleOf;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class MultipleOfBigIntegerTest extends LoggerSetup {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassPositiveBigIntegerField {
        
        @MultipleOf(7)
        public final BigInteger multipleOfSeven;
        
        public TestClassPositiveBigIntegerField(BigInteger multipleOfSeven) {
            this.multipleOfSeven = multipleOfSeven;
        }
        
    }
    
//    @Test
//    public void isMultiple() throws Exception {
//        TestClassPositiveBigIntegerField testClass = new TestClassPositiveBigIntegerField(BigInteger.valueOf(21));
//        AnnotationValidator.validate(testClass);
//    }
//    
//    @Test
//    public void isNotMultiple() throws Exception {
//        expectedException.expect(ValidationFailedException.class);
//        expectedException.expectMessage("20 is not a multiple of 7.");
//        TestClassPositiveBigIntegerField testClass = new TestClassPositiveBigIntegerField(BigInteger.valueOf(20));
//        AnnotationValidator.validate(testClass);
//    }
//    
//    @Test
//    public void isNegativeMultipleOf() throws Exception {
//        TestClassPositiveBigIntegerField testClass = new TestClassPositiveBigIntegerField(BigInteger.valueOf(-21));
//        AnnotationValidator.validate(testClass);
//    }
    
}
