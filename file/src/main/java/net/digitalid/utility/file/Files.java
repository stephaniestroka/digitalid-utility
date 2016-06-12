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

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.exceptions.UnexpectedFailureException;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.validation.annotations.file.existence.Existent;
import net.digitalid.utility.validation.annotations.file.existence.ExistentParent;
import net.digitalid.utility.validation.annotations.file.kind.Directory;
import net.digitalid.utility.validation.annotations.file.kind.Normal;
import net.digitalid.utility.validation.annotations.file.path.Absolute;
import net.digitalid.utility.validation.annotations.file.permission.Readable;
import net.digitalid.utility.validation.annotations.file.permission.Writable;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * This class provides useful operations on files.
 */
@Utility
public abstract class Files {
    
    /* -------------------------------------------------- Listing -------------------------------------------------- */
    
    /**
     * Returns the non-hidden files in the given directory.
     */
    @Pure
    public static @Nonnull FiniteIterable<@Nonnull @Existent File> listNonHiddenFiles(@Nonnull @Existent @Directory File directory) {
        return FiniteIterable.of(directory.listFiles((dir, name) -> !name.startsWith(".")));
    }
    
    /* -------------------------------------------------- Removal -------------------------------------------------- */
    
    /**
     * Empties the given directory and returns whether it was successfully emptied.
     */
    @Impure
    public static boolean empty(@Nonnull @Existent @Directory File directory) {
        for (@Nonnull File file : directory.listFiles()) {
            if (!delete(file)) { return false; }
        }
        return true;
    }
    
    /**
     * Deletes the given file or directory and returns whether it was successfully deleted.
     */
    @Impure
    public static boolean delete(@Nonnull @Existent File file) {
        if (file.isDirectory()) {
            if (!empty(file)) { return false; }
        }
        return file.delete();
    }
    
    /* -------------------------------------------------- Creation -------------------------------------------------- */
    
    /**
     * Creates the missing parent directories of the given file.
     */
    @Impure
    public static void createParentDirectories(@Nonnull @Absolute File file) {
        final @Nullable File directory = file.getParentFile();
        if (directory != null && !directory.exists() && !directory.mkdirs()) {
            throw UnexpectedFailureException.with("Could not create the missing parent directories of $.", file.getAbsolutePath());
        }
    }
    
    /* -------------------------------------------------- Directory -------------------------------------------------- */
    
    /**
     * Stores the typically hidden directory which is used to store the configuration and logging files of Digital ID.
     */
    public static final @Nonnull Configuration<@Absolute @Existent @Directory File> directory = Configuration.withUnknownProvider();
    
    /* -------------------------------------------------- Retrieval -------------------------------------------------- */
    
    /**
     * Returns the file with the given path relative to the working directory and creates missing parent directories.
     */
    @Impure
    public static @Nonnull @Absolute @ExistentParent File relativeToWorkingDirectory(@Nonnull String path) {
        final @Nonnull @Absolute File file = new File(path.replace("/", File.separator)).getAbsoluteFile();
        createParentDirectories(file);
        return file;
    }
    
    /**
     * Returns the file with the given path relative to the configuration directory and creates missing parent directories.
     */
    @Impure
    public static @Nonnull @Absolute @ExistentParent File relativeToConfigurationDirectory(@Nonnull String path) {
        return relativeToWorkingDirectory(directory.get().getPath() + "/" + path);
    }
    
    /* -------------------------------------------------- Lines Reading -------------------------------------------------- */
    
    /**
     * Reads and returns the lines in the given file.
     */
    @Pure
    public static @Nonnull FiniteIterable<@Nonnull String> readLines(@Nonnull @Existent @Normal @Readable File file) {
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
    public static @Nonnull FiniteIterable<@Nonnull String> readTrimmedLines(@Nonnull @Existent @Normal @Readable File file) {
        return readLines(file).map(String::trim);
    }
    
    /**
     * Reads and returns the non-empty trimmed lines in the given file.
     */
    @Pure
    public static @Nonnull FiniteIterable<@Nonnull String> readNonEmptyTrimmedLines(@Nonnull @Existent @Normal @Readable File file) {
        return readTrimmedLines(file).filterNot(String::isEmpty);
    }
    
    /**
     * Reads and returns the non-comment non-empty trimmed lines in the given file.
     */
    @Pure
    public static @Nonnull FiniteIterable<@Nonnull String> readNonCommentNonEmptyTrimmedLines(@Nonnull @Existent @Normal @Readable File file) {
        return readNonEmptyTrimmedLines(file).filter(line -> !line.startsWith("#"));
    }
    
    /* -------------------------------------------------- Lines Writing -------------------------------------------------- */
    
    /**
     * Writes the given lines to the given file.
     */
    @Pure
    public static void write(@Nonnull FiniteIterable<@Nonnull String> lines, @Nonnull @ExistentParent @Normal @Writable File file) {
        try (@Nonnull BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            lines.doForEach(line -> { writer.write(line); writer.newLine(); });
        } catch (@Nonnull IOException exception) {
            throw UnexpectedFailureException.with(exception);
        }
    }
    
}
