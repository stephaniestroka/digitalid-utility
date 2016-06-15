package net.digitalid.utility.generator.annotations.interceptors;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.generator.annotations.meta.Interceptor;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.generator.interceptor.MethodInterceptor;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.processing.logging.ErrorLogger;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processor.generator.JavaFileGenerator;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * This annotation indicates that any exception of the given type is caught and logged.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Interceptor(Caught.Interceptor.class)
public @interface Caught {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the type of exceptions which are caught and logged.
     */
    @Nonnull Class<? extends Exception> value() default Exception.class;
    
    /* -------------------------------------------------- Interceptor -------------------------------------------------- */
    
    /**
     * This class generates the code for the annotated method.
     */
    @Stateless
    public static class Interceptor extends MethodInterceptor {
        
        @Pure
        @Override
        public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull ErrorLogger errorLogger) {
            // TODO: Yes it is! :-) // errorLogger.log("Is the usage even checked?", SourcePosition.of(element, annotationMirror));
            // TODO: Check that this annotation is only used on methods without a return type or reference type that is nullable.
        }
        
        @Pure
        @Override
        protected @Nonnull String getPrefix() {
            return "caught";
        }
        
        @Pure
        @Override
        // TODO: Why is the result variable and a default value passed here? The annotation mirror would be more useful...
        protected void implementInterceptorMethod(@Nonnull JavaFileGenerator javaFileGenerator, @Nonnull MethodInformation method, @Nonnull String statement, @Nullable String resultVariable, @Nullable String defaultValue) {
            ProcessingLog.warning("1: " + javaFileGenerator.getCodeBlockStackAsString());
            javaFileGenerator.beginTry();
            ProcessingLog.warning("2: " + javaFileGenerator.getCodeBlockStackAsString());
            javaFileGenerator.addStatement((method.hasReturnType() ? "return " : "") + statement);
            javaFileGenerator.endTryOrCatchBeginCatch(Exception.class); // TODO: Read this from the annotation mirror!
            ProcessingLog.warning("3: " + javaFileGenerator.getCodeBlockStackAsString());
            javaFileGenerator.addStatement(javaFileGenerator.importIfPossible(Log.class) + ".error(\"A problem occurred.\", exception)");
            if (method.hasReturnType()) { javaFileGenerator.addStatement("return null"); }
            javaFileGenerator.endCatch();
            ProcessingLog.warning("4: " + javaFileGenerator.getCodeBlockStackAsString());
        }
        
    }
    
}
