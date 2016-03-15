package net.digitalid.utility.generator.information.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.functional.iterable.NonNullableIterable;
import net.digitalid.utility.functional.iterable.NullableIterable;
import net.digitalid.utility.functional.iterable.filter.predicate.NonNullPredicate;
import net.digitalid.utility.functional.iterable.map.function.NonNullToNonNullUnaryFunction;
import net.digitalid.utility.generator.BuilderGenerator;
import net.digitalid.utility.generator.SubclassGenerator;
import net.digitalid.utility.generator.information.ElementInformationImplementation;
import net.digitalid.utility.generator.information.exceptions.UnexpectedTypeContentException;
import net.digitalid.utility.generator.information.field.FieldInformationFactory;
import net.digitalid.utility.generator.information.field.GeneratedFieldInformation;
import net.digitalid.utility.generator.information.field.RepresentingFieldInformation;
import net.digitalid.utility.generator.information.method.ConstructorInformation;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.logging.processing.StaticProcessingEnvironment;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * This type collects the relevant information about a type for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 * 
 * @see InterfaceInformation
 * @see ClassInformation
 */
public abstract class TypeInformation extends ElementInformationImplementation {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    public abstract @Nonnull @NonNullableElements List<ConstructorInformation> getConstructors();
    
    /* -------------------------------------------------- Representing Field Information -------------------------------------------------- */
    
    // TODO: improve the exception
    public abstract @Nonnull @NonNullableElements List<RepresentingFieldInformation> getRepresentingFieldInformation() throws UnexpectedTypeContentException;
    
    /* -------------------------------------------------- Overriden Methods -------------------------------------------------- */
    
    public abstract @Nonnull @NonNullableElements List<MethodInformation> getOverriddenMethods();
    
    /* -------------------------------------------------- Abstract Methods -------------------------------------------------- */
    
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
    
    public final @Nonnull @NonNullableElements List<GeneratedFieldInformation> generatedFieldInformation;
    
    protected static final @Nonnull NonNullToNonNullUnaryFunction<ExecutableElement, MethodInformation, DeclaredType> methodInformationFunction = new NonNullToNonNullUnaryFunction<ExecutableElement, MethodInformation, DeclaredType>() {
        
        @Override
        public @Nonnull MethodInformation apply(@Nonnull ExecutableElement element, @Nullable DeclaredType containingType) {
            assert containingType != null;
            return MethodInformation.of(element, containingType);
        }
        
    };
    
    private final static @Nonnull NonNullPredicate<MethodInformation, Object> abstractGetterPredicate = new NonNullPredicate<MethodInformation, Object>() {
        
        @Override
        public boolean apply(@Nonnull MethodInformation object, @Nullable Object none) {
            return object.isGetter() && object.isAbstract();
        }
        
    };
    
    private final static @Nonnull NonNullPredicate<MethodInformation, Object> abstractSetterPredicate = new NonNullPredicate<MethodInformation, Object>() {
        
        @Override
        public boolean apply(@Nonnull MethodInformation object, @Nullable Object none) {
            return object.isSetter() && object.isAbstract();
        }
        
    };
    
    protected @Nonnull @NonNullableElements Map<String, MethodInformation> indexMethodInformation(@Nonnull @NonNullableElements Iterable<MethodInformation> iterable) {
        final @Nonnull @NonNullableElements Map<String, MethodInformation> indexedMethods = new HashMap<>();
        for (@Nonnull MethodInformation method : iterable) {
            indexedMethods.put(method.getFieldName(), method);
        }
        return indexedMethods;
    }
    
    protected @Nonnull @NonNullableElements NonNullableIterable<MethodInformation> getMethodInformation(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType) {
        return NullableIterable.ofNonNullableElements(javax.lang.model.util.ElementFilter.methodsIn(StaticProcessingEnvironment.getElementUtils().getAllMembers(typeElement))).map(methodInformationFunction, containingType);
    }
    
    /* -------------------------------------------------- Utility -------------------------------------------------- */
    
    protected static @Nonnull NonNullToNonNullUnaryFunction<? super RepresentingFieldInformation, RepresentingFieldInformation, Object> castToRepresentingFieldInformation = new NonNullToNonNullUnaryFunction<RepresentingFieldInformation, RepresentingFieldInformation, Object>() {
        
        @Override 
        public @Nonnull RepresentingFieldInformation apply(@Nonnull RepresentingFieldInformation element, @Nullable Object additionalInformation) {
            return element;
        }
        
    };
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    protected TypeInformation(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType) {
        super(typeElement, typeElement.asType(), containingType);
        
        final @Nonnull NonNullableIterable<MethodInformation> methodInformation = getMethodInformation(typeElement, containingType);
        
        this.abstractGetters = indexMethodInformation(methodInformation.filter(abstractGetterPredicate));
    
        this.abstractSetters = indexMethodInformation(methodInformation.filter(abstractSetterPredicate));
        
        this.generatedFieldInformation = FieldInformationFactory.getGeneratedFieldInformation(methodInformation).toList();
        
        this.generatable = true;
        
        // TODO: assert that all abstract methods can be implemented.
    }
    
}
