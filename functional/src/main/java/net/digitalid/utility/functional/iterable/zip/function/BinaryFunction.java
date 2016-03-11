package net.digitalid.utility.functional.iterable.zip.function;

import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

// TODO: element1 and element2 may have different types.
/**
 * The function interface which maps one element of type &lt;I&gt; to an element of type &lt;O&gt;.
 */
@Stateless
public interface BinaryFunction<I, O, A> {
    
    /**
     * Applies the function on an element of the type &lt;I&gt; and delivers a result of type &lt;O&gt;.
     */
    @Pure
    public O apply(I element1, I element2, @Nullable A additionalInformation);
    
}
