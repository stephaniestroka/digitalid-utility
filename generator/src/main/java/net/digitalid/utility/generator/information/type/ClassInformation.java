package net.digitalid.utility.generator.information.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.generator.BuilderGenerator;
import net.digitalid.utility.generator.SubclassGenerator;
import net.digitalid.utility.generator.information.exceptions.InvalidRecoveryParameterException;
import net.digitalid.utility.generator.information.field.DeclaredFieldInformation;
import net.digitalid.utility.generator.information.field.GeneratedFieldInformation;
import net.digitalid.utility.generator.information.field.ParameterBasedFieldInformation;
import net.digitalid.utility.generator.information.field.RepresentingFieldInformation;
import net.digitalid.utility.generator.information.filter.ElementFilter;
import net.digitalid.utility.generator.information.filter.ElementInformationNameMatcher;
import net.digitalid.utility.generator.information.filter.FilterCondition;
import net.digitalid.utility.generator.information.method.ConstructorInformation;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.generator.information.type.filter.AbstractGetterMatcher;
import net.digitalid.utility.generator.information.type.filter.AbstractSetterMatcher;
import net.digitalid.utility.generator.information.type.filter.AccessibleFieldInformationTransformer;
import net.digitalid.utility.generator.information.type.filter.FieldNameExtractor;
import net.digitalid.utility.generator.information.type.filter.ImplementedGetterMatcher;
import net.digitalid.utility.generator.information.type.filter.ParameterBasedFieldInformationTransformer;
import net.digitalid.utility.generator.information.type.filter.RecoverMethodMatcher;
import net.digitalid.utility.logging.processing.ProcessingLog;
import net.digitalid.utility.logging.processing.SourcePosition;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.state.Unmodifiable;

/**
 * This type collects the relevant information about a class for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 */
public class ClassInformation extends TypeInformation {
    
    /**
     * The class information builder collects information relevant for creating a class information object.
     */
    private static class ClassInformationBuilder implements TypeInformationBuilder {
        
        /* -------------------------------------------------- Final Fields -------------------------------------------------- */
        
        /**
         * The type element.
         */
        private final @Nonnull TypeElement typeElement;
        
        /**
         * The containing type.
         */
        private final @Nonnull DeclaredType containingType;
        
        /* -------------------------------------------------- Constructor -------------------------------------------------- */
        
        public ClassInformationBuilder(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType) {
            this.typeElement = typeElement;
            this.containingType = containingType;
            this.generatable = true;
        }
        
        /* -------------------------------------------------- Generatable -------------------------------------------------- */
        
        private boolean generatable;
        
        public boolean isGeneratable() {
            return generatable;
        }
        
        /* -------------------------------------------------- Equals Method -------------------------------------------------- */
        
        public @Nullable MethodInformation getEqualsMethod() {
            return ElementFilter.filterMethodInformation(typeElement, ElementInformationNameMatcher.<MethodInformation>withNames("equals"));
        }
        
        /* -------------------------------------------------- Hash Code Method -------------------------------------------------- */
        
        public @Nullable MethodInformation getHashCodeMethod() {
            return ElementFilter.filterMethodInformation(typeElement, ElementInformationNameMatcher.<MethodInformation>withNames("hashCode"));
        }
        
        /* -------------------------------------------------- To String Method -------------------------------------------------- */
        
        public @Nullable MethodInformation getToStringMethod() {
            return ElementFilter.filterMethodInformation(typeElement, ElementInformationNameMatcher.<MethodInformation>withNames("toString"));
        }
        
        /* -------------------------------------------------- Compare To Method -------------------------------------------------- */
        
        public @Nullable MethodInformation getCompareToMethod() {
            return ElementFilter.filterMethodInformation(typeElement, ElementInformationNameMatcher.<MethodInformation>withNames("compareTo"));
        }
        
        /* -------------------------------------------------- Clone Method  -------------------------------------------------- */
        
        public @Nullable MethodInformation getCloneMethod() {
            return ElementFilter.filterMethodInformation(typeElement, ElementInformationNameMatcher.<MethodInformation>withNames("clone"));
        }
        
        /* -------------------------------------------------- Validate Method -------------------------------------------------- */
        
        public @Nullable MethodInformation getValidateMethod() {
            return ElementFilter.filterMethodInformation(typeElement, ElementInformationNameMatcher.<MethodInformation>withNames("validate"));
        }
        
        /* -------------------------------------------------- Implemented Getters -------------------------------------------------- */
        
        public @Nonnull @NonNullableElements Map<String, MethodInformation> getImplementedGetters() {
            return ElementFilter.filterMethodInformations(typeElement, ImplementedGetterMatcher.get(), FieldNameExtractor.get());
        }
        
        /* -------------------------------------------------- Abstract Getters -------------------------------------------------- */
        
