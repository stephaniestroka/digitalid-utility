package net.digitalid.utility.logging;

import javax.annotation.Nonnull;

import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * The version of this library is used for logging.
 */
@Utility
public abstract class Version {
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    /**
     * Stores the version of this library.
     */
    public static final @Nonnull Configuration<String> string = Configuration.with("1.0.0");
    
}
