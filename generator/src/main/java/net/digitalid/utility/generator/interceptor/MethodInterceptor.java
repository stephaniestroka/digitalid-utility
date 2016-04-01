package net.digitalid.utility.generator.interceptor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.processor.generator.JavaFileGenerator;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.processing.utility.TypeImporter;
import net.digitalid.utility.validation.validator.MethodAnnotationValidator;

public abstract class MethodInterceptor extends MethodAnnotationValidator {
    
    @Override
    public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull TypeImporter typeImporter) {
        return Contract.with("true", "The universe got hacked if this is not true anymore.");
    }
    @Pure
    @Override
    public @Nonnull Class<?> getReceiverType() {
        return Object.class;
    }
    
    @Pure
    protected abstract  @Nonnull String getPrefix();
    
    /**
     * Implements an interceptor method. The method is required to implement the call to the given statement. It can wrap its own functionality around the statement.
     */
    @Pure
    protected abstract void implementInterceptorMethod(@Nonnull JavaFileGenerator javaFileGenerator, @Nonnull MethodInformation method, @Nonnull String statement, @Nullable String resultVariable);
    
    /**
     * Generates an interceptor method that wraps the original method, which information is kept in the method information object, around an interceptor method, which is implemented in a subclass of the {@link MethodInterceptor}. 
     * Returns a statement with a call to the method that is generated.
     */
    @Pure
    public @Nonnull String generateInterceptorMethod(@Nonnull JavaFileGenerator javaFileGenerator, @Nonnull MethodInformation method, @Nonnull String statement, @Nullable String resultVariable) {
        MethodUtility.generateBeginMethod(javaFileGenerator, method, getPrefix(), resultVariable);
        implementInterceptorMethod(javaFileGenerator, method, statement, resultVariable);
        if (resultVariable != null && method.hasReturnType()) {
            javaFileGenerator.addStatement("return " + resultVariable);
        }
        javaFileGenerator.endMethod();
        return MethodUtility.createMethodCall(method, getPrefix(), resultVariable);
    }
    
}
