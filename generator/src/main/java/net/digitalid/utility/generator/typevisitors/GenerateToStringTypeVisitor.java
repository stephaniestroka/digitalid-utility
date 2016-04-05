package net.digitalid.utility.generator.typevisitors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleTypeVisitor7;

import net.digitalid.utility.contracts.Require;

/**
 *
 */
public class GenerateToStringTypeVisitor extends SimpleTypeVisitor7<@Nonnull String, @Nullable String> {
    
    @Override
    protected @Nonnull String defaultAction(@Nonnull TypeMirror e, @Nullable String accessCode) {
        Require.that(accessCode != null).orThrow("The field access code is a required parameter and cannot be generated on the fly. Please call visit(TypeMirror, String) instead.");
        
        return "Objects.toString(" + accessCode + ")";
    }
    
    @Override 
    public @Nonnull String visitArray(@Nonnull ArrayType t, @Nullable String accessCode) {
        Require.that(accessCode != null).orThrow("The field access code is a required parameter and cannot be generated on the fly. Please call visit(TypeMirror, String) instead.");
    
        return "Arrays.toString(" + accessCode + ")";
    }
    
}
