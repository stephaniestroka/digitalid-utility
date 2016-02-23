package net.digitalid.utility.generator.information;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.generator.BuilderGenerator;
import net.digitalid.utility.generator.SubclassGenerator;
import net.digitalid.utility.generator.annotations.DefaultValue;
import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.logging.processing.AnnotationProcessing;
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
    
    public List<String> getAnnotations() {
        @Nonnull List<String> annotations = new ArrayList<>();
        @Nonnull List<? extends AnnotationMirror> annotationMirrors;
        if (field != null) {
            annotationMirrors = field.getAnnotationMirrors();
        } else if (getter != null) {
            annotationMirrors = getter.element.getAnnotationMirrors();
        } else {
            AnnotationLog.debugging("field is null and getter is null. Returning empty annotations list.");
            return annotations;
        }
        for (AnnotationMirror annotationMirror : annotationMirrors) {
            DeclaredType annotationType = annotationMirror.getAnnotationType();
            final @Nonnull TypeElement annotationsTypeElement = (TypeElement) annotationType.asElement();
    
            final @Nonnull String annotation = AnnotationProcessing.getElementUtils().getBinaryName(annotationsTypeElement).toString();
            annotations.add(annotation);
        }
        AnnotationLog.debugging("getAnnotations() : " + annotations);
        return annotations;
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
    
    // TODO: Move common parts of the following static methods into the constructor!
    
    @Pure
    public static @Nonnull FieldInformation forField(@Nonnull DeclaredType type, @Nonnull VariableElement field) {
        return new FieldInformation(false, field.getSimpleName().toString(), AnnotationProcessing.getTypeUtils().asMemberOf(type, field), null, field, null, null, ProcessingUtility.getCodeGenerators(field, Validator.class, AnnotationValidator.class));
    }
    
    @Pure
    public static @Nonnull FieldInformation forField(@Nonnull VariableElement parameter, @Nonnull VariableElement field) {
        return new FieldInformation(false, parameter.getSimpleName().toString(), parameter.asType(), ProcessingUtility.getStringValue(parameter, DefaultValue.class), field, null, null, ProcessingUtility.getCodeGenerators(parameter, Validator.class, AnnotationValidator.class));
    }
    
    @Pure
    public static @Nonnull FieldInformation forField(@Nonnull VariableElement parameter, @Nonnull MethodInformation getter) {
        return new FieldInformation(false, parameter.getSimpleName().toString(), parameter.asType(), ProcessingUtility.getStringValue(parameter, DefaultValue.class), null, getter, null, ProcessingUtility.getCodeGenerators(parameter, Validator.class, AnnotationValidator.class));
    }
    
    // TODO: The type might not be necessary here as getter.element is already a member of the given type!
    @Pure
    public static @Nonnull FieldInformation forField(@Nonnull DeclaredType type, @Nonnull String name, @Nonnull MethodInformation getter, @Nullable MethodInformation setter) {
        return new FieldInformation(true, name, ((ExecutableType) AnnotationProcessing.getTypeUtils().asMemberOf(type, getter.element)).getReturnType(), ProcessingUtility.getStringValue(getter.element, DefaultValue.class), null, getter, setter, ProcessingUtility.getCodeGenerators(getter.element, Validator.class, AnnotationValidator.class));
    }
    
}
