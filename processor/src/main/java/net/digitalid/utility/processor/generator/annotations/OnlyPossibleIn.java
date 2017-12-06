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
package net.digitalid.utility.processor.generator.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.processor.generator.JavaFileGenerator;

/**
 * This annotation indicates that a method may only be invoked in one of the given code blocks.
 * 
 * @see JavaFileGenerator
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnlyPossibleIn {
    
    // Due to a bug in the Javadoc plugin, the qualified name of the CodeBlock class has to be used in the following comment.
    /**
     * Returns the code blocks in which the annotated method may be invoked.
     * An empty array of code blocks is used to indicate that any block that
     * {@link net.digitalid.utility.processor.generator.JavaFileGenerator.CodeBlock#allowsStatements() allows statements}
     * is fine. For this reason, the value defaults to an empty array.
     */
    JavaFileGenerator.@Nonnull CodeBlock[] value() default {};
    
}
