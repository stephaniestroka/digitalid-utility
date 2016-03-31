package net.digitalid.utility.processing.utility;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.Element;
import javax.lang.model.element.QualifiedNameable;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleTypeVisitor7;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 *
 */
@Immutable
public class TypeNameVisitor extends SimpleTypeVisitor7<StringBuilder, StringBuilder> {
    
    private static @Nonnull StringBuilder get(@Nullable StringBuilder string) {
        return string == null ? new StringBuilder() : string;
    }
    
    /* -------------------------------------------------- Default Action -------------------------------------------------- */
    
    @Pure
    @Override
    protected @Nonnull StringBuilder defaultAction(@Nonnull TypeMirror type, @Nullable StringBuilder string) {
        return get(string).append(type.toString());
    }
    
    /* -------------------------------------------------- Visit -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull StringBuilder visitArray(@Nonnull ArrayType type, @Nullable StringBuilder string) {
        return visit(type.getComponentType(), get(string)).append("[]");
    }
    
    @Pure
    @Override
    public @Nonnull StringBuilder visitPrimitive(@Nonnull PrimitiveType type, @Nullable StringBuilder string) {
        return get(string).append(type.toString());
    }
    
    public @Nonnull StringBuilder visitDeclared(@Nonnull DeclaredType type, @Nullable StringBuilder string) {
        final @Nonnull Element typeElement = type.asElement();
        Require.that(typeElement.getKind().isClass() || typeElement.getKind().isInterface()).orThrow("The element $ has to be a type.", typeElement);
        
        final @Nonnull String qualifiedNameOfType = ((QualifiedNameable) typeElement).getQualifiedName().toString();
        return get(string).append(qualifiedNameOfType);
    }
    
}
