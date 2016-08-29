package net.digitalid.utility.conversion.converter;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.conversion.converter.types.CustomType;
import net.digitalid.utility.functional.failable.FailableUnaryFunction;
import net.digitalid.utility.logging.exceptions.ExternalException;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.size.Size;

/**
 * Implements null-checks on every method call and redirects call to setNull() if the parameter is null.
 */
public abstract class ValueCollectorImplementation<X extends ExternalException> implements ValueCollector<X> {
    
    /**
     * Collects a boolean value and processes it for conversion.
     */
    @Impure
    public abstract Integer setBoolean(boolean value) throws X;
    
    @Impure
    @Override
    public Integer setNullableBoolean(@Nullable Boolean value) throws X {
        return value == null ? setNull(CustomType.BOOLEAN) : setBoolean(value);
    }
    
    /**
     * Collects a byte value and processes it for conversion.
     */
    @Impure
    public abstract Integer setInteger08(byte value) throws X;
    
    @Impure
    @Override
    public Integer setNullableInteger08(@Nullable Byte value) throws X {
        return value == null ? setNull(CustomType.INTEGER08) : setInteger08(value);
    }
    
    /**
     * Collects a short value and processes it for conversion.
     */
    @Impure
    public abstract Integer setInteger16(short value) throws X;
    
    @Impure
    @Override
    public Integer setNullableInteger16(@Nullable Short value) throws X {
        return value == null ? setNull(CustomType.INTEGER16) : setInteger16(value);
    }
    
    /**
     * Collects an integer value and processes it for conversion.
     */
    @Impure
    public abstract Integer setInteger32(int value) throws X;
    
    @Impure
    @Override
    public Integer setNullableInteger32(@Nullable Integer value) throws X {
        return value == null ? setNull(CustomType.INTEGER32) : setInteger32(value);
    }
    
    /**
     * Collects a long value and processes it for conversion.
     */
    @Impure
    public abstract Integer setInteger64(long value) throws X;
    
    @Impure
    @Override
    public Integer setNullableInteger64(@Nullable Long value) throws X {
        return value == null ? setNull(CustomType.INTEGER64) : setInteger64(value);
    }
    
    /**
     * Collects a big integer value and processes it for conversion.
     */
    @Impure
    public abstract Integer setInteger(@Nonnull BigInteger value) throws X;
    
    @Impure
    @Override
    public Integer setNullableInteger(@Nullable BigInteger value) throws X {
        return value == null ? setNull(CustomType.INTEGER) : setInteger(value);
    }
    
    /**
     * Collects a float value and processes it for conversion.
     */
    @Impure
    public abstract Integer setDecimal32(float value) throws X;
    
    @Impure
    @Override
    public Integer setNullableDecimal32(@Nullable Float value) throws X {
        return value == null ? setNull(CustomType.DECIMAL32) : setDecimal32(value);
    }
    
    /**
     * Collects a double value and processes it for conversion.
     */
    @Impure
    public abstract Integer setDecimal64(double value) throws X;
    
    @Impure
    @Override
    public Integer setNullableDecimal64(@Nullable Double value) throws X {
        return value == null ? setNull(CustomType.DECIMAL64) : setDecimal64(value);
    }
    
    /**
     * Collects a character value and processes it for conversion.
     */
    @Impure
    public abstract Integer setString01(char value) throws X;
    
    @Impure
    @Override
    public Integer setNullableString01(@Nullable Character value) throws X {
        return value == null ? setNull(CustomType.STRING01) : setString01(value);
    }
    
    /**
     * Collects a string value of max size 64 and processes it for conversion.
     */
    @Impure
    public abstract Integer setString64(@Nonnull String value) throws X;
    
    @Impure
    @Override
    public Integer setNullableString64(@Nullable String value) throws X {
        return value == null ? setNull(CustomType.STRING64) : setString64(value);
    }
    
    /**
     * Collects a string value and processes it for conversion.
     */
    @Impure
    public abstract Integer setString(@Nonnull String value) throws X;
    
    @Impure
    @Override
    public Integer setNullableString(@Nullable String value) throws X {
        return value == null ? setNull(CustomType.STRING) : setString(value);
    }
    
