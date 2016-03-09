package net.digitalid.utility.functional.iterable.map.function;

import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * The function interface which maps one element of type &lt;I&gt; to an element of type &lt;O&gt;.
 * 
 * @param <A> additional information that might be required when applying the function.
 */
@Stateless
public interface UnaryFunction<I, O, A> {
    
    /**
     * Applies the function on an element of the type &lt;I&gt; and delivers a result of type &lt;O&gt;.
     */
    @Pure
    public O apply(I element, @Nullable A additionalInformation);
    
}
