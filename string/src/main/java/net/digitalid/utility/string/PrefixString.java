package net.digitalid.utility.string;

/**
 * This class provides useful operations to transform numbers into strings.
 */
public class PrefixString {
    
    /* -------------------------------------------------- Common Methods -------------------------------------------------- */
    
    /**
     * Returns the longest common prefix of the given non-nullable strings.
     */
    public static String longestCommonPrefix(String... strings) {
        if (strings.length == 0) { return ""; }
        String prefix = strings[0];
        string: for (int s = 1; s < strings.length; s++) {
            final String string = strings[s];
            final int minLength = Math.min(prefix.length(), string.length());
            character: for (int c = 0; c < minLength; c++) {
                if (prefix.charAt(c) != string.charAt(c)) {
                    prefix = prefix.substring(0, c);
                    continue string;
                }
            }
            prefix = prefix.substring(0, minLength);
        }
        return prefix;
    }
    
    /**
     * Returns whether the given non-nullable word starts with any of the given non-nullable prefixes.
     */
    public static boolean startsWithAny(String word, String... prefixes) {
        for (final String prefix : prefixes) {
            if (word.startsWith(prefix)) { return true; }
        }
        return false;
    }
    
    /* -------------------------------------------------- Indefinite Article -------------------------------------------------- */
    
    /**
     * Prepends the given non-nullable word with the appropriate indefinite article in upper or lower case based on a simple heuristic.
     */
    public static String withIndefiniteArticle(String word, boolean uppercase) {
        final String a = (uppercase ? "A" : "a") + " " + word, an = (uppercase ? "An" : "an") + " " + word;
        final String lowercaseWord = word.toLowerCase();
        
        final String[] prefixesForAn = { "hon", "heir" };
        final String[] prefixesForA = { "us", "univ" };
        final String[] vowels = { "a", "e", "i", "o", "u" };
        
        if (startsWithAny(lowercaseWord, prefixesForAn)) { return an; }
        if (startsWithAny(lowercaseWord, prefixesForA)) { return a; }
        if (startsWithAny(lowercaseWord, vowels)) { return an; }
        else { return a; }
    }
    
    /**
     * Prepends the given non-nullable word with the appropriate indefinite article written all lowercase.
     */
    public static String withIndefiniteArticle(String word) {
        return withIndefiniteArticle(word, false);
    }
    
}
