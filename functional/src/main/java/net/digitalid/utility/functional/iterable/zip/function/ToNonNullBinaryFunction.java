package net.digitalid.utility.functional.iterable.zip.function;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * The function interface which maps one or more elements of type &lt;I&gt; to elements of type &lt;O&gt;.
 */
@Stateless
public interface ToNonNullBinaryFunction<I, O> extends BinaryFunction<I, O> {
    
    /**
     * Applies the function on elements of the type &lt;I&gt; and delivers a result of type &lt;O&gt;.
     */
    @Pure
    public @Nonnull O apply(I element1, I element2);
    
}
