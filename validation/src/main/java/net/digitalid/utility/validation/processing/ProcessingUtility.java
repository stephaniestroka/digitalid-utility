package net.digitalid.utility.validation.processing;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.QualifiedNameable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.logging.processing.ProcessingLog;
import net.digitalid.utility.logging.processing.SourcePosition;
import net.digitalid.utility.logging.processing.StaticProcessingEnvironment;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.meta.MethodValidator;
import net.digitalid.utility.validation.annotations.meta.TypeValidator;
import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.reference.Capturable;
import net.digitalid.utility.validation.annotations.type.Utility;
import net.digitalid.utility.validation.validator.AnnotationHandler;
import net.digitalid.utility.validation.validator.MethodAnnotationValidator;
import net.digitalid.utility.validation.validator.TypeAnnotationValidator;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 * This class provides useful methods for annotation processing.
 */
@Utility
public class ProcessingUtility {
    
    /* -------------------------------------------------- Declaration -------------------------------------------------- */
    
    /**
     * Returns the qualified name of the package in which the given element is declared.
     */
    @Pure
    public static @Nonnull String getPackageName(@Nonnull Element element) {
        return StaticProcessingEnvironment.getElementUtils().getPackageOf(element).getQualifiedName().toString();
    }
    
    /**
     * Returns whether the given element is declared in the Java Runtime Environment (JRE).
     */
    @Pure
    public static boolean isDeclaredInRuntimeEnvironment(@Nonnull Element element) {
        // Matches java. and javax. packages.
        return getPackageName(element).startsWith("java");
    }
    
    /**
     * Returns whether the given element is declared in the Digital ID library (DID SDK).
     */
    @Pure
    public static boolean isDeclaredInDigitalIDLibrary(@Nonnull Element element) {
        return getPackageName(element).startsWith("net.digitalid.");
    }
    
    /* -------------------------------------------------- Surrounding Type -------------------------------------------------- */
    
    /**
     * Returns the type in which the given element is declared.
     * 
     * @require element.getKind() != ElementKind.PACKAGE : "The element may not be a package.";
     */
    @Pure
    public static @Nonnull TypeElement getSurroundingType(@Nonnull Element element) {
        Require.that(element.getKind() != ElementKind.PACKAGE).orThrow("The element $ may not be a package.", SourcePosition.of(element));
        
        if (element.getKind().isClass() || element.getKind().isInterface()) { return (TypeElement) element; }
        return getSurroundingType(element.getEnclosingElement());
    }
    
    /* -------------------------------------------------- Default Constructor -------------------------------------------------- */
    
    /**
     * Returns whether the given element is a class with a public default constructor.
     */
    @Pure
    public static boolean hasPublicDefaultConstructor(@Nonnull Element element) {
        for (@Nonnull ExecutableElement constructor : ElementFilter.constructorsIn(element.getEnclosedElements())) {
            ProcessingLog.verbose("Found the constructor", SourcePosition.of(constructor));
            if (constructor.getParameters().isEmpty() && constructor.getModifiers().contains(Modifier.PUBLIC)) { return true; }
        }
        ProcessingLog.debugging("Found no public default constructor in", SourcePosition.of(element));
        return false;
    }
    
    /* -------------------------------------------------- Annotation Mirrors -------------------------------------------------- */
    
    /**
     * Returns the qualified name of the annotation type of the given annotation mirror.
     */
    @Pure
    public static @Nonnull String getQualifiedName(@Nonnull AnnotationMirror annotationMirror) {
        return ((QualifiedNameable) annotationMirror.getAnnotationType().asElement()).getQualifiedName().toString();
    }
    
    /**
     * Returns the annotation mirror corresponding to the given annotation type of the given element or null if not found.
     */
    @Pure
    public static @Nullable AnnotationMirror getAnnotationMirror(@Nonnull Element element, @Nonnull Class<? extends Annotation> annotationType) {
        for (@Nonnull AnnotationMirror annotationMirror : StaticProcessingEnvironment.getElementUtils().getAllAnnotationMirrors(element)) {
            if (getQualifiedName(annotationMirror).equals(annotationType.getCanonicalName())) { return annotationMirror; }
        }
        ProcessingLog.debugging("Found no $ annotation on", SourcePosition.of(element), "@" + annotationType.getSimpleName());
        return null;
    }
    
    /**
     * Returns whether the given element has an annotation of the given type.
     */
    @Pure
    public static boolean hasAnnotation(@Nonnull Element element, @Nonnull Class<? extends Annotation> annotationType) {
        return getAnnotationMirror(element, annotationType) != null;
    }
    
