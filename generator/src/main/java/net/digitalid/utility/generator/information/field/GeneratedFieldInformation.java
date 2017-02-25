package net.digitalid.utility.generator.information.field;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.processing.utility.StaticProcessingEnvironment;

/**
 *
 */
public abstract class GeneratedFieldInformation extends NonDirectlyAccessibleFieldInformation implements PotentiallyInheritedFieldInformation {
    
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
    
    @Pure
    @Override
    public @Nonnull FiniteIterable<@Nonnull AnnotationMirror> getAnnotations() {
        return super.getAnnotations().combine(FiniteIterable.of(getGetter().getElement().getReturnType().getAnnotationMirrors()));
    }
    
}
