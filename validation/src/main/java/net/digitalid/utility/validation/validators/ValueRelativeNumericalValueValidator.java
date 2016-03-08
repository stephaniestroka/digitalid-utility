package net.digitalid.utility.validation.validators;

import java.math.BigInteger;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.interfaces.Numerical;
import net.digitalid.utility.validation.processing.ProcessingUtility;
import net.digitalid.utility.validation.processing.TypeImporter;

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
    public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull TypeImporter typeImporter) {
        if (ProcessingUtility.isAssignable(element, Numerical.class)) {
            return Contract.with("# == null || #.getValue().compareTo(" + typeImporter.importIfPossible(BigInteger.class) + ".valueOf(@)) " + getComparisonOperator() + " 0", "The # has to be null or " + getDecamelizedAnnotationName() + " @ but was $.", element, annotationMirror);
        } else if (ProcessingUtility.isAssignable(element, BigInteger.class)) {
            return Contract.with("# == null || #.compareTo(" + typeImporter.importIfPossible(BigInteger.class) + ".valueOf(@)) " + getComparisonOperator() + " 0", "The # has to be null or " + getDecamelizedAnnotationName() + " @ but was $.", element, annotationMirror);
        } else {
            return Contract.with("# " + getComparisonOperator() + " @", "The # has to be " + getDecamelizedAnnotationName() + " @ but was $.", element, annotationMirror);
        }
    }
    
}
