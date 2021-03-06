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
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.generator.generators.BuilderGenerator;
import net.digitalid.utility.generator.generators.SubclassGenerator;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.processing.logging.SourcePosition;

/**
 * This type collects the relevant information about a non-directly accessible field for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 * 
 * @see NonDirectlyAccessibleParameterBasedFieldInformation
 * @see GeneratedRepresentingFieldInformation
 */
public abstract class NonDirectlyAccessibleFieldInformation extends FieldInformationImplementation {
    
    /* -------------------------------------------------- Getter -------------------------------------------------- */
    
    private final @Nonnull MethodInformation getter;
    
    /**
     * Returns the getter through which the value can be accessed.
     * 
     * @ensure result.isGetter() : "The returned method is a getter.";
     */
    @Pure
    public @Nonnull MethodInformation getGetter() {
        return getter;
    }
    
    /* -------------------------------------------------- Access -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean isAccessible() {
        return true;
    }
    
    @Pure
    @Override
    public @Nonnull String getAccessCode() {
        Require.that(isAccessible()).orThrow("The field cannot be accessed");
        
        return getter.getName() + "()";
    }
    
    /* -------------------------------------------------- Setter -------------------------------------------------- */
    
    private final @Nullable MethodInformation setter;
    
    /**
     * Returns whether this field has a setter.
     */
    @Pure
    public boolean hasSetter() {
        return setter != null;
    }
    
    /**
     * Returns the setter through which the value can be modified.
     * This method requires that the setter is non-null.
     * 
     * @ensure result.isSetter() : "The returned method is a setter.";
     */
    @Pure
    @SuppressWarnings("null")
    public @Nonnull MethodInformation getNonNullSetter() {
        Require.that(hasSetter()).orThrow("The setter may not be null.");
        
        return setter;
    }
    
    /**
     * Returns the setter through which the value can be modified.
     */
    @Pure
    public @Nullable MethodInformation getSetter() {
        return setter;
    }
    
    /* -------------------------------------------------- Mutability -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean isMutable() {
        return hasSetter();
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * @param element Either the field element (VariableElement), the representative parameter, or the getter element if the field is going to be generated.
     * @param type
     * @param containingType
     * @param getter
     * @param setter
     */
    protected NonDirectlyAccessibleFieldInformation(@Nonnull Element element, @Nonnull TypeMirror type, @Nonnull DeclaredType containingType, @Nonnull MethodInformation getter, @Nullable MethodInformation setter) {
        super(element, type, containingType);
        
        Require.that(getter.isGetter()).orThrow("The method $ has to be a getter.", SourcePosition.of(getter.getElement()));
        Require.that(setter == null || setter.isSetter()).orThrow("The method $ has to be null or a setter.", setter == null ? null : SourcePosition.of(setter.getElement()));
        
        this.getter = getter;
        this.setter = setter;
    }
    
}
