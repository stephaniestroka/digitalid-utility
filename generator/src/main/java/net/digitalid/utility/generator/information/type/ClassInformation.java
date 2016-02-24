package net.digitalid.utility.generator.information.type;

import java.util.List;

import javax.annotation.Nonnull;

import net.digitalid.utility.generator.information.field.NonGeneratedFieldInformation;
import net.digitalid.utility.generator.information.method.ConstructorInformation;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.state.Unmodifiable;

/**
 * This type collects the relevant information about a class for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 */
public class ClassInformation extends TypeInformation {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    private final @Unmodifiable @Nonnull @NonNullableElements List<ConstructorInformation> constructors;
    
    /**
     * Returns the constructors that are declared in this class.
     */
    @Pure
    public @Unmodifiable @Nonnull @NonNullableElements List<ConstructorInformation> getConstructors() {
        return constructors;
    }
    
    /* -------------------------------------------------- Fields -------------------------------------------------- */
    
    /**
     * Stores the parameters of the constructor, which also represent the object.
     */
    public final @Nonnull @NonNullableElements List<ParamaterBasedFieldInformation> parameterBasedFields;
    
    /**
     * Stores the accessible fields, which can be validated by the generated subclass.
     */
    public final @Nonnull @NonNullableElements List<NonGeneratedFieldInformation> accessibleFields;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ClassInformation() {
        
    }
    
}
