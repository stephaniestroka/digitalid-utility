package net.digitalid.utility.generator.information.field;

import javax.annotation.Nonnull;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.generators.BuilderGenerator;
import net.digitalid.utility.generator.generators.SubclassGenerator;
import net.digitalid.utility.processing.utility.StaticProcessingEnvironment;

/**
 * This type collects the relevant information about a declared, directly-accessible field for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 */
public class DirectlyAccessibleDeclaredFieldInformation extends DirectlyAccessibleFieldInformation implements PotentiallyInheritedFieldInformation {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected DirectlyAccessibleDeclaredFieldInformation(@Nonnull VariableElement field, @Nonnull DeclaredType containingType) {
        super(field, StaticProcessingEnvironment.getTypeUtils().asMemberOf(containingType, field), containingType, field);
    }
    
    /**
     * Returns the field information of the given field and containing type.
     * 
     * @require field.getKind() == ElementKind.FIELD : "The element has to be a field.";
     */
    @Pure
    public static @Nonnull DirectlyAccessibleDeclaredFieldInformation of(@Nonnull VariableElement field, @Nonnull DeclaredType containingType) {
        return new DirectlyAccessibleDeclaredFieldInformation(field, containingType);
    }
    
}
