package net.digitalid.utility.generator.information;

import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.generator.information.type.TypeInformation;
import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * This class collects the relevant information about a non-field for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 * 
 * @see MethodInformation
 * @see TypeInformation
 */
public interface NonFieldInformation extends ElementInformation {
    
    /* -------------------------------------------------- Modifiers -------------------------------------------------- */
    
    /**
     * Returns whether the represented {@link #getElement() element} is abstract.
     */
    @Pure
    public boolean isAbstract();
    
}
