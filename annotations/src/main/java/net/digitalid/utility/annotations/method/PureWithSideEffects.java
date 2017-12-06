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
package net.digitalid.utility.annotations.method;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This annotation indicates that the annotated method has no side effects (other than caching) on the called object (or class) but affects other aspects of the system.
 * A method with this annotation should trigger similar side effects on each invocation but may fail after the first invocation (e.g. due to consistency constraints).
 * This annotation is often used on methods in {@link Immutable immutable} classes that log, send, store or execute something and thus have observable interactions.
 * 
 * @see Pure
 * @see Impure
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PureWithSideEffects {}
