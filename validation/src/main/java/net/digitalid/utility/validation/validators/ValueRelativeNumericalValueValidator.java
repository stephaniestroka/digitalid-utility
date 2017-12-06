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
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;

/**
 * This class generates numerical contracts that are relative to the annotation value.
 * 
 * @see net.digitalid.utility.validation.annotations.math.relative
 */
@Stateless
public abstract class ValueRelativeNumericalValueValidator extends NumericalValueValidator {
    
    /* -------------------------------------------------- Contract Generation -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull TypeImporter typeImporter) {
        if (ProcessingUtility.isRawSubtype(element, BigIntegerNumerical.class)) {
            return Contract.with("# == null || #.getValue().compareTo(" + typeImporter.importIfPossible(BigInteger.class) + ".valueOf(@)) " + getComparisonOperator() + " 0", "The # has to be null or " + getDecamelizedAnnotationName() + " @ but was $.", element, annotationMirror);
        } else if (ProcessingUtility.isRawSubtype(element, BigInteger.class)) {
            return Contract.with("# == null || #.compareTo(" + typeImporter.importIfPossible(BigInteger.class) + ".valueOf(@)) " + getComparisonOperator() + " 0", "The # has to be null or " + getDecamelizedAnnotationName() + " @ but was $.", element, annotationMirror);
        } else if (ProcessingUtility.isRawSubtype(element, LongNumerical.class)) {
            return Contract.with("# == null || #.getValue() " + getComparisonOperator() + " @", "The # has to be null or " + getDecamelizedAnnotationName() + " @ but was $.", element, annotationMirror);
        } else if (ProcessingUtility.isRawSubtype(element, Number.class)) {
            return Contract.with("# == null || # " + getComparisonOperator() + " @", "The # has to be null or " + getDecamelizedAnnotationName() + " @ but was $.", element, annotationMirror);
        } else {
            return Contract.with("# " + getComparisonOperator() + " @", "The # has to be " + getDecamelizedAnnotationName() + " @ but was $.", element, annotationMirror);
        }
    }
    
}
