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
package net.digitalid.utility.validation.annotations.generation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Types;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.processing.logging.ErrorLogger;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processing.utility.StaticProcessingEnvironment;
import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 * This annotation indicates that the annotated method or constructor is used to recover a converted object.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@ValueValidator(Recover.Validator.class)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Recover {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of the surrounding annotation.
     */
    @Stateless
    public static class Validator implements ValueAnnotationValidator {
        
        @Pure
        @Override
        public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull ErrorLogger errorLogger) {
            final @Nonnull TypeElement surroundingType = ProcessingUtility.getSurroundingType(element);
            if (element.getKind() == ElementKind.METHOD) {
                final @Nonnull ExecutableElement method = (ExecutableElement) element;
                final @Nonnull Types typeUtils = StaticProcessingEnvironment.getTypeUtils();
                if (!element.getModifiers().contains(Modifier.STATIC) || !typeUtils.isSubtype(method.getReturnType(), typeUtils.erasure(surroundingType.asType()))) {
                    errorLogger.log("The annotation $ may only be used on static methods whose return type is a subtype of the surrounding type.", SourcePosition.of(element, annotationMirror), getAnnotationNameWithLeadingAt());
                }
            } else if (element.getKind() == ElementKind.CONSTRUCTOR) {
                if (ProcessingUtility.getConstructors(surroundingType).isSingle()) {
                    errorLogger.log("The annotation $ may only be used on constructors if there are also other constructors in the same class.", SourcePosition.of(element, annotationMirror), getAnnotationNameWithLeadingAt());
                }
            } else {
                errorLogger.log("The annotation $ may only be used on methods and constructors.", SourcePosition.of(element, annotationMirror), getAnnotationNameWithLeadingAt());
            }
            
            for (@Nonnull ExecutableElement executableElement : ProcessingUtility.getConstructors(surroundingType).combine(ProcessingUtility.getMethods(surroundingType))) {
                if (ProcessingUtility.hasAnnotation(executableElement, Recover.class) && !executableElement.equals(element)) {
                    errorLogger.log("The annotation $ may only be used on at most one executable in the same type.", SourcePosition.of(element, annotationMirror), getAnnotationNameWithLeadingAt());
                }
            }
        }
        
    }
    
}
