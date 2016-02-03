package net.digitalid.utility.string;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Utiliy;

/**
 * This class provides useful operations to quote strings.
 */
@Utiliy
public class QuoteString {
    
    /* -------------------------------------------------- Single Quotes -------------------------------------------------- */
    
    /**
     * Returns the given string in single quotes.
     */
    @Pure
    public static @Nonnull String inSingle(@Nonnull String string) {
        return "'" + string + "'";
    }
    
    /**
     * Returns the given object in single quotes.
     */
    @Pure
    public static @Nonnull String inSingle(@Nonnull Object object) {
        return inSingle(object.toString());
    }
    
    /* -------------------------------------------------- Double Quotes -------------------------------------------------- */
    
    /**
     * Returns the given string in double quotes.
     */
    @Pure
    public static @Nonnull String inDouble(@Nonnull String string) {
        return "\"" + string + "\"";
    }
    
    /**
     * Returns the given object in double quotes.
     */
    @Pure
    public static @Nonnull String inDouble(@Nonnull Object object) {
        return inDouble(object.toString());
    }
    
}
