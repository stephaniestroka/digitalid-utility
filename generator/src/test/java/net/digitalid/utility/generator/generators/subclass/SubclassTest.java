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
package net.digitalid.utility.generator.generators.subclass;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.testing.UtilityTest;
import net.digitalid.utility.validation.annotations.generation.Default;
import net.digitalid.utility.validation.annotations.generation.Recover;
import net.digitalid.utility.validation.annotations.type.Immutable;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Immutable
@GenerateSubclass
interface SubclassedInterface {
    
    @Pure
    public boolean isFlag();
    
    @Pure
    public int getSize();
    
    @Pure
    public String getText();
    
}

@Immutable
@GenerateSubclass
abstract class SubclassedClass {
    
    @Pure
    public abstract boolean isFlag();
    
    @Pure
    public abstract int getSize();
    
    @Pure
    public abstract String getText();
    
}

@Immutable
@GenerateSubclass
abstract class ClassWithMultipleConstructors {
    
    final int number;
    
    ClassWithMultipleConstructors() {
        number = 0;
    }
    
    @Recover
    ClassWithMultipleConstructors(@Default("1") int number) {
        this.number = number;
    }
    
}

public class SubclassTest extends UtilityTest {
    
    @Test
    public void testInterfaceSubclass() {
        final @Nonnull SubclassedInterfaceSubclass object = new SubclassedInterfaceSubclass(true, 1234, "hi");
        assertEquals(true, object.isFlag());
        assertEquals(1234, object.getSize());
        assertEquals("hi", object.getText());
    }
    
    @Test
    public void testClassSubclass() {
        final @Nonnull SubclassedClassSubclass object = new SubclassedClassSubclass(true, 1234, "hi");
        assertEquals(true, object.isFlag());
        assertEquals(1234, object.getSize());
        assertEquals("hi", object.getText());
    }
    
    @Test
    public void testClassWithMultipleConstructors() {
        final @Nonnull ClassWithMultipleConstructorsSubclass object = new ClassWithMultipleConstructorsSubclass(2);
        assertEquals(2, object.number);
    }
    
}
