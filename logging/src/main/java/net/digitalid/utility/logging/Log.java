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
package net.digitalid.utility.logging;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.logging.logger.Logger;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * This class makes it easier to {@link Logger#log(net.digitalid.utility.logging.Level, java.lang.CharSequence, java.lang.Throwable, java.lang.Object...) log} messages.
 */
@Utility
public abstract class Log {
    
    /* -------------------------------------------------- Fatal -------------------------------------------------- */
    
    /**
     * Logs the given message and throwable as a fatal error that prevents the thread or process from continuing.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void fatal(@Nonnull CharSequence message, @Nullable Throwable throwable, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        Logger.log(Level.FATAL, message, throwable, arguments);
    }
    
    /**
     * Logs the given message as a fatal error that prevents the thread or process from continuing.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void fatal(@Nonnull CharSequence message, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        Logger.log(Level.FATAL, message, null, arguments);
    }
    
    /* -------------------------------------------------- Error -------------------------------------------------- */
    
    /**
     * Logs the given message and throwable as an error from which the application can possibly recover.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void error(@Nonnull CharSequence message, @Nullable Throwable throwable, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        Logger.log(Level.ERROR, message, throwable, arguments);
    }
    
    /**
     * Logs the given message as an error from which the application can possibly recover.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void error(@Nonnull CharSequence message, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        Logger.log(Level.ERROR, message, null, arguments);
    }
    
    /* -------------------------------------------------- Warning -------------------------------------------------- */
    
    /**
     * Logs the given message and throwable as a warning that indicate potential problems in the program.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void warning(@Nonnull CharSequence message, @Nullable Throwable throwable, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        Logger.log(Level.WARNING, message, throwable, arguments);
    }
    
    /**
     * Logs the given message as a warning that indicate potential problems in the program.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void warning(@Nonnull CharSequence message, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        Logger.log(Level.WARNING, message, null, arguments);
    }
    
    /* -------------------------------------------------- Information -------------------------------------------------- */
    
    /**
     * Logs the given message and throwable to inform about important runtime events.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void information(@Nonnull CharSequence message, @Nullable Throwable throwable, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        Logger.log(Level.INFORMATION, message, throwable, arguments);
    }
    
    /**
     * Logs the given message to inform about important runtime events.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void information(@Nonnull CharSequence message, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        Logger.log(Level.INFORMATION, message, null, arguments);
    }
    
    /* -------------------------------------------------- Debugging -------------------------------------------------- */
    
    /**
     * Logs the given message and throwable to help developers locate bugs in the code.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void debugging(@Nonnull CharSequence message, @Nullable Throwable throwable, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        Logger.log(Level.DEBUGGING, message, throwable, arguments);
    }
    
    /**
     * Logs the given message to help developers locate bugs in the code.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void debugging(@Nonnull CharSequence message, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        Logger.log(Level.DEBUGGING, message, null, arguments);
    }
    
    /* -------------------------------------------------- Verbose -------------------------------------------------- */
    
    /**
     * Logs the given message and throwable to make it easier to trace the execution of the program.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void verbose(@Nonnull CharSequence message, @Nullable Throwable throwable, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        Logger.log(Level.VERBOSE, message, throwable, arguments);
    }
    
    /**
     * Logs the given message to make it easier to trace the execution of the program.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void verbose(@Nonnull CharSequence message, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        Logger.log(Level.VERBOSE, message, null, arguments);
    }
    
}
