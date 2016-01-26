package net.digitalid.utility.directory;

import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.initialization.annotations.Initialize;

/**
 * Description.
 */
public class ProcessorTesting {
    
    public static void main(String[] args) {
        Configuration.initializeAllConfigurations();
    }
    
    /**
     * Initialization.
     */
    @Initialize
    public static void initialize() {
        System.out.println("Successful initialization!");
    }
    
}
