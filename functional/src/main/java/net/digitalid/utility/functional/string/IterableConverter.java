package net.digitalid.utility.functional.string;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * This class converts iterables to strings.
 * 
 * TODO: Refactor this class to a builder pattern.
 * TODO: Rename to NullableIterableConverter and make a subclass NonNullableIterableConverter.
 * 
 */
@Stateless
public final class IterableConverter {
    
    /**
     * Converts the given iterable to a string.
     * 
     * @deprecated because the converter should be replaced with the map function of the functional package.
     * 
     * @param iterable the iterable to convert to a string.
     * @param converter the converter applied to each element.
     * @param brackets the brackets used to embrace the list.
     * @param delimiter the delimiter between elements.
     * 
     * @return the given iterable as a string.
     */
    @Pure
    @Deprecated
    public static @Nonnull <E> String toString(@Nonnull Iterable<E> iterable, @Nonnull NonNullableElementConverter<? super E> converter, @Nullable Brackets brackets, @Nonnull String delimiter) {
        final @Nonnull StringBuilder string = new StringBuilder();
        if (brackets != null) { string.append(brackets.getOpening()); }
        for (final @Nonnull E element : iterable) {
            if (brackets == null && string.length() > 0 || brackets != null && string.length() > 1) { string.append(delimiter); }
            string.append(converter.toString(element));
        }
        if (brackets != null) { string.append(brackets.getClosing()); }
        return string.toString();
    }
    
    /**
     * Converts the given iterable to a string.
     *
     * @deprecated because the converter should be replaced with the map function of the functional package.
     * 
     * 
     * @param iterable the iterable to convert to a string.
     * @param converter the converter applied to each element.
     * @param brackets the brackets used to embrace the list.
     * 
     * @return the given iterable as a string.
     */
    @Pure
    @Deprecated
    public static @Nonnull <E> String toString(@Nonnull Iterable<E> iterable, @Nonnull NonNullableElementConverter<? super E> converter, @Nullable Brackets brackets) {
        return toString(iterable, converter, brackets, ", ");
    }
    
    /**
     * Converts the given iterable to a string.
     *
     * @deprecated because the converter should be replaced with the map function of the functional package.
     * 
     * @param iterable the iterable to convert to a string.
     * @param converter the converter applied to each element.
     * 
     * @return the given iterable as a string.
     */
    @Pure
    @Deprecated
    public static @Nonnull <E> String toString(@Nonnull Iterable<E> iterable, @Nonnull NonNullableElementConverter<? super E> converter) {
        return toString(iterable, converter, null);
    }
    
    /**
     * Converts the given iterable to a string.
     *
     * @param iterable the iterable to convert to a string.
     * @param brackets the brackets used to embrace the list.
     * @param delimiter the delimiter between elements.
     *
     * @return the given iterable as a string.
     */
    @Pure
    public static @Nonnull String toString(@Nonnull Iterable<String> iterable, @Nullable Brackets brackets, @Nonnull String delimiter) {
        final @Nonnull StringBuilder string = new StringBuilder();
        if (brackets != null) { string.append(brackets.getOpening()); }
        for (final @Nonnull String element : iterable) {
            if (brackets == null && string.length() > 0 || brackets != null && string.length() > 1) { string.append(delimiter); }
            string.append(element);
        }
        if (brackets != null) { string.append(brackets.getClosing()); }
        return string.toString();
    }
    
    /**
     * Converts the given iterable to a string.
     * 
     * @param iterable the iterable to convert to a string.
     * @param brackets the brackets used to embrace the list.
     * 
     * @return the given iterable as a string.
     */
    @Pure
    public static @Nonnull String toString(@Nonnull Iterable<String> iterable, @Nullable Brackets brackets) {
        return toString(iterable, brackets, ", ");
    }
    
    /**
     * Converts the given iterable to a string.
     * 
     * @param iterable the iterable to convert to a string.
     * @param delimiter the delimiter between elements.
     * 
     * @return the given iterable as a string.
     */
    @Pure
    public static @Nonnull String toString(@Nonnull Iterable<String> iterable, @Nonnull String delimiter) {
        return toString(iterable, null, delimiter);
    }
    
    /**
     * Converts the given iterable to a string.
     * 
     * @param iterable the iterable to convert to a string.
     * 
     * @return the given iterable as a string.
     */
    @Pure
    public static @Nonnull String toString(@Nonnull Iterable<String> iterable) {
        return toString(iterable, (Brackets) null);
    }
    
}
