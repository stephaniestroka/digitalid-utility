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
public class MultipleOfShortFieldTest extends TestBase {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassPositiveByteField {
        
        @MultipleOf(7)
        public final short multipleOfSeven;
        
        public TestClassPositiveByteField(short multipleOfSeven) {
            this.multipleOfSeven = multipleOfSeven;
        }
        
    }
    
    @Test
    public void isMultiple() throws Exception {
        TestClassPositiveByteField testClass = new TestClassPositiveByteField((short) 21);
        Validator.validate(testClass);
    }
    
    @Test
    public void isNotMultiple() throws Exception {
        expectedException.expect(ValidationFailedException.class);
        expectedException.expectMessage("20 is not a multiple of 7.");
        TestClassPositiveByteField testClass = new TestClassPositiveByteField((short) 20);
        Validator.validate(testClass);
    }
    
    @Test
    public void isNegativeMultipleOf() throws Exception {
        TestClassPositiveByteField testClass = new TestClassPositiveByteField((short) -21);
        Validator.validate(testClass);
    }
    
}
