package net.digitalid.utility.validation.math;

import net.digitalid.utility.testing.LoggerSetup;
import net.digitalid.utility.validation.annotations.math.MultipleOf;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class GeneralMultipleOfTest extends LoggerSetup {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassIncorrectFieldType {
        
        @MultipleOf(7)
        public final String multipleOfSeven;
        
        public TestClassIncorrectFieldType(String multipleOfSeven) {
            this.multipleOfSeven = multipleOfSeven;
        }
        
    }
    
//    @Test
//    public void isUnsupportedType() throws Exception {
//        expectedException.expect(ValidationFailedException.class);
//        expectedException.expectMessage("String is not a supported type for @MultipleOf validation.");
//        TestClassIncorrectFieldType testClassIncorrectFieldType = new TestClassIncorrectFieldType("10");
//        AnnotationValidator.validate(testClassIncorrectFieldType);
//    }
    
}
