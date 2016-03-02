package net.digitalid.utility.system;

import java.io.IOException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * This class makes it easier to access system properties.
 */
@Utility
public class SystemProperties {
    
    /* -------------------------------------------------- User Name -------------------------------------------------- */

    /**
     * Returns the name of the user.
     */
    @Pure
    public static @Nonnull String getUserName() {
        final @Nonnull String OSname = System.getProperty("os.name").toLowerCase();
        if (OSname.contains("mac")) {
            try {
                final @Nullable String userName = CommandExecutor.execute("id -F");
                if (userName != null) { return userName; }
            } catch (@Nonnull IOException exception) {}
        } else if (OSname.contains("win")) {
            // TODO: http://stackoverflow.com/questions/7809648/get-display-name-of-current-windows-domain-user-from-a-command-prompt
        } else if (OSname.contains("linux") || OSname.contains("unix")) {
            // TODO: getent passwd $USER | cut -d ":" -f 5
        }
        return System.getProperty("user.name");
    }
    
}
