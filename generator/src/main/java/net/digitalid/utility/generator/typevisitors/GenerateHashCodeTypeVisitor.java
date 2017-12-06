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
package net.digitalid.utility.generator.typevisitors;

import java.util.Arrays;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleTypeVisitor7;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.processor.generator.JavaFileGenerator;
import net.digitalid.utility.tuples.Triplet;

/**
 * A hash code type visitor which implements the hash code method generation for a type.
 */
public class GenerateHashCodeTypeVisitor extends SimpleTypeVisitor7<@Nonnull Object, @Nullable Triplet<@Nonnull String, @Nonnull JavaFileGenerator, @Nonnull String>> {
    
    @Pure
    @Override
    protected @Nullable Object defaultAction(@Nonnull TypeMirror e, @Nullable @Modified Triplet<@Nonnull String, @Nonnull JavaFileGenerator, @Nonnull String> triplet) {
        Require.that(triplet != null).orThrow("The java file generator is a required parameter and cannot be generated on the fly. Please call visit(TypeMirror, Triplet<String, JavaFileGenerator, String>) instead.");
        assert triplet != null;
    
        final @Nonnull String object = triplet.get0();
        final @Nonnull JavaFileGenerator fileGenerator = triplet.get1();
        final @Nonnull String result = triplet.get2();
    
        fileGenerator.addStatement(result + " = prime * " + result + " + " + fileGenerator.importIfPossible(Objects.class) + ".hashCode(" + object + ")");
        return null;
    }
    
    @Pure
    @Override 
    public Object visitArray(@Nonnull ArrayType t, @Nullable @Modified Triplet<@Nonnull String, @Nonnull JavaFileGenerator, @Nonnull String> triplet) {
        Require.that(triplet != null).orThrow("The java file generator is a required parameter and cannot be generated on the fly. Please call visit(TypeMirror, Triplet<String, JavaFileGenerator, String>) instead.");
        assert triplet != null;
        
        final @Nonnull String object = triplet.get0();
        final @Nonnull JavaFileGenerator fileGenerator = triplet.get1();
        final @Nonnull String result = triplet.get2();
        
        if (t.getComponentType().getKind().isPrimitive()) {
            fileGenerator.addStatement(result + " = prime * " + result + " + " + fileGenerator.importIfPossible(Arrays.class) + ".hashCode(" + object + ")");
        } else {
            fileGenerator.addStatement(result + " = prime * " + result + " + " + fileGenerator.importIfPossible(Arrays.class) + ".deepHashCode(" + object + ")");
        }
        return null;
    }
    
}
