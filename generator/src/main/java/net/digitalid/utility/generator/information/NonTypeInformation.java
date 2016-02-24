package net.digitalid.utility.generator.information;

import javax.annotation.Nonnull;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.generator.information.field.FieldInformation;
import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * This class collects the relevant information about a non-type for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 * 
 * @see NonTypeInformationImplementation
 * @see FieldInformation
 */
public interface NonTypeInformation extends ElementInformation {
    
    /* -------------------------------------------------- Containing Type -------------------------------------------------- */
    
    /**
     * Returns the type that contains the (potentially inherited) {@link #getElement() element}.
     */
    @Pure
    public @Nonnull DeclaredType getContainingType();
    
    /* -------------------------------------------------- Modifiers -------------------------------------------------- */

    /**
     * Returns whether the represented {@link #getElement() element} is static.
     */
    @Pure
    public boolean isStatic();
    
}
