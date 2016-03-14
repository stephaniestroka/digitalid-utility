package net.digitalid.utility.functional.function.binary;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * The function interface which maps one element of type &lt;I&gt; to an element of type &lt;O&gt;.
 */
@Stateless
public interface BinaryFunction<E0, E1, O> {
    
    /**
     * Applies the function on an element of the type &lt;I&gt; and delivers a result of type &lt;O&gt;.
     */
    @Pure
    public O apply(E0 element0, E1 element1);
    
}
