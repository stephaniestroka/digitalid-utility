package net.digitalid.utility.validation.annotations.string;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.processing.utility.TypeImporter;
import net.digitalid.utility.validation.validators.StringValidator;

/**
 * This annotation indicates that a string is a valid identifier in most languages like Java or SQL.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@ValueValidator(CodeIdentifier.Validator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
public @interface CodeIdentifier {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator extends StringValidator {
        
        private static final @Nonnull Pattern PATTERN = Pattern.compile("[a-z_$][a-z0-9_$]*", Pattern.CASE_INSENSITIVE);
        
        /**
         * Returns whether the given string is a valid identifier in most languages like Java or SQL.
         */
        @Pure
        public static boolean validate(@Nullable String string) {
            if (string == null) { return true; }
            return PATTERN.matcher(string).matches();
        }
        
        @Pure
        @Override
        public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull TypeImporter typeImporter) {
            return Contract.with(typeImporter.importIfPossible(CodeIdentifier.class) + ".Validator.validate(#)", "The # has to be a valid code identifier but was $.", element);
        }
        
    }
    
}
