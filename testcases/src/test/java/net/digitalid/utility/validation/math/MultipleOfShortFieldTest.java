package net.digitalid.utility.validation.math;

import net.digitalid.utility.testing.CustomTest;
import net.digitalid.utility.validation.annotations.math.MultipleOf;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class MultipleOfShortFieldTest extends CustomTest {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassPositiveByteField {
        
        @MultipleOf(7)
        public final short multipleOfSeven;
        
        public TestClassPositiveByteField(short multipleOfSeven) {
            this.multipleOfSeven = multipleOfSeven;
        }
        
    }
    
//    @Test
//    public void isMultiple() throws Exception {
//        TestClassPositiveByteField testClass = new TestClassPositiveByteField((short) 21);
//        AnnotationValidator.validate(testClass);
//    }
//    
//    @Test
//    public void isNotMultiple() throws Exception {
//        expectedException.expect(ValidationFailedException.class);
//        expectedException.expectMessage("20 is not a multiple of 7.");
//        TestClassPositiveByteField testClass = new TestClassPositiveByteField((short) 20);
//        AnnotationValidator.validate(testClass);
//    }
//    
//    @Test
//    public void isNegativeMultipleOf() throws Exception {
//        TestClassPositiveByteField testClass = new TestClassPositiveByteField((short) -21);
//        AnnotationValidator.validate(testClass);
//    }
    
}
