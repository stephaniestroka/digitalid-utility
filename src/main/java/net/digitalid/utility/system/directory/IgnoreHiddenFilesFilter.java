package net.digitalid.utility.system.directory;

import java.io.File;
import java.io.FilenameFilter;
import javax.annotation.Nonnull;
import net.digitalid.utility.annotations.state.Pure;
import net.digitalid.utility.annotations.state.Stateless;

/**
 * This class implements a filter to ignore hidden files.
 * 
 * @author Kaspar Etter (kaspar.etter@digitalid.net)
 * @version 1.0.0
 */
@Stateless
public class IgnoreHiddenFilesFilter implements FilenameFilter {
    
    @Pure
    @Override
    public boolean accept(@Nonnull File directory, @Nonnull String name) {
        return !name.startsWith(".");
    }
    
}
