package net.digitalid.utility.string;

/**
 * This class provides useful operations to format a string.
 */
public class FormatString {
    
    /**
     * Formats the given non-nullable message by replacing each dollar sign with the corresponding argument.
     */
    public static String format(String message, Object... arguments) {
        final StringBuilder string = new StringBuilder(message);
        int stringIndex = 0;
        int argumentIndex = 0;
        while (stringIndex < string.length() && argumentIndex < arguments.length) {
            if (string.charAt(stringIndex) == '$') {
                final String argument = QuoteString.inSingle(arguments[argumentIndex]);
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
                string.append(QuoteString.inSingle(arguments[argumentIndex]));
                argumentIndex++;
            }
            string.append("]");
        }
        return string.toString();
    }
    
}
