package net.digitalid.utility.generator.information.field;

import javax.annotation.Nonnull;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.logging.processing.SourcePosition;
import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This type collects the relevant information about a directly accessible field for generating a {@link net.digitalid.utility.generator.SubclassGenerator subclass} and {@link net.digitalid.utility.generator.BuilderGenerator builder}.
 * 
 * @see DirectlyAccessibleParameterBasedFieldInformation
 * @see DirectlyAccessibleDeclaredFieldInformation
 */
public abstract class DirectlyAccessibleFieldInformation extends FieldInformationImplementation {
    
    /* -------------------------------------------------- Field -------------------------------------------------- */
    
    private final @Nonnull VariableElement field;
    
    /**
     * Returns the field through which the value can be accessed.
     * 
     * @ensure result.getKind() == ElementKind.FIELD : "The returned element is a field.";
     */
    @Pure
    public @Nonnull VariableElement getField() {
        return field;
    }
    
    @Pure
    @Override
    public @Nonnull String getAccessCode() {
        return "this." + field.getSimpleName();
    }
    
    /* -------------------------------------------------- Mutability -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean isMutable() {
        return !isFinal();
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected DirectlyAccessibleFieldInformation(@Nonnull Element element, @Nonnull TypeMirror type, @Nonnull DeclaredType containingType, @Nonnull VariableElement field) {
        super(element, type, containingType);
        
        Require.that(field.getKind() == ElementKind.FIELD).orThrow("The element $ has to be a field.", SourcePosition.of(field));
        
        this.field = field;
    }
    
}
