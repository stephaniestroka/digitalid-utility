package net.digitalid.utility.generator.information.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.annotations.state.Unmodifiable;
import net.digitalid.utility.circumfixes.Brackets;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processing.utility.StaticProcessingEnvironment;

/**
 *
 */
public class InformationFilter {
    
    /**
     * Returns an iterable of method information objects for a given type element and declared type.
     */
    public static @Unmodifiable @Nonnull FiniteIterable<@Nonnull MethodInformation> getMethodInformation(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType) {
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
                    allMethods.add(MethodInformation.of(executableElement, containingType));
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
    
}
