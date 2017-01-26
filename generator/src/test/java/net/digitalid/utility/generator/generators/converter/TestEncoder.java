package net.digitalid.utility.generator.generators.converter;

import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
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
import net.digitalid.utility.conversion.interfaces.Converter;
import net.digitalid.utility.conversion.interfaces.Encoder;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.validation.annotations.method.Ensures;
import net.digitalid.utility.validation.annotations.method.Requires;
import net.digitalid.utility.validation.annotations.size.Size;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * The encoder that is used to test the functionality of the converter.
 */
@Mutable
public class TestEncoder implements Encoder<ConnectionException> {
    
    /* -------------------------------------------------- Representation -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull Representation getRepresentation() {
        return Representation.EXTERNAL;
    }
    
    /* -------------------------------------------------- Objects -------------------------------------------------- */
    
    @Impure
    @Override
    public <@Unspecifiable TYPE> void encodeObject(@Nonnull Converter<TYPE, ?> converter, @NonCaptured @Unmodified @Nonnull TYPE object) throws ConnectionException {
        converter.convert(object, this);
    }
    
    @Impure
    @Override
    public <@Unspecifiable TYPE> void encodeNullableObject(@Nonnull Converter<TYPE, ?> converter, @NonCaptured @Unmodified @Nullable TYPE object) throws ConnectionException {
        encodeBoolean(object != null);
        if (object != null) { encodeObject(converter, object); }
    }
    
    /* -------------------------------------------------- Values -------------------------------------------------- */
    
    protected final @Nonnull List<@Nonnull Pair<@Nonnull Object, @Nonnull Class<?>>> encodedValues = new ArrayList<>();
    
    @Impure
    @Override
    public void encodeBoolean(boolean value) {
        encodedValues.add(Pair.of(value, boolean.class));
    }
    
    @Impure
    @Override
    public void encodeInteger08(byte value) {
        encodedValues.add(Pair.of(value, byte.class));
    }
    
    @Impure
    @Override
    public void encodeInteger16(short value) {
        encodedValues.add(Pair.of(value, short.class));
    }
    
    @Impure
    @Override
    public void encodeInteger32(int value) {
        encodedValues.add(Pair.of(value, int.class));
    }
    
    @Impure
    @Override
    public void encodeInteger64(long value) {
        encodedValues.add(Pair.of(value, long.class));
    }
    
    @Impure
    @Override
    public void encodeInteger(@Nonnull BigInteger value) {
        encodedValues.add(Pair.of(value, BigInteger.class));
    }
    
    @Impure
    @Override
    public void encodeDecimal32(float value) {
        encodedValues.add(Pair.of(value, float.class));
    }
    
    @Impure
    @Override
    public void encodeDecimal64(double value) {
        encodedValues.add(Pair.of(value, double.class));
    }
    
    @Impure
    @Override
    public void encodeString01(char value) {
        encodedValues.add(Pair.of(value, char.class));
    }
    
    @Impure
    @Override
    public void encodeString64(@Nonnull String value) {
        encodedValues.add(Pair.of(value, String.class));
    }
    
    @Impure
    @Override
    public void encodeString(@Nonnull String value) {
        encodedValues.add(Pair.of(value, String.class));
    }
    
    @Impure
    @Override
    public void encodeBinary128(@Nonnull @Size(16) byte[] value) {
        encodedValues.add(Pair.of(value, byte[].class));
    }
    
    @Impure
    @Override
    public void encodeBinary256(@Nonnull @Size(32) byte[] value) {
        encodedValues.add(Pair.of(value, byte[].class));
    }
    
    @Impure
    @Override
    public void encodeBinary(@Nonnull byte[] value) {
        encodedValues.add(Pair.of(value, byte[].class));
    }
    
    @Impure
    @Override
    public void encodeBinaryStream(@Nonnull InputStream stream, int length) {
        encodedValues.add(Pair.of(stream, InputStream.class));
    }
    
    /* -------------------------------------------------- Collections -------------------------------------------------- */
    
