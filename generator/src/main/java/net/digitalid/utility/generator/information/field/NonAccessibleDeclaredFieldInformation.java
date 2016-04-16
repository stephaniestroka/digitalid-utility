package net.digitalid.utility.generator.information.field;

import javax.annotation.Nonnull;
import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.functional.fixes.Quotes;
import net.digitalid.utility.processing.utility.StaticProcessingEnvironment;

/**
 *
 */
public class NonAccessibleDeclaredFieldInformation extends FieldInformationImplementation implements RepresentingFieldInformation {
    
    protected NonAccessibleDeclaredFieldInformation(@Nonnull Element element, @Nonnull TypeMirror type, @Nonnull DeclaredType containingType) {
        super(element, type, containingType);
    }
    
    public static @Nonnull NonAccessibleDeclaredFieldInformation of(@Nonnull Element element, @Nonnull DeclaredType containingType) {
        return new NonAccessibleDeclaredFieldInformation(element, StaticProcessingEnvironment.getTypeUtils().asMemberOf(containingType, element), containingType);
    }
    
    @Override
    public @Nonnull String getAccessCode() {
        throw new UnsupportedOperationException("Non-accessible field " + Quotes.inSingle(getName()) + " does not have an access code.");
    }
    
    @Override
    public boolean isMutable() {
        return false;
    }
    
}
