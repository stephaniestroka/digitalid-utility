package net.digitalid.utility.generator.information.field;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.logging.processing.SourcePosition;
import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * This type collects the relevant information about a directly accessible parameter-based field for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 */
public class NonDirectlyAccessibleParameterBasedFieldInformation extends NonDirectlyAccessibleFieldInformation implements ParameterBasedFieldInformation {
    
    /* -------------------------------------------------- Parameter -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull VariableElement getParameter() {
        return (VariableElement) getElement();
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected NonDirectlyAccessibleParameterBasedFieldInformation(@Nonnull VariableElement parameter, @Nonnull DeclaredType containingType, @Nonnull MethodInformation getter, @Nullable MethodInformation setter) {
        super(parameter, parameter.asType(), containingType, getter, setter);
        
        Require.that(parameter.getKind() == ElementKind.PARAMETER).orThrow("The element $ has to be a parameter.", SourcePosition.of(parameter));
    }
    
    /**
     * Returns the field information of the given parameter, containing type, getter and setter which can be used to access the value.
     * 
     * @require parameter.getKind() == ElementKind.PARAMETER : "The element has to be a parameter.";
     * @require getter.isGetter() : "The first method has to be a getter.";
     * @require setter == null || setter.isSetter() : "The second method has to be null or a setter.";
     */
    @Pure
    public static @Nonnull NonDirectlyAccessibleParameterBasedFieldInformation of(@Nonnull VariableElement parameter, @Nonnull DeclaredType containingType, @Nonnull MethodInformation getter, @Nullable MethodInformation setter) {
        return new NonDirectlyAccessibleParameterBasedFieldInformation(parameter, containingType, getter, setter);
    }
    
}
