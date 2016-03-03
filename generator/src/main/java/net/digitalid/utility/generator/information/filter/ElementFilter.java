package net.digitalid.utility.generator.information.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.generator.information.method.ConstructorInformation;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.logging.processing.StaticProcessingEnvironment;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;

/**
 *
 */
public class ElementFilter {
    
    private ElementFilter() {
    }
    
    /* -------------------------------------------------- Filter Kinds of Elements -------------------------------------------------- */
    
    public static @Nonnull @NonNullableElements List<Element> filterKind(@Nonnull TypeElement typeElement, @Nonnull ElementKind elementKind) {
        final @Nonnull @NonNullableElements List<Element> filteredElements = new ArrayList<>();
        final @Nonnull @NonNullableElements List<? extends Element> elementsOfType = StaticProcessingEnvironment.getElementUtils().getAllMembers(typeElement);
        for (@Nonnull Element element : elementsOfType) {
            if (element.getKind() == elementKind) {
                filteredElements.add(element);
            }
        }
        return filteredElements;
    }
    
    /* -------------------------------------------------- Helper Methods -------------------------------------------------- */
    
    // TODO: Is the casting to DeclaredType correct in this case?
    private static @Nonnull DeclaredType getDeclaredType(@Nonnull TypeElement typeElement) {
        return (DeclaredType) typeElement.asType();
    }
    
    /* -------------------------------------------------- Filter Methods -------------------------------------------------- */
    
    public static @Nullable <T> T filterMethod(@Nonnull TypeElement typeElement, @Nonnull FilterCondition<T> filterCondition, @Nonnull Transformer<Element, T> elementTransformer) {
        final @Nonnull @NonNullableElements List<T> filteredMethods = filterMethods(typeElement, filterCondition, elementTransformer);
        if (filteredMethods.size() == 0) {
            return null;
        } else if (filteredMethods.size() != 1) {
            throw new RuntimeException("Expected exactly one method for the given condition");
        }
        return filteredMethods.get(0);
    }
    
    public static @Nonnull @NonNullableElements <T> List<T> filterMethods(@Nonnull TypeElement typeElement, @Nonnull Transformer<Element, T> elementTransformer) {
        return filterMethods(typeElement, FilterCondition.AcceptAll.<T>get(), elementTransformer);
    }
    
    public static @Nullable MethodInformation filterMethodInformation(@Nonnull TypeElement typeElement, @Nonnull FilterCondition<MethodInformation> filterCondition) {
        final @Nonnull @NonNullableElements List<MethodInformation> filteredMethodInformations = filterMethodInformations(typeElement, filterCondition);
        if (filteredMethodInformations.size() == 0) {
            return null;
        } else if (filteredMethodInformations.size() != 1) {
            throw new RuntimeException("Expected exactly one method for the given condition");
        }
        return filteredMethodInformations.get(0);
    }
    
    public static @Nonnull @NonNullableElements List<MethodInformation> filterMethodInformations(@Nonnull TypeElement typeElement, @Nonnull FilterCondition<MethodInformation> filterCondition) {
        return filterMethods(typeElement, filterCondition, Transformer.MethodInformationTransformer.get(getDeclaredType(typeElement)));
    }
    
    public static @Nonnull @NonNullableElements Map<String, MethodInformation> filterMethodInformations(@Nonnull TypeElement typeElement, @Nonnull FilterCondition<MethodInformation> filterCondition, KeyExtractor<String, MethodInformation> keyExtractor) {
        final @Nonnull Transformer<Element, MethodInformation> methodElementToMethodInformationTransformer = Transformer.MethodInformationTransformer.get(getDeclaredType(typeElement));
        return filterMethods(typeElement, filterCondition, methodElementToMethodInformationTransformer, keyExtractor);
    }
    
