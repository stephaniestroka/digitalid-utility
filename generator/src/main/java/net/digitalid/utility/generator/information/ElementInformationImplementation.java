package net.digitalid.utility.generator.information;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.annotations.state.Unmodifiable;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.generator.information.field.FieldInformationImplementation;
import net.digitalid.utility.generator.information.method.ExecutableInformation;
import net.digitalid.utility.generator.information.type.TypeInformation;
import net.digitalid.utility.processing.utility.StaticProcessingEnvironment;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.processing.utility.ProcessingUtility;

/**
 * This class implements the {@link ElementInformation} interface.
 * 
 * @see FieldInformationImplementation
 * @see ExecutableInformation
 * @see TypeInformation
 */
public abstract class ElementInformationImplementation implements ElementInformation {
    
    /* -------------------------------------------------- Element -------------------------------------------------- */
    
    private final @Nonnull Element element;
    
    @Pure
    @Override
    public @Nonnull Element getElement() {
        return element;
    }
    
    private final @Nonnull String name;
    
    @Pure
    @Override
    public @Nonnull String getName() {
        return name;
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
    
    /* -------------------------------------------------- Package -------------------------------------------------- */
    
    private final @Nonnull PackageElement packageElement;
    
    @Pure
    @Override
    public @Nonnull PackageElement getPackageElement() {
        return packageElement;
    }
    
    public final @Nonnull String packageName;
    
    @Pure
    @Override
    public @Nonnull String getPackageName() {
        return packageName;
    }
    
    @Pure
    @Override
    public boolean isDeclaredInRuntimeEnvironment() {
        // Matches java. and javax. packages.
        return packageName.startsWith("java");
    }
    
    @Pure
    @Override
    public boolean isDeclaredInDigitalIDLibrary() {
        return packageName.startsWith("net.digitalid.");
    }
    
    /* -------------------------------------------------- Modifiers -------------------------------------------------- */
    
    private final @Unmodifiable @Nonnull @NonNullableElements Set<Modifier> modifiers;
    
    @Pure
    @Override
    public @Unmodifiable @Nonnull @NonNullableElements Set<Modifier> getModifiers() {
        return modifiers;
    }
    
    @Pure
    @Override
    public boolean isPublic() {
        return getModifiers().contains(Modifier.PUBLIC);
    }
    
    @Pure
    @Override
    public boolean isProtected() {
        return getModifiers().contains(Modifier.PROTECTED);
    }
    
    @Pure
    @Override
    public boolean isPrivate() {
        return getModifiers().contains(Modifier.PRIVATE);
    }
    
    @Pure
    @Override
    public boolean isFinal() {
        return getModifiers().contains(Modifier.FINAL);
    }
    
    @Pure
    @Override
    public boolean isStatic() {
        return getModifiers().contains(Modifier.STATIC);
    }
    
    @Pure
    @Override
    public boolean isAbstract() {
        return getModifiers().contains(Modifier.ABSTRACT);
    }
    
    /* -------------------------------------------------- Contract Generators -------------------------------------------------- */
    
//    private final @Nonnull @NonNullableElements Map<AnnotationMirror, ContractGenerator> contractGenerators;
//    
//    @Pure
//    @Override
//    public @Nonnull @NonNullableElements Map<AnnotationMirror, ContractGenerator> getContractGenerators() {
//        return contractGenerators;
//    }
    
    /* -------------------------------------------------- Annotations -------------------------------------------------- */
    
    private final @Unmodifiable @Nonnull @NonNullableElements Map<String, AnnotationMirror> annotations;
    
    @Pure
    @Override
    public @Unmodifiable @Nonnull @NonNullableElements Collection<AnnotationMirror> getAnnotations() {
        return annotations.values();
    }
    
    @Pure
    @Override
    public boolean hasAnnotation(@Nonnull Class<? extends Annotation> annotationType) {
        return annotations.containsKey(annotationType.getCanonicalName());
    }
    
    /**
     * Returns an annotation of a given type of the field, or null, if no such annotation was found.
     */
    @Pure
    @Override
    public @Nonnull <A extends Annotation> A getAnnotation(@Nonnull Class<A> annotationType) {
        Require.that(hasAnnotation(annotationType)).orThrow("$ does not have an annotation named $", element, annotationType);
        return element.getAnnotation(annotationType);
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ElementInformationImplementation(@Nonnull Element element, @Nonnull TypeMirror type, @Nonnull DeclaredType containingType) {
        this.element = element;
        this.name = element.getSimpleName().toString();
        this.type = type;
        this.containingType = containingType;
        this.packageElement = StaticProcessingEnvironment.getElementUtils().getPackageOf(element);
        this.packageName = packageElement.getQualifiedName().toString();
        this.modifiers = Collections.unmodifiableSet(element.getModifiers());
//        this.validators = ProcessingUtility.getAnnotationHandlers(element);
        
        final @Nonnull @NonNullableElements Map<String, AnnotationMirror> annotations = new LinkedHashMap<>();
        for (@Nonnull AnnotationMirror annotationMirror : StaticProcessingEnvironment.getElementUtils().getAllAnnotationMirrors(element)) {
            annotations.put(ProcessingUtility.getQualifiedName(annotationMirror), annotationMirror);
        }
        this.annotations = Collections.unmodifiableMap(annotations);
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return element.getKind().toString().toLowerCase() + " " + name;
    }
    
}
