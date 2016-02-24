package net.digitalid.utility.generator.information;

import javax.annotation.Nonnull;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.generator.information.field.DirectlyAccessibleFieldInformation;
import net.digitalid.utility.generator.information.field.NonDirectlyAccessibleFieldInformation;
import net.digitalid.utility.generator.information.method.ExecutableInformation;
import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * This class implements the {@link NonTypeInformation} interface.
 * 
 * @see ExecutableInformation
 * @see DirectlyAccessibleFieldInformation
 * @see NonDirectlyAccessibleFieldInformation
 */
public abstract class NonTypeInformationImplementation extends ElementInformationImplementation implements NonTypeInformation {
    
    /* -------------------------------------------------- Containing Type -------------------------------------------------- */
    
    private final @Nonnull DeclaredType containingType;
    
    @Pure
    @Override
    public @Nonnull DeclaredType getContainingType() {
        return containingType;
    }
    
    /* -------------------------------------------------- Modifiers -------------------------------------------------- */

    @Pure
    @Override
    public boolean isStatic() {
        return getModifiers().contains(Modifier.STATIC);
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected NonTypeInformationImplementation(@Nonnull Element element, @Nonnull TypeMirror type, @Nonnull DeclaredType containingType) {
        super(element, type);
        
        this.containingType = containingType;
    }
    
}
