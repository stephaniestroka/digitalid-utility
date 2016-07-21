package net.digitalid.utility.generator.information.type;

import java.util.Collections;

import javax.annotation.Nonnull;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.information.field.EnumValueInformation;
import net.digitalid.utility.generator.information.field.FieldInformation;
import net.digitalid.utility.generator.information.method.ConstructorInformation;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.generator.information.variable.VariableElementInformation;
import net.digitalid.utility.validation.annotations.size.Empty;

/**
 *
 */
public class EnumInformation extends TypeInformation {
    
    @Override
    public @Nonnull @Empty FiniteIterable<@Nonnull ConstructorInformation> getConstructors() {
        return FiniteIterable.of(Collections.emptyList());
    }
    
    @Override
    public @Nonnull FiniteIterable<@Nonnull VariableElementInformation> getConstructorParameters() {
        return FiniteIterable.of(EnumValueInformation.of(getElement()));
    }
    
    @Override
    public @Nonnull FiniteIterable<@Nonnull FieldInformation> getRepresentingFieldInformation() {
        // returns the value of the enum
        return FiniteIterable.of(EnumValueInformation.of(getElement()));
    }
    
    @Override
    public @Nonnull FiniteIterable<@Nonnull FieldInformation> getAccessibleFieldInformation() {
        return FiniteIterable.of(Collections.emptyList());
    }
    
    @Override
    public @Nonnull FiniteIterable<@Nonnull FieldInformation> getFieldInformation() {
        return FiniteIterable.of(Collections.emptyList());
    }
    
    @Override
    public @Nonnull FiniteIterable<@Nonnull MethodInformation> getOverriddenMethods() {
        return FiniteIterable.of(Collections.emptyList());
    }
    
    /**
     * Creates a new enum type information instance.
     */
    protected EnumInformation(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType) {
        super(typeElement, containingType);
    }
    
    public static @Nonnull EnumInformation of(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType) {
        return new EnumInformation(typeElement, containingType);
    }
    
}
