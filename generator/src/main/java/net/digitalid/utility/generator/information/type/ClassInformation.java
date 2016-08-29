package net.digitalid.utility.generator.information.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.state.Unmodifiable;
import net.digitalid.utility.functional.interfaces.Predicate;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.generators.BuilderGenerator;
import net.digitalid.utility.generator.generators.ConverterGenerator;
import net.digitalid.utility.generator.generators.SubclassGenerator;
import net.digitalid.utility.generator.information.field.DirectlyAccessibleDeclaredFieldInformation;
import net.digitalid.utility.generator.information.field.FieldInformation;
import net.digitalid.utility.generator.information.field.GeneratedRepresentingFieldInformation;
import net.digitalid.utility.generator.information.filter.MethodSignatureMatcher;
import net.digitalid.utility.generator.information.method.ExecutableInformation;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.generator.information.method.MethodParameterInformation;
import net.digitalid.utility.validation.annotations.generation.NonRepresentative;

/**
 * This type collects the relevant information about a class for generating a {@link SubclassGenerator subclass}, {@link BuilderGenerator builder} and {@link ConverterGenerator converter}.
 */
public final class ClassInformation extends InstantiableTypeInformation {
    
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
    
    /* -------------------------------------------------- Representing Field Information -------------------------------------------------- */
    
    /**
     * Returns a subset of the accessible field information that maps to the parameters of the constructors.
     */
    @Override
    public @Unmodifiable @Nonnull FiniteIterable<FieldInformation> getRepresentingFieldInformation() {
        final @Nonnull List<FieldInformation> representingFieldInformation = new ArrayList<>();
        final @Nullable ExecutableInformation recoverConstructorOrMethod = getRecoverConstructorOrMethod();
        if (recoverConstructorOrMethod != null) {
            final @Nonnull FiniteIterable<MethodParameterInformation> parameters = recoverConstructorOrMethod.getParameters();
            for (@Nonnull MethodParameterInformation parameter : parameters) {
                if (parameter.getMatchingField() != null) {
                    final @Nonnull FieldInformation matchingFieldInformation = parameter.getMatchingField();
                    if (matchingFieldInformation.isAccessible() && !representingFieldInformation.contains(matchingFieldInformation)) {
                        representingFieldInformation.add(matchingFieldInformation);
                    }
                } else if (parameter.getMatchingGetter() != null) {
                    final @Nonnull MethodInformation matchingGetter = parameter.getMatchingGetter();
                    if (!representingFieldInformation.contains(parameter.getMatchingField())) {
                        representingFieldInformation.add(GeneratedRepresentingFieldInformation.of(getContainingType(), matchingGetter, null));
                    }
                }
            }
            // TODO: what if the constructor already declared generated representing fields? In that case we would duplicate the parameters.
            return FiniteIterable.of(representingFieldInformation).combine(generatedRepresentingFieldInformation).filter(field -> !field.hasAnnotation(NonRepresentative.class));
        }
        return FiniteIterable.of(Collections.emptyList());
    }
    
    /* -------------------------------------------------- Writable Accessible Fields -------------------------------------------------- */
    
    /**
     * Stores the accessible fields that are mutable and must be validated by the generated subclass.
     */
    public final @Nonnull FiniteIterable<@Nonnull DirectlyAccessibleDeclaredFieldInformation> writableAccessibleFields;
    
    /* -------------------------------------------------- Initialization Marker -------------------------------------------------- */
    
    private boolean initialized = false;
    
    @Override
    public boolean isInitialized() {
        return initialized;
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a class information object.
     */
    protected ClassInformation(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType) {
        super(typeElement, containingType);
    
        final @Nonnull Predicate<MethodInformation> equalsPredicate = MethodSignatureMatcher.of("equals", Object.class).and(method -> !method.isFinal()).and(method -> !method.isAbstract());
        final @Nonnull Predicate<MethodInformation> hashCodePredicate = MethodSignatureMatcher.of("hashCode").and(method -> !method.isFinal()).and(method -> !method.isAbstract());
        final @Nonnull Predicate<MethodInformation> toStringPredicate = MethodSignatureMatcher.of("toString").and(method -> !method.isFinal()).and(method -> !method.isAbstract());
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
    
        this.writableAccessibleFields = directlyAccessibleDeclaredFields.filter((field) -> (!field.getModifiers().contains(Modifier.FINAL)));
        
        this.initialized = true;
    }
    
    /**
     * Returns a class information object of the given type typeElement and containing type.
     */
    public static @Nonnull ClassInformation of(@Nonnull TypeElement element, @Nonnull DeclaredType containingType) {
        return new ClassInformation(element, containingType);
    }
    
}

