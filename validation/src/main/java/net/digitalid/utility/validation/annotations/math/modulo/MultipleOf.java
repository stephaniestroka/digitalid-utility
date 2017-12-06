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
package net.digitalid.utility.validation.annotations.math.modulo;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigInteger;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.interfaces.BigIntegerNumerical;
import net.digitalid.utility.interfaces.LongNumerical;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processing.utility.TypeImporter;
import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.validators.ModuloValidator;

/**
 * This annotation indicates that a numeric value is a multiple of the given value.
 */
@Documented
@Target({ElementType.TYPE_USE, ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.SOURCE)
@ValueValidator(MultipleOf.Validator.class)
public @interface MultipleOf {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the value of which the annotated numeric value is a multiple of.
     */
    long value();
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator extends ModuloValidator {
        
        @Pure
        @Override
        public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull TypeImporter typeImporter) {
            if (ProcessingUtility.isRawSubtype(element, BigIntegerNumerical.class)) {
                return Contract.with("# == null || #.getValue().mod(" + typeImporter.importIfPossible(BigInteger.class) + ".valueOf(@)).equals(" + typeImporter.importIfPossible(BigInteger.class) + ".ZERO)", "The # has to be null or a multiple of @ but was $.", element, annotationMirror);
            } else if (ProcessingUtility.isRawSubtype(element, BigInteger.class)) {
                return Contract.with("# == null || #.mod(" + typeImporter.importIfPossible(BigInteger.class) + ".valueOf(@)).equals(" + typeImporter.importIfPossible(BigInteger.class) + ".ZERO)", "The # has to be null or a multiple of @ but was $.", element, annotationMirror);
            } else if (ProcessingUtility.isRawSubtype(element, LongNumerical.class)) {
                return Contract.with("# == null || #.getValue() % @ == 0", "The # has to be a multiple of @ but was $.", element, annotationMirror);
            } else {
                return Contract.with("# % @ == 0", "The # has to be a multiple of @ but was $.", element, annotationMirror);
            }
        }
        
    }
    
}
