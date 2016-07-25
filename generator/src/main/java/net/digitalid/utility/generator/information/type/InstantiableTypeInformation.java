package net.digitalid.utility.generator.information.type;

import java.util.NoSuchElementException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.state.Unmodifiable;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.exceptions.FailedClassGenerationException;
import net.digitalid.utility.generator.information.field.DirectlyAccessibleDeclaredFieldInformation;
import net.digitalid.utility.generator.information.field.FieldInformation;
import net.digitalid.utility.generator.information.field.NonAccessibleDeclaredFieldInformation;
import net.digitalid.utility.generator.information.field.NonDirectlyAccessibleDeclaredFieldInformation;
import net.digitalid.utility.generator.information.filter.InformationFilter;
import net.digitalid.utility.generator.information.method.ConstructorInformation;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.generator.information.variable.VariableElementInformation;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.validation.annotations.generation.Recover;
import net.digitalid.utility.validation.annotations.size.NonEmpty;

/**
 *
 */
public abstract class InstantiableTypeInformation extends TypeInformation {
    
    protected final @Nonnull FiniteIterable<MethodInformation> methodInformationIterable;
    
    /* -------------------------------------------------- Recover Method -------------------------------------------------- */
    
    /**
     * The method information of the recover method, or null if the class does not implement a recover method.
     */
    private final @Nullable MethodInformation recoverMethod;
    
    @Override
    public @Nullable MethodInformation getRecoverMethod() {
        return recoverMethod;
    }
    
    /* -------------------------------------------------- Constructor Parameter -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull FiniteIterable<VariableElementInformation> getConstructorParameters() {
        if (recoverMethod != null) {
            return recoverMethod.getParameters().map(parameter -> parameter.getMatchingField()).map(variableElement -> getFieldInformation().findUnique(field -> variableElement.getSimpleName().contentEquals(field.getName())));
        } else {
            final @Nonnull FiniteIterable<VariableElementInformation> constructorParameters;
            @Unmodifiable @Nonnull final FiniteIterable<@Nonnull ConstructorInformation> constructors = getConstructors();
            @Nonnull ConstructorInformation constructorInformation;
            if (constructors.size() > 1) {
                try {
                    constructorInformation = constructors.findUnique(constructor -> constructor.hasAnnotation(Recover.class));
                } catch (NoSuchElementException e) {
                    throw FailedClassGenerationException.with("Multiple constructors found, but none is marked with @Recover.", SourcePosition.of(getElement()));
                }
            } else {
                constructorInformation = constructors.getFirst();
            }
            constructorParameters = constructorInformation.getParameters().map(parameter -> (VariableElementInformation) parameter).combine(generatedRepresentingFieldInformation);
            return constructorParameters;
        }
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
    
    /* -------------------------------------------------- All accessible fields -------------------------------------------------- */
    
    /**
     * Returns all field information objects that represent fields which are accessible from other classes.
     */
    @Override
    public final @Nonnull FiniteIterable<FieldInformation> getAccessibleFieldInformation() {
        return directlyAccessibleDeclaredFields.map(field -> (FieldInformation) field).combine(nonDirectlyAccessibleDeclaredFields).combine(generatedRepresentingFieldInformation);
    }
    
    /* -------------------------------------------------- Non-accessible Fields -------------------------------------------------- */
    
    /**
     * Stores the non-accessible fields.
     */
    public final @Nonnull FiniteIterable<@Nonnull NonAccessibleDeclaredFieldInformation> nonAccessibleDeclaredFields;
    
    /* -------------------------------------------------- All Field Information -------------------------------------------------- */
    
    /**
     * Returns all field information objects that represent accessible and non-accessible fields.
     */
    @Override
    public final @Nonnull FiniteIterable<FieldInformation> getFieldInformation() {
        return getAccessibleFieldInformation().combine(nonAccessibleDeclaredFields);
    }
    
    /* -------------------------------------------------- Object Construction -------------------------------------------------- */
    
    /**
     * The list of constructors. There is at least one constructor per class.
     */
    private final @Unmodifiable @Nonnull @NonEmpty FiniteIterable<@Nonnull ConstructorInformation> constructors;
    
    /**
     * Returns the constructors that are declared in this class.
     */
    @Pure
    @Override
    public @Unmodifiable @Nonnull @NonEmpty FiniteIterable<@Nonnull ConstructorInformation> getConstructors() {
        return constructors;
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates an instantiable type information object.
     */
    protected InstantiableTypeInformation(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType) {
        super(typeElement, containingType);
    
        this.methodInformationIterable = InformationFilter.getMethodInformation(typeElement, containingType);
        ProcessingLog.debugging("All methods of type $: $", containingType, methodInformationIterable.join());
        
        this.recoverMethod = methodInformationIterable.findFirst(method -> method.hasAnnotation(Recover.class));
        
        this.directlyAccessibleDeclaredFields = InformationFilter.getDirectlyAccessibleFieldInformation(typeElement);
        
        this.nonDirectlyAccessibleDeclaredFields = InformationFilter.getNonDirectlyAccessibleFieldInformation(typeElement, methodInformationIterable);
        
        this.nonAccessibleDeclaredFields = InformationFilter.getNonAccessibleFieldInformation(typeElement, methodInformationIterable);
    
        this.constructors = ProcessingUtility.getConstructors(typeElement).map((element) -> (ConstructorInformation.of(element, containingType)));
    }
    
}
