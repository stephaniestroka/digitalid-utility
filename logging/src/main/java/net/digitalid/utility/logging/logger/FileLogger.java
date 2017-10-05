package net.digitalid.utility.logging.logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Properties;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.validation.annotations.file.existence.ExistentParent;
import net.digitalid.utility.validation.annotations.file.kind.Normal;
import net.digitalid.utility.validation.annotations.file.permission.Writable;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a logger that logs the messages to a file.
 * <p>
 * <em>Warning:</em> Logging from different processes to the same file may fail!
 */
@Mutable
public class FileLogger extends PrintStreamLogger {
    
    /* -------------------------------------------------- Print Stream -------------------------------------------------- */
    
    /**
     * Sets the file to which the messages are printed.
     * 
     * @throws FileNotFoundException if the given file cannot be opened or created.
     */
    @Impure
    protected void setFile(@Captured @Nonnull @Normal @Writable @ExistentParent File file) throws FileNotFoundException {
        final boolean fileDidNotExist = !file.exists();
        final @Nonnull PrintStream printStream = new PrintStream(new FileOutputStream(file, true));
        
        if (fileDidNotExist) {
            final @Nonnull Properties properties = System.getProperties();
            printStream.println();
            printStream.println(properties.getProperty("java.runtime.name") + " " + properties.getProperty("java.runtime.version"));
            printStream.println(properties.getProperty("java.vm.name") + " " + properties.getProperty("java.vm.version"));
            printStream.println(properties.getProperty("os.name") + " " + properties.getProperty("os.version") + " on " + properties.getProperty("os.arch"));
            printStream.println();
        }
        
        setPrintStream(printStream);
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a file logger that logs the messages to the given file.
     * 
     * @throws FileNotFoundException if the given file cannot be opened or created.
     */
    protected FileLogger(@Captured @Nonnull @Normal @Writable @ExistentParent File file) throws FileNotFoundException {
        super(new PrintStream(new ByteArrayOutputStream(0))); // Dummy print stream because setPrintStream(file) cannot be called here.
        
        setFile(file);
    }
    
    /**
     * Returns a file logger that logs the messages to the given file.
     * 
     * @throws FileNotFoundException if the given file cannot be opened or created.
     */
    @Pure
    public static @Capturable @Nonnull FileLogger with(@Captured @Nonnull @Normal @Writable @ExistentParent File file) throws FileNotFoundException {
        return new FileLogger(file);
    }
    
}
