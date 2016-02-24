package net.digitalid.utility.generator.information.method;

import javax.annotation.Nonnull;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;

import net.digitalid.utility.generator.information.ElementInformationImplementation;
import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.logging.processing.AnnotationProcessing;
import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * This class collects the relevant information about an executable for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 * 
 * @see ConstructorInformation
 * @see MethodInformation
 */
public abstract class ExecutableInformation extends ElementInformationImplementation {
    
    /* -------------------------------------------------- Element -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull ExecutableElement getElement() {
        return (ExecutableElement) super.getElement();
    }
    
    /* -------------------------------------------------- Type -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull ExecutableType getType() {
        return (ExecutableType) super.getType();
    }
    
    /* -------------------------------------------------- Parameters -------------------------------------------------- */
    
    @Pure
    public boolean hasParameters() {
        return !getElement().getParameters().isEmpty();
    }
    
    @Pure
    public boolean hasSingleParameter() {
        return getElement().getParameters().size() == 1;
    }
    
    @Pure
    public boolean hasSingleParameter(@Nonnull String desiredTypeName) {
        if (hasSingleParameter()) {
            final @Nonnull String parameterTypeName = getElement().getParameters().get(0).asType().toString();
            AnnotationLog.verbose("Parameter type: $, desired type: $", parameterTypeName, desiredTypeName);
            return parameterTypeName.equals(desiredTypeName);
        } else {
            return false;
        }
    }
    
    @Pure
    public boolean hasSingleParameter(@Nonnull Class<?> type) {
        return hasSingleParameter(type.getCanonicalName());
    }
    
    /* -------------------------------------------------- Exceptions -------------------------------------------------- */
    
    @Pure
    public boolean throwsExceptions() {
        return !getElement().getThrownTypes().isEmpty();
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ExecutableInformation(@Nonnull Element element, @Nonnull DeclaredType containingType) {
        super(element, AnnotationProcessing.getTypeUtils().asMemberOf(containingType, element), containingType);
    }
    
}
