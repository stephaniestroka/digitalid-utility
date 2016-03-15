package net.digitalid.utility.functional.function.binary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * The function interface which maps two elements of type &lt;E0&gt; and &lt;E1&gt; to a non-nullable element of type &lt;O&gt;.
 */
@Stateless
public interface ToNonNullBinaryFunction<E0, E1, O> extends BinaryFunction<E0, E1, O> {
    
    /**
     * Applies the function on elements of the type &lt;E0&gt; and &lt;E1&gt; and delivers a non-nullable result of type &lt;O&gt;.
     */
    @Pure
    public @Nonnull O apply(E0 element0, E1 element1);
    
}
