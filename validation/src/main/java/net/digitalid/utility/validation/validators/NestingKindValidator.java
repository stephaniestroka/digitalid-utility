package net.digitalid.utility.validation.validators;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;

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
 * This class generates the contracts for the kinds of nesting.
 * 
 * @see net.digitalid.utility.validation.annotations.type.nesting
 */
@Stateless
public abstract class NestingKindValidator implements ValueAnnotationValidator {
    
    /* -------------------------------------------------- Target Types -------------------------------------------------- */
    
    private static final @Nonnull FiniteIterable<@Nonnull Class<?>> targetTypes = FiniteIterable.of(Class.class, TypeElement.class);
    
    @Pure
    @Override
    public @Nonnull FiniteIterable<@Nonnull Class<?>> getTargetTypes() {
        return targetTypes;
    }
    
    /* -------------------------------------------------- Kind -------------------------------------------------- */
    
    /**
     * Returns the nesting kind which the type has to have.
     */
    @Pure
    public abstract @Nonnull NestingKind getKind();
    
    /* -------------------------------------------------- Condition -------------------------------------------------- */
    
    /**
     * Returns the condition for the given element depending on the type of the element.
     */
    @Pure
    public static @Nonnull String getCondition(@Nonnull Element element, @Nonnull NestingKind kind, @NonCaptured @Modified @Nonnull TypeImporter typeImporter) {
        if (ProcessingUtility.isSubtype(element, Class.class)) {
            switch (kind) {
                case ANONYMOUS: return "#.isAnonymousClass()";
                case LOCAL: return "#.isLocalClass()";
                case MEMBER: return "#.isMemberClass()";
                case TOP_LEVEL: return "!#.isAnonymousClass() && !#.isLocalClass() && !#.isMemberClass()";
                default: return "false";
            }
        } else {
            return "#.getNestingKind() == " + typeImporter.importIfPossible(NestingKind.class) + "." + kind.name();
        }
    }
    
    /* -------------------------------------------------- Contract Generation -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull TypeImporter typeImporter) {
        return Contract.with("# == null || " + getCondition(element, getKind(), typeImporter), "The # has to be null or have the nesting kind '" + getKind().name().toLowerCase().replace("_", "-") + "' but was $.", element);
    }
    
}
