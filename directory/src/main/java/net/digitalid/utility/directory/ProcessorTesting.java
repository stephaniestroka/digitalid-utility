package net.digitalid.utility.directory;

import net.digitalid.utility.initialization.annotations.Initialize;
import net.digitalid.utility.initialization.initializer.Initializer;

/**
 * Description.
 */
public class ProcessorTesting {
    
    public static void main(String[] args) {
        Initializer.initialize();
    }
    
    /**
     * Initialization.
     */
    @Initialize
    public static void initialize() {
        System.out.println("Successful initialization!");
    }
    
}
