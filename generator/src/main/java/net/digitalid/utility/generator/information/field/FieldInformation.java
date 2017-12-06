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
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.generators.BuilderGenerator;
import net.digitalid.utility.generator.generators.SubclassGenerator;
import net.digitalid.utility.generator.information.ElementInformation;
import net.digitalid.utility.generator.information.variable.VariableElementInformation;
import net.digitalid.utility.processing.utility.TypeImporter;

/**
 * This type collects the relevant information about a field for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 * 
 * @see PotentiallyInheritedFieldInformation
 * @see RepresentingFieldInformation
 */
public interface FieldInformation extends ElementInformation, VariableElementInformation {
    
    /* -------------------------------------------------- Default Value -------------------------------------------------- */
    
    /**
     * Returns whether this field has a default value.
     */
    @Pure
    public boolean hasDefaultValue();
    
    /* -------------------------------------------------- Access -------------------------------------------------- */
    
    /**
     * Returns true, if accessible and false otherwise.
     */
    @Pure
    public boolean isAccessible();
    
    /**
     * Returns the code to retrieve the value of this field.
     */
    @Pure
    public @Nonnull String getAccessCode();
    
    /* -------------------------------------------------- Mutability -------------------------------------------------- */
    
    /**
     * Returns whether this field is mutable.
     */
    @Pure
    public boolean isMutable();
    
    /* -------------------------------------------------- Field Type -------------------------------------------------- */
    
    public @Nonnull String getFieldType(@Nonnull TypeImporter typeImporter);
    
    /* -------------------------------------------------- Is Array -------------------------------------------------- */
    
    @Pure
    public boolean isArray();
    
    public @Nonnull TypeMirror getComponentType();
    
}
