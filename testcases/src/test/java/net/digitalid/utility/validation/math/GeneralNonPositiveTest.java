package net.digitalid.utility.validation.math;

import net.digitalid.utility.testing.LoggerSetup;
import net.digitalid.utility.validation.annotations.math.NonPositive;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class GeneralNonPositiveTest extends LoggerSetup {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassIncorrectFieldType {
        
        @NonPositive
        public final String nonPositive;
        
        public TestClassIncorrectFieldType(String nonPositive) {
            this.nonPositive = nonPositive;
        }
        
    }
    
//    @Test
//    public void isUnsupportedType() throws Exception {
//        expectedException.expect(ValidationFailedException.class);
//        expectedException.expectMessage("String is not a supported type for @NonPositive validation.");
//        TestClassIncorrectFieldType testClassIncorrectFieldType = new TestClassIncorrectFieldType("1");
//        AnnotationValidator.validate(testClassIncorrectFieldType);
//    }
    
}
