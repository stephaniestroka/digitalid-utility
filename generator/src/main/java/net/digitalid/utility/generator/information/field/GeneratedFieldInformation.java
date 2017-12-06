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
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.processing.utility.StaticProcessingEnvironment;

/**
 *
 */
public abstract class GeneratedFieldInformation extends NonDirectlyAccessibleFieldInformation implements PotentiallyInheritedFieldInformation {
    
    /* -------------------------------------------------- Name -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String getName() {
        return getGetter().getFieldName();
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected GeneratedFieldInformation(@Nonnull DeclaredType containingType, @Nonnull MethodInformation getter, @Nullable MethodInformation setter) {
        super(getter.getElement(), ((ExecutableType) StaticProcessingEnvironment.getTypeUtils().asMemberOf(containingType, getter.getElement())).getReturnType(), containingType, getter, setter);
    }
    
    @Pure
    @Override
    public @Nonnull FiniteIterable<@Nonnull AnnotationMirror> getAnnotations() {
        return super.getAnnotations().combine(FiniteIterable.of(getGetter().getElement().getReturnType().getAnnotationMirrors()));
    }
    
}
