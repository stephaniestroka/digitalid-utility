package net.digitalid.utility.generator.information.variable;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.information.ElementInformation;

/**
 *
 */
public interface VariableElementInformation extends ElementInformation {
    
    /**
     * Returns the default value of this field, which is either declared with the @Default annotation, 'false' if it is a boolean, '0' if it is a numeric primitive or 'null' if it is another type.
     */
    @Pure
    public @Nonnull String getDefaultValue();
    
    @Pure
    public boolean hasDefaultValue();
    
    @Pure
    public boolean isMandatory();
    
}
