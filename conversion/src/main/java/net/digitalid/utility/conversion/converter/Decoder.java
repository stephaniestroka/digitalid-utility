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
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.collaboration.annotations.TODO;
import net.digitalid.utility.collaboration.enumerations.Author;
import net.digitalid.utility.functional.failable.FailableProducer;
import net.digitalid.utility.logging.exceptions.ExternalException;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.size.Size;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * The decoder is used to recover values from a data source like an SQL database or an XDF block.
 * 
 * @see Encoder
 */
@Mutable
@TODO(task = "Use 'decode' instead of 'get' as the method prefix (because impure getters are confusing)?", date = "2016-12-26", author = Author.KASPAR_ETTER)
public interface Decoder<EXCEPTION extends ExternalException> {
    
    /* -------------------------------------------------- Representation -------------------------------------------------- */
    
    /**
     * Returns the representation used for the decoding.
     */
    @Pure
    public @Nonnull Representation getRepresentation();
    
    /* -------------------------------------------------- Getters -------------------------------------------------- */
    
    /**
     * Returns nothing and moves forward to the next result.
     */
    @Impure
    public void getEmpty() throws EXCEPTION;
    
    /**
     * Returns the boolean value
     */
    @Impure
    public boolean getBoolean() throws EXCEPTION;
    
    /**
     * Returns the byte value and moves forward to the next result.
     */
    @Impure
    public byte getInteger08() throws EXCEPTION;
    
    /**
     * Returns the short value and moves forward to the next result.
     */
    @Impure
    public short getInteger16() throws EXCEPTION;
    
    /**
     * Returns the int value and moves forward to the next result.
     */
    @Impure
    public int getInteger32() throws EXCEPTION;
    
    /**
     * Returns the long value and moves forward to the next result.
     */
    @Impure
    public long getInteger64() throws EXCEPTION;
    
    /**
     * Returns the integer value and moves forward to the next result.
     */
    @Impure
    public @Nullable BigInteger getInteger() throws EXCEPTION;
    
    /**
     * Returns the float value and moves forward to the next result.
     */
    @Impure
    public float getDecimal32() throws EXCEPTION;
    
    /**
     * Returns the double value and moves forward to the next result.
     */
    @Impure
    public double getDecimal64() throws EXCEPTION;
    
    /**
     * Returns the char value and moves forward to the next result.
     */
    @Impure
    public char getString01() throws EXCEPTION;
    
    /**
     * Returns the string value and moves forward to the next result.
     */
    @Impure
    public @Nullable @MaxSize(64) String getString64() throws EXCEPTION;
    
    /**
     * Returns the string value and moves forward to the next result.
     */
    @Impure
    public @Nullable String getString() throws EXCEPTION;
    
    /**
     * Returns the binary value and moves forward to the next result.
     */
    @Impure
    public @Nullable @Size(16) byte[] getBinary128() throws EXCEPTION;
    
    /**
     * Returns the binary value and moves forward to the next result.
     */
    @Impure
    public @Nullable @Size(32) byte[] getBinary256() throws EXCEPTION;
    
    /**
     * Returns the binary value and moves forward to the next result.
     */
    @Impure
    public @Nullable byte[] getBinary() throws EXCEPTION;
    
    @Impure
    public <T> @Nonnull List<T> getList(@Nonnull FailableProducer<T, EXCEPTION> function) throws EXCEPTION;
    
    @Impure
    public <T> @Nonnull T[] getArray(@Nonnull FailableProducer<T, EXCEPTION> function) throws EXCEPTION;
    
    @Impure
    public <T> @Nonnull Set<T> getSet(@Nonnull FailableProducer<T, EXCEPTION> function) throws EXCEPTION;
    
    @Impure
    public <K, V> @Nonnull Map<K, V> getMap(@Nonnull FailableProducer<K, EXCEPTION> keyFunction, @Nonnull FailableProducer<V, EXCEPTION> valueFunction) throws EXCEPTION;
    
    @Impure
    public void setDecryptionCipher(@Nonnull Cipher cipher) throws EXCEPTION;
    
    @Impure
    public void popDecryptionCipher() throws EXCEPTION;
    
    @Impure
    public void setDecompression(@Nonnull Inflater inflater) throws EXCEPTION;
    
    @Impure
    public void popDecompression() throws EXCEPTION;
    
    @Impure
    public void setSignatureDigest(@Nonnull MessageDigest digest) throws EXCEPTION;
    
    @Impure
    public @Nonnull DigestInputStream popSignatureDigest() throws EXCEPTION;
    
}
