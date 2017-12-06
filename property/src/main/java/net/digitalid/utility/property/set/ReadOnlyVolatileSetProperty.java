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
package net.digitalid.utility.property.set;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.collections.set.ReadOnlySet;
import net.digitalid.utility.validation.annotations.type.ReadOnly;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This read-only property stores a set of values in volatile memory.
 * 
 * @see WritableVolatileSetProperty
 * @see ReadOnlyVolatileSimpleSetProperty
 */
@ThreadSafe
@ReadOnly(WritableVolatileSetProperty.class)
public interface ReadOnlyVolatileSetProperty<@Unspecifiable VALUE, @Unspecifiable READONLY_SET extends ReadOnlySet<@Nonnull @Valid VALUE>> extends ReadOnlySetProperty<VALUE, READONLY_SET, RuntimeException, RuntimeException, VolatileSetObserver<VALUE, READONLY_SET>, ReadOnlyVolatileSetProperty<VALUE, READONLY_SET>> {}
