package net.digitalid.utility.string;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Utiliy;

/**
 * This class provides useful operations to form the plural of words.
 */
@Utiliy
public class StringPlural {
    
    /**
     * Forms the plural of the given word in singular based on simple heuristics.
     */
    @Pure
    public static @Nonnull String pluralize(@Nonnull String word) {
        final @Nonnull String[][] rules = { { "a", "ae" }, { "an", "en" }, { "ch", "ches" }, { "ex", "ices" }, { "f", "ves" }, { "fe", "ves" }, { "is", "es" }, { "ix", "ices" }, { "s", "ses" }, { "sh", "shes" }, { "um", "a" }, { "x", "xes" }, { "y", "ies" } };
        
        if (word.contains("oo") && !word.equals("book")) { return word.replace("oo", "ee"); }
        for (final @Nonnull String[] rule : rules) {
            if (word.endsWith(rule[0])) {
                return word.substring(0, word.length() - rule[0].length()) + rule[1];
            }
        }
        return word + "s";
    }
    
    /**
     * Prepends the given word in singular with the given number (written-out if smaller than 13) and forms the plural if the number is not one.
     */
    @Pure
    public static @Nonnull String prependWithNumberAndPluralize(int number, @Nonnull String word) {
        return NumberString.getCardinal(number) + " " + (number == 1 ? word : pluralize(word));
    }
    
}
