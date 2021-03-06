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
import java.util.Objects;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.exceptions.CaseExceptionBuilder;
import net.digitalid.utility.immutable.ImmutableList;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class represents a field of a type. If is used to analyze the content of a class which is particularly interesting 
 * when an object of this class is converted to another form of representation.
 */
@Immutable
public class CustomField {
    
    /* -------------------------------------------------- Type -------------------------------------------------- */
    
    /**
     * The type of the field.
     */
    private final @Nonnull CustomType customType;
    
    /**
     * Returns the type of the field.
     */
    @Pure
    public @Nonnull CustomType getCustomType() {
        return customType;
    }
    
    /* -------------------------------------------------- Name -------------------------------------------------- */
    
    /**
     * The name of the field.
     */
    private final @Nonnull String name;
    
    /**
     * Returns the name of the field
     */
    @Pure
    public @Nonnull String getName() {
        return name;
    }
    
    /* -------------------------------------------------- Annotations -------------------------------------------------- */
    
    /**
     * An immutable list of annotations of the field.
     */
    private final @Nonnull ImmutableList<@Nonnull CustomAnnotation> annotations;
    
    /**
     * Returns an immutable list of annotations of the field.
     */
    @Pure
    public @Nonnull ImmutableList<@Nonnull CustomAnnotation> getAnnotations() {
        return annotations;
    }
    
    /**
     * Returns true iff the field is annotated with a given annotation type.
     */
    @Pure
    public <A extends Annotation> boolean isAnnotatedWith(@Nonnull Class<A> annotationType) {
        for (@Nonnull CustomAnnotation annotation : annotations) {
            if (annotationType.isAssignableFrom(annotation.getAnnotationType())) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Returns the annotation of the field that is of the given annotation type.
     * The annotation must exist.
     */
    @Pure
    @SuppressWarnings("unchecked")
    public <A extends Annotation> @Nonnull CustomAnnotation getAnnotation(@Nonnull Class<A> annotationType) {
        Require.that(isAnnotatedWith(annotationType)).orThrow("Field $ is not annotated with $.", name, annotationType);
    
        for (@Nonnull CustomAnnotation annotation : annotations) {
            if (annotationType.isAssignableFrom(annotation.getAnnotationType())) {
                return annotation;
            }
        }
        
        throw CaseExceptionBuilder.withVariable("annotationType").withValue(annotationType).build();
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new instance of custom field for a given custom type, name and list of field annotations.
     */
    private CustomField(@Nonnull CustomType customType, @Nonnull String name, @Nonnull ImmutableList<@Nonnull CustomAnnotation> annotations) {
        this.customType = customType;
        this.name = name;
        this.annotations = annotations;
    }
    
    /**
     * Returns a new instance of custom field for a given custom type, name and list of field annotations.
     */
    @Pure
    public static @Nonnull CustomField with(@Nonnull CustomType customType, @Nonnull String name, @Nonnull ImmutableList<@Nonnull CustomAnnotation> annotations) {
        return new CustomField(customType, name, annotations);
    }
    
    /**
     * Returns a new instance of custom field for a given custom type and name.
     */
    @Pure
    public static @Nonnull CustomField with(@Nonnull CustomType customType, @Nonnull String name) {
        return new CustomField(customType, name, ImmutableList.withElements());
    }
    
    /* -------------------------------------------------- Equals -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof CustomField)) {
            return false;
        } else {
            final @Nonnull CustomField other = (CustomField) object;
            return name.equals(other.name) && customType.equals(other.customType) && annotations.equals(other.annotations);
        }
    }
    
    @Pure
    @Override
    public int hashCode() {
        int prime = 92_821;
        int result = 46_411;
        result = prime * result + Objects.hashCode(name);
        result = prime * result + Objects.hashCode(customType);
        result = prime * result + Objects.hashCode(annotations);
        return result;
    }
    
}
