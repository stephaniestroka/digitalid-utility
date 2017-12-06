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
package net.digitalid.utility.generator.generators.builder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.testing.UtilityTest;
import net.digitalid.utility.validation.annotations.generation.Default;
import net.digitalid.utility.validation.annotations.generation.Recover;
import net.digitalid.utility.validation.annotations.type.Immutable;

import org.junit.Test;

@Immutable
abstract class AbstractFields {
    
    private final String first;
    
    @Pure
    public String getFirst() {
        return first;
    }
    
    private final String second;
    
    @Pure
    public String getSecond() {
        return second;
    }
    
    protected AbstractFields(String first, String second) {
        this.first = first;
        this.second = second;
    }
    
}

@Immutable
@GenerateBuilder
class MandatoryFields extends AbstractFields {
    
    protected MandatoryFields(@Nonnull String first, @Nonnull String second) {
        super(first, second);
    }
    
}

@Immutable
@GenerateBuilder
class OptionalFields extends AbstractFields {
    
    protected OptionalFields(@Nullable String first, @Nullable String second) {
        super(first, second);
    }
    
}

@Immutable
@GenerateBuilder
class MixedFields extends AbstractFields {
    
    protected MixedFields(@Nullable String first, @Nonnull String second) {
        super(first, second);
    }
    
}

@Immutable
@GenerateBuilder
class ClassWithMultipleConstructors {
    
    final int number;
    
    ClassWithMultipleConstructors() {
        number = 0;
    }
    
    @Recover
    ClassWithMultipleConstructors(@Default("1") int number) {
        this.number = number;
    }
    
}

@Immutable
@GenerateBuilder
class ClassWithGenericTypes<K, V> {
    
    final K number;
    
    @Recover
    ClassWithGenericTypes(K number) {
        this.number = number;
    }
    
}

@Immutable
@GenerateBuilder
class ClassWithGenericTypesWithBounds<K extends Number> {
    
    final K number;
    
    @Recover
    ClassWithGenericTypesWithBounds(K number) {
        this.number = number;
    }
    
}

public class BuilderTest extends UtilityTest {
    
    @Pure
    private void testFields(@Nonnull AbstractFields fields, @Nullable String first, @Nullable String second) {
        assertThat(fields.getFirst()).isEqualTo(first);
        assertThat(fields.getSecond()).isEqualTo(second);
    }
    
    @Test
    public void testMandatoryFields() {
        testFields(MandatoryFieldsBuilder.withFirst("alpha").withSecond("beta").build(), "alpha", "beta");
    }
    
    @Test
    public void testOptionalFields() {
        testFields(OptionalFieldsBuilder.build(), null, null);
        testFields(OptionalFieldsBuilder.withFirst("alpha").build(), "alpha", null);
        testFields(OptionalFieldsBuilder.withSecond("beta").build(), null, "beta");
        testFields(OptionalFieldsBuilder.withSecond("beta").withFirst("alpha").build(), "alpha", "beta");
    }
    
    @Test
    public void testMixedFields() {
        testFields(MixedFieldsBuilder.withSecond("beta").build(), null, "beta");
        testFields(MixedFieldsBuilder.withSecond("beta").withFirst("alpha").build(), "alpha", "beta");
    }
    
    @Test
    public void testClassWithMultipleConstructors() {
        // TODO: test
    }
    
    @Test
    public void testClassWithGenericTypes() {
        // TODO: test
    }
}
