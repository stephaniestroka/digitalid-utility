package net.digitalid.utility.generator.generators.converter;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.conversion.converter.SelectionResult;
import net.digitalid.utility.functional.interfaces.Producer;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.size.Size;

/**
 * The selection result that is used to test the functionality of the converter.
 */
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
