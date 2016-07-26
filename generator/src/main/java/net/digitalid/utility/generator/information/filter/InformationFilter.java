package net.digitalid.utility.generator.information.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.annotations.state.Unmodifiable;
import net.digitalid.utility.circumfixes.Brackets;
import net.digitalid.utility.exceptions.UnexpectedFailureException;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.information.field.DirectlyAccessibleDeclaredFieldInformation;
import net.digitalid.utility.generator.information.field.NonAccessibleDeclaredFieldInformation;
import net.digitalid.utility.generator.information.field.NonDirectlyAccessibleDeclaredFieldInformation;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.generator.information.type.TypeInformation;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processing.utility.StaticProcessingEnvironment;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;

/**
 *
 */
public class InformationFilter {
    
    /**
     * Returns an iterable of method information objects for a given type element and declared type.
     */
    public static @Unmodifiable @Nonnull FiniteIterable<@Nonnull MethodInformation> getMethodInformation(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType, @Nonnull TypeInformation typeInformation) {
        final List<? extends Element> allMembers = StaticProcessingEnvironment.getElementUtils().getAllMembers(typeElement);
        final @Nonnull HashMap<String, Integer> abstractMethodEntries = new HashMap<>();
        final @Nonnull Set<String> nonAbstractMethodEntries = new HashSet<>();
        final @Nonnull List<MethodInformation> allMethods = new ArrayList<>();
        final @Nonnull List<Integer> toRemove = new ArrayList<>();
        for (Element element : allMembers) {
            if (element.getKind() == ElementKind.METHOD) {
                final @Nonnull ExecutableElement executableElement = (ExecutableElement) element;
                final @Nonnull String methodKey = executableElement.getSimpleName().toString() + FiniteIterable.of(executableElement.getParameters()).map(parameter -> ProcessingUtility.getQualifiedName(parameter.asType())).join(Brackets.ROUND);
                if (!nonAbstractMethodEntries.contains(methodKey)) {
                    allMethods.add(MethodInformation.of(executableElement, containingType, typeInformation));
                }
                if (executableElement.getModifiers().contains(Modifier.ABSTRACT)) {
                    abstractMethodEntries.put(methodKey, allMethods.size() - 1);
                } else {
                    nonAbstractMethodEntries.add(methodKey);
                    if (abstractMethodEntries.containsKey(methodKey)) {
                        toRemove.add(abstractMethodEntries.get(methodKey));
                    }
                } 
            }
        }
        Collections.sort(toRemove);
        Collections.reverse(toRemove);
        for (Integer index : toRemove) {
            allMethods.remove(index);
        }
        return FiniteIterable.of(allMethods);
    }
    
    /**
     * Returns an iterable of directly accessible field information for a given type element.
     */
    public static @Unmodifiable @Nonnull FiniteIterable<DirectlyAccessibleDeclaredFieldInformation> getDirectlyAccessibleFieldInformation(@Nonnull TypeElement typeElement) {
        final @Nonnull FiniteIterable<@Nonnull VariableElement> fields = ProcessingUtility.getAllFields(typeElement);
        
        return fields.filter((field) -> (!field.getModifiers().contains(Modifier.PRIVATE))).map((field) -> (DirectlyAccessibleDeclaredFieldInformation.of(field, (DeclaredType) typeElement.asType())));
    }
    
    /* -------------------------------------------------- Non-Directly Accessible Declared Field Information -------------------------------------------------- */
    
    /**
     * Returns a method information object that matches the expected declaration of a getter for a certain field.
     * A {@link UnexpectedFailureException exception} is thrown if the getter was not found.
     */
    private static @Unmodifiable @Nonnull MethodInformation getGetterOf(@Nonnull String fieldName, @Nonnull FiniteIterable<@Nonnull MethodInformation> methodsOfType) {
        final @Nonnull String nameRegex = "(get|has|is)" + Strings.capitalizeFirstLetters(fieldName);
        final @Nullable MethodInformation methodInformation = methodsOfType.findFirst(MethodSignatureMatcher.of(nameRegex));
        if (methodInformation == null) {
            // TODO: Thou shalt not throw exceptions during annotation processing!
            throw UnexpectedFailureException.with("Getter method for $ not found", fieldName);
//            throw ConformityViolation.with("Getter method for $ not found", fieldName);
        }
        return methodInformation;
    }
    
