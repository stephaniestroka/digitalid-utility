package net.digitalid.utility.system.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.digitalid.utility.system.directory.Directory;
import net.digitalid.utility.system.errors.InitializationError;

/**
 * This class implements a logger that logs the messages to a file.
 * <p>
 * <em>Warning:</em> Logging from different processes to the same file may fail!
 */
public final class DefaultLogger extends Logger {
    
    /* -------------------------------------------------- Formats -------------------------------------------------- */
    
    /**
     * Stores the date formatter for the file name.
     */
    private static final @Nonnull ThreadLocal<DateFormat> day = new ThreadLocal<DateFormat>() {
        @Override protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    
    /**
     * Stores the date formatter for the message time.
     */
    private static final @Nonnull ThreadLocal<DateFormat> time = new ThreadLocal<DateFormat>() {
        @Override protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss.SSS");
        }
    };
    
    /* -------------------------------------------------- Fields -------------------------------------------------- */
    
    /**
     * Stores the level of this logger.
     */
    private final @Nonnull Level level;
    
    /**
     * Stores the name of this logger.
     */
    private final @Nonnull String name;
    
    /**
     * Stores the version of the code.
     */
    private final @Nonnull String version;
    
    /**
     * Stores the date of the currently open log file.
     */
    private @Nonnull Date date;
    
    /**
     * Stores the print stream to which the messages are written.
     */
    private @Nonnull PrintStream out;
    
    /* -------------------------------------------------- Rotation -------------------------------------------------- */
    
    /**
     * Rotates the log file.
     */
    private void rotate() {
        this.date = new Date();
        try {
            this.out = new PrintStream(new FileOutputStream(Directory.getLogsDirectory().getPath() + File.separator + day.get().format(new Date()) + " " + name + ".log", true));
        } catch (@Nonnull FileNotFoundException exception) {
            throw new InitializationError("Could not open the log file '" + name + "'.", exception);
        }
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new logger that writes to the file with the given name.
     * 
     * @param level the level above which messages should be logged.
     * @param name the name of the file to write the log messages to.
     * @param version the version of the code which called this logger.
     */
    public DefaultLogger(@Nonnull Level level, @Nonnull String name, @Nonnull String version) {
        this.level = level;
        this.name = name;
        this.version = version;
        rotate();
    }
    
    /* -------------------------------------------------- Logging -------------------------------------------------- */
    
    @Override
    @SuppressWarnings("deprecation")
    protected synchronized void protectedLog(@Nonnull Level level, @Nonnull String tag, @Nonnull String message, @Nullable Throwable throwable) {
        if (level.getValue() >= this.level.getValue()) {
            final @Nonnull Date date = new Date();
            if (date.getDate() != this.date.getDate()) { rotate(); }
            out.println(time.get().format(date) + " in " + version + " [" + Thread.currentThread().getName() + "] (" + level + ") <" + tag + ">: " + message);
            if (throwable != null) {
                out.println();
                throwable.printStackTrace(out);
                out.println();
            }
            out.flush();
        }
    }
    
}
