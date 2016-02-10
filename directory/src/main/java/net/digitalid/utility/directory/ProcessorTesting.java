package net.digitalid.utility.directory;

import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.initialization.Initialize;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.logging.Log;

/**
 * This class exists just temporarily for testing the initialization processor.
 */
public class ProcessorTesting {
    
    public static void main(String[] args) {
        Configuration.initializeAllConfigurations();
    }
    
    @Initialize(target = Level.class)
    static void initializeLevel() {
        Level.threshold.set(Level.VERBOSE);
        Log.verbose("Threshold level set.");
    }
    
}
