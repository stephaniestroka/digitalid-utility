package net.digitalid.utility.string;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Utiliy;

/**
 * This class provides useful operations to transform numbers into strings.
 */
@Utiliy
public class NumberToString {
    
    /* -------------------------------------------------- Cardinal Numbers -------------------------------------------------- */
    
    private static final @Nonnull String[] cardinalNumbers = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve"};
    
    /**
     * Returns the given number as a cardinal string (written-out if smaller than 13).
     */
    @Pure
    public static final @Nonnull String getCardinal(int number) {
        if (number >= 0 && number <= 12) { return cardinalNumbers[number]; }
        if (number >= -12 && number < 0) { return "minus " + cardinalNumbers[-number]; }
        return String.valueOf(number);
    }
    
    /* -------------------------------------------------- Ordinal Numbers with English Root -------------------------------------------------- */
    
    private static final @Nonnull String[] ordinalNumbersWithEnglishRoot = {"first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth", "tenth", "eleventh", "twelth"};
    
    /**
     * Returns the given number as an ordinal string (written-out if smaller than 13).
     */
    @Pure
    public static final @Nonnull String getOrdinal(int number) {
        if (number >= 1 && number <= 12) { return ordinalNumbersWithEnglishRoot[number - 1]; }
        final int mod10 = number % 10;
        if (mod10 == 1) { return String.valueOf(number) + "st"; }
        if (mod10 == 2) { return String.valueOf(number) + "nd"; }
        if (mod10 == 3) { return String.valueOf(number) + "rd"; }
        return String.valueOf(number) + "th";
    }
    
    /* -------------------------------------------------- Ordinal Numbers with Latin Root -------------------------------------------------- */
    
    private static final @Nonnull String[] ordinalNumbersWithLatinRoot = {"primary", "secondary", "tertiary", "quaternary", "quinary", "senary", "septenary", "octonary", "novenary", "decenary", "undenary", "duodenary"};
    
    /**
     * Returns the given number as an ordinal string with Latin root (written-out if smaller than 13).
     */
    @Pure
    public static final @Nonnull String getOrdinalWithLatinRoot(int number) {
        if (number >= 1 && number <= 12) { return ordinalNumbersWithLatinRoot[number - 1]; }
        return String.valueOf(number) + ".";
    }
    
}
