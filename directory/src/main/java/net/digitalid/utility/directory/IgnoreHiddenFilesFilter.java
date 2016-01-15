package net.digitalid.utility.directory;

import java.io.File;
import java.io.FilenameFilter;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.state.Pure;
import net.digitalid.utility.validation.state.Stateless;

/**
 * This class implements a filter to ignore hidden files.
 */
@Stateless
public class IgnoreHiddenFilesFilter implements FilenameFilter {
    
    @Pure
    @Override
    public boolean accept(@Nonnull File directory, @Nonnull String name) {
        return !name.startsWith(".");
    }
    
}
