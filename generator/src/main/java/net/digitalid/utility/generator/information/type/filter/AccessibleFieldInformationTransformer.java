package net.digitalid.utility.generator.information.type.filter;

import javax.annotation.Nonnull;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.generator.information.field.DeclaredFieldInformation;
import net.digitalid.utility.generator.information.filter.Transformer;

/**
 *
 */
public class AccessibleFieldInformationTransformer implements Transformer<Element, DeclaredFieldInformation> {
    
    private final @Nonnull DeclaredType containingType;
    
    AccessibleFieldInformationTransformer(@Nonnull DeclaredType containingType) {
        this.containingType = containingType;
    }
    
    public static @Nonnull AccessibleFieldInformationTransformer of(@Nonnull DeclaredType containingType) {
        return new AccessibleFieldInformationTransformer(containingType);
    }
    
    @Override
    public @Nonnull DeclaredFieldInformation transformNonNullable(Element from) {
        return DeclaredFieldInformation.of((VariableElement) from, containingType);
    }

    private boolean isAccessibleField(@Nonnull VariableElement fieldElement) {
        return !fieldElement.getModifiers().contains(Modifier.PRIVATE);
    }
    
    @Override 
    public boolean canTransform(Element from) {
        return isAccessibleField((VariableElement) from);
    }
    
}
