package net.digitalid.utility.functional.iterable.map.function;

/**
 * The function interface which maps one or more elements of type &lt;I&gt; to elements of type &lt;O&gt;.
 */
public interface Function<I, O> {
    
    /**
     * Applies the function on elements of the type &lt;I&gt; and delivers a result of type &lt;O&gt;.
     */
    public O apply(I... element);
    
}
