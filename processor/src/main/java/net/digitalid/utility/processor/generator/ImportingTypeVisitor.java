package net.digitalid.utility.processor.generator;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.SimpleTypeVisitor7;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.functional.fixes.Brackets;
import net.digitalid.utility.functional.interfaces.UnaryFunction;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.tuples.annotations.Pure;
import net.digitalid.utility.validation.annotations.reference.Capturable;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * Description.
 * 
 * TODO: Transform into an inner, non-static class of JavaFileGenerator.
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
    
    public final @Nonnull UnaryFunction<TypeMirror, String> TYPE_MAPPER = (type) -> (visit(type).toString());
    
    @Pure
    public @Nonnull String getTypeVariablesWithoutBounds(@Nonnull @NonNullableElements List<? extends TypeMirror> types, boolean withTrailingSpace) {
        final @Nonnull StringBuilder result = new StringBuilder();
        if (!types.isEmpty()) {
            result.append("<");
            for (@Nonnull TypeMirror type : types) {
                @Nonnull TypeVariable typeVariable = (TypeVariable) type;
                if (result.length() > 1) { result.append(", "); }
                result.append(typeVariable.toString());
            }
            result.append(">");
            if (withTrailingSpace) { result.append(" "); }
        }
        return result.toString();
    }
    
    @Pure
    public @Capturable @Nonnull @NonNullableElements List<String> mapTypeVariablesWithBoundsToStrings(@Nonnull @NonNullableElements List<? extends TypeMirror> types) {
        final @Nonnull @NonNullableElements List<String> result = new ArrayList<>(types.size());
        for (@Nonnull TypeMirror type : types) {
            final @Nonnull TypeVariable typeVariable = (TypeVariable) type;
            final @Nonnull StringBuilder string = new StringBuilder(typeVariable.toString());
            final @Nonnull TypeMirror lowerBound = typeVariable.getLowerBound();
            if (lowerBound.getKind() != TypeKind.NULL) {
                string.append(" super ");
                visit(lowerBound, string);
            }
            final @Nonnull TypeMirror upperBound = typeVariable.getUpperBound();
            if (!upperBound.toString().equals("java.lang.Object")) {
                string.append(" extends ");
                visit(upperBound, string);
            }
            result.add(string.toString());
        }
        return result;
    }
    
    @Pure
    public @Nonnull String reduceTypeVariablesWithBoundsToString(@Nonnull @NonNullableElements List<? extends TypeMirror> types) {
        return types.isEmpty() ? "" : FiniteIterable.of(mapTypeVariablesWithBoundsToStrings(types)).join(Brackets.POINTY);
    }
    
    @Pure
    public @Capturable @Nonnull @NonNullableElements List<String> mapParametersDeclarationToStrings(@Nonnull ExecutableType type, @Nonnull ExecutableElement element) {
        final @Nonnull @NonNullableElements List<? extends TypeMirror> parameterTypes = type.getParameterTypes();
        final @Nonnull @NonNullableElements List<? extends VariableElement> parameterElements = element.getParameters();
        Require.that(parameterTypes.size() == parameterElements.size()).orThrow("The number of parameter types and parameter elements have to be the same.");
        
        final @Nonnull @NonNullableElements List<String> result = new ArrayList<>(parameterTypes.size());
        for (int i = 0; i < parameterTypes.size(); i++) {
            result.add(visit(parameterTypes.get(i)).toString() + " " + parameterElements.get(i).getSimpleName());
        }
        return result;
    }
    
    @Pure
    public @Nonnull String reduceParametersDeclarationToString(@Nonnull ExecutableType type, @Nonnull ExecutableElement element) {
        return FiniteIterable.of(mapParametersDeclarationToStrings(type, element)).join(Brackets.ROUND);
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
    public @Nonnull StringBuilder visitWildcard(@Nonnull WildcardType type, @Nullable StringBuilder string) {
        final @Nonnull StringBuilder result = get(string).append("?");
        final @Nullable TypeMirror superBound = type.getSuperBound();
        if (superBound != null) {
            result.append(" super ");
            visit(superBound, result);
        }
        final @Nullable TypeMirror extendsBound = type.getExtendsBound();
        if (extendsBound != null) {
            result.append(" extends ");
            visit(extendsBound, result);
        }
        return result;
    }
    
    @Pure
    @Override
    public @Nonnull StringBuilder visitExecutable(@Nonnull ExecutableType type, @Nullable StringBuilder string) {
        return visit(type.getReturnType(), get(string).append(reduceTypeVariablesWithBoundsToString(type.getTypeVariables())).append(type.getTypeVariables().isEmpty() ? "" : " "));
    }
    
    @Pure
    @Override
    public @Nonnull StringBuilder visitArray(@Nonnull ArrayType type, @Nullable StringBuilder string) {
        return visit(type.getComponentType(), get(string)).append("[]");
    }
    
}
