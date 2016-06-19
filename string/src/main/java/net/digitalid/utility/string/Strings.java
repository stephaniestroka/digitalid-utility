package net.digitalid.utility.string;

import java.lang.reflect.Field;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.circumfixes.Brackets;
import net.digitalid.utility.circumfixes.Quotes;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.immutable.ImmutableList;
import net.digitalid.utility.immutable.ImmutableMap;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.validation.annotations.size.NonEmpty;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * This class provides useful operations on strings.
 */
@Utility
public class Strings {
    
    /* -------------------------------------------------- Formatting -------------------------------------------------- */
    
    /**
     * Formats the given message by replacing each given symbol with the corresponding argument surrounded by the given quotes.
     */
    @Pure
    public static @Nonnull String format(@Nonnull CharSequence message, char symbol, @Nullable Quotes quotes, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        final @Nonnull StringBuilder string = new StringBuilder(message);
        int stringIndex = 0;
        int argumentIndex = 0;
        while (stringIndex < string.length() && argumentIndex < arguments.length) {
            if (string.charAt(stringIndex) == symbol) {
                final @Nonnull String argument = Quotes.in(quotes, arguments[argumentIndex]);
                string.replace(stringIndex, stringIndex + 1, argument);
                stringIndex += argument.length();
                argumentIndex++;
            } else {
                stringIndex++;
            }
        }
        if (argumentIndex < arguments.length) {
            if (string.length() > 0) { string.append(" "); }
            string.append("[");
            boolean first = true;
            while (argumentIndex < arguments.length) {
                if (first) { first = false; } else { string.append(", "); }
                string.append(Quotes.in(quotes, arguments[argumentIndex]));
                argumentIndex++;
            }
            string.append("]");
        }
        return string.toString();
    }
    
    /**
     * Formats the given message by replacing each dollar sign with the corresponding argument surrounded by the given quotes.
     */
    @Pure
    public static @Nonnull String format(@Nonnull CharSequence message, @Nullable Quotes quotes, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        return format(message, '$', quotes, arguments);
    }
    
    /**
     * Formats the given message by replacing each dollar sign with the corresponding argument in single quotes.
     */
    @Pure
    public static @Nonnull String format(@Nonnull CharSequence message, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        return format(message, '$', Quotes.SINGLE, arguments);
    }
    
    /* -------------------------------------------------- String Case -------------------------------------------------- */
    
    /**
     * Returns the given string with the first letter of each word in uppercase or propagates null.
     */
    @Pure
    public static String capitalizeFirstLetters(String string) {
        if (string == null || string.isEmpty()) { return string; }
        final @Nonnull StringBuilder result = new StringBuilder(string);
        int index = 0;
        do {
            result.replace(index, index + 1, result.substring(index, index + 1).toUpperCase());
            index = result.indexOf(" ", index) + 1;
        } while (index > 0 && index < result.length());
        return result.toString();
    }
    
    /**
     * Returns the given string in camel case in lower case with spaces or propagates null.
     */
    @Pure
    public static String decamelize(String string) {
        if (string == null || string.isEmpty()) { return string; }
        final @Nonnull StringBuilder result = new StringBuilder(string);
        for (int index = 0; index < result.length(); index++) {
            if (Character.isUpperCase(result.charAt(index))) {
                result.replace(index, index + 1, result.substring(index, index + 1).toLowerCase());
                if (index > 0) { result.insert(index, ' '); }
            }
        }
        return result.toString();
    }
    
    /**
     * Returns the given string in snake case in lower case with spaces or propagates null.
     */
    @Pure
    public static String desnake(String string) {
        if (string == null || string.isEmpty()) { return string; }
        return string.replace("_", " ").toLowerCase();
    }
    
    /**
     * Returns the given string with the first character in lower case or propagates null.
     */
    @Pure
    public static String lowercaseFirstCharacter(String string) {
        return string == null || string.isEmpty() ? string : string.substring(0, 1).toLowerCase() + string.substring(1);
    }
    
