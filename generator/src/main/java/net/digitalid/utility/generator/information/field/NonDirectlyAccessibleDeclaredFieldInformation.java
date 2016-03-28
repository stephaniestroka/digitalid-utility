package net.digitalid.utility.generator.information.field;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.logging.processing.StaticProcessingEnvironment;
import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * This type collects the relevant information about a non-directly accessible, declared field for generating a {@link net.digitalid.utility.generator.SubclassGenerator subclass} and {@link net.digitalid.utility.generator.BuilderGenerator builder}.
 */
public class NonDirectlyAccessibleDeclaredFieldInformation extends NonDirectlyAccessibleFieldInformation implements PotentiallyInheritedFieldInformation {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected NonDirectlyAccessibleDeclaredFieldInformation(@Nonnull VariableElement field, @Nonnull DeclaredType containingType, @Nonnull MethodInformation getter, @Nullable MethodInformation setter) {
        super(field, StaticProcessingEnvironment.getTypeUtils().asMemberOf(containingType, field), containingType, getter, setter);
    }
    
    /**
     * Returns the field information of the given field and containing type.
     * 
     * @require field.getKind() == ElementKind.FIELD : "The element has to be a field.";
     */
    @Pure
    public static @Nonnull NonDirectlyAccessibleDeclaredFieldInformation of(@Nonnull VariableElement field, @Nonnull DeclaredType containingType, @Nonnull MethodInformation getter, @Nullable MethodInformation setter) {
        return new NonDirectlyAccessibleDeclaredFieldInformation(field, containingType, getter, setter);
    }
    
}