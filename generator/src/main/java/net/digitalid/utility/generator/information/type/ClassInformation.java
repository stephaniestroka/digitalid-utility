package net.digitalid.utility.generator.information.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.functional.iterable.NonNullIterable;
import net.digitalid.utility.functional.iterable.NullableIterable;
import net.digitalid.utility.functional.iterable.exceptions.UnexpectedResultException;
import net.digitalid.utility.functional.iterable.filter.predicate.NonNullPredicate;
import net.digitalid.utility.functional.iterable.map.function.NonNullToNonNullUnaryFunction;
import net.digitalid.utility.generator.BuilderGenerator;
import net.digitalid.utility.generator.SubclassGenerator;
import net.digitalid.utility.generator.annotations.Recover;
import net.digitalid.utility.generator.information.exceptions.UnexpectedTypeContentException;
import net.digitalid.utility.generator.information.field.DeclaredFieldInformation;
import net.digitalid.utility.generator.information.field.FieldInformationFactory;
import net.digitalid.utility.generator.information.field.ParameterBasedFieldInformation;
import net.digitalid.utility.generator.information.field.RepresentingFieldInformation;
import net.digitalid.utility.generator.information.method.ConstructorInformation;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.logging.processing.ProcessingLog;
import net.digitalid.utility.logging.processing.SourcePosition;
import net.digitalid.utility.logging.processing.StaticProcessingEnvironment;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.state.Unmodifiable;

/**
 * This type collects the relevant information about a class for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 */
public class ClassInformation extends TypeInformation {
    
    /* -------------------------------------------------- Generated Methods -------------------------------------------------- */
    
    public final @Nullable MethodInformation recoverMethod;
    
    public final @Nullable MethodInformation equalsMethod;
    
    public final @Nullable MethodInformation hashCodeMethod;
    
    public final @Nullable MethodInformation toStringMethod;
    
    public final @Nullable MethodInformation compareToMethod;
    
    public final @Nullable MethodInformation cloneMethod;
    
    public final @Nullable MethodInformation validateMethod;
    
    /* -------------------------------------------------- Overriden Methods -------------------------------------------------- */
    
    private final @Nonnull @NonNullableElements List<MethodInformation> overriddenMethods;
    
