package net.digitalid.utility.logging.logger;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.logging.Version;

/**
 * This class implements a logger that logs the messages to a print stream.
 * 
 * @see StandardOutputLogger
 * @see FileLogger
 */
public abstract class PrintStreamLogger extends Logger {
    
    /* -------------------------------------------------- Time Format -------------------------------------------------- */
    
    /**
     * Stores the date format of the message time.
     */
    private static final ThreadLocal<DateFormat> timeFormat = new ThreadLocal<DateFormat>() {
        @Override protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss.SSS");
        }
    };
    
    /* -------------------------------------------------- Print Stream -------------------------------------------------- */
    
    /**
     * Stores the non-nullable print stream to which the messages are printed.
     */
    private PrintStream printStream;
    
    /**
     * Sets the non-nullable print stream to which the messages are printed.
     * 
     * @require printStream != null : "The print stream may not be null.";
     */
    protected void setPrintStream(PrintStream printStream) {
        Require.that(printStream != null).orThrow("The print stream may not be null.");
        
        this.printStream = printStream;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a print stream logger that logs the messages to the given non-nullable print stream.
     * 
     * @require printStream != null : "The print stream may not be null.";
     */
    protected PrintStreamLogger(PrintStream printStream) {
        Require.that(printStream != null).orThrow("The print stream may not be null.");
        
        this.printStream = printStream;
    }
    
    /* -------------------------------------------------- Logging -------------------------------------------------- */
    
    @Override
    protected synchronized void log(Level level, String tag, String message, Throwable throwable) {
        final String version = Version.string.get();
        printStream.println(timeFormat.get().format(new Date()) + (version.isEmpty() ? "" : " in " + version) + " [" + Thread.currentThread().getName() + "] (" + level + ") <" + tag + ">: " + message);
        if (throwable != null) {
            printStream.println();
            throwable.printStackTrace(printStream);
            printStream.println();
        }
        printStream.flush();
    }
    
}
