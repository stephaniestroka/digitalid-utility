package net.digitalid.utility.directory;

import net.digitalid.utility.initialization.Initialize;
import net.digitalid.utility.initialization.Library;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.logging.Version;
import net.digitalid.utility.logging.logger.Logger;

/**
 * Description.
 */
public class ProcessorTesting {
    
    public static void main(String[] args) {
        Library.initialize();
    }
    
    /**
     * Initialization.
     */
    @Initialize(target = Logger.class, dependencies = {Level.class, Version.class})
    public static void initialize() {
        System.out.println("Successful initialization of logger!");
    }
    
    @Initialize(target = Level.class)
    public static void otherInitialize() {
        System.out.println("Successful initialization of level!");
    }
    
}
