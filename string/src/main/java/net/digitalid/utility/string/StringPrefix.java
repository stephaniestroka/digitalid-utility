package net.digitalid.utility.string;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Utiliy;

/**
 * This class provides useful operations to transform numbers into strings.
 */
@Utiliy
public class StringPrefix {
    
    /* -------------------------------------------------- Common Methods -------------------------------------------------- */
    
    /**
     * Returns the longest common prefix of the given strings.
     */
    @Pure
    public static @Nonnull String longestCommonPrefix(@Nonnull String... strings) {
        if (strings.length == 0) { return ""; }
        @Nonnull String prefix = strings[0];
        for (int s = 1; s < strings.length; s++) {
            final @Nonnull String string = strings[s];
            final int minLength = Math.min(prefix.length(), string.length());
            for (int c = 0; c < minLength; c++) {
                if (prefix.charAt(c) != string.charAt(c)) {
                    prefix = prefix.substring(0, c);
                }
            }
            prefix = prefix.substring(0, minLength);
        }
        return prefix;
    }
    
    /**
     * Returns whether the given word starts with any of the given prefixes.
     */
    @Pure
    public static boolean startsWithAny(@Nonnull String word, @Nonnull String... prefixes) {
        for (final @Nonnull String prefix : prefixes) {
            if (word.startsWith(prefix)) { return true; }
        }
        return false;
    }
    
    /* -------------------------------------------------- Indefinite Article -------------------------------------------------- */
    
    /**
     * Prepends the given word with the appropriate indefinite article in upper or lower case based on a simple heuristic.
     */
    @Pure
    public static @Nonnull String prependWithIndefiniteArticle(@Nonnull String word, boolean uppercase) {
        final @Nonnull String a = (uppercase ? "A" : "a") + " " + word, an = (uppercase ? "An" : "an") + " " + word;
        final @Nonnull String lowercaseWord = word.toLowerCase();
        
        final @Nonnull String[] prefixesForAn = { "hon", "heir" };
        final @Nonnull String[] prefixesForA = { "us", "univ" };
        final @Nonnull String[] vowels = { "a", "e", "i", "o", "u" };
        
        if (startsWithAny(lowercaseWord, prefixesForAn)) { return an; }
        if (startsWithAny(lowercaseWord, prefixesForA)) { return a; }
        if (startsWithAny(lowercaseWord, vowels)) { return an; }
        else { return a; }
    }
    
    /**
     * Prepends the given word with the appropriate indefinite article written all lowercase.
     */
    @Pure
    public static @Nonnull String prependWithIndefiniteArticle(@Nonnull String word) {
        return prependWithIndefiniteArticle(word, false);
    }
    
}
