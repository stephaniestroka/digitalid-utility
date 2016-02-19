package net.digitalid.utility.generator.information;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.generator.BuilderGenerator;
import net.digitalid.utility.generator.SubclassGenerator;
import net.digitalid.utility.generator.annotations.DefaultValue;
import net.digitalid.utility.processor.ProcessingUtility;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.meta.Validator;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.validator.AnnotationValidator;

/**
 * This class collects the relevant information about a field for generating a subclass and builder.
 * 
 * @see SubclassGenerator
 * @see BuilderGenerator
 */
public class FieldInformation {
    
    public final boolean generated;
    
    public final @Nonnull String name;
    
    public final @Nonnull TypeMirror type;
    
    public final @Nullable String defaultValue;
    
    public final @Nullable VariableElement field;
    
    public final @Nullable MethodInformation getter;
    
    public final @Nullable MethodInformation setter;
    
    @Pure
    public boolean isMutable() {
        return setter != null || field != null && !field.getModifiers().contains(Modifier.FINAL);
    }
    
    /* -------------------------------------------------- Validators -------------------------------------------------- */
    
    /**
     * Stores the validators that validate the field.
     */
    public final @Nonnull @NonNullableElements Map<AnnotationMirror, AnnotationValidator> validators;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected FieldInformation(boolean generated, @Nonnull String name, @Nonnull TypeMirror type, @Nullable String defaultValue, @Nullable VariableElement field, @Nullable MethodInformation getter, @Nullable MethodInformation setter, @Nonnull @NonNullableElements Map<AnnotationMirror, AnnotationValidator> variableValidators) {
        this.generated = generated;
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
        this.field = field;
        this.getter = getter;
        this.setter = setter;
        this.validators = variableValidators;
    }
    
    @Pure
    public static @Nonnull FieldInformation forField(@Nonnull VariableElement parameter, @Nonnull VariableElement field) {
        return new FieldInformation(false, parameter.getSimpleName().toString(), parameter.asType(), ProcessingUtility.getStringValue(parameter, DefaultValue.class), field, null, null, ProcessingUtility.getCodeGenerators(parameter, Validator.class, AnnotationValidator.class));
    }
    
    @Pure
    public static @Nonnull FieldInformation forField(@Nonnull VariableElement parameter, @Nonnull MethodInformation getter) {
        return new FieldInformation(false, parameter.getSimpleName().toString(), parameter.asType(), ProcessingUtility.getStringValue(parameter, DefaultValue.class), null, getter, null, ProcessingUtility.getCodeGenerators(parameter, Validator.class, AnnotationValidator.class));
    }
    
    @Pure
    public static @Nonnull FieldInformation forField(@Nonnull String name, @Nullable MethodInformation getter, @Nullable MethodInformation setter) {
        Require.that(getter != null || setter != null).orThrow("The getter and the setter may not both be null.");
        
        final @Nonnull TypeMirror type = getter != null ? getter.methodElement.getReturnType() : setter.methodElement.getParameters().get(0).asType();
        final @Nullable String defaultValue = getter != null ? ProcessingUtility.getStringValue(getter.methodElement, DefaultValue.class) : null;
        return new FieldInformation(true, name, type, defaultValue, null, getter, setter, ProcessingUtility.getCodeGenerators(getter != null ? getter.methodElement : setter.methodElement.getParameters().get(0), Validator.class, AnnotationValidator.class));
    }
    
}
