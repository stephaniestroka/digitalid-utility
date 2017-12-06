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
package net.digitalid.utility.generator.special;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.processing.Processor;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.rootclass.RootClass;

abstract class GenericClass<G extends GenericClass<G>> extends RootClass {
    
    @Pure
    public <T> T test(@Nonnull T object) {
        return object;
    }
    
    @Pure
    public G[] getGenericFields() {
        return null;
    }
    
    @Impure
    public void setGenericField(@Nonnull G genericClass) {}
    
    @Pure
    public <T extends GenericClass<?>> T getFieldWithUpperBound() {
        return null;
    }
    
    @Pure
    public void addNumbers(List<? super Integer> list) {
        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }
    }
    
    @Pure
    public static @Nonnull <G extends GenericClass<G>> GenericClass<G> with() {
// TODO:       return new GeneratedGenericClass<>();
        return null;
    }
    
    GenericClass() {
        
    }
    
}

abstract class ExtendingGenericClass extends GenericClass<ExtendingGenericClass> {
    
    ExtendingGenericClass() {
        
    }
    
}

abstract class SimpleGenericClass<P extends Processor> extends RootClass {
    
    @Pure
    public abstract @Nonnull P getProcessor();
    
    public SimpleGenericClass() {
        
    }
    
    @Pure
    public void addTo(List<? super Processor> list) {
        list.add(getProcessor());
    }
    
}


public class GenericsTest {
    
    // Test direct, indirect and bounded generics.
    
}
