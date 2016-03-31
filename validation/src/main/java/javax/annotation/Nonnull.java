package javax.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.immutable.collections.ImmutableSet;
import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.processing.utility.TypeImporter;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

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
@ValueValidator(Nonnull.Validator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.TYPE_USE})
public @interface Nonnull {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator extends ValueAnnotationValidator {
        
        private static final @Nonnull ImmutableSet<Class<?>> targetTypes = ImmutableSet.<Class<?>>with(Object.class);
        
        @Pure
        @Override
        public @Nonnull ImmutableSet<Class<?>> getTargetTypes() {
            return targetTypes;
        }
        
        @Pure
        @Override
        public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull TypeImporter typeImporter) {
            return Contract.with("# != null", "The # may not be null.", element);
        }
        
    }
    
}
