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
package net.digitalid.utility.processing.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.digitalid.utility.processing.logging.ProcessingLog;

/**
 * This annotation indicates that the annotated method logs a processing error when returning null.
 * The caller can thus simply check whether the return value is null without having to log another error.
 * The reason for not throwing exceptions instead is that the annotation processing shall continue to the end.
 * 
 * @see ProcessingLog#error(java.lang.CharSequence, net.digitalid.utility.processing.logging.SourcePosition, java.lang.Object...)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface LogsErrorWhenReturningNull {}
