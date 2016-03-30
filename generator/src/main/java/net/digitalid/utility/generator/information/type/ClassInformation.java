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
import net.digitalid.utility.functional.iterable.old.InfiniteNonNullableIterable;
import net.digitalid.utility.functional.iterable.old.NonNullableIterable;
import net.digitalid.utility.functional.iterable.old.NullableIterable;
import net.digitalid.utility.functional.iterable.map.function.implementation.GetNonNull0Function;
import net.digitalid.utility.functional.predicate.NonNullablePredicate;
import net.digitalid.utility.generator.BuilderGenerator;
import net.digitalid.utility.generator.SubclassGenerator;
import net.digitalid.utility.generator.annotations.Recover;
import net.digitalid.utility.generator.information.field.DirectlyAccessibleDeclaredFieldInformation;
import net.digitalid.utility.generator.information.field.DirectlyAccessibleFieldInformation;
import net.digitalid.utility.generator.information.field.DirectlyAccessibleParameterBasedFieldInformation;
import net.digitalid.utility.generator.information.field.FieldInformation;
import net.digitalid.utility.generator.information.field.NonDirectlyAccessibleDeclaredFieldInformation;
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
import net.digitalid.utility.tuples.pair.NonNullablePair;
import net.digitalid.utility.tuples.quartet.NonNullableQuartet;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.annotations.method.Pure;
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
    
    /**
     * Only methods that are non-abstract can be overridden.
     */
    protected static final @Nonnull NonNullablePredicate<MethodInformation> abstractMethods = new NonNullablePredicate<MethodInformation>() {
        
        @Override 
        public boolean apply(@Nonnull MethodInformation methodInformation) {
            return !methodInformation.isAbstract();
        }
        
    };
     
    /**
     * Only methods that are non-static can be overridden.
     */
    protected static final @Nonnull NonNullablePredicate<MethodInformation> staticMethods = new NonNullablePredicate<MethodInformation>() {
        
        @Override 
        public boolean apply(@Nonnull MethodInformation methodInformation) {
            return !methodInformation.isStatic();
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
     * Stores the accessible fields that are mutable and must be validated by the generated subclass.
     */
    public final @Nonnull NonNullableIterable<DirectlyAccessibleDeclaredFieldInformation> writableAccessibleFields;
    
    /**
     * Stores the directly accessible fields.
     */
    public final @Nonnull NonNullableIterable<DirectlyAccessibleDeclaredFieldInformation> directlyAccessibleDeclaredFields;
    
    /**
     * Stores the non-directly accessible fields.
     */
    public final @Nonnull NonNullableIterable<NonDirectlyAccessibleDeclaredFieldInformation> nonDirectlyAccessibleDeclaredFields;
    
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
    private static @Nonnull NonNullablePredicate<NonNullablePair<FieldInformation, String>> fieldNameMatcher = new NonNullablePredicate<NonNullablePair<FieldInformation, String>>() {
        
        @Override
        public boolean apply(@Nonnull NonNullablePair<FieldInformation, String> pair) {
            final @Nonnull FieldInformation fieldInformation = pair.get0();
            final @Nonnull String name = pair.get1();
            ProcessingLog.debugging("Checking whether field $ = parameter $", fieldInformation.getName(), name);
            return fieldInformation.getName().equals(name);
        }
        
    };
    
    /**
     * Returns a method information object that matches the expected declaration of a getter for a certain field.
     * A {@link ConformityViolation conformity violation exception} is thrown if the getter was not found.
     */
    private static @Nullable MethodInformation getGetterOf(@Nonnull String fieldName, @Nonnull @NonNullableElements NonNullableIterable<MethodInformation> methodsOfType) {
        final @Nonnull String nameRegex = "(get|has|is)" + StringCase.capitalizeFirstLetters(fieldName);
        final @Nonnull NonNullableIterable<MethodSignatureMatcher> methodSignature = InfiniteNonNullableIterable.ofNonNullableElement(MethodSignatureMatcher.of(nameRegex, new TypeElement[0]));
        final @Nullable MethodInformation methodInformation = methodsOfType.zipNonNull(methodSignature).findFirst(methodMatcher, GET_METHOD_INFORMATION);
        ProcessingLog.debugging("getGetterOf($) : $", fieldName, methodInformation);
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
    private static NonNullToNullableUnaryFunction<NonNullableQuartet<VariableElement, NonNullableIterable<FieldInformation>, DeclaredType, NonNullableIterable<MethodInformation>>, ParameterBasedFieldInformation> toParameterBasedFieldInformation = new NonNullToNullableUnaryFunction<NonNullableQuartet<VariableElement, NonNullableIterable<FieldInformation>, DeclaredType, NonNullableIterable<MethodInformation>>, ParameterBasedFieldInformation>() {
        
        @Override
        public @Nullable ParameterBasedFieldInformation apply(@Nonnull NonNullableQuartet<VariableElement, NonNullableIterable<FieldInformation>, DeclaredType, NonNullableIterable<MethodInformation>> quartet) {
            final @Nonnull VariableElement representingParameter = quartet.get0();
            final @Nonnull NonNullableIterable<FieldInformation> fields = quartet.get1();
            final @Nonnull DeclaredType containingType = quartet.get2();
            final @Nonnull NonNullableIterable<MethodInformation> methodsOfType = quartet.get3();
            
            final @Nonnull String parameterName = representingParameter.getSimpleName().toString();
            final @Nonnull NonNullableIterable<String> infiniteIterableParameterName = NullableIterable.ofNonNullableElement(parameterName);
            
            final @Nullable NonNullablePair<FieldInformation, String> result = fields.zipNonNull(infiniteIterableParameterName).findFirst(fieldNameMatcher);
            ProcessingLog.debugging("Parameter: $", parameterName);
            ProcessingLog.debugging("Field information: ");
            for (FieldInformation field : fields) {
                ProcessingLog.debugging(field.getName());
            }
            if (result == null) {
                throw ConformityViolation.with("Representative fields of $ must have the same name as their parameters, but the field for $ was not found", containingType, parameterName);
            }
            
            final @Nonnull FieldInformation field = result.get0();
            if (field instanceof NonDirectlyAccessibleDeclaredFieldInformation) {
                return NonDirectlyAccessibleParameterBasedFieldInformation.of(representingParameter, (NonDirectlyAccessibleDeclaredFieldInformation) field);
            } else if (field instanceof DirectlyAccessibleDeclaredFieldInformation) {
                return DirectlyAccessibleParameterBasedFieldInformation.of(representingParameter, (DirectlyAccessibleFieldInformation) field);
            } else {
            //    throw UnexpectedFailureException.with("Did not expect field information of type $. Cannot create a parameter-based field information instance from that type.", field.getClass());
                return null;
            }
            
        }
        
    };
    
    /**
     * Returns an iterable of parameter-based field information objects by transforming an iterable of parameter 
     * variable elements plus additional information that is required for the transformation. 
     * The additional information is: an iterable of the fields of a type, a containing type and an iterable of the methods of the type.
     * Throws an unsupported type exception if a conformity violation exception was caught.
     */
    private static @Nonnull @NonNullableElements NonNullableIterable<ParameterBasedFieldInformation> getParameterBasedFieldInformation(@Nonnull NonNullableIterable<FieldInformation> fields, @Nonnull NonNullableIterable<VariableElement> representingParameters, @Nonnull DeclaredType containingType, @Nonnull NonNullableIterable<MethodInformation> methodsOfType) throws UnsupportedTypeException {
        
        final @Nonnull NonNullableIterable<NonNullableIterable<FieldInformation>> infiniteIterableOfFields = NullableIterable.ofNonNullableElement(fields);
        final @Nonnull NonNullableIterable<DeclaredType> infiniteIterableOfContainingType = NullableIterable.ofNonNullableElement(containingType);
        final @Nonnull NonNullableIterable<NonNullableIterable<MethodInformation>> infiniteIterableOfMethodsOfType = NullableIterable.ofNonNullableElement(methodsOfType);
        
        try {
            return representingParameters.zipNonNull(infiniteIterableOfFields, infiniteIterableOfContainingType, infiniteIterableOfMethodsOfType).map(toParameterBasedFieldInformation).filterNonNull();
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
    public @Nonnull NonNullableIterable<RepresentingFieldInformation> getRepresentingFieldInformation() {
        final @Nonnull NonNullableIterable<RepresentingFieldInformation> generatedFieldInformationCasted = generatedFieldInformation.map(toRepresentingFieldInformation);
        return parameterBasedFieldInformation.map(toRepresentingFieldInformation).combine(generatedFieldInformationCasted);
    }
    
    /* -------------------------------------------------- Fields -------------------------------------------------- */
    
    /**
     * This function casts a element with its containing type to a declared field information object.
     */
    private static @Nonnull NonNullToNonNullUnaryFunction<FieldInformation, FieldInformation> castToFieldInformation = new NonNullToNonNullUnaryFunction<FieldInformation, FieldInformation>() {
        
        @Override
        public @Nonnull FieldInformation apply(@Nonnull FieldInformation fieldInformation) {
            return fieldInformation;
        }
        
    };
    
    /* -------------------------------------------------- Accessible Fields -------------------------------------------------- */
    
    /**
     * This predicate checks whether a variable element has a non-private modifier.
     */
    private static @Nonnull NonNullablePredicate<DirectlyAccessibleDeclaredFieldInformation> nonFinalFieldsPredicate = new NonNullablePredicate<DirectlyAccessibleDeclaredFieldInformation>() {
        
        @Override
        public boolean apply(@Nonnull DirectlyAccessibleDeclaredFieldInformation field) {
            return !field.getModifiers().contains(Modifier.FINAL);
        }
        
    };
    
    /**
     * This predicate checks whether a variable element has a non-private modifier.
     */
    private static @Nonnull NonNullablePredicate<VariableElement> directlyAccessibleFieldPredicate = new NonNullablePredicate<VariableElement>() {
        
        @Override
        public boolean apply(@Nonnull VariableElement field) {
            return !field.getModifiers().contains(Modifier.PRIVATE);
        }
        
    };
    
    /**
     * This function maps a variable element with its containing type to a declared field information object.
     */
    private static @Nonnull NonNullToNonNullUnaryFunction<NonNullablePair<VariableElement, DeclaredType>, DirectlyAccessibleDeclaredFieldInformation> toDirectlyAccessibleDeclaredFieldInformation = new NonNullToNonNullUnaryFunction<NonNullablePair<VariableElement, DeclaredType>, DirectlyAccessibleDeclaredFieldInformation>() {
        
        @Override
        public @Nonnull DirectlyAccessibleDeclaredFieldInformation apply(@Nonnull NonNullablePair<VariableElement, DeclaredType> pair) {
            final @Nonnull VariableElement element = pair.get0();
            final @Nonnull DeclaredType containingType = pair.get1();
            return DirectlyAccessibleDeclaredFieldInformation.of(element, containingType);
        }
        
    };
    
    /**
     * Retrieves declared field information objects for fields in a type.
     */
    private static @Nonnull @NonNullableElements NonNullableIterable<DirectlyAccessibleDeclaredFieldInformation> getDirectlyAccessibleFieldInformation(@Nonnull @NonNullableElements NonNullableIterable<VariableElement> fields, @Nonnull DeclaredType containingType) {
        return fields.filter(directlyAccessibleFieldPredicate).zipNonNull(NonNullableIterable.ofNonNullableElement(containingType)).map(toDirectlyAccessibleDeclaredFieldInformation);
    }
    
    /* -------------------------------------------------- Non-directly Accessible Fields -------------------------------------------------- */
    
    /**
     * This predicate checks whether a variable element has a non-private, non-final modifier.
     */
    private static @Nonnull NonNullablePredicate<NonNullablePair<VariableElement, NonNullableIterable<MethodInformation>>> nonDirectlyAccessibleFieldMatcher = new NonNullablePredicate<NonNullablePair<VariableElement, NonNullableIterable<MethodInformation>>>() {
        
        @Override
        public boolean apply(@Nonnull NonNullablePair<VariableElement, NonNullableIterable<MethodInformation>> pair) {
            final @Nonnull VariableElement field = pair.get0();
            final @Nonnull NonNullableIterable<MethodInformation> methods = pair.get1();
            boolean isNonDirectlyAccessible = field.getModifiers().contains(Modifier.PRIVATE) && 
            getGetterOf(field.getSimpleName().toString(), methods) != null;
            ProcessingLog.debugging("The field $ is " + (!isNonDirectlyAccessible ? "not " : "") + "accessible via a getter", field.getSimpleName().toString());
            return isNonDirectlyAccessible;
        }
        
    };
    
    /**
     * This function maps a variable element with its containing type to a declared field information object.
     * Only expecting variable elements that have already been filtered for being accessible via a getter method.
     */
    private static @Nonnull NonNullToNonNullUnaryFunction<NonNullablePair<NonNullablePair<VariableElement, NonNullableIterable<MethodInformation>>, DeclaredType>, NonDirectlyAccessibleDeclaredFieldInformation> toNonDirectlyAccessibleDeclaredFieldInformation = new NonNullToNonNullUnaryFunction<NonNullablePair<NonNullablePair<VariableElement, NonNullableIterable<MethodInformation>>, DeclaredType>, NonDirectlyAccessibleDeclaredFieldInformation>() {
        
        @Override
        public @Nonnull NonDirectlyAccessibleDeclaredFieldInformation apply(@Nonnull NonNullablePair<NonNullablePair<VariableElement, NonNullableIterable<MethodInformation>>, DeclaredType> pair) {
            final @Nonnull NonNullablePair<VariableElement, NonNullableIterable<MethodInformation>> elementAndMethods = pair.get0();
            final @Nonnull VariableElement element = elementAndMethods.get0();
            final @Nonnull NonNullableIterable<MethodInformation> methods = elementAndMethods.get1();
            final @Nonnull String fieldName = element.getSimpleName().toString();
            final @Nullable MethodInformation getter = getGetterOf(fieldName, methods);
            if (getter != null) {
                final @Nonnull TypeElement fieldType = (TypeElement) StaticProcessingEnvironment.getTypeUtils().asElement(element.asType());
                final @Nullable MethodInformation setter = getSetterOf(fieldName, fieldType, methods);
                final @Nonnull DeclaredType containingType = pair.get1();
                return NonDirectlyAccessibleDeclaredFieldInformation.of(element, containingType, getter, setter);
            }
            throw UnexpectedFailureException.with("Cannot create non-directly accessible declared field information if no getter was found.");
        }
        
    };
    
    /**
     * Retrieves declared field information objects for fields in a type.
     */
    private static @Nonnull @NonNullableElements NonNullableIterable<NonDirectlyAccessibleDeclaredFieldInformation> getNonDirectlyAccessibleFieldInformation(@Nonnull NonNullableIterable<VariableElement> fields, @Nonnull NonNullableIterable<MethodInformation> methodInformation, @Nonnull DeclaredType containingType) {
        return fields.zipNonNull(NonNullableIterable.ofNonNullableElement(methodInformation)).filter(nonDirectlyAccessibleFieldMatcher).zipNonNull(NonNullableIterable.ofNonNullableElement(containingType)).map(toNonDirectlyAccessibleDeclaredFieldInformation);
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
        
        this.overriddenMethods = methodInformationIterable.filter(javaRuntimeMethod).filter(finalMethods).filter(abstractMethods).filter(staticMethods);
        
        NonNullableIterable<MethodInformation> methodsMatchingTheRecoverMethodMatcher = methodInformationIterable.filter(recoverMethodMatcher);
        if (methodsMatchingTheRecoverMethodMatcher.size() > 1) {
            ProcessingLog.debugging("More than one recover method found.");
            ProcessingLog.information("More than one recover methods found in class '" + typeElement.getSimpleName() + "'. We cannot decide which method to use for object construction.");
            generatable = false;
            this.recoverMethod = null;
        } else if (methodsMatchingTheRecoverMethodMatcher.iterator().hasNext()) {
            this.recoverMethod = methodsMatchingTheRecoverMethodMatcher.iterator().next();
        } else {
            this.recoverMethod = null;
        }
        
        final @Nonnull NonNullableIterable<DeclaredType> containingTypeIterable = NullableIterable.ofNonNullableElement(containingType);
        this.constructors = NullableIterable.ofNonNullableElements(javax.lang.model.util.ElementFilter.constructorsIn(StaticProcessingEnvironment.getElementUtils().getAllMembers(typeElement))).zipNonNull(containingTypeIterable).map(toConstructorInformation);
        
        final @Nonnull NonNullableIterable<VariableElement> fields = NonNullableIterable.ofNonNullableElements(ElementFilter.fieldsIn(StaticProcessingEnvironment.getElementUtils().getAllMembers(typeElement)));
        
        this.directlyAccessibleDeclaredFields = getDirectlyAccessibleFieldInformation(fields, containingType);
        
        this.writableAccessibleFields = directlyAccessibleDeclaredFields.filter(nonFinalFieldsPredicate);
        
        this.nonDirectlyAccessibleDeclaredFields = getNonDirectlyAccessibleFieldInformation(fields, methodInformationIterable, containingType);
    
        ProcessingLog.debugging("Directly accessible fields: ");
        for (FieldInformation field : directlyAccessibleDeclaredFields) {
            ProcessingLog.debugging(field.getName());
        }
        ProcessingLog.debugging("Non-directly accessible fields: ");
        for (FieldInformation field : nonDirectlyAccessibleDeclaredFields) {
            ProcessingLog.debugging(field.getName());
        }
        
        final @Nonnull NonNullableIterable<FieldInformation> directlyAccessibleDeclaredFieldsCasted = directlyAccessibleDeclaredFields.map(castToFieldInformation);
        final @Nonnull NonNullableIterable<FieldInformation> nonDirectlyAccessibleDeclaredFieldsCasted = nonDirectlyAccessibleDeclaredFields.map(castToFieldInformation);
        final @Nonnull NonNullableIterable<FieldInformation> generatedFieldInformationCasted = generatedFieldInformation.map(castToFieldInformation);
        final @Nonnull NonNullableIterable<FieldInformation> fieldInformation = directlyAccessibleDeclaredFieldsCasted.combine(nonDirectlyAccessibleDeclaredFieldsCasted).combine(generatedFieldInformationCasted);
        ProcessingLog.debugging("Fields(size: $): ", fieldInformation.size());
        for (FieldInformation field : fieldInformation) {
            ProcessingLog.debugging("> $", field.getName());
        }
        
        @Nonnull NonNullableIterable<ParameterBasedFieldInformation> parameterBasedFieldInformation = NonNullableIterable.ofNonNullableElements(Collections.<ParameterBasedFieldInformation>emptyList());
        
        try {
            final @Nonnull ExecutableElement recoverExecutable;
                recoverExecutable = getRecoverExecutable(recoverMethod, constructors);
            final @Nonnull NonNullableIterable<VariableElement> parameterVariables = getParameterVariables(recoverExecutable);
            parameterBasedFieldInformation = getParameterBasedFieldInformation(fieldInformation, parameterVariables, containingType, methodInformationIterable);
        } catch (UnsupportedTypeException e) {
            ProcessingLog.debugging("Caught an unsupported type exception.");
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
        try {
            return new ClassInformation(element, containingType);
        } catch (Exception e) {
            throw UnexpectedFailureException.with("Failed to collect class information on type $", e, element);
        }
    }
     
}
