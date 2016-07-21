package net.digitalid.utility.generator.values;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.generation.Default;

import org.junit.Assert;
import org.junit.Test;

@GenerateBuilder
@GenerateSubclass
abstract class ClassWithDefaultValueInConstructorParameter {
    
    private final @Nonnull String optionalText;
    
    private final @Nonnull String mandatoryText;
    
    ClassWithDefaultValueInConstructorParameter(@Default("\"blubb\"") String optionalText, @Nonnull String mandatoryText) {
        this.optionalText = optionalText;
        this.mandatoryText = mandatoryText;
    }
    
    @Pure
    public @Nonnull String getOptionalText() {
        return optionalText;
    }
    
}

@GenerateBuilder
@GenerateSubclass
abstract class ClassWithDefaultValueInFields {
    
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
    public void testDefaultValues() {
        ClassWithDefaultValueInConstructorParameter classWithDefaultValueInConstructorParameter = ClassWithDefaultValueInConstructorParameterBuilder.withMandatoryText("Bla").build();
        Assert.assertEquals("blubb", classWithDefaultValueInConstructorParameter.getOptionalText());
        Assert.assertEquals(42, ClassWithDefaultValueInFieldsBuilder.build().getIdentity());
    }
    
}
