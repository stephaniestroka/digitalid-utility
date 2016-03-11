package net.digitalid.utility.functional.iterable.zip.function;

import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * The function interface which maps one or more elements of type &lt;I&gt; to elements of type &lt;O&gt;.
 */
@Stateless
public interface ToNullableBinaryFunction<I, O, A> extends BinaryFunction<I, O, A> {
    
    /**
     * Applies the function on elements of the type &lt;I&gt; and delivers a result of type &lt;O&gt;.
     */
    @Pure
    public @Nullable O apply(I element1, I element2, @Nullable A additionalInformation);
    
}
