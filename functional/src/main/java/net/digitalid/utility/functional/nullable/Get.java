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
package net.digitalid.utility.functional.nullable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * This utility class allows to replace nullable values with non-nullable default values.
 * 
 * @see Evaluate
 */
@Utility
public abstract class Get {
    
    /**
     * Returns the given nullable value if it is not null or the given default value otherwise.
     */
    @Pure
    public static <@Unspecifiable TYPE> @Nonnull TYPE valueOrDefault(@NonCaptured @Unmodified @Nullable TYPE nullableValue, @NonCaptured @Unmodified @Nonnull TYPE defaultValue) {
        return nullableValue != null ? nullableValue : defaultValue;
    }
    
}
