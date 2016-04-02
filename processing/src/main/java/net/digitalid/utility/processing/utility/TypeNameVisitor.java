package net.digitalid.utility.processing.utility;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.QualifiedNameable;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleTypeVisitor7;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * This type visitor returns the qualified name of the given type.
 */
@Stateless
public class TypeNameVisitor extends SimpleTypeVisitor7<@Nonnull StringBuilder, @Nullable StringBuilder> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected TypeNameVisitor() {}
    
    /**
     * Stores an instance of the stateless type name visitor.
     */
    public static final @Nonnull TypeNameVisitor INSTANCE = new TypeNameVisitor();
    
    /* -------------------------------------------------- Utility -------------------------------------------------- */
    
    /**
     * Returns the given string builder if it is not null or a new string builder otherwise.
     */
    @Pure
    protected static @Capturable @Nonnull StringBuilder get(@NonCaptured @Unmodified @Nullable StringBuilder string) {
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
    public @Nonnull StringBuilder visitDeclared(@Nonnull DeclaredType type, @Nullable StringBuilder string) {
        return get(string).append(((QualifiedNameable) type.asElement()).getQualifiedName());
    }
    
}