    @Pure
    @Override
    public @Nonnull @NonNullableElements List<MethodInformation> getOverriddenMethods() {
        return overriddenMethods;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    private final @Unmodifiable @Nonnull @NonNullableElements List<ConstructorInformation> constructors;
    
    /**
     * Returns the constructors that are declared in this class.
     */
    @Pure
    @Override
    public @Unmodifiable @Nonnull @NonNullableElements List<ConstructorInformation> getConstructors() {
        return constructors;
    }
    
    /* -------------------------------------------------- Fields -------------------------------------------------- */
    
    /**
     * Stores the fields associated with the parameters of the constructor, which also represent the object.
     */
    public final @Nonnull @NonNullableElements List<ParameterBasedFieldInformation> parameterBasedFieldInformation;
    
    /**
     * Stores the accessible fields, which can be validated by the generated subclass.
     */
    public final @Nonnull @NonNullableElements List<DeclaredFieldInformation> accessibleFields;
    
    /**
     * Stores the implemented getters for the fields in the type.
     */
    public final @Nonnull @NonNullableElements Map<String, MethodInformation> implementedGetters;
    
    /* -------------------------------------------------- Parameter Variables -------------------------------------------------- */
    
    @SuppressWarnings("unchecked")
    public @Nonnull @NonNullableElements List<VariableElement> getParameterVariables(@Nonnull ConstructorInformation constructorInformation, @Nullable MethodInformation recoverMethod) throws UnexpectedTypeContentException, UnexpectedResultException {
        final @Nullable ExecutableElement recoverExecutable;
        if (recoverMethod != null) {
            recoverExecutable = recoverMethod.getElement();
        } else {
            recoverExecutable = constructorInformation.getElement();
        }
        return (List<VariableElement>) recoverExecutable.getParameters();
    }
    
    /* -------------------------------------------------- Representing Field Information -------------------------------------------------- */
    
    /**
     * Combines and returns the parameter-based fields and the generated fields of the type.
     */
    @Override
    @SuppressWarnings("unchecked")
    public @Nonnull @NonNullableElements List<RepresentingFieldInformation> getRepresentingFieldInformation() throws UnexpectedTypeContentException {
        return NullableIterable.ofNonNullElements(parameterBasedFieldInformation).map(castToRepresentingFieldInformation).combine(NullableIterable.ofNonNullElements(generatedFieldInformation).map(castToRepresentingFieldInformation)).toList();
    }
    
    /* -------------------------------------------------- Generatable -------------------------------------------------- */
    
    private final boolean generatable;
    
    @Override
    public boolean isGeneratable() {
        return super.isGeneratable() && generatable;
    }
    
    /* -------------------------------------------------- Predicates -------------------------------------------------- */
    
    protected static final @Nonnull NonNullPredicate<MethodInformation, String> methodNameMatcher = new NonNullPredicate<MethodInformation, String>() {
        
        @Override 
        public boolean apply(@Nonnull MethodInformation methodInformation, @Nullable String name) {
            assert name != null;
            return methodInformation.getName().equals(name);
        }
        
    };
    
    protected static final @Nonnull NonNullPredicate<MethodInformation, Object> recoverMethodMatcher = new NonNullPredicate<MethodInformation, Object>() {
        
        final @Nonnull TypeMirror recoverAnnotationType = StaticProcessingEnvironment.getElementUtils().getTypeElement(Recover.class.getName()).asType();
        
        @Override 
        public boolean apply(@Nonnull MethodInformation methodInformation, @Nullable Object none) {
            for (@Nonnull AnnotationMirror annotationMirror : methodInformation.getAnnotations()) {
                if (annotationMirror.getAnnotationType().equals(recoverAnnotationType)) {
                    return true;
                }
            }
            return false;
        }
        
    };
    
    protected static final @Nonnull NonNullPredicate<MethodInformation, Object> implementedGetterMatcher = new NonNullPredicate<MethodInformation, Object>() {
        
        @Override 
        public boolean apply(@Nonnull MethodInformation methodInformation, @Nullable Object none) {
            return !methodInformation.isAbstract() && methodInformation.isGetter();
        }
        
    };
    
    protected static final @Nonnull NonNullPredicate<MethodInformation, Object> noJavaRuntimeMethod = new NonNullPredicate<MethodInformation, Object>() {
        
        @Override 
        public boolean apply(@Nonnull MethodInformation methodInformation, @Nullable Object none) {
            return !methodInformation.isDeclaredInRuntimeEnvironment();
        }
        
    };
    
    /* -------------------------------------------------- Functions -------------------------------------------------- */
    
    protected static final @Nonnull NonNullToNonNullUnaryFunction<ExecutableElement, ConstructorInformation, DeclaredType> constructorInformationFunction = new NonNullToNonNullUnaryFunction<ExecutableElement, ConstructorInformation, DeclaredType>() {
        
        @Nonnull @Override public ConstructorInformation apply(@Nonnull ExecutableElement element, @Nullable DeclaredType containingType) {
            assert containingType != null;
            return ConstructorInformation.of(element, containingType);
        }
        
    };
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a class information object with the help of the given class information helper.
     */
    protected ClassInformation(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType) {
        super(typeElement, containingType);
    
        boolean generatable = true;
        @Nullable MethodInformation equalsMethod = null;
        @Nullable MethodInformation hashCodeMethod = null;
        @Nullable MethodInformation toStringMethod = null;
        @Nullable MethodInformation compareToMethod = null;
        @Nullable MethodInformation cloneMethod = null;
        @Nullable MethodInformation validateMethod = null;
        @Nonnull Map<String, MethodInformation> implementedGetters = new HashMap<>();
        @Nonnull List<MethodInformation> overriddenMethods = new ArrayList<>();
        @Nonnull List<ConstructorInformation> constructors = new ArrayList<>();
        @Nullable MethodInformation recoverMethod = null;
        @Nonnull @NonNullableElements List<ParameterBasedFieldInformation> parameterBasedFieldInformation = new ArrayList<>();
        @Nonnull List<DeclaredFieldInformation> accessibleFields = new ArrayList<>();
        
        try {
            final @Nonnull NonNullIterable<MethodInformation> methodInformationIterable = getMethodInformation(typeElement, containingType);
            
            equalsMethod = methodInformationIterable.find(methodNameMatcher, "equals");
            hashCodeMethod = methodInformationIterable.find(methodNameMatcher, "hashCode");
            toStringMethod = methodInformationIterable.find(methodNameMatcher, "toString");
            compareToMethod = methodInformationIterable.find(methodNameMatcher, "compareTo");
            cloneMethod = methodInformationIterable.find(methodNameMatcher, "clone");
            validateMethod = methodInformationIterable.find(methodNameMatcher, "validate");
            
            implementedGetters = indexMethodInformation(methodInformationIterable.filter(implementedGetterMatcher));
            
            overriddenMethods = methodInformationIterable.filter(noJavaRuntimeMethod).toList();
            
            constructors = NullableIterable.ofNonNullElements(javax.lang.model.util.ElementFilter.constructorsIn(StaticProcessingEnvironment.getElementUtils().getAllMembers(typeElement))).map(constructorInformationFunction).toList();
            
            recoverMethod = methodInformationIterable.find(recoverMethodMatcher);
            parameterBasedFieldInformation = FieldInformationFactory.getParameterBasedFieldInformation(typeElement, containingType, NonNullIterable.ofNonNullElements(getParameterVariables(constructors.get(0), recoverMethod)), methodInformationIterable);
            
            accessibleFields = FieldInformationFactory.getDirectlyAccessibleFieldInformation(typeElement, containingType).toList();
            
        } catch (UnexpectedResultException | UnexpectedTypeContentException  e) {
            ProcessingLog.information(e.getMessage(), SourcePosition.of(typeElement));
            generatable = false;
        } finally {
            this.equalsMethod = equalsMethod;
            this.hashCodeMethod = hashCodeMethod;
            this.toStringMethod = toStringMethod;
            this.compareToMethod = compareToMethod;
            this.cloneMethod = cloneMethod;
            this.validateMethod = validateMethod;
            this.implementedGetters = implementedGetters;
            this.overriddenMethods = overriddenMethods;
            this.constructors = constructors;
            this.recoverMethod = recoverMethod;
            this.parameterBasedFieldInformation = parameterBasedFieldInformation;
            this.accessibleFields = accessibleFields;
            this.generatable = generatable;
        }
    }
    
    /**
     * Returns a class information object of the given type typeElement and containing type.
     */
    public static @Nonnull ClassInformation of(@Nonnull TypeElement element, @Nonnull DeclaredType containingType) {
        return new ClassInformation(element, containingType);
    }
     
}
