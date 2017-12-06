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
package net.digitalid.utility.conversion.model;

import java.lang.annotation.Annotation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.immutable.ImmutableMap;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class stores information about the field annotations parsed during compile-time. Type-use annotations and field annotations are treated equally.
 */
@Immutable
public class CustomAnnotation {
    
    /* -------------------------------------------------- Annotation Type -------------------------------------------------- */
    
    private final @Nonnull Class<? extends Annotation> annotationType;
    
    public @Nonnull Class<? extends Annotation> getAnnotationType() {
        return annotationType;
    }
    
    /* -------------------------------------------------- Annotation Fields -------------------------------------------------- */
    
    private final @Nonnull ImmutableMap<@Nonnull String, @Nullable Object> annotationFields;
    
    @SuppressWarnings("unchecked")
    public <T> @Nullable T get(@Nonnull String fieldName, @Nonnull Class<T> type) {
        return (T) annotationFields.get(fieldName);
    }
    
    /* -------------------------------------------------- Custom Annotation -------------------------------------------------- */
    
    CustomAnnotation(@Nonnull Class<? extends Annotation> annotationType, @Nonnull ImmutableMap<@Nonnull String, @Nullable Object> annotationFields) {
        this.annotationType = annotationType;
        this.annotationFields = annotationFields;
    }
    
    @Pure
    public static @Nonnull CustomAnnotation with(@Nonnull Class<? extends Annotation> annotationType, @Nonnull ImmutableMap<@Nonnull String, @Nullable Object> annotationFields) {
        return new CustomAnnotation(annotationType, annotationFields);
    }
    
    @Pure
    public static @Nonnull CustomAnnotation with(@Nonnull Class<? extends Annotation> annotationType) {
        return new CustomAnnotation(annotationType, ImmutableMap.withNoEntries());
    }
    
    /* -------------------------------------------------- Equals -------------------------------------------------- */
    
    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof CustomAnnotation)) {
            return false;
        } else {
            return annotationType.equals(((CustomAnnotation) object).annotationType);
        }
    }
    
    @Override
    public int hashCode() {
        return annotationType.hashCode();
    }
}
