package net.digitalid.utility.validation.validators;

import java.io.File;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processing.utility.TypeImporter;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 * This class declares the target types for substring annotations.
 * 
 * @see net.digitalid.utility.validation.annotations.substring
 */
@Stateless
public abstract class SubstringValidator implements ValueAnnotationValidator {
    
    /* -------------------------------------------------- Target Types -------------------------------------------------- */
    
    private static final @Nonnull FiniteIterable<@Nonnull Class<?>> targetTypes = FiniteIterable.of(CharSequence.class, File.class);
    
    @Pure
    @Override
    public @Nonnull FiniteIterable<@Nonnull Class<?>> getTargetTypes() {
        return targetTypes;
    }
    
    /* -------------------------------------------------- Abstract Methods -------------------------------------------------- */
    
    /**
     * Returns the name of the method which is used to validate the substring.
     */
    @Pure
    public abstract @Nonnull String getMethodName();
    
    /**
     * Returns the string which is used as a condition in the message of the contract.
     */
    @Pure
    public abstract @Nonnull String getMessageCondition();
    
    /* -------------------------------------------------- Contract Generation -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull TypeImporter typeImporter) {
        if (ProcessingUtility.isRawSubtype(element, CharSequence.class)) {
            return Contract.with("# == null || #." + getMethodName() + "(\"@\")", "The # has to " + getMessageCondition() + " '@' but was $.", element, annotationMirror);
        } else {
            return Contract.with("# == null || #.getAbsoluteFile().getName()." + getMethodName() + "(\"@\")", "The # has to " + getMessageCondition() + " '@' but was $.", element, annotationMirror);
        }
    }
    
}
