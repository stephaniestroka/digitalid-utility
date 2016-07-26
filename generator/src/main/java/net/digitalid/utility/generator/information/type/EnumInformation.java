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
public final class EnumInformation extends InstantiableTypeInformation {
    
    /* -------------------------------------------------- Representing Field Information -------------------------------------------------- */
    
    @Override
    public @Nonnull FiniteIterable<@Nonnull FieldInformation> getRepresentingFieldInformation() {
        if (getRecoverMethod() == null) {
            // returns the value of the enum
            return FiniteIterable.of(EnumValueInformation.of(getElement()));
        } else {
            // return the fields matching the parameters of the recover method
            return getRecoverMethod().getParameters().map(MethodParameterInformation::getMatchingField);
        }
    }
    
    /* -------------------------------------------------- Overridden Methods -------------------------------------------------- */
    
    @Override
    public @Nonnull FiniteIterable<@Nonnull MethodInformation> getOverriddenMethods() {
        return FiniteIterable.of(Collections.emptyList());
    }
    
    /* -------------------------------------------------- Initialization Marker -------------------------------------------------- */
    
    private boolean initialized;
    
    @Override
    public boolean isInitialized() {
        return initialized;
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new enum type information instance.
     */
    protected EnumInformation(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType) {
        super(typeElement, containingType);
        
        this.initialized = true;
    }
    
    /**
     * Returns an enum type information instance.
     */
    public static @Nonnull EnumInformation of(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType) {
        return new EnumInformation(typeElement, containingType);
    }
    
}
