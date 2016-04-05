package net.digitalid.utility.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * This class makes it easier to execute commands.
 */
@Utility
public final class CommandExecutor {
    
    /* -------------------------------------------------- Execute -------------------------------------------------- */
    
    /**
     * Executes the given command and returns the first line of the command's output.
     */
    @Impure
    public static @Nullable String execute(@Nonnull String command) throws IOException {
        final @Nonnull Process process = Runtime.getRuntime().exec(command);
        final @Nonnull BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        return reader.readLine();
    }
    
}
