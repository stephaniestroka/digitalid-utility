package net.digitalid.utility.string;

/**
 * This class provides useful operations to quote strings.
 */
public class QuoteString {
    
    /* -------------------------------------------------- Quotation Marks -------------------------------------------------- */
    
    /**
     * This class enumerates the various quotation marks.
     */
    public static enum Mark {
        NONE, SINGLE, DOUBLE, CODE;
    }
    
    /* -------------------------------------------------- General Form -------------------------------------------------- */
    
    /**
     * Returns the given object surrounded by the given quotation mark.
     */
    public static String in(Mark mark, Object object) {
        final String string;
        switch (mark) {
            case NONE: string = ""; break;
            case SINGLE: string = "\'"; break;
            case DOUBLE: string = "\""; break;
            case CODE: string = object instanceof CharSequence ? "\"" : ""; break;
            default: string = "";
        }
        return string + String.valueOf(object) + string;
    }
    
    /* -------------------------------------------------- Single Quotes -------------------------------------------------- */
    
    /**
     * Returns the given string in single quotes.
     */
    public static String inSingle(String string) {
        return in(Mark.SINGLE, string);
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
        return in(Mark.DOUBLE, string);
    }
    
    /**
     * Returns the given object in double quotes.
     */
    public static String inDouble(Object object) {
        return inDouble(String.valueOf(object));
    }
    
    /* -------------------------------------------------- Code Quotes -------------------------------------------------- */
    
    /**
     * Returns the given object in double quotes if it is an instance of {@link CharSequence} or without quotes otherwise.
     */
    public static String inCode(Object object) {
        return in(Mark.CODE, object);
    }
    
}
