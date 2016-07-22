package net.digitalid.utility.generator.generators;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.conversion.converter.CustomField;
import net.digitalid.utility.conversion.converter.Declaration;
import net.digitalid.utility.conversion.converter.SelectionResult;
import net.digitalid.utility.conversion.converter.ValueCollector;
import net.digitalid.utility.conversion.converter.types.CustomType;
import net.digitalid.utility.exceptions.UnexpectedFailureException;
import net.digitalid.utility.functional.interfaces.Consumer;
import net.digitalid.utility.functional.interfaces.Producer;
import net.digitalid.utility.functional.interfaces.UnaryFunction;
import net.digitalid.utility.generator.annotations.generators.GenerateConverter;
import net.digitalid.utility.immutable.ImmutableList;
import net.digitalid.utility.testing.CustomTest;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.validation.annotations.generation.Recover;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.size.Size;
import net.digitalid.utility.validation.annotations.type.Immutable;

import org.junit.Test;

import static net.digitalid.utility.conversion.converter.types.CustomType.*;

@Immutable
@GenerateConverter
class VariousFields {
    
    public final boolean flag;
    
    public final int size;
    
    public final String text;
    
    protected VariousFields(boolean flag, int size, @Nonnull String text) {
        this.flag = flag;
        this.size = size;
        this.text = text;
    }
    
}

@GenerateConverter
enum SimpleEnum {
    
    STAR,
    PLANET,
    COMET,
    MOON
    
}

@GenerateConverter
enum EnumWithRecoverMethod {
    
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3);
    
    int id;
    
    EnumWithRecoverMethod(int id) {
        this.id = id;
    }
    
    @Recover
    public static @Nonnull EnumWithRecoverMethod of(int id) {
        switch (id) {
            case 0:
                return ZERO;
            case 1:
                return ONE;
            case 2:
                return TWO;
            case 3:
                return THREE;
        }
        throw UnexpectedFailureException.with("Unexpected id $ found", id);
    }
    
}

class TestDeclaration implements Declaration {
    
    List<@Nonnull CustomField> collectedFields = new ArrayList<>();
    
    @Impure
    @Override
    public void setField(@Nonnull CustomField customField) {
        collectedFields.add(customField);
    }
    
}

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

class TestSelectionResult implements SelectionResult {
    
    final @Nonnull Queue<@Nonnull Object> selectedObjects;
    
    TestSelectionResult(@Nonnull Queue<@Nonnull Object> selectedObjects) {
        this.selectedObjects = selectedObjects;
    }
    
    @Impure
    @Override
    public void getEmpty() {
        
    }
    