    /**
     * Returns the given string with the first character in upper case or propagates null.
     */
    @Pure
    public static String uppercaseFirstCharacter(String string) {
        return string == null || string.isEmpty() ? string : string.substring(0, 1).toUpperCase() + string.substring(1);
    }
    
    /* -------------------------------------------------- Substrings -------------------------------------------------- */
    
    /**
     * Returns the substring from the last occurrence of the given delimiter in the given string or the given string if the delimiter is not found.
     */
    @Pure
    public static @Nonnull String substringFromLast(@Nonnull String string, @Nonnull @NonEmpty String delimiter) {
        return string.substring(string.lastIndexOf(delimiter) + 1);
    }
    
    /**
     * Returns the substring until the first occurrence of the given delimiter in the given string or the given string if the delimiter is not found.
     */
    @Pure
    public static @Nonnull String substringUntilFirst(@Nonnull String string, @Nonnull @NonEmpty String delimiter) {
        final int index = string.indexOf(delimiter);
        return index >= 0 ? string.substring(0, index) : string;
    }
    
    /* -------------------------------------------------- Repetitions -------------------------------------------------- */
    
    /**
     * Returns the given string repeated the given number of times or propagates null.
     */
    @Pure
    public static String repeat(String string, @NonNegative int count) {
        if (string == null) { return null; }
        final @Nonnull StringBuilder result = new StringBuilder(count * string.length());
        for (int i = 0; i < count; i++) {
            result.append(string);
        }
        return result.toString();
    }
    
    /**
     * Returns the given character repeated the given number of times.
     */
    @Pure
    public static @Nonnull String repeat(char character, @NonNegative int count) {
        final @Nonnull char[] result = new char[count];
        for (int i = 0; i < count; i++) {
            result[i] = character;
        }
        return new String(result);
    }
    
    /* -------------------------------------------------- Prefixes -------------------------------------------------- */
    
