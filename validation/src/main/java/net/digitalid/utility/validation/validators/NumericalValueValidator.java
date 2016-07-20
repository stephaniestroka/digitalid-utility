package net.digitalid.utility.validation.validators;

import java.math.BigInteger;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.interfaces.BigIntegerNumerical;
import net.digitalid.utility.interfaces.LongNumerical;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processing.utility.TypeImporter;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 * This class generates the contracts for numerical annotations.
 * 
 * @see net.digitalid.utility.validation.annotations.math
 * 
 * @see ValueRelativeNumericalValueValidator
 */
@Stateless
public abstract class NumericalValueValidator implements ValueAnnotationValidator {
    
    /* -------------------------------------------------- Target Types -------------------------------------------------- */
    
    private static final @Nonnull FiniteIterable<@Nonnull Class<?>> targetTypes = FiniteIterable.of(byte.class, Byte.class, short.class, Short.class, int.class, Integer.class, long.class, Long.class, float.class, Float.class, double.class, Double.class, BigInteger.class, LongNumerical.class, BigIntegerNumerical.class);
    
    @Pure
    @Override
    public @Nonnull FiniteIterable<@Nonnull Class<?>> getTargetTypes() {
        return targetTypes;
    }
    
    /* -------------------------------------------------- Comparison Operator -------------------------------------------------- */
    
    /**
     * Returns the operator which is used to compare the element.
     */
    @Pure
    public abstract @Nonnull String getComparisonOperator();
    
    /* -------------------------------------------------- Contract Generation -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull TypeImporter typeImporter) {
        if (ProcessingUtility.isRawSubtype(element, BigIntegerNumerical.class)) {
            return Contract.with("# == null || #.getValue().compareTo(" + typeImporter.importIfPossible(BigInteger.class) + ".ZERO) " + getComparisonOperator() + " 0", "The # has to be null or " + getDecamelizedAnnotationName().replace(" ", "-") + " but was $.", element);
        } else if (ProcessingUtility.isRawSubtype(element, BigInteger.class)) {
            return Contract.with("# == null || #.compareTo(" + typeImporter.importIfPossible(BigInteger.class) + ".ZERO) " + getComparisonOperator() + " 0", "The # has to be null or " + getDecamelizedAnnotationName().replace(" ", "-") + " but was $.", element);
        } else if (ProcessingUtility.isRawSubtype(element, LongNumerical.class)) {
            return Contract.with("# == null || #.getValue() " + getComparisonOperator() + " 0", "The # has to be null or " + getDecamelizedAnnotationName().replace(" ", "-") + " but was $.", element);
        } else if (ProcessingUtility.isRawSubtype(element, Number.class)) {
            return Contract.with("# == null || # " + getComparisonOperator() + " 0", "The # has to be null or " + getDecamelizedAnnotationName().replace(" ", "-") + " but was $.", element);
        } else {
            return Contract.with("# " + getComparisonOperator() + " 0", "The # has to be " + getDecamelizedAnnotationName().replace(" ", "-") + " but was $.", element);
        }
    }
    
}
