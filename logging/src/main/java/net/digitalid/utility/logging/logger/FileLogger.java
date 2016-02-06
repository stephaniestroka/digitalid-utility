package net.digitalid.utility.logging.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.exceptions.UnexpectedFailureException;

/**
 * This class implements a logger that logs the messages to a file.
 * <p>
 * <em>Warning:</em> Logging from different processes to the same file may fail!
 */
public class FileLogger extends PrintStreamLogger {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a file logger that logs the messages to the given non-nullable file.
     * 
     * @throws FileNotFoundException if the given file cannot be opened or created.
     * 
     * @require file != null : "The file may not be null.";
     */
    protected FileLogger(File file) throws FileNotFoundException {
        super(new PrintStream(new FileOutputStream(file, true)));
    }
    
    /**
     * Returns a file logger that logs the messages to the given non-nullable file.
     * 
     * @throws FileNotFoundException if the given file cannot be opened or created.
     * 
     * @require file != null : "The file may not be null.";
     */
    public static FileLogger with(File file) throws FileNotFoundException {
        Require.that(file != null).orThrow("The file may not be null.");
        
        return new FileLogger(file);
    }
    
    /**
     * Returns a file logger that logs the messages to a file at the given non-nullable path.
     * This method creates missing parent directories and the file, if necessary.
     * 
     * @require path != null : "The path may not be null.";
     */
    public static FileLogger with(String path) {
        Require.that(path != null).orThrow("The path may not be null.");
        
        final File file = new File(path);
        final File directory = file.getParentFile();
        if (directory != null && !directory.exists() && !directory.mkdirs()) {
            throw UnexpectedFailureException.with("Could not create the directory '" + directory.getPath() + "'.");
        }
        try {
            return new FileLogger(file);
        } catch (FileNotFoundException exception) {
            throw UnexpectedFailureException.with("The file '" + file.getPath() + "' could not be opened or created.", exception);
        }
    }
    
    /* -------------------------------------------------- File -------------------------------------------------- */
    
    /**
     * Sets the non-nullable file to which the messages are logged.
     * 
     * @throws FileNotFoundException if the given file cannot be opened or created.
     * 
     * @require file != null : "The file may not be null.";
     */
    protected void setFile(File file) throws FileNotFoundException {
        Require.that(file != null).orThrow("The file may not be null.");
        
        setPrintStream(new PrintStream(new FileOutputStream(file, true)));
    }
    
}
