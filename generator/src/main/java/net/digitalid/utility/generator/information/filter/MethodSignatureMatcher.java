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
package net.digitalid.utility.generator.information.filter;

import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.VariableElement;

import net.digitalid.utility.functional.interfaces.Predicate;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;

/**
 * Implements a method signature matcher which is used in queries for method information objects.
 */
public class MethodSignatureMatcher implements Predicate<MethodInformation> {
    
    /**
     * A regular expression that is used to find a method information.
     */
    private final @Nonnull Pattern namePattern;
    
    /**
     * An array of method parameters.
     */
    public final @Nonnull @NonNullableElements String[] parameters;
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new method signature matcher with a given name regex and a list of expected parameters as strings.
     */
    private MethodSignatureMatcher(@Nonnull String nameRegex, @Nullable @NonNullableElements String... parameters) {
        this.namePattern = Pattern.compile(nameRegex);
        if (parameters == null) {
            this.parameters = new String[0];
        } else {
            this.parameters = parameters;
        }
    }
    
    /**
     * Returns a method signature matcher with a given name regex and a list of expected parameters as classes.
     */
    public static @Nonnull MethodSignatureMatcher of(@Nonnull String nameRegex, @Nullable @NonNullableElements Class<?>... parameters) {
        final @Nonnull String[] typeNames;
        if (parameters == null) {
            typeNames = new String[0];
        } else {
            typeNames = new String[parameters.length];
            int i = 0;
            for (@Nonnull Class<?> parameter : parameters) {
                typeNames[i] = parameter.getCanonicalName();
                i++;
            }
        }
        return new MethodSignatureMatcher(nameRegex, typeNames);
    }
    
    /**
     * Returns a method signature matcher for a given regular expression that matches the method name and a list of parameters as strings.
     */
    public static @Nonnull MethodSignatureMatcher of(@Nonnull String nameRegex, @Nullable @NonNullableElements String... parameters) {
        return new MethodSignatureMatcher(nameRegex, parameters);
    }
    
    /**
     * Returns a method signature matcher for a given regular expression that matches the method name.
     */
    public static @Nonnull MethodSignatureMatcher of(@Nonnull String nameRegex) {
        return new MethodSignatureMatcher(nameRegex, null);
    }
    
    /* -------------------------------------------------- Matcher -------------------------------------------------- */
    
    /**
     * Checks whether the given object matches this method signature.
     */
    @Override
    public boolean evaluate(@Nonnull MethodInformation methodInformation) {
        boolean matches = namePattern.matcher(methodInformation.getName()).matches();
        final @Nonnull @NonNullableElements List<? extends VariableElement> methodParameters = methodInformation.getElement().getParameters();
        if (parameters.length == methodParameters.size()) {
            for (int i = 0; i < methodParameters.size(); i++) {
                final @Nonnull String nameOfDeclaredType = ProcessingUtility.getQualifiedName(methodParameters.get(i).asType());
                ProcessingLog.debugging("name of type: $", nameOfDeclaredType);
                final @Nonnull String parameter = parameters[i];
                if (!parameter.equals("?")) {
                    matches = matches && nameOfDeclaredType.equals(parameter);
                }
            }
        } else {
            matches = false;
        }
        return matches;
    }
    
}
