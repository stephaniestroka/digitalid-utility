package net.digitalid.utility.generator.information.method;

import javax.annotation.Nonnull;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.generators.BuilderGenerator;
import net.digitalid.utility.generator.generators.SubclassGenerator;
import net.digitalid.utility.generator.information.field.FieldInformation;
import net.digitalid.utility.processing.logging.SourcePosition;

/**
 * This type collects the relevant information about a constructor for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 */
public class ConstructorInformation extends ExecutableInformation {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ConstructorInformation(@Nonnull ExecutableElement element, @Nonnull DeclaredType containingType, @Nonnull FiniteIterable<@Nonnull FieldInformation> fieldInformation) {
        super(element, containingType, fieldInformation);
        
        Require.that(element.getKind() == ElementKind.CONSTRUCTOR).orThrow("The element $ has to be a constructor.", SourcePosition.of(element));
    }
    
    /**
     * Returns the constructor information of the given constructor element and containing type.
     * 
     * @require element.getKind() == ElementKind.CONSTRUCTOR : "The element has to be a constructor.";
     */
    @Pure
    public static @Nonnull ConstructorInformation of(@Nonnull ExecutableElement element, @Nonnull DeclaredType containingType, @Nonnull FiniteIterable<@Nonnull FieldInformation> fieldInformation) {
        return new ConstructorInformation(element, containingType, fieldInformation);
    }
    
    public @Nonnull String toString() {
        return getElement() + "(" + getElement().getParameters() + ")";
    }
    
}
