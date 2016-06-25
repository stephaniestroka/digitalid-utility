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