    @Impure
    @Override
    public boolean getBoolean() {
        return (boolean) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public byte getInteger08() {
        return (byte) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public short getInteger16() {
        return (short) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public int getInteger32() {
        return (int) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public long getInteger64() {
        return (long) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public @Nullable BigInteger getInteger() {
        return (BigInteger) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public float getDecimal32() {
        return (float) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public double getDecimal64() {
        return (double) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public char getString01() {
        return (char) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public @Nullable @MaxSize(64) String getString64() {
        return (String) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public @Nullable String getString() {
        return (String) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public @Nullable @Size(16) byte[] getBinary128() {
        return (byte[]) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public @Nullable @Size(32) byte[] getBinary256() {
        return (byte[]) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public @Nullable byte[] getBinary() {
        return (byte[]) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public <T> List<T> getList(Producer<T> function) {
        return (List<T>) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public <T> T[] getArray(Producer<T> function) {
        return (T[]) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public <T> Set<T> getSet(Producer<T> function) {
        return (Set<T>) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public <K, V> Map<K, V> getMap(Producer<K> keyFunction, Producer<V> valueFunction) {
        return (Map<K, V>) selectedObjects.poll();
    }
    
}

public class ConverterTest extends CustomTest {
    
    @Test
    public void testFieldsOfClass() {
        final @Nonnull TestDeclaration testDeclaration = new TestDeclaration();
        VariousFieldsConverter.INSTANCE.declare(testDeclaration);
        final @Nonnull CustomField[] customFieldsArray = { 
                CustomField.with(BOOLEAN, "flag", ImmutableList.withElements()),
                CustomField.with(INTEGER32, "size", ImmutableList.withElements()),
                CustomField.with(STRING, "text", ImmutableList.withElements())
        };
        assertEquals(Arrays.asList(customFieldsArray), testDeclaration.collectedFields);
    }
    
    @Test
    public void testValueCollectionOfClass() {
        final @Nonnull VariousFields variousFields = new VariousFields(true, 5, "bla");
        final @Nonnull TestValueCollector testValueCollector = new TestValueCollector();
        VariousFieldsConverter.INSTANCE.convert(variousFields, testValueCollector);
        assertEquals(3, testValueCollector.collectedValues.size());
        assertEquals(Pair.of(true, boolean.class), testValueCollector.collectedValues.get(0));
        assertEquals(Pair.of(5, int.class), testValueCollector.collectedValues.get(1));
        assertEquals(Pair.of("bla", String.class), testValueCollector.collectedValues.get(2));
    }
    
    @Test
    public void testSelectionResultOfClass() {
        final Queue<@Nonnull Object> testQueue = new LinkedList<>();
        testQueue.add(true);
        testQueue.add(5);
        testQueue.add("bla");
        final @Nonnull TestSelectionResult testSelectionResult = new TestSelectionResult(testQueue);
        final @Nonnull VariousFields recoveredObject = VariousFieldsConverter.INSTANCE.recover(testSelectionResult);
        assertEquals(true, recoveredObject.flag);
        assertEquals(5, recoveredObject.size);
        assertEquals("bla", recoveredObject.text);
    }
    
    @Test
    public void testFieldsOfEnum() {
        final @Nonnull TestDeclaration testDeclaration = new TestDeclaration();
        SimpleEnumConverter.INSTANCE.declare(testDeclaration);
        assertEquals(CustomField.with(STRING, "SimpleEnum", ImmutableList.withElements()), testDeclaration.collectedFields.get(0));
    }
    
    @Test
    public void testValueCollectionOfEnum() {
        final @Nonnull TestValueCollector testValueCollector = new TestValueCollector();
        SimpleEnumConverter.INSTANCE.convert(SimpleEnum.COMET, testValueCollector);
        assertEquals(1, testValueCollector.collectedValues.size());
        assertEquals(Pair.of("COMET", String.class), testValueCollector.collectedValues.get(0));
    }
    
    @Test
    public void testSelectionResultOfEnum() {
        final Queue<@Nonnull Object> testQueue = new LinkedList<>();
        testQueue.add(SimpleEnum.COMET.name());
        final @Nonnull TestSelectionResult testSelectionResult = new TestSelectionResult(testQueue);
        final @Nonnull SimpleEnum recoveredObject = SimpleEnumConverter.INSTANCE.recover(testSelectionResult);
        assertEquals(SimpleEnum.COMET, recoveredObject);
    }
    
    @Test
    public void testFieldsOfEnumWithRecoverMethod() {
        final @Nonnull TestDeclaration testDeclaration = new TestDeclaration();
        EnumWithRecoverMethodConverter.INSTANCE.declare(testDeclaration);
        assertEquals(CustomField.with(INTEGER32, "id", ImmutableList.withElements()), testDeclaration.collectedFields.get(0));
    }

    @Test
    public void testValueCollectionOfEnumWithRecoverMethod() {
        final @Nonnull TestValueCollector testValueCollector = new TestValueCollector();
        EnumWithRecoverMethodConverter.INSTANCE.convert(EnumWithRecoverMethod.ONE, testValueCollector);
        assertEquals(1, testValueCollector.collectedValues.size());
        assertEquals(Pair.of(1, int.class), testValueCollector.collectedValues.get(0));
    }

    @Test
    public void testSelectionResultOfEnumWithRecoverMethod() {
        final Queue<@Nonnull Object> testQueue = new LinkedList<>();
        testQueue.add(3);
        final @Nonnull TestSelectionResult testSelectionResult = new TestSelectionResult(testQueue);
        final @Nonnull EnumWithRecoverMethod recoveredObject = EnumWithRecoverMethodConverter.INSTANCE.recover(testSelectionResult);
        assertEquals(EnumWithRecoverMethod.THREE, recoveredObject);
    }
    
}
