package net.digitalid.utility.system.converter.exceptions;

public class TypeUnknownException extends Exception {
    
    protected TypeUnknownException(Class<?> type) {
        super("The type '" + type + "' is unknown.");
    }

    protected TypeUnknownException(Class<?> type, Throwable t) {
        super("The type '" + type + "' is unknown.", t);
    }
    
    public static TypeUnknownException get(Class<?> type) {
        return new TypeUnknownException(type);
    }
    
    public static TypeUnknownException get(Class<?> type, Throwable t) {
        return new TypeUnknownException(type, t);
    }
}
