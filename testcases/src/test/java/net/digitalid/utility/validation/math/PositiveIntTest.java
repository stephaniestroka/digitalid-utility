package net.digitalid.utility.validation.math;

import net.digitalid.utility.testing.CustomTest;
import net.digitalid.utility.validation.annotations.math.Positive;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class PositiveIntTest extends CustomTest {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassPositiveIntField {
        
        @Positive
        public final int positive;
        
        public TestClassPositiveIntField(int positive) {
            this.positive = positive;
        }
        
    }
    
//    @Test
//    public void isPositive() throws Exception {
//        TestClassPositiveIntField testClassPositiveIntField = new TestClassPositiveIntField(1);
//        AnnotationValidator.validate(testClassPositiveIntField);
//    }
//    
//    @Test
//    public void isNotPositive() throws Exception {
//        expectedException.expect(ValidationFailedException.class);
//        expectedException.expectMessage("0 is not positive.");
//        TestClassPositiveIntField testClassPositiveIntField = new TestClassPositiveIntField(0);
//        AnnotationValidator.validate(testClassPositiveIntField);
//    }
    
}
