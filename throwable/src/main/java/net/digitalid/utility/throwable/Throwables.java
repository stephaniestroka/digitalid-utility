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
package net.digitalid.utility.throwable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * This class provides useful operations on throwables.
 */
@Utility
public abstract class Throwables {
    
    /* -------------------------------------------------- Summary -------------------------------------------------- */
    
    /**
     * Returns a one-line summary of the given throwable.
     */
    @Pure
    public static @Nonnull String getSummary(@Nonnull Throwable throwable) {
        final @Nonnull StringBuilder message = new StringBuilder(throwable.getMessage());
        @Nullable Throwable cause = throwable.getCause();
        while (cause != null) {
            message.append(": ");
            if (cause instanceof NullPointerException) {
                message.append("A NullPointerException happened at: ").append(cause.getStackTrace()[0]).append(".");
            } else {
                message.append(cause.getMessage());
            }
            cause = cause.getCause();
        }
        return message.toString();
    }
    
}
