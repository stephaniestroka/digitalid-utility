package net.digitalid.utility.generator.information.field;

import javax.annotation.Nonnull;
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;

/**
 *
 */
public class EnumValueInformation extends FieldInformationImplementation implements RepresentingFieldInformation {
    
    @Override
    public boolean isAccessible() {
        return true;
    }
    
    @Override
    public @Nonnull String getAccessCode() {
        return "name()";
    }
    
    @Override
    public boolean isMutable() {
        return false;
    }
    
    protected EnumValueInformation(@Nonnull Element element) {
        // element == enum element 
        super(element, element.asType(), (DeclaredType) element.asType());
    }
    
    public static @Nonnull EnumValueInformation of(@Nonnull Element element) {
        return new EnumValueInformation(element);
    }
    
}
