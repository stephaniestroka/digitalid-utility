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
import javax.annotation.Nullable;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.generators.BuilderGenerator;
import net.digitalid.utility.generator.generators.SubclassGenerator;
import net.digitalid.utility.generator.information.method.MethodInformation;

/**
 * This type collects the relevant information about a generated representing field for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 */
public class GeneratedRepresentingFieldInformation extends GeneratedFieldInformation implements RepresentingFieldInformation {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected GeneratedRepresentingFieldInformation(@Nonnull DeclaredType containingType, @Nonnull MethodInformation getter, @Nullable MethodInformation setter) {
        super(containingType, getter, setter);
    }
    
    @Override
    public boolean isMandatory() {
        return !(this.hasDefaultValue() || this.hasAnnotation(Nullable.class) || this.getGetter().getReturnType().getAnnotation(Nullable.class) != null || getSetter() != null);
    }
    
    /**
     * Returns the field information of the given containing type, getter and setter which can be used to access the value.
     * 
     * @require getter.isGetter() : "The first method has to be a getter.";
     * @require setter == null || setter.isSetter() : "The second method has to be null or a setter.";
     */
    @Pure
    public static @Nonnull GeneratedRepresentingFieldInformation of(@Nonnull DeclaredType containingType, @Nonnull MethodInformation getter, @Nullable MethodInformation setter) {
        return new GeneratedRepresentingFieldInformation(containingType, getter, setter);
    }
    
}
