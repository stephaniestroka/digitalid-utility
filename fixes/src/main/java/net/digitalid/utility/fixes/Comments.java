package net.digitalid.utility.fixes;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class enumerates various comments.
 */
@Immutable
public enum Comments implements Fixes {
    
    /* -------------------------------------------------- Enumerations -------------------------------------------------- */
    
    /**
     * The Java comment prefix '/* ' and suffix ' *\/'.
     */
    JAVA("/* ", " */"),
    
    /**
     * The XML comment prefix '<!-- ' and suffix ' -->'.
     */
    XML("<!-- ", " -->");
    
    /* -------------------------------------------------- Prefix -------------------------------------------------- */
    
    private final @Nonnull String prefix;
    
    @Pure
    @Override
    public @Nonnull String getPrefix() {
        return prefix;
    }
    
    /* -------------------------------------------------- Suffix -------------------------------------------------- */
    
    private final @Nonnull String suffix;
    
    @Pure
    @Override
    public @Nonnull String getSuffix() {
        return suffix;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    private Comments(@Nonnull String prefix, @Nonnull String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }
    
    /* -------------------------------------------------- Formatting -------------------------------------------------- */
    
    /**
     * Returns the given object surrounded by the given comments.
     */
    @Pure
    public static @Nonnull String in(@Nullable Comments comments, @NonCaptured @Unmodified @Nullable Object object) {
        if (comments == null) { return String.valueOf(object); }
        else { return comments.getPrefix() + String.valueOf(object) + comments.getSuffix(); }
    }
    
    /**
     * Returns the given object in Java comments.
     */
    @Pure
    public static @Nonnull String inJava(@Nullable Object object) {
        return in(Comments.JAVA, object);
    }
    
    /**
     * Returns the given object in XML comments.
     */
    @Pure
    public static @Nonnull String inXML(@Nullable Object object) {
        return in(Comments.XML, object);
    }
    
}
