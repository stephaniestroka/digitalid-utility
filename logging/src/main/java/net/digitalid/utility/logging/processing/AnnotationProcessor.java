package net.digitalid.utility.logging.processing;

import javax.annotation.processing.ProcessingEnvironment;

import net.digitalid.utility.configuration.Configuration;

/**
 * This class provides the environment for annotation processing.
 */
public class AnnotationProcessor {
    
    /* -------------------------------------------------- Environment -------------------------------------------------- */
    
    /**
     * Stores the processing environment of the current annotation processor.
     */
    public static final Configuration<ProcessingEnvironment> environment = Configuration.withUnknownProvider();
    
}
