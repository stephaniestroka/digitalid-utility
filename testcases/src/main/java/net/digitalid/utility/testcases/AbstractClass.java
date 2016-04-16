package net.digitalid.utility.testcases;

import java.io.IOException;

import javax.annotation.Nonnull;

import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.generator.annotations.Default;
import net.digitalid.utility.generator.annotations.Logged;
import net.digitalid.utility.generator.annotations.Recover;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.rootclass.RootClass;
import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * Description.
 */
@Immutable
public abstract class AbstractClass extends RootClass {
    
    @Pure
    @Logged
    public abstract @Nonnull String[] getValue();
    
    @Pure
    @Default(name="number", value="42")
    public abstract @Positive int getNumber();
    
    public abstract void setNumber(@Positive int number);
    
    protected AbstractClass() throws IOException {}
    
    @Pure
    @Recover
    public static @Nonnull AbstractClass with(@Nonnull String[] value, int number) throws IOException {
// TODO:       return new GeneratedAbstractClass(value, number);
        return null;
    }
    
    public static void main(@Nonnull String... args) throws IOException {
        Level.threshold.set(Level.VERBOSE);
        Configuration.initializeAllConfigurations();
        final @Nonnull AbstractClass object = AbstractClass.with(new String[] {"test"}, 4);
        Log.verbose("object.getNumber(): " + object.getNumber());
    }
    
}
