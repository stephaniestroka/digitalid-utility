package net.digitalid.utility.string;

/**
 * This class provides useful operations to format a string.
 */
public class FormatString {
    
    /**
     * Formats the given non-nullable message by replacing each given symbol with the corresponding argument surrounded by the given quotation marks.
     */
    public static String format(CharSequence message, char symbol, QuoteString.Mark mark, Object... arguments) {
        final StringBuilder string = new StringBuilder(message);
        int stringIndex = 0;
        int argumentIndex = 0;
        while (stringIndex < string.length() && argumentIndex < arguments.length) {
            if (string.charAt(stringIndex) == symbol) {
                final String argument = QuoteString.in(mark, arguments[argumentIndex]);
                string.replace(stringIndex, stringIndex + 1, argument);
                stringIndex += argument.length();
                argumentIndex++;
            } else {
                stringIndex++;
            }
        }
        if (argumentIndex < arguments.length) {
            string.append(" [");
            boolean first = true;
            while (argumentIndex < arguments.length) {
                if (first) { first = false; } else { string.append(", "); }
                string.append(QuoteString.in(mark, arguments[argumentIndex]));
                argumentIndex++;
            }
            string.append("]");
        }
        return string.toString();
    }
    
    /**
     * Formats the given non-nullable message by replacing each dollar sign with the corresponding argument surrounded by the given quotation marks.
     */
    public static String format(CharSequence message, QuoteString.Mark mark, Object... arguments) {
        return format(message, '$', mark, arguments);
    }
    
    /**
     * Formats the given non-nullable message by replacing each dollar sign with the corresponding argument in single quotes.
     */
    public static String format(CharSequence message, Object... arguments) {
        return format(message, '$', QuoteString.Mark.SINGLE, arguments);
    }
    
}
