package net.digitalid.utility.validation.validators;

import java.util.Collection;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.immutable.collections.ImmutableSet;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.interfaces.Countable;
import net.digitalid.utility.validation.processing.ProcessingUtility;
import net.digitalid.utility.validation.processing.TypeImporter;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 * This class implements common methods for all size-related validators.
 * 
 * @see net.digitalid.utility.validation.annotations.size
 */
@Stateless
public abstract class SizeValidator extends ValueAnnotationValidator {
    
    /* -------------------------------------------------- Target Types -------------------------------------------------- */
    
    private static final @Nonnull ImmutableSet<Class<?>> targetTypes = ImmutableSet.with(Collection.class, Countable.class, Object[].class, CharSequence.class);
    
    @Pure
    @Override
    public @Nonnull ImmutableSet<Class<?>> getTargetTypes() {
        return targetTypes;
    }
    
    /* -------------------------------------------------- Abstract Methods -------------------------------------------------- */
    
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
    
    /* -------------------------------------------------- Contract Generation -------------------------------------------------- */
    
    @Pure
    private @Nonnull Contract generateContract(@Nonnull String condition, @Nonnull String message, @Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull String suffix) {
        if (condition.contains("@")) {
            return Contract.with(condition, message, element, annotationMirror, suffix);
        } else {
            return Contract.with(condition, message, element, suffix);
        }
    }
    
    @Pure
    @Override
    public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull TypeImporter typeImporter) {
        // TODO: isAssignable() does not yet work for arrays.
        /*if (ProcessingUtility.isAssignable(element, Object[].class)) {
            return generateContract("# == null || #.length " + getSizeComparison(), "The length of the # has to be " + getMessageCondition() + " but was $.", element, annotationMirror, ".length");
        } else */if (ProcessingUtility.isAssignable(element, CharSequence.class)) {
            return generateContract("# == null || #.length() " + getSizeComparison(), "The length of the # has to be " + getMessageCondition() + " but was $.", element, annotationMirror, ".length()");
        } else {
            return generateContract("# == null || #.size() " + getSizeComparison(), "The size of the # has to be " + getMessageCondition() + " but was $.", element, annotationMirror, ".size()");
        }
    }
    
}