    /**
     * Returns true iff a getter method was found in a list of methods of a type for a given field.
     */
    private static boolean hasGetter(@Nonnull String fieldName, @Nonnull TypeMirror fieldType, @Nonnull FiniteIterable<@Nonnull MethodInformation> methodsOfType) {
        final @Nonnull String nameRegex = "(get|has|is)" + Strings.capitalizeFirstLetters(fieldName);
        final @Nullable MethodInformation methodInformation = methodsOfType.findFirst(MethodSignatureMatcher.of(nameRegex));
        if (methodInformation != null) {
            @Nullable TypeMirror returnType = methodInformation.getReturnType();
            if (StaticProcessingEnvironment.getTypeUtils().isAssignable(returnType, fieldType)) {
                ProcessingLog.information("Found getter for field $: $", fieldName, methodInformation);
                return true;
            } else {
                ProcessingLog.information("Found method that looks like a getter, but return type of method is: $, type of field: $ ", returnType, fieldType);
                return false;
            }
        }
        return methodInformation != null;
    }
    
    /**
     * Returns a method information object that matches the expected declaration of a setter for a certain field.
     * If the setter was not found, null is returned.
     */
    private static @Nullable MethodInformation getSetterOf(@Nonnull String fieldName, @Nonnull String fieldType, @Nonnull @NonNullableElements FiniteIterable<MethodInformation> methodsOfType) {
        final @Nonnull String methodName = "set" + Strings.capitalizeFirstLetters(fieldName);
        return methodsOfType.findFirst(MethodSignatureMatcher.of(methodName, fieldType));
    }
    
    /**
     * Retrieves declared field information objects for fields in a type.
     */
    public static @Nonnull FiniteIterable<@Nonnull NonDirectlyAccessibleDeclaredFieldInformation> getNonDirectlyAccessibleFieldInformation(@Nonnull TypeElement typeElement, @Nonnull FiniteIterable<@Nonnull MethodInformation> methodsOfType) {
        final @Nonnull FiniteIterable<@Nonnull VariableElement> fields = ProcessingUtility.getAllFields(typeElement);
        
        return fields.filter(field -> (
                field.getModifiers().contains(Modifier.PRIVATE) &&
                        hasGetter(field.getSimpleName().toString(), field.asType(), methodsOfType)
        )).map(field ->
                NonDirectlyAccessibleDeclaredFieldInformation.of(field, (DeclaredType) typeElement.asType(), getGetterOf(field.getSimpleName().toString(), methodsOfType), getSetterOf(field.getSimpleName().toString(), ProcessingUtility.getQualifiedName(field.asType()), methodsOfType))
        );
    }
    
    /* -------------------------------------------------- Non-Accessible Field Information -------------------------------------------------- */
    
    /**
     * Retrieves declared field information objects for fields in a type.
     */
    public static @Nonnull FiniteIterable<@Nonnull NonAccessibleDeclaredFieldInformation> getNonAccessibleFieldInformation(@Nonnull TypeElement typeElement, @Nonnull FiniteIterable<@Nonnull MethodInformation> methodsOfType) {
        final @Nonnull FiniteIterable<@Nonnull VariableElement> fields = ProcessingUtility.getAllFields(typeElement);
        return fields.filter(field -> (
                field.getModifiers().contains(Modifier.PRIVATE) &&
                        !hasGetter(field.getSimpleName().toString(), field.asType(), methodsOfType)
        )).map(field ->
                NonAccessibleDeclaredFieldInformation.of(field, (DeclaredType) typeElement.asType())
        );
    }
    
}