    @Impure
    @Override
    public <@Unspecifiable TYPE> void encodeOrderedIterable(@Nonnull Converter<TYPE, ?> converter, @Nonnull FiniteIterable<@Nonnull TYPE> iterable) throws ConnectionException {
        encodedValues.add(Pair.of(iterable, Iterable.class));
    }
    
    @Impure
    @Override
    public <@Unspecifiable TYPE> void encodeOrderedIterableWithNullableElements(@Nonnull Converter<TYPE, ?> converter, @Nonnull FiniteIterable<@Nullable TYPE> iterable) throws ConnectionException {
        encodedValues.add(Pair.of(iterable, Iterable.class));
    }
    
    @Impure
    @Override
    public <@Unspecifiable TYPE> void encodeUnorderedIterable(@Nonnull Converter<TYPE, ?> converter, @Nonnull FiniteIterable<@Nonnull TYPE> iterable) throws ConnectionException {
        encodedValues.add(Pair.of(iterable, Iterable.class));
    }
    
    @Impure
    @Override
    public <@Unspecifiable TYPE> void encodeUnorderedIterableWithNullableElements(@Nonnull Converter<TYPE, ?> converter, @Nonnull FiniteIterable<@Nullable TYPE> iterable) throws ConnectionException {
        encodedValues.add(Pair.of(iterable, Iterable.class));
    }
    
    @Impure
    @Override
    public <@Unspecifiable KEY, @Unspecifiable VALUE> void encodeMap(@Nonnull Converter<KEY, ?> keyConverter, @Nonnull Converter<VALUE, ?> valueConverter, @Nonnull Map<@Nonnull KEY, @Nonnull VALUE> map) throws ConnectionException {
        encodedValues.add(Pair.of(map, Map.class));
    }
    
    @Impure
    @Override
    public <@Unspecifiable KEY, @Unspecifiable VALUE> void encodeMapWithNullableValues(@Nonnull Converter<KEY, ?> keyConverter, @Nonnull Converter<VALUE, ?> valueConverter, @Nonnull Map<@Nullable KEY, @Nullable VALUE> map) throws ConnectionException {
        encodedValues.add(Pair.of(map, Map.class));
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
    @Ensures(condition = "isHashing()", message = "The encoder has to be hashing.")
    public void startHashing(@Nonnull MessageDigest digest) {
        encodedValues.add(Pair.of(digest, MessageDigest.class));
        this.hashing = true;
    }
    
    @Impure
    @Override
    @Requires(condition = "isHashing()", message = "The encoder has to be hashing.")
    public @Nonnull byte[] stopHashing() {
        this.hashing = false;
        return new byte[32];
    }
    
    /* -------------------------------------------------- Compressing -------------------------------------------------- */
    
    private boolean compressing = false;
    
    @Pure
    @Override
    public boolean isCompressing() {
        return compressing;
    }
    
    @Impure
    @Override
    @Ensures(condition = "isCompressing()", message = "The encoder has to be compressing.")
    public void startCompressing(@Nonnull Deflater deflater) {
        encodedValues.add(Pair.of(deflater, Deflater.class));
        this.compressing = true;
    }
    
    @Impure
    @Override
    @Requires(condition = "isCompressing()", message = "The encoder has to be compressing.")
    public void stopCompressing() throws ConnectionException {
        this.compressing = false;
    }
    
    /* -------------------------------------------------- Encrypting -------------------------------------------------- */
    
    private boolean encrypting = false;
    
    @Pure
    @Override
    public boolean isEncrypting() {
        return encrypting;
    }
    
    @Impure
    @Override
    @Ensures(condition = "isEncrypting()", message = "The encoder has to be encrypting.")
    public void startEncrypting(@Nonnull Cipher cipher) {
        encodedValues.add(Pair.of(cipher, Cipher.class));
        this.encrypting = true;
    }
    
    @Impure
    @Override
    @Requires(condition = "isEncrypting()", message = "The encoder has to be encrypting.")
    public void stopEncrypting() throws ConnectionException {
        this.encrypting = false;
    }
    
    /* -------------------------------------------------- Closing -------------------------------------------------- */
    
    @Impure
    @Override
    public void close() throws ConnectionException {}
    
}
