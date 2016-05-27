package net.digitalid.utility.directory.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.directory.Directory;
import net.digitalid.utility.initialization.annotations.Initialize;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.logging.logger.FileLogger;
import net.digitalid.utility.logging.logger.Logger;
import net.digitalid.utility.logging.logger.PrintStreamLogger;
import net.digitalid.utility.logging.logger.StandardOutputLogger;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a logger that logs the messages to a rotating file.
 */
@Mutable
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
        return new File(Directory.getLogsDirectory().getPath() + "/" + fileFormat.get().format(new Date()) + ".log");
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
    @Impure
    private void rotate() {
        this.currentDate = new Date();
        try {
            setFile(getCurrentFile());
        } catch (@Nonnull FileNotFoundException exception) {
            throw new RuntimeException("Could not open or create the log file '" + getCurrentFile().getAbsolutePath() + "'.", exception);
        }
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a rotating file logger that logs the messages to the given non-nullable file.
     * 
     * @throws FileNotFoundException if the given file cannot be opened or created.
     */
    protected RotatingFileLogger(@Nonnull File file) throws FileNotFoundException {
        super(file);
        
        this.currentDate = new Date();
        Directory.configuration.register((configuration, oldFile, newFile) -> rotate());
    }
    
    /**
     * Returns a rotating file logger that logs the messages to the given non-nullable file.
     * 
     * @throws FileNotFoundException if the current file cannot be opened or created.
     */
    @Pure
    public static @Capturable @Nonnull RotatingFileLogger withDefaultDirectory() throws FileNotFoundException {
        return new RotatingFileLogger(getCurrentFile());
    }
    
    /* -------------------------------------------------- Logging -------------------------------------------------- */
    
    @Impure
    @Override
    @SuppressWarnings("deprecation")
    protected synchronized void log(@Nonnull Level level, @Nonnull String caller, @Nonnull String thread, @Nonnull String message, @Nullable Throwable throwable) {
        final Date date = new Date();
        if (date.getDate() != this.currentDate.getDate()) { rotate(); }
        PrintStreamLogger.super.log(level, caller, thread, message, throwable);
    }
    
    /* -------------------------------------------------- Initialization -------------------------------------------------- */
    
    /**
     * Initializes the logger with a rotating file logger.
     */
    @Impure
    @Initialize(target = Logger.class, dependencies = {Directory.class})
    public static void initialize() throws FileNotFoundException {
        if (Logger.logger.get() instanceof StandardOutputLogger) {
            Logger.logger.set(RotatingFileLogger.withDefaultDirectory());
            Log.verbose("Replaced the default standard output logger with a rotating file logger.");
        } else {
            Log.verbose("Did not replace the non-default logger with a rotating file logger.");
        }
    }
    
}
