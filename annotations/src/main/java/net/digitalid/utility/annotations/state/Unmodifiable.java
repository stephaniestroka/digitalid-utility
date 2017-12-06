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
package net.digitalid.utility.annotations.state;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collections;

import net.digitalid.utility.annotations.method.Pure;

/**
 * This annotation indicates that an object is unmodifiable.
 * Only {@link Pure pure} methods can be called on unmodifiable objects.
 * (This annotation is intended for wrappers that still expose modifying methods but
 * throw, for example, an {@link UnsupportedOperationException} if they are called.)
 * 
 * @see Collections#unmodifiableCollection(java.util.Collection)
 * @see Collections#unmodifiableList(java.util.List)
 * @see Collections#unmodifiableMap(java.util.Map)
 * @see Collections#unmodifiableSet(java.util.Set)
 * 
 * @see Modifiable
 */
@Documented
@Target({ElementType.TYPE_USE, ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Unmodifiable {}
