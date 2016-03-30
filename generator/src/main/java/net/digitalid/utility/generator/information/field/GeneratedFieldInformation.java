package net.digitalid.utility.generator.information.field;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;

import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.logging.processing.StaticProcessingEnvironment;
import net.digitalid.utility.annotations.method.Pure;

/**
 * This type collects the relevant information about a generated field for generating a {@link net.digitalid.utility.generator.SubclassGenerator subclass} and {@link net.digitalid.utility.generator.BuilderGenerator builder}.
 */
public class GeneratedFieldInformation extends NonDirectlyAccessibleFieldInformation implements RepresentingFieldInformation, PotentiallyInheritedFieldInformation {
    
    /* -------------------------------------------------- Name -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String getName() {
        return getGetter().getFieldName();
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected GeneratedFieldInformation(@Nonnull DeclaredType containingType, @Nonnull MethodInformation getter, @Nullable MethodInformation setter) {
        super(getter.getElement(), ((ExecutableType) StaticProcessingEnvironment.getTypeUtils().asMemberOf(containingType, getter.getElement())).getReturnType(), containingType, getter, setter);
    }
    
    /**
     * Returns the field information of the given containing type, getter and setter which can be used to access the value.
     * 
     * @require getter.isGetter() : "The first method has to be a getter.";
     * @require setter == null || setter.isSetter() : "The second method has to be null or a setter.";
     */
    @Pure
    public static @Nonnull GeneratedFieldInformation of(@Nonnull DeclaredType containingType, @Nonnull MethodInformation getter, @Nullable MethodInformation setter) {
        return new GeneratedFieldInformation(containingType, getter, setter);
    }
    
}
