package net.digitalid.utility.generator.interceptor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.circumfixes.Brackets;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.processor.generator.JavaFileGenerator;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * This utility class groups code generation functions together.
 */
@Stateless
public final class MethodUtility {
    
    /**
     * Creates a begin-method statement from a given method information objects by collecting parameters, exception declarations and the return type.
     * If a prefix is given, the method name is prepended with it.
     */
    @Pure
    public static @Nonnull String generateBeginMethod(@Nonnull JavaFileGenerator javaFileGenerator, @Nonnull MethodInformation method, @Nullable String prefix) {
        final @Nonnull String methodName;
        if (prefix != null && !prefix.isEmpty()) {
            methodName = prefix + Strings.capitalizeFirstLetters(method.getName());
        } else {
            methodName = method.getName();
        }
        javaFileGenerator.beginMethod(method.getModifiersForOverridingMethod() + method.getReturnTypeAnnotations(javaFileGenerator) + javaFileGenerator.importIfPossible(method.getType()) + " " + methodName + javaFileGenerator.declareParameters(method.getType(), method.getElement()) + (method.getElement().getThrownTypes().isEmpty() ? "" : " throws " + FiniteIterable.of(method.getElement().getThrownTypes()).map(javaFileGenerator::importIfPossible).join()));
        return methodName;
    }
    
    /**
     * Creates a call to the method with the given methodName and the information in method information.
     */
    @Pure
    private static @Nonnull String createMethodCallWithMethodName(@Nonnull MethodInformation method, @Nonnull String methodName) {
        return methodName + FiniteIterable.of(method.getElement().getParameters()).map(parameter -> parameter.getSimpleName().toString()).join(Brackets.ROUND);
    }
    
    /**
     * Creates a call to the super method of the method in method information.
     */
    @Pure
    public static @Nonnull String createSuperCall(@Nonnull MethodInformation method) {
        return createMethodCallWithMethodName(method, "super." + method.getName());
    }
    
    /**
     * Creates a call to the method with a given prefix and the information taken from the method in method information object.
     */
    @Pure
    public static @Nonnull String createMethodCall(@Nonnull MethodInformation method, @Nullable String prefix) {
        final @Nonnull String methodName = prefix != null ? prefix + Strings.capitalizeFirstLetters(method.getName()) : method.getName();
        return createMethodCallWithMethodName(method, methodName);
    }
    
}
