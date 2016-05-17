package net.digitalid.utility.processing.utility;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

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
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.SimpleTypeVisitor7;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.functional.interfaces.UnaryFunction;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.annotations.type.Utility;

import com.sun.tools.javac.code.Type;

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
        // Matches both 'java.*' and 'javax.*' packages.
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
        for (Map.@Nonnull Entry<? extends ExecutableElement, ? extends AnnotationValue> annotationEntry : StaticProcessingEnvironment.getElementUtils().getElementValuesWithDefaults(annotationMirror).entrySet()) {
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
    
    @Pure
    public static @Nonnull String getAnnotationsAsString(@Nonnull UnaryFunction<AnnotationMirror, String> function, @Nonnull Collection<? extends AnnotationMirror> annotationMirrors) {
        final @Nonnull StringBuilder annotationsAsString = new StringBuilder();
        for (@Nonnull AnnotationMirror annotationMirror : annotationMirrors) {
            annotationsAsString.append("@").append(function.evaluate(annotationMirror));
            if (annotationMirror.getElementValues().size() > 0) {
                annotationsAsString.append("(");
                boolean first = true;
                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> elementValue : annotationMirror.getElementValues().entrySet()) {
                    if (first) { first = false; } else { annotationsAsString.append(", "); }
                    final @Nonnull String nameOfKey = elementValue.getKey().getSimpleName().toString();
                    if (!nameOfKey.equals("value")) {
                        annotationsAsString.append(nameOfKey).append(" = ").append(elementValue.getValue());
                    } else {
                        annotationsAsString.append(elementValue.getValue());
                    }
                }
                annotationsAsString.append(")");
            }
            annotationsAsString.append(" ");
        }
        return annotationsAsString.toString();
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
            if (!declaredType.getKind().isPrimitive()) {
                return false;
            }
            final @Nonnull String declaredPrimitiveType;
            if (declaredType instanceof Type.AnnotatedType) {
                Type.AnnotatedType annotatedType = (Type.AnnotatedType) declaredType;
                declaredPrimitiveType = annotatedType.unannotatedType().toString();
            } else {
                declaredPrimitiveType = declaredType.toString();
            }
            return declaredPrimitiveType.equals(desiredType.toString());
        }
        // TODO: arrays cannot be fetched like this!
        if (desiredType.isArray()) {
            // TODO: find a way to check whether the declaredType is an array of a certain type.
            return true;
        }
        final @Nullable TypeElement desiredTypeElement = StaticProcessingEnvironment.getElementUtils().getTypeElement(desiredType.getCanonicalName());
        if (desiredTypeElement == null) { ProcessingLog.error("Could not retrieve the element for the type $.", desiredType); return false; }
        
        boolean result = StaticProcessingEnvironment.getTypeUtils().isAssignable(getType(StaticProcessingEnvironment.getTypeUtils().erasure(getType(declaredType))), desiredTypeElement.asType());
        if (!result) {
            ProcessingLog.debugging("The given type $ is not assignable to the desired type $. Checking super types (with type erasure).");
            result = checkTypeErasedSuperTypes(getType(declaredType), desiredTypeElement.asType());
        }
        ProcessingLog.debugging("= $", result);
        return result;
    }
    
    private static boolean checkTypeErasedSuperTypes(@Nonnull TypeMirror declaredType, @Nonnull TypeMirror desiredType) {
        final Queue<TypeMirror> typeMirrors = new LinkedList<>(StaticProcessingEnvironment.getTypeUtils().directSupertypes(getType(declaredType)));
        while (!typeMirrors.isEmpty()) {
            final TypeMirror superType = typeMirrors.poll();
            boolean result = StaticProcessingEnvironment.getTypeUtils().isAssignable(getType(StaticProcessingEnvironment.getTypeUtils().erasure(superType)), desiredType);
            if (result) {
                return true;
            }
            typeMirrors.addAll(StaticProcessingEnvironment.getTypeUtils().directSupertypes(getType(superType)));
        }
        return false;
    }
    
    /**
     * Returns whether the given element is assignable to the given type.
     */
    @Pure
    public static boolean isAssignable(@Nonnull Element element, @Nonnull Class<?> type) {
        if (element instanceof ExecutableElement) {
            return isAssignable(((ExecutableElement) element).getReturnType(), type);
        } else {
            return isAssignable(element.asType(), type);
        }
    }
    
    @Pure
    public static boolean isArray(@Nonnull Element element) {
        if (element instanceof ExecutableElement) {
            return isArray(((ExecutableElement) element).getReturnType());
        } else {
            return isArray(element.asType());
        }
    }
    
    @Pure
    public static boolean isArray(@Nonnull TypeMirror type) {
        return (type instanceof Type.ArrayType);
    }
    
    @Pure
    public static boolean isCollection(@Nonnull TypeMirror type) {
        return (type instanceof DeclaredType && ProcessingUtility.isAssignable(type, Collection.class));
    }
    
    @Pure
    public static @Nonnull TypeMirror getComponentType(@Nonnull TypeMirror type) {
        Require.that(isArray(type) || isCollection(type)).orThrow("Expected array or collection type");
    
        if (isArray(type)) {
            final Type.@Nonnull ArrayType arrayType = (Type.ArrayType) type;
            @Nonnull TypeMirror componentType = arrayType.getComponentType();
            if (componentType instanceof Type.AnnotatedType) {
                componentType = ((Type.AnnotatedType) componentType).unannotatedType();
            }
            return componentType;
        } else {
            final @Nonnull List<@Nonnull ? extends TypeMirror> typeArguments = ((DeclaredType) type).getTypeArguments();
            Require.that(typeArguments.size() == 1).orThrow("Expected collection type with exactly one type argument");
            return typeArguments.get(0);
        }
    }
    
    /* -------------------------------------------------- Type Visitor -------------------------------------------------- */
    
    /**
     * This type visitor returns the qualified name of the given type.
     */
    @Stateless
    public static class TypeVisitor extends SimpleTypeVisitor7<@Nonnull String, @Nullable Void> {
        
        protected TypeVisitor() {}
        
        @Pure
        @Override
        protected @Nonnull String defaultAction(@Nonnull TypeMirror type, @Nullable Void none) {
            return type.toString();
        }
        
        @Pure
        @Override
        public @Nonnull String visitArray(@Nonnull ArrayType type, @Nullable Void none) {
            return visit(type.getComponentType()) + "[]";
        }
        
        @Pure
        @Override
        public @Nonnull String visitDeclared(@Nonnull DeclaredType type, @Nullable Void none) {
            return ((QualifiedNameable) type.asElement()).getQualifiedName().toString();
        }
        
        @Pure
        @Override
        public @Nonnull String visitWildcard(@Nonnull WildcardType type, @Nullable Void none) {
            return "Object";
        }
        
    }
    
    private static final @Nonnull TypeVisitor TYPE_VISITOR = new TypeVisitor();
    
    /**
     * Returns the simple name of the given type mirror.
     */
    @Pure
    public static @Nonnull String getSimpleName(@Nonnull TypeMirror typeMirror) {
        final @Nonnull String qualifiedName = getQualifiedName(typeMirror);
        int indexOfPoint = qualifiedName.lastIndexOf(".");
        if (indexOfPoint >= 0) {
            return qualifiedName.substring(indexOfPoint + 1);
        } else {
            return qualifiedName;
        }
    }
    
    /**
     * Returns the qualified name of the given type mirror.
     */
    @Pure
    public static @Nonnull String getQualifiedName(@Nonnull TypeMirror typeMirror) {
        return TYPE_VISITOR.visit(typeMirror);
    }
    
    /* -------------------------------------------------- Fields of Type -------------------------------------------------- */
    
    /**
     * Returns a list of all the fields with the given type in the given class.
     */
    @Pure
    public static @Nonnull FiniteIterable<@Nonnull VariableElement> getFieldsOfType(@Nonnull TypeElement classElement, @Nonnull Class<?> fieldType) {
        final @Nonnull List<@Nonnull VariableElement> fields = new LinkedList<>();
        for (@Nonnull VariableElement field : ElementFilter.fieldsIn(classElement.getEnclosedElements())) {
            final @Nonnull String fieldTypeName = getQualifiedName(field.asType());
            ProcessingLog.verbose("Found with the type $ the field", SourcePosition.of(field), fieldTypeName);
            if (fieldTypeName.equals(fieldType.getCanonicalName())) { fields.add(field); }
        }
        return FiniteIterable.of(fields);
    }
    
    /**
     * Returns the unique, public and static field with the given type in the given class.
     * If no field fulfilling all these criteria is found, this method logs a warning message and returns null. 
     */
    @Pure
    public static @Nullable VariableElement getUniquePublicStaticFieldOfType(@Nonnull TypeElement classElement, @Nonnull Class<?> fieldType) {
        final @Nonnull FiniteIterable<@Nonnull VariableElement> fields = getFieldsOfType(classElement, fieldType);
        if (fields.isSingle()) {
            final @Nonnull VariableElement field = fields.getFirst();
            if (!field.getModifiers().contains(Modifier.PUBLIC)) {
                ProcessingLog.warning("The field of type $ has to be public:", SourcePosition.of(field), fieldType.getCanonicalName());
            } else if (!field.getModifiers().contains(Modifier.STATIC)) {
                ProcessingLog.warning("The field of type $ has to be static:", SourcePosition.of(field), fieldType.getCanonicalName());
            } else {
                return field;
            }
        } else {
            ProcessingLog.warning("There is not exactly one field of type $ in the class", SourcePosition.of(classElement), fieldType.getCanonicalName());
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
            final @Nonnull ExecutableType methodType = (ExecutableType) StaticProcessingEnvironment.getTypeUtils().asMemberOf(surroundingType, inheritedMethod);
            if (inheritedMethod.getSimpleName().contentEquals(methodName) && inheritedMethod.getThrownTypes().isEmpty()) {
                if (isAssignable(inheritedMethod.getReturnType(), returnType)) {
                    if (methodType.getParameterTypes().size() == parameterTypes.length) {
                        boolean isAssignable = true;
                        for (int i = 0; i < parameterTypes.length; i++) {
                            final @Nonnull TypeMirror parameterType = methodType.getParameterTypes().get(i);
                            if (parameterType.getKind() == TypeKind.TYPEVAR || !parameterType.toString().equals(parameterTypes[i].getCanonicalName())) { isAssignable = false; }
                        }
                        if (isAssignable) { return inheritedMethod; }
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
