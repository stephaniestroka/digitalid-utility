package net.digitalid.utility.generator.information.type;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.functional.iterable.NullableIterable;
import net.digitalid.utility.generator.BuilderGenerator;
import net.digitalid.utility.generator.SubclassGenerator;
import net.digitalid.utility.generator.information.exceptions.UnexpectedTypeContentException;
import net.digitalid.utility.generator.information.field.RepresentingFieldInformation;
import net.digitalid.utility.generator.information.method.ConstructorInformation;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * This type collects the relevant information about an interface for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 */
public class InterfaceInformation extends TypeInformation {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull List<ConstructorInformation> getConstructors() {
        return Collections.emptyList();
    }
    
    /* -------------------------------------------------- Representing Field Information -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull List<RepresentingFieldInformation> getRepresentingFieldInformation() throws UnexpectedTypeContentException {
        return NullableIterable.ofNonNullableElements(generatedFieldInformation).map(castToRepresentingFieldInformation).toList();
    }
    
    /* -------------------------------------------------- Overriden Methods -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull List<MethodInformation> getOverriddenMethods() {
        return Collections.emptyList();
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
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
