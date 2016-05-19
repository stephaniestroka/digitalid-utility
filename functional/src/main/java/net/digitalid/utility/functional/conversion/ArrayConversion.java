package net.digitalid.utility.functional.conversion;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * This class facilitates the conversion of arrays with primitive types to and from reference types.
 */
@Utility
public class ArrayConversion {
    
    /* -------------------------------------------------- Boxing -------------------------------------------------- */
    
    /**
     * Boxes the values of the given array or propagates null.
     */
    @Pure
    public static Boolean[] box(boolean[] array) {
        if (array == null) { return null; }
        final @Nonnull Boolean[] result = new Boolean[array.length];
        for (int i = 0; i < array.length; i++) { result[i] = array[i]; }
        return result;
    }
    
    /**
     * Boxes the values of the given array or propagates null.
     */
    @Pure
    public static Character[] box(char[] array) {
        if (array == null) { return null; }
        final @Nonnull Character[] result = new Character[array.length];
        for (int i = 0; i < array.length; i++) { result[i] = array[i]; }
        return result;
    }
    
    /**
     * Boxes the values of the given array or propagates null.
     */
    @Pure
    public static Byte[] box(byte[] array) {
        if (array == null) { return null; }
        final @Nonnull Byte[] result = new Byte[array.length];
        for (int i = 0; i < array.length; i++) { result[i] = array[i]; }
        return result;
    }
    
    /**
     * Boxes the values of the given array or propagates null.
     */
    @Pure
    public static Short[] box(short[] array) {
        if (array == null) { return null; }
        final @Nonnull Short[] result = new Short[array.length];
        for (int i = 0; i < array.length; i++) { result[i] = array[i]; }
        return result;
    }
    
    /**
     * Boxes the values of the given array or propagates null.
     */
    @Pure
    public static Integer[] box(int[] array) {
        if (array == null) { return null; }
        final @Nonnull Integer[] result = new Integer[array.length];
        for (int i = 0; i < array.length; i++) { result[i] = array[i]; }
        return result;
    }
    
    /**
     * Boxes the values of the given array or propagates null.
     */
    @Pure
    public static Long[] box(long[] array) {
        if (array == null) { return null; }
        final @Nonnull Long[] result = new Long[array.length];
        for (int i = 0; i < array.length; i++) { result[i] = array[i]; }
        return result;
    }
    
    /**
     * Boxes the values of the given array or propagates null.
     */
    @Pure
    public static Float[] box(float[] array) {
        if (array == null) { return null; }
        final @Nonnull Float[] result = new Float[array.length];
        for (int i = 0; i < array.length; i++) { result[i] = array[i]; }
        return result;
    }
    
    /**
     * Boxes the values of the given array or propagates null.
     */
    @Pure
    public static Double[] box(double[] array) {
        if (array == null) { return null; }
        final @Nonnull Double[] result = new Double[array.length];
        for (int i = 0; i < array.length; i++) { result[i] = array[i]; }
        return result;
    }
    
    /* -------------------------------------------------- Unboxing -------------------------------------------------- */
    
    /**
     * Unboxes the values of the given array or propagates null.
     * 
     * @throws NullPointerException if the array contains null.
     */
    @Pure
    public static boolean[] unbox(Boolean[] array) {
        if (array == null) { return null; }
        final @Nonnull boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) { result[i] = array[i]; }
        return result;
    }
    
    /**
     * Unboxes the values of the given array or propagates null.
     * 
     * @throws NullPointerException if the array contains null.
     */
    @Pure
    public static char[] unbox(Character[] array) {
        if (array == null) { return null; }
        final @Nonnull char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) { result[i] = array[i]; }
        return result;
    }
    
    /**
     * Unboxes the values of the given array or propagates null.
     * 
     * @throws NullPointerException if the array contains null.
     */
    @Pure
    public static byte[] unbox(Byte[] array) {
        if (array == null) { return null; }
        final @Nonnull byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) { result[i] = array[i]; }
        return result;
    }
    
    /**
     * Unboxes the values of the given array or propagates null.
     * 
     * @throws NullPointerException if the array contains null.
     */
    @Pure
    public static short[] unbox(Short[] array) {
        if (array == null) { return null; }
        final @Nonnull short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++) { result[i] = array[i]; }
        return result;
    }
    
    /**
     * Unboxes the values of the given array or propagates null.
     * 
     * @throws NullPointerException if the array contains null.
     */
    @Pure
    public static int[] unbox(Integer[] array) {
        if (array == null) { return null; }
        final @Nonnull int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) { result[i] = array[i]; }
        return result;
    }
    
    /**
     * Unboxes the values of the given array or propagates null.
     * 
     * @throws NullPointerException if the array contains null.
     */
    @Pure
    public static long[] unbox(Long[] array) {
        if (array == null) { return null; }
        final @Nonnull long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) { result[i] = array[i]; }
        return result;
    }
    
    /**
     * Unboxes the values of the given array or propagates null.
     * 
     * @throws NullPointerException if the array contains null.
     */
    @Pure
    public static float[] unbox(Float[] array) {
        if (array == null) { return null; }
        final @Nonnull float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) { result[i] = array[i]; }
        return result;
    }
    
    /**
     * Unboxes the values of the given array or propagates null.
     * 
     * @throws NullPointerException if the array contains null.
     */
    @Pure
    public static double[] unbox(Double[] array) {
        if (array == null) { return null; }
        final @Nonnull double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) { result[i] = array[i]; }
        return result;
    }
    
}
