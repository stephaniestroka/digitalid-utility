package net.digitalid.utility.processing.utility;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
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
import javax.lang.model.util.SimpleTypeVisitor7;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.fixes.Brackets;
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
    public static @Nonnull String getQualifiedPackageName(@Nonnull Element element) {
        return StaticProcessingEnvironment.getElementUtils().getPackageOf(element).getQualifiedName().toString();
    }
    
    /**
     * Returns whether the given element is declared in the Java Runtime Environment (JRE).
     */
    @Pure
    public static boolean isDeclaredInRuntimeEnvironment(@Nonnull Element element) {
        // Matches both 'java.*' and 'javax.*' packages.
        return getQualifiedPackageName(element).startsWith("java");
    }
    
    /**
     * Returns whether the given element is declared in the Digital ID library (DID SDK).
     */
    @Pure
    public static boolean isDeclaredInDigitalIDLibrary(@Nonnull Element element) {
        return getQualifiedPackageName(element).startsWith("net.digitalid.");
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
    
    /* -------------------------------------------------- Member Elements -------------------------------------------------- */
    
    /**
     * Returns the elements in the given iterable which are of the given kind cast to the given type.
     */
    @Pure
    public static <E extends Element> @Nonnull FiniteIterable<@Nonnull E> filter(@Nonnull FiniteIterable<@Nonnull Element> elements, @Nonnull ElementKind kind, @Nonnull Class<E> type) {
        return elements.filter(element -> element.getKind() == kind).map(type::cast);
    }
    
    /**
     * Returns the directly declared members of the given type element.
     */
    @Pure
    public static @Nonnull FiniteIterable<@Nonnull Element> getMembers(@Nonnull TypeElement typeElement) {
        return FiniteIterable.of(typeElement.getEnclosedElements());
    }
    
    /**
     * Returns the directly declared fields of the given type element.
     */
    @Pure
    public static @Nonnull FiniteIterable<@Nonnull VariableElement> getFields(@Nonnull TypeElement typeElement) {
        return filter(getMembers(typeElement), ElementKind.FIELD, VariableElement.class);
    }
    
    /**
     * Returns the directly declared methods of the given type element.
     */
    @Pure
    public static @Nonnull FiniteIterable<@Nonnull ExecutableElement> getMethods(@Nonnull TypeElement typeElement) {
        return filter(getMembers(typeElement), ElementKind.METHOD, ExecutableElement.class);
    }
    
    /**
     * Returns the directly declared constructors of the given type element.
     */
    @Pure
    public static @Nonnull FiniteIterable<@Nonnull ExecutableElement> getConstructors(@Nonnull TypeElement typeElement) {
        return filter(getMembers(typeElement), ElementKind.CONSTRUCTOR, ExecutableElement.class);
    }
    
    /**
     * Returns the directly declared and inherited members of the given type element.
     */
    @Pure
    public static @Nonnull FiniteIterable<@Nonnull Element> getAllMembers(@Nonnull TypeElement typeElement) {
        return FiniteIterable.of(StaticProcessingEnvironment.getElementUtils().getAllMembers(typeElement));
    }
    
    /**
     * Returns the directly declared and inherited fields of the given type element.
     */
    @Pure
    public static @Nonnull FiniteIterable<@Nonnull VariableElement> getAllFields(@Nonnull TypeElement typeElement) {
        return filter(getAllMembers(typeElement), ElementKind.FIELD, VariableElement.class);
    }
    
    /**
     * Returns the directly declared and inherited methods of the given type element.
     */
    @Pure
    public static @Nonnull FiniteIterable<@Nonnull ExecutableElement> getAllMethods(@Nonnull TypeElement typeElement) {
        return filter(getAllMembers(typeElement), ElementKind.METHOD, ExecutableElement.class);
    }
    
    /* -------------------------------------------------- Executable Type -------------------------------------------------- */
    
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
    
    /* -------------------------------------------------- Annotation Mirrors -------------------------------------------------- */
    
    /**
     * Returns the simple name of the given annotation mirror with a leading at symbol.
     */
    @Pure
    public static @Nonnull String getSimpleName(@Nonnull AnnotationMirror annotationMirror) {
        return "@" + annotationMirror.getAnnotationType().asElement().getSimpleName();
    }
    
    /**
     * Returns the qualified name of the annotation type of the given annotation mirror.
     */
    @Pure
    public static @Nonnull String getQualifiedName(@Nonnull AnnotationMirror annotationMirror) {
        return ((QualifiedNameable) annotationMirror.getAnnotationType().asElement()).getQualifiedName().toString();
    }
    
    /**
     * Returns the annotation mirrors that are present on the given element or its (return) (component) type.
     */
    @Pure
    public static @Nonnull FiniteIterable<@Nonnull AnnotationMirror> getAnnotationMirrors(@Nonnull Element element) {
        final @Nonnull List<@Nonnull AnnotationMirror> annotationMirrors = new LinkedList<>();
        annotationMirrors.addAll(element.getAnnotationMirrors());
        final @Nonnull TypeMirror typeMirror = getType(element);
        annotationMirrors.addAll(typeMirror.getAnnotationMirrors());
        if (typeMirror.getKind() == TypeKind.ARRAY) {
            annotationMirrors.addAll(((ArrayType) typeMirror).getComponentType().getAnnotationMirrors());
        }
        return FiniteIterable.of(annotationMirrors);
    }
    
    /**
     * Returns the annotation mirror corresponding to the given annotation type of the given element or null if not found.
     */
    @Pure
    public static @Nullable AnnotationMirror getAnnotationMirror(@Nonnull Element element, @Nonnull Class<? extends Annotation> annotationType) {
        return getAnnotationMirrors(element).findFirst(annotationMirror -> getQualifiedName(annotationMirror).equals(annotationType.getCanonicalName()));
    }
    
    /**
     * Returns whether the given element has an annotation of the given type.
     */
    @Pure
    public static boolean hasAnnotation(@Nonnull Element element, @Nonnull Class<? extends Annotation> annotationType) {
        return getAnnotationMirror(element, annotationType) != null;
    }
    
    /* -------------------------------------------------- Annotation Values -------------------------------------------------- */
    
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
    
    /* -------------------------------------------------- Value Conversions -------------------------------------------------- */
    
    /**
     * Returns the given annotation value as a string or propagates null.
     */
    @Pure
    public static @Nullable String getString(@Nullable AnnotationValue annotationValue) {
        if (annotationValue != null) {
            final @Nonnull Object object = annotationValue.getValue();
            if (object instanceof String) {
                return (String) object;
            } else {
                ProcessingLog.error("The annotation value is not a string: $.", object);
            }
        }
        return null;
    }
    
    /**
     * Returns the given type mirror as a class object.
     */
    @Pure
    public static @Nullable Class<?> getClass(@Nonnull TypeMirror typeMirror) {
        switch (typeMirror.getKind()) {
            case DECLARED:
                final @Nonnull TypeElement typeElement = (TypeElement) ((DeclaredType) typeMirror).asElement();
                final @Nonnull String binaryName = StaticProcessingEnvironment.getElementUtils().getBinaryName(typeElement).toString();
                try {
                    return Class.forName(binaryName);
                } catch (@Nonnull ClassNotFoundException exception) {
                    ProcessingLog.error("Could not find the class $.", binaryName);
                    return null;
                }
            case ARRAY:
                final @Nonnull Class<?> componentType  = getClass(((ArrayType) typeMirror).getComponentType());
                return Array.newInstance(componentType, 0).getClass();
            case BOOLEAN: return boolean.class;
            case CHAR: return char.class;
            case BYTE: return byte.class;
            case SHORT: return short.class;
            case INT: return int.class;
            case LONG: return long.class;
            case FLOAT: return float.class;
            case DOUBLE: return double.class;
            default:
                ProcessingLog.error("The type mirror represents neither a primitive, a declared nor an array type: $.", typeMirror);
                return null;
        }
    }
    
    /**
     * Returns the given annotation value as a class object or propagates null.
     */
    @Pure
    public static @Nullable Class<?> getClass(@Nullable AnnotationValue annotationValue) {
        if (annotationValue != null) {
            final @Nonnull Object object = annotationValue.getValue();
            if (object instanceof TypeMirror) {
                return getClass((TypeMirror) object);
            } else {
                ProcessingLog.error("The annotation value is not a class: $.", object);
            }
        }
        return null;
    }
    
    /**
     * Returns the given annotation value as a new instance of the given type or propagates null.
     */
    @Pure
    @SuppressWarnings("unchecked")
    public static <T> @Nullable T getInstance(@Nullable AnnotationValue annotationValue, @Nonnull Class<T> desiredType) {
        final @Nullable Class<?> declaredType = getClass(annotationValue);
        if (declaredType != null) {
            try {
                final @Nonnull Object object = declaredType.newInstance();
                if (desiredType.isInstance(object)) {
                    return (T) object;
                } else {
                    ProcessingLog.error("$ is not an instance of $.", object, desiredType.getCanonicalName());
                }
            } catch (@Nonnull InstantiationException | IllegalAccessException exception) {
                ProcessingLog.error("Could not instantiate the class $.", declaredType.getCanonicalName());
            }
        }
        return null;
    }
    
    /* -------------------------------------------------- Annotation Strings -------------------------------------------------- */
    
    /**
     * Returns the given annotation value as a string.
     */
    @Pure
    private static @Nonnull String getAnnotationValueAsString(@Nonnull AnnotationValue annotationValue, @NonCaptured @Modified @Nonnull TypeImporter typeImporter) {
        final @Nonnull Object object = annotationValue.getValue();
        if (object instanceof TypeMirror) {
            return typeImporter.importIfPossible((TypeMirror) object) + ".class";
        } else {
            return annotationValue.toString();
        }
    }
    
    /**
     * Returns the given annotation entry as a string.
     */
    @Pure
    private static @Nonnull String getAnnotationEntryAsString(@NonCaptured @Unmodified Map.@Nonnull Entry<@Nonnull ? extends ExecutableElement, @Nonnull ? extends AnnotationValue> annotationEntry, @NonCaptured @Modified @Nonnull TypeImporter typeImporter) {
        final @Nonnull String methodName = annotationEntry.getKey().getSimpleName().toString();
        return (methodName.equals("value") ? "" : methodName + " = ") + getAnnotationValueAsString(annotationEntry.getValue(), typeImporter);
    }
    
    /**
     * Returns the given annotation with its values as a string.
     */
    @Pure
    public static @Nonnull String getAnnotationAsString(@Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull TypeImporter typeImporter) {
        return "@" + typeImporter.importIfPossible(annotationMirror.getAnnotationType()) + FiniteIterable.of(annotationMirror.getElementValues().entrySet()).map(annotationEntry -> getAnnotationEntryAsString(annotationEntry, typeImporter)).join(Brackets.ROUND, "");
    }
    
    /**
     * Returns the given annotations with their values as a string.
     */
    @Pure
    public static @Nonnull String getAnnotationsAsString(@Nonnull FiniteIterable<@Nonnull ? extends AnnotationMirror> annotationMirrors, @NonCaptured @Modified @Nonnull TypeImporter typeImporter) {
        return annotationMirrors.map(annotationMirror -> getAnnotationAsString(annotationMirror, typeImporter)).join("", " ", "", " ");
    }
    
    /* -------------------------------------------------- Assignability -------------------------------------------------- */
    
    /**
     * 
     */
    @Pure
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
     * Returns whether the given declared type is assignable to the given desired type.
     */
    @Pure
    public static boolean isAssignable(@Nonnull TypeMirror declaredType, @Nonnull Class<?> desiredType) {
        ProcessingLog.verbose("Checking whether $ is assignable to $.", declaredType, desiredType.getCanonicalName());
        
        if (desiredType.isArray()) {
            // TODO: find a way to check whether the declaredType is an array of a certain type.
            return true;
        }
        
        if (desiredType.isPrimitive()) {
            if (!declaredType.getKind().isPrimitive()) {
                return false;
            }
            final @Nonnull String declaredPrimitiveType;
            if (declaredType instanceof Type.AnnotatedType) {
                final Type.@Nonnull AnnotatedType annotatedType = (Type.AnnotatedType) declaredType;
                declaredPrimitiveType = annotatedType.unannotatedType().toString();
            } else {
                declaredPrimitiveType = declaredType.toString();
            }
            return declaredPrimitiveType.equals(desiredType.toString());
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
    
    /**
     * Returns whether the given element is assignable to the given type.
     */
    @Pure
    public static boolean isAssignable(@Nonnull Element element, @Nonnull Class<?> type) {
        return isAssignable(getType(element), type);
    }
    
    /* -------------------------------------------------- Component Type -------------------------------------------------- */
    
    // TODO: Review and document the following methods.
    
    @Pure
    public static boolean isArray(@Nonnull TypeMirror type) {
        return (type instanceof Type.ArrayType);
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
    public static boolean isCollection(@Nonnull TypeMirror type) {
        return (type instanceof DeclaredType && isAssignable(type, Collection.class));
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
    
    /* -------------------------------------------------- Type Visitors -------------------------------------------------- */
    
    /**
     * This type visitor returns the qualified name of the given type.
     */
    @Stateless
    public static class QualifiedNameTypeVisitor extends SimpleTypeVisitor7<@Nonnull String, @Nullable Void> {
        
        protected QualifiedNameTypeVisitor() {}
        
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
    
    private static final @Nonnull QualifiedNameTypeVisitor QUALIFIED_NAME_TYPE_VISITOR = new QualifiedNameTypeVisitor();
    
    /**
     * Returns the qualified name of the given type mirror.
     */
    @Pure
    public static @Nonnull String getQualifiedName(@Nonnull TypeMirror typeMirror) {
        return QUALIFIED_NAME_TYPE_VISITOR.visit(typeMirror);
    }
    
    /**
     * This type visitor returns the simple name of the given type.
     */
    @Stateless
    public static class SimpleNameTypeVisitor extends QualifiedNameTypeVisitor {
        
        protected SimpleNameTypeVisitor() {}
        
        @Pure
        @Override
        public @Nonnull String visitDeclared(@Nonnull DeclaredType type, @Nullable Void none) {
            return type.asElement().getSimpleName().toString();
        }
        
    }
    
    private static final @Nonnull SimpleNameTypeVisitor SIMPLE_NAME_TYPE_VISITOR = new SimpleNameTypeVisitor();
    
    /**
     * Returns the simple name of the given type mirror.
     */
    @Pure
    public static @Nonnull String getSimpleName(@Nonnull TypeMirror typeMirror) {
        return SIMPLE_NAME_TYPE_VISITOR.visit(typeMirror);
    }
    
    /* -------------------------------------------------- Fields of Type -------------------------------------------------- */
    
    /**
     * Returns a list of all the fields with the given type in the given type element.
     */
    @Pure
    public static @Nonnull FiniteIterable<@Nonnull VariableElement> getFieldsOfType(@Nonnull TypeElement typeElement, @Nonnull Class<?> fieldType) {
        return getFields(typeElement).filter(field -> getQualifiedName(field.asType()).equals(fieldType.getCanonicalName()));
    }
    
    /**
     * Returns the unique, public and static field with the given type in the given type element.
     * If not exactly one field fulfilling all these criteria is found, this method logs a warning message and returns null. 
     */
    @Pure
    public static @Nullable VariableElement getUniquePublicStaticFieldOfType(@Nonnull TypeElement typeElement, @Nonnull Class<?> fieldType) {
        final @Nonnull FiniteIterable<@Nonnull VariableElement> fields = getFieldsOfType(typeElement, fieldType);
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
            ProcessingLog.warning("There is not exactly one field of type $ in the class", SourcePosition.of(typeElement), fieldType.getCanonicalName());
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
        for (@Nonnull ExecutableElement inheritedMethod : getAllMethods(typeElement)) {
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
    
    /* -------------------------------------------------- Default Constructor -------------------------------------------------- */
    
    /**
     * Returns whether the given type element has a public default constructor.
     */
    @Pure
    public static boolean hasPublicDefaultConstructor(@Nonnull TypeElement typeElement) {
        return getConstructors(typeElement).matchAny(constructor -> constructor.getParameters().isEmpty() && constructor.getModifiers().contains(Modifier.PUBLIC));
    }
    
}
