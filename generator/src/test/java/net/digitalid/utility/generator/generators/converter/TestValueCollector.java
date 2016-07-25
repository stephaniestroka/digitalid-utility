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
import net.digitalid.utility.functional.interfaces.Consumer;
import net.digitalid.utility.functional.interfaces.UnaryFunction;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.validation.annotations.size.Size;

/**
 * The value collector that is used to test the functionality of the converter.
 */
class TestValueCollector implements ValueCollector<Void> {
    
    
    List<@Nonnull Pair<@Nonnull Object, @Nonnull Class<?>>> collectedValues = new ArrayList<>();
    
    @Impure
    @Override
    public Void setEmpty() {
        collectedValues.add(Pair.of(null, boolean.class));
        return null;
    }
    
    @Impure
    @Override
    public Void setBoolean(boolean value) {
        collectedValues.add(Pair.of(value, boolean.class));
        return null;
    }
    
    @Impure
    @Override
    public Void setInteger08(byte value) {
        collectedValues.add(Pair.of(value, byte.class));
        return null;
    }
    
    @Impure
    @Override
    public Void setInteger16(short value) {
        collectedValues.add(Pair.of(value, short.class));
        return null;
    }
    
    @Impure
    @Override
    public Void setInteger32(int value) {
        collectedValues.add(Pair.of(value, int.class));
        return null;
    }
    
    @Impure
    @Override
    public Void setInteger64(long value) {
        collectedValues.add(Pair.of(value, long.class));
        return null;
    }
    
    @Impure
    @Override
    public Void setInteger(BigInteger value) {
        collectedValues.add(Pair.of(value, BigInteger.class));
        return null;
    }
    
    @Impure
    @Override
    public Void setDecimal32(float value) {
        collectedValues.add(Pair.of(value, float.class));
        return null;
    }
    
    @Impure
    @Override
    public Void setDecimal64(double value) {
        collectedValues.add(Pair.of(value, double.class));
        return null;
    }
    
    @Impure
    @Override
    public Void setString01(char value) {
        collectedValues.add(Pair.of(value, char.class));
        return null;
    }
    
    @Impure
    @Override
    public Void setString64(String value) {
        collectedValues.add(Pair.of(value, String.class));
        return null;
    }
    
    @Impure
    @Override
    public Void setString(String value) {
        collectedValues.add(Pair.of(value, String.class));
        return null;
    }
    
    @Impure
    @Override
    public Void setBinary128(@Nonnull @Size(16) byte[] value) {
        collectedValues.add(Pair.of(value, byte[].class));
        return null;
    }
    
    @Impure
    @Override
    public Void setBinary256(@Nonnull @Size(32) byte[] value) {
        collectedValues.add(Pair.of(value, byte[].class));
        return null;
    }
    
    @Impure
    @Override
    public Void setBinary(@Nonnull byte[] value) {
        collectedValues.add(Pair.of(value, byte[].class));
        return null;
    }
    
    @Impure
    @Override
    public Void setBinaryStream(InputStream stream, int length) {
        collectedValues.add(Pair.of(stream, InputStream.class));
        return null;
    }
    
    @Impure
    @Override
    public <T> Void setList(List<T> list, UnaryFunction<T, Void> entityCollector) {
        collectedValues.add(Pair.of(list, List.class));
        return null;
    }
    
    @Impure
    @Override
    public <T> Void setArray(T[] value, Consumer<T> entityCollector) {
        collectedValues.add(Pair.of(value, Object[].class));
        return null;
    }
    
    @Impure
    @Override
    public <T> Void setSet(Set<T> value, Consumer<T> entityCollector) {
        collectedValues.add(Pair.of(value, Set.class));
        return null;
    }
    
    @Impure
    @Override
    public <K, V> Void setMap(Map<K, V> value, Consumer<K> genericTypeKey, Consumer<V> genericTypeValue) {
        collectedValues.add(Pair.of(value, Map.class));
        return null;
    }
    
    @Impure
    @Override
    public Void setNull(CustomType customType) {
        collectedValues.add(Pair.of(null, Object.class));
        return null;
    }
    
}
