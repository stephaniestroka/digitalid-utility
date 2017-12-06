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
package net.digitalid.utility.generator.generators.converter;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.generator.annotations.generators.GenerateTableConverter;
import net.digitalid.utility.storage.enumerations.ForeignKeyAction;
import net.digitalid.utility.storage.interfaces.Unit;
import net.digitalid.utility.testing.UtilityTest;

import org.junit.Test;

@GenerateSubclass
@GenerateTableConverter(columns = "key", onDelete = ForeignKeyAction.SET_NULL)
interface ClassForTable {
    
    @Pure
    public long getKey();
    
    @Pure
    public @Nonnull String getValue();
    
}

public class TableConverterTest extends UtilityTest {
    
    @Test
    public void testColumnNames() {
        assertThat(ClassForTableConverter.INSTANCE.getColumnNames(Unit.DEFAULT)).containsExactly("key");
    }
    
    @Test
    public void testOnDeleteAction() {
        assertThat(ClassForTableConverter.INSTANCE.getOnDeleteAction()).isEqualTo(ForeignKeyAction.SET_NULL);
    }
    
}
