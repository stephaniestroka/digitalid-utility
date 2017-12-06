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
