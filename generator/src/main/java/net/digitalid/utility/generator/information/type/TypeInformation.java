package net.digitalid.utility.generator.information.type;

import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.generator.BuilderGenerator;
import net.digitalid.utility.generator.SubclassGenerator;
import net.digitalid.utility.generator.information.ElementInformationImplementation;
import net.digitalid.utility.generator.information.field.GeneratedFieldInformation;
import net.digitalid.utility.generator.information.field.RepresentingFieldInformation;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * This type collects the relevant information about a type for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 * 
 * @see InterfaceInformation
 * @see ClassInformation
 */
public abstract class TypeInformation extends ElementInformationImplementation {
    
    /* -------------------------------------------------- Type Information Builder -------------------------------------------------- */
    
    protected interface TypeInformationBuilder {
        
        public boolean isGeneratable();
        
        public @Nonnull @NonNullableElements Map<String, MethodInformation> getAbstractGetters();
        
        public @Nonnull @NonNullableElements Map<String, MethodInformation> getAbstractSetters();
    
        public @Nonnull @NonNullableElements List<GeneratedFieldInformation> getGeneratedFieldsInformation();
    
        public @Nonnull @NonNullableElements List<RepresentingFieldInformation> getRepresentingFieldInformation();
    }
    
    /**
     * Either the method declarations of an interface, or the abstract methods of an implemented class.
     */
    protected final @Nonnull @NonNullableElements Map<String, MethodInformation> abstractGetters;
    
    protected final @Nonnull @NonNullableElements Map<String, MethodInformation> abstractSetters;
    
    /* -------------------------------------------------- Element -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull TypeElement getElement() {
        return (TypeElement) super.getElement();
    }
    
    /* -------------------------------------------------- Type -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull DeclaredType getType() {
        return (DeclaredType) super.getType();
    }
    
    /* -------------------------------------------------- Subclass -------------------------------------------------- */
    
    @Pure
    public @Nonnull String getSimpleNameOfGeneratedSubclass() {
        return "Generated" + getName();
    }
    
    @Pure
    public @Nonnull String getQualifiedNameOfGeneratedSubclass() {
        return getPackageName() + "." + getSimpleNameOfGeneratedSubclass();
    }
    
    /* -------------------------------------------------- Builder -------------------------------------------------- */
    
    @Pure
    public @Nonnull String getSimpleNameOfGeneratedBuilder() {
        return getName() + "Builder";
    }
    
    @Pure
    public @Nonnull String getQualifiedNameOfGeneratedBuilder() {
        return getPackageName() + "." + getSimpleNameOfGeneratedBuilder();
    }
    
    /* -------------------------------------------------- Generatable -------------------------------------------------- */
    
    private final boolean generatable;
    
    /**
     * Returns whether a subclass can be generated for this type.
     */
    @Pure
    public boolean isGeneratable() {
        return generatable;
    }
    
    /* -------------------------------------------------- Fields -------------------------------------------------- */
    
    public final @Nonnull @NonNullableElements List<GeneratedFieldInformation> generatedFieldInformations;
    
    public final @Nonnull @NonNullableElements List<RepresentingFieldInformation> representingFieldInformations;
    
    public @Nonnull @NonNullableElements List<RepresentingFieldInformation> getRepresentingFields() {
        return representingFieldInformations;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected TypeInformation(@Nonnull TypeElement element, @Nonnull DeclaredType containingType, @Nonnull TypeInformationBuilder builder) {
        super(element, element.asType(), containingType);
        
        this.abstractGetters = builder.getAbstractGetters();
        this.abstractSetters = builder.getAbstractSetters();
        this.generatable = builder.isGeneratable();
        
        this.generatedFieldInformations = builder.getGeneratedFieldsInformation();
        this.representingFieldInformations = builder.getRepresentingFieldInformation();
    }
    
}
