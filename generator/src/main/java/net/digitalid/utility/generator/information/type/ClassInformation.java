package net.digitalid.utility.generator.information.type;

import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;

import net.digitalid.utility.exceptions.ConformityViolation;
import net.digitalid.utility.functional.function.unary.NonNullToNonNullUnaryFunction;
import net.digitalid.utility.functional.iterable.InfiniteNonNullableIterable;
import net.digitalid.utility.functional.iterable.NonNullableIterable;
import net.digitalid.utility.functional.iterable.NullableIterable;
import net.digitalid.utility.functional.iterable.map.function.implementation.GetNonNull0Function;
import net.digitalid.utility.functional.predicate.NonNullablePredicate;
import net.digitalid.utility.generator.BuilderGenerator;
import net.digitalid.utility.generator.SubclassGenerator;
import net.digitalid.utility.generator.annotations.Recover;
import net.digitalid.utility.generator.information.exceptions.UnexpectedTypeContentException;
import net.digitalid.utility.generator.information.field.DeclaredFieldInformation;
import net.digitalid.utility.generator.information.field.DirectlyAccessibleParameterBasedFieldInformation;
import net.digitalid.utility.generator.information.field.FieldInformationFactory;
import net.digitalid.utility.generator.information.field.NonDirectlyAccessibleParameterBasedFieldInformation;
import net.digitalid.utility.generator.information.field.ParameterBasedFieldInformation;
import net.digitalid.utility.generator.information.field.RepresentingFieldInformation;
import net.digitalid.utility.generator.information.method.ConstructorInformation;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.generator.query.MethodSignatureMatcher;
import net.digitalid.utility.logging.processing.StaticProcessingEnvironment;
import net.digitalid.utility.string.StringCase;
import net.digitalid.utility.tuples.pair.NonNullablePair;
import net.digitalid.utility.tuples.quartet.NonNullableQuartet;
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
    public @Nonnull @NonNullableElements List<VariableElement> getParameterVariables(@Nonnull ConstructorInformation constructorInformation, @Nullable MethodInformation recoverMethod) {
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
     * A predicate that returns true iff a given method information matches a given method signature.
     */
    private static @Nonnull NonNullablePredicate<NonNullablePair<MethodInformation, MethodSignatureMatcher>> methodMatcher = new NonNullablePredicate<NonNullablePair<MethodInformation, MethodSignatureMatcher>>() {
        
        @Override
        public boolean apply(@Nonnull NonNullablePair<MethodInformation, MethodSignatureMatcher> pair) {
            final @Nonnull MethodInformation methodInformation = pair.get0();
            final @Nonnull MethodSignatureMatcher methodSignatureMatcher = pair.get1();
            return methodSignatureMatcher.matches(methodInformation);
        }
        
    };
    
    /**
     * A predicate that returns true iff a given variable element name matches a given string.
     */
    private static @Nonnull NonNullablePredicate<NonNullablePair<VariableElement, String>> variableElementNameMatcher = new NonNullablePredicate<NonNullablePair<VariableElement, String>>() {
        
        @Override
        public boolean apply(@Nonnull NonNullablePair<VariableElement, String> pair) {
            final @Nonnull VariableElement variableElement = pair.get0();
            final @Nonnull String name = pair.get1();
            return variableElement.getSimpleName().contentEquals(name);
        }
        
    };
    
    private final static @Nonnull GetNonNull0Function<MethodInformation, MethodSignatureMatcher> GET_METHODINFORMATION = new GetNonNull0Function<>();
    
    /**
     * Returns a method information object that matches the expected declaration of a getter for a certain field.
     * A {@link ConformityViolation conformity violation exception} is thrown if the getter was not found.
     */
    private static @Nonnull MethodInformation getGetterOf(@Nonnull String fieldName, @Nonnull @NonNullableElements NonNullableIterable<MethodInformation> methodsOfType) {
        final @Nonnull String nameRegex = "(get|has|is)" + StringCase.capitalizeFirstLetters(fieldName);
        final @Nonnull NonNullableIterable<MethodSignatureMatcher> methodSignature = InfiniteNonNullableIterable.ofNonNullableElement(MethodSignatureMatcher.of(nameRegex, new TypeElement[0]));
        final @Nullable MethodInformation methodInformation = methodsOfType.zipNonNull(methodSignature).findFirst(methodMatcher, GET_METHODINFORMATION);
        if (methodInformation == null) {
            throw ConformityViolation.with("Representative fields must be accessible, either directly or through a getter, but the getter for the field '" + fieldName + "' was not found");
        }
        return methodInformation;
    }
    
    /**
     * Returns a method information object that matches the expected declaration of a setter for a certain field.
     * If the setter was not found, null is returned.
     */
    private static @Nullable MethodInformation getSetterOf(@Nonnull String fieldName, @Nonnull TypeElement fieldType, @Nonnull @NonNullableElements NonNullableIterable<MethodInformation> methodsOfType) {
        final @Nonnull String methodName = "set" + StringCase.capitalizeFirstLetters(fieldName);
        final @Nonnull NonNullableIterable<MethodSignatureMatcher> methodSignature = InfiniteNonNullableIterable.ofNonNullableElement(MethodSignatureMatcher.of(methodName, new TypeElement[] { fieldType }));
        return methodsOfType.zipNonNull(methodSignature).findFirst(methodMatcher, GET_METHODINFORMATION);
    }
    
    /**
     * A unary function that transforms a quartet that consists of a parameter variable element, an iterable of field variable elements, a containing type and an iterable of method informations to a parameter-based field information object.
     */
    private static NonNullToNonNullUnaryFunction<NonNullableQuartet<VariableElement, NonNullableIterable<VariableElement>, DeclaredType, NonNullableIterable<MethodInformation>>, ParameterBasedFieldInformation> parametersToFieldFunction = new NonNullToNonNullUnaryFunction<NonNullableQuartet<VariableElement, NonNullableIterable<VariableElement>, DeclaredType, NonNullableIterable<MethodInformation>>, ParameterBasedFieldInformation>() {
        
        @Override
        public @Nonnull ParameterBasedFieldInformation apply(@Nonnull NonNullableQuartet<VariableElement, NonNullableIterable<VariableElement>, DeclaredType, NonNullableIterable<MethodInformation>> quartet) {
            final @Nonnull VariableElement representingParameter = quartet.get0();
            final @Nonnull NonNullableIterable<VariableElement> fields = quartet.get1();
            final @Nonnull DeclaredType containingType = quartet.get2();
            final @Nonnull NonNullableIterable<MethodInformation> methodsOfType = quartet.get3();
            
            final @Nonnull String parameterName = representingParameter.getSimpleName().toString();
            final @Nonnull NonNullableIterable<String> infiniteIterableParameterName = NullableIterable.ofNonNullableElement(parameterName);
            
            final @Nullable NonNullablePair<VariableElement, String> result = fields.zipNonNull(infiniteIterableParameterName).findFirst(variableElementNameMatcher);
            if (result == null) {
                throw ConformityViolation.with("Representative fields must have the same name as their parameters, but the field for '" + parameterName + "' was not found");
            }
            
            final @Nonnull VariableElement field = result.get0();
            if (!field.getModifiers().contains(Modifier.PRIVATE)) {
                return DirectlyAccessibleParameterBasedFieldInformation.of(representingParameter, containingType, field);
            } else {
                final @Nonnull TypeElement fieldType = (TypeElement) StaticProcessingEnvironment.getTypeUtils().asElement(field.asType());
                return NonDirectlyAccessibleParameterBasedFieldInformation.of(representingParameter, containingType, getGetterOf(parameterName, methodsOfType), getSetterOf(parameterName, fieldType, methodsOfType));
            }
            
        }
        
    };
    
    /**
     * Returns an iterable of parameter-based field information objects by transforming an iterable of parameter 
     * variable elements plus additional information that is required for the transformation. 
     * The additional information is: an iterable of the fields of a type, a containing type and an iterable of the methods of the type.
     */
    private static @Nonnull @NonNullableElements NonNullableIterable<ParameterBasedFieldInformation> getParameterBasedFieldInformation(@Nonnull NonNullableIterable<VariableElement> fields, @Nonnull NonNullableIterable<VariableElement> representingParameters, @Nonnull DeclaredType containingType, @Nonnull NonNullableIterable<MethodInformation> methodsOfType) {
        
        final @Nonnull NonNullableIterable<NonNullableIterable<VariableElement>> infiniteIterableOfFields = NullableIterable.ofNonNullableElement(fields);
        final @Nonnull NonNullableIterable<DeclaredType> infiniteIterableOfContainingType = NullableIterable.ofNonNullableElement(containingType);
        final @Nonnull NonNullableIterable<NonNullableIterable<MethodInformation>> infiniteIterableOfMethodsOfType = NullableIterable.ofNonNullableElement(methodsOfType);
        
        return representingParameters.zipNonNull(infiniteIterableOfFields, infiniteIterableOfContainingType, infiniteIterableOfMethodsOfType).map(parametersToFieldFunction);
    }
    
    /**
     * This function receives a subtype of representing field information and returns a representing field information object.
     */
    private static @Nonnull NonNullToNonNullUnaryFunction<? super RepresentingFieldInformation, RepresentingFieldInformation> castToRepresentingFieldInformation = new NonNullToNonNullUnaryFunction<RepresentingFieldInformation, RepresentingFieldInformation>() {
        
        @Override 
        public @Nonnull RepresentingFieldInformation apply(@Nonnull RepresentingFieldInformation element) {
            return element;
        }
        
    };
    
    /**
     * Combines and returns the parameter-based fields and the generated fields of the type.
     */
    @Override
    @SuppressWarnings("unchecked")
    public @Nonnull @NonNullableElements List<RepresentingFieldInformation> getRepresentingFieldInformation() throws UnexpectedTypeContentException {
        return NullableIterable.ofNonNullableElements(parameterBasedFieldInformation).map(castToRepresentingFieldInformation).combine(NullableIterable.ofNonNullableElements(generatedFieldInformation).map(castToRepresentingFieldInformation)).toList();
    }
    
    /* -------------------------------------------------- Generatable -------------------------------------------------- */
    
    private final boolean generatable;
    
    @Override
    public boolean isGeneratable() {
        return super.isGeneratable() && generatable;
    }
    
    /* -------------------------------------------------- Predicates -------------------------------------------------- */
    
    protected static final @Nonnull NonNullablePredicate<MethodInformation> recoverMethodMatcher = new NonNullablePredicate<MethodInformation>() {
        
        final @Nonnull TypeMirror recoverAnnotationType = StaticProcessingEnvironment.getElementUtils().getTypeElement(Recover.class.getName()).asType();
        
        @Override 
        public boolean apply(@Nonnull MethodInformation methodInformation) {
            for (@Nonnull AnnotationMirror annotationMirror : methodInformation.getAnnotations()) {
                if (annotationMirror.getAnnotationType().equals(recoverAnnotationType)) {
                    return true;
                }
            }
            return false;
        }
        
    };
    
    protected static final @Nonnull NonNullablePredicate<MethodInformation> implementedGetterMatcher = new NonNullablePredicate<MethodInformation>() {
        
        @Override 
        public boolean apply(@Nonnull MethodInformation methodInformation) {
            return !methodInformation.isAbstract() && methodInformation.isGetter();
        }
        
    };
    
    protected static final @Nonnull NonNullablePredicate<MethodInformation> noJavaRuntimeMethod = new NonNullablePredicate<MethodInformation>() {
        
        @Override 
        public boolean apply(@Nonnull MethodInformation methodInformation) {
            return !methodInformation.isDeclaredInRuntimeEnvironment();
        }
        
    };
    
    /* -------------------------------------------------- Functions -------------------------------------------------- */
    
    protected static final @Nonnull NonNullToNonNullUnaryFunction<NonNullablePair<ExecutableElement, DeclaredType>, ConstructorInformation> constructorInformationFunction = new NonNullToNonNullUnaryFunction<NonNullablePair<ExecutableElement, DeclaredType>, ConstructorInformation>() {
        
        @Nonnull @Override public ConstructorInformation apply(@Nonnull NonNullablePair<ExecutableElement, DeclaredType> pair) {
            final @Nonnull ExecutableElement element = pair.get0();
            final @Nonnull DeclaredType containingType = pair.get1();
            return ConstructorInformation.of(element, containingType);
        }
        
    };
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a class information object.
     */
    protected ClassInformation(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType) {
        super(typeElement, containingType);
    
        final @Nonnull NonNullableIterable<MethodInformation> methodInformationIterable = getMethodInformation(typeElement, containingType);
        
        final @Nonnull NonNullableIterable<MethodSignatureMatcher> equalsMatcherIterable = NullableIterable.ofNonNullableElement(MethodSignatureMatcher.of("equals", new TypeElement[0]));
        this.equalsMethod = methodInformationIterable.zipNonNull(equalsMatcherIterable).findFirst(methodMatcher, GET_METHODINFORMATION);
        final @Nonnull NonNullableIterable<MethodSignatureMatcher> hashCodeMatcherIterable = NullableIterable.ofNonNullableElement(MethodSignatureMatcher.of("hashCode", new TypeElement[0]));
        this.hashCodeMethod = methodInformationIterable.zipNonNull(hashCodeMatcherIterable).findFirst(methodMatcher, GET_METHODINFORMATION);
        final @Nonnull NonNullableIterable<MethodSignatureMatcher> toStringMatcherIterable = NullableIterable.ofNonNullableElement(MethodSignatureMatcher.of("toString", new TypeElement[0]));
        this.toStringMethod = methodInformationIterable.zipNonNull(toStringMatcherIterable).findFirst(methodMatcher, GET_METHODINFORMATION);
        final @Nonnull NonNullableIterable<MethodSignatureMatcher> compareToMatcherIterable = NullableIterable.ofNonNullableElement(MethodSignatureMatcher.of("compareTo", new TypeElement[] { typeElement }));
        this.compareToMethod = methodInformationIterable.zipNonNull(compareToMatcherIterable).findFirst(methodMatcher, GET_METHODINFORMATION);
        final @Nonnull NonNullableIterable<MethodSignatureMatcher> cloneMethodMatcherIterable = NullableIterable.ofNonNullableElement(MethodSignatureMatcher.of("clone", new TypeElement[0]));
        this.cloneMethod = methodInformationIterable.zipNonNull(cloneMethodMatcherIterable).findFirst(methodMatcher, GET_METHODINFORMATION);
        final @Nonnull NonNullableIterable<MethodSignatureMatcher> validateMethodMatcherIterable = NullableIterable.ofNonNullableElement(MethodSignatureMatcher.of("validate", new TypeElement[0]));
        this.validateMethod = methodInformationIterable.zipNonNull(validateMethodMatcherIterable).findFirst(methodMatcher, GET_METHODINFORMATION);
        
        this.implementedGetters = indexMethodInformation(methodInformationIterable.filter(implementedGetterMatcher));
        
        this.overriddenMethods = methodInformationIterable.filter(noJavaRuntimeMethod).toList();
    
        final @Nonnull NonNullableIterable<DeclaredType> containingTypeIterable = NullableIterable.ofNonNullableElement(containingType);
        this.constructors = NullableIterable.ofNonNullableElements(javax.lang.model.util.ElementFilter.constructorsIn(StaticProcessingEnvironment.getElementUtils().getAllMembers(typeElement))).zipNonNull(containingTypeIterable).map(constructorInformationFunction).toList();
        
        this.recoverMethod = methodInformationIterable.findFirst(recoverMethodMatcher);
        final @Nonnull NonNullableIterable<VariableElement> fields = NonNullableIterable.ofNonNullableElements(ElementFilter.fieldsIn(StaticProcessingEnvironment.getElementUtils().getAllMembers(typeElement)));
        this.parameterBasedFieldInformation = getParameterBasedFieldInformation(fields, NonNullableIterable.ofNonNullableElements(getParameterVariables(constructors.get(0), recoverMethod)), containingType, methodInformationIterable).toList();
    
        this.accessibleFields = FieldInformationFactory.getDirectlyAccessibleFieldInformation(typeElement, containingType).toList();
        
        // TODO: think of cases where the generation can fail
        this.generatable = true;
    }
    
    /**
     * Returns a class information object of the given type typeElement and containing type.
     */
    public static @Nonnull ClassInformation of(@Nonnull TypeElement element, @Nonnull DeclaredType containingType) {
        return new ClassInformation(element, containingType);
    }
     
}
