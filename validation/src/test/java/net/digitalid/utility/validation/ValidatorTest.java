package net.digitalid.utility.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.testing.LoggerSetup;
import net.digitalid.utility.validation.state.Validated;
import net.digitalid.utility.validation.validator.Validator;
import net.digitalid.utility.validation.validator.annotation.ValidateWith;
import net.digitalid.utility.validation.validator.exceptions.ValidationFailedException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class ValidatorTest extends LoggerSetup {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static class ClassWithNonValidatableAnnotation {
        
        @Validated
        public final int field = 1;
        
    }
    
    @Test
    public void isNotValidated() throws Exception {
        ClassWithNonValidatableAnnotation objectWithNonValidatableAnnotation = new ClassWithNonValidatableAnnotation();
        Validator.validate(objectWithNonValidatableAnnotation);
    }
    
    public static class ClassWithClassInvariant {
    
        private final int field;
        
        public ClassWithClassInvariant(int field) {
            this.field = field;
        }
        
        public void classInvariant() throws ValidationFailedException {
            if (field < 0) {
                throw ValidationFailedException.get("Failed to satisfy class invariant.");
            }
        }
        
    }
    
    @Test
    public void shouldSucceedClassInvariant() throws Exception {
        ClassWithClassInvariant classWithClassInvariant = new ClassWithClassInvariant(2);
        Validator.validate(classWithClassInvariant);
    }
    
    @Test
    public void shouldFailClassInvariant() throws Exception {
        expectedException.expect(ValidationFailedException.class);
        expectedException.expectMessage("Failed to validate class invariant for type 'ClassWithClassInvariant'.");
        ClassWithClassInvariant classWithClassInvariant = new ClassWithClassInvariant(-2);
        Validator.validate(classWithClassInvariant);
    }
    
    public static class CorruptValidator extends Validator<CorruptAnnotation> {
        
        private static CorruptValidator get() {
            return new CorruptValidator();
        }
    
        @Override
        public void validate(@Nullable Object fieldValue, @Nonnull CorruptAnnotation annotation) throws ValidationFailedException {
            // this is never reached.
        }
    }
    
    @ValidateWith(CorruptValidator.class)
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CorruptAnnotation {
        
    }
    
    public static class ClassWithCorruptAnnotation {
        
        @CorruptAnnotation
        public final int field = 1;
        
    }
    
    @Test
    public void shouldFailToInstantiateCorruptValidator() throws Exception {
        expectedException.expect(ValidationFailedException.class);
        expectedException.expectMessage("Failed to initiate validator for annotation 'CorruptAnnotation'");
        ClassWithCorruptAnnotation classWithCorruptAnnotation = new ClassWithCorruptAnnotation();
        Validator.validate(classWithCorruptAnnotation);
    }
    
}
