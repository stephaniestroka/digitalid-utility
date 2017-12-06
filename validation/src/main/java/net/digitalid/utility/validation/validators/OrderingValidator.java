/*
 * Copyright (C) 2017 Synacts GmbH, Switzerland (info@synacts.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.digitalid.utility.validation.validators;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.processing.logging.ErrorLogger;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processing.utility.TypeImporter;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;

/**
 * This class implements common methods for all ordering-related validators.
 * 
 * @see net.digitalid.utility.validation.annotations.order
 */
@Stateless
public abstract class OrderingValidator extends IterableValidator {
    
    /* -------------------------------------------------- Usage Check -------------------------------------------------- */
    
    @Pure
    @Override
    public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull ErrorLogger errorLogger) {
        super.checkUsage(element, annotationMirror, errorLogger);
        
        final @Nullable TypeMirror componentType = ProcessingUtility.getComponentType(element, errorLogger);
        if (componentType != null && !componentType.getKind().isPrimitive() && !ProcessingUtility.isRawSubtype(componentType, Comparable.class)) {
            errorLogger.log("The annotation $ may only be used on arrays and iterables whose component type is comparable, which is not the case for $:", SourcePosition.of(element, annotationMirror), getAnnotationNameWithLeadingAt(), componentType);
        }
    }
    
    /* -------------------------------------------------- Validation -------------------------------------------------- */
    
    /**
     * Returns whether the elements in the given iterable are ordered (excluding null values).
     * 
     * @param strictly whether the ordering is strict (i.e. without equal values).
     * @param ascending whether the ordering is ascending (true) or descending (false).
     * 
     * @return {@code true} if the elements in the iterable are ordered, {@code false} otherwise.
     */
    @Pure
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<? super T>> boolean validate(@NonCaptured @Unmodified @Nullable Iterable<@Nullable T> iterable, boolean strictly, boolean ascending) {
        if (iterable == null) { return true; }
        @Nullable T lastElement = null;
        for (@Nullable T element : iterable) {
            if (element != null) {
                if (lastElement != null) {
                    if (element.compareTo(lastElement) * (ascending ? 1 : -1) < (strictly ? 1 : 0)) { return false; }
                }
                lastElement = element;
            }
        }
        return true;
    }
    
    /**
     * Returns whether the elements in the given array are ordered (excluding null values).
     * 
     * @param strictly whether the ordering is strict (i.e. without equal values).
     * @param ascending whether the ordering is ascending (true) or descending (false).
     * 
     * @return {@code true} if the elements in the array are ordered, {@code false} otherwise.
     */
    @Pure
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<? super T>> boolean validate(@NonCaptured @Unmodified @Nullable @NullableElements T[] array, boolean strictly, boolean ascending) {
        if (array == null) { return true; }
        @Nullable T lastElement = null;
        for (@Nullable T element : array) {
            if (element != null) {
                if (lastElement != null) {
                    if (element.compareTo(lastElement) * (ascending ? 1 : -1) < (strictly ? 1 : 0)) { return false; }
                }
                lastElement = element;
            }
        }
        return true;
    }
    
    /**
     * Returns whether the values in the given array are ordered.
     * 
     * @param strictly whether the ordering is strict (i.e. without equal values).
     * @param ascending whether the ordering is ascending (true) or descending (false).
     * 
     * @return {@code true} if the values in the array are ordered, {@code false} otherwise.
     */
    @Pure
    public static boolean validate(@NonCaptured @Unmodified @Nullable boolean[] array, boolean strictly, boolean ascending) {
        if (array == null || array.length < 2) { return true; }
        else if (strictly && ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1]) { return false; } } }
        else if (!strictly && ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1] && !array[i]) { return false; } } }
        else if (strictly && !ascending) { for (int i = 1; i < array.length; i++) { if(!array[i - 1]) { return false; } } }
        else if (!strictly && !ascending) { for (int i = 1; i < array.length; i++) { if(!array[i - 1] && array[i]) { return false; } } }
        return true;
    }
    
    /**
     * Returns whether the values in the given array are ordered.
     * 
     * @param strictly whether the ordering is strict (i.e. without equal values).
     * @param ascending whether the ordering is ascending (true) or descending (false).
     * 
     * @return {@code true} if the values in the array are ordered, {@code false} otherwise.
     */
    @Pure
    public static boolean validate(@NonCaptured @Unmodified @Nullable char[] array, boolean strictly, boolean ascending) {
        if (array == null || array.length < 2) { return true; }
        else if (strictly && ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1] >= array[i]) { return false; } } }
        else if (!strictly && ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1] > array[i]) { return false; } } }
        else if (strictly && !ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1] <= array[i]) { return false; } } }
        else if (!strictly && !ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1] < array[i]) { return false; } } }
        return true;
    }
    
    /**
     * Returns whether the values in the given array are ordered.
     * 
     * @param strictly whether the ordering is strict (i.e. without equal values).
     * @param ascending whether the ordering is ascending (true) or descending (false).
     * 
     * @return {@code true} if the values in the array are ordered, {@code false} otherwise.
     */
    @Pure
    public static boolean validate(@NonCaptured @Unmodified @Nullable byte[] array, boolean strictly, boolean ascending) {
        if (array == null || array.length < 2) { return true; }
        else if (strictly && ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1] >= array[i]) { return false; } } }
        else if (!strictly && ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1] > array[i]) { return false; } } }
        else if (strictly && !ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1] <= array[i]) { return false; } } }
        else if (!strictly && !ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1] < array[i]) { return false; } } }
        return true;
    }
    
    /**
     * Returns whether the values in the given array are ordered.
     * 
     * @param strictly whether the ordering is strict (i.e. without equal values).
     * @param ascending whether the ordering is ascending (true) or descending (false).
     * 
     * @return {@code true} if the values in the array are ordered, {@code false} otherwise.
     */
    @Pure
    public static boolean validate(@NonCaptured @Unmodified @Nullable short[] array, boolean strictly, boolean ascending) {
        if (array == null || array.length < 2) { return true; }
        else if (strictly && ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1] >= array[i]) { return false; } } }
        else if (!strictly && ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1] > array[i]) { return false; } } }
        else if (strictly && !ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1] <= array[i]) { return false; } } }
        else if (!strictly && !ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1] < array[i]) { return false; } } }
        return true;
    }
    
    /**
     * Returns whether the values in the given array are ordered.
     * 
     * @param strictly whether the ordering is strict (i.e. without equal values).
     * @param ascending whether the ordering is ascending (true) or descending (false).
     * 
     * @return {@code true} if the values in the array are ordered, {@code false} otherwise.
     */
    @Pure
    public static boolean validate(@NonCaptured @Unmodified @Nullable int[] array, boolean strictly, boolean ascending) {
        if (array == null || array.length < 2) { return true; }
        else if (strictly && ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1] >= array[i]) { return false; } } }
        else if (!strictly && ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1] > array[i]) { return false; } } }
        else if (strictly && !ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1] <= array[i]) { return false; } } }
        else if (!strictly && !ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1] < array[i]) { return false; } } }
        return true;
    }
    
    /**
     * Returns whether the values in the given array are ordered.
     * 
     * @param strictly whether the ordering is strict (i.e. without equal values).
     * @param ascending whether the ordering is ascending (true) or descending (false).
     * 
     * @return {@code true} if the values in the array are ordered, {@code false} otherwise.
     */
    @Pure
    public static boolean validate(@NonCaptured @Unmodified @Nullable long[] array, boolean strictly, boolean ascending) {
        if (array == null || array.length < 2) { return true; }
        else if (strictly && ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1] >= array[i]) { return false; } } }
        else if (!strictly && ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1] > array[i]) { return false; } } }
        else if (strictly && !ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1] <= array[i]) { return false; } } }
        else if (!strictly && !ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1] < array[i]) { return false; } } }
        return true;
    }
    
    /**
     * Returns whether the values in the given array are ordered.
     * 
     * @param strictly whether the ordering is strict (i.e. without equal values).
     * @param ascending whether the ordering is ascending (true) or descending (false).
     * 
     * @return {@code true} if the values in the array are ordered, {@code false} otherwise.
     */
    @Pure
    public static boolean validate(@NonCaptured @Unmodified @Nullable float[] array, boolean strictly, boolean ascending) {
        if (array == null || array.length < 2) { return true; }
        else if (strictly && ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1] >= array[i]) { return false; } } }
        else if (!strictly && ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1] > array[i]) { return false; } } }
        else if (strictly && !ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1] <= array[i]) { return false; } } }
        else if (!strictly && !ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1] < array[i]) { return false; } } }
        return true;
    }
    
    /**
     * Returns whether the values in the given array are ordered.
     * 
     * @param strictly whether the ordering is strict (i.e. without equal values).
     * @param ascending whether the ordering is ascending (true) or descending (false).
     * 
     * @return {@code true} if the values in the array are ordered, {@code false} otherwise.
     */
    @Pure
    public static boolean validate(@NonCaptured @Unmodified @Nullable double[] array, boolean strictly, boolean ascending) {
        if (array == null || array.length < 2) { return true; }
        else if (strictly && ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1] >= array[i]) { return false; } } }
        else if (!strictly && ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1] > array[i]) { return false; } } }
        else if (strictly && !ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1] <= array[i]) { return false; } } }
        else if (!strictly && !ascending) { for (int i = 1; i < array.length; i++) { if(array[i - 1] < array[i]) { return false; } } }
        return true;
    }
    
    /*
    
    // Other implementations for arrays with primitive component types are:
    
    @Pure
    public static boolean validate(@NonCaptured @Unmodified @Nullable int[] array, boolean strictly, boolean ascending) {
        if (array == null || array.length < 2) { return true; }
        int lastValue = array[0];
        final int factor = ascending ? 1 : -1;
        for (int i = 1; i < array.length; i++) {
            final int nextValue = array[i];
            if (lastValue * factor >= nextValue * factor && (strictly || lastValue != nextValue)) { return false; }
            lastValue = nextValue;
        }
        return true;
    }
    
    @Pure
    public static boolean validate(@NonCaptured @Unmodified @Nullable int[] array, boolean strictly, boolean ascending) {
        if (array == null) { return true; }
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] * (ascending ? 1 : -1) >= array[i] * (ascending ? 1 : -1) && (strictly || array[i - 1] != array[i])) { return false; }
        }
        return true;
    }
    
    */
    
    /* -------------------------------------------------- Abstract Methods -------------------------------------------------- */
    
    @Pure
    protected abstract boolean isStrictly();
    
    @Pure
    protected abstract boolean isAscending();
    
    /* -------------------------------------------------- Contract Generation -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull TypeImporter typeImporter) {
        return Contract.with(typeImporter.importIfPossible(OrderingValidator.class) + ".validate(#, " + isStrictly() + ", " + isAscending() + ")", "The # has to be ordered.", element);
    }
    
}
