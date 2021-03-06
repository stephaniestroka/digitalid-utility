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
package net.digitalid.utility.logging.logger;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.logging.Version;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a logger that logs the messages to a print stream.
 * 
 * @see StandardOutputLogger
 * @see FileLogger
 */
@Mutable
public abstract class PrintStreamLogger extends Logger {
    
    /* -------------------------------------------------- Time Format -------------------------------------------------- */
    
    private static final @Nonnull ThreadLocal<@Nonnull DateFormat> timeFormat = new ThreadLocal<DateFormat>() {
        @Pure @Override protected @Capturable @Nonnull DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss.SSS");
        }
    };
    
    /* -------------------------------------------------- Print Stream -------------------------------------------------- */
    
    private @Nonnull PrintStream printStream;
    
    /**
     * Sets the print stream to which the messages are printed.
     * This class uses the given print stream for synchronization.
     */
    @Impure
    @SuppressWarnings("SynchronizeOnNonFinalField")
    protected void setPrintStream(@Captured @Nonnull PrintStream printStream) {
        Require.that(printStream != null).orThrow("The print stream may not be null.");
        
        synchronized (this.printStream) {
            this.printStream.close();
            this.printStream = printStream;
        }
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a print stream logger that logs the messages to the given print stream.
     * This class uses the given print stream for synchronization.
     */
    protected PrintStreamLogger(@Captured @Nonnull PrintStream printStream) {
        Require.that(printStream != null).orThrow("The print stream may not be null.");
        
        this.printStream = printStream;
    }
    
    /* -------------------------------------------------- Logging -------------------------------------------------- */
    
    @Impure
    @Override
    @SuppressWarnings("SynchronizeOnNonFinalField")
    protected void log(@Nonnull Level level, @Nonnull String caller, @Nonnull String thread, @Nonnull String message, @Nullable Throwable throwable) {
        final @Nonnull String version = Version.string.get();
        synchronized (printStream) {
            printStream.println(timeFormat.get().format(new Date()) + (version.isEmpty() ? "" : " in " + version) + " [" + thread + "] (" + level + ") <" + caller + ">: " + message);
            if (throwable != null) {
                printStream.println();
                throwable.printStackTrace(printStream);
                printStream.println();
            }
            printStream.flush();
        }
    }
    
}
