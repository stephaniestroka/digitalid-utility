package net.digitalid.utility.generator.information.field;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.generator.annotations.DefaultValue;
import net.digitalid.utility.generator.information.ElementInformationImplementation;
import net.digitalid.utility.processor.ProcessingUtility;
import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * This class implements the {@link FieldInformation} interface.
 */
public class FieldInformationImplementation extends ElementInformationImplementation {
    
    /* -------------------------------------------------- Default Value -------------------------------------------------- */
    
    private final @Nullable String defaultValue;
    
    /**
     * Returns whether this field has a default value.
     */
    @Pure
    public boolean hasDefaultValue() {
        return defaultValue != null;
    }
    
    /**
     * Returns the default value of this field.
     * 
     * @require hasDefaultValue() : "This field has to have a default value.";
     */
    @Pure
    @SuppressWarnings("null")
    public @Nonnull String getDefaultValue() {
        Require.that(hasDefaultValue()).orThrow("This field has to have a default value.");
        
        return defaultValue;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected FieldInformationImplementation(@Nonnull Element element, @Nonnull TypeMirror type, @Nonnull DeclaredType containingType) {
        super(element, type, containingType);
        
        this.defaultValue = ProcessingUtility.getStringValue(element, DefaultValue.class);
    }
    
}
