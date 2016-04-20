package net.digitalid.utility.generator.information.field;

import java.lang.annotation.Annotation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.generator.BuilderGenerator;
import net.digitalid.utility.generator.SubclassGenerator;
import net.digitalid.utility.generator.information.ElementInformation;
import net.digitalid.utility.annotations.method.Pure;

/**
 * This type collects the relevant information about a field for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 * 
 * @see PotentiallyInheritedFieldInformation
 * @see RepresentingFieldInformation
 */
public interface FieldInformation extends ElementInformation {
    
    /* -------------------------------------------------- Default Value -------------------------------------------------- */
    
    /**
     * Returns whether this field has a default value.
     */
    @Pure
    public boolean hasDefaultValue();
    
    /**
     * Returns the default value of this field.
     * 
     * @require hasDefaultValue() : "This field has to have a default value.";
     */
    @Pure
    public @Nonnull String getDefaultValue();
    
    /* -------------------------------------------------- Access -------------------------------------------------- */
    
    /**
     * Returns the code to retrieve the value of this field.
     */
    @Pure
    public @Nonnull String getAccessCode();
    
    /* -------------------------------------------------- Mutability -------------------------------------------------- */
    
    /**
     * Returns whether this field is mutable.
     */
    @Pure
    public boolean isMutable();
    
}
