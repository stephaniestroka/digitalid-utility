package net.digitalid.utility.validation.math;

import net.digitalid.utility.validation.validator.Validator;
import org.junit.Test;

/**
 *
 */
public class MultipleOfTest {
    
    private static class TestClassLongField {
        
        @MultipleOf(7)
        public final long value;
        
        public TestClassLongField(long value) {
            this.value = value;
        }
        
    }
    
    @Test
    public void isMultipleOf() throws Exception {
        TestClassLongField testClass = new TestClassLongField(21);
        Validator.validate(testClass);
    }
    
}