    public static @Nonnull @NonNullableElements <T> List<T> filterMethods(@Nonnull TypeElement typeElement, @Nonnull FilterCondition<T> filterCondition, @Nonnull Transformer<Element, T> elementTransformer) {
        final @Nonnull @NonNullableElements List<T> filteredMethods = new ArrayList<>();
        final @Nonnull @NonNullableElements List<? extends Element> elementsOfType = StaticProcessingEnvironment.getElementUtils().getAllMembers(typeElement);
        for (@Nonnull Element element : elementsOfType) {
            if (element.getKind() == ElementKind.METHOD) {
                T transformedElement = elementTransformer.transformNonNullable(element);
                if (filterCondition.filter(transformedElement)) {
                    filteredMethods.add(transformedElement);
                }
            }
        }
        return filteredMethods;
    }
    
    public static @Nonnull @NonNullableElements <T> Map<String, T> filterMethods(@Nonnull TypeElement typeElement, @Nonnull FilterCondition<T> filterCondition, @Nonnull Transformer<Element, T> elementTransformer, @Nonnull KeyExtractor<String, T> keyExtractor) {
        final @Nonnull @NonNullableElements Map<String, T> filteredMethods = new HashMap<>();
        final @Nonnull @NonNullableElements List<? extends Element> elementsOfType = StaticProcessingEnvironment.getElementUtils().getAllMembers(typeElement);
        for (@Nonnull Element element : elementsOfType) {
            if (element.getKind() == ElementKind.METHOD) {
                final @Nonnull T transformedElement = elementTransformer.transformNonNullable( element);
                if (filterCondition.filter(transformedElement)) {
                    filteredMethods.put(keyExtractor.getKey(transformedElement), transformedElement);
                }
            }
        }
        return filteredMethods;
    }
    
    /* -------------------------------------------------- Filter Constructors -------------------------------------------------- */
    
    public static @Nonnull @NonNullableElements List<ConstructorInformation> filterConstructors(@Nonnull TypeElement typeElement) {
        return filterConstructors(typeElement, FilterCondition.AcceptAll.<Element>get());
    }
    
    public static @Nonnull @NonNullableElements List<ConstructorInformation> filterConstructors(@Nonnull TypeElement typeElement, @Nonnull FilterCondition<Element> filterCondition) {
        final @Nonnull @NonNullableElements List<ConstructorInformation> constructorInformations = new ArrayList<>();
        final @Nonnull @NonNullableElements List<ExecutableElement> constructors = javax.lang.model.util.ElementFilter.constructorsIn(StaticProcessingEnvironment.getElementUtils().getAllMembers(typeElement));
        for (@Nonnull Element element : constructors) {
            if (filterCondition.filter(element)) {
                constructorInformations.add(ConstructorInformation.of((ExecutableElement) element, getDeclaredType(typeElement)));
            }
        }
        return constructorInformations;
    }
    
    /* -------------------------------------------------- Filter Fields -------------------------------------------------- */
    
    public static @Nullable <T> T filterField(@Nonnull TypeElement typeElement, @Nonnull FilterCondition<T> filterCondition, @Nonnull Transformer<Element, T> elementTransformer) {
        final @Nonnull @NonNullableElements List<T> fieldElements = filterFields(typeElement, filterCondition, elementTransformer);
        if (fieldElements.size() == 0) {
            return null;
        } else if (fieldElements.size() != 1) {
            throw new RuntimeException("Expected exactly one field for the given condition, but got: " + fieldElements.size());
        }
        return fieldElements.get(0);
    }
    
    public static @Nonnull @NonNullableElements <T> List<T> filterFields(@Nonnull TypeElement typeElement, @Nonnull FilterCondition<T> filterCondition, @Nonnull Transformer<Element, T> elementTransformer) {
        final @Nonnull @NonNullableElements List<VariableElement> fields = javax.lang.model.util.ElementFilter.fieldsIn(StaticProcessingEnvironment.getElementUtils().getAllMembers(typeElement));
        final @Nonnull @NonNullableElements List<T> fieldElements = new ArrayList<>();
        for (@Nonnull VariableElement field : fields) {
            T transformedElement = elementTransformer.transformNonNullable(field);
            if (filterCondition.filter(transformedElement)) {
                fieldElements.add(transformedElement);
            }
        }
        return fieldElements;
    }
    
}
