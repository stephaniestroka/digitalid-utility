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
package net.digitalid.utility.processor.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.annotation.processing.SupportedAnnotationTypes;

import net.digitalid.utility.processor.CustomProcessor;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;

/**
 * This annotation indicates what annotation types an annotation processor supports.
 * It is an alternative to {@link SupportedAnnotationTypes} which prevents typos and
 * supports auto-completion and renaming by integrated development environments.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SupportedAnnotations {
    
    /**
     * Returns the annotation types that the annotated annotation processor supports.
     */
    @Nonnull @NonNullableElements Class<? extends Annotation>[] value() default {};
    
    /**
     * Returns the prefixes of annotation types that the annotated annotation processor supports.
     * The wildcard character '*' will be appended to each prefix by the {@link CustomProcessor}.
     */
    @Nonnull @NonNullableElements String[] prefix() default {};
    
}
