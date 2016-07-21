package net.digitalid.utility.conversion.converter;

import java.lang.annotation.Annotation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.immutable.ImmutableMap;

/**
 * This class stores information about the field annotations parsed during compile-time. Type-use annotations and field annotations are treated equally.
 */
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
    
    public static @Nonnull CustomAnnotation with(@Nonnull Class<? extends Annotation> annotationType, @Nonnull ImmutableMap<@Nonnull String, @Nullable Object> annotationFields) {
        return new CustomAnnotation(annotationType, annotationFields);
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
