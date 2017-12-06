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
package net.digitalid.utility.generator.information.type;

import java.util.Collections;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.generators.BuilderGenerator;
import net.digitalid.utility.generator.generators.ConverterGenerator;
import net.digitalid.utility.generator.generators.SubclassGenerator;
import net.digitalid.utility.generator.information.field.FieldInformation;
import net.digitalid.utility.generator.information.filter.InformationFilter;
import net.digitalid.utility.generator.information.method.ConstructorInformation;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.generator.information.variable.VariableElementInformation;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.validation.annotations.generation.NonRepresentative;
import net.digitalid.utility.validation.annotations.generation.Recover;
import net.digitalid.utility.validation.annotations.size.Empty;
import net.digitalid.utility.validation.annotations.size.MinSize;

/**
 * This type collects the relevant information about an interface for generating a {@link SubclassGenerator subclass}, {@link BuilderGenerator builder} and {@link ConverterGenerator converter}.
 */
public final class InterfaceInformation extends TypeInformation {
    
    /* -------------------------------------------------- Recover Method -------------------------------------------------- */
    
    /**
     * The method information of the recover method, or null if the class does not implement a recover method.
     */
    private final @Nullable MethodInformation recoverMethod;
    
    @Pure
    @Override
    public @Nullable MethodInformation getRecoverMethod() {
        return recoverMethod;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull @MinSize(0) @Empty FiniteIterable<@Nonnull ConstructorInformation> getConstructors() {
        return FiniteIterable.of(Collections.<ConstructorInformation>emptyList());
    }
    
    /* -------------------------------------------------- Field Information -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull FiniteIterable<@Nonnull FieldInformation> getFieldInformation() {
        return generatedRepresentingFieldInformation.map(field -> field);
    }
    
    /* -------------------------------------------------- Accessible Field Information -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull FiniteIterable<@Nonnull FieldInformation> getAccessibleFieldInformation() {
        return getFieldInformation();
    }
    
    /* -------------------------------------------------- Constructor Parameters -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull FiniteIterable<VariableElementInformation> getRecoverParameters() {
        return generatedRepresentingFieldInformation.map(field -> (VariableElementInformation) field).filter(field -> !field.hasAnnotation(NonRepresentative.class));
    }
    
    @Pure
    @Override
    public @Nonnull FiniteIterable<VariableElementInformation> getConstructorParameters() {
        return generatedRepresentingFieldInformation.map(field -> (VariableElementInformation) field).filter(field -> !field.hasAnnotation(NonRepresentative.class));
    }
    
    /* -------------------------------------------------- Representing Field Information -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull FiniteIterable<@Nonnull FieldInformation> getRepresentingFieldInformation() {
        return getFieldInformation().filter(field -> !field.hasAnnotation(NonRepresentative.class));
    }
    
    /* -------------------------------------------------- Overriden Methods -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull FiniteIterable<@Nonnull MethodInformation> getOverriddenMethods() {
        return FiniteIterable.of(Collections.<MethodInformation>emptyList());
    }
    
    /* -------------------------------------------------- Initialization Marker -------------------------------------------------- */
    
    private boolean initialized = false;
    
    @Override
    public boolean isInitialized() {
        return initialized;
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new interface information instance.
     */
    protected InterfaceInformation(@Nonnull TypeElement element, @Nonnull DeclaredType containingType) {
        super(element, containingType);
    
        final @Nonnull FiniteIterable<@Nonnull MethodInformation> methodInformationIterable = InformationFilter.getMethodInformation(element, containingType, this);
    
        ProcessingLog.debugging("All methods of type $: $", containingType, methodInformationIterable.join());
        
        this.recoverMethod = methodInformationIterable.findFirst(method -> method.hasAnnotation(Recover.class));
        
        this.initialized = true;
    }
    
    /**
     * Returns a class information object of the given type typeElement and containing type.
     */
    @Pure
    public static @Nonnull InterfaceInformation of(@Nonnull TypeElement element, @Nonnull DeclaredType containingType) {
        return new InterfaceInformation(element, containingType);
    }
    
}
