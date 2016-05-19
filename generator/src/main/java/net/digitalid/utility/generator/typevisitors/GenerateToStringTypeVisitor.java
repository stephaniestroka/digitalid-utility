package net.digitalid.utility.generator.typevisitors;

import java.util.Arrays;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleTypeVisitor7;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.fixes.Quotes;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processor.generator.JavaFileGenerator;
import net.digitalid.utility.tuples.Pair;

/**
 * The type visitor prints character sequence values in double quotes and primitive type values without quotes.
 */
public class GenerateToStringTypeVisitor extends SimpleTypeVisitor7<@Nonnull String, @Nullable Pair<String, JavaFileGenerator>> {
    
    @Pure
    @Override
    protected @Nonnull String defaultAction(@Nonnull TypeMirror type, @Nullable Pair<String, JavaFileGenerator> pair) {
        Require.that(pair.get0() != null).orThrow("The field access code is a required parameter and cannot be generated on the fly. Please call visit(TypeMirror, String) instead.");
        
        return pair.get1().importIfPossible(Quotes.class) + ".inDouble(" + pair.get0() + ")";
    }
    
    @Pure
    @Override 
    public @Nonnull String visitPrimitive(@Nonnull PrimitiveType type, @Nullable Pair<String, JavaFileGenerator> pair) {
        Require.that(pair.get0() != null).orThrow("The field access code is a required parameter and cannot be generated on the fly. Please call visit(TypeMirror, String) instead.");
        
        return pair.get0();
    }
    
    @Pure
    @Override 
    public @Nonnull String visitDeclared(@Nonnull DeclaredType type, @Nullable Pair<String, JavaFileGenerator> pair) {
        Require.that(pair.get0() != null).orThrow("The field access code is a required parameter and cannot be generated on the fly. Please call visit(TypeMirror, String) instead.");
        
        if (ProcessingUtility.isRawlyAssignable(type, CharSequence.class)) {
            return pair.get1().importIfPossible(Quotes.class) + ".inDouble(" + pair.get0() + ")";
        } else {
            return "String.valueOf(" + pair.get0() + ")";
        }
    }
    
    @Pure
    @Override 
    public @Nonnull String visitArray(@Nonnull ArrayType type, @Nullable Pair<String, JavaFileGenerator> pair) {
        Require.that(pair.get0() != null).orThrow("The field access code is a required parameter and cannot be generated on the fly. Please call visit(TypeMirror, String) instead.");
        
        // TODO: Make sure that the elements are quoted if they are strings.
        return pair.get1().importIfPossible(Arrays.class) + ".toString(" + pair.get0() + ")";
    }
    
}
