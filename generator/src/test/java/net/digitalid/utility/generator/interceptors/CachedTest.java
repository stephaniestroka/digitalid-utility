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
package net.digitalid.utility.generator.interceptors;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.generator.annotations.interceptors.Cached;
import net.digitalid.utility.testing.UtilityTest;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.validation.annotations.type.Immutable;

import org.junit.Test;

@Immutable
@GenerateSubclass
class CachedMethod {
    
    @Pure
    @Cached
    double method(int input) {
        return Math.random();
    }
    
    @Pure
    @Cached
    double method(double input) {
        return Math.random();
    }
    
    @Pure
    @Cached
    double method(String input1, int input2) {
        return Math.random();
    }
    
    @Pure
    @Cached
    double method(Pair<String, Integer> input) {
        return Math.random();
    }
    
}

public class CachedTest extends UtilityTest {
    
    @Test
    public void testCaching() {
        final @Nonnull CachedMethodSubclass object = new CachedMethodSubclass();
        assertThat(object.method(0) == object.method(0)).isTrue();
        assertThat(object.method(1) == object.method(1)).isTrue();
        assertThat(object.method(0) == object.method(1)).isFalse();
    }
    
}
