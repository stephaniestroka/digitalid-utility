/*
 * Copyright (C) 2017 Synacts GmbH, Switzerland (info@synacts.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.digitalid.utility.generator.annotations.interceptors;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.meta.Interceptor;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.generator.interceptor.MethodInterceptor;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.processor.generator.JavaFileGenerator;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * This annotation indicates that every call to the annotated method is logged with the given level.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Interceptor(Logged.Interceptor.class)
public @interface Logged {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the level at which the method call is logged.
     */
    @Nonnull Level value() default Level.VERBOSE;
    
    /* -------------------------------------------------- Interceptor -------------------------------------------------- */
    
    /**
     * This class generates content for the annotated method.
     */
    @Stateless
    public static class Interceptor extends MethodInterceptor {
        
        @Pure
        @Override
        protected @Nonnull String getPrefix() {
            return "logged";
        }
        
        @Pure
        @Override
        protected void implementInterceptorMethod(@Nonnull JavaFileGenerator javaFileGenerator, @Nonnull MethodInformation method, @Nonnull String statement, @Nullable String resultVariable, @Nullable String defaultValue) {
            if (resultVariable != null && method.hasReturnType()) {
                javaFileGenerator.addStatement(method.getReturnType(javaFileGenerator) + " " + resultVariable + " = " + defaultValue);
            }
            javaFileGenerator.beginTry();
            javaFileGenerator.addStatement(javaFileGenerator.importIfPossible(Log.class) + ".verbose(\"" + method.getName() + "() {'\")");
            if (resultVariable != null && method.hasReturnType()) {
                javaFileGenerator.addStatement(resultVariable + " = " + statement);
            } else {
                javaFileGenerator.addStatement(statement);
            }
            javaFileGenerator.endTryOrCatchBeginFinally();
            if (resultVariable != null && method.hasReturnType()) {
                javaFileGenerator.addStatement(javaFileGenerator.importIfPossible(Log.class) + ".verbose(\"} = (\" + " + resultVariable + " + \")\")");
            } else {
                javaFileGenerator.addStatement(javaFileGenerator.importIfPossible(Log.class) + ".verbose(\"}\")");
            }
            javaFileGenerator.endFinally();
            if (resultVariable != null && method.hasReturnType()) {
                javaFileGenerator.addStatement("return " + resultVariable);
            }
        }
    
    }
    
}