        public @Nonnull @NonNullableElements Map<String, MethodInformation> getAbstractGetters() {
            return ElementFilter.filterMethodInformations(typeElement, AbstractGetterMatcher.get(), FieldNameExtractor.get());
        }
        
        /* -------------------------------------------------- Abstract Setters -------------------------------------------------- */
        
        public @Nonnull @NonNullableElements Map<String, MethodInformation> getAbstractSetters() {
            return ElementFilter.filterMethodInformations(typeElement, AbstractSetterMatcher.get(), FieldNameExtractor.get());
        }
        
        /* -------------------------------------------------- Overriden Methods -------------------------------------------------- */
        
        public @Nonnull @NonNullableElements List<MethodInformation> getOverridenMethods() {
            return ElementFilter.filterMethodInformations(typeElement, new FilterCondition<MethodInformation>() {
                    @Override public boolean filter(@Nonnull MethodInformation methodInformation) {
                        return !methodInformation.isDeclaredInRuntimeEnvironment();
                    }
                });
        }
        
        public @Nonnull @NonNullableElements List<DeclaredFieldInformation> getAccessibleFields() {
            return ElementFilter.filterFields(typeElement, FilterCondition.AcceptAll.<DeclaredFieldInformation>get(), AccessibleFieldInformationTransformer.of(containingType));
        }
        
        /* -------------------------------------------------- Constructors -------------------------------------------------- */
        
        private @Nullable @NonNullableElements List<ConstructorInformation> constructors;
        
        public @Nonnull @NonNullableElements List<ConstructorInformation> getConstructors() {
            if (constructors == null) {
                constructors = ElementFilter.filterConstructors(typeElement);
            }
            return constructors;
        }
        
        /* -------------------------------------------------- Recover Methods -------------------------------------------------- */
        
        private @Nullable @NonNullableElements List<MethodInformation> recoverMethods;
        
        private @Nonnull @NonNullableElements List<MethodInformation> getRecoverMethods() {
            if (recoverMethods == null) {
                recoverMethods = ElementFilter.filterMethodInformations(typeElement, RecoverMethodMatcher.get());
            }
            return recoverMethods;
        }
        
        public @Nullable @NonNullableElements MethodInformation getRecoverMethod() {
            final @Nonnull @NonNullableElements List<MethodInformation> recoverMethods = getRecoverMethods();
            if (recoverMethods.size() > 0) {
                if (recoverMethods.size() > 1) {
                    ProcessingLog.information("Cannot determine the representing fields with multiple recover methods:", SourcePosition.of(typeElement));
                    generatable = false;
                } else {
                    return recoverMethods.get(0);
                }
            }
            return null;
        }
        
        /* -------------------------------------------------- Recover Executable -------------------------------------------------- */
        
        private @Nullable ExecutableElement recoverExecutable;
        
        private @Nullable ExecutableElement getRecoverExecutable() {
            if (recoverExecutable == null) {
                final @Nullable MethodInformation recoverMethod = getRecoverMethod();
                if (recoverMethod != null) {
                    recoverExecutable = recoverMethod.getElement();
                } else {
                    final @Nonnull @NonNullableElements List<ConstructorInformation> constructors = getConstructors();
                    // we always have at least one constructor in a class. (TODO: check if this is true for abstract classes as well)
                    if (constructors.size() > 1) {
                        ProcessingLog.information("Cannot determine the representing fields with several constructors and no recover method:", SourcePosition.of(typeElement));
                        generatable = false;
                    } else {
                        recoverExecutable = constructors.get(0).getElement();
                    }
                }
            }
            return recoverExecutable;
        }
        
        /* -------------------------------------------------- Parameter-based Field Information  -------------------------------------------------- */
        
        private @Nonnull ParameterBasedFieldInformation getParameterBasedFieldInformation(@Nonnull TypeElement typeElement, @Nonnull VariableElement variableElement) throws InvalidRecoveryParameterException {
            final @Nonnull String parameterName = variableElement.getSimpleName().toString();
            final @Nullable ParameterBasedFieldInformation parameterBasedFieldInformation = ElementFilter.filterField(typeElement, ElementInformationNameMatcher.<ParameterBasedFieldInformation>withNames(parameterName), ParameterBasedFieldInformationTransformer.with(typeElement));
            
            if (parameterBasedFieldInformation == null) {
                throw InvalidRecoveryParameterException.with(parameterName);
            }
            return parameterBasedFieldInformation;
        }
        
        private @Nullable @NonNullableElements List<ParameterBasedFieldInformation> parameterBasedFieldInformations;
        
