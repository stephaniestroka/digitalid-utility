package net.digitalid.utility.generator.information.field;

import javax.annotation.Nonnull;
import javax.lang.model.element.VariableElement;

import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This type collects the relevant information about a parameter-based field for generating a {@link net.digitalid.utility.generator.SubclassGenerator subclass} and {@link net.digitalid.utility.generator.BuilderGenerator builder}.
 */
public interface ParameterBasedFieldInformation extends RepresentingFieldInformation {
    
    /* -------------------------------------------------- Parameter -------------------------------------------------- */
    
    /**
     * Returns the parameter that is represented by this field.
     * 
     * @ensure result.getKind() == ElementKind.PARAMETER : "The returned element is a parameter.";
     */
    @Pure
    public @Nonnull VariableElement getParameter();
    
}
