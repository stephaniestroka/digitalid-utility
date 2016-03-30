package net.digitalid.utility.generator.information.type;

import java.util.Collections;

import javax.annotation.Nonnull;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.functional.iterable.old.NonNullableIterable;
import net.digitalid.utility.generator.BuilderGenerator;
import net.digitalid.utility.generator.SubclassGenerator;
import net.digitalid.utility.generator.information.field.RepresentingFieldInformation;
import net.digitalid.utility.generator.information.method.ConstructorInformation;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This type collects the relevant information about an interface for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 */
public class InterfaceInformation extends TypeInformation {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull NonNullableIterable<ConstructorInformation> getConstructors() {
        return NonNullableIterable.ofNonNullableElements(Collections.<ConstructorInformation>emptyList());
    }
    
    /* -------------------------------------------------- Representing Field Information -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull NonNullableIterable<RepresentingFieldInformation> getRepresentingFieldInformation() {
        return generatedFieldInformation.map(toRepresentingFieldInformation);
    }
    
    /* -------------------------------------------------- Overriden Methods -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull NonNullableIterable<MethodInformation> getOverriddenMethods() {
        return NonNullableIterable.ofNonNullableElements(Collections.<MethodInformation>emptyList());
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new interface information instance.
     */
    protected InterfaceInformation(@Nonnull TypeElement element, @Nonnull DeclaredType containingType) {
        super(element, containingType);
    }
    
    /**
     * Returns a class information object of the given type typeElement and containing type.
     */
    public static @Nonnull InterfaceInformation of(@Nonnull TypeElement element, @Nonnull DeclaredType containingType) {
        return new InterfaceInformation(element, containingType);
    }
    
}
