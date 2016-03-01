package net.digitalid.utility.generator.information.field;

import javax.annotation.Nonnull;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.logging.processing.StaticProcessingEnvironment;
import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * This type collects the relevant information about a declared field for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 */
public class DeclaredFieldInformation extends DirectlyAccessibleFieldInformation implements PotentiallyInheritedFieldInformation {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected DeclaredFieldInformation(@Nonnull VariableElement field, @Nonnull DeclaredType containingType) {
        super(field, StaticProcessingEnvironment.getTypeUtils().asMemberOf(containingType, field), containingType, field);
    }
    
    /**
     * Returns the field information of the given field and containing type.
     * 
     * @require field.getKind() == ElementKind.FIELD : "The element has to be a field.";
     */
    @Pure
    public static @Nonnull DeclaredFieldInformation of(@Nonnull VariableElement field, @Nonnull DeclaredType containingType) {
        return new DeclaredFieldInformation(field, containingType);
    }
    
}