        public @Nonnull @NonNullableElements List<ParameterBasedFieldInformation> getParameterBasedFieldInformations() {
            if (parameterBasedFieldInformations == null) {
                final @Nullable ExecutableElement recoverExecutable = getRecoverExecutable();
                if (recoverExecutable != null) {
                    @Nonnull @NonNullableElements List<? extends VariableElement> recoveryParameters = recoverExecutable.getParameters();
                    parameterBasedFieldInformations = new ArrayList<>(recoveryParameters.size());
                    for (@Nonnull VariableElement recoveryParameter : recoveryParameters) {
                        try {
                            final @Nullable ParameterBasedFieldInformation parameterBasedFieldInformation = getParameterBasedFieldInformation(typeElement, recoveryParameter);
                            parameterBasedFieldInformations.add(parameterBasedFieldInformation);
                        } catch (InvalidRecoveryParameterException e) {
                            ProcessingLog.information(e.getMessage());
                            generatable = false;
                        }
                    }
                } else {
                    ProcessingLog.information("Failed to retrieve the constructor or recover method", SourcePosition.of(typeElement));
                    generatable = false;
                }
            }
            return parameterBasedFieldInformations;
        }
        
        /* -------------------------------------------------- Generated Field Information -------------------------------------------------- */
        
        private @Nullable @NonNullableElements List<GeneratedFieldInformation> generatedFieldInformations;
        
        public @Nonnull @NonNullableElements List<GeneratedFieldInformation> getGeneratedFieldsInformation() {
            if (generatedFieldInformations == null) {
                 generatedFieldInformations = new ArrayList<>(getAbstractGetters().size());
                for (@Nonnull Map.Entry<String, MethodInformation> abstractGetter : getAbstractGetters().entrySet()) {
                    generatedFieldInformations.add(GeneratedFieldInformation.of(containingType, abstractGetter.getValue(), getAbstractSetters().get(abstractGetter.getKey())));
                }
            }
            return generatedFieldInformations;
        }
        
        /* -------------------------------------------------- Representing Field Information -------------------------------------------------- */
        
        /**
         * Combines and returns the parameter-based fields and the generated fields of the type.
         */
        public @Nonnull @NonNullableElements List<RepresentingFieldInformation> getRepresentingFieldInformation() {
            final @Nonnull @NonNullableElements List<RepresentingFieldInformation> representingFieldInformations = new ArrayList<>(getGeneratedFieldsInformation().size() + getParameterBasedFieldInformations().size());
            representingFieldInformations.addAll(getParameterBasedFieldInformations());
            representingFieldInformations.addAll(getGeneratedFieldsInformation());
            return representingFieldInformations;
        }
        
    }
    
    /* -------------------------------------------------- Generated Methods -------------------------------------------------- */
    
    public final @Nullable MethodInformation recoverMethod;
    
    public final @Nullable MethodInformation equalsMethod;
    
    public final @Nullable MethodInformation hashCodeMethod;
    
    public final @Nullable MethodInformation toStringMethod;
    
    public final @Nullable MethodInformation compareToMethod;
    
    public final @Nullable MethodInformation cloneMethod;
    
    public final @Nullable MethodInformation validateMethod;
    
    public final @Nonnull @NonNullableElements List<MethodInformation> overriddenMethods;
    
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
     * Stores the fields associated with the parameters of the constructor, which also represent the object.
     */
    public final @Nonnull @NonNullableElements List<ParameterBasedFieldInformation> parameterBasedFieldInformations;
    
    /**
     * Stores the accessible fields, which can be validated by the generated subclass.
     */
    public final @Nonnull @NonNullableElements List<DeclaredFieldInformation> accessibleFields;
    
    /**
     * Stores the implemented getters for the fields in the type.
     */
    public final @Nonnull @NonNullableElements Map<String, MethodInformation> implementedGetters;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a class information object with the help of the given class information helper.
     */
    protected ClassInformation(@Nonnull TypeElement element, @Nonnull DeclaredType containingType, @Nonnull ClassInformationBuilder builder) {
        super(element, containingType, builder);
        
        this.equalsMethod = builder.getEqualsMethod();
        this.hashCodeMethod = builder.getHashCodeMethod();
        this.toStringMethod= builder.getToStringMethod();
        this.compareToMethod = builder.getCompareToMethod();
        this.cloneMethod = builder.getCloneMethod();
        this.validateMethod = builder.getValidateMethod();
        
        this.implementedGetters = builder.getImplementedGetters();
        this.overriddenMethods = builder.getOverridenMethods();
        this.constructors = builder.getConstructors();
        this.recoverMethod = builder.getRecoverMethod();
        this.parameterBasedFieldInformations = builder.getParameterBasedFieldInformations();
        this.accessibleFields = builder.getAccessibleFields();
    }
    
    /**
     * Returns a class information object of the given type typeElement and containing type.
     */
    public static @Nonnull ClassInformation of(@Nonnull TypeElement element, @Nonnull DeclaredType containingType) {
        final @Nonnull ClassInformationBuilder builder = new ClassInformationBuilder(element, containingType);
        
        return new ClassInformation(element, containingType, builder);
    }
     
}
