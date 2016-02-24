package net.digitalid.utility.generator.information.field;

import javax.annotation.Nonnull;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.logging.processing.SourcePosition;
import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * This type collects the relevant information about a directly accessible parameter-based field for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 */
public class DirectlyAccessibleParameterBasedFieldInformation extends DirectlyAccessibleFieldInformation implements ParameterBasedFieldInformation {
    
    /* -------------------------------------------------- Parameter -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull VariableElement getParameter() {
        return (VariableElement) getElement();
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected DirectlyAccessibleParameterBasedFieldInformation(@Nonnull VariableElement parameter, @Nonnull DeclaredType containingType, @Nonnull VariableElement field) {
        super(parameter, parameter.asType(), containingType, field);
        
        Require.that(parameter.getKind() == ElementKind.PARAMETER).orThrow("The element $ has to be a parameter.", SourcePosition.of(parameter));
    }
    
    /**
     * Returns the field information of the given parameter, containing type and field which can be used to access the value.
     * 
     * @require parameter.getKind() == ElementKind.PARAMETER : "The first element has to be a parameter.";
     * @require field.getKind() == ElementKind.FIELD : "The second element has to be a field.";
     */
    @Pure
    public static @Nonnull DirectlyAccessibleParameterBasedFieldInformation of(@Nonnull VariableElement parameter, @Nonnull DeclaredType containingType, @Nonnull VariableElement field) {
        return new DirectlyAccessibleParameterBasedFieldInformation(parameter, containingType, field);
    }
    
}
