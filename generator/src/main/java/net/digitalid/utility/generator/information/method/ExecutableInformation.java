/*
 * Copyright (C) 2017 Synacts GmbH, Switzerland (info@synacts.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.generators.BuilderGenerator;
import net.digitalid.utility.generator.generators.SubclassGenerator;
import net.digitalid.utility.generator.information.ElementInformationImplementation;
import net.digitalid.utility.generator.information.type.TypeInformation;
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
    
    protected ExecutableInformation(@Nonnull Element element, @Nonnull DeclaredType containingType, @Nonnull TypeInformation typeInformation) {
        super(element, StaticProcessingEnvironment.getTypeUtils().asMemberOf(containingType, element), containingType);
        
        final @Nonnull List<MethodParameterInformation> parameters = new ArrayList<>(getElement().getParameters().size());
        for (@Nonnull VariableElement variableElement : getElement().getParameters()) {
            parameters.add(new MethodParameterInformation(variableElement, getContainingType(), typeInformation));
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
