package net.digitalid.utility.generator.information.method;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.collaboration.annotations.TODO;
import net.digitalid.utility.collaboration.enumerations.Author;
import net.digitalid.utility.collaboration.enumerations.Priority;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.generators.BuilderGenerator;
import net.digitalid.utility.generator.generators.SubclassGenerator;
import net.digitalid.utility.generator.information.ElementInformationImplementation;
import net.digitalid.utility.generator.information.field.FieldInformation;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.utility.StaticProcessingEnvironment;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This type collects the relevant information about an executable for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 * 
 * @see ConstructorInformation
 * @see MethodInformation
 */
@Immutable
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
    
    /* -------------------------------------------------- Method Parameters -------------------------------------------------- */
    
    private final @Nonnull FiniteIterable<@Nonnull MethodParameterInformation> parameters;
    
    /**
     * Returns a list of parameters.
     */
    @Pure
    public @Nonnull FiniteIterable<@Nonnull MethodParameterInformation> getParameters() {
        return parameters;
        
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ExecutableInformation(@Nonnull Element element, @Nonnull DeclaredType containingType) {
        super(element, StaticProcessingEnvironment.getTypeUtils().asMemberOf(containingType, element), containingType);
        
        final @Nonnull List<MethodParameterInformation> parameters = new ArrayList<>(getElement().getParameters().size());
        for (@Nonnull VariableElement variableElement : getElement().getParameters()) {
            parameters.add(new MethodParameterInformation(variableElement, getContainingType()));
        }
        this.parameters = FiniteIterable.of(parameters);
    }
    
    @TODO(task = "Remove the code duplication of the two constructors by making the field information nullable and calling this constructor from the other.", date = "2016-05-16", author = Author.KASPAR_ETTER, assignee = Author.STEPHANIE_STROKA, priority = Priority.LOW)
    protected ExecutableInformation(@Nonnull Element element, @Nonnull DeclaredType containingType, @Nonnull FiniteIterable<@Nonnull FieldInformation> fieldInformation) {
        super(element, StaticProcessingEnvironment.getTypeUtils().asMemberOf(containingType, element), containingType);
        
        final @Nonnull List<MethodParameterInformation> parameters = new ArrayList<>(getElement().getParameters().size());
        for (@Nonnull VariableElement variableElement : getElement().getParameters()) {
            parameters.add(new MethodParameterInformation(variableElement, getContainingType(), fieldInformation));
        }
        this.parameters = FiniteIterable.of(parameters);
    }
    
    /* -------------------------------------------------- Parameters -------------------------------------------------- */
    
    // TODO: Review and document the following methods!
    
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
            ProcessingLog.verbose("Parameter type: $, desired type: $", parameterTypeName, desiredTypeName);
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
    
}
