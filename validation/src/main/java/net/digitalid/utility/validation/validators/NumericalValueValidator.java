package net.digitalid.utility.validation.validators;

import java.math.BigInteger;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.immutable.collections.ImmutableSet;
import net.digitalid.utility.tuples.annotations.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.interfaces.Numerical;
import net.digitalid.utility.validation.processing.ProcessingUtility;
import net.digitalid.utility.validation.processing.TypeImporter;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 * This class generates the contracts for numerical annotations.
 * 
 * @see net.digitalid.utility.validation.annotations.math
 */
@Stateless
public abstract class NumericalValueValidator extends ValueAnnotationValidator {
    
    /* -------------------------------------------------- Target Types -------------------------------------------------- */
    
    private static final @Nonnull ImmutableSet<Class<?>> targetTypes = ImmutableSet.with(byte.class, short.class, int.class, long.class, float.class, double.class, BigInteger.class, Numerical.class);
    
    @Pure
    @Override
    public @Nonnull ImmutableSet<Class<?>> getTargetTypes() {
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
    public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull TypeImporter typeImporter) {
        if (ProcessingUtility.isAssignable(element, Numerical.class)) {
            return Contract.with("# == null || #.getValue().compareTo(" + typeImporter.importIfPossible(BigInteger.class) + ".ZERO) " + getComparisonOperator() + " 0", "The # has to be null or " + getDecamelizedAnnotationName().replace(" ", "-") + " but was $.", element);
        } else if (ProcessingUtility.isAssignable(element, BigInteger.class)) {
            return Contract.with("# == null || #.compareTo(" + typeImporter.importIfPossible(BigInteger.class) + ".ZERO) " + getComparisonOperator() + " 0", "The # has to be null or " + getDecamelizedAnnotationName().replace(" ", "-") + " but was $.", element);
        } else {
            return Contract.with("# " + getComparisonOperator() + " 0", "The # has to be " + getDecamelizedAnnotationName().replace(" ", "-") + " but was $.", element);
        }
    }
    
}
