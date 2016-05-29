package net.digitalid.utility.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.collaboration.annotations.TODO;
import net.digitalid.utility.collaboration.enumerations.Author;
import net.digitalid.utility.collaboration.enumerations.Priority;
import net.digitalid.utility.collections.set.ReadOnlySet;
import net.digitalid.utility.console.exceptions.EscapeOptionException;
import net.digitalid.utility.exceptions.UnexpectedFailureException;
import net.digitalid.utility.property.extensible.VolatileWritableExtensiblePropertyBuilder;
import net.digitalid.utility.property.extensible.WritableExtensibleProperty;
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
     * Writes the given string to the standard output without terminating the line.
     */
    @Impure
    public static void write(@Nonnull String string) {
        System.out.print(string);
    }
    
    /**
     * Writes the given string to the standard output and terminates the line.
     */
    @Impure
    public static void writeLine(@Nonnull String string) {
        System.out.println(string);
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
    
    /* -------------------------------------------------- Reading -------------------------------------------------- */
    
    /**
     * Stores a buffered reader of the standard input.
     */
    private static final @Nonnull BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    /**
     * Reads a line from the standard input and returns it.
     */
    @Impure
    private static @Nonnull String read() {
        try {
            final @Nullable String input = reader.readLine();
            if (input == null) { throw new IOException("The end of the standard input has been reached."); }
            return input;
        } catch (@Nonnull IOException exception) {
            throw UnexpectedFailureException.with("Could not read from the standard input.", exception);
        }
    }
    
    /**
     * Reads a string from the standard input.
     * 
     * @return the string read from the standard input or the given default value if the input was empty.
     */
    @Impure
    public static @Nonnull String readString(@Nullable String defaultValue) {
        final @Nonnull String input = read();
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
            final @Nonnull String input = read();
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
            final @Nonnull String input = read();
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
        return Console.readNumber(defaultValue);
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
    @TODO(task = "Fix the generics of the builder and allow a direct (static) build call on the builder.", date = "2016-05-01", author = Author.KASPAR_ETTER, assignee = Author.STEPHANIE_STROKA, priority = Priority.MIDDLE)
    public static final @Nonnull WritableExtensibleProperty<Option, ReadOnlySet<Option>> options = VolatileWritableExtensiblePropertyBuilder.get().build();
    
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
                try { options.get().get(input).execute(); } catch (@Nonnull EscapeOptionException exception) {}
            } else {
                writeLine("Please choose one of the given options!");
            }
            writeLine();
        }
    }
    
}
