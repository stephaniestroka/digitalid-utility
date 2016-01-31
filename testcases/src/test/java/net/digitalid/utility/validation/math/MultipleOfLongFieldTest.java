package net.digitalid.utility.validation.math;

import net.digitalid.utility.testing.LoggerSetup;
import net.digitalid.utility.validation.annotations.math.MultipleOf;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class MultipleOfLongFieldTest extends LoggerSetup {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassPositiveLongField {
        
        @MultipleOf(7)
        public final long multipleOfSeven;
        
        public TestClassPositiveLongField(long multipleOfSeven) {
            this.multipleOfSeven = multipleOfSeven;
        }
        
    }
    
//    @Test
//    public void isMultipleOf() throws Exception {
//        TestClassPositiveLongField testClass = new TestClassPositiveLongField(21);
//        AnnotationValidator.validate(testClass);
//    }
//    
//    @Test
//    public void isNotBeMultipleOf() throws Exception {
//        expectedException.expect(ValidationFailedException.class);
//        expectedException.expectMessage("20 is not a multiple of 7.");
//        TestClassPositiveLongField testClass = new TestClassPositiveLongField(20);
//        AnnotationValidator.validate(testClass);
//    }
//    
//    @Test
//    public void isNegativeMultipleOf() throws Exception {
//        TestClassPositiveLongField testClass = new TestClassPositiveLongField(-21);
//        AnnotationValidator.validate(testClass);
//    }
//    
//    @Test
//    public void isNegativeNotMultipleOf() throws Exception {
//        expectedException.expect(ValidationFailedException.class);
//        expectedException.expectMessage("-20 is not a multiple of 7.");
//        TestClassPositiveLongField testClass = new TestClassPositiveLongField(-20);
//        AnnotationValidator.validate(testClass);
//    }
//    
//    private static class TestClassPositiveLongBoxedField {
//        
//        @MultipleOf(7)
//        public final Long multipleOfSeven;
//        
//        public TestClassPositiveLongBoxedField(Long value) {
//            this.multipleOfSeven = value;
//        }
//        
//    }
//    
//    @Test
//    public void isNullIgnored() throws Exception {
//        TestClassPositiveLongBoxedField testClass = new TestClassPositiveLongBoxedField(null);
//        AnnotationValidator.validate(testClass);
//    }
//    
//    private static class TestClassNegativeLongField {
//        
//        @MultipleOf(-7)
//        public final long multipleOfMinusSeven;
//        
//        public TestClassNegativeLongField(long value) {
//            this.multipleOfMinusSeven = value;
//        }
//        
//    }
//    
//    @Test
//    public void isMultipleOfNegative() throws Exception {
//        TestClassNegativeLongField testClass = new TestClassNegativeLongField(21);
//        AnnotationValidator.validate(testClass);
//    }
    
}
