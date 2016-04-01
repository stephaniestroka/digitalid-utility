package net.digitalid.utility.generator.information.method;

import javax.annotation.Nonnull;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.annotations.method.Pure;

/**
 * This type collects the relevant information about a constructor for generating a {@link net.digitalid.utility.generator.SubclassGenerator subclass} and {@link net.digitalid.utility.generator.BuilderGenerator builder}.
 */
public class ConstructorInformation extends ExecutableInformation {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ConstructorInformation(@Nonnull ExecutableElement element, @Nonnull DeclaredType containingType) {
        super(element, containingType);
        
        Require.that(element.getKind() == ElementKind.CONSTRUCTOR).orThrow("The element $ has to be a constructor.", SourcePosition.of(element));
    }
    
    /**
     * Returns the constructor information of the given constructor element and containing type.
     * 
     * @require element.getKind() == ElementKind.CONSTRUCTOR : "The element has to be a constructor.";
     */
    @Pure
    public static @Nonnull ConstructorInformation of(@Nonnull ExecutableElement element, @Nonnull DeclaredType containingType) {
        return new ConstructorInformation(element, containingType);
    }
    
}
