package net.digitalid.utility.directory;

import java.io.File;

import javax.annotation.Nonnull;

import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.configuration.InitializationError;
import net.digitalid.utility.directory.annotations.Existing;
import net.digitalid.utility.directory.annotations.IsDirectory;
import net.digitalid.utility.validation.state.Pure;
import net.digitalid.utility.validation.state.Stateless;


/**
 * This class provides references to all directories that are used by this implementation.
 */
@Stateless
public final class Directory {
    
    /* -------------------------------------------------- Root Directory -------------------------------------------------- */
    
    /**
     * Stores the default directory in the user home directory.
     */
    public static final @Nonnull @IsDirectory File DEFAULT = new File(System.getProperty("user.home") + File.separator + ".digitalid");
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    /**
     * Stores the root directory that contains all other directories.
     */
    public static final @Nonnull Configuration<File> configuration = Configuration.of(DEFAULT);
    
    /* -------------------------------------------------- Directories -------------------------------------------------- */
    
    /**
     * Creates the directory with the given name in the root directory.
     * 
     * @param name the name of the directory which is to be created.
     * 
     * @return the newly created directory with the given name.
     */
    private static @Nonnull @Existing @IsDirectory File createDirectory(@Nonnull String name) {
        final @Nonnull File directory = new File(configuration.get().getPath() + File.separator + name);
        if (!directory.exists() && !directory.mkdirs()) { throw InitializationError.of("Could not create the directory '" + directory.getPath() + "'."); }
        return directory;
    }
    
    /**
     * Returns the directory that contains the log files.
     * 
     * @return the directory that contains the log files.
     */
    public static @Nonnull @Existing @IsDirectory File getLogsDirectory() {
        return createDirectory("logs");
    }
    
    /**
     * Returns the directory that contains the configuration or the data of the database.
     * 
     * @return the directory that contains the configuration or the data of the database.
     */
    public static @Nonnull @Existing @IsDirectory File getDataDirectory() {
        return createDirectory("data");
    }
    
    /**
     * Returns the directory that contains the secret key of each local client.
     * 
     * @return the directory that contains the secret key of each local client.
     */
    public static @Nonnull @Existing @IsDirectory File getClientsDirectory() {
        return createDirectory("clients");
    }
    
    /**
     * Returns the directory that contains the key pairs of each local host.
     * 
     * @return the directory that contains the key pairs of each local host.
     */
    public static @Nonnull @Existing @IsDirectory File getHostsDirectory() {
        return createDirectory("hosts");
    }
    
    /**
     * Returns the directory that contains the code of all installed services.
     * 
     * @return the directory that contains the code of all installed services.
     */
    public static @Nonnull @Existing @IsDirectory File getServicesDirectory() {
        return createDirectory("services");
    }
    
    /* -------------------------------------------------- Listing -------------------------------------------------- */
    
    /**
     * Stores a filter to ignore hidden files.
     */
    private final static @Nonnull IgnoreHiddenFilesFilter ignoreHiddenFilesFilter = new IgnoreHiddenFilesFilter();
    
    /**
     * Returns a list of the non-hidden files in the given directory.
     * 
     * @param directory the directory whose files are to be returned.
     * 
     * @return a list of the non-hidden files in the given directory.
     */
    @Pure
    public static @Nonnull File[] listFiles(@Nonnull @Existing @IsDirectory File directory) {
        return directory.listFiles(ignoreHiddenFilesFilter);
    }
    
    /* -------------------------------------------------- Removal -------------------------------------------------- */
    
    /**
     * Deletes the given file or directory and returns whether it was successful.
     * 
     * @param file the file or directory to be deleted.
     * 
     * @return {@code true} if the given file was successfully deleted, {@code false} otherwise.
     */
    public static boolean delete(@Nonnull File file) {
        if (file.isDirectory()) {
            final @Nonnull File[] subfiles = file.listFiles();
            for (final @Nonnull File subfile : subfiles) {
                if (!delete(subfile)) { return false; }
            }
        }
        return file.delete();
    }
    
    /**
     * Empties the given directory and returns whether it was successful.
     * 
     * @param directory the directory to be emptied.
     * 
     * @return {@code true} if the given file was successfully emptied, {@code false} otherwise.
     */
    public static boolean empty(@Nonnull @IsDirectory File directory) {
        final @Nonnull File[] subfiles = directory.listFiles();
        for (final @Nonnull File subfile : subfiles) {
            if (!delete(subfile)) { return false; }
        }
        return true;
    }
    
}
