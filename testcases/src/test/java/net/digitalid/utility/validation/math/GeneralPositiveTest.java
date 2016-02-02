package net.digitalid.utility.validation.math;

import net.digitalid.utility.testing.LoggerSetup;
import net.digitalid.utility.validation.annotations.math.Positive;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class GeneralPositiveTest extends LoggerSetup {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassIncorrectFieldType {
        
        @Positive
        public final String positive;
        
        public TestClassIncorrectFieldType(String positive) {
            this.positive = positive;
        }
        
    }
    
//    @Test
//    public void isUnsupportedType() throws Exception {
//        expectedException.expect(ValidationFailedException.class);
//        expectedException.expectMessage("String is not a supported type for @Positive validation.");
//        TestClassIncorrectFieldType testClassIncorrectFieldType = new TestClassIncorrectFieldType("1");
//        AnnotationValidator.validate(testClassIncorrectFieldType);
//    }
    
}
