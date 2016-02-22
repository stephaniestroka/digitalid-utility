package net.digitalid.utility.processor;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.ElementFilter;

import net.digitalid.utility.logging.Log;
import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.logging.processing.AnnotationProcessing;
import net.digitalid.utility.logging.processing.SourcePosition;
import net.digitalid.utility.string.QuoteString;
import net.digitalid.utility.string.iterable.NonNullableElementConverter;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.meta.Validator;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.reference.Capturable;
import net.digitalid.utility.validation.annotations.type.Utiliy;
import net.digitalid.utility.validation.validator.AnnotationValidator;
import net.digitalid.utility.validation.validator.CodeGenerator;

/**
 * This class provides useful methods for annotation processing.
 */
@Utiliy
public class ProcessingUtility {
    
    /* -------------------------------------------------- Default Constructor -------------------------------------------------- */
    
    /**
     * Returns whether the given element is a class with a public default constructor.
     */
    @Pure
    public static boolean hasPublicDefaultConstructor(@Nonnull Element element) {
        for (@Nonnull ExecutableElement constructor : ElementFilter.constructorsIn(element.getEnclosedElements())) {
            AnnotationLog.verbose("Found the constructor", SourcePosition.of(constructor));
            if (constructor.getParameters().isEmpty() && constructor.getModifiers().contains(Modifier.PUBLIC)) { return true; }
        }
        AnnotationLog.debugging("Found no public default constructor in", SourcePosition.of(element));
        return false;
    }
    
    /* -------------------------------------------------- Annotation Mirror -------------------------------------------------- */
    
    /**
     * Returns the annotation mirror corresponding to the given annotation type of the given element or null if not found.
     */
    @Pure
    public static @Nullable AnnotationMirror getAnnotationMirror(@Nonnull Element element, @Nonnull Class<? extends Annotation> annotationType) {
        for (@Nonnull AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            final @Nonnull TypeElement annotationElement = (TypeElement) annotationMirror.getAnnotationType().asElement();
            AnnotationLog.verbose("Found the annotation '@" + annotationElement.getSimpleName() + "' on", SourcePosition.of(element));
            if (annotationElement.getQualifiedName().contentEquals(annotationType.getCanonicalName())) { return annotationMirror; }
        }
        return null;
    }
    
