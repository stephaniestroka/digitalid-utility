package net.digitalid.utility.validation.generators;

import java.math.BigInteger;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.generator.ContractGenerator;
import net.digitalid.utility.validation.interfaces.Numerical;
import net.digitalid.utility.validation.processing.ProcessingUtility;
import net.digitalid.utility.validation.processing.TypeImporter;

/**
 * This class generates the contracts for numerical annotations.
 * 
 * @see net.digitalid.utility.validation.annotations.math
 */
@Stateless
public abstract class NumericalContractGenerator extends ContractGenerator {
    
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
            return Contract.with("# == null || #.getValue().compareTo(" + typeImporter.importIfPossible(BigInteger.class) + ".ZERO) " + getComparisonOperator() + " 0", "The # has to be null or " + getDecamelizedName().replace(" ", "-") + " but was $.", element);
        } else if (ProcessingUtility.isAssignable(element, BigInteger.class)) {
            return Contract.with("# == null || #.compareTo(" + typeImporter.importIfPossible(BigInteger.class) + ".ZERO) " + getComparisonOperator() + " 0", "The # has to be null or " + getDecamelizedName().replace(" ", "-") + " but was $.", element);
        } else {
            return Contract.with("# " + getComparisonOperator() + " 0", "The # has to be " + getDecamelizedName().replace(" ", "-") + " but was $.", element);
        }
    }
    
}
