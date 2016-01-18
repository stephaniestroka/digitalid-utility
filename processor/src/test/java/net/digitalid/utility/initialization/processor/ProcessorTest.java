package net.digitalid.utility.initialization.processor;

import net.digitalid.utility.initialization.annotations.Initialize;

import org.junit.Test;

public class ProcessorTest {
    
    public ProcessorTest() {
    }

    @Test
    public void testSomeMethod() {
//        Initializer.initialize();
    }
    
    /**
     * Initialization.
     */
    @Initialize
    public static void initialize() {
        System.out.println("Successful initialization!");
    }
    
}
