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
package net.digitalid.utility.generator.archive;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateConverter;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.rootclass.RootClass;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.size.MaxSize;

@GenerateBuilder
@GenerateSubclass
@GenerateConverter
public abstract class ComplexClass extends RootClass {
    
    public @Nonnull @MaxSize(256) String text;
    
    @Pure
    protected abstract int getValue();
    
    @Pure
    protected abstract @Nonnull String getNonnullString();
    
    @Pure
    protected abstract @Nullable String getNullableString();
    
    @Pure
    public abstract @Nonnull SimpleClass getSimpleClass();
    
    @Pure
    public abstract @Nonnull List<Integer> getListOfIntegers();
    
    @Pure
    public abstract @Nonnull Set<SimpleClass> getSetOfSimpleClass();
    
    @Pure
    public abstract @Nonnull String[] getArrayOfStrings();
    
    @Pure
    protected abstract @Nonnull @NonNullableElements List<@Nonnull String> getListWithNonnullStrings();
    
    @Pure
    protected abstract @Nonnull @NullableElements List<@Nullable String> getListWithNullableStrings();
    
    @Pure
    protected abstract @Nonnull @NonNullableElements Map<@Nonnull String, @Nonnull String> getMapWithNonnullStrings();
    
    @Pure
    protected abstract @Nonnull @NullableElements Map<@Nullable String, @Nullable String> getMapWithNullableStrings();
    
    public ComplexClass(@Nonnull String text) {
        this.text = text;
    }
    
    @Impure
    public void corruptMethod() {
        text = null;
    }
    
    @Pure
    public int getHashCode() {
        return hashCode();
    }
    
}
