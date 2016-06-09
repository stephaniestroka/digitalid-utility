package net.digitalid.utility.directory;

import java.io.File;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.exceptions.UnexpectedFailureException;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.validation.annotations.file.existence.Existent;
import net.digitalid.utility.validation.annotations.file.kind.Directory;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * This class provides references to all directories that are used by this implementation.
 */
@Utility
public abstract class FormerDirectory {
    
    /* -------------------------------------------------- Root Directory -------------------------------------------------- */
    
    /**
     * Stores the default directory in the user home directory.
     */
    public static final @Nonnull @Directory File DEFAULT = new File(System.getProperty("user.home") + File.separator + ".digitalid" + File.separator);
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    /**
     * Stores the root directory that contains all other directories.
     */
    public static final @Nonnull Configuration<@Directory File> root = Configuration.with(DEFAULT);
    
    /* -------------------------------------------------- Directories -------------------------------------------------- */
    
    /**
     * Creates and returns the directory with the given name in the root directory.
     */
    @Pure
    private static @Nonnull @Existent @Directory File createDirectory(@Nonnull String name) {
        final @Nonnull File directory = new File(root.get().getPath() + File.separator + name);
        if (!directory.exists() && !directory.mkdirs()) { throw UnexpectedFailureException.with("Could not create the directory '" + directory.getPath() + "'."); }
        return directory;
    }
    
    /**
     * Returns the directory that contains the log files.
     */
    @Pure
    public static @Nonnull @Existent @Directory File getLogsDirectory() {
        return createDirectory("logs");
    }
    
    /**
     * Returns the directory that contains the configuration or the data of the database.
     */
    @Pure
    public static @Nonnull @Existent @Directory File getDataDirectory() {
        return createDirectory("data");
    }
    
    /**
     * Returns the directory that contains the secret key of each local client.
     */
    @Pure
    public static @Nonnull @Existent @Directory File getClientsDirectory() {
        return createDirectory("clients");
    }
    
    /**
     * Returns the directory that contains the key pairs of each local host.
     */
    @Pure
    public static @Nonnull @Existent @Directory File getHostsDirectory() {
        return createDirectory("hosts");
    }
    
    /**
     * Returns the directory that contains the code of all installed services.
     */
    @Pure
    public static @Nonnull @Existent @Directory File getServicesDirectory() {
        return createDirectory("services");
    }
    
    /* -------------------------------------------------- Listing -------------------------------------------------- */
    
    /**
     * Returns the non-hidden files in the given directory.
     */
    @Pure
    public static @Nonnull FiniteIterable<@Nonnull File> listFiles(@Nonnull @Existent @Directory File directory) {
        return FiniteIterable.of(directory.listFiles((dir, name) -> !name.startsWith(".")));
    }
    
    /* -------------------------------------------------- Removal -------------------------------------------------- */
    
    /**
     * Deletes the given file or directory and returns whether it was successfully deleted.
     */
    @Impure
    public static boolean delete(@Nonnull File file) {
        if (file.isDirectory()) {
            for (@Nonnull File subfile : file.listFiles()) {
                if (!delete(subfile)) { return false; }
            }
        }
        return file.delete();
    }
    
    /**
     * Empties the given directory and returns whether it was successfully emptied.
     */
    @Impure
    public static boolean empty(@Nonnull @Directory File directory) {
        for (@Nonnull File file : directory.listFiles()) {
            if (!delete(file)) { return false; }
        }
        return true;
    }
    
}