    /**
     * Returns the annotation value for the given method name of the given annotation mirror or null if not found.
     */
    @Pure
    public static @Nullable AnnotationValue getAnnotationValue(@Nonnull AnnotationMirror annotationMirror, @Nonnull String methodName) {
        for (@Nonnull Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> annotationEntry : StaticProcessingEnvironment.getElementUtils().getElementValuesWithDefaults(annotationMirror).entrySet()) {
            if (annotationEntry.getKey().getSimpleName().contentEquals(methodName)) {
                return annotationEntry.getValue();
            }
        }
        ProcessingLog.error("Found no value $ for the annotation $.", methodName, "@" + annotationMirror.getAnnotationType().asElement().getSimpleName());
        return null;
    }
    
    /**
     * Returns the annotation value for the value method of the given annotation mirror or null if not found.
     */
    @Pure
    public static @Nullable AnnotationValue getAnnotationValue(@Nonnull AnnotationMirror annotationMirror) {
        return getAnnotationValue(annotationMirror, "value");
    }
    
    /**
     * Returns the annotation value for the given method name of the given annotation type on the given element or null if not found.
     */
    @Pure
    public static @Nullable AnnotationValue getAnnotationValue(@Nonnull Element element, @Nonnull Class<? extends Annotation> annotationType, @Nonnull String methodName) {
        final @Nullable AnnotationMirror annotationMirror = getAnnotationMirror(element, annotationType);
        return annotationMirror != null ? getAnnotationValue(annotationMirror, methodName) : null;
    }
    
    /**
     * Returns the annotation value for the default value method of the given annotation type on the given element or null if not found.
     */
    @Pure
    public static @Nullable AnnotationValue getAnnotationValue(@Nonnull Element element, @Nonnull Class<? extends Annotation> annotationType) {
        return getAnnotationValue(element, annotationType, "value");
    }
    
    /**
     * Returns the value of the given annotation type on the given element or null if not found.
     */
    @Pure
    public static @Nullable String getStringValue(@Nonnull Element element, @Nonnull Class<? extends Annotation> annotationType) {
        final @Nullable AnnotationValue annotationValue = getAnnotationValue(element, annotationType);
        if (annotationValue != null) {
            final @Nonnull Object object = annotationValue.getValue();
            if (object instanceof String) {
                return (String) object;
            } else {
                ProcessingLog.error("The value is not a string:", SourcePosition.of(element, getAnnotationMirror(element, annotationType), annotationValue));
            }
        }
        return null;
    }
    
    /* -------------------------------------------------- Assignability -------------------------------------------------- */
    
    /**
     * Returns the given type or the return type if the given type is an executable type.
     */
    @Pure
    public static @Nonnull TypeMirror getType(@Nonnull TypeMirror type) {
        return type.getKind() == TypeKind.EXECUTABLE ? ((ExecutableType) type).getReturnType() : type;
    }
    
    /**
     * Returns the type of the given element or the return type if the given element is an executable element.
     */
    @Pure
    public static @Nonnull TypeMirror getType(@Nonnull Element element) {
        return getType(element.asType());
    }
    
    /**
     * Returns whether the given declared type is assignable to the given desired type.
     */
    @Pure
    public static boolean isAssignable(@Nonnull TypeMirror declaredType, @Nonnull Class<?> desiredType) {
        ProcessingLog.debugging("Checking whether $ is assignable from $", declaredType.toString(), desiredType.getCanonicalName());
        if (desiredType.isPrimitive()) {
            return declaredType.toString().equals(desiredType.toString());
        }
        // TODO: arrays cannot be fetched like this!
        if (desiredType.isArray()) {
            // TODO: find a way to check whether the declaredType is an array of a certain type.
            return true;
        }
        final @Nullable TypeElement desiredTypeElement = StaticProcessingEnvironment.getElementUtils().getTypeElement(desiredType.getCanonicalName());
        if (desiredTypeElement == null) { ProcessingLog.error("Could not retrieve the element for the type $.", desiredType); return false; }
        // TODO: Check whether this works with upper bounds of generic parameters and raw types.
        boolean result = StaticProcessingEnvironment.getTypeUtils().isAssignable(getType(declaredType), desiredTypeElement.asType());
        ProcessingLog.debugging("= $", result);
        return result;
    }
    
