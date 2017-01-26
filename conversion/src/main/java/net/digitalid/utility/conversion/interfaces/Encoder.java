package net.digitalid.utility.conversion.interfaces;

import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Map;
import java.util.zip.Deflater;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.Cipher;

import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.conversion.enumerations.Representation;
import net.digitalid.utility.conversion.exceptions.ConnectionException;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.validation.annotations.method.Ensures;
import net.digitalid.utility.validation.annotations.method.Requires;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.size.Size;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * An encoder encodes values to a data sink like an SQL database or an XDF stream.
 * 
 * @see Decoder
 */
@Mutable
public interface Encoder<@Unspecifiable EXCEPTION extends ConnectionException> extends AutoCloseable {
    
    /* -------------------------------------------------- Representation -------------------------------------------------- */
    
    /**
     * Returns the representation which is used to encode the values.
     */
    @Pure
    public @Nonnull Representation getRepresentation();
    
    /* -------------------------------------------------- Objects -------------------------------------------------- */
    
    /**
     * Encodes the given non-nullable object with the given converter.
     */
    @Impure
    public <@Unspecifiable TYPE> void encodeObject(@Nonnull Converter<TYPE, ?> converter, @NonCaptured @Unmodified @Nonnull TYPE object) throws EXCEPTION;
    
    /**
     * Encodes the given nullable object with the given converter.
     */
    @Impure
    public <@Unspecifiable TYPE> void encodeNullableObject(@Nonnull Converter<TYPE, ?> converter, @NonCaptured @Unmodified @Nullable TYPE object) throws EXCEPTION;
    
    /* -------------------------------------------------- Values -------------------------------------------------- */
    
    /**
     * Encodes the given boolean value.
     */
    @Impure
    public void encodeBoolean(boolean value) throws EXCEPTION;
    
    /**
     * Encodes the given byte value.
     */
    @Impure
    public void encodeInteger08(byte value) throws EXCEPTION;
    
    /**
     * Encodes the given short value.
     */
    @Impure
    public void encodeInteger16(short value) throws EXCEPTION;
    
    /**
     * Encodes the given int value.
     */
    @Impure
    public void encodeInteger32(int value) throws EXCEPTION;
    
    /**
     * Encodes the given long value.
     */
    @Impure
    public void encodeInteger64(long value) throws EXCEPTION;
    
    /**
     * Encodes the given big integer value.
     */
    @Impure
    public void encodeInteger(@Nonnull BigInteger value) throws EXCEPTION;
    
    /**
     * Encodes the given float value.
     */
    @Impure
    public void encodeDecimal32(float value) throws EXCEPTION;
    
    /**
     * Encodes the given double value.
     */
    @Impure
    public void encodeDecimal64(double value) throws EXCEPTION;
    
    /**
     * Encodes the given character value.
     */
    @Impure
    public void encodeString01(char value) throws EXCEPTION;
    
    /**
     * Encodes the given string of at most 64 characters.
     */
    @Impure
    public void encodeString64(@Nonnull @MaxSize(64) String string) throws EXCEPTION;
    
    /**
     * Encodes the given string.
     */
    @Impure
    public void encodeString(@Nonnull String string) throws EXCEPTION;
    
    /**
     * Encodes the given byte array of size 16.
     */
    @Impure
    public void encodeBinary128(@Nonnull @Size(16) byte[] bytes) throws EXCEPTION;
    
    /**
     * Encodes the given byte array of size 32.
     */
    @Impure
    public void encodeBinary256(@Nonnull @Size(32) byte[] bytes) throws EXCEPTION;
    
    /**
     * Encodes the given byte array.
     */
    @Impure
    public void encodeBinary(@Nonnull byte[] bytes) throws EXCEPTION;
    
    /**
     * Encodes the bytes from the given input stream for the given length.
     */
    @Impure
    public void encodeBinaryStream(@Nonnull InputStream inputStream, int length) throws EXCEPTION;
    
    /* -------------------------------------------------- Collections -------------------------------------------------- */
    
    /**
     * Encodes the non-nullable elements of given iterable with the given converter in the given order.
     */
    @Impure
    public <@Unspecifiable TYPE> void encodeOrderedIterable(@Nonnull Converter<TYPE, ?> converter, @Nonnull FiniteIterable<@Nonnull TYPE> iterable) throws EXCEPTION;
    
