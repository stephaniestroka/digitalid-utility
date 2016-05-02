package net.digitalid.utility.generator.typevisitors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleTypeVisitor7;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.processing.utility.ProcessingUtility;

/**
 *
 */
public class GenerateToStringTypeVisitor extends SimpleTypeVisitor7<@Nonnull String, @Nullable String> {
    
    @Pure
    @Override
    protected @Nonnull String defaultAction(@Nonnull TypeMirror type, @Nullable String accessCode) {
        Require.that(accessCode != null).orThrow("The field access code is a required parameter and cannot be generated on the fly. Please call visit(TypeMirror, String) instead.");
        
        return "Quotes.inDouble(" + accessCode + ")";
    }
    
    @Pure
    @Override 
    public @Nonnull String visitPrimitive(@Nonnull PrimitiveType type, @Nullable String accessCode) {
        Require.that(accessCode != null).orThrow("The field access code is a required parameter and cannot be generated on the fly. Please call visit(TypeMirror, String) instead.");
        
        return accessCode;
    }
    
    @Pure
    @Override 
    public @Nonnull String visitDeclared(@Nonnull DeclaredType type, @Nullable String accessCode) {
        Require.that(accessCode != null).orThrow("The field access code is a required parameter and cannot be generated on the fly. Please call visit(TypeMirror, String) instead.");
        
        if (ProcessingUtility.isAssignable(type, CharSequence.class)) {
            return "Quotes.inDouble(" + accessCode + ")";
        } else {
            return "String.valueOf(" + accessCode + ")";
        }
    }
    
    @Pure
    @Override 
    public @Nonnull String visitArray(@Nonnull ArrayType type, @Nullable String accessCode) {
        Require.that(accessCode != null).orThrow("The field access code is a required parameter and cannot be generated on the fly. Please call visit(TypeMirror, String) instead.");
        
        // TODO: Make sure that the elements are quoted if they are strings.
        return "Arrays.toString(" + accessCode + ")";
    }
    
}
