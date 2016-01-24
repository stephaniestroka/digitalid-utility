package net.digitalid.utility.directory.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Nonnull;

import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.configuration.ConfigurationObserver;
import net.digitalid.utility.directory.Directory;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.logging.logger.FileLogger;
import net.digitalid.utility.logging.logger.Logger;
import net.digitalid.utility.validation.state.Pure;

/**
 * This class implements a logger that logs the messages to a rotating file.
 */
public class RotatingFileLogger extends FileLogger {
    
    /* -------------------------------------------------- File Format -------------------------------------------------- */
    
    /**
     * Stores the date format of the file name.
     */
    private static final @Nonnull ThreadLocal<DateFormat> fileFormat = new ThreadLocal<DateFormat>() {
        @Override protected @Nonnull DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    
    /* -------------------------------------------------- Current File -------------------------------------------------- */
    
    /**
     * Returns the current file to log the messages to.
     */
    @Pure
    private static @Nonnull File getCurrentFile() {
        return new File(Directory.getLogsDirectory().getPath() + File.separator + fileFormat.get().format(new Date()) + ".log");
    }
    
    /* -------------------------------------------------- Current Date -------------------------------------------------- */
    
    /**
     * Stores the date of the currently open log file.
     */
    private @Nonnull Date currentDate;
    
    /* -------------------------------------------------- Rotation -------------------------------------------------- */
    
    /**
     * Rotates the log file.
     */
    private void rotate() {
        this.currentDate = new Date();
        try {
            setFile(getCurrentFile());
        } catch (@Nonnull FileNotFoundException exception) {
            throw new RuntimeException("Could not open or create the log file '" + getCurrentFile().getAbsolutePath() + "'.", exception);
        }
    }
    
    /* -------------------------------------------------- Observer -------------------------------------------------- */
    
    /**
     * Stores the observer, which changes the log file to the new root directory.
     */
    private final @Nonnull ConfigurationObserver<File> observer = new ConfigurationObserver<File>() {
        @Override public void replaced(@Nonnull Configuration<File> configuration, @Nonnull File oldFile, @Nonnull File newFile) {
            rotate();
        }
    };
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a rotating file logger that logs the messages to the given non-nullable file.
     * 
     * @throws FileNotFoundException if the given file cannot be opened or created.
     */
    protected RotatingFileLogger(@Nonnull File file) throws FileNotFoundException {
        super(file);
        
        this.currentDate = new Date();
        Directory.configuration.register(observer);
    }
    
    /**
     * Returns a rotating file logger that logs the messages to the given non-nullable file.
     * 
     * @throws FileNotFoundException if the current file cannot be opened or created.
     */
    @Pure
    public static @Nonnull RotatingFileLogger of() throws FileNotFoundException {
        return new RotatingFileLogger(getCurrentFile());
    }
    
    /* -------------------------------------------------- Logging -------------------------------------------------- */
    
    @Override
    @SuppressWarnings("deprecation")
    protected synchronized void log(Level level, String tag, String message, Throwable throwable) {
        final Date date = new Date();
        if (date.getDate() != this.currentDate.getDate()) { rotate(); }
        super.log(level, tag, message, throwable);
    }
    
    /* -------------------------------------------------- Initialization -------------------------------------------------- */
    
    /**
     * Initializes the logger and registers for modifications of the directory configuration.
     */
    // @Initialize(Logger.class, dependencies = {Directory.class})
    public static void initialize() throws FileNotFoundException {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(@Nonnull Thread thread, @Nonnull Throwable throwable) {
                Log.error("The following issue caused this thread to terminate.", throwable);
            }
        });
        
        Logger.configuration.set(RotatingFileLogger.of());
    }
    
}
