package net.digitalid.utility.generator.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.generator.interceptor.MethodInterceptor;
import net.digitalid.utility.generator.interceptor.MethodInvocation;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.string.QuoteString;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.processing.TypeImporter;

/**
 * 
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Interceptor(Logged.Interceptor.class)
public @interface Logged {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class generates content for the annotated method.
     */
    public static class Interceptor implements MethodInterceptor {
    
        @Override
        public @Nullable Object invoke(@Nonnull MethodInvocation methodInvocation) throws Throwable {
            Log.verbose(QuoteString.inDouble("The method " + methodInvocation.getMethod().getName() + " was called."));
            return null;
        }
        
    }
    
}