    /**
     * Returns whether the given element is assignable to the given type.
     */
    @Pure
    public static boolean isAssignable(@Nonnull Element element, @Nonnull Class<?> type) {
        return isAssignable(element.asType(), type);
    }
    
    /* -------------------------------------------------- Annotation Handlers -------------------------------------------------- */
    
    private static final @Nonnull @NonNullableElements Map<String, AnnotationHandler> cachedAnnotationHandlers = new HashMap<>();
    
    /**
     * Returns the annotation handlers of the given type which are found with the given meta-annotation type on the annotations of the given element.
     */
    @Pure
    @SuppressWarnings("unchecked")
    public static @Nonnull @NonNullableElements <G extends AnnotationHandler> Map<AnnotationMirror, G> getAnnotationHandlers(@Nonnull Element element, @Nonnull Class<? extends Annotation> metaAnnotationType, @Nonnull Class<G> annotationHandlerType) {
        final @Nonnull @NonNullableElements Map<AnnotationMirror, G> result = new LinkedHashMap<>();
        for (@Nonnull AnnotationMirror annotationMirror : StaticProcessingEnvironment.getElementUtils().getAllAnnotationMirrors(element)) {
            final @Nonnull String qualifiedAnnotationName = getQualifiedName(annotationMirror);
            final @Nullable AnnotationHandler cachedAnnotationHandler = cachedAnnotationHandlers.get(qualifiedAnnotationName);
            if (cachedAnnotationHandler != null) {
                if (annotationHandlerType.isInstance(cachedAnnotationHandler)) {
                    cachedAnnotationHandler.checkUsage(element, annotationMirror);
                    result.put(annotationMirror, (G) cachedAnnotationHandler);
                }
            } else {
                final @Nonnull TypeElement annotationElement = (TypeElement) annotationMirror.getAnnotationType().asElement();
                final @Nonnull String annotationName = "@" + annotationElement.getSimpleName();
                final @Nullable AnnotationValue metaAnnotationValue = getAnnotationValue(annotationElement, metaAnnotationType);
                if (metaAnnotationValue != null) {
                    final @Nonnull DeclaredType annotationHandlerImplementationType = (DeclaredType) metaAnnotationValue.getValue();
                    ProcessingLog.verbose("The declared annotation handler type is $.", annotationHandlerImplementationType);
                    final @Nonnull TypeElement annotationHandlerImplementationElement = (TypeElement) annotationHandlerImplementationType.asElement();
                    final @Nonnull String annotationHandlerImplementationBinaryName = StaticProcessingEnvironment.getElementUtils().getBinaryName(annotationHandlerImplementationElement).toString();
                    try {
                        ProcessingLog.debugging("Trying to retrieve class for name $", annotationHandlerImplementationBinaryName);
                        final @Nonnull Class<?> annotationHandlerImplementationClass = Class.forName(annotationHandlerImplementationBinaryName);
                        ProcessingLog.debugging("Annotation handler class: $", annotationHandlerImplementationClass);
                        if (annotationHandlerType.isAssignableFrom(annotationHandlerImplementationClass)) {
                            final @Nonnull G annotationHandler = (G) annotationHandlerImplementationClass.newInstance();
                            cachedAnnotationHandlers.put(qualifiedAnnotationName, annotationHandler);
                            annotationHandler.checkUsage(element, annotationMirror);
                            result.put(annotationMirror, annotationHandler);
                            ProcessingLog.debugging("Found the annotation handler $ for", SourcePosition.of(element), annotationName);
                        } else {
                            ProcessingLog.error("The annotation handler $ is not assignable to $:", SourcePosition.of(element), annotationName, annotationHandlerImplementationClass.getCanonicalName());
                        }
                    } catch (@Nonnull ClassNotFoundException | InstantiationException | IllegalAccessException exception) {
                        ProcessingLog.error("Could not instantiate the annotation handler $ for", SourcePosition.of(element), annotationHandlerImplementationBinaryName);
                        Log.error("Problem:", exception);
                    }
                }
            }
        }
        return Collections.unmodifiableMap(result);
    }
    
    /**
     * Returns the method validators mapped from their corresponding annotation mirror with which the given element is annotated.
     */
    @Pure
    public static @Nonnull @NonNullableElements Map<AnnotationMirror, MethodAnnotationValidator> getMethodValidators(@Nonnull Element element) {
        return getAnnotationHandlers(element, MethodValidator.class, MethodAnnotationValidator.class);
    }
    
