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
package net.digitalid.utility.validation.annotations.testing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.processing.logging.ErrorLogger;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.meta.MethodValidator;
import net.digitalid.utility.validation.annotations.meta.TypeValidator;
import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.validator.AnnotationHandler;
import net.digitalid.utility.validation.validator.MethodAnnotationValidator;
import net.digitalid.utility.validation.validator.TypeAnnotationValidator;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 * This annotation indicates that the given annotation (handler) is used incorrectly on the annotated element.
 * Please note this annotation cannot be used to test the usage check of annotations that require a value.
 */
@Retention(RetentionPolicy.RUNTIME)
@TypeValidator(IncorrectUsage.Validator.class)
@ValueValidator(IncorrectUsage.Validator.class)
@MethodValidator(IncorrectUsage.Validator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.CONSTRUCTOR, ElementType.TYPE_USE})
public @interface IncorrectUsage {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns (the type of) the annotation handler whose usage check is to be tested.
     */
    @Nonnull Class<? extends AnnotationHandler> value();
    
    /* -------------------------------------------------- Logger -------------------------------------------------- */
    
    /**
     * This class checks whether the annotation handler logs an error.
     */
    @Mutable
    public static class Logger extends ErrorLogger {
        
        private Logger() {}
        
        private boolean correctlyUsed = true;
        
        /**
         * Returns whether the annotation is used correctly.
         */
        @Pure
        public boolean isCorrectlyUsed() {
            return correctlyUsed;
        }
        
        @Impure
        @Override
        public void log(@Nonnull CharSequence message, @Nullable SourcePosition position, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
            this.correctlyUsed = false;
        }
        
    }
    
    /* -------------------------------------------------- Validators -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator implements TypeAnnotationValidator, ValueAnnotationValidator, MethodAnnotationValidator {
        
        @Pure
        @Override
        public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull ErrorLogger errorLogger) {
            final @Nullable AnnotationHandler annotationHandler = ProcessingUtility.getInstance(ProcessingUtility.getAnnotationValue(annotationMirror), AnnotationHandler.class);
            if (annotationHandler != null) {
                final @Nonnull Logger logger = new Logger();
                annotationHandler.checkUsage(element, annotationMirror, logger);
                if (logger.isCorrectlyUsed()) {
                    errorLogger.log("The annotation $ is used correctly:", SourcePosition.of(element, annotationMirror), annotationHandler.getAnnotationNameWithLeadingAt());
                }
            }
        }
        
    }
    
}
