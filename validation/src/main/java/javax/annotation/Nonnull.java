package javax.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.validation.annotations.meta.Generator;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.generator.ContractGenerator;
import net.digitalid.utility.validation.processing.TypeImporter;

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
@Generator(Nonnull.Generator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
public @interface Nonnull {
    
    @Stateless
    public static class Generator extends ContractGenerator {
        
        @Pure
        @Override
        public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull TypeImporter typeImporter) {
            return Contract.with("# != null", "The # may not be null.", element);
        }
        
    }
    
}
