package net.digitalid.utility.generator.information.type;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.state.Unmodifiable;
import net.digitalid.utility.validation.annotations.generation.Recover;
import net.digitalid.utility.exceptions.ConformityViolation;
import net.digitalid.utility.exceptions.UnexpectedFailureException;
import net.digitalid.utility.functional.interfaces.Predicate;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.generators.BuilderGenerator;
import net.digitalid.utility.generator.generators.ConverterGenerator;
import net.digitalid.utility.generator.generators.SubclassGenerator;
import net.digitalid.utility.generator.information.field.DirectlyAccessibleDeclaredFieldInformation;
import net.digitalid.utility.generator.information.field.FieldInformation;
import net.digitalid.utility.generator.information.field.NonAccessibleDeclaredFieldInformation;
import net.digitalid.utility.generator.information.field.NonDirectlyAccessibleDeclaredFieldInformation;
import net.digitalid.utility.generator.information.filter.InformationFilter;
import net.digitalid.utility.generator.information.filter.MethodSignatureMatcher;
import net.digitalid.utility.generator.information.method.ConstructorInformation;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.generator.information.method.MethodParameterInformation;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processing.utility.StaticProcessingEnvironment;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.size.MinSize;

/**
 * This type collects the relevant information about a class for generating a {@link SubclassGenerator subclass}, {@link BuilderGenerator builder} and {@link ConverterGenerator converter}.
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
    private final @Nonnull FiniteIterable<MethodInformation> overriddenMethods;
    
    @Pure
    @Override
    public @Nonnull FiniteIterable<MethodInformation> getOverriddenMethods() {
        return overriddenMethods;
    }
    
    /* -------------------------------------------------- Object Construction -------------------------------------------------- */
    
    /**
     * The method information of the recover method, or null if the class does not implement a recover method.
     */
    // TODO (steffi, 18.04.2016): do we even need this?!
    public final @Nullable MethodInformation recoverMethod;
    
    /**
     * The predicate that checks whether a method information object is annotated with {@link Recover @Recover}.
     */
    private static final @Nonnull Predicate<MethodInformation> recoverMethodMatcher = new Predicate<MethodInformation>() {
        
        final @Nonnull TypeMirror recoverAnnotationType = StaticProcessingEnvironment.getElementUtils().getTypeElement(Recover.class.getName()).asType();
        
        @Override 
        public boolean evaluate(@Nonnull MethodInformation methodInformation) {
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
    private final @Unmodifiable @Nonnull @MinSize(1) FiniteIterable<@Nonnull ConstructorInformation> constructors;
    
    /**
     * Returns the constructors that are declared in this class.
     */
    @Pure
    @Override
    public @Unmodifiable @Nonnull @MinSize(1) FiniteIterable<@Nonnull ConstructorInformation> getConstructors() {
        return constructors;
    }
    
    /* -------------------------------------------------- Recover Executable -------------------------------------------------- */
    
    /**
     * Returns the executable element used to create an instance of this type.
     * The executable element is either the recover method (if available) or the single
     * constructor. If no recover method and multiple constructors exist, a unsupported type exception is thrown, indicating that we cannot defer how to create an instance of this type.
     * An unexpected failure exception is thrown if no constructors could be found. This should never happen.
     */
    public @Nullable ExecutableElement getRecoverExecutable(@Nullable MethodInformation recoverMethod, @Nonnull FiniteIterable<@Nonnull ConstructorInformation> constructors) {
        if (recoverMethod != null) {
            return recoverMethod.getElement();
        } else {
            if (constructors.size() > 1) {
                ProcessingLog.debugging("More than one constructor, but no recover method found. We cannot decide which constructor to use for object construction.");
                return null;
            } else if (constructors.size() == 0) {
                throw UnexpectedFailureException.with("No constructor found.");
            } else {
                final @Nonnull ConstructorInformation constructor = constructors.iterator().next();
                return constructor.getElement();
            }
        }
    }
    
    /* -------------------------------------------------- Representing Field Information -------------------------------------------------- */
    
    /**
     * Finds and returns a subset of the accessible field information that maps to the parameters of the constructors.
     */
    private @Nonnull FiniteIterable<@Nonnull FieldInformation> extractRepresentingFieldInformation() {
        final @Nonnull Set<FieldInformation> representingFieldInformation = new HashSet<>();
        for (@Nonnull ConstructorInformation constructor : constructors) {
            @Nonnull final FiniteIterable<MethodParameterInformation> parameters = constructor.getParameters();
            for (@Nonnull MethodParameterInformation parameter : parameters) {
                if (parameter.getMatchingField() != null && parameter.getMatchingField().isAccessible()) {
                    representingFieldInformation.add(parameter.getMatchingField());
                }
            }
        }
        return FiniteIterable.of(representingFieldInformation).combine(generatedRepresentingFieldInformation);
    }
    
    private final @Nonnull FiniteIterable<@Nonnull FieldInformation> representingFieldInformation;
    
    /**
     * Returns a subset of the accessible field information that maps to the parameters of the constructors.
     */
    @Override
    public @Unmodifiable @Nonnull FiniteIterable<FieldInformation> getRepresentingFieldInformation() {
        return representingFieldInformation;
    }
    
    /* -------------------------------------------------- Directly Accessible Field Information -------------------------------------------------- */
    
    /**
     * Stores the directly accessible fields.
     */
    public final @Unmodifiable @Nonnull FiniteIterable<@Nonnull DirectlyAccessibleDeclaredFieldInformation> directlyAccessibleDeclaredFields;
    
    /* -------------------------------------------------- Non-directly Accessible Fields -------------------------------------------------- */
    
    /**
     * Stores the non-directly accessible fields.
     */
    public final @Unmodifiable @Nonnull FiniteIterable<@Nonnull NonDirectlyAccessibleDeclaredFieldInformation> nonDirectlyAccessibleDeclaredFields;
    
    /**
     * Returns a method information object that matches the expected declaration of a getter for a certain field.
     * A {@link ConformityViolation conformity violation exception} is thrown if the getter was not found.
     */
    private static @Unmodifiable @Nonnull MethodInformation getGetterOf(@Nonnull String fieldName, @Nonnull FiniteIterable<@Nonnull MethodInformation> methodsOfType) {
        final @Nonnull String nameRegex = "(get|has|is)" + Strings.capitalizeFirstLetters(fieldName);
        final @Nullable MethodInformation methodInformation = methodsOfType.findFirst(MethodSignatureMatcher.of(nameRegex));
        if (methodInformation == null) {
            throw ConformityViolation.with("Getter method for $ not found", fieldName);
        }
        return methodInformation;
    }
    
    /**
     * Returns true iff a getter method was found in a list of methods of a type for a given field.
     */
    private static boolean hasGetter(@Nonnull String fieldName, @Nonnull FiniteIterable<@Nonnull MethodInformation> methodsOfType) {
        final @Nonnull String nameRegex = "(get|has|is)" + Strings.capitalizeFirstLetters(fieldName);
        final @Nullable MethodInformation methodInformation = methodsOfType.findFirst(MethodSignatureMatcher.of(nameRegex));
        return methodInformation != null;
    }
    
    /**
     * Returns a method information object that matches the expected declaration of a setter for a certain field.
     * If the setter was not found, null is returned.
     */
    private static @Nullable MethodInformation getSetterOf(@Nonnull String fieldName, @Nonnull String fieldType, @Nonnull @NonNullableElements FiniteIterable<MethodInformation> methodsOfType) {
        final @Nonnull String methodName = "set" + Strings.capitalizeFirstLetters(fieldName);
        return methodsOfType.findFirst(MethodSignatureMatcher.of(methodName, fieldType));
    }
    
    /**
     * Retrieves declared field information objects for fields in a type.
     */
    private static @Nonnull FiniteIterable<@Nonnull NonDirectlyAccessibleDeclaredFieldInformation> getNonDirectlyAccessibleFieldInformation(@Nonnull FiniteIterable<@Nonnull VariableElement> fields, @Nonnull FiniteIterable<@Nonnull MethodInformation> methodInformation, @Nonnull DeclaredType containingType) {
        return fields.filter(field -> (
                field.getModifiers().contains(Modifier.PRIVATE) &&
                        hasGetter(field.getSimpleName().toString(), methodInformation)
        )).map(field ->
                NonDirectlyAccessibleDeclaredFieldInformation.of(field, containingType, getGetterOf(field.getSimpleName().toString(), methodInformation), getSetterOf(field.getSimpleName().toString(), ProcessingUtility.getQualifiedName(field.asType()), methodInformation))
        );
    }
    
    /* -------------------------------------------------- All accessible fields -------------------------------------------------- */
    
    /**
     * Returns all field information objects that represent fields which are accessible from other classes.
     */
    @Override
    public @Nonnull FiniteIterable<FieldInformation> getAccessibleFieldInformation() {
        return directlyAccessibleDeclaredFields.map(field -> (FieldInformation) field).combine(nonDirectlyAccessibleDeclaredFields).combine(generatedRepresentingFieldInformation);
    }
    
    /* -------------------------------------------------- Writable Accessible Fields -------------------------------------------------- */
    
    /**
     * Stores the accessible fields that are mutable and must be validated by the generated subclass.
     */
    public final @Nonnull FiniteIterable<@Nonnull DirectlyAccessibleDeclaredFieldInformation> writableAccessibleFields;
    
    /* -------------------------------------------------- Non-accessible Fields -------------------------------------------------- */
    
    /**
     * Stores the non-accessible fields.
     */
    public final @Nonnull FiniteIterable<@Nonnull NonAccessibleDeclaredFieldInformation> nonAccessibleDeclaredFields;
    
    /**
     * Retrieves declared field information objects for fields in a type.
     */
    private static @Nonnull FiniteIterable<@Nonnull NonAccessibleDeclaredFieldInformation> getNonAccessibleFieldInformation(@Nonnull FiniteIterable<@Nonnull VariableElement> fields, @Nonnull FiniteIterable<@Nonnull MethodInformation> methodInformation, @Nonnull DeclaredType containingType) {
        ProcessingLog.debugging("Fields: $", fields.join());
        return fields.filter(field -> (
                field.getModifiers().contains(Modifier.PRIVATE) &&
                        !hasGetter(field.getSimpleName().toString(), methodInformation)
        )).map(field ->
                NonAccessibleDeclaredFieldInformation.of(field, containingType)
        );
    }
    
    /* -------------------------------------------------- All Field Information -------------------------------------------------- */
    
    /**
     * Returns all field information objects that represent accessible and non-accessible fields.
     */
    @Override
    public @Nonnull FiniteIterable<FieldInformation> getFieldInformation() {
        return getAccessibleFieldInformation().combine(nonAccessibleDeclaredFields);
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a class information object.
     */
    protected ClassInformation(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType) {
        super(typeElement, containingType);
        
        final @Nonnull FiniteIterable<MethodInformation> methodInformationIterable = InformationFilter.getMethodInformation(typeElement, containingType);
        
        ProcessingLog.debugging("All methods of type $: $", containingType, methodInformationIterable.join());
        
        
        final @Nonnull Predicate<MethodInformation> equalsPredicate = MethodSignatureMatcher.of("equals", Object.class).and(method -> !method.isFinal()).and(method -> !method.isAbstract());
        final @Nonnull Predicate<MethodInformation> hashCodePredicate = MethodSignatureMatcher.of("hashCode").and(method -> !method.isFinal()).and(method -> !method.isAbstract());
        final @Nonnull Predicate<MethodInformation> toStringPredicate = MethodSignatureMatcher.of("toString").and(method -> !method.isFinal()).and(method -> !method.isAbstract());
        ProcessingLog.debugging("compare to method signature using type: $", StaticProcessingEnvironment.getTypeUtils().asElement(containingType).toString());
        final @Nonnull Predicate<MethodInformation> compareToPredicate = MethodSignatureMatcher.of("compareTo", "?").and(method -> !method.isFinal()).and(MethodInformation::isAbstract);
        final @Nonnull Predicate<MethodInformation> clonePredicate = MethodSignatureMatcher.of("clone").and(method -> !method.isFinal());
        final @Nonnull Predicate<MethodInformation> validatePredicate = MethodSignatureMatcher.of("validate").and(method -> !method.isFinal());
        this.equalsMethod = methodInformationIterable.findFirst(equalsPredicate);
        this.hashCodeMethod = methodInformationIterable.findFirst(hashCodePredicate);
        this.toStringMethod = methodInformationIterable.findFirst(toStringPredicate);
        this.compareToMethod = methodInformationIterable.findFirst(compareToPredicate);
        this.cloneMethod = methodInformationIterable.findFirst(clonePredicate);
        this.validateMethod = methodInformationIterable.findFirst(validatePredicate);
        
        this.overriddenMethods = methodInformationIterable.filter(method -> !method.isDeclaredInRuntimeEnvironment()).filter(method -> !method.isFinal()).filter(method -> !method.isAbstract()).filter(method -> !method.isStatic()).filter(method -> !method.isPrivate()).filter(equalsPredicate.negate().and(hashCodePredicate.negate()).and(toStringPredicate.negate()).and(compareToPredicate.negate()).and(clonePredicate.negate()).and(validatePredicate.negate()));
        
        FiniteIterable<MethodInformation> methodsMatchingTheRecoverMethodMatcher = methodInformationIterable.filter(recoverMethodMatcher);
        if (methodsMatchingTheRecoverMethodMatcher.size() > 1) {
            ProcessingLog.information("More than one recover methods found in class '" + typeElement.getSimpleName() + "'. We cannot decide which method to use for object construction.");
            this.recoverMethod = null;
        } else if (methodsMatchingTheRecoverMethodMatcher.iterator().hasNext()) {
            this.recoverMethod = methodsMatchingTheRecoverMethodMatcher.iterator().next();
        } else {
            this.recoverMethod = null;
        }
        
        final @Nonnull FiniteIterable<@Nonnull VariableElement> fields = ProcessingUtility.getAllFields(typeElement);
        
        this.directlyAccessibleDeclaredFields = fields.filter((field) -> (!field.getModifiers().contains(Modifier.PRIVATE))).map((field) -> (DirectlyAccessibleDeclaredFieldInformation.of(field, containingType)));
        
        this.writableAccessibleFields = directlyAccessibleDeclaredFields.filter((field) -> (!field.getModifiers().contains(Modifier.FINAL)));
        
        this.nonDirectlyAccessibleDeclaredFields = getNonDirectlyAccessibleFieldInformation(fields, methodInformationIterable, containingType);
        
        this.nonAccessibleDeclaredFields = getNonAccessibleFieldInformation(fields, methodInformationIterable, containingType);
        
        this.constructors = ProcessingUtility.getConstructors(typeElement).map((element) -> (ConstructorInformation.of(element, containingType, getFieldInformation())));
        
        this.representingFieldInformation = extractRepresentingFieldInformation();
    }
    
    /**
     * Returns a class information object of the given type typeElement and containing type.
     */
    public static @Nonnull ClassInformation of(@Nonnull TypeElement element, @Nonnull DeclaredType containingType) {
        return new ClassInformation(element, containingType);
    }
    
}

