package net.digitalid.utility.processor.visitor;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.Element;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.SimpleTypeVisitor7;

import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.processor.generator.JavaFileGenerator;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * Description.
 */
@Immutable
public class ImportingTypeVisitor extends SimpleTypeVisitor7<StringBuilder, StringBuilder> {
    
    /* -------------------------------------------------- Java File Generator -------------------------------------------------- */
    
    private final @Nonnull JavaFileGenerator javaFileGenerator;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ImportingTypeVisitor(@Nonnull JavaFileGenerator javaFileGenerator) {
        this.javaFileGenerator = javaFileGenerator;
    }
    
    @Pure
    public static @Nonnull ImportingTypeVisitor with(@Nonnull JavaFileGenerator javaFileGenerator) {
        return new ImportingTypeVisitor(javaFileGenerator);
    }
    
    /* -------------------------------------------------- Utility -------------------------------------------------- */
    
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
    public @Nonnull StringBuilder visitDeclared(@Nonnull DeclaredType type, @Nullable StringBuilder string) {
        final @Nonnull Element element = type.asElement();
        final @Nonnull StringBuilder result = get(string).append(javaFileGenerator.importIfPossible(element));
        final @Nonnull @NonNullableElements List<? extends TypeMirror> typeArguments = type.getTypeArguments();
        if (!typeArguments.isEmpty()) {
            result.append("<");
            boolean first = true;
            for (@Nonnull TypeMirror typeArgument : typeArguments) {
                if (!first) { result.append(", "); first = false; }
                visit(typeArgument, result);
            }
            result.append(">");
        }
        return result;
    }
    
    @Pure
    @Override
    public @Nonnull StringBuilder visitTypeVariable(@Nonnull TypeVariable type, @Nullable StringBuilder string) {
        final @Nonnull StringBuilder result = get(string).append(type.toString());
        final @Nonnull TypeMirror lowerBound = type.getLowerBound();
        AnnotationLog.verbose("lowerBound.toString(): " + lowerBound.toString());
        AnnotationLog.verbose("lowerBound.getKind(): " + lowerBound.getKind());
        if (lowerBound.getKind() != TypeKind.NULL) {
            result.append(" super ");
            visit(lowerBound, result);
        }
        final @Nonnull TypeMirror upperBound = type.getUpperBound();
        AnnotationLog.verbose("upperBound.toString(): " + upperBound.toString());
        if (!upperBound.toString().equals("java.lang.Object")) {
//            result.append(" extends ");
//            visit(upperBound, result);
        }
        return result;
    }
    
    @Pure
    @Override
    public @Nonnull StringBuilder visitExecutable(@Nonnull ExecutableType type, @Nullable StringBuilder string) {
        final @Nonnull StringBuilder result = get(string);
        final @Nonnull @NonNullableElements List<? extends TypeVariable> typeVariables = type.getTypeVariables();
        if (!typeVariables.isEmpty()) {
            result.append("<");
            boolean first = true;
            for (@Nonnull TypeVariable typeVariable : typeVariables) {
                if (!first) { result.append(", "); first = false; }
                result.append(typeVariable.toString());
            }
            result.append("> ");
        }
        final @Nonnull TypeMirror returnType = type.getReturnType();
        return visit(returnType, result);
    }
    
}
