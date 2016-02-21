package net.digitalid.utility.testcases;

import javax.annotation.Nonnull;

import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.generator.annotations.DefaultValue;
import net.digitalid.utility.generator.annotations.Logged;
import net.digitalid.utility.generator.annotations.Recover;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * Description.
 */
@Immutable
public abstract class AbstractClass {
    
    @Pure
    @Logged
    public abstract @Nonnull String getValue();
    
    @Pure
    @DefaultValue("42")
    public abstract @Positive int getNumber();
    
    @Pure
    @Recover
    public static @Nonnull AbstractClass with(@Nonnull String value) {
        return new GeneratedAbstractClass();
    }
    
    public static void main(@Nonnull String... args) {
        Level.threshold.set(Level.VERBOSE);
        Configuration.initializeAllConfigurations();
        final @Nonnull AbstractClass object = AbstractClass.with("test");
        Log.verbose("object.getValue(): " + object.getValue());
    }
    
}
