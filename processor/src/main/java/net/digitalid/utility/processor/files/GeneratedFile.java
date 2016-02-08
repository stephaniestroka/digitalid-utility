package net.digitalid.utility.processor.files;

import javax.annotation.Nonnull;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.contracts.exceptions.PreconditionViolationException;
import net.digitalid.utility.string.QuoteString;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * Subclasses of this class make it easier to generate files during annotation processing.
 * 
 * @see JavaSourceFile
 * @see ServiceLoaderFile
 */
@Mutable
public abstract class GeneratedFile {
    
    /* -------------------------------------------------- Name -------------------------------------------------- */
    
    /**
     * Returns the name of this generated file.
     */
    @Pure
    public abstract @Nonnull String getName();
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return QuoteString.inSingle(getName());
    }
    
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
        Require.that(!written).orThrow("The generated file " + this + " has already been written.");
    }
    
    /* -------------------------------------------------- Writing -------------------------------------------------- */
    
    /**
     * Writes this generated file to the file system and returns whether it was successful.
     * <p>
     * <em>Important:</em> This method is guaranteed to be called only once and the
     * implementation should not check that this file has not already been written.
     */
    @NonWrittenRecipient
    protected abstract boolean writeOnce(); // TODO: Catch the IOException in the other write-method!
    
    /**
     * Writes this generated file to the file system and returns whether it was successful.
     */
    @NonWrittenRecipient
    public final boolean write() {
        requireNotWritten();
        
        final boolean result = writeOnce();
        this.written = true;
        return result;
    }
    
}
