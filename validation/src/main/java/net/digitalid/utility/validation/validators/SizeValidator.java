package net.digitalid.utility.validation.validators;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.processing.ProcessingUtility;
import net.digitalid.utility.validation.processing.TypeImporter;
import net.digitalid.utility.validation.validator.AnnotationValidator;
import net.digitalid.utility.validation.validator.GeneratedContract;

/**
 * This class implements common methods for all size validators.
 * 
 * @see net.digitalid.utility.validation.annotations.size
 */
@Stateless
public abstract class SizeValidator extends AnnotationValidator {
    
    /**
     * Returns the string which is used to compare the size/length of the element.
     */
    @Pure
    public abstract @Nonnull String getSizeComparison();
    
    /**
     * Returns the string which is used as a condition in the message of the contract.
     */
    @Pure
    public abstract @Nonnull String getMessageCondition();
    
    @Pure
    private @Nonnull GeneratedContract generateContract(@Nonnull String condition, @Nonnull String message, @Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull String suffix) {
        if (condition.contains("@")) {
            return GeneratedContract.with(condition, message, element, annotationMirror, suffix);
        } else {
            return GeneratedContract.with(condition, message, element, suffix);
        }
    }
    
    @Pure
    @Override
    public @Nonnull GeneratedContract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull TypeImporter typeImporter) {
        if (ProcessingUtility.isAssignable(element, Object[].class)) {
            return generateContract("# == null || #.length " + getSizeComparison(), "The length of the # has to be " + getMessageCondition() + " but was $.", element, annotationMirror, ".length");
        } else if (ProcessingUtility.isAssignable(element, CharSequence.class)) {
            return generateContract("# == null || #.length() " + getSizeComparison(), "The length of the # has to be " + getMessageCondition() + " but was $.", element, annotationMirror, ".length()");
        } else {
            return generateContract("# == null || #.size() " + getSizeComparison(), "The size of the # has to be " + getMessageCondition() + " but was $.", element, annotationMirror, ".size()");
        }
    }
    
}