    /**
     * Returns the longest common prefix of the given strings.
     */
    @Pure
    public static @Nonnull String longestCommonPrefix(@NonCaptured @Unmodified @Nonnull @NonNullableElements String... strings) {
        if (strings.length == 0) { return ""; }
        @Nonnull String prefix = strings[0];
        string: for (int s = 1; s < strings.length; s++) {
            final @Nonnull String string = strings[s];
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
     * Returns whether the given word starts with any of the given prefixes.
     */
    @Pure
    public static boolean startsWithAny(@Nonnull String word, @NonCaptured @Unmodified @Nonnull @NonNullableElements String... prefixes) {
        for (@Nonnull String prefix : prefixes) {
            if (word.startsWith(prefix)) { return true; }
        }
        return false;
    }
    
    /* -------------------------------------------------- Reflection -------------------------------------------------- */
    
    /**
     * Returns the field value or a &lt;access failure&gt; error message if the field cannot be accessed.
     */
    @Pure
    private static @Nullable Object getFieldValueOrErrorMessage(@NonCaptured @Modified @Nonnull Field field, @NonCaptured @Unmodified @Nonnull Object object) {
        field.setAccessible(true);
        try { return field.get(object); } catch (@Nonnull IllegalAccessException exception) { return "<access failure>"; }
    }
    
    /**
     * Returns a string representation of the given object.
     */
    @Pure
    public static @Nonnull String toString(@NonCaptured @Unmodified @Nullable Object object) {
        if (object == null) { return "null"; }
        else { return object.getClass().getSimpleName() + FiniteIterable.of(object.getClass().getDeclaredFields()).map(field -> field.getName() + ": " + Quotes.inCode(getFieldValueOrErrorMessage(field, object))).join(Brackets.ROUND); }
    }
    
    /* -------------------------------------------------- Cardinal Numbers -------------------------------------------------- */
    
    private static final @Nonnull ImmutableList<@Nonnull String> cardinalNumbers = ImmutableList.with("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve");
    
    /**
     * Returns the given number as a cardinal string (written-out if smaller than 13).
     */
    @Pure
    public static final @Nonnull String getCardinal(int number) {
        if (number >= 0 && number <= 12) { return cardinalNumbers.get(number); }
        if (number >= -12 && number < 0) { return "minus " + cardinalNumbers.get(-number); }
        return String.valueOf(number);
    }
    
    /* -------------------------------------------------- Ordinal Numbers with English Root -------------------------------------------------- */
    
    private static final @Nonnull ImmutableList<@Nonnull String> ordinalNumbersWithEnglishRoot = ImmutableList.with("first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth", "tenth", "eleventh", "twelfth", "13th");
    
    /**
     * Returns the given number as an ordinal string (written-out if smaller than 13).
     */
    @Pure
    public static @Nonnull String getOrdinal(@Positive int number) {
        if (number >= 1 && number <= 13) { return ordinalNumbersWithEnglishRoot.get(number - 1); }
        final int mod10 = number % 10;
        if (mod10 == 1) { return String.valueOf(number) + "st"; }
        if (mod10 == 2) { return String.valueOf(number) + "nd"; }
        if (mod10 == 3) { return String.valueOf(number) + "rd"; }
        return String.valueOf(number) + "th";
    }
    
    /* -------------------------------------------------- Ordinal Numbers with Latin Root -------------------------------------------------- */
    
    private static final @Nonnull ImmutableList<@Nonnull String> ordinalNumbersWithLatinRoot = ImmutableList.with("primary", "secondary", "tertiary", "quaternary", "quinary", "senary", "septenary", "octonary", "novenary", "decenary", "undenary", "duodenary");
    
    /**
     * Returns the given number as an ordinal string with Latin root (written-out if smaller than 13).
     */
    @Pure
    public static @Nonnull String getOrdinalWithLatinRoot(@Positive int number) {
        if (number >= 1 && number <= 12) { return ordinalNumbersWithLatinRoot.get(number - 1); }
        return String.valueOf(number) + ".";
    }
    
    /* -------------------------------------------------- Plural Formation -------------------------------------------------- */
    
    private static final @Nonnull ImmutableMap<@Nonnull String, @Nonnull String> rules = ImmutableMap.with("a", "ae").with("an", "en").with("ch", "ches").with("ex", "ices").with("f", "ves").with("fe", "ves").with("is", "es").with("ix", "ices").with("s", "ses").with("sh", "shes").with("um", "a").with("x", "xes").with("y", "ies").build();
    
    /**
     * Forms the plural of the given word in singular based on simple heuristics or propagates null.
     */
    @Pure
    public static String pluralize(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        } else {
            if (word.contains("oo") && !word.equals("book")) { return word.replace("oo", "ee"); }
            for (Map.@Nonnull Entry<String, String> rule : rules.entrySet()) {
                if (word.endsWith(rule.getKey())) {
                    return word.substring(0, word.length() - rule.getKey().length()) + rule.getValue();
                }
            }
            return word + "s";
        }
    }
    
    /**
     * Prepends the given word in singular with the given number (written-out if smaller than 13) and forms the plural if the number is not one.
     */
    @Pure
    public static @Nonnull String prependWithNumberAndPluralize(int number, @Nonnull @NonEmpty String word) {
        return getCardinal(number) + " " + (number == 1 ? word : pluralize(word));
    }
    
    /* -------------------------------------------------- Indefinite Article -------------------------------------------------- */
    
    /**
     * Prepends the given word with the appropriate indefinite article in upper or lower case based on a simple heuristic or propagates null.
     */
    @Pure
    public static @Nonnull String prependWithIndefiniteArticle(String word, boolean uppercase) {
        if (word == null || word.isEmpty()) {
            return word;
        } else {
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
    }
    
    /**
     * Prepends the given word with the appropriate indefinite article written all lowercase or propagates null.
     */
    @Pure
    public static String prependWithIndefiniteArticle(String word) {
        return Strings.prependWithIndefiniteArticle(word, false);
    }
    
}