    /**
     * Returns the value of the given annotation type on the given element or null if not found.
     */
    @Pure
    public static @Nullable String getStringValue(@Nonnull Element element, @Nonnull Class<? extends Annotation> annotationType) {
        final @Nullable AnnotationMirror annotationMirror = getAnnotationMirror(element, annotationType);
        if (annotationMirror != null) {
            for (@Nonnull Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> annotationEntry : annotationMirror.getElementValues().entrySet()) {
                if (annotationEntry.getKey().getSimpleName().contentEquals("value")) {
                    final @Nonnull AnnotationValue annotationValue = annotationEntry.getValue();
                    final @Nonnull Object object = annotationValue.getValue();
                    if (object instanceof String) {
                        return (String) object;
                    } else {
                        AnnotationLog.error("The value is not a string:", SourcePosition.of(element, annotationMirror, annotationValue));
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * Returns the code generators of the given type which are found with the given meta-annotation type on the annotations of the given element.
     */
    @Pure
    @SuppressWarnings("unchecked")
    public static @Nonnull @NonNullableElements <G extends CodeGenerator> Map<AnnotationMirror, G> getCodeGenerators(@Nonnull Element element, @Nonnull Class<? extends Annotation> metaAnnotationType, @Nonnull Class<G> codeGeneratorType) {
        final @Nonnull @NonNullableElements Map<AnnotationMirror, G> result = new LinkedHashMap<>();
        for (@Nonnull AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            final @Nonnull TypeElement annotationElement = (TypeElement) annotationMirror.getAnnotationType().asElement();
            AnnotationLog.verbose("Found the annotation '@" + annotationElement.getSimpleName() + "' on", SourcePosition.of(element));
            final @Nullable AnnotationMirror metaAnnotationMirror = getAnnotationMirror(annotationElement, metaAnnotationType);
            if (metaAnnotationMirror != null) {
                for (@Nonnull Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> metaAnnotationEntry : metaAnnotationMirror.getElementValues().entrySet()) {
                    if (metaAnnotationEntry.getKey().getSimpleName().contentEquals("value")) {
                        final @Nonnull AnnotationValue metaAnnotationValue = metaAnnotationEntry.getValue();
                        final @Nonnull DeclaredType codeGeneratorImplementationType = (DeclaredType) metaAnnotationValue.getValue();
                        AnnotationLog.verbose("The declared generator type is " + QuoteString.inSingle(codeGeneratorImplementationType));
                        final @Nonnull TypeElement codeGeneratorImplementationElement = (TypeElement) codeGeneratorImplementationType.asElement();
                        final @Nonnull String codeGeneratorImplementationBinaryName = AnnotationProcessing.getElementUtils().getBinaryName(codeGeneratorImplementationElement).toString();
                        try {
                            final @Nonnull Class<?> codeGeneratorImplementationClass = Class.forName(codeGeneratorImplementationBinaryName);
                            if (codeGeneratorType.isAssignableFrom(codeGeneratorImplementationClass)) {
                                // TODO: Cache the new generator instance!
                                final @Nonnull G codeGenerator = (G) codeGeneratorImplementationClass.newInstance();
                                codeGenerator.checkUsage(element, annotationMirror);
                                result.put(annotationMirror, codeGenerator);
                                AnnotationLog.debugging("Found the code generator '@" + annotationElement.getSimpleName() + "' for", SourcePosition.of(element));
                            } else {
                                AnnotationLog.error("The code generator '@" + annotationElement.getSimpleName() + "' is not assignable to '" + codeGeneratorImplementationClass.getCanonicalName() + "':", SourcePosition.of(element));
                            }
                        } catch (@Nonnull ClassNotFoundException | InstantiationException | IllegalAccessException exception) {
                            AnnotationLog.error("Could not instantiate the code generator " + QuoteString.inSingle(codeGeneratorImplementationBinaryName) + " for", SourcePosition.of(element));
                            Log.error("Problem:", exception);
                        }
                    } else {
                        AnnotationLog.error("The meta-annotation may only have a single method named 'value':", SourcePosition.of(metaAnnotationMirror.getAnnotationType().asElement()));
                    }
                }
            }
        }
        return Collections.unmodifiableMap(result);
    }
    
    /**
     * Returns the annotation validators mapped from their corresponding annotation mirror with which the given element is annotated.
     */
    @Pure
    public static @Nonnull @NonNullableElements Map<AnnotationMirror, AnnotationValidator> getAnnotationValidators(@Nonnull Element element) {
        return getCodeGenerators(element, Validator.class, AnnotationValidator.class);
    }
    
    /* -------------------------------------------------- Fields of Type -------------------------------------------------- */
    
    /**
     * Returns a list of all the fields with the given type in the given class.
     */
    @Pure
    public static @Capturable @Nonnull List<VariableElement> getFieldsOfType(@Nonnull TypeElement classElement, @Nonnull Class<?> fieldType) {
        final @Nonnull List<VariableElement> fields = new LinkedList<>();
        for (@Nonnull VariableElement field : ElementFilter.fieldsIn(classElement.getEnclosedElements())) {
            final @Nonnull String fieldTypeName = AnnotationProcessing.getTypeUtils().erasure(field.asType()).toString();
            AnnotationLog.verbose("Found with the type '" + fieldTypeName + "' the field", SourcePosition.of(field));
            if (fieldTypeName.equals(fieldType.getCanonicalName())) { fields.add(field); }
        }
        return fields;
    }
    
    /**
     * Returns the unique, public and static field with the given type in the given class.
     * If no field fulfilling all these criteria is found, this method logs a warning message and returns null. 
     */
    @Pure
    public static @Nullable VariableElement getUniquePublicStaticFieldOfType(@Nonnull TypeElement classElement, @Nonnull Class<?> fieldType) {
        final @Nonnull List<VariableElement> fields = getFieldsOfType(classElement, fieldType);
        if (fields.size() != 1) {
            AnnotationLog.warning("There is not exactly one field of type '" + fieldType.getCanonicalName()+ "' in the class", SourcePosition.of(classElement));
        } else {
            final @Nonnull VariableElement field = fields.get(0);
            if (!fields.get(0).getModifiers().contains(Modifier.PUBLIC)) {
                AnnotationLog.warning("The field of type '" + fieldType.getCanonicalName()+ "' has to be public:", SourcePosition.of(field));
            } else if (!fields.get(0).getModifiers().contains(Modifier.STATIC)) {
                AnnotationLog.warning("The field of type '" + fieldType.getCanonicalName()+ "' has to be static:", SourcePosition.of(field));
            } else {
                return field;
            }
        }
        return null;
    }
    
    /* -------------------------------------------------- Converters -------------------------------------------------- */
    
    @Deprecated
    public static final @Nonnull NonNullableElementConverter<VariableElement> DECLARATION_CONVERTER = new NonNullableElementConverter<VariableElement>() {
        @Override
        public String toString(@Nonnull VariableElement element) {
            return element.asType() + " " + element.getSimpleName();
        }
    };
    
    public static final @Nonnull NonNullableElementConverter<VariableElement> CALL_CONVERTER = new NonNullableElementConverter<VariableElement>() {
        @Override
        public String toString(@Nonnull VariableElement element) {
            return element.getSimpleName().toString();
        }
    };
    
}
