package net.digitalid.utility.validation.math;

import net.digitalid.testing.base.TestBase;
import net.digitalid.utility.validation.validator.Validator;
import net.digitalid.utility.validation.validator.exceptions.ValidationFailedException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class GeneralNegativeTest extends TestBase {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassIncorrectFieldType {
        
        @Negative
        public final String negative;
        
        public TestClassIncorrectFieldType(String negative) {
            this.negative = negative;
        }
        
    }
    
    @Test
    public void isUnsupportedType() throws Exception {
        expectedException.expect(ValidationFailedException.class);
        expectedException.expectMessage("String is not a supported type for @Negative validation.");
        TestClassIncorrectFieldType testClassIncorrectFieldType = new TestClassIncorrectFieldType("1");
        Validator.validate(testClassIncorrectFieldType);
    }
    
}
