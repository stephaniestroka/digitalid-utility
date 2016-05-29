package net.digitalid.utility.logging.logger;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.logging.Version;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a logger that logs the messages to a print stream.
 * 
 * @see StandardOutputLogger
 * @see FileLogger
 */
@Mutable
public abstract class PrintStreamLogger extends Logger {
    
    /* -------------------------------------------------- Time Format -------------------------------------------------- */
    
    private static final @Nonnull ThreadLocal<@Nonnull DateFormat> timeFormat = new ThreadLocal<DateFormat>() {
        @Pure @Override protected @Capturable @Nonnull DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss.SSS");
        }
    };
    
    /* -------------------------------------------------- Print Stream -------------------------------------------------- */
    
    private @Nonnull PrintStream printStream;
    
    /**
     * Sets the print stream to which the messages are printed.
     */
    @Impure
    protected void setPrintStream(@Captured @Nonnull PrintStream printStream) {
        Require.that(printStream != null).orThrow("The print stream may not be null.");
        
        this.printStream = printStream;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a print stream logger that logs the messages to the given print stream.
     */
    protected PrintStreamLogger(@Captured @Nonnull PrintStream printStream) {
        Require.that(printStream != null).orThrow("The print stream may not be null.");
        
        this.printStream = printStream;
    }
    
    /* -------------------------------------------------- Logging -------------------------------------------------- */
    
    @Impure
    @Override
    protected synchronized void log(@Nonnull Level level, @Nonnull String caller, @Nonnull String thread, @Nonnull String message, @Nullable Throwable throwable) {
        final @Nonnull String version = Version.string.get();
        printStream.println(timeFormat.get().format(new Date()) + (version.isEmpty() ? "" : " in " + version) + " [" + thread + "] (" + level + ") <" + caller + ">: " + message);
        if (throwable != null) {
            printStream.println();
            throwable.printStackTrace(printStream);
            printStream.println();
        }
        printStream.flush();
    }
    
}
