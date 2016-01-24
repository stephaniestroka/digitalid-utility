package net.digitalid.utility.logging.logger;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.digitalid.utility.logging.Level;
import net.digitalid.utility.logging.Version;

/**
 * This class implements a logger that logs the messages to a print stream.
 */
public abstract class PrintStreamLogger extends Logger {
    
    /* -------------------------------------------------- Date Format -------------------------------------------------- */
    
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
     * @require printStream != null : "The given print stream is not null.";
     */
    protected void setPrintStream(PrintStream printStream) {
        assert printStream != null : "The given print stream is not null.";
        
        this.printStream = printStream;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a print stream logger that logs the messages to the given non-nullable print stream.
     * 
     * @require printStream != null : "The given print stream is not null.";
     */
    protected PrintStreamLogger(PrintStream printStream) {
        assert printStream != null : "The given print stream is not null.";
        
        this.printStream = printStream;
    }
    
    /* -------------------------------------------------- Logging -------------------------------------------------- */
    
    @Override
    protected synchronized void log(Level level, String tag, String message, Throwable throwable) {
        printStream.println(timeFormat.get().format(new Date()) + " in " + Version.configuration.get() + " [" + Thread.currentThread().getName() + "] (" + level + ") <" + tag + ">: " + message);
        if (throwable != null) {
            printStream.println();
            throwable.printStackTrace(printStream);
            printStream.println();
        }
        printStream.flush();
    }
    
}
