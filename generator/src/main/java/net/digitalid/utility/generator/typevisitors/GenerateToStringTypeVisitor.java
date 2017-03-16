package net.digitalid.utility.generator.typevisitors;

import java.util.Arrays;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleTypeVisitor7;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.circumfixes.Quotes;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processor.generator.JavaFileGenerator;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * The type visitor prints character sequence values in double quotes and primitive type values without quotes.
 */
@Stateless
public class GenerateToStringTypeVisitor extends SimpleTypeVisitor7<@Nonnull String, @Nullable Pair<@Nonnull String, @Nonnull JavaFileGenerator>> {
    
    @Pure
    @Override
    protected @Nonnull String defaultAction(@Nonnull TypeMirror type, @Nullable Pair<@Nonnull String, @Nonnull JavaFileGenerator> pair) {
        return pair.get0();
    }
    
    @Pure
    @Override 
    public @Nonnull String visitDeclared(@Nonnull DeclaredType type, @Nullable Pair<@Nonnull String, @Nonnull JavaFileGenerator> pair) {
        if (ProcessingUtility.isRawSubtype(type, CharSequence.class)) {
            return pair.get1().importIfPossible(Quotes.class) + ".inDouble(" + pair.get0() + ")";
        } else {
            return pair.get0();
        }
    }
    
    @Pure
    @Override 
    public @Nonnull String visitArray(@Nonnull ArrayType type, @Nullable Pair<@Nonnull String, @Nonnull JavaFileGenerator> pair) {
        // TODO: Make sure that the elements are quoted if they are strings.
        return pair.get1().importIfPossible(Arrays.class) + ".toString(" + pair.get0() + ")";
    }
    
}
