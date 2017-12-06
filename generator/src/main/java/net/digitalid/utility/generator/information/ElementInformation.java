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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.generators.BuilderGenerator;
import net.digitalid.utility.generator.generators.SubclassGenerator;
import net.digitalid.utility.generator.information.field.FieldInformation;
import net.digitalid.utility.immutable.ImmutableSet;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.rootclass.RootInterface;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This type collects the relevant information about an element for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 * 
 * @see ElementInformationImplementation
 * @see FieldInformation
 */
@Immutable
public interface ElementInformation extends RootInterface {
    
    /* -------------------------------------------------- Element -------------------------------------------------- */
    
    /**
     * Returns the element that is represented by this information object.
     */
    @Pure
    public @Nonnull Element getElement();
    
    /**
     * Returns the name of the represented {@link #getElement() element}.
     */
    @Pure
    public default @Nonnull String getName() {
        return getElement().getSimpleName().toString();
    }
    
    /* -------------------------------------------------- Type -------------------------------------------------- */
    
    /**
     * Returns the type of the represented {@link #getElement() element}.
     */
    @Pure
    public @Nonnull TypeMirror getType();
    
    /* -------------------------------------------------- Containing Type -------------------------------------------------- */
    
    /**
     * Returns the type that contains the (potentially inherited) {@link #getElement() element}.
     * (In case of top-level types, this method returns the {@link #getType() type} itself.)
     */
    @Pure
    public @Nonnull DeclaredType getContainingType();
    
    /* -------------------------------------------------- Package -------------------------------------------------- */
    
    /**
     * Returns the name of the package in which the represented {@link #getElement() element} is declared.
     */
    @Pure
    public default @Nonnull String getQualifiedPackageName() {
        return ProcessingUtility.getQualifiedPackageName(getElement());
    }
    
    /**
     * Returns whether the {@link #getElement() element} is declared in the Java Runtime Environment (JRE).
     */
    @Pure
    public default boolean isDeclaredInRuntimeEnvironment() {
        return ProcessingUtility.isDeclaredInRuntimeEnvironment(getElement());
    }
    
    /**
     * Returns whether the {@link #getElement() element} is declared in the Digital ID library (DID SDK).
     */
    @Pure
    public default boolean isDeclaredInDigitalIDLibrary() {
        final @Nonnull String containingTypeName = ProcessingUtility.getSimpleName(getContainingType());
        return ProcessingUtility.isDeclaredInDigitalIDLibrary(getElement()) && !containingTypeName.endsWith("Assert") && !containingTypeName.endsWith("Assertions");
    }
    
    /* -------------------------------------------------- Modifiers -------------------------------------------------- */
    
    /**
     * Returns the modifiers of the represented {@link #getElement() element}.
     */
    @Pure
    public @Nonnull ImmutableSet<@Nonnull Modifier> getModifiers();
    
    /**
     * Returns whether the represented {@link #getElement() element} is public.
     */
    @Pure
    public default boolean isPublic() {
        return getModifiers().contains(Modifier.PUBLIC);
    }
    
    /**
     * Returns whether the represented {@link #getElement() element} is protected.
     */
    @Pure
    public default boolean isProtected() {
        return getModifiers().contains(Modifier.PROTECTED);
    }
    
    /**
     * Returns whether the represented {@link #getElement() element} is private.
     */
    @Pure
    public default boolean isPrivate() {
        return getModifiers().contains(Modifier.PRIVATE);
    }
    
    /**
     * Returns whether the represented {@link #getElement() element} is final.
     */
    @Pure
    public default boolean isFinal() {
        return getModifiers().contains(Modifier.FINAL);
    }
    
    /**
     * Returns whether the represented {@link #getElement() element} is static.
     * (Constructors and top-level types cannot be static.)
     */
    @Pure
    public default boolean isStatic() {
        return getModifiers().contains(Modifier.STATIC);
    }
    
    /**
     * Returns whether the represented {@link #getElement() element} is abstract.
     * (Fields cannot be abstract.)
     */
    @Pure
    public default boolean isAbstract() {
        return getModifiers().contains(Modifier.ABSTRACT);
    }
    
    /* -------------------------------------------------- Annotations -------------------------------------------------- */
    
    /**
     * Returns the annotations on the represented {@link #getElement() element}.
     */
    @Pure
    public @Nonnull FiniteIterable<@Nonnull AnnotationMirror> getAnnotations();
    
    /**
     * Returns whether the represented {@link #getElement() element} has the annotation with the given canonical name.
     */
    @Pure
    public boolean hasAnnotation(@Nonnull String canonicalName);
    
    /**
     * Returns whether the represented {@link #getElement() element} has the given annotation.
     */
    @Pure
    public boolean hasAnnotation(@Nonnull Class<? extends Annotation> annotationType);
    
    /**
     * Returns the annotation with the given type of the represented {@link #getElement() element} or null.
     */
    @Pure
    public default <A extends Annotation> @Nullable A getAnnotationOrNull(@Nonnull Class<A> annotationType) {
        return getElement().getAnnotation(annotationType);
    }
    
    /**
     * Returns the annotation with the given type of the represented {@link #getElement() element}.
     * 
     * @require hasAnnotation(annotationType) : "The element has to be annotated with the given annotation type.";
     */
    @Pure
    public default <A extends Annotation> @Nonnull A getAnnotation(@Nonnull Class<A> annotationType) {
        Require.that(hasAnnotation(annotationType)).orThrow("The element $ is not annotated with $.", getElement(), annotationType);
        
        return getElement().getAnnotation(annotationType);
    }
    
}
