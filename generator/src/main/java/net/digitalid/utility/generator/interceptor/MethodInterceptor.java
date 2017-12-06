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
package net.digitalid.utility.generator.interceptor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.collaboration.annotations.TODO;
import net.digitalid.utility.collaboration.enumerations.Author;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.generator.information.type.TypeInformation;
import net.digitalid.utility.processor.generator.JavaFileGenerator;
import net.digitalid.utility.validation.validator.MethodAnnotationValidator;

@TODO(task = "The inheritance is wrong. MethodAnnotationValidator was intended for method preconditions, not for interceptors. The first two methods can then also be removed. [This part is done by now.]", date = "2016-05-16", author = Author.KASPAR_ETTER)
public abstract class MethodInterceptor implements MethodAnnotationValidator {
    
    @Pure
    protected @Nonnull String getPrefix() { return "implemented"; }
    
    @Pure
    public void generateFieldsRequiredByMethod(@Nonnull JavaFileGenerator javaFileGenerator, @Nonnull MethodInformation method, @Nonnull TypeInformation typeInformation) {}
        
    /**
     * Implements an interceptor method. The method is required to implement the call to the given statement. It can wrap its own functionality around the statement.
     */
    @Pure
    protected abstract void implementInterceptorMethod(@Nonnull JavaFileGenerator javaFileGenerator, @Nonnull MethodInformation method, @Nonnull String statement, @Nullable String resultVariable, @Nullable String defaultValue);
    
    /**
     * Generates an interceptor method that wraps the original method, which information is kept in the method information object, around an interceptor method, which is implemented in a subclass of the {@link MethodInterceptor}. 
     * Returns a statement with a call to the method that is generated.
     */
    @Pure
    public @Nonnull String generateInterceptorMethod(@Nonnull JavaFileGenerator javaFileGenerator, @Nonnull MethodInformation method, @Nonnull String statement, @Nullable String resultVariable, @Nullable String defaultValue) {
        MethodUtility.generateBeginMethod(javaFileGenerator, method, getPrefix());
        implementInterceptorMethod(javaFileGenerator, method, statement, resultVariable, defaultValue);
        javaFileGenerator.endMethod();
        return MethodUtility.createMethodCall(method, getPrefix());
    }
    
}
