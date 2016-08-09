package net.digitalid.utility.conversion.converter;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.functional.interfaces.Producer;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.size.Size;

/**
 * The selection result is used to recover values from another data source, e.g. an SQL data base or an XDF block.
 */
public interface SelectionResult {
    
    /* -------------------------------------------------- Getters -------------------------------------------------- */
    
    /**
     * Returns nothing and moves forward to the next result.
     */
    @Impure
    public void getEmpty();
    
    /**
     * Returns the boolean value
     */
    @Impure
    public boolean getBoolean();
    
    /**
     * Returns the byte value and moves forward to the next result.
     */
    @Impure
    public byte getInteger08();
    
    /**
     * Returns the short value and moves forward to the next result.
     */
    @Impure
    public short getInteger16();
    
    /**
     * Returns the int value and moves forward to the next result.
     */
    @Impure
    public int getInteger32();
    
    /**
     * Returns the long value and moves forward to the next result.
     */
    @Impure
    public long getInteger64();
    
    /**
     * Returns the integer value and moves forward to the next result.
     */
    @Impure
    public @Nullable BigInteger getInteger();
    
    /**
     * Returns the float value and moves forward to the next result.
     */
    @Impure
    public float getDecimal32();
    
    /**
     * Returns the double value and moves forward to the next result.
     */
    @Impure
    public double getDecimal64();
    
    /**
     * Returns the char value and moves forward to the next result.
     */
    @Impure
    public char getString01();
    
    /**
     * Returns the string value and moves forward to the next result.
     */
    @Impure
    public @Nullable @MaxSize(64) String getString64();
    
    /**
     * Returns the string value and moves forward to the next result.
     */
    @Impure
    public @Nullable String getString();
    
    /**
     * Returns the binary value and moves forward to the next result.
     */
    @Impure
    public @Nullable @Size(16) byte[] getBinary128();
    
    /**
     * Returns the binary value and moves forward to the next result.
     */
    @Impure
    public @Nullable @Size(32) byte[] getBinary256();
    
    /**
     * Returns the binary value and moves forward to the next result.
     */
    @Impure
    public @Nullable byte[] getBinary();
    
    @Impure
    public <T> @Nonnull List<T> getList(@Nonnull Producer<T> function);
    
    @Impure
    public <T> @Nonnull T[] getArray(@Nonnull Producer<T> function);
    
    @Impure
    public <T> @Nonnull Set<T> getSet(@Nonnull Producer<T> function);
    
    @Impure
    public <K, V> @Nonnull Map<K, V> getMap(@Nonnull Producer<K> keyFunction, @Nonnull Producer<V> valueFunction);
    
    @Impure
    public boolean moveToNextRow();
    
    @Impure
    public void moveToFirstColumn();
    
}
