package net.digitalid.utility.processor.generator;

import java.io.IOException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.contracts.exceptions.PreconditionViolationException;
import net.digitalid.utility.fixes.Quotes;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processor.generator.annotations.NonWrittenRecipient;
import net.digitalid.utility.rootclass.RootClass;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * Subclasses of this class generate files during annotation processing.
 * 
 * @see ServiceFileGenerator
 * @see JavaFileGenerator
 */
@Mutable
public abstract class FileGenerator extends RootClass {
    
    /* -------------------------------------------------- Name -------------------------------------------------- */
    
    /**
     * Returns the name of this generated file.
     */
    @Pure
    public abstract @Nonnull String getName();
    
    /* -------------------------------------------------- Written -------------------------------------------------- */
    
    private boolean written = false;
    
    /**
     * Returns whether this generated file has already been written.
     */
    @Pure
    public boolean isWritten() {
        return written;
    }
    
    /**
     * Throws a {@link PreconditionViolationException} if this file has already been written.
     */
    @Pure
    protected void requireNotWritten() {
        Require.that(!written).orThrow("The generated file $ has already been written.", getName());
    }
    
    /* -------------------------------------------------- Writing -------------------------------------------------- */
    
    /**
     * Writes the generated file to the file system.
     * This method is guaranteed to be called only once.
     */
    @Impure
    @NonWrittenRecipient
    protected abstract void writeOnce() throws IOException;
    
    /**
     * Writes the generated file to the file system and returns whether it was successful.
     */
    @Impure
    @NonWrittenRecipient
    public final boolean write() {
        requireNotWritten();
        
        try {
            writeOnce();
        } catch (@Nonnull IOException exception) {
            ProcessingLog.error("A problem occurred while generating the file $.", getName());
            Log.error("Problem:", exception);
            return false;
        }
        
        ProcessingLog.information("Generated the file $.", getName());
        this.written = true;
        return true;
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean equals(@Nullable Object object) {
        if (object == this) { return true; }
        if (object == null || !(object instanceof FileGenerator)) { return false; }
        final @Nonnull FileGenerator that = (FileGenerator) object;
        return this.getName().equals(that.getName());
    }
    
    @Pure
    @Override
    public int hashCode() {
        return getName().hashCode();
    }
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return "FileGenerator(name: " + Quotes.inDouble(getName()) + ")";
    }
    
}
