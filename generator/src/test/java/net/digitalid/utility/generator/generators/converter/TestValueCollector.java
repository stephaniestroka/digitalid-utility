package net.digitalid.utility.generator.generators.converter;

import java.io.InputStream;
import java.math.BigInteger;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import javax.annotation.Nonnull;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.conversion.converter.ValueCollector;
import net.digitalid.utility.conversion.converter.ValueCollectorImplementation;
import net.digitalid.utility.conversion.converter.types.CustomType;
import net.digitalid.utility.functional.failable.FailableUnaryFunction;
import net.digitalid.utility.logging.exceptions.ExternalException;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.validation.annotations.size.Size;

/**
 * The value collector that is used to test the functionality of the converter.
 */
class TestValueCollector extends ValueCollectorImplementation<ExternalException> {
    
    List<@Nonnull Pair<@Nonnull Object, @Nonnull Class<?>>> collectedValues = new ArrayList<>();
    
    @Impure
    @Override
    public @Nonnull Integer setEmpty() {
        collectedValues.add(Pair.of(null, boolean.class));
        return 1;
    }
    
    @Impure
    @Override
    public @Nonnull Integer setBoolean(boolean value) {
        collectedValues.add(Pair.of(value, boolean.class));
        return 1;
    }
    
    @Impure
    @Override
    public @Nonnull Integer setInteger08(byte value) {
        collectedValues.add(Pair.of(value, byte.class));
        return 1;
    }
    
    @Impure
    @Override
    public @Nonnull Integer setInteger16(short value) {
        collectedValues.add(Pair.of(value, short.class));
        return 1;
    }
    
    @Impure
    @Override
    public @Nonnull Integer setInteger32(int value) {
        collectedValues.add(Pair.of(value, int.class));
        return 1;
    }
    
    @Impure
    @Override
    public @Nonnull Integer setInteger64(long value) {
        collectedValues.add(Pair.of(value, long.class));
        return 1;
    }
    
    @Impure
    @Override
    public @Nonnull Integer setInteger(@Nonnull BigInteger value) {
        collectedValues.add(Pair.of(value, BigInteger.class));
        return 1;
    }
    
    @Impure
    @Override
    public @Nonnull Integer setDecimal32(float value) {
        collectedValues.add(Pair.of(value, float.class));
        return 1;
    }
    
    @Impure
    @Override
    public @Nonnull Integer setDecimal64(double value) {
        collectedValues.add(Pair.of(value, double.class));
        return 1;
    }
    
    @Impure
    @Override
    public @Nonnull Integer setString01(char value) {
        collectedValues.add(Pair.of(value, char.class));
        return 1;
    }
    
    @Impure
    @Override
    public @Nonnull Integer setString64(@Nonnull String value) {
        collectedValues.add(Pair.of(value, String.class));
        return 1;
    }
    
    @Impure
    @Override
    public @Nonnull Integer setString(@Nonnull String value) {
        collectedValues.add(Pair.of(value, String.class));
        return 1;
    }
    
    @Impure
    @Override
    public @Nonnull Integer setBinary128(@Nonnull @Size(16) byte[] value) {
        collectedValues.add(Pair.of(value, byte[].class));
        return 1;
    }
    
    @Impure
    @Override
    public @Nonnull Integer setBinary256(@Nonnull @Size(32) byte[] value) {
        collectedValues.add(Pair.of(value, byte[].class));
        return 1;
    }
    
    @Impure
    @Override
    public @Nonnull Integer setBinary(@Nonnull byte[] value) {
        collectedValues.add(Pair.of(value, byte[].class));
        return 1;
    }
    
    @Impure
    @Override
    public @Nonnull Integer setBinaryStream(@Nonnull InputStream stream, int length) {
        collectedValues.add(Pair.of(stream, InputStream.class));
        return 1;
    }
    
    @Impure
    @Override
    public <T> @Nonnull Integer setList(@Nonnull List<T> list, @Nonnull FailableUnaryFunction<T, @Nonnull Integer, ExternalException> entityCollector) {
        collectedValues.add(Pair.of(list, List.class));
        return 1;
    }
    
    @Impure
    @Override
    public <T> @Nonnull Integer setArray(@Nonnull T[] value, @Nonnull FailableUnaryFunction<T, @Nonnull Integer, ExternalException> entityCollector) {
        collectedValues.add(Pair.of(value, Object[].class));
        return 1;
    }
    
    @Impure
    @Override
    public <T> @Nonnull Integer setSet(@Nonnull Set<T> value, @Nonnull FailableUnaryFunction<T, @Nonnull Integer, ExternalException> entityCollector) {
        collectedValues.add(Pair.of(value, Set.class));
        return 1;
    }
    
    @Impure
    @Override
    public <K, V> @Nonnull Integer setMap(@Nonnull Map<K, V> value, @Nonnull FailableUnaryFunction<K, @Nonnull Integer, ExternalException> genericTypeKey, @Nonnull FailableUnaryFunction<V, @Nonnull Integer, ExternalException> genericTypeValue) {
        collectedValues.add(Pair.of(value, Map.class));
        return 1;
    }
    
    @Impure
    @Override
    public @Nonnull Integer setNull(@Nonnull CustomType customType) {
        collectedValues.add(Pair.of(null, Object.class));
        return 1;
    }
    
    @Impure
    @Override
    public void setEncryptionCipher(@Nonnull Cipher cipher) {
        collectedValues.add(Pair.of(cipher, Cipher.class));
    }
    
    @Impure
    @Override
    public @Nonnull CipherOutputStream popEncryptionCipher() {
        return null;
    }
    
    @Impure
    @Override
    public void setSignatureDigest(@Nonnull MessageDigest digest) {
        collectedValues.add(Pair.of(digest, MessageDigest.class));
    }
    
    @Impure
    @Override
    public @Nonnull DigestOutputStream popSignatureDigest() {
        return null;
    }
    
    @Impure
    @Override
    public void setCompression(@Nonnull Deflater deflater) {
        collectedValues.add(Pair.of(deflater, Deflater.class));
    }
    
    @Impure
    @Override
    public @Nonnull DeflaterOutputStream popCompression() throws ExternalException {
        return null;
    }
    
}
