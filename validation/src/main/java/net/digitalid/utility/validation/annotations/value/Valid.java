package net.digitalid.utility.validation.annotations.value;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.functional.interfaces.Predicate;
import net.digitalid.utility.processing.logging.ErrorLogger;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processing.utility.TypeImporter;
import net.digitalid.utility.validation.annotations.generation.Default;
import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 * This annotation indicates that the annotated value is valid.
 */
@Documented
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
@ValueValidator(Valid.Validator.class)
public @interface Valid {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * This interface encapsulates a value which is validated by the given validator.
     */
    @Functional
    public static interface Value<V> {
        
        /**
         * Returns the validator which validates the encapsulated value(s).
         */
        @Pure
        @Default("object -> true")
        public @Nonnull Predicate<? super V> getValueValidator();
        
    }
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator implements ValueAnnotationValidator {
        
        @Pure
        @Override
        public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull ErrorLogger errorLogger) {
            final @Nonnull TypeElement surroundingType = ProcessingUtility.getSurroundingType(element);
            if (!ProcessingUtility.isRawSubtype(surroundingType, Valid.Value.class)) {
                final @Nullable ExecutableElement isValidMethod = ProcessingUtility.getNonPrivateMethod(surroundingType, "isValid", boolean.class, ProcessingUtility.getClass(ProcessingUtility.getType(element)));
                if (isValidMethod == null || !isValidMethod.getModifiers().contains(Modifier.PUBLIC) || !isValidMethod.getModifiers().contains(Modifier.STATIC)) {
                    errorLogger.log("The annotation '@Valid' may only be used in types that have a 'public static boolean isValid(value)' method or implement the Valid.Value interface:", SourcePosition.of(element, annotationMirror));
                }
            }
        }
        
        @Pure
        @Override
        public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull TypeImporter typeImporter) {
            final @Nonnull TypeElement surroundingType = ProcessingUtility.getSurroundingType(element);
            if (ProcessingUtility.isRawSubtype(surroundingType, Valid.Value.class)) {
                return Contract.with("# == null || getValueValidator().evaluate(#)", "The # has to be null or valid but was $.", element);
            } else if (!ProcessingUtility.getType(element).getKind().isPrimitive()) {
                return Contract.with("# == null || " + typeImporter.importIfPossible(ProcessingUtility.getSurroundingType(element)) + ".isValid(#)", "The # has to be null or valid but was $.", element);
            } else {
                return Contract.with(typeImporter.importIfPossible(ProcessingUtility.getSurroundingType(element)) + ".isValid(#)", "The # has to be valid but was $.", element);
            }
        }
        
    }
    
}
