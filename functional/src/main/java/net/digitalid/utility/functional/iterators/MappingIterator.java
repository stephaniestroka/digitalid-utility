/*
 * Copyright (C) 2017 Synacts GmbH, Switzerland (info@synacts.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.digitalid.utility.functional.iterators;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.exceptions.IterationExceptionBuilder;
import net.digitalid.utility.functional.failable.FailableUnaryFunction;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a mapping iterator that iterates over the elements of the given iterator mapped by the given function.
 */
@Mutable
public class MappingIterator<@Specifiable OUTPUT, @Specifiable INPUT> extends SingleIteratorBasedIterator<OUTPUT, INPUT> {
    
    /* -------------------------------------------------- Predicate -------------------------------------------------- */
    
    protected final @Nonnull FailableUnaryFunction<? super INPUT, ? extends OUTPUT, ?> function;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected MappingIterator(@Captured @Nonnull Iterator<INPUT> primaryIterator, @Nonnull FailableUnaryFunction<? super INPUT, ? extends OUTPUT, ?> function) {
        super(primaryIterator);
        
        this.function = function;
    }
    
    /**
     * Returns a new mapping iterator that iterates over the elements of the given iterator mapped by the given function.
     */
    @Pure
    public static @Capturable <@Specifiable OUTPUT, @Specifiable INPUT> @Nonnull MappingIterator<OUTPUT, INPUT> with(@Captured @Nonnull Iterator<INPUT> iterator, @Nonnull FailableUnaryFunction<? super INPUT, ? extends OUTPUT, ?> function) {
        return new MappingIterator<>(iterator, function);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean hasNext() {
        return primaryIterator.hasNext();
    }
    
    @Impure
    @Override
    public OUTPUT next() {
        try {
            return function.evaluate(primaryIterator.next());
        } catch (@Nonnull Exception exception) {
            throw IterationExceptionBuilder.withCause(exception).build();
        }
    }
    
}
