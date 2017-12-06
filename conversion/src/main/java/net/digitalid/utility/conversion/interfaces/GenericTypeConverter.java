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
package net.digitalid.utility.conversion.interfaces;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * A converter for a type with a generic parameter has to be constructed with a converter for objects of that parameter.
 */
@Immutable
public interface GenericTypeConverter<@Unspecifiable OBJECT, @Unspecifiable TYPE, @Specifiable PROVIDED> extends Converter<TYPE, PROVIDED> {
    
    /* -------------------------------------------------- Object Converter -------------------------------------------------- */
    
    /**
     * Returns the converter of the wrapped object.
     */
    @Pure
    public abstract @Nonnull Converter<OBJECT, Void> getObjectConverter();
    
}
