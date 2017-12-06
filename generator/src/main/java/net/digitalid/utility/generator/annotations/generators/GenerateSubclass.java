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
package net.digitalid.utility.generator.annotations.generators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.generator.generators.SubclassGenerator;
import net.digitalid.utility.validation.annotations.meta.TypeValidator;

/**
 * Marks a class such that the {@link SubclassGenerator subclass generator} generates a subclass for the annotated class.
 * 
 * @see GenerateBuilder
 * @see GenerateConverter
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@TypeValidator(GenerateAnnotationValidator.class)
public @interface GenerateSubclass {
    
    /**
     * Returns whether the generated subclass shall be public.
     */
    @Nonnull boolean makePublic() default false;
    
}
