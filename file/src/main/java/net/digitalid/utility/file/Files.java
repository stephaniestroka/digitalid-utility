package net.digitalid.utility.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.exceptions.UnexpectedFailureException;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * This class provides useful operations on files.
 */
@Utility
public class Files {
    
    /* -------------------------------------------------- Parent Directories -------------------------------------------------- */
    
    /**
     * This method returns the file with the given path and creates missing parent directories.
     */
    @Pure
    public static @Nonnull File with(@Nonnull String path) {
        final @Nonnull File file = new File(path);
        final @Nullable File directory = file.getParentFile();
        if (directory != null && !directory.exists() && !directory.mkdirs()) {
            throw UnexpectedFailureException.with("Could not create the directory $.", directory.getPath());
        }
        return file;
    }
    
    /* -------------------------------------------------- Lines Reading -------------------------------------------------- */
    
    /**
     * Reads and returns the lines in the given file.
     */
    @Pure
    public static @Nonnull FiniteIterable<@Nonnull String> readLines(@Nonnull File file) {
        final @Nonnull LinkedList<@Nonnull String> lines = new LinkedList<>();
        try (@Nonnull BufferedReader reader = new BufferedReader(new FileReader(file))) {
            @Nullable String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (@Nonnull IOException exception) {
            throw UnexpectedFailureException.with(exception);
        }
        return FiniteIterable.of(lines);
    }
    
    /**
     * Reads and returns the trimmed lines in the given file.
     */
    @Pure
    public static @Nonnull FiniteIterable<@Nonnull String> readTrimmedLines(@Nonnull File file) {
        return readLines(file).map(String::trim);
    }
    
    /**
     * Reads and returns the non-empty trimmed lines in the given file.
     */
    @Pure
    public static @Nonnull FiniteIterable<@Nonnull String> readNonEmptyTrimmedLines(@Nonnull File file) {
        return readTrimmedLines(file).filterNot(String::isEmpty);
    }
    
    /**
     * Reads and returns the non-comment non-empty trimmed lines in the given file.
     */
    @Pure
    public static @Nonnull FiniteIterable<@Nonnull String> readNonCommentNonEmptyTrimmedLines(@Nonnull File file) {
        return readNonEmptyTrimmedLines(file).filter(line -> !line.startsWith("#"));
    }
    
    /* -------------------------------------------------- Lines Writing -------------------------------------------------- */
    
    /**
     * Writes the given lines to the given file.
     */
    @Pure
    public static void write(@Nonnull FiniteIterable<@Nonnull String> lines, @Nonnull File file) {
        try (@Nonnull BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            lines.doForEach(line -> { writer.write(line); writer.newLine(); });
        } catch (@Nonnull IOException exception) {
            throw UnexpectedFailureException.with(exception);
        }
    }
    
}
