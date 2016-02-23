package net.digitalid.utility.string;

/**
 * This class provides useful operations to quote strings.
 */
public class QuoteString {
    
    /* -------------------------------------------------- Single Quotes -------------------------------------------------- */
    
    /**
     * Returns the given string in single quotes.
     */
    public static String inSingle(String string) {
        return "'" + string + "'";
    }
    
    /**
     * Returns the given object in single quotes.
     */
    public static String inSingle(Object object) {
        return inSingle(String.valueOf(object));
    }
    
    /* -------------------------------------------------- Double Quotes -------------------------------------------------- */
    
    /**
     * Returns the given string in double quotes.
     */
    public static String inDouble(String string) {
        return "\"" + string + "\"";
    }
    
    /**
     * Returns the given object in double quotes.
     */
    public static String inDouble(Object object) {
        return inDouble(String.valueOf(object));
    }
    
}