    /**
     * Encodes the nullable elements of given iterable with the given converter in the given order.
     */
    @Impure
    public <@Unspecifiable TYPE> void encodeOrderedIterableWithNullableElements(@Nonnull Converter<TYPE, ?> converter, @Nonnull FiniteIterable<@Nullable TYPE> iterable) throws EXCEPTION;
    
    /**
     * Encodes the non-nullable elements of the given iterable with the given converter without preserving the order.
     */
    @Impure
    public <@Unspecifiable TYPE> void encodeUnorderedIterable(@Nonnull Converter<TYPE, ?> converter, @Nonnull FiniteIterable<@Nonnull TYPE> iterable) throws EXCEPTION;
    
    /**
     * Encodes the nullable elements of the given iterable with the given converter without preserving the order.
     */
    @Impure
    public <@Unspecifiable TYPE> void encodeUnorderedIterableWithNullableElements(@Nonnull Converter<TYPE, ?> converter, @Nonnull FiniteIterable<@Nullable TYPE> iterable) throws EXCEPTION;
    
    /**
     * Encodes the non-nullable key-value pairs of the given map with the given converters.
     */
    @Impure
    public <@Unspecifiable KEY, @Unspecifiable VALUE> void encodeMap(@Nonnull Converter<KEY, ?> keyConverter, @Nonnull Converter<VALUE, ?> valueConverter, @Nonnull Map<@Nonnull KEY, @Nonnull VALUE> map) throws EXCEPTION;
    
    /**
     * Encodes the nullable key-value pairs of the given map with the given converters.
     */
    @Impure
    public <@Unspecifiable KEY, @Unspecifiable VALUE> void encodeMapWithNullableValues(@Nonnull Converter<KEY, ?> keyConverter, @Nonnull Converter<VALUE, ?> valueConverter, @Nonnull Map<@Nullable KEY, @Nullable VALUE> map) throws EXCEPTION;
    
    /* -------------------------------------------------- Hashing -------------------------------------------------- */
    
    /**
     * Returns whether this encoder is currently hashing the encoded values.
     */
    @Pure
    public boolean isHashing();
    
    /**
     * Starts hashing all encodings with the given digest until you {@link #stopHashing() stop hashing}.
     */
    @Impure
    @Ensures(condition = "isHashing()", message = "The encoder has to be hashing.")
    public void startHashing(@Nonnull MessageDigest digest);
    
    /**
     * Stops hashing and returns the hash.
     */
    @Impure
    @Requires(condition = "isHashing()", message = "The encoder has to be hashing.")
    public @Nonnull byte[] stopHashing();
    
    /* -------------------------------------------------- Compressing -------------------------------------------------- */
    
    /**
     * Returns whether this encoder is currently compressing the encoded values.
     */
    @Pure
    public boolean isCompressing();
    
    /**
     * Starts compressing all encodings with the given deflater until you {@link #stopCompressing() stop compressing}.
     */
    @Impure
    @Ensures(condition = "isCompressing()", message = "The encoder has to be compressing.")
    public void startCompressing(@Nonnull Deflater deflater);
    
    /**
     * Stops compressing.
     */
    @Impure
    @Requires(condition = "isCompressing()", message = "The encoder has to be compressing.")
    public void stopCompressing() throws EXCEPTION;
    
    /* -------------------------------------------------- Encrypting -------------------------------------------------- */
    
    /**
     * Returns whether this encoder is currently encrypting the encoded values.
     */
    @Pure
    public boolean isEncrypting();
    
    /**
     * Starts encrypting all encodings with the given cipher until you {@link #stopEncrypting() stop encrypting}.
     */
    @Impure
    @Ensures(condition = "isEncrypting()", message = "The encoder has to be encrypting.")
    public void startEncrypting(@Nonnull Cipher cipher);
    
    /**
     * Stops encrypting.
     */
    @Impure
    @Requires(condition = "isEncrypting()", message = "The encoder has to be encrypting.")
    public void stopEncrypting() throws EXCEPTION;
    
    /* -------------------------------------------------- Closing -------------------------------------------------- */
    
    @Impure
    @Override
    public void close() throws EXCEPTION;
    
}
