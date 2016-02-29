package net.digitalid.utility.generator.information.filter;

import javax.annotation.Nonnull;

import net.digitalid.utility.generator.information.ElementInformation;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;

/**
 * Matches all ElementInformations with certain names.
 */
public class ElementInformationNameMatcher<E extends ElementInformation> implements FilterCondition<E> {
    
    /**
     * The names of the elements for which matching element information objects are filtered.
     */
    private @Nonnull @NonNullableElements String[] names;
    
    /**
     * Creates a new element information name matcher with a given array of names.
     */
    private ElementInformationNameMatcher(@Nonnull @NonNullableElements String... names) {
        this.names = names;
    }
    
    /**
     * Returns a new element information name matcher with a given array of names.
     */
    public static @Nonnull <E extends ElementInformation> ElementInformationNameMatcher<E> withNames(@Nonnull String... names) {
        return new ElementInformationNameMatcher<>(names);
    }
    
    @Override 
    public boolean filter(@Nonnull E elementInformation) {
        for (@Nonnull String name : names) {
            if (elementInformation.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
}
