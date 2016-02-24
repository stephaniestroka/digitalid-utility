package net.digitalid.utility.generator.information.field;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * Description.
 */
public class GeneratedFieldInformation extends NonDirectlyAccessibleFieldInformation {
    
    /* -------------------------------------------------- Setter -------------------------------------------------- */
    
    private final @Nullable MethodInformation setter;
    
    /**
     * Returns whether this field has a setter.
     */
    @Pure
    public boolean hasSetter() {
        return setter != null;
    }
    
    @Pure
    @SuppressWarnings("null")
    public @Nonnull MethodInformation getSetter() {
        Require.that(hasSetter()).orThrow("The setter may not be null.");
        
        return setter;
    }
    
    /* -------------------------------------------------- Mutability -------------------------------------------------- */
    
    @Pure
    public boolean isMutable() {
        return hasSetter();
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected GeneratedFieldInformation() {
        
    }
    
}
