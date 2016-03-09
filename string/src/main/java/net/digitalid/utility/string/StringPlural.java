package net.digitalid.utility.string;

import java.util.Map;

import net.digitalid.utility.immutable.collections.ImmutableMap;

/**
 * This class provides useful operations to form the plural of words.
 */
public class StringPlural {
    
    private static final ImmutableMap<String, String> rules = ImmutableMap.with("a", "ae").with("an", "en").with("ch", "ches").with("ex", "ices").with("f", "ves").with("fe", "ves").with("is", "es").with("ix", "ices").with("s", "ses").with("sh", "shes").with("um", "a").with("x", "xes").with("y", "ies").build();
    
    /**
     * Forms the plural of the given non-nullable word in singular based on simple heuristics.
     */
    public static String pluralize(String word) {
        if (word.contains("oo") && !word.equals("book")) { return word.replace("oo", "ee"); }
        for (Map.Entry<String, String> rule : rules.entrySet()) {
            if (word.endsWith(rule.getKey())) {
                return word.substring(0, word.length() - rule.getKey().length()) + rule.getValue();
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
