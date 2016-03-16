package net.digitalid.utility.generator.information.field;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.logging.processing.SourcePosition;
import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * This type collects the relevant information about a non-directly accessible field for generating a {@link net.digitalid.utility.generator.SubclassGenerator subclass} and {@link net.digitalid.utility.generator.BuilderGenerator builder}.
 * 
 * @see NonDirectlyAccessibleParameterBasedFieldInformation
 * @see GeneratedFieldInformation
 */
public abstract class NonDirectlyAccessibleFieldInformation extends FieldInformationImplementation {
    
    /* -------------------------------------------------- Getter -------------------------------------------------- */
    
    private final @Nonnull MethodInformation getter;
    
    /**
     * Returns the getter through which the value can be accessed.
     * 
     * @ensure result.isGetter() : "The returned method is a getter.";
     */
    @Pure
    public @Nonnull MethodInformation getGetter() {
        return getter;
    }
    
    @Pure
    @Override
    public @Nonnull String getAccessCode() {
        return getter.getName() + "()";
    }
    
    /* -------------------------------------------------- Setter -------------------------------------------------- */
    
    private final @Nullable MethodInformation setter;
    
    /**
     * Returns whether this field has a setter.
     */
    @Pure
    public boolean hasSetter() {
        return setter != null;
    }
    
    /**
     * Returns the setter through which the value can be modified.
     * 
     * @ensure result.isSetter() : "The returned method is a setter.";
     */
    @Pure
    @SuppressWarnings("null")
    public @Nonnull MethodInformation getSetter() {
        Require.that(hasSetter()).orThrow("The setter may not be null.");
        
        return setter;
    }
    
    /* -------------------------------------------------- Mutability -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean isMutable() {
        return hasSetter();
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected NonDirectlyAccessibleFieldInformation(@Nonnull Element element, @Nonnull TypeMirror type, @Nonnull DeclaredType containingType, @Nonnull MethodInformation getter, @Nullable MethodInformation setter) {
        super(element, type, containingType);
        
        Require.that(getter.isGetter()).orThrow("The method $ has to be a getter.", SourcePosition.of(getter.getElement()));
        Require.that(setter == null || setter.isSetter()).orThrow("The method $ has to be null or a setter.", setter == null ? null : SourcePosition.of(setter.getElement()));
        
        this.getter = getter;
        this.setter = setter;
    }
    
}
