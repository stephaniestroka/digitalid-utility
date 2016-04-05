package net.digitalid.collaboration.enumerations;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class enumerates the authors of the Digital ID library.
 */
@Immutable
public enum Author {
    
    ANYONE("Anyone", "info@digitalid.net"),
    
    KASPAR_ETTER("Kaspar Etter", "kaspar.etter@digitalid.net"),
    
    STEPHANIE_STROKA("Stephanie Stroka", "stephanie.stroka@digitalid.net");
    
    /* -------------------------------------------------- Name -------------------------------------------------- */
    
    private final @Nonnull String name;
    
    /**
     * Returns the name of this author.
     */
    // @Pure
    public @Nonnull String getName() {
        return name;
    }
    
    /* -------------------------------------------------- Email -------------------------------------------------- */
    
    private final @Nonnull String email;
    
    /**
     * Returns the email address of this author.
     */
    // @Pure
    public @Nonnull String getEmail() {
        return email;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    private Author(@Nonnull String name, @Nonnull String email) {
        this.name = name;
        this.email = email;
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    // @Pure
    @Override
    public @Nonnull String toString() {
        return getName();
    }
    
}
