package net.digitalid.utility.logging.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.exceptions.UnexpectedFailureException;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a logger that logs the messages to a file.
 * <p>
 * <em>Warning:</em> Logging from different processes to the same file may fail!
 */
@Mutable
public class FileLogger extends PrintStreamLogger {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a file logger that logs the messages to the given file.
     * 
     * @throws FileNotFoundException if the given file cannot be opened or created.
     */
    protected FileLogger(@Nonnull File file) throws FileNotFoundException {
        super(new PrintStream(new FileOutputStream(file, true)));
    }
    
    /**
     * Returns a file logger that logs the messages to the given file.
     * 
     * @throws FileNotFoundException if the given file cannot be opened or created.
     */
    @Pure
    public static @Capturable @Nonnull FileLogger with(@Nonnull File file) throws FileNotFoundException {
        Require.that(file != null).orThrow("The file may not be null.");
        
        return new FileLogger(file);
    }
    
    /**
     * Returns a file logger that logs the messages to a file at the given path.
     * This method creates missing parent directories and the file, if necessary.
     */
    @Pure
    public static @Capturable @Nonnull FileLogger with(@Nonnull String path) {
        Require.that(path != null).orThrow("The path may not be null.");
        
        final @Nonnull File file = new File(path);
        final @Nullable File directory = file.getParentFile();
        if (directory != null && !directory.exists() && !directory.mkdirs()) {
            throw UnexpectedFailureException.with("Could not create the directory $.", directory.getPath());
        }
        try {
            return new FileLogger(file);
        } catch (@Nonnull FileNotFoundException exception) {
            throw UnexpectedFailureException.with("The file $ could not be opened or created.", exception, file.getPath());
        }
    }
    
    /* -------------------------------------------------- File -------------------------------------------------- */
    
    /**
     * Sets the file to which the messages are logged.
     * 
     * @throws FileNotFoundException if the given file cannot be opened or created.
     */
    @Impure
    protected void setFile(@Nonnull File file) throws FileNotFoundException {
        Require.that(file != null).orThrow("The file may not be null.");
        
        setPrintStream(new PrintStream(new FileOutputStream(file, true)));
    }
    
}
