package net.digitalid.utility.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.console.exceptions.EscapeException;
import net.digitalid.utility.exceptions.UncheckedExceptionBuilder;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.logging.logger.Logger;
import net.digitalid.utility.property.set.WritableVolatileSimpleSetProperty;
import net.digitalid.utility.property.set.WritableVolatileSimpleSetPropertyBuilder;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * This class helps to read from standard input and write to standard output.
 * 
 * @see Option
 */
@Utility
@SuppressWarnings("UseOfSystemOutOrSystemErr")
public abstract class Console {
    
    /* -------------------------------------------------- Writing -------------------------------------------------- */
    
    /**
     * Writes the given message to the standard output without terminating the line.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void write(@Nonnull CharSequence message, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        final @Nonnull String formattedMessage = Strings.format(message, arguments);
        System.out.print(formattedMessage);
        Log.information("Wrote the string $ to the console.", formattedMessage);
    }
    
    /**
     * Writes the given message to the standard output and terminates the line.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void writeLine(@Nonnull CharSequence message, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        final @Nonnull String formattedMessage = Strings.format(message, arguments);
        System.out.println(formattedMessage);
        Log.information("Wrote the line $ to the console.", formattedMessage);
    }
    
    /**
     * Writes the line separator string to the standard output.
     */
    @Impure
    public static void writeLine() {
        System.out.println();
    }
    
    /**
     * Flushes the standard output.
     */
    @Impure
    public static void flush() {
        System.out.flush();
    }
    
    /* -------------------------------------------------- Logging -------------------------------------------------- */
    
    /**
     * Logs the given message and throwable both to the console and the log file.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    @SuppressWarnings("CallToPrintStackTrace")
    public static void log(@Nonnull Level level, @Nonnull CharSequence message, @Nullable Throwable throwable, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        Logger.log(level, message, throwable, arguments);
        
        System.out.println(Strings.format(message, arguments));
        if (throwable != null) {
            System.out.println();
            throwable.printStackTrace();
            System.out.println();
        }
    }
    
    /**
     * Logs the given message both to the console and the log file.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void log(@Nonnull Level level, @Nonnull CharSequence message, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        Logger.log(level, message, null, arguments);
        System.out.println(Strings.format(message, arguments));
    }
    
    /* -------------------------------------------------- Reading -------------------------------------------------- */
    
    /**
     * Stores a buffered reader of the standard input.
     */
    private static final @Nonnull BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    /**
     * Reads a line from the standard input and returns it.
     */
    @Impure
    private static @Nonnull String readLine() {
        try {
            final @Nullable String input = reader.readLine();
            if (input == null) { throw new IOException("The end of the standard input has been reached."); }
            Log.information("Read the line $ from the console.", input);
            return input;
        } catch (@Nonnull IOException exception) {
            throw UncheckedExceptionBuilder.withCause(exception).build();
        }
    }
    
    /**
     * Reads a string from the standard input.
     * 
     * @return the string read from the standard input or the given default value if the input was empty.
     */
    @Impure
    public static @Nonnull String readString(@Nullable String defaultValue) {
        final @Nonnull String input = readLine();
        if (input.isEmpty() && defaultValue != null) { return defaultValue; }
        else { return input; }
    }
    
    /**
     * Reads a number from the standard input and prompts the user repeatedly until the input can be parsed.
     * 
     * @return the number read from the standard input or the given default value if the input was empty.
     */
    @Impure
    public static int readNumber(@Nullable Integer defaultValue) {
        while (true) {
            final @Nonnull String input = readLine();
            if (input.isEmpty() && defaultValue != null) { return defaultValue; }
            try {
                return Integer.decode(input);
            } catch (@Nonnull NumberFormatException exception) {
                write("Could not parse the input. Please enter a number: ");
            }
        }
    }
    
    /**
     * Reads a boolean from the standard input and prompts the user repeatedly until the input can be parsed.
     * 
     * @return the boolean read from the standard input or the given default value if the input was empty.
     */
    @Impure
    public static boolean readBoolean(@Nullable Boolean defaultValue) {
        while (true) {
            final @Nonnull String input = readLine();
            if (input.isEmpty() && defaultValue != null) { return defaultValue; }
            if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) { return true; }
            if (input.equalsIgnoreCase("false") || input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n")) { return false; }
            write("Could not parse the input. Please enter 'yes' or 'no': ");
        }
    }
    
    /* -------------------------------------------------- Prompting -------------------------------------------------- */
    
    /**
     * Writes the given prompt to the standard output and reads a string from the standard input.
     * 
     * @return the string read from the standard input or the given default value if the input was empty.
     */
    @Impure
    public static @Nonnull String readString(@Nonnull String prompt, @Nullable String defaultValue) {
        write(prompt);
        return readString(defaultValue);
    }
    
    /**
     * Writes the given prompt to the standard output and reads a number from the standard input.
     * 
     * @return the number read from the standard input or the given default value if the input was empty.
     */
    @Impure
    public static int readNumber(@Nonnull String prompt, @Nullable Integer defaultValue) {
        write(prompt);
        return readNumber(defaultValue);
    }
    
    /**
     * Writes the given prompt to the standard output and reads a boolean from the standard input.
     * 
     * @return the boolean read from the standard input or the given default value if the input was empty.
     */
    @Impure
    public static boolean readBoolean(@Nonnull String prompt, @Nullable Boolean defaultValue) {
        write(prompt);
        return readBoolean(defaultValue);
    }
    
    /* -------------------------------------------------- Options -------------------------------------------------- */
    
    /**
     * Stores the available options for the user.
     */
    public static final @Nonnull WritableVolatileSimpleSetProperty<Option> options = WritableVolatileSimpleSetPropertyBuilder.build();
    
    /* -------------------------------------------------- Loop -------------------------------------------------- */
    
    /**
     * Starts an infinite loop that lets the user choose from the list of options.
     */
    @Impure
    public static void start() {
        while (true) {
            writeLine("----------------------------------------------------------------------------------------");
            writeLine();
            writeLine("You have the following options:");
            final int size = options.get().size();
            for (int i = 0; i < size; i++) {
                writeLine("– " + (size > 10 && i < 10 ? " " : "") + i + ": " + options.get().get(i).getDescription());
            }
            writeLine();
            final int input = readNumber("Execute the option: ", null);
            writeLine();
            if (input >= 0 && input < size) {
                try { options.get().get(input).execute(); } catch (@Nonnull EscapeException exception) {}
            } else {
                writeLine("Please choose one of the given options!");
            }
            writeLine();
        }
    }
    
}
