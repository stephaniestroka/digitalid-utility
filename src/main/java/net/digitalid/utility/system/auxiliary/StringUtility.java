package net.digitalid.utility.system.auxiliary;

import javax.annotation.Nonnull;
import net.digitalid.utility.annotations.state.Pure;
import net.digitalid.utility.annotations.state.Stateless;

/**
 * This class provides useful operations on strings.
 */
@Stateless
public final class StringUtility {
    
    /**
     * Returns whether the given word starts with any of the given prefixes.
     * 
     * @param word the word whose beginning is checked for all the prefixes.
     * @param prefixes the prefixes with which the given word might start.
     * 
     * @return whether the given word starts with any of the given prefixes.
     */
    @Pure
    public static boolean startsWithAny(@Nonnull String word, @Nonnull String... prefixes) {
        for (final @Nonnull String prefix : prefixes) {
            if (word.startsWith(prefix)) { return true; }
        }
        return false;
    }
    
    /**
     * Prepends the given word with the appropriate indefinite article based on a simple heuristic.
     * 
     * @param word the word which is to be prepended with the appropriate indefinite article.
     * @param uppercase whether the first letter of the indefinite article shall be in uppercase.
     * 
     * @return the given word prepended with the appropriate indefinite article and separated by a space.
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
     * 
     * @param word the word which is to be prepended with the appropriate indefinite article.
     * 
     * @return the given word prepended with the appropriate indefinite article in lowercase.
     */
    @Pure
    public static @Nonnull String prependWithIndefiniteArticle(@Nonnull String word) {
        return prependWithIndefiniteArticle(word, false);
    }
    
    /**
     * Returns the phrase with the first letter of each word in uppercase.
     * 
     * @param phrase the phrase whose first letters are to be capitalized.
     * 
     * @return the phrase with the first letter of each word in uppercase.
     */
    @Pure
    public static @Nonnull String capitalizeFirstLetters(@Nonnull String phrase) {
        final @Nonnull StringBuilder string = new StringBuilder(phrase);
        int index = 0;
        do {
            string.replace(index, index + 1, string.substring(index, index + 1).toUpperCase());
            index =  string.indexOf(" ", index) + 1;
        } while (index > 0 && index < string.length());
        return string.toString();
    }
    
}
