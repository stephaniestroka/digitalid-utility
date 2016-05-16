package net.digitalid.utility.generator.information;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.state.Unmodifiable;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.information.field.FieldInformationImplementation;
import net.digitalid.utility.generator.information.method.ExecutableInformation;
import net.digitalid.utility.generator.information.type.TypeInformation;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processing.utility.StaticProcessingEnvironment;
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
    
    private final @Unmodifiable @Nonnull Set<@Nonnull Modifier> modifiers;
    
    @Pure
    @Override
    public @Unmodifiable @Nonnull Set<@Nonnull Modifier> getModifiers() {
        return modifiers;
    }
    
    /* -------------------------------------------------- Annotations -------------------------------------------------- */
    
    private final @Unmodifiable @Nonnull Map<@Nonnull String, @Nonnull AnnotationMirror> annotations;
    
    @Pure
    @Override
    public @Unmodifiable @Nonnull Collection<@Nonnull ? extends AnnotationMirror> getAnnotations() {
        return annotations.values();
    }
    
    @Pure
    @Override
    public boolean hasAnnotation(@Nonnull Class<? extends Annotation> annotationType) {
        return annotations.containsKey(annotationType.getCanonicalName());
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ElementInformationImplementation(@Nonnull Element element, @Nonnull TypeMirror type, @Nonnull DeclaredType containingType) {
        this.element = element;
        this.type = type;
        this.containingType = containingType;
        this.modifiers = Collections.unmodifiableSet(element.getModifiers());
        this.annotations = Collections.unmodifiableMap(FiniteIterable.of(StaticProcessingEnvironment.getElementUtils().getAllAnnotationMirrors(element)).toMap(annotationMirror -> ProcessingUtility.getQualifiedName(annotationMirror)));
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return element.getKind().toString().toLowerCase() + " " + getName();
    }
    
}
