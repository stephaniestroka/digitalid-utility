package net.digitalid.utility.generator.information.field;

import javax.annotation.Nonnull;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.generator.generators.BuilderGenerator;
import net.digitalid.utility.generator.generators.SubclassGenerator;
import net.digitalid.utility.processing.logging.SourcePosition;

/**
 * This type collects the relevant information about a directly accessible field for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
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
    
    /* -------------------------------------------------- Access -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean isAccessible() {
        return true;
    }
    
    @Pure
    @Override
    public @Nonnull String getAccessCode() {
        return field.getSimpleName().toString();
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
