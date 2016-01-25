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
public class GeneralNonNegativeTest extends LoggerSetup {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassIncorrectFieldType {
        
        @NonNegative
        public final String nonNegative;
        
        public TestClassIncorrectFieldType(String nonNegative) {
            this.nonNegative = nonNegative;
        }
        
    }
    
    @Test
    public void isUnsupportedType() throws Exception {
        expectedException.expect(ValidationFailedException.class);
        expectedException.expectMessage("String is not a supported type for @NonNegative validation.");
        TestClassIncorrectFieldType testClassIncorrectFieldType = new TestClassIncorrectFieldType("1");
        Validator.validate(testClassIncorrectFieldType);
    }
    
}
