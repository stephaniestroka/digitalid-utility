package net.digitalid.utility.generator.information.method;

import javax.annotation.Nonnull;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.generator.information.ElementInformationImplementation;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processing.utility.StaticProcessingEnvironment;

/**
 *
 */
public class MethodParameterInformation extends ElementInformationImplementation {
    
    protected MethodParameterInformation(@Nonnull Element element, @Nonnull DeclaredType containingType) {
        super(element, element.asType(), containingType);
        
        Require.that(element.getKind() == ElementKind.PARAMETER).orThrow("The element $ has to be a parameter.", SourcePosition.of(element));
    }
    
    @Override
    public @Nonnull String toString() {
        return getType()  + " " + getElement();
    }
}
