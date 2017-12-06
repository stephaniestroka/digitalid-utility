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
package net.digitalid.utility.generator.values;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.generation.Default;

import org.junit.Assert;
import org.junit.Test;

@GenerateBuilder
@GenerateSubclass
abstract class ClassWithDefaultValueInConstructorParameter {
    
    private final @Nonnull String optionalText;
    
    private final @Nonnull String mandatoryText;
    
    ClassWithDefaultValueInConstructorParameter(@Default("\"blubb\"") String optionalText, @Nonnull String mandatoryText) {
        this.optionalText = optionalText;
        this.mandatoryText = mandatoryText;
    }
    
    @Pure
    public @Nonnull String getOptionalText() {
        return optionalText;
    }
    
}

@GenerateBuilder
@GenerateSubclass
abstract class ClassWithDefaultValueInFields {
    
    @GenerateBuilder
    @GenerateSubclass
    abstract class NestedClass {}
    
    @Pure
    @Default("42")
    public abstract int getIdentity();
    
}

public class DefaultTest {
    
    @Test
    @Pure
    public void testDefaultValues() {
        ClassWithDefaultValueInConstructorParameter classWithDefaultValueInConstructorParameter = ClassWithDefaultValueInConstructorParameterBuilder.withMandatoryText("Bla").build();
        Assert.assertEquals("blubb", classWithDefaultValueInConstructorParameter.getOptionalText());
        Assert.assertEquals(42, ClassWithDefaultValueInFieldsBuilder.build().getIdentity());
    }
    
}
