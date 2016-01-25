package net.digitalid.utility.validation.math;

import net.digitalid.utility.testing.LoggerSetup;
import net.digitalid.utility.validation.validator.Validator;
import net.digitalid.utility.validation.validator.exceptions.ValidationFailedException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class MultipleOfIntFieldTest extends LoggerSetup {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassPositiveIntField {
        
        @MultipleOf(7)
        public final int multipleOfSeven;
        
        public TestClassPositiveIntField(int multipleOfSeven) {
            this.multipleOfSeven = multipleOfSeven;
        }
        
    }
    
    @Test
    public void isMultiple() throws Exception {
        TestClassPositiveIntField testClass = new TestClassPositiveIntField(21);
        Validator.validate(testClass);
    }
    
    @Test
    public void isNotMultiple() throws Exception {
        expectedException.expect(ValidationFailedException.class);
        expectedException.expectMessage("20 is not a multiple of 7.");
        TestClassPositiveIntField testClass = new TestClassPositiveIntField(20);
        Validator.validate(testClass);
    }
    
    @Test
    public void isNegativeMultipleOf() throws Exception {
        TestClassPositiveIntField testClass = new TestClassPositiveIntField(-21);
        Validator.validate(testClass);
    }
    
}
