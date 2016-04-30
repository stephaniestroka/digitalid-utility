package net.digitalid.utility.testcases;

import java.io.IOException;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.validation.annotations.getter.Default;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.generator.annotations.interceptors.Logged;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.rootclass.RootClass;
import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * Description.
 */
@Immutable
@GenerateBuilder
@GenerateSubclass
public abstract class AbstractClass extends RootClass {
    
    @Pure
    @Logged
    public abstract @Nonnull String[] getValue();
    
    @Pure
    @Logged
    @Default(name="DefaultNumber", value="42")
    public abstract @Positive int getNumber();
    
    public abstract void setNumber(@Positive int number);
    
    protected AbstractClass() throws IOException {}
    
    public static void main(@Nonnull String... args) throws IOException {
        Level.threshold.set(Level.VERBOSE);
        Configuration.initializeAllConfigurations();
        final @Nonnull AbstractClass object = AbstractClassBuilder.withValue(new String[] {"test"}).withNumber(4).build();
        Log.verbose("object.getNumber(): " + object.getNumber());
    }
    
}
