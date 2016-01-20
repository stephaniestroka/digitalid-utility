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
public class GeneralNonPositiveTest extends TestBase {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassIncorrectFieldType {
        
        @NonPositive
        public final String nonPositive;
        
        public TestClassIncorrectFieldType(String nonPositive) {
            this.nonPositive = nonPositive;
        }
        
    }
    
    @Test
    public void isUnsupportedType() throws Exception {
        expectedException.expect(ValidationFailedException.class);
        expectedException.expectMessage("String is not a supported type for @NonPositive validation.");
        TestClassIncorrectFieldType testClassIncorrectFieldType = new TestClassIncorrectFieldType("1");
        Validator.validate(testClassIncorrectFieldType);
    }
    
}
