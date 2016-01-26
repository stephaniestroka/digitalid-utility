package net.digitalid.utility.logging.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import net.digitalid.utility.configuration.InitializationError;

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
     * @require file != null : "The given file is not null.";
     */
    protected FileLogger(File file) throws FileNotFoundException {
        super(new PrintStream(new FileOutputStream(file, true)));
    }
    
    /**
     * Returns a file logger that logs the messages to the given non-nullable file.
     * 
     * @throws FileNotFoundException if the given file cannot be opened or created.
     * 
     * @require file != null : "The given file is not null.";
     */
    public static FileLogger of(File file) throws FileNotFoundException {
        assert file != null : "The given file is not null.";
        
        return new FileLogger(file);
    }
    
    /**
     * Returns a file logger that logs the messages to a file at the given non-nullable path.
     * This method creates missing parent directories and the file, if necessary.
     * 
     * @require path != null : "The given path is not null.";
     */
    public static FileLogger of(String path) {
        assert path != null : "The given path is not null.";
        
        final File file = new File(path);
        final File directory = file.getParentFile();
        if (directory != null && !directory.exists() && !directory.mkdirs()) {
            throw InitializationError.of("Could not create the directory '" + directory.getPath() + "'.");
        }
        try {
            return new FileLogger(file);
        } catch (FileNotFoundException exception) {
            throw InitializationError.of("The file '" + file.getPath() + "' could not be opened or created.", exception);
        }
    }
    
    /* -------------------------------------------------- File -------------------------------------------------- */
    
    /**
     * Sets the non-nullable file to which the messages are logged.
     * 
     * @throws FileNotFoundException if the given file cannot be opened or created.
     * 
     * @require file != null : "The given file is not null.";
     */
    protected void setFile(File file) throws FileNotFoundException {
        assert file != null : "The given file is not null.";
        
        setPrintStream(new PrintStream(new FileOutputStream(file, true)));
    }
    
}
