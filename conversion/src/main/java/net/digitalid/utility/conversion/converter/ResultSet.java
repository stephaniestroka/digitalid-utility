package net.digitalid.utility.conversion.converter;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.functional.interfaces.Producer;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.size.Size;

/**
 *
 */
public interface ResultSet {
    
    /* -------------------------------------------------- Getters -------------------------------------------------- */
    
    /**
     * Returns nothing and moves forward to the next result.
     */
    public void getEmpty();
    
    /**
     * Returns the boolean value
     */
    public boolean getBoolean();
    
    /**
     * Returns the byte value and moves forward to the next result.
     */
    public byte getInteger08();
    
    /**
     * Returns the short value and moves forward to the next result.
     */
    public short getInteger16();
    
    /**
     * Returns the int value and moves forward to the next result.
     */
    public int getInteger32();
    
    /**
     * Returns the long value and moves forward to the next result.
     */
    public long getInteger64();
    
    /**
     * Returns the integer value and moves forward to the next result.
     */
    public @Nullable BigInteger getInteger();
    
    /**
     * Returns the float value and moves forward to the next result.
     */
    public float getDecimal32();
    
    /**
     * Returns the double value and moves forward to the next result.
     */
    public double getDecimal64();
    
    /**
     * Returns the char value and moves forward to the next result.
     */
    public char getString01();
    
    /**
     * Returns the string value and moves forward to the next result.
     */
    public @Nullable @MaxSize(64) String getString64();
    
    /**
     * Returns the string value and moves forward to the next result.
     */
    public @Nullable String getString();
    
    /**
     * Returns the binary value and moves forward to the next result.
     */
    public @Nullable @Size(16) byte[] getBinary128();
    
    /**
     * Returns the binary value and moves forward to the next result.
     */
    public @Nullable @Size(32) byte[] getBinary256();
    
    /**
     * Returns the binary value and moves forward to the next result.
     */
    public @Nullable byte[] getBinary();
    
    public <T> @Nonnull List<T> getList(@Nonnull Producer<T> function);
    
    public <T> @Nonnull T[] getArray(@Nonnull Producer<T> function);
    
    public <T> @Nonnull Set<T> getSet(@Nonnull Producer<T> function);
    
    public <K, V> @Nonnull Map<K, V> getMap(@Nonnull Producer<K> keyFunction, @Nonnull Producer<V> valueFunction);
    
}