    /**
     * Returns the value validators mapped from their corresponding annotation mirror with which the given element is annotated.
     */
    @Pure
    public static @Nonnull @NonNullableElements Map<AnnotationMirror, ValueAnnotationValidator> getValueValidators(@Nonnull Element element) {
        return getAnnotationHandlers(element, ValueValidator.class, ValueAnnotationValidator.class);
    }
    
    /**
     * Returns the type validators mapped from their corresponding annotation mirror with which the given type element is annotated.
     */
    @Pure
    public static @Nonnull @NonNullableElements Map<AnnotationMirror, TypeAnnotationValidator> getTypeValidators(@Nonnull TypeElement element) {
        return getAnnotationHandlers(element, TypeValidator.class, TypeAnnotationValidator.class);
    }
    
    /* -------------------------------------------------- Fields of Type -------------------------------------------------- */
    
    /**
     * Returns a list of all the fields with the given type in the given class.
     */
    @Pure
    public static @Capturable @Nonnull List<VariableElement> getFieldsOfType(@Nonnull TypeElement classElement, @Nonnull Class<?> fieldType) {
        final @Nonnull List<VariableElement> fields = new LinkedList<>();
        for (@Nonnull VariableElement field : ElementFilter.fieldsIn(classElement.getEnclosedElements())) {
            final @Nonnull TypeMirror declaredFieldType = field.asType();
            final @Nonnull String fieldTypeName;
            if (declaredFieldType instanceof  DeclaredType) {
                fieldTypeName = ((DeclaredType) declaredFieldType).asElement().toString();
            } else if (declaredFieldType instanceof PrimitiveType) {
                fieldTypeName = declaredFieldType.toString();
            } else {
                ProcessingLog.error("The type of the field $ is $", declaredFieldType, declaredFieldType.getClass());
                fieldTypeName = declaredFieldType.toString();
            }
            ProcessingLog.verbose("Found with the type $ the field", SourcePosition.of(field), fieldTypeName);
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
            ProcessingLog.warning("There is not exactly one field of type $ in the class", SourcePosition.of(classElement), fieldType.getCanonicalName());
        } else {
            final @Nonnull VariableElement field = fields.get(0);
            if (!fields.get(0).getModifiers().contains(Modifier.PUBLIC)) {
                ProcessingLog.warning("The field of type $ has to be public:", SourcePosition.of(field), fieldType.getCanonicalName());
            } else if (!fields.get(0).getModifiers().contains(Modifier.STATIC)) {
                ProcessingLog.warning("The field of type $ has to be static:", SourcePosition.of(field), fieldType.getCanonicalName());
            } else {
                return field;
            }
        }
        return null;
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    /**
     * Returns the method with the given name, return type, parameter types and no thrown types in the given type element or null if no such method is found.
     */
    @Pure
    public static @Nullable ExecutableElement getMethod(@Nonnull TypeElement typeElement, @Nonnull String methodName, @Nonnull Class<?> returnType, @Nonnull Class<?>... parameterTypes) {
        final @Nonnull DeclaredType surroundingType = (DeclaredType) typeElement.asType();
        for (@Nonnull ExecutableElement inheritedMethod : ElementFilter.methodsIn(StaticProcessingEnvironment.getElementUtils().getAllMembers(typeElement))) {
            final @Nonnull ExecutableElement method = (ExecutableElement) StaticProcessingEnvironment.getTypeUtils().asMemberOf(surroundingType, inheritedMethod);
            if (method.getSimpleName().contentEquals(methodName) && method.getThrownTypes().isEmpty()) {
                final @Nonnull ExecutableType methodType = (ExecutableType) method.asType();
                if (isAssignable(method.getReturnType(), returnType)) {
                    if (methodType.getParameterTypes().size() == parameterTypes.length) {
                        boolean isAssignable = true;
                        for (int i = 0; i < parameterTypes.length; i++) {
                            final @Nonnull TypeMirror parameterType = methodType.getParameterTypes().get(i);
                            if (parameterType.getKind() == TypeKind.TYPEVAR || !parameterType.toString().equals(parameterTypes[i].getCanonicalName())) { isAssignable = false; }
                        }
                        if (isAssignable) { return method; }
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * Returns whether the given type element contains a method with the given name, return type, parameter types and no thrown types.
     */
    @Pure
    public static boolean hasMethod(@Nonnull TypeElement typeElement, @Nonnull String methodName, @Nonnull Class<?> returnType, @Nonnull Class<?>... parameterTypes) {
        return getMethod(typeElement, methodName, returnType, parameterTypes) != null;
    }
    
}
