package net.digitalid.utility.logging.logger;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a logger that logs the messages to the standard output.
 */
@Mutable
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
    @Pure
    public static @Capturable @Nonnull StandardOutputLogger withNoArguments() {
        return new StandardOutputLogger();
    }
    
}
