package net.digitalid.utility.generator.information.type;

import java.util.Collections;
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
import net.digitalid.utility.exceptions.UnexpectedFailureException;
import net.digitalid.utility.functional.function.unary.NonNullToNonNullUnaryFunction;
import net.digitalid.utility.functional.iterable.InfiniteNonNullableIterable;
import net.digitalid.utility.functional.iterable.NonNullableIterable;
import net.digitalid.utility.functional.iterable.NullableIterable;
import net.digitalid.utility.functional.iterable.map.function.implementation.GetNonNull0Function;
import net.digitalid.utility.functional.predicate.NonNullablePredicate;
import net.digitalid.utility.generator.BuilderGenerator;
import net.digitalid.utility.generator.SubclassGenerator;
import net.digitalid.utility.generator.annotations.Recover;
import net.digitalid.utility.generator.information.field.DeclaredFieldInformation;
import net.digitalid.utility.generator.information.field.DirectlyAccessibleParameterBasedFieldInformation;
import net.digitalid.utility.generator.information.field.NonDirectlyAccessibleParameterBasedFieldInformation;
import net.digitalid.utility.generator.information.field.ParameterBasedFieldInformation;
import net.digitalid.utility.generator.information.field.RepresentingFieldInformation;
import net.digitalid.utility.generator.information.method.ConstructorInformation;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.generator.information.type.exceptions.UnsupportedTypeException;
import net.digitalid.utility.generator.query.MethodSignatureMatcher;
import net.digitalid.utility.logging.processing.ProcessingLog;
import net.digitalid.utility.logging.processing.SourcePosition;
import net.digitalid.utility.logging.processing.StaticProcessingEnvironment;
import net.digitalid.utility.string.StringCase;
import net.digitalid.utility.tuples.NonNullablePair;
import net.digitalid.utility.tupless.quartet.NonNullableQuartet;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.size.MinSize;
import net.digitalid.utility.validation.annotations.state.Unmodifiable;

/**
 * This type collects the relevant information about a class for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 */
public class ClassInformation extends TypeInformation {
    
    /* -------------------------------------------------- Type-specific Methods -------------------------------------------------- */
    
    /**
     * The method information of the equals method, or null if the class does not implement an equals method.
     */
    public final @Nullable MethodInformation equalsMethod;
    
    /**
     * The method information of the hashCode method, or null if the class does not implement a hashCode method.
     */
    public final @Nullable MethodInformation hashCodeMethod;
    
    /**
     * The method information of the toString method, or null if the class does not implement a toString method.
     */
    public final @Nullable MethodInformation toStringMethod;
    
    /**
     * The method information of the compareTo method, or null if the class does not implement a compareTo method.
     */
    public final @Nullable MethodInformation compareToMethod;
    
    /**
     * The method information of the clone method, or null if the class does not implement a clone method.
     */
    public final @Nullable MethodInformation cloneMethod;
    
    /**
     * The method information of the validate method, or null if the class does not implement a validate method.
     */
    public final @Nullable MethodInformation validateMethod;
    
    /* -------------------------------------------------- Overridden Methods -------------------------------------------------- */
    
    /**
     * All methods that are implemented in this class and can be overridden by its generated subclass.
     * Overridden methods will validate pre- and post-conditions on the method.
     */
    private final @Nonnull NonNullableIterable<MethodInformation> overriddenMethods;
    
    @Pure
    @Override
    public @Nonnull NonNullableIterable<MethodInformation> getOverriddenMethods() {
        return overriddenMethods;
    }
    
    /**
     * Only methods not declared in the java runtime environment are considered overridden methods.
     */
    protected static final @Nonnull NonNullablePredicate<MethodInformation> javaRuntimeMethod = new NonNullablePredicate<MethodInformation>() {
        
        @Override 
        public boolean apply(@Nonnull MethodInformation methodInformation) {
            return !methodInformation.isDeclaredInRuntimeEnvironment();
        }
        
    };
    
