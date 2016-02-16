package net.digitalid.utility.processor;

import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;

import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.logging.processing.AnnotationProcessing;
import net.digitalid.utility.logging.processing.SourcePosition;
import net.digitalid.utility.string.iterable.NonNullableElementConverter;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.reference.Capturable;
import net.digitalid.utility.validation.annotations.type.Utiliy;

/**
 * This class provides useful methods for annotation processing.
 */
@Utiliy
public class ProcessingUtility {
    
    /* -------------------------------------------------- Default Constructor -------------------------------------------------- */
    
    /**
     * Returns whether the given element is a class with a public default constructor.
     */
    @Pure
    public static boolean hasPublicDefaultConstructor(@Nonnull Element element) {
        for (@Nonnull ExecutableElement constructor : ElementFilter.constructorsIn(element.getEnclosedElements())) {
            AnnotationLog.verbose("Found the constructor", SourcePosition.of(constructor));
            if (constructor.getParameters().isEmpty() && constructor.getModifiers().contains(Modifier.PUBLIC)) { return true; }
        }
        AnnotationLog.debugging("Found no public default constructor in", SourcePosition.of(element));
        return false;
    }
    
    /* -------------------------------------------------- Annotation Mirror -------------------------------------------------- */
    
    /**
     * Returns the annotation mirror corresponding to the given annotation type of the given element or null if not found.
     */
    @Pure
    public static @Nullable AnnotationMirror getAnnotationMirror(@Nonnull Element element, @Nonnull Class<? extends Annotation> annotation) {
        for (@Nonnull AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            final @Nonnull TypeElement annotationElement = (TypeElement) annotationMirror.getAnnotationType().asElement();
            AnnotationLog.verbose("Found the annotation '@" + annotationElement.getSimpleName() + "' on", SourcePosition.of(element));
            if (annotationElement.getQualifiedName().contentEquals(annotation.getCanonicalName())) { return annotationMirror; }
        }
        return null;
    }
    
    /* -------------------------------------------------- Fields of Type -------------------------------------------------- */
    
    /**
     * Returns a list of all the fields with the given type in the given class.
     */
    @Pure
    public static @Capturable @Nonnull List<VariableElement> getFieldsOfType(@Nonnull TypeElement classElement, @Nonnull Class<?> fieldType) {
        final @Nonnull List<VariableElement> fields = new LinkedList<>();
        for (@Nonnull VariableElement field : ElementFilter.fieldsIn(classElement.getEnclosedElements())) {
            final @Nonnull String fieldTypeName = AnnotationProcessing.getTypeUtils().erasure(field.asType()).toString();
            AnnotationLog.verbose("Found with the type '" + fieldTypeName + "' the field", SourcePosition.of(field));
            if (fieldTypeName.equals(fieldType.getCanonicalName())) { fields.add(field); }
        }
        return fields;
    }
    
    /**
     * Returns the unique, public and static field with the given type in the given class.
     * If no field fulfilling all these criteria is found, this method logs a warning message and returns null. 
     */
    @Pure
    public static @Nullable VariableElement getUniquePublicStaticFieldOfType(@Nonnull TypeElement classElement, @Nonnull Class<?> fieldType) {
        final @Nonnull List<VariableElement> fields = getFieldsOfType(classElement, fieldType);
        if (fields.size() != 1) {
            AnnotationLog.warning("There is not exactly one field of type '" + fieldType.getCanonicalName()+ "' in the class", SourcePosition.of(classElement));
        } else {
            final @Nonnull VariableElement field = fields.get(0);
            if (!fields.get(0).getModifiers().contains(Modifier.PUBLIC)) {
                AnnotationLog.warning("The field of type '" + fieldType.getCanonicalName()+ "' has to be public:", SourcePosition.of(field));
            } else if (!fields.get(0).getModifiers().contains(Modifier.STATIC)) {
                AnnotationLog.warning("The field of type '" + fieldType.getCanonicalName()+ "' has to be static:", SourcePosition.of(field));
            } else {
                return field;
            }
        }
        return null;
    }
    
    /* -------------------------------------------------- Converters -------------------------------------------------- */
    
    public static final @Nonnull NonNullableElementConverter<VariableElement> DECLARATION_CONVERTER = new NonNullableElementConverter<VariableElement>() {
        @Override
        public String toString(@Nonnull VariableElement element) {
            return element.asType() + " " + element.getSimpleName();
        }
    };
    
    public static final @Nonnull NonNullableElementConverter<VariableElement> CALL_CONVERTER = new NonNullableElementConverter<VariableElement>() {
        @Override
        public String toString(@Nonnull VariableElement element) {
            return element.getSimpleName().toString();
        }
    };
    
    public static final @Nonnull NonNullableElementConverter<TypeParameterElement> TYPE_CONVERTER = new NonNullableElementConverter<TypeParameterElement>() {
        @Override
        public String toString(@Nonnull TypeParameterElement element) {
            AnnotationLog.debugging("element.toString(): " + element.toString());
            AnnotationLog.debugging("element.asType(): " + element.asType());
            AnnotationLog.debugging("element.getSimpleName(): " + element.getSimpleName());
            final @Nonnull @NonNullableElements List<? extends TypeMirror> bounds = element.getBounds();
            for (@Nonnull TypeMirror bound : bounds) {
                AnnotationLog.debugging("bound.toString(): " + bound.toString());
            }
            return element.getSimpleName().toString() + (bounds.isEmpty() ? "" : " extends " + bounds.get(0));
        }
    };
    
}
