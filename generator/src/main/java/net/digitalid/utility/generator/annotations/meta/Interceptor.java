package net.digitalid.utility.generator.annotations.meta;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.generator.interceptor.MethodInterceptor;

/**
 * This meta-annotation links to the interceptor which intercepts calls to methods that are annotated with the annotated annotation.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Interceptor {
    
    /**
     * Returns the interceptor which intercepts calls to methods that are annotated with the annotated annotation.
     */
    @Nonnull Class<? extends MethodInterceptor> value();
    
}