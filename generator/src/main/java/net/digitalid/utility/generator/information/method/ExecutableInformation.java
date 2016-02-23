package net.digitalid.utility.generator.information.method;

import javax.annotation.Nonnull;
import javax.lang.model.element.ExecutableElement;

import net.digitalid.utility.generator.information.NonTypeInformation;
import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.string.QuoteString;
import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * Description.
 */
public abstract class ExecutableInformation extends NonTypeInformation {
    
    /* -------------------------------------------------- Element -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull ExecutableElement getElement() {
        return (ExecutableElement) super.getElement();
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
            AnnotationLog.verbose("Parameter type: " + QuoteString.inSingle(parameterTypeName) + ", desired type:" + QuoteString.inSingle(desiredTypeName));
            return parameterTypeName.equals(desiredTypeName);
        } else {
            return false;
        }
    }
    
    @Pure
    public boolean hasSingleParameter(@Nonnull Class<?> type) {
        return hasSingleParameter(type.getCanonicalName());
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ExecutableInformation() {
        // TODO
    }
    
}
