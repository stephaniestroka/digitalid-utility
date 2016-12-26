package net.digitalid.utility.conversion.converter;

import java.io.InputStream;
import java.math.BigInteger;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.collaboration.annotations.TODO;
import net.digitalid.utility.collaboration.enumerations.Author;
import net.digitalid.utility.conversion.converter.types.CustomType;
import net.digitalid.utility.functional.failable.FailableUnaryFunction;
import net.digitalid.utility.logging.exceptions.ExternalException;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.size.Size;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * The encoder is used to convert values to a data sink like an SQL database or an XDF block.
 * 
 * @see Decoder
 */
@Mutable
@TODO(task = "Use 'encode' instead of 'set' as the method prefix?", date = "2016-12-26", author = Author.KASPAR_ETTER)
public interface Encoder<EXCEPTION extends ExternalException> {
    
    /* -------------------------------------------------- Representation -------------------------------------------------- */
    
    /**
     * Returns the representation used for the encoding.
     */
    @Pure
    public @Nonnull Representation getRepresentation();
    
    /* -------------------------------------------------- Setters -------------------------------------------------- */
    
    /**
     * Collects an empty value and processes it for conversion.
     */
    @Impure
    public Integer setEmpty() throws EXCEPTION;
    
    /**
     * Collects a boolean value and processes it for conversion.
     */
    @Impure
    public Integer setNullableBoolean(@Nullable Boolean value) throws EXCEPTION;
    
    /**
     * Collects a byte value and processes it for conversion.
     */
    @Impure
    public Integer setNullableInteger08(@Nullable Byte value) throws EXCEPTION;
    
    /**
     * Collects a short value and processes it for conversion.
     */
    @Impure
    public Integer setNullableInteger16(@Nullable Short value) throws EXCEPTION;
    
    /**
     * Collects an integer value and processes it for conversion.
     */
    @Impure
    public Integer setNullableInteger32(@Nullable Integer value) throws EXCEPTION;
    
    /**
     * Collects a long value and processes it for conversion.
     */
    @Impure
    public Integer setNullableInteger64(@Nullable Long value) throws EXCEPTION;
    
    /**
     * Collects a big integer value and processes it for conversion.
     */
    @Impure
    public Integer setNullableInteger(@Nullable BigInteger value) throws EXCEPTION;
    
    /**
     * Collects a float value and processes it for conversion.
     */
    @Impure
    public Integer setNullableDecimal32(@Nullable Float value) throws EXCEPTION;
    
    /**
     * Collects a double value and processes it for conversion.
     */
    @Impure
    public Integer setNullableDecimal64(@Nullable Double value) throws EXCEPTION;
    
    /**
     * Collects a character value and processes it for conversion.
     */
    @Impure
    public Integer setNullableString01(@Nullable Character value) throws EXCEPTION;
    
    /**
     * Collects a string value of max size 64 and processes it for conversion.
     */
    @Impure
    public Integer setNullableString64(@Nullable @MaxSize(64) String value) throws EXCEPTION;
    
    /**
     * Collects a string value and processes it for conversion.
     */
    @Impure
    public Integer setNullableString(@Nullable String value) throws EXCEPTION;
    
    /**
     * Collects a byte array value of size 16 and processes it for conversion.
     */
    @Impure
    public Integer setNullableBinary128(@Nullable @Size(16) byte[] value) throws EXCEPTION;
    
    /**
     * Collects a byte array value of size 32 and processes it for conversion.
     */
    @Impure
    public Integer setNullableBinary256(@Nullable @Size(32) byte[] value) throws EXCEPTION;
    
    /**
     * Collects a byte array value and processes it for conversion.
     */
    @Impure
    public Integer setNullableBinary(@Nullable byte[] value) throws EXCEPTION;
    
    /**
     * Collects an input stream and processes it for conversion.
     */
    @Impure
    public Integer setNullableBinaryStream(@Nullable InputStream stream, int length) throws EXCEPTION;
    
    /**
     * Collects a list and processes it for conversion.
     */
    @Impure
    public <T> Integer setNullableList(@Nullable List<@Nullable T> list, @Nonnull FailableUnaryFunction<@Nullable T, Integer, EXCEPTION> entityCollector) throws EXCEPTION;
    
    /**
     * Collects an array and processes it for conversion.
     */
    @Impure
    public <T> Integer setNullableArray(@Nullable @NullableElements T[] value, @Nonnull FailableUnaryFunction<@Nullable T, Integer, EXCEPTION> entityCollector) throws EXCEPTION;
    
    /**
     * Collects a set and processes it for conversion.
     */
    @Impure
    public <T> Integer setNullableSet(@Nullable Set<@Nullable T> value, @Nonnull FailableUnaryFunction<@Nullable T, Integer, EXCEPTION> entityCollector) throws EXCEPTION;
    
    /**
     * Collects a map and processes it for conversion.
     */
    @Impure
    public <K, V> Integer setNullableMap(@Nullable Map<@Nullable K, @Nullable V> value, @Nonnull FailableUnaryFunction<@Nullable K, Integer, EXCEPTION> genericTypeKey, @Nonnull FailableUnaryFunction<@Nullable V, Integer, EXCEPTION> genericTypeValue) throws EXCEPTION;
    
    /* -------------------------------------------------- Null -------------------------------------------------- */
    
    /**
     * Collects a null value and processes it for conversion.
     */
    @Impure
    public Integer setNull(@Nonnull CustomType customType) throws EXCEPTION;
    
    /**
     * Marks the value collection for encryption. This means, all following data will be encrypted with the given cipher.
     */
    @Impure
    public void setEncryptionCipher(@Nonnull Cipher cipher);
    
    /**
     * Marks the end of the encrypted content.
     */
    @Impure
    public @Nonnull CipherOutputStream popEncryptionCipher() throws EXCEPTION;
    
    /**
     * Marks the value collection for hashing so that the content can be signed. This means, all following data will be updated with the given message digest.
     */
    @Impure
    public void setSignatureDigest(@Nonnull MessageDigest digest);
    
    /**
     * Marks the end of the signed content.
     */
    @Impure
    public @Nonnull DigestOutputStream popSignatureDigest();
    
    /**
     * Marks the value collection for compression. This means, all following data will be compressed with the given deflater.
     */
    @Impure
    public void setCompression(@Nonnull Deflater deflater);
    
    /**
     * Marks the end of the compressed content.
     */
    @Impure
    public @Nonnull DeflaterOutputStream popCompression() throws EXCEPTION;
    
}
