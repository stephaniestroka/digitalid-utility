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
package net.digitalid.utility.validation.annotations.type;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.type.ThreadSafe;

/**
 * This annotation indicates that the objects of the annotated class are mutable.
 * It is not safe to share mutable objects between various instances and threads,
 * unless the annotated class is also {@link ThreadSafe thread-safe}.
 * 
 * @see Immutable
 * @see ReadOnly
 * @see Stateless
 * @see Utility
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mutable {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the type which provides read-only access to the annotated type (or the type of this annotation as a default because null is not allowed).
     */
    @Nonnull Class<?> value() default Mutable.class;
    
}
