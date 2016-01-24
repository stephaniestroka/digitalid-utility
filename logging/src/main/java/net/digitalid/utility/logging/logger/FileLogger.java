package net.digitalid.utility.logging.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

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
