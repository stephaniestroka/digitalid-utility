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
package net.digitalid.utility.generator.information;

import java.lang.annotation.Annotation;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.information.field.FieldInformationImplementation;
import net.digitalid.utility.generator.information.method.ExecutableInformation;
import net.digitalid.utility.generator.information.type.TypeInformation;
import net.digitalid.utility.immutable.ImmutableSet;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class implements the {@link ElementInformation} interface.
 * 
 * @see FieldInformationImplementation
 * @see ExecutableInformation
 * @see TypeInformation
 */
@Immutable
public abstract class ElementInformationImplementation implements ElementInformation {
    
    /* -------------------------------------------------- Element -------------------------------------------------- */
    
    private final @Nonnull Element element;
    
    @Pure
    @Override
    public @Nonnull Element getElement() {
        return element;
    }
    
    /* -------------------------------------------------- Type -------------------------------------------------- */
    
    private final @Nonnull TypeMirror type;
    
    @Pure
    @Override
    public @Nonnull TypeMirror getType() {
        return type;
    }
    
    /* -------------------------------------------------- Containing Type -------------------------------------------------- */
    
    private final @Nonnull DeclaredType containingType;
    
    @Pure
    @Override
    public @Nonnull DeclaredType getContainingType() {
        return containingType;
    }
    
    /* -------------------------------------------------- Modifiers -------------------------------------------------- */
    
    private final @Nonnull ImmutableSet<@Nonnull Modifier> modifiers;
    
    @Pure
    @Override
    public @Nonnull ImmutableSet<@Nonnull Modifier> getModifiers() {
        return modifiers;
    }
    
    /* -------------------------------------------------- Annotations -------------------------------------------------- */
    
    private final @Nonnull FiniteIterable<@Nonnull AnnotationMirror> annotations;
    
    @Pure
    @Override
    public @Nonnull FiniteIterable<@Nonnull AnnotationMirror> getAnnotations() {
        return annotations;
    }
    
    private final @Nonnull Map<@Nonnull String, @Nonnull AnnotationMirror> annotationsMap;
    
    @Pure
    @Override
    public boolean hasAnnotation(@Nonnull String canonicalName) {
        return annotationsMap.containsKey(canonicalName);
    }
    
    @Pure
    @Override
    public boolean hasAnnotation(@Nonnull Class<? extends Annotation> annotationType) {
        return hasAnnotation(annotationType.getCanonicalName());
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ElementInformationImplementation(@Nonnull Element element, @Nonnull TypeMirror type, @Nonnull DeclaredType containingType) {
        this.element = element;
        this.type = type;
        this.containingType = containingType;
        this.modifiers = ImmutableSet.withElementsOfCollection(element.getModifiers());
        this.annotations = ProcessingUtility.getAnnotationMirrors(element);
        this.annotationsMap = annotations.toMap(ProcessingUtility::getQualifiedName);
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean equals(@Nullable Object object) {
        if (object == this) { return true; }
        if (object == null || !(object instanceof ElementInformation)) { return false; }
        final @Nonnull ElementInformation that = (ElementInformation) object;
        return this.getName().equals(that.getName());
    }
    
    @Pure
    @Override
    public int hashCode() {
        return getName().hashCode();
    }
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return element.getKind().toString().toLowerCase() + " " + getName();
    }
    
}
