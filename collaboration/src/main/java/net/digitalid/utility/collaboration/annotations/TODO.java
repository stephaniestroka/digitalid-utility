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
package net.digitalid.utility.collaboration.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.collaboration.enumerations.Author;
import net.digitalid.utility.collaboration.enumerations.Priority;

/**
 * This annotation indicates a task that still needs to be done.
 * 
 * @see Question
 * @see Review
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD})
public @interface TODO {
    
    /**
     * Returns a description of the task that needs to be done.
     */
    @Nonnull String task();
    
    /**
     * Returns the date when this todo was created.
     * (The desired format is 'YYYY-MM-DD'.)
     */
    @Nonnull String date();
    
    /**
     * Returns the author that created this todo.
     */
    @Nonnull Author author();
    
    /**
     * Returns the programmer to whom the task is assigned.
     */
    @Nonnull Author assignee() default Author.ANYONE;
    
    /**
     * Returns the priority with which the task should be done.
     */
    @Nonnull Priority priority() default Priority.UNSPECIFIED;
    
}
