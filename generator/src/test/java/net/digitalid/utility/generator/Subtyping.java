package net.digitalid.utility.generator;

import java.util.LinkedList;
import java.util.List;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.testing.TestTypes;
import net.digitalid.utility.validation.annotations.testing.NotSubtypeOf;
import net.digitalid.utility.validation.annotations.testing.SubtypeOf;
import net.digitalid.utility.validation.annotations.type.Immutable;

@Immutable
public abstract class Subtyping {
    
    /* -------------------------------------------------- Objects -------------------------------------------------- */
    
    @Pure
    public abstract @SubtypeOf(String.class) String getStringSubtypeOfString();
    
    @Pure
    public abstract @SubtypeOf(CharSequence.class) String getStringSubtypeOfCharSequence();
    
    @Pure
    public abstract @SubtypeOf(Comparable.class) String getStringSubtypeOfComparable();
    
    @Pure
    public abstract @NotSubtypeOf(Integer.class) String getStringNotSubtypeOfInteger();
    
    @Pure
    public abstract @NotSubtypeOf(int.class) String getStringNotSubtypeOfInt();
    
    @Pure
    public abstract @NotSubtypeOf(List.class) String getStringNotSubtypeOfList();
    
    @Pure
    public abstract @NotSubtypeOf(String[].class) String getStringNotSubtypeOfStringArray();
    
    @Pure
    public abstract @NotSubtypeOf(String[][].class) String getStringNotSubtypeOfNestedStringArray();
    
    /* -------------------------------------------------- Primitives -------------------------------------------------- */
    
    @Pure
    public abstract @SubtypeOf(int.class) int getIntSubtypeOfInt();
    
    @Pure
    public abstract @NotSubtypeOf(Integer.class) int getIntNotSubtypeOfInteger();
    
    @Pure
    public abstract @NotSubtypeOf(int.class) Integer getIntegerNotSubtypeOfInt();
    
    @Pure
    public abstract @NotSubtypeOf(Comparable.class) int getIntNotSubtypeOfComparable();
    
    @Pure
    public abstract @SubtypeOf(long.class) int getIntSubtypeOfLong();
    
    @Pure
    public abstract @NotSubtypeOf(short.class) int getIntNotSubtypeOfShort();
    
    @Pure
    public abstract @NotSubtypeOf(String.class) int getIntNotSubtypeOfString();
    
    @Pure
    public abstract @NotSubtypeOf(List.class) int getIntNotSubtypeOfList();
    
    @Pure
    public abstract @NotSubtypeOf(int[].class) int getIntNotSubtypeOfIntArray();
    
    @Pure
    public abstract @NotSubtypeOf(int[][].class) int getIntNotSubtypeOfNestedIntArray();
    
    /* -------------------------------------------------- Arrays -------------------------------------------------- */
    
    @Pure
    public abstract @SubtypeOf(int[].class) int[] getIntArraySubtypeOfIntArray();
    
    @Pure
    public abstract @SubtypeOf(Object.class) int[] getIntArraySubtypeOfObject();
    
    @Pure
    public abstract @SubtypeOf(int[][].class) int[][] getNestedIntArraySubtypeOfNestedIntArray();
    
    @Pure
    public abstract @SubtypeOf(Object[].class) int[][] getNestedIntArraySubtypeOfObjectArray();
    
    @Pure
    public abstract @SubtypeOf(String[].class) String[] getStringArraySubtypeOfStringArray();
    
    @Pure
    public abstract @SubtypeOf(Object[].class) String[] getStringArraySubtypeOfObjectArray();
    
    @Pure
    public abstract @SubtypeOf(Object.class) String[] getStringArraySubtypeOfObject();
    
    @Pure
    public abstract @SubtypeOf(String[][].class) String[][] getNestedStringArraySubtypeOfNestedStringArray();
    
    @Pure
    public abstract @SubtypeOf(Object[][].class) String[][] getNestedStringArraySubtypeOfNestedObjectArray();
    
    @Pure
    public abstract @SubtypeOf(Object[].class) String[][] getNestedStringArraySubtypeOfObjectArray();
    
    @Pure
    public abstract @NotSubtypeOf(int.class) int[] getIntArrayNotSubtypeOfInt();
    
    @Pure
    public abstract @NotSubtypeOf(long[].class) int[] getIntArrayNotSubtypeOfLongArray();
    
    @Pure
    public abstract @NotSubtypeOf(Integer[].class) int[] getIntArrayNotSubtypeOfIntegerArray();
    
    @Pure
    public abstract @NotSubtypeOf(int[][].class) int[] getIntArrayNotSubtypeOfNestedIntArray();
    
    @Pure
    public abstract @NotSubtypeOf(int[].class) int[][] getNestedIntArrayNotSubtypeOfIntArray();
    
    @Pure
    public abstract @NotSubtypeOf(String.class) String[] getStringArrayNotSubtypeOfString();
    
    @Pure
    public abstract @NotSubtypeOf(String[].class) Object[] getObjectArrayNotSubtypeOfStringArray();
    
    @Pure
    public abstract @NotSubtypeOf(String[][].class) String[] getStringArrayNotSubtypeOfNestedStringArray();
    
    @Pure
    public abstract @NotSubtypeOf(String[].class) String[][] getNestedStringArrayNotSubtypeOfStringArray();
    
    /* -------------------------------------------------- Generics -------------------------------------------------- */
    
    @Pure
    public abstract @SubtypeOf(List.class) List<String> getStringListSubtypeOfList();
    
    @Pure
    public abstract @SubtypeOf(List[].class) List<String>[] getStringListArraySubtypeOfListArray();
    
    @Pure
    public abstract @SubtypeOf(Comparable[].class) Integer[] getIntegerArraySubtypeOfComparableArray();
    
    @Pure
    public abstract @NotSubtypeOf(LinkedList.class) List<String> getStringListNotSubtypeOfLinkedList();
    
    /* -------------------------------------------------- Nested -------------------------------------------------- */
    
    @Pure
    public abstract TestTypes.@SubtypeOf(TestTypes.NestedSuperclass.class) NestedSuperclass getNestedSuperclassSubtypeOfNestedSuperclass();
    
    @Pure
    public abstract TestTypes.@SubtypeOf(TestTypes.NestedSuperclass.class) NestedSubclass getNestedSubclassSubtypeOfNestedSuperclass();
    
    @Pure
    public abstract TestTypes.@SubtypeOf(TestTypes.InnerSuperclass.class) InnerSuperclass getInnerSuperclassSubtypeOfInnerSuperclass();
    
    @Pure
    public abstract TestTypes.@SubtypeOf(TestTypes.InnerSuperclass.class) InnerSubclass getInnerSubclassSubtypeOfInnerSuperclass();
    
    @Pure
    public abstract TestTypes.@NotSubtypeOf(TestTypes.NestedSubclass.class) NestedSuperclass getNestedSuperclassNotSubtypeOfNestedSubclass();
    
    @Pure
    public abstract TestTypes.@NotSubtypeOf(TestTypes.InnerSubclass.class) InnerSuperclass getInnerSuperclassNotSubtypeOfInnerSubclass();
    
    @Pure
    public abstract TestTypes.@NotSubtypeOf(TestTypes.InnerSuperclass.class) NestedSuperclass getNestedSuperclassNotSubtypeOfInnerSuperclass();
    
    @Pure
    public abstract TestTypes.@NotSubtypeOf(TestTypes.NestedSuperclass.class) InnerSuperclass getInnerSuperclassNotSubtypeOfNestedSuperclass();
    
}
