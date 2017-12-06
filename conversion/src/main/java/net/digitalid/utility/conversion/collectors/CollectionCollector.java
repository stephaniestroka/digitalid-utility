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

import java.util.Collection;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.conversion.exceptions.RecoveryException;
import net.digitalid.utility.functional.failable.FailableCollector;
import net.digitalid.utility.validation.annotations.type.Mutable;

@Mutable
public class CollectionCollector<@Specifiable TYPE, @Unspecifiable COLLECTION extends Collection<TYPE>> implements FailableCollector<TYPE, COLLECTION, RecoveryException, RecoveryException> {
    
    /* -------------------------------------------------- Collection -------------------------------------------------- */
    
    private final @Nonnull COLLECTION collection;
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    protected CollectionCollector(@Nonnull COLLECTION collection) {
        this.collection = collection;
    }
    
    @Pure
    public static <@Specifiable TYPE, @Unspecifiable COLLECTION extends Collection<TYPE>> @Nonnull CollectionCollector<TYPE, COLLECTION> with(@Nonnull COLLECTION collection) {
        return new CollectionCollector<>(collection);
    }
    
    /* -------------------------------------------------- Collector -------------------------------------------------- */
    
    @Impure
    @Override
    public void consume(@Captured TYPE element) {
        collection.add(element);
    }
    
    @Pure
    @Override
    public @Capturable COLLECTION getResult() {
        return collection;
    }
    
}
