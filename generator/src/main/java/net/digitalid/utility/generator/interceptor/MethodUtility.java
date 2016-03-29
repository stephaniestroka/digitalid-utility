package net.digitalid.utility.generator.interceptor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.functional.iterable.NullableIterable;
import net.digitalid.utility.functional.fixes.Brackets;
import net.digitalid.utility.functional.fixes.IterableConverter;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.logging.processing.ProcessingLog;
import net.digitalid.utility.processor.generator.JavaFileGenerator;
import net.digitalid.utility.string.StringCase;
import net.digitalid.utility.validation.annotations.method.Pure;
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
    public static @Nonnull String generateBeginMethod(@Nonnull JavaFileGenerator javaFileGenerator, @Nonnull MethodInformation method, @Nullable String prefix, @Nullable String resultVariable) {
        final @Nonnull String methodName;
        if (prefix != null && !prefix.isEmpty()) {
            methodName = prefix + StringCase.capitalizeFirstLetters(method.getName());
        } else {
            methodName = method.getName();
        }
        javaFileGenerator.beginMethod(method.getModifiersForOverridingMethod() + javaFileGenerator.importIfPossible(method.getType()) + " " + methodName + javaFileGenerator.importingTypeVisitor.reduceParametersDeclarationToString(method.getType(), method.getElement()) + (method.getElement().getThrownTypes().isEmpty() ? "" : " throws " + IterableConverter.toString(method.getElement().getThrownTypes(), javaFileGenerator.importingTypeVisitor.TYPE_MAPPER)));
        if (resultVariable != null && method.hasReturnType()) {
            final @Nonnull String initialValue;
            if (method.getType().getReturnType().getKind().isPrimitive()) {
                ProcessingLog.debugging("Found primitive return type in method $", method.getName());
                final @Nonnull String typeAsString = javaFileGenerator.importIfPossible(method.getType());
                if (typeAsString.equals("boolean")) {
                    initialValue = "false";
                } else {
                    initialValue = "0";
                }
            } else {
                ProcessingLog.debugging("Found non-primitive return type in method $", method.getName());
                initialValue = "null";
            }
            javaFileGenerator.addStatement(javaFileGenerator.importIfPossible(method.getType().getReturnType()) + " " + resultVariable + " = " + initialValue);
        }
        return methodName;
    }
    
    /**
     * Creates a call to the method with the given methodName and the information in method information. If a return type is declared, a variable assignment on the variable <i>result&lt;MethodName&gt;</i> is created, where &lt;MethodName&gt; is the name of the method starting with an upper case letter.
     */
    @Pure
    private static @Nonnull String createMethodCallWithMethodName(@Nonnull MethodInformation method, @Nonnull String methodName, @Nullable String resultVariable) {
        return (method.hasReturnType() ? resultVariable + " = " : "") + methodName + IterableConverter.toString(NullableIterable.ofNonNullableElements(method.getElement().getParameters()).map(JavaFileGenerator.parameterToStringFunction), Brackets.ROUND);
    }
    
    /**
     * Creates a call to the super method of the method in method information. If a return type is declared, a variable assignment on the variable <i>result&lt;MethodName&gt;</i> is created, where &lt;MethodName&gt; is the name of the method starting with an upper case letter.
     */
    @Pure
    public static @Nonnull String createSuperCall(@Nonnull MethodInformation method, @Nullable String resultVariable) {
        return createMethodCallWithMethodName(method, "super." + method.getName(), resultVariable);
    }
    
    /**
     * Creates a call to the method with a given prefix and the information taken from the method in method information object. If a return type is declared, a variable assignment on the variable <i>result&lt;MethodName&gt;</i> is created, where &lt;MethodName&gt; is the name of the method starting with an upper case letter.
     */
    @Pure
    public static @Nonnull String createMethodCall(@Nonnull MethodInformation method, @Nullable String prefix, @Nullable String resultVariable) {
        final @Nonnull String methodName = prefix != null ? prefix + StringCase.capitalizeFirstLetters(method.getName()) : method.getName();
        return createMethodCallWithMethodName(method, methodName, resultVariable);
    }
    
}
