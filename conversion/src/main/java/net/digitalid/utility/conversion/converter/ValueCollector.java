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
import net.digitalid.utility.conversion.converter.types.CustomType;
import net.digitalid.utility.functional.failable.FailableUnaryFunction;
import net.digitalid.utility.logging.exceptions.ExternalException;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.size.Size;

/**
 *
 */
public interface ValueCollector<X extends ExternalException> {
    
    /* -------------------------------------------------- Setters -------------------------------------------------- */
    
    /**
     * Collects an empty value and processes it for conversion.
     */
    @Impure
    public Integer setEmpty() throws X;
    
    /**
     * Collects a boolean value and processes it for conversion.
     */
    @Impure
    public Integer setNullableBoolean(@Nullable Boolean value) throws X;
    
    /**
     * Collects a byte value and processes it for conversion.
     */
    @Impure
    public Integer setNullableInteger08(@Nullable Byte value) throws X;
    
    /**
     * Collects a short value and processes it for conversion.
     */
    @Impure
    public Integer setNullableInteger16(@Nullable Short value) throws X;
    
    /**
     * Collects an integer value and processes it for conversion.
     */
    @Impure
    public Integer setNullableInteger32(@Nullable Integer value) throws X;
    
    /**
     * Collects a long value and processes it for conversion.
     */
    @Impure
    public Integer setNullableInteger64(@Nullable Long value) throws X;
    
    /**
     * Collects a big integer value and processes it for conversion.
     */
    @Impure
    public Integer setNullableInteger(@Nullable BigInteger value) throws X;
    
    /**
     * Collects a float value and processes it for conversion.
     */
    @Impure
    public Integer setNullableDecimal32(@Nullable Float value) throws X;
    
    /**
     * Collects a double value and processes it for conversion.
     */
    @Impure
    public Integer setNullableDecimal64(@Nullable Double value) throws X;
    
    /**
     * Collects a character value and processes it for conversion.
     */
    @Impure
    public Integer setNullableString01(@Nullable Character value) throws X;
    
    /**
     * Collects a string value of max size 64 and processes it for conversion.
     */
    @Impure
    public Integer setNullableString64(@Nullable @MaxSize(64) String value) throws X;
    
    /**
     * Collects a string value and processes it for conversion.
     */
    @Impure
    public Integer setNullableString(@Nullable String value) throws X;
    
    /**
     * Collects a byte array value of size 16 and processes it for conversion.
     */
    @Impure
    public Integer setNullableBinary128(@Nullable @Size(16) byte[] value) throws X;
    
    /**
     * Collects a byte array value of size 32 and processes it for conversion.
     */
    @Impure
    public Integer setNullableBinary256(@Nullable @Size(32) byte[] value) throws X;
    
    /**
     * Collects a byte array value and processes it for conversion.
     */
    @Impure
    public Integer setNullableBinary(@Nullable byte[] value) throws X;
    
    /**
     * Collects an input stream and processes it for conversion.
     */
    @Impure
    public Integer setNullableBinaryStream(@Nullable InputStream stream, int length) throws X;
    
    /**
     * Collects a list and processes it for conversion.
     */
    @Impure
    public <T> Integer setNullableList(@Nullable List<@Nullable T> list, @Nonnull FailableUnaryFunction<@Nullable T, Integer, X> entityCollector) throws X;
    
    /**
     * Collects an array and processes it for conversion.
     */
    @Impure
    public <T> Integer setNullableArray(@Nullable @NullableElements T[] value, @Nonnull FailableUnaryFunction<@Nullable T, Integer, X> entityCollector) throws X;
    
    /**
     * Collects a set and processes it for conversion.
     */
    @Impure
    public <T> Integer setNullableSet(@Nullable Set<@Nullable T> value, @Nonnull FailableUnaryFunction<@Nullable T, Integer, X> entityCollector) throws X;
    
    /**
     * Collects a map and processes it for conversion.
     */
    @Impure
    public <K, V> Integer setNullableMap(@Nullable Map<@Nullable K, @Nullable V> value, @Nonnull FailableUnaryFunction<@Nullable K, Integer, X> genericTypeKey, @Nonnull FailableUnaryFunction<@Nullable V, Integer, X> genericTypeValue) throws X;
    
    /* -------------------------------------------------- Null -------------------------------------------------- */
    
    /**
     * Collects a null value and processes it for conversion.
     */
    @Impure
    public Integer setNull(@Nonnull CustomType customType) throws X;
    
    /**
     * Marks the value collection for encryption. This means, all following data will be encrypted with the given cipher.
     */
    @Impure
    public void setEncryptionCipher(@Nonnull Cipher cipher);
    
    /**
     * Marks the end of the encrypted content.
     */
    @Impure
    public @Nonnull CipherOutputStream popEncryptionCipher() throws X;
    
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
    public @Nonnull DeflaterOutputStream popCompression() throws X;
    
}
