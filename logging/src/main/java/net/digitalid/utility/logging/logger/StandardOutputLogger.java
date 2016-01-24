package net.digitalid.utility.logging.logger;

/**
 * This class implements a logger that logs the messages to the standard output.
 */
public class StandardOutputLogger extends PrintStreamLogger {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a standard output logger that logs the messages to the standard output.
     */
    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    protected StandardOutputLogger() {
        super(System.out);
    }
    
    /**
     * Returns a standard output logger that logs the messages to the standard output.
     */
    public static StandardOutputLogger of() {
        return new StandardOutputLogger();
    }
    
}
