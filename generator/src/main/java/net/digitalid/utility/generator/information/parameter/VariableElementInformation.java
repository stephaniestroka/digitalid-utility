package net.digitalid.utility.generator.information.parameter;

import javax.annotation.Nonnull;
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.generator.information.ElementInformationImplementation;

/**
 *
 */
public class VariableElementInformation extends ElementInformationImplementation {
    
    protected VariableElementInformation(@Nonnull Element element, @Nonnull TypeMirror type, @Nonnull DeclaredType containingType) {
        super(element, type, containingType);
    }
    
    public static @Nonnull VariableElementInformation of(@Nonnull Element element, @Nonnull DeclaredType containingType) {
        return new VariableElementInformation(element, element.asType(), containingType);
    }
    
}
