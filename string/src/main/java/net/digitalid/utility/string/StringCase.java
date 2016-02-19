package net.digitalid.utility.string;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Utiliy;

/**
 * This class provides useful operations to transform the case of strings.
 */
@Utiliy
public class StringCase {
    
    /**
     * Returns the given phrase with the first letter of each word in uppercase.
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
     * Returns the given word in camel case in lower case with spaces.
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
    
    /**
     * Returns the given string with the first character in lower case.
     */
    @Pure
    public static @Nonnull String lowerCaseFirstCharacter(@Nonnull String string) {
        return string.isEmpty() ? "" : string.substring(0, 1).toLowerCase() + string.substring(1);
    }
    
    /**
     * Returns the given string with the first character in upper case.
     */
    @Pure
    public static @Nonnull String upperCaseFirstCharacter(@Nonnull String string) {
        return string.isEmpty() ? "" : string.substring(0, 1).toUpperCase()+ string.substring(1);
    }
    
}
