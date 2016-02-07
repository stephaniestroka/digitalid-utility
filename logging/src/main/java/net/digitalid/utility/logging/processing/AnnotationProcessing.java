package net.digitalid.utility.logging.processing;


import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import net.digitalid.utility.configuration.Configuration;

/**
 * This class provides the environment for annotation processing.
 */
public class AnnotationProcessing {
    
    /* -------------------------------------------------- Environment -------------------------------------------------- */
    
    /**
     * Stores the processing environment of the current annotation processor.
     */
    public static final Configuration<ProcessingEnvironment> environment = Configuration.withUnknownProvider();
    
    /* -------------------------------------------------- Shortcuts -------------------------------------------------- */
    
    /**
     * Returns a non-nullable implementation of some utility methods for operating on elements.
     */
    public static Elements getElementUtils() {
        return environment.get().getElementUtils();
    }
    
    /**
     * Returns a non-nullable implementation of some utility methods for operating on types.
     */
    public static Types getTypeUtils() {
        return environment.get().getTypeUtils();
    }
    
}
