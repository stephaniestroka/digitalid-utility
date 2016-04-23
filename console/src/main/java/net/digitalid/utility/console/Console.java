package net.digitalid.utility.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.console.exceptions.EscapeOptionException;
import net.digitalid.utility.exceptions.UnexpectedFailureException;
import net.digitalid.utility.generator.annotations.GenerateNoBuilder;
import net.digitalid.utility.generator.annotations.GenerateNoSubclass;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * This class helps to read from standard input and write to standard output.
 * 
 * @see Option
 */
@Stateless
@GenerateNoBuilder
@GenerateNoSubclass
@SuppressWarnings("UseOfSystemOutOrSystemErr")
public final class Console {
    
    /* -------------------------------------------------- Reading -------------------------------------------------- */
    
    /**
     * Stores the buffered reader from the standard input.
     */
    private static final @Nonnull BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    /**
     * Reads a line from the standard input.
     * 
     * @return the line read from the standard input.
     */
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
     * @param defaultValue a default value to return if the input is empty.
     * 
     * @return the string read from the standard input or the default value.
     */
    public static @Nonnull String readString(@Nullable String defaultValue) {
        final @Nonnull String input = read();
        if (input.isEmpty() && defaultValue != null) { return defaultValue; }
        else { return input; }
    }
    
    /**
     * Reads a number from the standard input.
     * 
     * @param defaultValue a default value to return if the input is empty.
     * 
     * @return the number read from the standard input or the default value.
     */
    public static int readNumber(@Nullable Integer defaultValue) {
        while (true) {
            final @Nonnull String input = read();
            if (input.isEmpty() && defaultValue != null) { return defaultValue; }
            try {
                return Integer.decode(input);
            } catch (@Nonnull NumberFormatException exception) {
                System.out.print("Could not parse the input. Please enter a number: ");
            }
        }
    }
    
    /**
     * Reads a boolean from the standard input.
     * 
     * @param defaultValue a default value to return if the input is empty.
     * 
     * @return the boolean read from the standard input or the default value.
     */
    public static boolean readBoolean(@Nullable Boolean defaultValue) {
        while (true) {
            final @Nonnull String input = read();
            if (input.isEmpty() && defaultValue != null) { return defaultValue; }
            if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) { return true; }
            if (input.equalsIgnoreCase("false") || input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n")) { return false; }
            System.out.print("Could not parse the input. Please enter 'yes' or 'no': ");
        }
    }
    
    /* -------------------------------------------------- Prompting -------------------------------------------------- */
    
    /**
     * Writes the given prompt to the standard output and reads a string from the standard input.
     * 
     * @param prompt the prompt to write to the standard output.
     * @param defaultValue a default value to return if the input is empty.
     * 
     * @return the string read from the standard input or the default value.
     */
    public static @Nonnull String readString(@Nonnull String prompt, @Nullable String defaultValue) {
        System.out.print(prompt);
        return readString(defaultValue);
    }
    
    /**
     * Writes the given prompt to the standard output and reads a number from the standard input.
     * 
     * @param prompt the prompt to write to the standard output.
     * @param defaultValue a default value to return if the input is empty.
     * 
     * @return the number read from the standard input or the default value.
     */
    public static int readNumber(@Nonnull String prompt, @Nullable Integer defaultValue) {
        System.out.print(prompt);
        return Console.readNumber(defaultValue);
    }
    
    /**
     * Writes the given prompt to the standard output and reads a boolean from the standard input.
     * 
     * @param prompt the prompt to write to the standard output.
     * @param defaultValue a default value to return if the input is empty.
     * 
     * @return the boolean read from the standard input or the default value.
     */
    public static boolean readBoolean(@Nonnull String prompt, @Nullable Boolean defaultValue) {
        System.out.print(prompt);
        return readBoolean(defaultValue);
    }
    
    /* -------------------------------------------------- Writing -------------------------------------------------- */
    
    /**
     * Writes the given string to the standard output (including a new line).
     * 
     * @param string the string to be written to the standard output.
     */
    public static void write(@Nonnull String string) {
        System.out.println(string);
    }
    
    /**
     * Writes a new line to the standard output.
     */
    public static void write() {
        System.out.println();
    }
    
    /**
     * Flushes the standard output.
     */
    public static void flush() {
        System.out.flush();
    }
    
    /* -------------------------------------------------- Options -------------------------------------------------- */
    
    /**
     * Stores the available options for the user.
     */
    private static final @Nonnull List<Option> options = new LinkedList<>();
    
    /**
     * Adds the given option to the list of options.
     * 
     * @param option the option to be added.
     */
    public static void addOption(@Nonnull Option option) {
        options.add(option);
    }
    
    /**
     * Starts an infinite loop that lets the user choose from the list of options.
     */
    public static void start() {
        while (true) {
            write("----------------------------------------------------------------------------------------");
            write();
            write("You have the following options:");
            final int size = options.size();
            for (int i = 0; i < size; i++) {
                write("– " + (size > 10 && i < 10 ? " " : "") + i + ": " + options.get(i).getDescription());
            }
            write();
            final int input = readNumber("Execute the option: ", null);
            write();
            if (input >= 0 && input < size) {
                try { options.get(input).execute(); } catch (@Nonnull EscapeOptionException exception) {}
            } else {
                write("Please choose one of the given options!");
            }
            write();
        }
    }
    
}
