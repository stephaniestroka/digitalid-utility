package net.digitalid.utility.functional.interfaces;

import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This functional interface models a binary function that maps an object of type {@code I0} and an object of type {@code I1} to an object of type {@code O}.
 */
public interface BinaryFunction<I0, I1, O> {
    
    /**
     * Applies the function on two elements of the type &lt;E0&gt; and &lt;E1&gt; and delivers a result of type &lt;O&gt;.
     */
    @Pure
    public O evaluate(I0 element0, I1 element1);
    
}
