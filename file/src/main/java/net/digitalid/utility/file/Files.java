package net.digitalid.utility.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
    
    /* -------------------------------------------------- Lines -------------------------------------------------- */
    
    /**
     * Returns the lines in the given file.
     */
    @Pure
    public static @Nonnull FiniteIterable<@Nonnull String> getLines(@Nonnull File file) {
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
     * Returns the trimmed lines in the given file.
     */
    @Pure
    public static @Nonnull FiniteIterable<@Nonnull String> getTrimmedLines(@Nonnull File file) {
        return getLines(file).map(String::trim);
    }
    
    /**
     * Returns the non-empty trimmed lines in the given file.
     */
    @Pure
    public static @Nonnull FiniteIterable<@Nonnull String> getNonEmptyTrimmedLines(@Nonnull File file) {
        return getTrimmedLines(file).filterNot(String::isEmpty);
    }
    
    /**
     * Returns the non-comment non-empty trimmed lines in the given file.
     */
    @Pure
    public static @Nonnull FiniteIterable<@Nonnull String> getNonCommentNonEmptyTrimmedLines(@Nonnull File file) {
        return getNonEmptyTrimmedLines(file).filter(line -> !line.startsWith("#"));
    }
    
}
