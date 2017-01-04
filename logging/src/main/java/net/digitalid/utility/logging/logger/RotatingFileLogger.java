package net.digitalid.utility.logging.logger;

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
import net.digitalid.utility.exceptions.UncheckedExceptionBuilder;
import net.digitalid.utility.file.Files;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.validation.annotations.file.existence.ExistentParent;
import net.digitalid.utility.validation.annotations.file.kind.Normal;
import net.digitalid.utility.validation.annotations.file.path.Absolute;
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
    private static final @Nonnull ThreadLocal<@Nonnull DateFormat> fileFormat = new ThreadLocal<DateFormat>() {
        @Override protected @Nonnull DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    
    /* -------------------------------------------------- Current File -------------------------------------------------- */
    
    /**
     * Returns the current file to log the messages to.
     */
    @Pure
    private static @Nonnull @Absolute @Normal @ExistentParent File getCurrentFile() {
        return Files.relativeToConfigurationDirectory("logs/" + fileFormat.get().format(new Date()) + ".log");
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
            throw UncheckedExceptionBuilder.withCause(exception).build();
        }
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a rotating file logger that logs the messages to files in the default directory.
     * 
     * @throws FileNotFoundException if the current file cannot be opened or created.
     */
    protected RotatingFileLogger() throws FileNotFoundException {
        super(getCurrentFile());
        
        this.currentDate = new Date();
        Files.directory.register((configuration, oldFile, newFile) -> rotate());
    }
    
    /**
     * Returns a rotating file logger that logs the messages to files in the default directory.
     * 
     * @throws FileNotFoundException if the current file cannot be opened or created.
     */
    @Pure
    public static @Capturable @Nonnull RotatingFileLogger withDefaultDirectory() throws FileNotFoundException {
        return new RotatingFileLogger();
    }
    
    /* -------------------------------------------------- Logging -------------------------------------------------- */
    
    @Impure
    @Override
    @SuppressWarnings("deprecation")
    protected void log(@Nonnull Level level, @Nonnull String caller, @Nonnull String thread, @Nonnull String message, @Nullable Throwable throwable) {
        final Date date = new Date();
        if (date.getDate() != this.currentDate.getDate()) { rotate(); }
        super.log(level, caller, thread, message, throwable);
    }
    
}
