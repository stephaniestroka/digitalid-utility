package net.digitalid.utility.generator.values;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.getter.Default;

import org.junit.Test;

import static org.junit.Assert.*;

@GenerateBuilder
@GenerateSubclass
abstract class ClassWithDefaultValuesInFields {
    
    @Pure
    @Default("99")
    public abstract int getNumber();
    
    @Default("\"blubb\"") 
    private final @Nonnull String optionalText;
    
    private final @Nonnull String mandatoryText;
    
    ClassWithDefaultValuesInFields(String optionalText, @Nonnull String mandatoryText) {
        this.optionalText = optionalText;
        this.mandatoryText = mandatoryText;
    }
    
}

@GenerateBuilder
@GenerateSubclass
abstract class OtherClass {
    
    @GenerateBuilder
    @GenerateSubclass
    abstract class NestedClass {}
    
    @Pure
    @Default("42")
    public abstract int getIdentity();
    
}

public class DefaultTest {
    
    @Test
    @Pure
    public void testNonnull() {
        assertEquals(42, OtherClassBuilder.get().build().getIdentity());
    }
    
}
