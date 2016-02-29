package net.digitalid.utility.generator.information.type.filter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.exceptions.UnexpectedFailureException;
import net.digitalid.utility.generator.information.field.DirectlyAccessibleParameterBasedFieldInformation;
import net.digitalid.utility.generator.information.field.NonDirectlyAccessibleParameterBasedFieldInformation;
import net.digitalid.utility.generator.information.field.ParameterBasedFieldInformation;
import net.digitalid.utility.generator.information.filter.ElementFilter;
import net.digitalid.utility.generator.information.filter.ElementInformationNameMatcher;
import net.digitalid.utility.generator.information.filter.FilterCondition;
import net.digitalid.utility.generator.information.filter.Transformer;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.string.StringCase;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;

/**
 *
 */
public class ParameterBasedFieldInformationTransformer  implements Transformer<Element, ParameterBasedFieldInformation> {
        
    private final @Nonnull TypeElement typeElement;
    
    protected ParameterBasedFieldInformationTransformer(@Nonnull TypeElement typeElement) {
        this.typeElement = typeElement;
    }
    
    public static @Nonnull ParameterBasedFieldInformationTransformer with(@Nonnull TypeElement typeElement) {
        return new ParameterBasedFieldInformationTransformer(typeElement);
    }
    
    /**
     * TODO: do this with functional methods.
     */
    private @Nonnull @NonNullableElements String[] prefixWith(@Nonnull String input, @Nonnull @NonNullableElements String... prefixes) {
        final @Nonnull @NonNullableElements String[] output = new String[prefixes.length];
        int i = 0;
        for (@Nonnull String prefix : prefixes) {
            output[i] = prefix + input;
            i++;
        }
        return output;
    }
    
    @Override
    public @Nonnull ParameterBasedFieldInformation transformNonNullable(@Nonnull Element from) {
        final @Nullable VariableElement fieldElement = getField(from.getSimpleName().toString());
        if (fieldElement != null && isAccessibleField(fieldElement)) {
            return DirectlyAccessibleParameterBasedFieldInformation.of((VariableElement) from, (DeclaredType) typeElement.asType(), fieldElement);
        } else if (fieldElement != null) {
            final @Nullable MethodInformation getter = getGetter(fieldElement);
            assert getter != null;
            
            return NonDirectlyAccessibleParameterBasedFieldInformation.of((VariableElement) from, (DeclaredType) typeElement.asType(), getter, getSetter(fieldElement));
        } else {
            throw UnexpectedFailureException.with("Could not find field for parameter $", from.getSimpleName().toString());
        }
    }
    
    private @Nullable MethodInformation getGetter(@Nonnull VariableElement element) {
        final @Nonnull String parameterName = element.getSimpleName().toString();
        final @Nonnull @NonNullableElements String[] getterVariants = prefixWith(StringCase.capitalizeFirstLetters(parameterName), "get", "has", "is");
        final @Nullable MethodInformation getterOfParameterBasedField = ElementFilter.filterMethodInformation(typeElement, ElementInformationNameMatcher.<MethodInformation>withNames(getterVariants));
        return getterOfParameterBasedField;
    }
    
    private @Nullable MethodInformation getSetter(@Nonnull VariableElement element) {
        final @Nonnull String parameterName = element.getSimpleName().toString();
        final @Nullable MethodInformation getterOfParameterBasedField = ElementFilter.filterMethodInformation(typeElement, ElementInformationNameMatcher.<MethodInformation>withNames("set" + StringCase.capitalizeFirstLetters(parameterName)));
        return getterOfParameterBasedField;
    }
    
    private boolean hasGetter(@Nonnull VariableElement element) {
        final @Nullable MethodInformation getterOfParameterBasedField = getGetter(element); 
        return getterOfParameterBasedField != null;
    }
    
    private boolean isAccessibleField(@Nonnull VariableElement fieldElement) {
        return !fieldElement.getModifiers().contains(Modifier.PRIVATE);
    }
    
    private @Nullable VariableElement getField(@Nonnull final String fieldName) {
        final @Nullable VariableElement parameterBasedField = ElementFilter.filterField(typeElement, new FilterCondition<VariableElement>() {
            @Override public boolean filter(@Nonnull VariableElement type) {
                return type.getSimpleName().contentEquals(fieldName);
            }
        }, VariableElementTransformer.get());
        return parameterBasedField;
    }
    
    private boolean isAccessible(@Nonnull VariableElement fieldElement) {
        return isAccessibleField(fieldElement) || hasGetter(fieldElement);
    }
    
    @Override
    public boolean canTransform(@Nonnull Element element) {
        if (element.getKind() == ElementKind.PARAMETER) {
            final @Nullable VariableElement fieldElement = getField(element.getSimpleName().toString());
            return fieldElement != null && isAccessible(fieldElement);
        }
        return false;
    }
    
}
