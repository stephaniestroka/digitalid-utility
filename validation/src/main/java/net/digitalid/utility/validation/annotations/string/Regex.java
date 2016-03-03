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

import net.digitalid.utility.validation.annotations.meta.Generator;
import net.digitalid.utility.validation.annotations.meta.TargetTypes;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.generator.ContractGenerator;
import net.digitalid.utility.validation.processing.TypeImporter;

/**
 * This annotation indicates that a string matches the given regular expression.
 */
@Documented
@TargetTypes(CharSequence.class)
@Generator(Regex.Generator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
public @interface Regex {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the regular expression which the annotated string matches.
     */
    @Nonnull String value();
    
    /* -------------------------------------------------- Generator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Generator extends ContractGenerator {
        
        /**
         * Returns whether the given string is a valid regular expression.
         */
        @Pure
        public static boolean validate(@Nullable String string) {
            if (string == null) { return true; }
            try {
                Pattern.compile(string);
                return true;
            } catch (@Nonnull PatternSyntaxException exception) {
                return false;
            }
        }
        
        @Pure
        @Override
        public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull TypeImporter typeImporter) {
            return Contract.with(typeImporter.importIfPossible(Regex.class) + ".Generator.validate(#)", "The # has to be a valid regular expression but was $.", element);
        }
        
    }
    
}
