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
package net.digitalid.utility.generator.information.field;

import javax.annotation.Nonnull;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.VariableElement;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.generator.generators.BuilderGenerator;
import net.digitalid.utility.generator.generators.SubclassGenerator;
import net.digitalid.utility.processing.logging.SourcePosition;

/**
 * This type collects the relevant information about a directly accessible parameter-based field for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 */
public class NonDirectlyAccessibleParameterBasedFieldInformation extends NonDirectlyAccessibleFieldInformation implements ParameterBasedFieldInformation {
    
    /* -------------------------------------------------- Parameter -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull VariableElement getParameter() {
        return (VariableElement) getElement();
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected NonDirectlyAccessibleParameterBasedFieldInformation(@Nonnull VariableElement parameter, @Nonnull NonDirectlyAccessibleFieldInformation nonDirectlyAccessibleFieldInformation) {
        super(parameter, parameter.asType(), nonDirectlyAccessibleFieldInformation.getContainingType(), nonDirectlyAccessibleFieldInformation.getGetter(), nonDirectlyAccessibleFieldInformation.getSetter());
        
        Require.that(parameter.getKind() == ElementKind.PARAMETER).orThrow("The element $ has to be a parameter.", SourcePosition.of(parameter));
    }
    
    /**
     * Returns the field information of the given parameter, containing type, getter and setter which can be used to access the value.
     * 
     * @require parameter.getKind() == ElementKind.PARAMETER : "The element has to be a parameter.";
     * @require getter.isGetter() : "The first method has to be a getter.";
     * @require setter == null || setter.isSetter() : "The second method has to be null or a setter.";
     */
    @Pure
    public static @Nonnull NonDirectlyAccessibleParameterBasedFieldInformation of(@Nonnull VariableElement parameter, @Nonnull NonDirectlyAccessibleFieldInformation nonDirectlyAccessibleFieldInformation) {
        return new NonDirectlyAccessibleParameterBasedFieldInformation(parameter, nonDirectlyAccessibleFieldInformation);
    }
    
}
