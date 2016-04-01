package net.digitalid.utility.generator;

import java.util.Arrays;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleTypeVisitor7;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.processor.generator.JavaFileGenerator;
import net.digitalid.utility.string.StringCase;
import net.digitalid.utility.tuples.Triplet;

/**
 * When using this type visitor, you need to call {@link javax.lang.model.util.AbstractTypeVisitor6#visit(TypeMirror, Object)} with a {@link Triplet triplet} of the left reference, the right reference and a {@link JavaFileGenerator java file generator}. 
 * The {@link javax.lang.model.util.AbstractTypeVisitor6#visit(TypeMirror)} method cannot create the reference names nor a java file generator.
 */
public class GenerateComparisonTypeVisitor extends SimpleTypeVisitor7<Object, Triplet<@Nonnull String, @Nonnull String, @Nonnull JavaFileGenerator>> {
    
    @Override
    protected Object defaultAction(TypeMirror e, Triplet<String, String, JavaFileGenerator> triplet) {
        Require.that(triplet != null).orThrow("The java file generator is a required parameter and cannot be generated on the fly. Please call visit(TypeMirror, Triplet<String, String, JavaFileGenerator>) instead.");
        assert triplet != null;
    
        final @Nonnull String leftReference = triplet.get0();
        final @Nonnull String rightReference = triplet.get1();
        final @Nonnull JavaFileGenerator fileGenerator = triplet.get2();
    
        fileGenerator.addStatement(fileGenerator.importIfPossible(Objects.class) + ".equals(" + leftReference + ", " + rightReference + ")");
        return null;
    }
    
    @Override 
    public Object visitArray(@Nonnull ArrayType t, @Nullable Triplet<@Nonnull String, @Nonnull String, @Nonnull JavaFileGenerator> triplet) {
        Require.that(triplet != null).orThrow("The java file generator is a required parameter and cannot be generated on the fly. Please call visit(TypeMirror, Triple<String, String, JavaFileGenerator>) instead.");
        assert triplet != null;
        
        final @Nonnull String leftReference = triplet.get0();
        final @Nonnull String rightReference = triplet.get1();
        final @Nonnull JavaFileGenerator fileGenerator = triplet.get2();
        if (t.getComponentType().getKind().isPrimitive()) {
            fileGenerator.addStatement(fileGenerator.importIfPossible(Arrays.class) + ".equals(" + leftReference + ", " + rightReference + ")");
        } else {
            fileGenerator.addStatement(fileGenerator.importIfPossible(Arrays.class) + ".deepEquals(" + leftReference + ", " + rightReference + ")");
        }
        return null;
    }
    
}
