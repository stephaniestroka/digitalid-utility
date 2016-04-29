package net.digitalid.utility.conversion.converter;

import java.lang.annotation.Annotation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.immutable.ImmutableList;
import net.digitalid.utility.tuples.Pair;

/**
 * This class represents a field of a type.
 */
public class CustomField {
    
    /* -------------------------------------------------- Type -------------------------------------------------- */
    
    private final @Nonnull Pair<@Nonnull CustomType, @Nullable Converter<?>> type;
    
    @Pure
    public @Nonnull Pair<@Nonnull CustomType, @Nullable Converter<?>> getType() {
        return type;
    }
    
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
    
    private CustomField(@Nonnull Pair<@Nonnull CustomType, @Nullable Converter<?>> type, @Nonnull String name, @Nonnull ImmutableList<@Nonnull Annotation> annotations) {
        this.type = type;
        this.name = name;
        this.annotations = annotations;
    }
    
    public static @Nonnull CustomField with(@Nonnull Pair<@Nonnull CustomType, @Nullable Converter<?>> type, @Nonnull String name, @Nonnull ImmutableList<@Nonnull Annotation> annotations) {
        return new CustomField(type, name, annotations);
    }
    
}
