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
package net.digitalid.utility.threading;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * This interfaces facilitates the handling of threads.
 */
@Stateless
@Functional
public interface Threading {
    
    /* -------------------------------------------------- Interface -------------------------------------------------- */
    
    /**
     * Runs the given runnable on the GUI thread immediately or at some unspecified time in the future.
     * If the current thread is the GUI thread, then the given runnable may be executed immediately.
     * Otherwise, the given runnable is posted to an event queue and the method returns immediately.
     * The runnables passed to this method are guaranteed to be run in the order they were passed.
     */
    @Pure
    public void executeOnGuiThread(@Nonnull Runnable runnable);
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    /**
     * Stores the threading implementation which has to be provided by another package.
     */
    public static final @Nonnull Configuration<Threading> configuration = Configuration.withUnknownProvider();
    
    /* -------------------------------------------------- Static Access -------------------------------------------------- */
    
    /**
     * Runs the given runnable on the GUI thread immediately or at some unspecified time in the future.
     * If the current thread is the GUI thread, then the given runnable may be executed immediately.
     * Otherwise, the given runnable is posted to an event queue and the method returns immediately.
     * The runnables passed to this method are guaranteed to be run in the order they were passed.
     */
    @Pure
    public static void runOnGuiThread(@Nonnull Runnable runnable) {
        configuration.get().executeOnGuiThread(runnable);
    }
    
    /* -------------------------------------------------- Main Thread -------------------------------------------------- */
    
    /**
     * Stores the main thread.
     */
    public static final @Nonnull Thread MAIN = Thread.currentThread();
    
    /**
     * Returns whether the current thread is the main thread.
     */
    @Pure
    public static boolean isMainThread() {
        return Thread.currentThread() == MAIN;
    }
    
}
