package net.digitalid.utility.generator.classes;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.Default;
import net.digitalid.utility.generator.annotations.GenerateBuilder;
import net.digitalid.utility.generator.annotations.GenerateSubclass;

/**
 *
 */
@GenerateBuilder
@GenerateSubclass
public abstract class ClassWithDefaultValuesInFields {
    
    @Pure
    @Default(name = "number", value = "99")
    public abstract int getNumber();
    
    private final @Nonnull String text;
    
    private final @Nonnull String mandatoryText;
    
    public ClassWithDefaultValuesInFields(@Default(name = "text", value = "\"blubb\"") String text, @Nonnull String mandatoryText) {
        this.text = text;
        this.mandatoryText = mandatoryText;
    }
    
}
