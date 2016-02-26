package javax.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.validation.annotations.meta.Validator;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.processing.TypeImporter;
import net.digitalid.utility.validation.validator.AnnotationValidator;
import net.digitalid.utility.validation.validator.GeneratedContract;

/**
 * This annotation indicates that the annotated reference is not null.
 * 
 * Ideally, this annotation would be in {@code net.digitalid.utility.validation.reference} and be
 * called NonNullable. However, we stick to this package and name because of better tool support.
 * 
 * @see Nullable
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Validator(Nonnull.Validator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
public @interface Nonnull {
    
    @Stateless
    public static class Validator extends AnnotationValidator {
        
        @Pure
        @Override
        public @Nonnull GeneratedContract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull TypeImporter typeImporter) {
            return GeneratedContract.with("# != null", "The # may not be null.", element);
        }
        
    }
    
}
