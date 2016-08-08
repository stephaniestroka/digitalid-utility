package net.digitalid.utility.generator.generators.converter;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.conversion.converter.ValueCollector;
import net.digitalid.utility.conversion.converter.types.CustomType;
import net.digitalid.utility.functional.interfaces.UnaryFunction;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.validation.annotations.size.Size;

/**
 * The value collector that is used to test the functionality of the converter.
 */
class TestValueCollector implements ValueCollector {
    
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
    public @Nonnull Integer setInteger(BigInteger value) {
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
    public @Nonnull Integer setString64(String value) {
        collectedValues.add(Pair.of(value, String.class));
        return 1;
    }
    
    @Impure
    @Override
    public @Nonnull Integer setString(String value) {
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
    public @Nonnull Integer setBinaryStream(InputStream stream, int length) {
        collectedValues.add(Pair.of(stream, InputStream.class));
        return 1;
    }
    
    @Impure
    @Override
    public <T> @Nonnull Integer setList(List<T> list, UnaryFunction<T, @Nonnull Integer> entityCollector) {
        collectedValues.add(Pair.of(list, List.class));
        return 1;
    }
    
    @Impure
    @Override
    public <T> @Nonnull Integer setArray(T[] value, UnaryFunction<T, @Nonnull Integer> entityCollector) {
        collectedValues.add(Pair.of(value, Object[].class));
        return 1;
    }
    
    @Impure
    @Override
    public <T> @Nonnull Integer setSet(Set<T> value, UnaryFunction<T, @Nonnull Integer> entityCollector) {
        collectedValues.add(Pair.of(value, Set.class));
        return 1;
    }
    
    @Impure
    @Override
    public <K, V> @Nonnull Integer setMap(Map<K, V> value, UnaryFunction<K, @Nonnull Integer> genericTypeKey, UnaryFunction<V, @Nonnull Integer> genericTypeValue) {
        collectedValues.add(Pair.of(value, Map.class));
        return 1;
    }
    
    @Impure
    @Override
    public @Nonnull Integer setNull(CustomType customType) {
        collectedValues.add(Pair.of(null, Object.class));
        return 1;
    }
    
}
