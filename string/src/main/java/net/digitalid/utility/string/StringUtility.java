package net.digitalid.utility.string;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

import java.lang.reflect.Field;

/**
 * This class provides useful operations on strings.
 */
@Stateless
public final class StringUtility {

    /**
     * A set of styles which can be used for the string representation.
     */
    public static enum RepresentationStyle {
        DEFAULT
    }

    /**
     * Returns the field value or a &lt;failed to access&gt; error message if the field cannot be accessed.
     */
    private static @Nonnull Object getFieldValueOrErrorMessage(@Nonnull Field field, @Nonnull Object object) {
        field.setAccessible(true);
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            return "<failed to access>";
        }
    }

    /**
     * Returns a doubly-quoted string if the field value is a string, otherwise, toString() is called on the object.
     */
    private static @Nonnull String transformFieldValue(@Nonnull Object fieldValue) {
        if (String.class.isInstance(fieldValue)) {
            return "\"" + fieldValue + "\"";
        } else {
            return fieldValue.toString();
        }
    }

    /**
     * Stores a representation of the given object in the given {@link RepresentationStyle style} into the given string builder.
     */
    public static void toString(@Nonnull Object object, @Nonnull RepresentationStyle style, @Nonnull StringBuilder string) {
        final @Nonnull Class<?> type = object.getClass();
        final @Nonnull String typeName = type.getSimpleName();
        final @Nonnull Field[] fields = type.getDeclaredFields();
        final int numberOfFields = fields.length;
        switch (style) {
            case DEFAULT:
                string.append(typeName).append("(");
                if (numberOfFields > 0) {
                    for (int i = 0; i < numberOfFields - 1; i++) {
                        final @Nonnull Field field = fields[i];
                        string.append(field.getName()).append(": \"").append(transformFieldValue(getFieldValueOrErrorMessage(field, object))).append("\"");
                        string.append(fields[i]).append(", ");
                    }
                    Field lastField = fields[numberOfFields - 1];
                    string.append(lastField.getName()).append(": \"").append(transformFieldValue(getFieldValueOrErrorMessage(lastField, object))).append("\"");
                }
                string.append(")");
            break;
        }
    }

    /* -------------------------------------------------- Pluralization -------------------------------------------------- */
    
    /**
     * Pluralizes the given word based on simple heuristics.
     * 
     * @param word the word in singular to be pluralized.
     * 
     * @return the word in plural based on simple heuristics.
     */
    @Pure
    public static @Nonnull String pluralize(@Nonnull String word) {
        final @Nonnull String[][] rules = { { "a", "ae" }, { "an", "en" }, { "ch", "ches" }, { "ex", "ices" }, { "f", "ves" }, { "fe", "ves" }, { "is", "es" }, { "ix", "ices" }, { "s", "ses" }, { "sh", "shes" }, { "um", "a" }, { "x", "xes" }, { "y", "ies" } };
        
        if (word.contains("oo")) { return word.replace("oo", "ee"); }
        for (final @Nonnull String[] rule : rules) {
            if (word.endsWith(rule[0])) {
                return word.substring(0, word.length() - rule[0].length()) + rule[1];
            }
        }
        return word + "s";
    }
    
    /**
     * Prepends the given word with the given number (written-out if smaller than 13).
     * 
     * @param number the number which is to be prepended to the given singular word.
     * @param word the word in singular which is to be prepended by the given number.
     * 
     * @return the potentially pluralized word prepended with the given written-out number.
     */
    @Pure
    public static @Nonnull String prependWithNumber(@NonNegative int number, @Nonnull String word) {
        assert number >= 0 : "The given number is non-negative.";
        
        final @Nonnull String[] numbers = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve" };
        return (number < numbers.length ? numbers[number] : number) + " " + (number == 1 ? word : pluralize(word));
    }
    
    /* -------------------------------------------------- Prefixes -------------------------------------------------- */
    
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
    
    /* -------------------------------------------------- Capitalization -------------------------------------------------- */
    
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
    
    /**
     * Returns the word in lower case with spaces.
     * 
     * @param word the word which is in camel case.
     * 
     * @return the word in lower case with spaces.
     */
    @Pure
    public static @Nonnull String decamelize(@Nonnull String word) {
        final @Nonnull StringBuilder string = new StringBuilder(word);
        for (int index = 0; index < string.length(); index++) {
            if (Character.isUpperCase(string.charAt(index))) {
                string.replace(index, index + 1, string.substring(index, index + 1).toLowerCase());
                if (index > 0) { string.insert(index, ' '); }
            }
        }
        return string.toString();
    }
    
}