    /**
     * Only methods that are non-final can be overridden.
     */
    protected static final @Nonnull NonNullablePredicate<MethodInformation> finalMethods = new NonNullablePredicate<MethodInformation>() {
        
        @Override 
        public boolean apply(@Nonnull MethodInformation methodInformation) {
            return !methodInformation.isFinal();
        }
        
    };
    
    /* -------------------------------------------------- Object Construction -------------------------------------------------- */
    
    /**
     * The method information of the recover method, or null if the class does not implement a recover method.
     */
    public final @Nullable MethodInformation recoverMethod;
    
    /**
     * The predicate that checks whether a method information object is annotated with {@link Recover @Recover}.
     */
    private static final @Nonnull NonNullablePredicate<MethodInformation> recoverMethodMatcher = new NonNullablePredicate<MethodInformation>() {
        
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
    
    /**
     * The list of constructors. There is at least one constructor per class.
     */
    @MinSize(1)
    private final @Unmodifiable @Nonnull NonNullableIterable<ConstructorInformation> constructors;
    
    /**
     * Returns the constructors that are declared in this class.
     */
    @Pure
    @Override
    public @Unmodifiable @Nonnull NonNullableIterable<ConstructorInformation> getConstructors() {
        return constructors;
    }
    
    /* -------------------------------------------------- Fields -------------------------------------------------- */
    
    /**
     * Stores the fields associated with the parameters of the constructor, which also represent the object.
     */
    public final @Nonnull NonNullableIterable<ParameterBasedFieldInformation> parameterBasedFieldInformation;
    
    /**
     * Stores the accessible fields, which can be validated by the generated subclass.
     */
    public final @Nonnull NonNullableIterable<DeclaredFieldInformation> accessibleFields;
    
    /**
     * Stores the implemented getters for the fields in the type.
     */
    public final @Nonnull @NonNullableElements Map<String, MethodInformation> implementedGetters;
    
    /* -------------------------------------------------- Method Matcher -------------------------------------------------- */
    
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
    
    /* -------------------------------------------------- Method Information -------------------------------------------------- */
    
    private final static @Nonnull GetNonNull0Function<MethodInformation, MethodSignatureMatcher> GET_METHOD_INFORMATION = new GetNonNull0Function<>();
    
    
    /* -------------------------------------------------- Constructor Information -------------------------------------------------- */
    
    protected static final @Nonnull NonNullToNonNullUnaryFunction<NonNullablePair<ExecutableElement, DeclaredType>, ConstructorInformation> toConstructorInformation = new NonNullToNonNullUnaryFunction<NonNullablePair<ExecutableElement, DeclaredType>, ConstructorInformation>() {
        
        @Nonnull @Override public ConstructorInformation apply(@Nonnull NonNullablePair<ExecutableElement, DeclaredType> pair) {
            final @Nonnull ExecutableElement element = pair.get0();
            final @Nonnull DeclaredType containingType = pair.get1();
            return ConstructorInformation.of(element, containingType);
        }
        
    };
    
    /* -------------------------------------------------- Recover Executable -------------------------------------------------- */
    
    /**
     * Returns the executable element used to create an instance of this type.
     * The executable element is either the recover method (if available) or the single
     * constructor. If no recover method and multiple constructors exist, a unsupported type exception is thrown, indicating that we cannot defer how to create an instance of this type.
     * An unexpected failure exception is thrown if no constructors could be found. This should never happen.
     */
    public @Nonnull ExecutableElement getRecoverExecutable(@Nullable MethodInformation recoverMethod, @Nonnull NonNullableIterable<ConstructorInformation> constructors) throws UnsupportedTypeException {
        if (recoverMethod != null) {
            return recoverMethod.getElement();
        } else {
            if (constructors.size() > 1) {
                throw UnsupportedTypeException.get("More than one constructor, but no recover method found. We cannot decide which constructor to use for object construction.");
            } else if (constructors.size() == 0) {
                throw UnexpectedFailureException.with("No constructor found.");
            } else {
                final @Nonnull ConstructorInformation constructor = constructors.iterator().next();
                return constructor.getElement();
            }
        }
    }
    
    /* -------------------------------------------------- Parameter Variables -------------------------------------------------- */
    
    @SuppressWarnings("unchecked")
    public @Nonnull NonNullableIterable<VariableElement> getParameterVariables(@Nonnull ExecutableElement recoverExecutable) {
        return NonNullableIterable.ofNonNullableElements((List<VariableElement>) recoverExecutable.getParameters());
    }
    
    /* -------------------------------------------------- Parameter Based Field Information -------------------------------------------------- */
    
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
    
    /**
     * Returns a method information object that matches the expected declaration of a getter for a certain field.
     * A {@link ConformityViolation conformity violation exception} is thrown if the getter was not found.
     */
    private static @Nonnull MethodInformation getGetterOf(@Nonnull String fieldName, @Nonnull @NonNullableElements NonNullableIterable<MethodInformation> methodsOfType) {
        final @Nonnull String nameRegex = "(get|has|is)" + StringCase.capitalizeFirstLetters(fieldName);
        final @Nonnull NonNullableIterable<MethodSignatureMatcher> methodSignature = InfiniteNonNullableIterable.ofNonNullableElement(MethodSignatureMatcher.of(nameRegex, new TypeElement[0]));
        final @Nullable MethodInformation methodInformation = methodsOfType.zipNonNull(methodSignature).findFirst(methodMatcher, GET_METHOD_INFORMATION);
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
        return methodsOfType.zipNonNull(methodSignature).findFirst(methodMatcher, GET_METHOD_INFORMATION);
    }
    
    /**
     * A unary function that transforms a quartet that consists of a parameter variable element, an iterable of field variable elements, a containing type and an iterable of method information to a parameter-based field information object.
     * A {@link ConformityViolation conformity violation exception} is thrown if any of the representative fields was not found or if a getter for a non-directly accessible field was not found.
     */
    private static NonNullToNonNullUnaryFunction<NonNullableQuartet<VariableElement, NonNullableIterable<VariableElement>, DeclaredType, NonNullableIterable<MethodInformation>>, ParameterBasedFieldInformation> toParameterBasedFieldInformation = new NonNullToNonNullUnaryFunction<NonNullableQuartet<VariableElement, NonNullableIterable<VariableElement>, DeclaredType, NonNullableIterable<MethodInformation>>, ParameterBasedFieldInformation>() {
        
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
     * Throws an unsupported type exception if a conformity violation exception was caught.
     */
    private static @Nonnull @NonNullableElements NonNullableIterable<ParameterBasedFieldInformation> getParameterBasedFieldInformation(@Nonnull NonNullableIterable<VariableElement> fields, @Nonnull NonNullableIterable<VariableElement> representingParameters, @Nonnull DeclaredType containingType, @Nonnull NonNullableIterable<MethodInformation> methodsOfType) throws UnsupportedTypeException {
        
        final @Nonnull NonNullableIterable<NonNullableIterable<VariableElement>> infiniteIterableOfFields = NullableIterable.ofNonNullableElement(fields);
        final @Nonnull NonNullableIterable<DeclaredType> infiniteIterableOfContainingType = NullableIterable.ofNonNullableElement(containingType);
        final @Nonnull NonNullableIterable<NonNullableIterable<MethodInformation>> infiniteIterableOfMethodsOfType = NullableIterable.ofNonNullableElement(methodsOfType);
        
        try {
            return representingParameters.zipNonNull(infiniteIterableOfFields, infiniteIterableOfContainingType, infiniteIterableOfMethodsOfType).map(toParameterBasedFieldInformation);
        } catch (ConformityViolation e) {
            throw UnsupportedTypeException.get(e.getMessage(), e);
        }
    }
    
    /* -------------------------------------------------- Representing Field Information -------------------------------------------------- */
    
    /**
     * This function receives a subtype of representing field information and returns a representing field information object.
     */
    private static @Nonnull NonNullToNonNullUnaryFunction<? super RepresentingFieldInformation, RepresentingFieldInformation> toRepresentingFieldInformation = new NonNullToNonNullUnaryFunction<RepresentingFieldInformation, RepresentingFieldInformation>() {
        
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
    public @Nonnull NonNullableIterable<RepresentingFieldInformation> getRepresentingFieldInformation() {
        return parameterBasedFieldInformation.map(toRepresentingFieldInformation).combine(generatedFieldInformation.map(toRepresentingFieldInformation));
    }
    /* -------------------------------------------------- Accessible Fields -------------------------------------------------- */
    
    /**
     * This predicate checks whether a variable element has a non-private, non-final modifier.
     */
    private static @Nonnull NonNullablePredicate<VariableElement> accessibleFieldMatcher = new NonNullablePredicate<VariableElement>() {
        
        @Override
        public boolean apply(@Nonnull VariableElement field) {
            return !field.getModifiers().contains(Modifier.PRIVATE) && !field.getModifiers().contains(Modifier.FINAL);
        }
        
    };
    
    /**
     * This function maps a variable element with its containing type to a declared field information object.
     */
    private static @Nonnull NonNullToNonNullUnaryFunction<NonNullablePair<VariableElement, DeclaredType>, DeclaredFieldInformation> toDeclaredFieldInformation = new NonNullToNonNullUnaryFunction<NonNullablePair<VariableElement, DeclaredType>, DeclaredFieldInformation>() {
        
        @Override
        public @Nonnull DeclaredFieldInformation apply(@Nonnull NonNullablePair<VariableElement, DeclaredType> pair) {
            final @Nonnull VariableElement element = pair.get0();
            final @Nonnull DeclaredType containingType = pair.get1();
            return DeclaredFieldInformation.of(element, containingType);
        }
        
    };
    
    /**
     * Retrieves declared field information objects for fields in a type.
     */
    private static @Nonnull @NonNullableElements NonNullableIterable<DeclaredFieldInformation> getDirectlyAccessibleFieldInformation(@Nonnull @NonNullableElements NonNullableIterable<VariableElement> fields, @Nonnull DeclaredType containingType) {
        return fields.filter(accessibleFieldMatcher).zipNonNull(NonNullableIterable.ofNonNullableElement(containingType)).map(toDeclaredFieldInformation);
    }
    
    /* -------------------------------------------------- Generatable -------------------------------------------------- */
    
    private final boolean generatable;
    
    @Override
    public boolean isGeneratable() {
        return super.isGeneratable() && generatable;
    }
    
    /* -------------------------------------------------- Predicates -------------------------------------------------- */
    
    
    
    protected static final @Nonnull NonNullablePredicate<MethodInformation> implementedGetterMatcher = new NonNullablePredicate<MethodInformation>() {
        
        @Override 
        public boolean apply(@Nonnull MethodInformation methodInformation) {
            return !methodInformation.isAbstract() && methodInformation.isGetter();
        }
        
    };
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a class information object.
     */
    protected ClassInformation(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType) {
        super(typeElement, containingType);
    
        boolean generatable = true;
        
        final @Nonnull NonNullableIterable<MethodInformation> methodInformationIterable = getMethodInformation(typeElement, containingType);
        
        final @Nonnull NonNullableIterable<MethodSignatureMatcher> equalsMatcherIterable = NullableIterable.ofNonNullableElement(MethodSignatureMatcher.of("equals", new TypeElement[0]));
        this.equalsMethod = methodInformationIterable.zipNonNull(equalsMatcherIterable).findFirst(methodMatcher, GET_METHOD_INFORMATION);
        final @Nonnull NonNullableIterable<MethodSignatureMatcher> hashCodeMatcherIterable = NullableIterable.ofNonNullableElement(MethodSignatureMatcher.of("hashCode", new TypeElement[0]));
        this.hashCodeMethod = methodInformationIterable.zipNonNull(hashCodeMatcherIterable).findFirst(methodMatcher, GET_METHOD_INFORMATION);
        final @Nonnull NonNullableIterable<MethodSignatureMatcher> toStringMatcherIterable = NullableIterable.ofNonNullableElement(MethodSignatureMatcher.of("toString", new TypeElement[0]));
        this.toStringMethod = methodInformationIterable.zipNonNull(toStringMatcherIterable).findFirst(methodMatcher, GET_METHOD_INFORMATION);
        final @Nonnull NonNullableIterable<MethodSignatureMatcher> compareToMatcherIterable = NullableIterable.ofNonNullableElement(MethodSignatureMatcher.of("compareTo", new TypeElement[] { typeElement }));
        this.compareToMethod = methodInformationIterable.zipNonNull(compareToMatcherIterable).findFirst(methodMatcher, GET_METHOD_INFORMATION);
        final @Nonnull NonNullableIterable<MethodSignatureMatcher> cloneMethodMatcherIterable = NullableIterable.ofNonNullableElement(MethodSignatureMatcher.of("clone", new TypeElement[0]));
        this.cloneMethod = methodInformationIterable.zipNonNull(cloneMethodMatcherIterable).findFirst(methodMatcher, GET_METHOD_INFORMATION);
        final @Nonnull NonNullableIterable<MethodSignatureMatcher> validateMethodMatcherIterable = NullableIterable.ofNonNullableElement(MethodSignatureMatcher.of("validate", new TypeElement[0]));
        this.validateMethod = methodInformationIterable.zipNonNull(validateMethodMatcherIterable).findFirst(methodMatcher, GET_METHOD_INFORMATION);
        
        this.implementedGetters = indexMethodInformation(methodInformationIterable.filter(implementedGetterMatcher));
        
        this.overriddenMethods = methodInformationIterable.filter(javaRuntimeMethod).filter(finalMethods);
        
        NonNullableIterable<MethodInformation> methodsMatchingTheRecoverMethodMatcher = methodInformationIterable.filter(recoverMethodMatcher);
        if (methodsMatchingTheRecoverMethodMatcher.size() > 1) {
            ProcessingLog.information("More than one recover methods found in class '" + typeElement.getSimpleName() + "'. We cannot decide which method to use for object construction.");
            generatable = false;
            this.recoverMethod = null;
        } else {
            this.recoverMethod = methodsMatchingTheRecoverMethodMatcher.iterator().next();
        }
        
        final @Nonnull NonNullableIterable<DeclaredType> containingTypeIterable = NullableIterable.ofNonNullableElement(containingType);
        this.constructors = NullableIterable.ofNonNullableElements(javax.lang.model.util.ElementFilter.constructorsIn(StaticProcessingEnvironment.getElementUtils().getAllMembers(typeElement))).zipNonNull(containingTypeIterable).map(toConstructorInformation);
        
        final @Nonnull NonNullableIterable<VariableElement> fields = NonNullableIterable.ofNonNullableElements(ElementFilter.fieldsIn(StaticProcessingEnvironment.getElementUtils().getAllMembers(typeElement)));
    
        this.accessibleFields = getDirectlyAccessibleFieldInformation(fields, containingType);
        
        @Nonnull @NonNullableElements NonNullableIterable<ParameterBasedFieldInformation> parameterBasedFieldInformation = NonNullableIterable.ofNonNullableElements(Collections.<ParameterBasedFieldInformation>emptyList());
        
        try {
            final @Nonnull ExecutableElement recoverExecutable;
                recoverExecutable = getRecoverExecutable(recoverMethod, constructors);
            final @Nonnull NonNullableIterable<VariableElement> parameterVariables = getParameterVariables(recoverExecutable);
            parameterBasedFieldInformation = getParameterBasedFieldInformation(fields, parameterVariables, containingType, methodInformationIterable);
        } catch (UnsupportedTypeException e) {
            ProcessingLog.information(e.getMessage(), SourcePosition.of(typeElement));
            generatable = false;
        } finally {
            this.parameterBasedFieldInformation = parameterBasedFieldInformation;
        }
        
        this.generatable = generatable;
    }
    
    /**
     * Returns a class information object of the given type typeElement and containing type.
     */
    public static @Nonnull ClassInformation of(@Nonnull TypeElement element, @Nonnull DeclaredType containingType) {
        return new ClassInformation(element, containingType);
    }
     
}
