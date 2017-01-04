package net.digitalid.utility.validation.validators;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.interfaces.Countable;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processing.utility.TypeImporter;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 * This class implements common methods for all size-related validators.
 * 
 * @see net.digitalid.utility.validation.annotations.size
 */
@Stateless
public abstract class SizeValidator implements ValueAnnotationValidator {
    
    /* -------------------------------------------------- Target Types -------------------------------------------------- */
    
    private static final @Nonnull FiniteIterable<@Nonnull Class<?>> targetTypes = FiniteIterable.of(CharSequence.class, Collection.class, Map.class, Countable.class, Object[].class, boolean[].class, char[].class, byte[].class, short[].class, int[].class, long[].class, float[].class, double[].class);
    
    @Pure
    @Override
    public @Nonnull FiniteIterable<@Nonnull Class<?>> getTargetTypes() {
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
    public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull TypeImporter typeImporter) {
        if (ProcessingUtility.isRawSubtype(element, CharSequence.class)) {
            return generateContract("# == null || #.length() " + getSizeComparison(), "The length of the # has to be " + getMessageCondition() + " but was $.", element, annotationMirror, ".length()");
        } else if (ProcessingUtility.getType(element).getKind() == TypeKind.ARRAY) {
            return generateContract("# == null || #.length " + getSizeComparison(), "The length of the # has to be " + getMessageCondition() + " but was $.", element, annotationMirror, ".length");
        } else {
            return generateContract("# == null || #.size() " + getSizeComparison(), "The size of the # has to be " + getMessageCondition() + " but was $.", element, annotationMirror, ".size()");
        }
    }
    
}
