package net.digitalid.utility.string;

/**
 * This class provides useful operations to transform the case of strings.
 */
public class StringCase {
    
    /**
     * Returns the given non-nullable phrase with the first letter of each word in uppercase.
     */
    public static String capitalizeFirstLetters(String phrase) {
        final StringBuilder string = new StringBuilder(phrase);
        int index = 0;
        do {
            string.replace(index, index + 1, string.substring(index, index + 1).toUpperCase());
            index =  string.indexOf(" ", index) + 1;
        } while (index > 0 && index < string.length());
        return string.toString();
    }
    
    /**
     * Returns the given non-nullable word in camel case in lower case with spaces.
     */
    public static String decamelize(String word) {
        final StringBuilder string = new StringBuilder(word);
        for (int index = 0; index < string.length(); index++) {
            if (Character.isUpperCase(string.charAt(index))) {
                string.replace(index, index + 1, string.substring(index, index + 1).toLowerCase());
                if (index > 0) { string.insert(index, ' '); }
            }
        }
        return string.toString();
    }
    
    /**
     * Returns the given non-nullable string with the first character in lower case.
     */
    public static String lowerCaseFirstCharacter(String string) {
        return string.isEmpty() ? "" : string.substring(0, 1).toLowerCase() + string.substring(1);
    }
    
    /**
     * Returns the given non-nullable string with the first character in upper case.
     */
    public static String upperCaseFirstCharacter(String string) {
        return string.isEmpty() ? "" : string.substring(0, 1).toUpperCase()+ string.substring(1);
    }
    
}
