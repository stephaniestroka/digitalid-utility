package net.digitalid.utility.logging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.digitalid.utility.logging.directory.Directory;

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
    private static final ThreadLocal<DateFormat> day = new ThreadLocal<DateFormat>() {
        @Override protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    
    /**
     * Stores the date formatter for the message time.
     */
    private static final ThreadLocal<DateFormat> time = new ThreadLocal<DateFormat>() {
        @Override protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss.SSS");
        }
    };
    
    /* -------------------------------------------------- Fields -------------------------------------------------- */
    
    /**
     * Stores the level of this logger.
     */
    private final Level level;
    
    /**
     * Stores the name of this logger.
     */
    private final String name;
    
    /**
     * Stores the version of the code.
     */
    private final String version;
    
    /**
     * Stores the date of the currently open log file.
     */
    private Date date;
    
    /**
     * Stores the print stream to which the messages are written.
     */
    private PrintStream out;

    /**
     * Stores an instance of the directory service.
     */
    private final Directory directory;
    
    /* -------------------------------------------------- Rotation -------------------------------------------------- */
    
    /**
     * Rotates the log file.
     */
    private void rotate() {
        this.date = new Date();
        try {
            this.out = new PrintStream(new FileOutputStream(directory.getLogsDirectory().getPath() + File.separator + day.get().format(new Date()) + " " + name + ".log", true));
        } catch (FileNotFoundException exception) {
            throw new RuntimeException("Could not open the log file '" + name + "'.", exception);
        }
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new logger that writes to the file with the given name.
     * 
     * @param level the level above which messages should be logged.
     * @param name the name of the file to write the log messages to.
     * @param version the version of the code which called this logger.
     * @param directory the directory into which the log files are written.
     */
    protected DefaultLogger(Level level, String name, String version, Directory directory) {
        this.level = level;
        this.name = name;
        this.version = version;
        this.directory = directory;
        rotate();
    }
    
    /**
     * Returns a new logger that writes to the file with the given name.
     * 
     * @param level the level above which messages should be logged.
     * @param name the name of the file to write the log messages to.
     * @param version the version of the code which called this logger.
     * @param directory the directory into which the log files are written.
     *
     * @return a new logger that writes to the file with the given name.
     */
    public static DefaultLogger get(Level level, String name, String version, Directory directory) {
        return new DefaultLogger(level, name, version, directory);
    }
    
    /* -------------------------------------------------- Logging -------------------------------------------------- */
    
    @Override
    @SuppressWarnings("deprecation")
    protected synchronized void protectedLog(Level level, String tag, String message, Throwable throwable) {
        if (level.getValue() >= this.level.getValue()) {
            final Date date = new Date();
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
