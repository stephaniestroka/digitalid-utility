package net.digitalid.utility.generator.classes;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.getter.Default;

@GenerateBuilder
@GenerateSubclass
public abstract class ClassWithDefaultValuesInFields {
    
    @Pure
    @Default("99")
    public abstract int getNumber();
    
    @Default("\"blubb\"") 
    private final @Nonnull String text;
    
    private final @Nonnull String mandatoryText;
    
    public ClassWithDefaultValuesInFields(String text, @Nonnull String mandatoryText) {
        this.text = text;
        this.mandatoryText = mandatoryText;
    }
    
}
