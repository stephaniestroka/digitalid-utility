package net.digitalid.utility.system.writer;

import java.io.IOException;
import java.io.Writer;
import javax.annotation.Nonnull;

/**
 * This class extends the {@link java.io.BufferedWriter} with a {@link #writeLine(java.lang.String)} method.
 */
public class BufferedWriter extends java.io.BufferedWriter {
    
    /**
     * Creates a buffered character-output stream with a default-sized buffer.
     *
     * @param writer the writer to write to.
     */
    public BufferedWriter(@Nonnull Writer writer) {
        super(writer);
    }
    
    /**
     * Writes the given line, makes a new line and flushes the buffer.
     * 
     * @param line the line to write.
     * 
     * @throws IOException if the line cannot be written.
     */
    public void writeLine(@Nonnull String line) throws IOException {
        write(line);
        newLine();
        flush();
    }
    
}
