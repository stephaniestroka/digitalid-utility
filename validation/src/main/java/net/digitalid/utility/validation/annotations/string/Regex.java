package net.digitalid.utility.validation.annotations.string;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processing.utility.TypeImporter;
import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.processing.ErrorLogger;
import net.digitalid.utility.validation.validators.StringValidator;

/**
 * This annotation indicates that a string matches the given regular expression.
 */
@Documented
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
@ValueValidator(Regex.Validator.class)
public @interface Regex {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the regular expression which the annotated string matches.
     */
    @Nonnull String value();
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator extends StringValidator {
        
        @Pure
        @Override
        public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull ErrorLogger errorLogger) {
            super.checkUsage(element, annotationMirror, errorLogger);
            
            final @Nullable String regex = ProcessingUtility.getString(ProcessingUtility.getAnnotationValue(annotationMirror));
            if (regex != null) {
                try {
                    Pattern.compile(regex);
                } catch (@Nonnull PatternSyntaxException exception) {
                    errorLogger.log("The regular expression $ is invalid because of: " + exception.getDescription(), SourcePosition.of(element, annotationMirror), regex);
                }
            }
        }
        
        /**
         * Returns whether the given string matches the given regular expression.
         */
        @Pure
        public static boolean validate(@Nonnull String regex, @Nullable String string) {
            if (string == null) { return true; }
            else { return string.matches(regex); }
        }
        
        @Pure
        @Override
        public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull TypeImporter typeImporter) {
            return Contract.with(typeImporter.importIfPossible(Regex.class) + ".Validator.validate(\"@\", #)", "The # has to match the regular expression '@' but was $.", element, annotationMirror);
        }
        
    }
    
}
