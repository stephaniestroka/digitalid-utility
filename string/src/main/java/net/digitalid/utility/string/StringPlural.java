package net.digitalid.utility.string;

/**
 * This class provides useful operations to form the plural of words.
 */
public class StringPlural {
    
    /**
     * Forms the plural of the given non-nullable word in singular based on simple heuristics.
     */
    public static String pluralize(String word) {
        final String[][] rules = { { "a", "ae" }, { "an", "en" }, { "ch", "ches" }, { "ex", "ices" }, { "f", "ves" }, { "fe", "ves" }, { "is", "es" }, { "ix", "ices" }, { "s", "ses" }, { "sh", "shes" }, { "um", "a" }, { "x", "xes" }, { "y", "ies" } };
        
        if (word.contains("oo") && !word.equals("book")) { return word.replace("oo", "ee"); }
        for (final String[] rule : rules) {
            if (word.endsWith(rule[0])) {
                return word.substring(0, word.length() - rule[0].length()) + rule[1];
            }
        }
        return word + "s";
    }
    
    /**
     * Prepends the given non-nullable word in singular with the given number (written-out if smaller than 13) and forms the plural if the number is not one.
     */
    public static String prependWithNumberAndPluralize(int number, String word) {
        return NumberString.getCardinal(number) + " " + (number == 1 ? word : pluralize(word));
    }
    
}
