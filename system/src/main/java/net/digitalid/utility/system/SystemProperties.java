/*
 * Copyright (C) 2017 Synacts GmbH, Switzerland (info@synacts.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.digitalid.utility.system;

import java.io.IOException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * This class makes it easier to access system properties.
 */
@Utility
public abstract class SystemProperties {
    
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
