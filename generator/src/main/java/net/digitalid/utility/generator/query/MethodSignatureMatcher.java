package net.digitalid.utility.generator.query;

import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.VariableElement;

import net.digitalid.utility.functional.interfaces.Predicate;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;

/**
 * Implements a method signature matcher which is used in queries for method information objects.
 */
public class MethodSignatureMatcher implements Predicate<MethodInformation> {
    
    /**
     * A regular expression that is used to find a method information.
     */
    public final @Nonnull Pattern namePattern;
    
    /**
     * An array of method parameters.
     */
    public final @Nonnull @NonNullableElements String[] parameters;
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new method signature matcher with a given name regex and a list of expected parameters.
     */
    /*private MethodSignatureMatcher(@Nonnull String nameRegex, @Nullable @NonNullableElements Class<?>... parameters) {
        this.namePattern = Pattern.compile(nameRegex);
        if (parameters == null) {
            this.parameters = new String[0];
        } else {
            this.parameters = new String[parameters.length];
            int i = 0;
            for (@Nonnull Class<?> parameter : parameters) {
                this.parameters[i] = parameter.getCanonicalName();
                i++;
            }
        }
    }*/
    
    private MethodSignatureMatcher(@Nonnull String nameRegex, @Nullable @NonNullableElements String... parameters) {
        this.namePattern = Pattern.compile(nameRegex);
        if (parameters == null) {
            this.parameters = new String[0];
        } else {
            this.parameters = parameters;
        }
    }
    
    /**
     * Returns a method signature matcher with a given name regex and a list of expected parameters.
     */
    public static @Nonnull MethodSignatureMatcher of(@Nonnull String nameRegex, @Nullable @NonNullableElements Class<?>... parameters) {
        final @Nonnull String[] typeNames;
        if (parameters == null) {
            typeNames = new String[0];
        } else {
            typeNames = new String[parameters.length];
            int i = 0;
            for (@Nonnull Class<?> parameter : parameters) {
                typeNames[i] = parameter.getCanonicalName();
                i++;
            }
        }
        return new MethodSignatureMatcher(nameRegex, typeNames);
    }
    
    public static @Nonnull MethodSignatureMatcher of(@Nonnull String nameRegex, @Nullable @NonNullableElements String... parameters) {
        return new MethodSignatureMatcher(nameRegex, parameters);
    }
    
    public static @Nonnull MethodSignatureMatcher of(@Nonnull String nameRegex) {
        return new MethodSignatureMatcher(nameRegex, null);
    }
    
    /* -------------------------------------------------- Matcher -------------------------------------------------- */
    
    /**
     * Checks whether the given object matches this method signature.
     */
    @Override
    public boolean evaluate(@Nonnull MethodInformation methodInformation) {
        boolean matches = namePattern.matcher(methodInformation.getName()).matches();
        final @Nonnull @NonNullableElements List<? extends VariableElement> methodParameters = methodInformation.getElement().getParameters();
        if (parameters.length == methodParameters.size()) {
            for (int i = 0; i < methodParameters.size(); i++) {
                final @Nonnull String nameOfDeclaredType = ProcessingUtility.getQualifiedName(methodParameters.get(i).asType());
                ProcessingLog.debugging("name of type: $", nameOfDeclaredType);
                final @Nonnull String parameter = parameters[i];
                if (!parameter.equals("?")) {
                    matches = matches && nameOfDeclaredType.equals(parameter);
                }
            }
        } else {
            matches = false;
        }
        return matches;
    }
    
}
