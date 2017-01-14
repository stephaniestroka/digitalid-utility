package net.digitalid.utility.conversion.interfaces;

import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Map;
import java.util.zip.Inflater;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.Cipher;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Shared;
import net.digitalid.utility.conversion.enumerations.Representation;
import net.digitalid.utility.conversion.exceptions.ConnectionException;
import net.digitalid.utility.conversion.exceptions.RecoveryException;
import net.digitalid.utility.functional.failable.FailableCollector;
import net.digitalid.utility.functional.interfaces.UnaryFunction;
import net.digitalid.utility.validation.annotations.method.Ensures;
import net.digitalid.utility.validation.annotations.method.Requires;
import net.digitalid.utility.validation.annotations.size.Empty;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.size.Size;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * A decoder decodes values from a data source like an SQL database or an XDF stream.
 * 
 * @see Encoder
 */
@Mutable
public interface Decoder<EXCEPTION extends ConnectionException> {
    
    /* -------------------------------------------------- Representation -------------------------------------------------- */
    
    /**
     * Returns the representation which is used to decode the values.
     */
    @Pure
    public @Nonnull Representation getRepresentation();
    
    /* -------------------------------------------------- Objects -------------------------------------------------- */
    
    /**
     * Decodes a non-nullable object with the given converter and provided object.
     */
    @Impure
    public <@Unspecifiable TYPE, @Specifiable PROVIDED> @Nonnull TYPE decodeObject(@Nonnull Converter<TYPE, PROVIDED> converter, @Shared PROVIDED provided) throws EXCEPTION, RecoveryException;
    
    /**
     * Decodes a nullable object with the given converter and provided object.
     */
    @Impure
    public <@Unspecifiable TYPE, @Specifiable PROVIDED> @Nullable TYPE decodeNullableObject(@Nonnull Converter<TYPE, PROVIDED> converter, @Shared PROVIDED provided) throws EXCEPTION, RecoveryException;
    
    /* -------------------------------------------------- Values -------------------------------------------------- */
    
    /**
     * Decodes and returns a boolean value.
     */
    @Impure
    public boolean decodeBoolean() throws EXCEPTION;
    
    /**
     * Decodes and returns a byte value.
     */
    @Impure
    public byte decodeInteger08() throws EXCEPTION;
    
    /**
     * Decodes and returns a short value.
     */
    @Impure
    public short decodeInteger16() throws EXCEPTION;
    
    /**
     * Decodes and returns an int value.
     */
    @Impure
    public int decodeInteger32() throws EXCEPTION;
    
    /**
     * Decodes and returns a long value.
     */
    @Impure
    public long decodeInteger64() throws EXCEPTION;
    
    /**
     * Decodes and returns an integer value.
     */
    @Impure
    public @Nonnull BigInteger decodeInteger() throws EXCEPTION;
    
    /**
     * Decodes and returns a float value.
     */
    @Impure
    public float decodeDecimal32() throws EXCEPTION;
    
    /**
     * Decodes and returns a double value.
     */
    @Impure
    public double decodeDecimal64() throws EXCEPTION;
    
    /**
     * Decodes and returns a char value.
     */
    @Impure
    public char decodeString01() throws EXCEPTION;
    
    /**
     * Decodes and returns a string of at most 64 characters.
     */
    @Impure
    public @Nonnull @MaxSize(64) String decodeString64() throws EXCEPTION;
    
    /**
     * Decodes and returns a string.
     */
    @Impure
    public @Nonnull String decodeString() throws EXCEPTION;
    
    /**
     * Decodes and returns a byte array of size 16.
     */
    @Impure
    public @Nonnull @Size(16) byte[] decodeBinary128() throws EXCEPTION;
    
    /**
     * Decodes and returns a byte array of size 32.
     */
    @Impure
    public @Nonnull @Size(32) byte[] decodeBinary256() throws EXCEPTION;
    
    /**
     * Decodes and returns a byte array.
     */
    @Impure
    public @Nonnull byte[] decodeBinary() throws EXCEPTION;
    
    /**
     * Decodes and returns a binary stream.
     */
    @Impure
    public @Nonnull InputStream decodeBinaryStream() throws EXCEPTION;
    
    /* -------------------------------------------------- Collections -------------------------------------------------- */
    
    /**
     * Decodes non-nullable elements with the given converter and provided object in the given order and returns the iterable created by the given constructor, which receives the size of the iterable, with the decoded elements.
     */
    @Impure
    public <@Unspecifiable TYPE, @Specifiable PROVIDED, @Unspecifiable ITERABLE, @Unspecifiable COLLECTOR extends FailableCollector<@Nonnull TYPE, @Nonnull ITERABLE, RecoveryException, RecoveryException>> @Nonnull ITERABLE decodeOrderedIterable(@Nonnull Converter<TYPE, PROVIDED> converter, @Shared PROVIDED provided, @Nonnull UnaryFunction<@Nonnull Integer, @Nonnull COLLECTOR> constructor) throws EXCEPTION, RecoveryException;
    
    /**
     * Decodes nullable elements with the given converter and provided object in the given order and returns the iterable created by the given constructor, which receives the size of the iterable, with the decoded elements.
     */
    @Impure
    public <@Unspecifiable TYPE, @Specifiable PROVIDED, @Unspecifiable ITERABLE, @Unspecifiable COLLECTOR extends FailableCollector<@Nullable TYPE, @Nonnull ITERABLE, RecoveryException, RecoveryException>> @Nonnull ITERABLE decodeOrderedIterableWithNullableElements(@Nonnull Converter<TYPE, PROVIDED> converter, @Shared PROVIDED provided, @Nonnull UnaryFunction<@Nonnull Integer, @Nonnull COLLECTOR> constructor) throws EXCEPTION, RecoveryException;
    
