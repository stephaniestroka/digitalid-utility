package net.digitalid.utility.generator.information;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.generator.BuilderGenerator;
import net.digitalid.utility.generator.SubclassGenerator;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.state.Unmodifiable;

/**
 * This type collects the relevant information about an element for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 * 
 * @see ElementInformationImplementation
 * @see net.digitalid.utility.generator.information.field.FieldInformation
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
    
    /* -------------------------------------------------- Containing Type -------------------------------------------------- */
    
    /**
     * Returns the type that contains the (potentially inherited) {@link #getElement() element}.
     * (In case of top-level types, this method returns the {@link #getType() type} itself.)
     */
    @Pure
    public @Nonnull DeclaredType getContainingType();
    
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
    public @Unmodifiable @Nonnull @NonNullableElements Set<Modifier> getModifiers();
    
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
    
    /**
     * Returns whether the represented {@link #getElement() element} is static.
     * (Constructors and top-level types cannot be static.)
     */
    @Pure
    public boolean isStatic();
    
    /**
     * Returns whether the represented {@link #getElement() element} is abstract.
     * (Fields cannot be abstract.)
     */
    @Pure
    public boolean isAbstract();
    
    /* -------------------------------------------------- Contract Generators -------------------------------------------------- */
    
    /**
     * TODO: remove!
     * Returns the contract generators declared on the represented {@link #getElement() element}.
    @Pure
    public @Nonnull @NonNullableElements Map<AnnotationMirror, ContractGenerator> getContractGenerators();
     */
    
    /* -------------------------------------------------- Annotations -------------------------------------------------- */
    
    /**
     * Returns the annotations on the represented {@link #getElement() element}.
     */
    @Pure
    public @Unmodifiable @Nonnull @NonNullableElements Collection<? extends AnnotationMirror> getAnnotations();
    
    /**
     * Returns whether the represented {@link #getElement() element} has the given annotation.
     */
    @Pure
    public boolean hasAnnotation(@Nonnull Class<? extends Annotation> annotationType);
    
    // TODO: Implement method to retrieve all annotations including their values as a string. (Determine if there is already somewhere a utility method for that or implement one in ProcessingUtility otherwise.)
    
}
