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
package net.digitalid.utility.validation.validator;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.processing.logging.ErrorLogger;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.processing.AnnotationHandlerUtility;

/**
 * An annotation handler checks the use of and generates code for an annotation during annotation processing.
 * 
 * @see MethodAnnotationValidator
 * @see ValueAnnotationValidator
 * @see TypeAnnotationValidator
 * 
 * @see AnnotationHandlerUtility
 */
@Stateless
@Functional
public interface AnnotationHandler {
    
    /* -------------------------------------------------- Usage Check -------------------------------------------------- */
    
    /**
     * Checks whether the given annotation is used correctly on the given element and logs errors with the given logger otherwise.
     */
    @Pure
    public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull ErrorLogger errorLogger);
    
    /* -------------------------------------------------- Annotation Name -------------------------------------------------- */
    
    /**
     * Returns the name of the surrounding annotation.
     */
    @Pure
    public default @Nonnull String getAnnotationName() {
        final @Nonnull String name = getClass().getName();
        return name.contains("$") ? name.substring(name.lastIndexOf('.') + 1, name.indexOf('$')) : name;
    }
    
    /**
     * Returns the name of the surrounding annotation with a leading at symbol.
     */
    @Pure
    public default @Nonnull String getAnnotationNameWithLeadingAt() {
        return "@" + getAnnotationName();
    }
    
    /**
     * Returns the decamelized name of the surrounding annotation.
     * 
     * @see Strings#decamelize(java.lang.String)
     */
    @Pure
    public default @Nonnull String getDecamelizedAnnotationName() {
        return Strings.decamelize(getAnnotationName());
    }
    
}
