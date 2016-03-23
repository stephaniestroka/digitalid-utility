package net.digitalid.utility.functional.interfaces;

/**
 * This functional interface models a binary operator that maps two objects of type {@code T} to another object of type {@code T}.
 */
public interface UnaryOperator<T> extends UnaryFunction<T, T> {
    
    /**
     * Returns a unary operator that always returns its input argument.
     */
    public static <T> UnaryOperator<T> identity() {
        return t -> t;
    }
    
}
