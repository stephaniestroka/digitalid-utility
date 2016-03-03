package net.digitalid.utility.generator.information.type;

import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.functional.Predicate;
import net.digitalid.utility.generator.BuilderGenerator;
import net.digitalid.utility.generator.SubclassGenerator;
import net.digitalid.utility.generator.information.ElementInformationImplementation;
import net.digitalid.utility.generator.information.exceptions.UnexpectedTypeContentException;
import net.digitalid.utility.generator.information.field.GeneratedFieldInformation;
import net.digitalid.utility.generator.information.field.RepresentingFieldInformation;
import net.digitalid.utility.generator.information.filter.ElementFilter;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.generator.information.type.filter.AbstractGetterMatcher;
import net.digitalid.utility.generator.information.type.filter.AbstractSetterMatcher;
import net.digitalid.utility.generator.information.type.filter.FieldNameExtractor;
import net.digitalid.utility.logging.processing.AnnotationProcessingEnvironment;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * This type collects the relevant information about a type for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 * 
 * @see InterfaceInformation
 * @see ClassInformation
 */
public abstract class TypeInformation extends ElementInformationImplementation {
    
    // TODO: get all method information by iterating through java's ElementFilter.methodsIn(..) and transforming them into MethodInformation object.
    // TODO: Implement getAbstractGetters() and getAbstractSetters() by filtering the available MethodInformation iterable.
    
    public @Nonnull @NonNullableElements Map<String, MethodInformation> getAbstractGetters() {
        return null; // TODO: implement
    }
    
    public @Nonnull @NonNullableElements Map<String, MethodInformation> getAbstractSetters() {
        return null; // TODO: implement
    }
    
    public @Nonnull @NonNullableElements List<GeneratedFieldInformation> getGeneratedFieldsInformation(){
        return null; // TODO: implement
    }
    
    public abstract @Nonnull @NonNullableElements List<RepresentingFieldInformation> getRepresentingFieldInformation() throws UnexpectedTypeContentException;
    
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
    
    /**
     * Returns whether a subclass can be generated for this type.
     */
    @Pure
    public abstract boolean isGeneratable();
    
    /* -------------------------------------------------- Fields -------------------------------------------------- */
    
    public final @Nonnull @NonNullableElements List<GeneratedFieldInformation> generatedFieldInformations;
    
    public final @Nonnull @NonNullableElements List<RepresentingFieldInformation> representingFieldInformations;
    
    public @Nonnull @NonNullableElements List<RepresentingFieldInformation> getRepresentingFields() {
        return representingFieldInformations;
    }
    
    private static final Predicate<MethodInformation> abstractGetterPredicate = new Predicate {
        
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected TypeInformation(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType) {
        super(element, element.asType(), containingType);
        
        final @Nonnull Iterable<MethodInformation> methodInformations = ElementFilter.allMethodInformation(typeElement);
        
        FluentIterable fluentIterable = new FluentIterable(javax.lang.model.util.ElementFilter.methodsIn(AnnotationProcessingEnvironment.getElementUtils().getAllMembers(typeElement));
        
        this.abstractGetters = fluentIterable.map(methodInformationFunction).filter(AbstractGetterMatcher.get()).map(FieldNameExtractor.get());
        
        this.abstractSetters = ElementFilter.filterMethodInformations(methodInformations, AbstractSetterMatcher.get(), FieldNameExtractor.get());
        
        this.generatedFieldInformations = getGeneratedFieldsInformation();
        try {
            this.representingFieldInformations = getRepresentingFieldInformation();
        } catch (UnexpectedTypeContentException e) {
            e.printStackTrace();
        }
    }
    
}
