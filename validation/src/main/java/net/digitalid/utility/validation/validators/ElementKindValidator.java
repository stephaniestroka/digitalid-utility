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
package net.digitalid.utility.validation.validators;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processing.utility.TypeImporter;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 * This class generates the contracts for the kinds of types.
 * 
 * @see net.digitalid.utility.validation.annotations.type.kind
 */
@Stateless
public abstract class ElementKindValidator implements ValueAnnotationValidator {
    
    /* -------------------------------------------------- Target Types -------------------------------------------------- */
    
    private static final @Nonnull FiniteIterable<@Nonnull Class<?>> targetTypes = FiniteIterable.of(Class.class, Element.class);
    
    @Pure
    @Override
    public @Nonnull FiniteIterable<@Nonnull Class<?>> getTargetTypes() {
        return targetTypes;
    }
    
    /* -------------------------------------------------- Kind -------------------------------------------------- */
    
    /**
     * Returns the kind which the type has to be.
     */
    @Pure
    public abstract @Nonnull ElementKind getKind();
    
    /* -------------------------------------------------- Condition -------------------------------------------------- */
    
    /**
     * Returns the condition for the given element depending on the type of the element.
     */
    @Pure
    public static @Nonnull String getCondition(@Nonnull Element element, @Nonnull ElementKind kind, @NonCaptured @Modified @Nonnull TypeImporter typeImporter) {
        if (ProcessingUtility.isRawSubtype(element, Class.class)) {
            switch (kind) {
                case CLASS: return "!#.isInterface() && !#.isEnum()";
                case INTERFACE: return "#.isInterface() && !#.isAnnotation()";
                case ENUM: return "#.isEnum()";
                case ANNOTATION_TYPE: return "#.isAnnotation()";
                default: return "false";
            }
        } else {
            return "#.getKind() == " + typeImporter.importIfPossible(ElementKind.class) + "." + kind.name();
        }
    }
    
    /* -------------------------------------------------- Contract Generation -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull TypeImporter typeImporter) {
        return Contract.with("# == null || " + getCondition(element, getKind(), typeImporter), "The # has to be null or of the kind '" + getKind().name().toLowerCase().replace("_", " ") + "' but was $.", element);
    }
    
}
