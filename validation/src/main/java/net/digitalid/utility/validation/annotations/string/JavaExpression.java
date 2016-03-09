package net.digitalid.utility.validation.annotations.string;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.processing.TypeImporter;
import net.digitalid.utility.validation.validators.StringValidator;

/**
 * This annotation indicates that a string is a valid Java expression.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@ValueValidator(JavaExpression.Validator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
public @interface JavaExpression {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator extends StringValidator {
        
        /**
         * Returns whether the given string is a valid Java expression.
         */
        @Pure
        public static boolean validate(@Nullable String string) {
            if (string == null) { return true; }
            // TODO: Do some reasonable checks here.
            return true;
        }
        
        @Pure
        @Override
        public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull TypeImporter typeImporter) {
            return Contract.with(typeImporter.importIfPossible(JavaExpression.class) + ".Validator.validate(#)", "The # has to be a valid Java expression but was $.", element);
        }
        
    }
    
}
