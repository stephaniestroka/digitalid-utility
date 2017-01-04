package net.digitalid.utility.generator.generators.converter;

import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Queue;
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
import net.digitalid.utility.conversion.interfaces.Converter;
import net.digitalid.utility.conversion.interfaces.Decoder;
import net.digitalid.utility.functional.failable.FailableCollector;
import net.digitalid.utility.functional.interfaces.UnaryFunction;
import net.digitalid.utility.validation.annotations.method.Ensures;
import net.digitalid.utility.validation.annotations.method.Requires;
import net.digitalid.utility.validation.annotations.size.Empty;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.size.Size;

/**
 * The decoder that is used to test the functionality of the converter.
 */
public class TestDecoder implements Decoder<ConnectionException> {
    
    /* -------------------------------------------------- Representation -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull Representation getRepresentation() {
        return Representation.EXTERNAL;
    }
    
    /* -------------------------------------------------- Objects -------------------------------------------------- */
    
    @Impure
    @Override
    public <@Unspecifiable TYPE, @Specifiable PROVIDED> @Nonnull TYPE decodeObject(@Nonnull Converter<TYPE, PROVIDED> converter, @Shared PROVIDED provided) throws ConnectionException, RecoveryException {
        return converter.recover(this, provided);
    }
    
    @Impure
    @Override
    public <@Unspecifiable TYPE, @Specifiable PROVIDED> @Nullable TYPE decodeNullableObject(@Nonnull Converter<TYPE, PROVIDED> converter, @Shared PROVIDED provided) throws ConnectionException, RecoveryException {
        if (decodeBoolean()) { return converter.recover(this, provided); }
        else { return null; }
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    protected final @Nonnull Queue<@Nonnull Object> decodedValues;
    
    public TestDecoder(@Nonnull Queue<@Nonnull Object> decodedValues) {
        this.decodedValues = decodedValues;
    }
    
    /* -------------------------------------------------- Values -------------------------------------------------- */
    
    @Impure
    @Override
    public boolean decodeBoolean() {
        return (boolean) decodedValues.poll();
    }
    
    @Impure
    @Override
    public byte decodeInteger08() {
        return (byte) decodedValues.poll();
    }
    
    @Impure
    @Override
    public short decodeInteger16() {
        return (short) decodedValues.poll();
    }
    
    @Impure
    @Override
    public int decodeInteger32() {
        return (int) decodedValues.poll();
    }
    
    @Impure
    @Override
    public long decodeInteger64() {
        return (long) decodedValues.poll();
    }
    
    @Impure
    @Override
    public @Nullable BigInteger decodeInteger() {
        return (BigInteger) decodedValues.poll();
    }
    
    @Impure
    @Override
    public float decodeDecimal32() {
        return (float) decodedValues.poll();
    }
    
    @Impure
    @Override
    public double decodeDecimal64() {
        return (double) decodedValues.poll();
    }
    
    @Impure
    @Override
    public char decodeString01() {
        return (char) decodedValues.poll();
    }
    
    @Impure
    @Override
    public @Nullable @MaxSize(64) String decodeString64() {
        return (String) decodedValues.poll();
    }
    
    @Impure
    @Override
    public @Nullable String decodeString() {
        return (String) decodedValues.poll();
    }
    
    @Impure
    @Override
    public @Nullable @Size(16) byte[] decodeBinary128() {
        return (byte[]) decodedValues.poll();
    }
    
    @Impure
    @Override
    public @Nullable @Size(32) byte[] decodeBinary256() {
        return (byte[]) decodedValues.poll();
    }
    
    @Impure
    @Override
    public @Nullable byte[] decodeBinary() {
        return (byte[]) decodedValues.poll();
    }
    
    @Impure
    @Override
    public @Nonnull InputStream decodeBinaryStream() throws ConnectionException {
        return (InputStream) decodedValues.poll();
    }
    
    /* -------------------------------------------------- Collections -------------------------------------------------- */
    
    @Impure
    @Override
    @SuppressWarnings("unchecked")
    public <@Unspecifiable TYPE, @Specifiable PROVIDED, @Unspecifiable ITERABLE, @Unspecifiable COLLECTOR extends FailableCollector<@Nonnull TYPE, @Nonnull ITERABLE, RecoveryException, RecoveryException>> @Nonnull ITERABLE decodeOrderedIterable(@Nonnull Converter<TYPE, PROVIDED> converter, @Shared PROVIDED provided, @Nonnull UnaryFunction<@Nonnull Integer, @Nonnull COLLECTOR> constructor) throws ConnectionException, RecoveryException {
        return (ITERABLE) decodedValues.poll();
    }
    
    @Impure
    @Override
    @SuppressWarnings("unchecked")
    public <@Unspecifiable TYPE, @Specifiable PROVIDED, @Unspecifiable ITERABLE, @Unspecifiable COLLECTOR extends FailableCollector<@Nullable TYPE, @Nonnull ITERABLE, RecoveryException, RecoveryException>> @Nonnull ITERABLE decodeOrderedIterableWithNullableElements(@Nonnull Converter<TYPE, PROVIDED> converter, @Shared PROVIDED provided, @Nonnull UnaryFunction<@Nonnull Integer, @Nonnull COLLECTOR> constructor) throws ConnectionException, RecoveryException {
        return (ITERABLE) decodedValues.poll();
    }
    
    @Impure
    @Override
    @SuppressWarnings("unchecked")
    public <@Unspecifiable TYPE, @Specifiable PROVIDED, @Unspecifiable ITERABLE, @Unspecifiable COLLECTOR extends FailableCollector<@Nonnull TYPE, @Nonnull ITERABLE, RecoveryException, RecoveryException>> @Nonnull ITERABLE decodeUnorderedIterable(@Nonnull Converter<TYPE, PROVIDED> converter, @Shared PROVIDED provided, @Nonnull UnaryFunction<@Nonnull Integer, @Nonnull COLLECTOR> constructor) throws ConnectionException, RecoveryException {
        return (ITERABLE) decodedValues.poll();
    }
    
    @Impure
    @Override
    @SuppressWarnings("unchecked")
    public <@Unspecifiable TYPE, @Specifiable PROVIDED, @Unspecifiable ITERABLE, @Unspecifiable COLLECTOR extends FailableCollector<@Nullable TYPE, @Nonnull ITERABLE, RecoveryException, RecoveryException>> @Nonnull ITERABLE decodeUnorderedIterableWithNullableElements(@Nonnull Converter<TYPE, PROVIDED> converter, @Shared PROVIDED provided, @Nonnull UnaryFunction<@Nonnull Integer, @Nonnull COLLECTOR> constructor) throws ConnectionException, RecoveryException {
        return (ITERABLE) decodedValues.poll();
    }
    
    @Impure
    @Override
    @SuppressWarnings("unchecked")
    public <@Unspecifiable KEY, @Specifiable PROVIDED_FOR_KEY, @Unspecifiable VALUE, @Specifiable PROVIDED_FOR_VALUE> @Nonnull Map<@Nonnull KEY, @Nonnull VALUE> decodeMap(@Nonnull Converter<KEY, PROVIDED_FOR_KEY> keyConverter, @Shared PROVIDED_FOR_KEY providedForKey, @Nonnull Converter<VALUE, PROVIDED_FOR_VALUE> valueConverter, @Shared PROVIDED_FOR_VALUE providedForValue, @Nonnull @Empty Map<@Nonnull KEY, @Nonnull VALUE> emptyMap) throws ConnectionException, RecoveryException {
        return (Map<KEY, VALUE>) decodedValues.poll();
    }
    
    @Impure
    @Override
    @SuppressWarnings("unchecked")
    public <@Unspecifiable KEY, @Specifiable PROVIDED_FOR_KEY, @Unspecifiable VALUE, @Specifiable PROVIDED_FOR_VALUE> @Nonnull Map<@Nullable KEY, @Nullable VALUE> decodeMapWithNullableValues(@Nonnull Converter<KEY, PROVIDED_FOR_KEY> keyConverter, @Shared PROVIDED_FOR_KEY providedForKey, @Nonnull Converter<VALUE, PROVIDED_FOR_VALUE> valueConverter, @Shared PROVIDED_FOR_VALUE providedForValue, @Nonnull @Empty Map<@Nullable KEY, @Nullable VALUE> emptyMap) throws ConnectionException, RecoveryException {
        return (Map<KEY, VALUE>) decodedValues.poll();
    }
    
    /* -------------------------------------------------- Hashing -------------------------------------------------- */
    
    private boolean hashing = false;
    
    @Pure
    @Override
    public boolean isHashing() {
        return hashing;
    }
    
    @Impure
    @Override
    @Ensures(condition = "isHashing()", message = "The decoder has to be hashing.")
    public void startHashing(@Nonnull MessageDigest digest) {
        this.hashing = true;
    }
    
    @Impure
    @Override
    @Requires(condition = "isHashing()", message = "The decoder has to be hashing.")
    public @Nonnull byte[] stopHashing() {
        this.hashing = false;
        return new byte[32];
    }
    
    /* -------------------------------------------------- Decompressing -------------------------------------------------- */
    
    private boolean decompressing = false;
    
    @Pure
    @Override
    public boolean isDecompressing() {
        return decompressing;
    }
    
    @Impure
    @Override
    @Ensures(condition = "isDecompressing()", message = "The decoder has to be decompressing.")
    public void startDecompressing(@Nonnull Inflater inflater) {
        this.decompressing = true;
    }
    
    @Impure
    @Override
    @Requires(condition = "isDecompressing()", message = "The decoder has to be decompressing.")
    public void stopDecompressing() throws ConnectionException {
        this.decompressing = false;
    }
    
    /* -------------------------------------------------- Decrypting -------------------------------------------------- */
    
    private boolean decrypting = false;
    
    @Pure
    @Override
    public boolean isDecrypting() {
        return decrypting;
    }
    
    @Impure
    @Override
    @Ensures(condition = "isDecrypting()", message = "The decoder has to be decrypting.")
    public void startDecrypting(@Nonnull Cipher cipher) {
        this.decrypting = true;
    }
    
    @Impure
    @Override
    @Requires(condition = "isDecrypting()", message = "The decoder has to be decrypting.")
    public void stopDecrypting() throws ConnectionException {
        this.decrypting = false;
    }
    
}
