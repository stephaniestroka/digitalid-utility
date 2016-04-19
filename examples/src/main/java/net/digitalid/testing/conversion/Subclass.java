package net.digitalid.testing.conversion;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.size.MinSize;
import net.digitalid.utility.collections.freezable.FreezableList;
import net.digitalid.utility.collections.readonly.ReadOnlyList;
import net.digitalid.utility.conversion.annotations.Constructing;
import net.digitalid.utility.conversion.annotations.ConvertToConvertibleType;
import net.digitalid.utility.conversion.annotations.GenericTypes;
import net.digitalid.utility.conversion.annotations.IgnoreForConversion;
import net.digitalid.utility.generator.annotations.Invariant;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.annotations.method.Pure;

import com.google.j2objc.annotations.Property;

/**
 * The subclass with integers and a list.
 */
@Mutable
public class Subclass extends Superclass {
    
    /* -------------------------------------------------- Integers -------------------------------------------------- */
    
    /**
     * Stores a first integer.
     */
    @Property("readonly")
    public final @NonNegative int first;
    
    /**
     * Stores a second integer.
     */
    @Property("readonly")
    @ConvertToConvertibleType(IntLongMapper.class)
    public final @Positive int second;
    
    /* -------------------------------------------------- List -------------------------------------------------- */
    
    /**
     * Stores a freezable list of strings.
     */
    @IgnoreForConversion
    private final @Nonnull @NonNullableElements @NonFrozen @MaxSize(10) FreezableList<String> privateList;
    
    /**
     * Stores a read-only list of strings.
     */
    @Property("readonly")
    @GenericTypes(String.class)
    public final @Nonnull @NonNullableElements @NonFrozen @MaxSize(10) ReadOnlyList<String> list;
    
    /**
     * Adds the given string to the list.
     * 
     * @param string the string to be added.
     */
    public final void addString(@Nonnull String string) {
        privateList.add(string);
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new subclass object.
     * 
     * @param name the name of the new object.
     * @param first a first integer.
     * @param second a second integer.
     * @param list a freezable list of strings.
     */
    protected Subclass(@Nonnull @MinSize(6) @MaxSize(10) String name, @NonNegative int first, @Positive int second, @Captured @Nonnull @NonNullableElements @NonFrozen @MaxSize(10) FreezableList<String> list) {
        super(name);
        
        this.first = first;
        this.second = second;
        this.privateList = list;
        this.list = list;
    }
    
    /**
     * Returns a new subclass object.
     * 
     * @param name the name of the new object.
     * @param first a first integer.
     * @param second a second integer.
     * @param list a freezable list of strings.
     * 
     * @return a new subclass object.
     */
    @Pure
//    @Recover
//    @Recovery
//    @Recovering
    @Constructing
    public static @Nonnull Subclass get(@Nonnull @MinSize(6) @MaxSize(10) String name, @NonNegative int first, @Positive int second, @Captured @Nonnull @NonNullableElements @NonFrozen @MaxSize(10) FreezableList<String> list) {
        return new Subclass(name, first, second, list);
    }
    
    /**
     * Returns whether the invariant holds.
     * 
     * @return whether the invariant holds.
     */
    @Pure
    @Invariant
    protected boolean invariant() {
        return first < second;
    }
    
}
