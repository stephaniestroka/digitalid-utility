package net.digitalid.utility.generator.information.field;

import javax.annotation.Nonnull;
import javax.lang.model.element.VariableElement;

import net.digitalid.utility.generator.information.ElementInformationImplementation;
import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * Description.
 */
public class DirectlyAccessibleFieldInformation extends ElementInformationImplementation implements FieldInformation {
    
    /* -------------------------------------------------- Field -------------------------------------------------- */
    
    private final @Nonnull VariableElement field;
    
    /**
     * Returns the field through which 
     */
    @Pure
    public @Nonnull VariableElement getField() {
        return field;
    }
    
    @Pure
    @Override
    public @Nonnull String getAccessCode() {
        return "this." + field.getSimpleName();
    }
    
    /* -------------------------------------------------- Mutability -------------------------------------------------- */
    
    @Pure
    public boolean isMutable() {
        return !isFinal();
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected DirectlyAccessibleFieldInformation() {
        
    }
    
}
