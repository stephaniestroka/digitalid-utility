package net.digitalid.utility.generator.information.type;

import java.util.Collections;

import javax.annotation.Nonnull;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.generators.ConverterGenerator;
import net.digitalid.utility.generator.information.field.EnumValueInformation;
import net.digitalid.utility.generator.information.field.FieldInformation;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.generator.information.method.MethodParameterInformation;

/**
 * This type collects the relevant information about an enum for generating a {@link ConverterGenerator converter}.
 */
public class EnumInformation extends InstantiableTypeInformation {
    
    /* -------------------------------------------------- Representing Field Information -------------------------------------------------- */
    
    @Override
    public @Nonnull FiniteIterable<@Nonnull FieldInformation> getRepresentingFieldInformation() {
        if (getRecoverMethod() == null) {
            // returns the value of the enum
            return FiniteIterable.of(EnumValueInformation.of(getElement()));
        } else {
            // return the parameters of the recover method
            final @Nonnull FiniteIterable<@Nonnull VariableElement> variableElements = getRecoverMethod().getParameters().map(MethodParameterInformation::getMatchingField);
            // Ignoring the warning by IntelliJ. findFirst() can indeed return a null object and therefore the predicate does not always return true. IntelliJ is confused because we are using @Nullable from the bootstrap project in the iterable project and it doesn't map to the @Nullable in the validation project.
            return getFieldInformation().filter(field -> variableElements.findFirst(variableElement -> variableElement.getSimpleName().contentEquals(field.getName())) != null);
        }
    }
    
    /* -------------------------------------------------- Overridden Methods -------------------------------------------------- */
    
    @Override
    public @Nonnull FiniteIterable<@Nonnull MethodInformation> getOverriddenMethods() {
        return FiniteIterable.of(Collections.emptyList());
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new enum type information instance.
     */
    protected EnumInformation(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType) {
        super(typeElement, containingType);
    }
    
    /**
     * Returns an enum type information instance.
     */
    public static @Nonnull EnumInformation of(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType) {
        return new EnumInformation(typeElement, containingType);
    }
    
}
