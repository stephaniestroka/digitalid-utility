package net.digitalid.utility.generator.interceptors;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.generator.annotations.interceptors.Logged;

@GenerateSubclass
public class LoggedTest {
    
    @Pure
    @Logged
    public @Nonnull String method(@Nonnull String input) {
        return input;
    }
    
}
