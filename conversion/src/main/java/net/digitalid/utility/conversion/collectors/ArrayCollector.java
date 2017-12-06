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
package net.digitalid.utility.conversion.collectors;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.conversion.exceptions.RecoveryException;
import net.digitalid.utility.conversion.exceptions.RecoveryExceptionBuilder;
import net.digitalid.utility.functional.failable.FailableCollector;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.type.Mutable;

@Mutable
public class ArrayCollector<@Specifiable TYPE> implements FailableCollector<TYPE, TYPE[], RecoveryException, RecoveryException> {
    
    /* -------------------------------------------------- Array -------------------------------------------------- */
    
    private final @Nonnull TYPE[] array;
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    @SuppressWarnings("unchecked")
    protected ArrayCollector(@NonNegative int size) {
        this.array = (TYPE[]) new Object[size];
    }
    
    @Pure
    public static <@Specifiable TYPE> @Nonnull ArrayCollector<TYPE> with(@NonNegative int size) {
        return new ArrayCollector<>(size);
    }
    
    /* -------------------------------------------------- Collector -------------------------------------------------- */
    
    private int nextIndex = 0;
    
    @Impure
    @Override
    public void consume(@Captured TYPE element) throws RecoveryException {
        if (nextIndex < array.length) {
            array[nextIndex] = element;
            nextIndex += 1;
        } else {
            throw RecoveryExceptionBuilder.withMessage("The array can take at most " + Strings.getCardinal(array.length) + " elements.").build();
        }
    }
    
    @Pure
    @Override
    @SuppressWarnings("ReturnOfCollectionOrArrayField")
    public @Capturable TYPE[] getResult() {
        return array;
    }
    
}
