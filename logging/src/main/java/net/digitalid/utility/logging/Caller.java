package net.digitalid.utility.logging;

import net.digitalid.utility.configuration.Configuration;

/**
 * This class determines the caller of a method.
 */
public class Caller {
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    /**
     * Stores the index of the caller of the logging method in the stack trace.
     */
    public static final Configuration<Integer> index = Configuration.with(5);
    
    /* -------------------------------------------------- Retrieval -------------------------------------------------- */
    
    /**
     * Returns the entry at the given index in the stack trace.
     */
    public static String get(int index) {
        final StackTraceElement element = Thread.currentThread().getStackTrace()[index];
        final String className = element.getClassName();
        final int lineNumber = element.getLineNumber();
        return className.substring(className.lastIndexOf('.') + 1) + "." + element.getMethodName() + (lineNumber > 0 ? ":" + lineNumber : "");
    }
    
    /**
     * Returns the caller of the logging method.
     */
    public static String get() {
        return get(index.get());
    }
    
}
