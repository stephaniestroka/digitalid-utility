package net.digitalid.utility.concurrency.local;


import java.util.NoSuchElementException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.functional.iterators.ReadOnlyIterator;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a thread-local iterable, which means that each thread that accesses an instance of this class has its own iterable.
 */
@Mutable
@ThreadSafe
@GenerateBuilder
@GenerateSubclass
public abstract class ThreadLocalIterable<@Specifiable ELEMENT> implements FiniteIterable<ELEMENT> {
    
    /* -------------------------------------------------- Node -------------------------------------------------- */
    
    @Immutable
    private static class Node<@Specifiable ELEMENT> {
        
        private final ELEMENT element;
        
        private final @Nullable Node<ELEMENT> nextNode;
        
        private Node(ELEMENT element, @Nullable Node<ELEMENT> nextNode) {
            this.element = element;
            this.nextNode = nextNode;
        }
        
    }
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    @Mutable
    private static class Iterator<@Specifiable ELEMENT> extends ReadOnlyIterator<ELEMENT> {
        
        private @Nullable Node<ELEMENT> nextNode;
        
        @Pure
        @Override
        public boolean hasNext() {
            return nextNode != null;
        }
        
        @Impure
        @Override
        public ELEMENT next() throws NoSuchElementException {
            if (nextNode == null) { throw new NoSuchElementException(); }
            final ELEMENT result = nextNode.element;
            this.nextNode = nextNode.nextNode;
            return result;
        }
        
        private Iterator(@Nullable Node<ELEMENT> nextNode) {
            this.nextNode = nextNode;
        }
        
    }
    
    /* -------------------------------------------------- Iterable -------------------------------------------------- */
    
    private final @Nonnull ThreadLocal<@Nullable Node<ELEMENT>> threadLocal = new ThreadLocal<>();
    
    @Pure
    @Override
    public @Capturable @Nonnull ReadOnlyIterator<ELEMENT> iterator() {
        return new Iterator<>(threadLocal.get());
    }
    
    /* -------------------------------------------------- Modifications -------------------------------------------------- */
    
    /**
     * Adds the given element at the beginning of this thread-local iterable.
     */
    @Impure
    public void add(ELEMENT element) {
        threadLocal.set(new Node<>(element, threadLocal.get()));
    }
    
    /**
     * Removes all elements from this thread-local iterable.
     */
    @Impure
    public void clear() {
        threadLocal.remove();
    }
    
}
