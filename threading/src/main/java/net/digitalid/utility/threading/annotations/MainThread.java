package net.digitalid.utility.threading.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.threading.Threading;
import net.digitalid.utility.validation.annotations.meta.MethodValidator;
import net.digitalid.utility.tuples.annotations.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.processing.TypeImporter;
import net.digitalid.utility.validation.validator.MethodAnnotationValidator;

/**
 * This annotation indicates that a method should only be invoked on the {@link Threading#isMainThread() main thread}.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@MethodValidator(MainThread.Validator.class)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface MainThread {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator extends MethodAnnotationValidator {
        
        @Pure
        @Override
        public @Nonnull Class<?> getReceiverType() {
            return Object.class;
        }
        
        @Pure
        @Override
        public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull TypeImporter typeImporter) {
            return Contract.with(typeImporter.importIfPossible(Threading.class) + ".isMainThread()", "The method # may only be called on the main thread.", element);
        }
        
    }
    
}