    /**
     * Decodes non-nullable elements with the given converter and provided object without preserving the order and returns the iterable created by the given constructor, which receives the size of the iterable, with the decoded elements.
     */
    @Impure
    public <@Unspecifiable TYPE, @Specifiable PROVIDED, @Unspecifiable ITERABLE, @Unspecifiable COLLECTOR extends FailableCollector<@Nonnull TYPE, @Nonnull ITERABLE, RecoveryException, RecoveryException>> @Nonnull ITERABLE decodeUnorderedIterable(@Nonnull Converter<TYPE, PROVIDED> converter, @Shared PROVIDED provided, @Nonnull UnaryFunction<@Nonnull Integer, @Nonnull COLLECTOR> constructor) throws EXCEPTION, RecoveryException;
    
    /**
     * Decodes nullable elements with the given converter and provided object without preserving the order and returns the iterable created by the given constructor, which receives the size of the iterable, with the decoded elements.
     */
    @Impure
    public <@Unspecifiable TYPE, @Specifiable PROVIDED, @Unspecifiable ITERABLE, @Unspecifiable COLLECTOR extends FailableCollector<@Nullable TYPE, @Nonnull ITERABLE, RecoveryException, RecoveryException>> @Nonnull ITERABLE decodeUnorderedIterableWithNullableElements(@Nonnull Converter<TYPE, PROVIDED> converter, @Shared PROVIDED provided, @Nonnull UnaryFunction<@Nonnull Integer, @Nonnull COLLECTOR> constructor) throws EXCEPTION, RecoveryException;
    
    /**
     * Decodes non-nullable key-value pairs with the given converters and provided objects and returns the given empty map with the decoded key-value pairs.
     */
    @Impure
    public <@Unspecifiable KEY, @Specifiable PROVIDED_FOR_KEY, @Unspecifiable VALUE, @Specifiable PROVIDED_FOR_VALUE> @Nonnull Map<@Nonnull KEY, @Nonnull VALUE> decodeMap(@Nonnull Converter<KEY, PROVIDED_FOR_KEY> keyConverter, @Shared PROVIDED_FOR_KEY providedForKey, @Nonnull Converter<VALUE, PROVIDED_FOR_VALUE> valueConverter, @Shared PROVIDED_FOR_VALUE providedForValue, @Nonnull @Empty Map<@Nonnull KEY, @Nonnull VALUE> emptyMap) throws EXCEPTION, RecoveryException;
    
    /**
     * Decodes nullable key-value pairs with the given converters and provided objects and returns the given empty map with the decoded key-value pairs.
     */
    @Impure
    public <@Unspecifiable KEY, @Specifiable PROVIDED_FOR_KEY, @Unspecifiable VALUE, @Specifiable PROVIDED_FOR_VALUE> @Nonnull Map<@Nullable KEY, @Nullable VALUE> decodeMapWithNullableValues(@Nonnull Converter<KEY, PROVIDED_FOR_KEY> keyConverter, @Shared PROVIDED_FOR_KEY providedForKey, @Nonnull Converter<VALUE, PROVIDED_FOR_VALUE> valueConverter, @Shared PROVIDED_FOR_VALUE providedForValue, @Nonnull @Empty Map<@Nullable KEY, @Nullable VALUE> emptyMap) throws EXCEPTION, RecoveryException;
    
    /* -------------------------------------------------- Hashing -------------------------------------------------- */
    
    /**
     * Returns whether this decoder is currently hashing the decoded values.
     */
    @Pure
    public boolean isHashing();
    
    /**
     * Starts hashing all decodings with the given digest until you {@link #stopHashing() stop hashing}.
     */
    @Impure
    @Ensures(condition = "isHashing()", message = "The decoder has to be hashing.")
    public void startHashing(@Nonnull MessageDigest digest);
    
    /**
     * Stops hashing and returns the hash.
     */
    @Impure
    @Requires(condition = "isHashing()", message = "The decoder has to be hashing.")
    public @Nonnull byte[] stopHashing();
    
    /* -------------------------------------------------- Decompressing -------------------------------------------------- */
    
    /**
     * Returns whether this decoder is currently decompressing the decoded values.
     */
    @Pure
    public boolean isDecompressing();
    
    /**
     * Starts decompressing all decodings with the given inflater until you {@link #stopDecompressing() stop decompressing}.
     */
    @Impure
    @Ensures(condition = "isDecompressing()", message = "The decoder has to be decompressing.")
    public void startDecompressing(@Nonnull Inflater inflater);
    
    /**
     * Stops decompressing.
     */
    @Impure
    @Requires(condition = "isDecompressing()", message = "The decoder has to be decompressing.")
    public void stopDecompressing() throws EXCEPTION;
    
    /* -------------------------------------------------- Decrypting -------------------------------------------------- */
    
    /**
     * Returns whether this decoder is currently decrypting the decoded values.
     */
    @Pure
    public boolean isDecrypting();
    
    /**
     * Starts decrypting all decodings with the given cipher until you {@link #stopDecrypting() stop decrypting}.
     */
    @Impure
    @Ensures(condition = "isDecrypting()", message = "The decoder has to be decrypting.")
    public void startDecrypting(@Nonnull Cipher cipher);
    
    /**
     * Stops decrypting.
     */
    @Impure
    @Requires(condition = "isDecrypting()", message = "The decoder has to be decrypting.")
    public void stopDecrypting() throws EXCEPTION;
    
}
