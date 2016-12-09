package net.digitalid.utility.processing.utility;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.HashMap;
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
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.AbstractAnnotationValueVisitor8;
import javax.lang.model.util.SimpleTypeVisitor8;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.circumfixes.Brackets;
import net.digitalid.utility.circumfixes.Quotes;
import net.digitalid.utility.collaboration.annotations.TODO;
import net.digitalid.utility.collaboration.enumerations.Author;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.immutable.ImmutableMap;
import net.digitalid.utility.processing.annotations.LogsErrorWhenReturningNull;
import net.digitalid.utility.processing.logging.ErrorLogger;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.annotations.type.Utility;
import net.digitalid.utility.validation.annotations.type.kind.EnumType;

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
    @TODO(task = "Change the prefix back to 'net.digitalid.'.", date = "2016-11-08", author = Author.KASPAR_ETTER)
    public static boolean isDeclaredInDigitalIDLibrary(@Nonnull Element element) {
        return getQualifiedPackageName(element).startsWith("net.digitalid.utility.") || 
                getQualifiedPackageName(element).startsWith("net.digitalid.database.") ||
                getQualifiedPackageName(element).startsWith("net.digitalid.core.");
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
     * Returns the simple name of the given annotation mirror.
     */
    @Pure
    public static @Nonnull String getSimpleName(@Nonnull AnnotationMirror annotationMirror) {
        return annotationMirror.getAnnotationType().asElement().getSimpleName().toString();
    }
    
    /**
     * Returns the simple name of the given annotation mirror with a leading at symbol.
     */
    @Pure
    public static @Nonnull String getSimpleNameWithLeadingAt(@Nonnull AnnotationMirror annotationMirror) {
        return "@" + getSimpleName(annotationMirror);
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
     * Returns the annotation values mapped from their name for all methods of the given annotation mirror.
     */
    @Pure
    public static @Nonnull Map<@Nonnull String, @Nonnull AnnotationValue> getAnnotationValues(@Nonnull AnnotationMirror annotationMirror) {
        final @Nonnull Map<@Nonnull String, @Nonnull AnnotationValue> result = new HashMap<>();
        for (Map.@Nonnull Entry<@Nonnull ? extends ExecutableElement, @Nonnull ? extends AnnotationValue> annotationEntry : StaticProcessingEnvironment.getElementUtils().getElementValuesWithDefaults(annotationMirror).entrySet()) {
            result.put(annotationEntry.getKey().getSimpleName().toString(), annotationEntry.getValue());
        }
        return result;
    }
    
    /**
     * Returns the annotation value for the given method name of the given annotation mirror or null if not found.
     */
    @Pure
    @LogsErrorWhenReturningNull
    public static @Nullable AnnotationValue getAnnotationValue(@Nonnull AnnotationMirror annotationMirror, @Nonnull String methodName) {
        final @Nullable AnnotationValue result = getAnnotationValues(annotationMirror).get(methodName);
        if (result == null) { ProcessingLog.error("Found no value $ for the annotation $.", methodName, "@" + annotationMirror.getAnnotationType().asElement().getSimpleName()); }
        return result;
    }
    
    /**
     * Returns the annotation value for the value method of the given annotation mirror or null if not found.
     */
    @Pure
    @LogsErrorWhenReturningNull
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
                ProcessingLog.error("The annotation value $ is not a string.", object);
            }
        }
        return null;
    }
    
    /**
     * Returns the given annotation value as a constant of the given enum type or propagates null.
     */
    @Pure
    public static <T extends Enum<T>> @Nullable T getEnum(@Nullable AnnotationValue annotationValue, @Nonnull @EnumType Class<T> type) {
        if (annotationValue != null) {
            final @Nonnull Object object = annotationValue.getValue();
            if (object instanceof VariableElement) {
                final @Nonnull String name = ((VariableElement) object).getSimpleName().toString();
                try {
                    return Enum.valueOf(type, name);
                } catch (@Nonnull IllegalArgumentException exception) {
                    ProcessingLog.error("The enum type $ has no constant with the name $.", type.getCanonicalName(), name);
                }
            } else {
                ProcessingLog.error("The annotation value is not an enum constant: $.", object);
            }
        }
        return null;
    }
    
    /**
     * Returns the given annotation value as constants of the given enum type or returns an empty iterable if the given annotation value is null.
     */
    @Pure
    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> @Nonnull FiniteIterable<@Nonnull T> getEnums(@Nullable AnnotationValue annotationValue, @Nonnull @EnumType Class<T> type) {
        if (annotationValue != null) {
            final @Nonnull Object object = annotationValue.getValue();
            if (object instanceof List<?>) {
                final @Nonnull List<@Nonnull ? extends AnnotationValue> list = (List<? extends AnnotationValue>) object;
                return FiniteIterable.of(list).map(value -> getEnum(value, type)).filterNulls();
            } else {
                ProcessingLog.error("The annotation value is not a list: $.", object);
            }
        }
        return FiniteIterable.of();
    }
    
    /**
     * Returns the given type mirror as a class object.
     */
    @Pure
    @LogsErrorWhenReturningNull
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
                final @Nullable Class<?> componentType = getClass(((ArrayType) typeMirror).getComponentType());
                return componentType != null ? Array.newInstance(componentType, 0).getClass() : null;
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
     * Returns the given type mirror or its component type in case of arrays as a type element.
     */
    @Pure
    @LogsErrorWhenReturningNull
    public static @Nullable TypeElement getTypeElement(@Nonnull TypeMirror typeMirror) {
        switch (typeMirror.getKind()) {
            case DECLARED:
                final @Nonnull TypeElement typeElement = (TypeElement) ((DeclaredType) typeMirror).asElement();
                return typeElement;
            case ARRAY:
                return getTypeElement(((ArrayType) typeMirror).getComponentType());
            default:
                ProcessingLog.error("The type mirror of kind $ cannot be converted to a type element.", typeMirror.getKind());
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
    
    /**
     * Returns whether the given type mirror or its component type in case of arrays is primitive.
     */
    @Pure
    public static boolean isPrimitive(@Nonnull TypeMirror typeMirror) {
        switch(typeMirror.getKind()) {
            case BOOLEAN:
            case BYTE:
            case DOUBLE:
            case FLOAT:
            case INT:
            case LONG:
            case SHORT:
                return true;
            case ARRAY:
                return isPrimitive(((ArrayType) typeMirror).getComponentType());
            default:
                return false;
        }
    }
    
    /**
     * Returns the boxed type of the given type mirror or its component type in case of arrays.
     */
    @Pure
    public static @Nonnull TypeMirror getBoxedType(@Nonnull TypeMirror typeMirror) {
        if (isPrimitive(typeMirror)) {
            return ProcessingUtility.getType(StaticProcessingEnvironment.getTypeUtils().boxedClass((PrimitiveType) typeMirror));
        } else {
            return typeMirror;
        }
    }
    
    /* -------------------------------------------------- Annotation Strings -------------------------------------------------- */
    
    /**
     * <em>Important:</em> Do not call {@link AnnotationValueVisitor#visit(javax.lang.model.element.AnnotationValue)} as this passes null as the visitor-specified parameter instead of a type importer.
     */
    @Stateless
    public static class AnnotationValueVisitor extends AbstractAnnotationValueVisitor8<@Nonnull String, @Nonnull TypeImporter> {
        
        protected AnnotationValueVisitor() {}
        
        @Pure
        @Override
        public @Nonnull String visitBoolean(boolean value, @NonCaptured @Modified @Nullable TypeImporter typeImporter) {
            return String.valueOf(value);
        }
        
        @Pure
        @Override
        public @Nonnull String visitByte(byte value, @NonCaptured @Modified @Nullable TypeImporter typeImporter) {
            return String.valueOf(value);
        }
        
        @Pure
        @Override
        public @Nonnull String visitChar(char value, @NonCaptured @Modified @Nullable TypeImporter typeImporter) {
            return Quotes.inSingle(String.valueOf(value));
        }
        
        @Pure
        @Override
        public @Nonnull String visitDouble(double value, @NonCaptured @Modified @Nullable TypeImporter typeImporter) {
            return String.valueOf(value) + "d";
        }
        
        @Pure
        @Override
        public @Nonnull String visitFloat(float value, @NonCaptured @Modified @Nullable TypeImporter typeImporter) {
            return String.valueOf(value) + "f";
        }
        
        @Pure
        @Override
        public @Nonnull String visitInt(int value, @NonCaptured @Modified @Nullable TypeImporter typeImporter) {
            return String.valueOf(value);
        }
        
        @Pure
        @Override
        public @Nonnull String visitLong(long value, @NonCaptured @Modified @Nullable TypeImporter typeImporter) {
            return String.valueOf(value) + "l";
        }
        
        @Pure
        @Override
        public @Nonnull String visitShort(short value, @NonCaptured @Modified @Nullable TypeImporter typeImporter) {
            return String.valueOf(value);
        }
        
        @Pure
        @Override 
        public @Nonnull String visitString(@Nonnull String string, @NonCaptured @Modified @Nullable TypeImporter typeImporter) {
            return Quotes.inDouble(string);
        }
        
        @Pure
        @Override
        public @Nonnull String visitType(@Nonnull TypeMirror type, @NonCaptured @Modified @Nullable TypeImporter typeImporter) {
            final @Nonnull String qualifiedTypeName = ProcessingUtility.getQualifiedName(type);
            return (typeImporter != null ? typeImporter.importIfPossible(qualifiedTypeName) : qualifiedTypeName) + ".class";
        }
        
        @Pure
        @Override
        public @Nonnull String visitEnumConstant(@Nonnull VariableElement constant, @NonCaptured @Modified @Nullable TypeImporter typeImporter) {
            final @Nonnull String qualifiedMemberName = ProcessingUtility.getQualifiedName(constant.asType()) + "." + constant.getSimpleName();
            return typeImporter != null ? typeImporter.importStaticallyIfPossible(qualifiedMemberName) : qualifiedMemberName;
        }
        
        @Pure
        @Override
        public @Nonnull String visitAnnotation(@Nonnull AnnotationMirror annotation, @NonCaptured @Modified @Nullable TypeImporter typeImporter) {
            return ProcessingUtility.getAnnotationAsString(annotation, typeImporter); // TODO: This call leads to a NullPointerException if the given type importer is null.
        }
        
        @Pure
        @Override
        public @Nonnull String visitArray(@Nonnull List<@Nonnull ? extends AnnotationValue> list, @NonCaptured @Modified @Nullable TypeImporter typeImporter) {
            return FiniteIterable.of(list).map(annotationValue -> visit(annotationValue, typeImporter)).join(Brackets.CURLY);
        }
        
    }
    
    private static final @Nonnull AnnotationValueVisitor ANNOTATION_VALUE_VISITOR = new AnnotationValueVisitor();
    
    /**
     * Returns the given annotation value as a string.
     */
    @Pure
    public static @Nonnull String getAnnotationValueAsString(@Nonnull AnnotationValue annotationValue, @NonCaptured @Modified @Nonnull TypeImporter typeImporter) {
        return ANNOTATION_VALUE_VISITOR.visit(annotationValue, typeImporter);
    }
    
    /**
     * Returns the given annotation entry as a string.
     */
    @Pure
    private static @Nonnull String getAnnotationEntryAsString(@NonCaptured @Unmodified Map.@Nonnull Entry<@Nonnull ? extends ExecutableElement, @Nonnull ? extends AnnotationValue> annotationEntry, @NonCaptured @Modified @Nonnull TypeImporter typeImporter, boolean isUniqueEntry) {
        final @Nonnull String methodName = annotationEntry.getKey().getSimpleName().toString();
        return (isUniqueEntry && methodName.equals("value") ? "" : methodName + " = ") + getAnnotationValueAsString(annotationEntry.getValue(), typeImporter);
    }
    
    /**
     * Returns the given annotation with its values as a string.
     */
    @Pure
    public static @Nonnull String getAnnotationAsString(@Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull TypeImporter typeImporter) {
        final boolean isUniqueEntry = annotationMirror.getElementValues().size() == 1;
        return "@" + typeImporter.importIfPossible(annotationMirror.getAnnotationType()) + FiniteIterable.of(annotationMirror.getElementValues().entrySet()).map(annotationEntry -> getAnnotationEntryAsString(annotationEntry, typeImporter, isUniqueEntry)).join(Brackets.ROUND, "");
    }
    
    /**
     * Returns the given annotations with their values as a string.
     */
    @Pure
    public static @Nonnull String getAnnotationsAsString(@Nonnull FiniteIterable<@Nonnull ? extends AnnotationMirror> annotationMirrors, @NonCaptured @Modified @Nonnull TypeImporter typeImporter) {
        return annotationMirrors.map(annotationMirror -> getAnnotationAsString(annotationMirror, typeImporter)).join("", " ", "", " ");
    }
    
    /**
     * Returns the annotations of the given type mirror with their values as a string.
     */
    @Pure
    public static @Nonnull String getAnnotationsAsString(@Nonnull TypeMirror typeMirror, @NonCaptured @Modified @Nonnull TypeImporter typeImporter) {
        return getAnnotationsAsString(FiniteIterable.of(typeMirror.getAnnotationMirrors()), typeImporter);
    }
    
    /* -------------------------------------------------- Type Conversion -------------------------------------------------- */
    
    private static final @Nonnull ImmutableMap<@Nonnull Class<?>, @Nonnull TypeKind> primitiveTypes = ImmutableMap.<Class<?>, TypeKind>with(boolean.class, TypeKind.BOOLEAN).with(char.class, TypeKind.CHAR).with(byte.class, TypeKind.BYTE).with(short.class, TypeKind.SHORT).with(int.class, TypeKind.INT).with(long.class, TypeKind.LONG).with(float.class, TypeKind.FLOAT).with(double.class, TypeKind.DOUBLE).build();
    
    /**
     * Returns the given class object as a type mirror.
     */
    @Pure
    @LogsErrorWhenReturningNull
    public static @Nullable TypeMirror getTypeMirror(@Nonnull Class<?> type) {
        if (type.isArray()) {
            final @Nullable TypeMirror componentType = getTypeMirror(type.getComponentType());
            if (componentType == null) { ProcessingLog.error("Could not retrieve a type mirror for the component type of the array $.", type.getCanonicalName()); }
            else { return StaticProcessingEnvironment.getTypeUtils().getArrayType(componentType); }
        } else if (type.isPrimitive()) {
            final @Nullable TypeKind typeKind = primitiveTypes.get(type);
            if (typeKind == null) { ProcessingLog.error("There is no mapping for the primitive type $.", type.getName()); }
            else { return StaticProcessingEnvironment.getTypeUtils().getPrimitiveType(typeKind); }
        } else if (type.isLocalClass()) {
            ProcessingLog.error("Cannot retrieve a type mirror for the local class $.", type.getName());
        } else if (type.isAnonymousClass()) {
            ProcessingLog.error("Cannot retrieve a type mirror for the anonymous class $.", type.getName());
        } else {
            final @Nullable TypeElement desiredTypeElement = StaticProcessingEnvironment.getElementUtils().getTypeElement(type.getCanonicalName());
            if (desiredTypeElement == null) { ProcessingLog.error("Could not retrieve the element for the type $.", type.getCanonicalName()); }
            else { return desiredTypeElement.asType(); }
        }
        return null;
    }
    
    /**
     * Returns the given class object as an erased type mirror.
     */
    @Pure
    @LogsErrorWhenReturningNull
    public static @Nullable TypeMirror getErasedTypeMirror(@Nonnull Class<?> type) {
        final @Nullable TypeMirror typeMirror = getTypeMirror(type);
        return typeMirror != null ? StaticProcessingEnvironment.getTypeUtils().erasure(typeMirror) : null;
    }
    
    /* -------------------------------------------------- Assignability -------------------------------------------------- */
    
    /**
     * Returns whether the given declared type is rawly assignable to the given desired type.
     */
    @Pure
    public static boolean isRawlyAssignable(@Nonnull TypeMirror declaredType, @Nonnull Class<?> desiredType) {
        final @Nullable TypeMirror typeMirror = getErasedTypeMirror(desiredType);
        return typeMirror != null ? StaticProcessingEnvironment.getTypeUtils().isAssignable(declaredType, typeMirror) : false;
    }
    
    /**
     * Returns whether the given element is rawly assignable to the given type.
     */
    @Pure
    public static boolean isRawlyAssignable(@Nonnull Element element, @Nonnull Class<?> type) {
        return isRawlyAssignable(getType(element), type);
    }
    
    /* -------------------------------------------------- Subtyping -------------------------------------------------- */
    
    /**
     * Returns whether the given declared type is a raw subtype of the given desired type.
     */
    @Pure
    public static boolean isRawSubtype(@Nonnull TypeMirror declaredType, @Nonnull Class<?> desiredType) {
        final @Nullable TypeMirror typeMirror = getErasedTypeMirror(desiredType);
        return typeMirror != null ? StaticProcessingEnvironment.getTypeUtils().isSubtype(declaredType, typeMirror) : false;
    }
    
    /**
     * Returns whether the type of the given element is a raw subtype of the given type.
     */
    @Pure
    public static boolean isRawSubtype(@Nonnull Element element, @Nonnull Class<?> type) {
        return ProcessingUtility.isRawSubtype(getType(element), type);
    }
    
    /* -------------------------------------------------- Getters and Setters -------------------------------------------------- */
    
    /**
     * Returns whether the given method is a (potentially static) getter.
     */
    @Pure
    public static boolean isGetter(@Nonnull ExecutableElement method) {
        final @Nonnull String name = method.getSimpleName().toString();
        return method.getKind() == ElementKind.METHOD &&
                method.getTypeParameters().isEmpty() &&
                method.getThrownTypes().isEmpty() &&
                method.getParameters().isEmpty() &&
                method.getReturnType().getKind() != TypeKind.VOID &&
                (name.startsWith("get") || (name.startsWith("is") || name.startsWith("has") || name.startsWith("can")) && isRawlyAssignable(method.getReturnType(), boolean.class));
    }
    
    /**
     * Returns whether the given method is a (potentially static) setter.
     */
    @Pure
    public boolean isSetter(@Nonnull ExecutableElement method) {
        return method.getKind() == ElementKind.METHOD &&
                method.getTypeParameters().isEmpty() &&
                method.getThrownTypes().isEmpty() &&
                method.getParameters().size() == 1 &&
                method.getReturnType().getKind() == TypeKind.VOID &&
                method.getSimpleName().toString().startsWith("set");
    }
    
    /* -------------------------------------------------- Type Visitors -------------------------------------------------- */
    
    /**
     * This type visitor returns the qualified name of the given type without generic parameters.
     */
    @Stateless
    public static class QualifiedNameTypeVisitor extends SimpleTypeVisitor8<@Nonnull String, @Nullable Void> {
        
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
            return visit(type.getExtendsBound());
        }
        
    }
    
    private static final @Nonnull QualifiedNameTypeVisitor QUALIFIED_NAME_TYPE_VISITOR = new QualifiedNameTypeVisitor();
    
    /**
     * Returns the qualified name of the given type mirror without generic parameters.
     */
    @Pure
    public static @Nonnull String getQualifiedName(@Nonnull TypeMirror typeMirror) {
        return QUALIFIED_NAME_TYPE_VISITOR.visit(typeMirror);
    }
    
    /**
     * This type visitor returns the simple name of the given type without generic parameters.
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
     * Returns the simple name of the given type mirror without generic parameters.
     */
    @Pure
    public static @Nonnull String getSimpleName(@Nonnull TypeMirror typeMirror) {
        return SIMPLE_NAME_TYPE_VISITOR.visit(typeMirror);
    }
    
    /* -------------------------------------------------- Type Equality -------------------------------------------------- */
    
    /**
     * Returns whether the given mirror corresponds to the given type.
     */
    @Pure
    public static boolean correspond(@Nonnull TypeMirror mirror, @Nonnull Class<?> type) {
        return getQualifiedName(mirror).equals(type.getCanonicalName());
    }
    
    /**
     * Returns whether the type of the given element corresponds to the given type.
     */
    @Pure
    public static boolean correspond(@Nonnull Element element, @Nonnull Class<?> type) {
        return correspond(getType(element), type);
    }
    
    /* -------------------------------------------------- Supertype -------------------------------------------------- */
    
    /**
     * Returns the supertype of the given source type that corresponds to the given target type or null (without logged errors) if not found.
     */
    @Pure
    public static @Nullable DeclaredType getSupertype(@Nonnull DeclaredType sourceType, @Nonnull Class<?> targetType) {
        if (correspond(sourceType, targetType)) {
            return sourceType;
        } else {
            final @Nonnull List<@Nonnull ? extends TypeMirror> supertypes = StaticProcessingEnvironment.getTypeUtils().directSupertypes(sourceType);
            for (@Nonnull TypeMirror supertype : supertypes) {
                final @Nullable DeclaredType result = getSupertype((DeclaredType) supertype, targetType);
                if (result != null) { return result; }
            }
            return null;
        }
    }
    
    /* -------------------------------------------------- Component Type -------------------------------------------------- */
    
    /**
     * Returns the component type of the given type if it is an array or an iterable and logs errors with the given logger otherwise.
     */
    @Pure
    @LogsErrorWhenReturningNull
    public static @Nullable TypeMirror getComponentType(@Nonnull TypeMirror type, @NonCaptured @Modified @Nonnull ErrorLogger errorLogger) {
        if (type.getKind() == TypeKind.ARRAY) {
            return ((ArrayType) type).getComponentType();
        } else if (type.getKind() == TypeKind.DECLARED) {
            final @Nullable DeclaredType supertype = ProcessingUtility.getSupertype((DeclaredType) type, Iterable.class);
            if (supertype != null) {
                return supertype.getTypeArguments().get(0);
            } else {
                errorLogger.log("The declared type $ is not iterable.", null, type);
            }
        } else {
            errorLogger.log("The type $ is neither an array nor a declared type.", null, type);
        }
        return null;
    }
    
    /**
     * Returns the component type of the given type if it is an array or an iterable.
     */
    @Pure
    @LogsErrorWhenReturningNull
    public static @Nullable TypeMirror getComponentType(@Nonnull TypeMirror type) {
        return getComponentType(type, ErrorLogger.INSTANCE);
    }
    
    /**
     * Returns the component type of the type of the given element if it is an array or an iterable and logs errors with the given logger otherwise.
     */
    @Pure
    @LogsErrorWhenReturningNull
    public static @Nullable TypeMirror getComponentType(@Nonnull Element element, @NonCaptured @Modified @Nonnull ErrorLogger errorLogger) {
        return getComponentType(getType(element), errorLogger);
    }
    
    /**
     * Returns the component type of the type of the given element if it is an array or an iterable.
     */
    @Pure
    @LogsErrorWhenReturningNull
    public static @Nullable TypeMirror getComponentType(@Nonnull Element element) {
        return getComponentType(element, ErrorLogger.INSTANCE);
    }
    
    /* -------------------------------------------------- Fields of Type -------------------------------------------------- */
    
    /**
     * Returns a list of all the fields with the given type in the given type element.
     */
    @Pure
    public static @Nonnull FiniteIterable<@Nonnull VariableElement> getFieldsOfType(@Nonnull TypeElement typeElement, @Nonnull Class<?> fieldType) {
        return getFields(typeElement).filter(field -> correspond(field, fieldType));
    }
    
    /**
     * Returns the unique, public and static field with the given type in the given type element.
     */
    @Pure
    @LogsErrorWhenReturningNull
    public static @Nullable VariableElement getUniquePublicStaticFieldOfType(@Nonnull TypeElement typeElement, @Nonnull Class<?> fieldType) {
        final @Nonnull FiniteIterable<@Nonnull VariableElement> fields = getFieldsOfType(typeElement, fieldType);
        if (fields.isSingle()) {
            final @Nonnull VariableElement field = fields.getFirst();
            if (!field.getModifiers().contains(Modifier.PUBLIC)) {
                ProcessingLog.error("The field of type $ has to be public:", SourcePosition.of(field), fieldType.getCanonicalName());
            } else if (!field.getModifiers().contains(Modifier.STATIC)) {
                ProcessingLog.error("The field of type $ has to be static:", SourcePosition.of(field), fieldType.getCanonicalName());
            } else {
                return field;
            }
        } else {
            ProcessingLog.error("There is not exactly one field of type $ in the class", SourcePosition.of(typeElement), fieldType.getCanonicalName());
        }
        return null;
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    /**
     * Returns the non-private method with the given name, return type, parameter types and no thrown types in the given type element or null if no such method is found.
     */
    @Pure
    public static @Nullable ExecutableElement getNonPrivateMethod(@Nonnull TypeElement typeElement, @Nonnull String methodName, @Nonnull Class<?> returnType, @Nonnull @NonNullableElements Class<?>... parameterTypes) {
        final @Nonnull DeclaredType surroundingType = (DeclaredType) typeElement.asType();
        for (@Nonnull ExecutableElement inheritedMethod : getAllMethods(typeElement)) {
            if (!inheritedMethod.getModifiers().contains(Modifier.PRIVATE) && inheritedMethod.getSimpleName().contentEquals(methodName) && inheritedMethod.getThrownTypes().isEmpty()) {
                final @Nonnull ExecutableType methodType = (ExecutableType) StaticProcessingEnvironment.getTypeUtils().asMemberOf(surroundingType, inheritedMethod);
                if (isRawSubtype(methodType.getReturnType(), returnType)) {
                    if (methodType.getParameterTypes().size() == parameterTypes.length) {
                        boolean isAssignable = true;
                        for (int i = 0; i < parameterTypes.length; i++) {
                            final @Nonnull TypeMirror parameterType = methodType.getParameterTypes().get(i);
                            if (parameterType.getKind() == TypeKind.TYPEVAR || !correspond(parameterType, parameterTypes[i])) { isAssignable = false; }
                        }
                        if (isAssignable) { return inheritedMethod; }
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * Returns whether the given type element contains a non-private method with the given name, return type, parameter types and no thrown types.
     */
    @Pure
    public static boolean hasNonPrivateMethod(@Nonnull TypeElement typeElement, @Nonnull String methodName, @Nonnull Class<?> returnType, @Nonnull @NonNullableElements Class<?>... parameterTypes) {
        return getNonPrivateMethod(typeElement, methodName, returnType, parameterTypes) != null;
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
