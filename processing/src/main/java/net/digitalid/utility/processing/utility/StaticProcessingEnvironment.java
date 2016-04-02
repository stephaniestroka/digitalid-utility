package net.digitalid.utility.processing.utility;


import javax.annotation.Nonnull;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * This class provides the environment for annotation processing.
 */
@Utility
public class StaticProcessingEnvironment {
    
    /* -------------------------------------------------- Environment -------------------------------------------------- */
    
    /**
     * Stores the processing environment of the current annotation processor.
     */
    public static final @Nonnull Configuration<ProcessingEnvironment> environment = Configuration.withUnknownProvider();
    
    /* -------------------------------------------------- Shortcuts -------------------------------------------------- */
    
    /**
     * Returns a implementation of some utility methods for operating on elements.
     */
    @Pure
    public static @Nonnull Elements getElementUtils() {
        return environment.get().getElementUtils();
    }
    
    /**
     * Returns a implementation of some utility methods for operating on types.
     */
    @Pure
    public static @Nonnull Types getTypeUtils() {
        return environment.get().getTypeUtils();
    }
    
}