    /**
     * Collects a byte array value of size 16 and processes it for conversion.
     */
    @Impure
    public abstract Integer setBinary128(@Nonnull @Size(16) byte[] value) throws X;
    
    @Impure
    @Override
    public Integer setNullableBinary128(@Nullable @Size(16) byte[] value) throws X {
        return value == null ? setNull(CustomType.BINARY128) : setBinary128(value);
    }
    
    /**
     * Collects a byte array value of size 32 and processes it for conversion.
     */
    @Impure
    public abstract Integer setBinary256(@Nonnull @Size(32) byte[] value) throws X;
    
    @Impure
    @Override
    public Integer setNullableBinary256(@Nullable @Size(32) byte[] value) throws X {
        return value == null ? setNull(CustomType.BINARY256) : setBinary256(value);
    }
    
    /**
     * Collects a byte array value and processes it for conversion.
     */
    @Impure
    public abstract Integer setBinary(@Nonnull byte[] value) throws X;
    
    @Impure
    @Override
    public Integer setNullableBinary(@Nullable byte[] value) throws X {
        return value == null ? setNull(CustomType.BINARY) : setBinary(value);
    }
    
    /**
     * Collects an input stream and processes it for conversion.
     */
    @Impure
    public abstract Integer setBinaryStream(@Nonnull InputStream stream, int length) throws X;
    
    @Impure
    @Override
    public Integer setNullableBinaryStream(@Nullable InputStream value, int length) throws X {
        return value == null ? setNull(CustomType.BINARYSTREAM) : setBinaryStream(value, length);
    }
    
    /**
     * Collects a list and processes it for conversion.
     */
    @Impure
    public abstract <T> Integer setList(@Nonnull List<@Nullable T> list, @Nonnull FailableUnaryFunction<@Nullable T, Integer, X> entityCollector) throws X;
    
    @Impure
    @Override
    public <T> Integer setNullableList(@Nullable List<@Nullable T> list, @Nonnull FailableUnaryFunction<@Nullable T, Integer, X> entityCollector) throws X {
        return list == null ? setNull(CustomType.LIST) : setList(list, entityCollector);
    }
    
    /**
     * Collects an array and processes it for conversion.
     */
    @Impure
    public abstract <T> Integer setArray(@Nonnull @NullableElements T[] value, @Nonnull FailableUnaryFunction<@Nullable T, Integer, X> entityCollector) throws X;
    
    @Impure
    @Override
    public <T> Integer setNullableArray(@Nullable @NullableElements T[] value, @Nonnull FailableUnaryFunction<@Nullable T, Integer, X> entityCollector) throws X {
        return value == null ? setNull(CustomType.ARRAY) : setArray(value, entityCollector);
    }
    
    /**
     * Collects a set and processes it for conversion.
     */
    @Impure
    public abstract <T> Integer setSet(@Nonnull Set<@Nullable T> value, @Nonnull FailableUnaryFunction<@Nullable T, Integer, X> entityCollector) throws X;
    
    @Impure
    @Override
    public <T> Integer setNullableSet(@Nullable Set<@Nullable T> value, @Nonnull FailableUnaryFunction<@Nullable T, Integer, X> entityCollector) throws X {
        return value == null ? setNull(CustomType.SET) : setSet(value, entityCollector);
    }
    
    /**
     * Collects a map and processes it for conversion.
     */
    @Impure
    public abstract <K, V> Integer setMap(@Nonnull Map<@Nullable K, @Nullable V> value, @Nonnull FailableUnaryFunction<@Nullable K, Integer, X> keyCollector, @Nonnull FailableUnaryFunction<@Nullable V, Integer, X> valueCollector) throws X;
    
    @Impure
    @Override
    public <K, V> Integer setNullableMap(@Nullable Map<@Nullable K, @Nullable V> value, @Nonnull FailableUnaryFunction<@Nullable K, Integer, X> keyCollector, @Nonnull FailableUnaryFunction<@Nullable V, Integer, X> valueCollector) throws X {
        return value == null ? setNull(CustomType.MAP) : setMap(value, keyCollector, valueCollector);
    }
    
}
