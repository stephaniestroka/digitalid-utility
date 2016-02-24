package net.digitalid.utility.generator.information;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.generator.BuilderGenerator;
import net.digitalid.utility.generator.SubclassGenerator;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.validator.AnnotationValidator;

/**
 * This class collects the relevant information about an element for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 * 
 * @see ElementInformationImplementation
 * @see NonFieldInformation
 * @see NonTypeInformation
 */
public interface ElementInformation {
    
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
    public @Nonnull String getName();
    
    /* -------------------------------------------------- Type -------------------------------------------------- */
    
    /**
     * Returns the type of the represented {@link #getElement() element}.
     */
    @Pure
    public @Nonnull TypeMirror getType();
    
    /* -------------------------------------------------- Package -------------------------------------------------- */
    
    /**
     * Returns the package in which the represented {@link #getElement() element} is declared.
     */
    @Pure
    public @Nonnull PackageElement getPackageElement();
    
    /**
     * Returns the name of the {@link #getPackageElement() package} in which the represented {@link #getElement() element} is declared.
     */
    @Pure
    public @Nonnull String getPackageName();
    
    /**
     * Returns whether the {@link #getElement() element} is declared in the Java Runtime Environment (JRE).
     */
    @Pure
    public boolean isDeclaredInRuntimeEnvironment();
    
    /**
     * Returns whether the {@link #getElement() element} is declared in the Digital ID library (DID SDK).
     */
    @Pure
    public boolean isDeclaredInDigitalIDLibrary();
    
    /* -------------------------------------------------- Modifiers -------------------------------------------------- */
    
    /**
     * Returns the modifiers of the represented {@link #getElement() element}.
     */
    @Pure
    public @Nonnull @NonNullableElements Set<Modifier> getModifiers();
    
    /**
     * Returns whether the represented {@link #getElement() element} is public.
     */
    @Pure
    public boolean isPublic();
    
    /**
     * Returns whether the represented {@link #getElement() element} is protected.
     */
    @Pure
    public boolean isProtected();
    
    /**
     * Returns whether the represented {@link #getElement() element} is private.
     */
    @Pure
    public boolean isPrivate();
    
    /**
     * Returns whether the represented {@link #getElement() element} is final.
     */
    @Pure
    public boolean isFinal();
    
    /* -------------------------------------------------- Validators -------------------------------------------------- */
    
    /**
     * Returns the validators declared on the represented {@link #getElement() element}.
     */
    @Pure
    public @Nonnull @NonNullableElements Map<AnnotationMirror, AnnotationValidator> getValidators();
    
    /* -------------------------------------------------- Annotations -------------------------------------------------- */
    
    /**
     * Returns the annotations on the represented {@link #getElement() element}.
     */
    @Pure
    public @Nonnull @NonNullableElements Collection<? extends AnnotationMirror> getAnnotations();
    
    /**
     * Returns whether the represented {@link #getElement() element} has the given annotation.
     */
    @Pure
    public boolean hasAnnotation(@Nonnull Class<? extends Annotation> annotationType);
    
}
