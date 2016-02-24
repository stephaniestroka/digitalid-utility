package net.digitalid.utility.generator.information;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.generator.information.type.TypeInformation;
import net.digitalid.utility.logging.processing.AnnotationProcessing;
import net.digitalid.utility.processor.ProcessingUtility;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.validator.AnnotationValidator;

/**
 * This class implements the {@link ElementInformation} interface.
 * 
 * @see NonTypeInformationImplementation
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
    
    private final @Nonnull @NonNullableElements Set<Modifier> modifiers;
    
    @Pure
    @Override
    public @Nonnull @NonNullableElements Set<Modifier> getModifiers() {
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
    
    /* -------------------------------------------------- Validators -------------------------------------------------- */
    
    private final @Nonnull @NonNullableElements Map<AnnotationMirror, AnnotationValidator> validators;
    
    @Pure
    @Override
    public @Nonnull @NonNullableElements Map<AnnotationMirror, AnnotationValidator> getValidators() {
        return validators;
    }
    
    /* -------------------------------------------------- Annotations -------------------------------------------------- */
    
    private final @Nonnull @NonNullableElements Map<String, AnnotationMirror> annotations;
    
    @Pure
    @Override
    public @Nonnull @NonNullableElements Collection<AnnotationMirror> getAnnotations() {
        return annotations.values();
    }
    
    @Pure
    @Override
    public boolean hasAnnotation(@Nonnull Class<? extends Annotation> annotationType) {
        return annotations.containsKey(annotationType.getCanonicalName());
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ElementInformationImplementation(@Nonnull Element element, @Nonnull TypeMirror type) {
        this.element = element;
        this.name = element.getSimpleName().toString();
        this.type = type;
        this.packageElement = AnnotationProcessing.getElementUtils().getPackageOf(element);
        this.packageName = packageElement.getQualifiedName().toString();
        this.modifiers = Collections.unmodifiableSet(element.getModifiers());
        this.validators = ProcessingUtility.getAnnotationValidators(element);
        
        final @Nonnull @NonNullableElements Map<String, AnnotationMirror> annotations = new LinkedHashMap<>();
        for (@Nonnull AnnotationMirror annotationMirror : AnnotationProcessing.getElementUtils().getAllAnnotationMirrors(element)) {
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
