package net.digitalid.utility.generator.information;

import javax.annotation.Nonnull;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * Description.
 */
public abstract class NonTypeInformation extends ElementInformationImplementation {
    
    /* -------------------------------------------------- Declared Type -------------------------------------------------- */
    
    private @Nonnull DeclaredType containingType;
    
    /**
     * Returns the type that contains the (potentially inherited) {@link #getElement() element}.
     */
    @Pure
    public @Nonnull DeclaredType getContainingType() {
        return containingType;
    }
    
    /* -------------------------------------------------- Modifiers -------------------------------------------------- */

    /**
     * Returns whether the represented {@link #getElement() element} is static.
     */
    @Pure
    public boolean isStatic() {
        return getModifiers().contains(Modifier.STATIC);
    }
    
    // TODO: Introduce a 'boolean isGenerated()' here?
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected NonTypeInformation() {
        // TODO
    }
    
}
