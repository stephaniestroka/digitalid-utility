package net.digitalid.utility.generator.query;

import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.logging.processing.StaticProcessingEnvironment;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;

/**
 * Implements a method signature matcher which is used in queries for method information objects.
 */
public class MethodSignatureMatcher {
    
    /**
     * A regular expression that is used to find a method information.
     */
    public final @Nonnull Pattern namePattern;
    
    /**
     * An array of method parameters.
     */
    public final @Nonnull @NonNullableElements TypeElement[] parameters;
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new method signature matcher with a given name regex and a list of expected parameters.
     */
    private MethodSignatureMatcher(@Nonnull String nameRegex, @Nonnull @NonNullableElements TypeElement[] parameters) {
        this.namePattern = Pattern.compile(nameRegex);
        this.parameters = parameters;
    }
    
    /**
     * Returns a method signature matcher with a given name regex and a list of expected parameters.
     */
    public static @Nonnull MethodSignatureMatcher of(@Nonnull String nameRegex, @Nonnull @NonNullableElements TypeElement[] parameters) {
        return new MethodSignatureMatcher(nameRegex, parameters);
    }
    
    /* -------------------------------------------------- Matcher -------------------------------------------------- */
    
    /**
     * Checks whether the given object matches this method signature.
     */
    public boolean matches(@Nonnull MethodInformation methodInformation) {
        boolean matches = namePattern.matcher(methodInformation.getName()).matches();
        final @Nonnull @NonNullableElements List<? extends VariableElement> methodParameters = methodInformation.getElement().getParameters();
        if (parameters.length == methodParameters.size()) {
            for (int i = 0; i < methodParameters.size(); i++) {
                matches = matches && StaticProcessingEnvironment.getTypeUtils().asElement(methodParameters.get(i).asType()).equals(parameters[i]);
            }
        } else {
            matches = false;
        }
        return matches;
    }
    
}
