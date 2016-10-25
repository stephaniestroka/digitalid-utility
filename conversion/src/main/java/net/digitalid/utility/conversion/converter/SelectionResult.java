package net.digitalid.utility.conversion.converter;

import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.Inflater;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.Cipher;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.collaboration.annotations.TODO;
import net.digitalid.utility.collaboration.enumerations.Author;
import net.digitalid.utility.functional.failable.FailableProducer;
import net.digitalid.utility.logging.exceptions.ExternalException;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.size.Size;

/**
 * The selection result is used to recover values from another data source, e.g. an SQL data base or an XDF block.
 */
@TODO(task = "Rename to Decoder.", date = "2016-10-13", author = Author.KASPAR_ETTER)
public interface SelectionResult<X extends ExternalException> {
    
    /* -------------------------------------------------- Getters -------------------------------------------------- */
    
    /**
     * Returns nothing and moves forward to the next result.
     */
    @Impure
    public void getEmpty() throws X;
    
    /**
     * Returns the boolean value
     */
    @Impure
    public boolean getBoolean() throws X;
    
    /**
     * Returns the byte value and moves forward to the next result.
     */
    @Impure
    public byte getInteger08() throws X;
    
    /**
     * Returns the short value and moves forward to the next result.
     */
    @Impure
    public short getInteger16() throws X;
    
    /**
     * Returns the int value and moves forward to the next result.
     */
    @Impure
    public int getInteger32() throws X;
    
    /**
     * Returns the long value and moves forward to the next result.
     */
    @Impure
    public long getInteger64() throws X;
    
    /**
     * Returns the integer value and moves forward to the next result.
     */
    @Impure
    public @Nullable BigInteger getInteger() throws X;
    
    /**
     * Returns the float value and moves forward to the next result.
     */
    @Impure
    public float getDecimal32() throws X;
    
    /**
     * Returns the double value and moves forward to the next result.
     */
    @Impure
    public double getDecimal64() throws X;
    
    /**
     * Returns the char value and moves forward to the next result.
     */
    @Impure
    public char getString01() throws X;
    
    /**
     * Returns the string value and moves forward to the next result.
     */
    @Impure
    public @Nullable @MaxSize(64) String getString64() throws X;
    
    /**
     * Returns the string value and moves forward to the next result.
     */
    @Impure
    public @Nullable String getString() throws X;
    
    /**
     * Returns the binary value and moves forward to the next result.
     */
    @Impure
    public @Nullable @Size(16) byte[] getBinary128() throws X;
    
    /**
     * Returns the binary value and moves forward to the next result.
     */
    @Impure
    public @Nullable @Size(32) byte[] getBinary256() throws X;
    
    /**
     * Returns the binary value and moves forward to the next result.
     */
    @Impure
    public @Nullable byte[] getBinary() throws X;
    
    @Impure
    public <T> @Nonnull List<T> getList(@Nonnull FailableProducer<T, X> function) throws X;
    
    @Impure
    public <T> @Nonnull T[] getArray(@Nonnull FailableProducer<T, X> function) throws X;
    
    @Impure
    public <T> @Nonnull Set<T> getSet(@Nonnull FailableProducer<T, X> function) throws X;
    
    @Impure
    public <K, V> @Nonnull Map<K, V> getMap(@Nonnull FailableProducer<K, X> keyFunction, @Nonnull FailableProducer<V, X> valueFunction) throws X;
    
    @Impure
    public void setDecryptionCipher(@Nonnull Cipher cipher) throws X;
    
    @Impure
    public void popDecryptionCipher() throws X;
    
    @Impure
    public void setDecompression(@Nonnull Inflater inflater) throws X;
    
    @Impure
    public void popDecompression() throws X;
    
    @Impure
    public void setSignatureDigest(@Nonnull MessageDigest digest) throws X;
    
    @Impure
    public @Nonnull DigestInputStream popSignatureDigest() throws X;
    
}
