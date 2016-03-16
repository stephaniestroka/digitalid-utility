package net.digitalid.utility.generator.interceptor;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.validation.validator.AnnotationHandler;

public class MethodInterceptor extends AnnotationHandler {
    
    public Object invoke(@Nonnull MethodInvocation methodInvocation) throws Throwable {
        return null;
    }
    
    @Override public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror) {
        
    }
}
