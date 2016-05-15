package net.digitalid.utility.generator.information.field;

import javax.annotation.Nonnull;
import javax.lang.model.element.VariableElement;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.generators.BuilderGenerator;
import net.digitalid.utility.generator.generators.SubclassGenerator;

/**
 * This type collects the relevant information about a parameter-based field for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
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
