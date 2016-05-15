package net.digitalid.utility.conversion.converter;

import java.lang.annotation.Annotation;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.immutable.ImmutableList;

/**
 * This class represents a field of a type.
 */
public class CustomField {
    
    /* -------------------------------------------------- Type -------------------------------------------------- */
    
    public final @Nonnull LeafConverter<?> converter;
    
    /* -------------------------------------------------- Name -------------------------------------------------- */ 
    
    private final @Nonnull String name; @Pure
    
    public @Nonnull String getName() {
        return name;
    }
    
    /* -------------------------------------------------- Annotations -------------------------------------------------- */
    
    private final @Nonnull ImmutableList<@Nonnull Annotation> annotations;
    
    @Pure
    public @Nonnull ImmutableList<@Nonnull Annotation> getAnnotations() {
        return annotations;
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    private CustomField(@Nonnull LeafConverter<?> converter, @Nonnull String name, @Nonnull ImmutableList<@Nonnull Annotation> annotations) {
        this.converter = converter;
        this.name = name;
        this.annotations = annotations;
    }
    
    public static @Nonnull CustomField with(@Nonnull LeafConverter<?> converter, @Nonnull String name, @Nonnull ImmutableList<@Nonnull Annotation> annotations) {
        return new CustomField(converter, name, annotations);
    }
    
}
