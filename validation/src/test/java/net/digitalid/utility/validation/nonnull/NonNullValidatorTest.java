package net.digitalid.utility.validation.nonnull;

import javax.annotation.Nonnull;
import net.digitalid.testing.base.TestBase;
import net.digitalid.utility.validation.validator.Validator;
import net.digitalid.utility.validation.validator.exceptions.ValidationFailedException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class NonNullValidatorTest extends TestBase {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class TestClassNonNullField {
        
        @Nonnull
        public final String nonnull;
        
        public TestClassNonNullField(String nonnull) {
            this.nonnull = nonnull;
        }
        
    }
    
    @Test
    public void isNonNull() throws Exception {
        TestClassNonNullField testClassNonNullField = new TestClassNonNullField("nonnull value");
        Validator.validate(testClassNonNullField);
    }
    
    @Test
    public void isNotNonNull() throws Exception {
        expectedException.expect(ValidationFailedException.class);
        expectedException.expectMessage("Non-null value expected for field annotated with @Nonnull.");
        TestClassNonNullField testClassNonNullField = new TestClassNonNullField(null);
        Validator.validate(testClassNonNullField);
    }
    
}
