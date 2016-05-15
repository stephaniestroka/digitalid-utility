package net.digitalid.utility.generator.information.field;

import javax.annotation.Nonnull;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.generators.BuilderGenerator;
import net.digitalid.utility.generator.generators.SubclassGenerator;
import net.digitalid.utility.generator.information.ElementInformation;
import net.digitalid.utility.generator.information.variable.VariableElementInformation;
import net.digitalid.utility.processor.generator.JavaFileGenerator;

/**
 * This type collects the relevant information about a field for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 * 
 * @see PotentiallyInheritedFieldInformation
 * @see RepresentingFieldInformation
 */
public interface FieldInformation extends ElementInformation, VariableElementInformation {
    
    /* -------------------------------------------------- Default Value -------------------------------------------------- */
    
    /**
     * Returns whether this field has a default value.
     */
    @Pure
    public boolean hasDefaultValue();
    
    /* -------------------------------------------------- Access -------------------------------------------------- */
    
    /**
     * Returns the code to retrieve the value of this field.
     */
    @Pure
    public @Nonnull String getAccessCode();
    
    /* -------------------------------------------------- Mutability -------------------------------------------------- */
    
    /**
     * Returns whether this field is mutable.
     */
    @Pure
    public boolean isMutable();
    
    /* -------------------------------------------------- Field Type -------------------------------------------------- */
    
    public @Nonnull String getFieldType(@Nonnull JavaFileGenerator javaFileGenerator);
    
    /* -------------------------------------------------- Is Array -------------------------------------------------- */
    
    public boolean isArray();
    
    public @Nonnull TypeMirror getComponentType();
    
}
