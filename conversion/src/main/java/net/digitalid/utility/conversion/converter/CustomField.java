package net.digitalid.utility.conversion.converter;

import java.lang.annotation.Annotation;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.conversion.converter.types.CustomType;
import net.digitalid.utility.exceptions.UnexpectedFailureException;
import net.digitalid.utility.immutable.ImmutableList;

/**
 * This class represents a field of a type. If is used to analyze the content of a class which is particularly interesting 
 * when an object of this class is converted to another form of representation.
 */
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
    private final @Nonnull ImmutableList<@Nonnull Annotation> annotations;
    
    /**
     * Returns an immutable list of annotations of the field.
     */
    @Pure
    public @Nonnull ImmutableList<@Nonnull Annotation> getAnnotations() {
        return annotations;
    }
    
    /**
     * Returns true iff the field is annotated with a given annotation type.
     */
    @Pure
    public <A extends Annotation> boolean isAnnotatedWith(@Nonnull Class<A> annotationType) {
        for (@Nonnull Annotation annotation : annotations) {
            if (annotationType.isInstance(annotation)) {
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
    public @Nonnull <A extends Annotation> A getAnnotation(@Nonnull Class<A> annotationType) {
        Require.that(isAnnotatedWith(annotationType)).orThrow("Field $ is not annotated with $", name, annotationType);
    
        for (@Nonnull Annotation annotation : annotations) {
            if (annotationType.isInstance(annotation)) {
                return (A) annotation;
            }
        }
        throw UnexpectedFailureException.with("Field $ is not annotated with $", name, annotationType);
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new instance of custom field for a given custom type, name and list of field annotations.
     */
    private CustomField(@Nonnull CustomType customType, @Nonnull String name, @Nonnull ImmutableList<@Nonnull Annotation> annotations) {
        this.customType = customType;
        this.name = name;
        this.annotations = annotations;
    }
    
    /**
     * Returns a new instance of custom field for a given custom type, name and list of field annotations.
     */
    public static @Nonnull CustomField with(@Nonnull CustomType customType, @Nonnull String name, @Nonnull ImmutableList<@Nonnull Annotation> annotations) {
        return new CustomField(customType, name, annotations);
    }
    
}
