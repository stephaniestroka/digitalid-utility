package net.digitalid.utility.generator.information.method;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.QualifiedNameable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.conversion.annotations.Recover;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.annotations.meta.Interceptor;
import net.digitalid.utility.generator.generators.BuilderGenerator;
import net.digitalid.utility.generator.generators.SubclassGenerator;
import net.digitalid.utility.generator.interceptor.MethodInterceptor;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processing.utility.StaticProcessingEnvironment;
import net.digitalid.utility.processor.generator.JavaFileGenerator;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.getter.Default;
import net.digitalid.utility.validation.processing.ValidatorProcessingUtility;
import net.digitalid.utility.validation.validator.MethodAnnotationValidator;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

import com.sun.tools.javac.code.Type;

/**
 * This type collects the relevant information about a method for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 */
public class MethodInformation extends ExecutableInformation {
    
    /* -------------------------------------------------- Value Validators -------------------------------------------------- */
    
    private final @Nonnull Map<AnnotationMirror, ValueAnnotationValidator> returnValueValidators;
    
    public @Nonnull Map<AnnotationMirror, ValueAnnotationValidator> getReturnValueValidators() {
        return returnValueValidators;
    }
    
    /* -------------------------------------------------- Method Validators -------------------------------------------------- */
    
    private final @Nonnull Map<AnnotationMirror, MethodAnnotationValidator> methodValidators;
    
    public @Nonnull Map<AnnotationMirror, MethodAnnotationValidator> getMethodValidators() {
        return methodValidators;
    }
    
    /* -------------------------------------------------- Type -------------------------------------------------- */
    
    /**
     * Returns whether this method declares its own generic parameters.
     */
    @Pure
    public boolean isGeneric() {
        return !getType().getTypeVariables().isEmpty();
    }
    
    /* -------------------------------------------------- Return Type -------------------------------------------------- */
    
    /**
     * Returns whether this method has the given return type.
     */
    @Pure
    public boolean hasReturnType(@Nonnull String desiredTypeName) {
        final @Nonnull String returnTypeName = getElement().getReturnType().toString();
        ProcessingLog.verbose("Return type: $, desired type: $", returnTypeName, desiredTypeName);
        return returnTypeName.equals(desiredTypeName);
    }
    
    /**
     * Returns whether this method has the given return type.
     */
    @Pure
    public boolean hasReturnType(@Nonnull Class<?> type) {
        return hasReturnType(type.getCanonicalName());
    }
    
    /**
     * Returns whether this method has a return type (does not return void).
     */
    @Pure
    public boolean hasReturnType() {
        return getElement().getReturnType().getKind() != TypeKind.VOID;
    }
    
    /* -------------------------------------------------- Getters and Setters -------------------------------------------------- */
    
    /**
     * Returns whether this method is a getter.
     */
    @Pure
    public boolean isGetter() {
        return !isStatic() && !isGeneric() && !throwsExceptions() && !hasParameters() && hasReturnType() && (getName().startsWith("get") || (getName().startsWith("is") || getName().startsWith("has")) && hasReturnType(boolean.class));
    }
    
    /**
     * Returns whether this method is a setter.
     */
    @Pure
    public boolean isSetter() {
        return !isGeneric() && !throwsExceptions() && hasSingleParameter() && !hasReturnType() && getName().startsWith("set");
    }
    
    /**
     * Returns the name of the field that corresponds to this getter or setter method.
     * 
     * @require isGetter() || isSetter() : "The method is neither a getter nor a setter.";
     */
    @Pure
    public @Nonnull String getFieldName() {
        Require.that(isGetter() || isSetter()).orThrow("The method $ is neither a getter nor a setter.", getName());
        
        return Strings.lowercaseFirstCharacter(getName().substring(getName().startsWith("is") ? 2 : 3));
    }
    
    /* -------------------------------------------------- Modifiers -------------------------------------------------- */
    
    /**
     * Returns whether the represented {@link #getElement() element} is synchronized.
     */
    @Pure
    public boolean isSynchronized() {
        return getModifiers().contains(Modifier.SYNCHRONIZED);
    }
    
    /**
     * Returns the modifiers for the method that overrides this method.
     */
    @Pure
    public @Nonnull String getModifiersForOverridingMethod() {
        final @Nonnull StringBuilder result = new StringBuilder();
        if (isPublic()) { result.append("public "); }
        if (isProtected()) { result.append("protected "); }
        if (isSynchronized()) { result.append("synchronized "); }
        return result.toString();
    }
    
    /* -------------------------------------------------- Annotations -------------------------------------------------- */
    
    /**
     * Returns whether the method is annotated with '@Recover'.
     */
    @Pure
    public boolean isRecover() {
        return hasAnnotation(Recover.class);
    }
    
    /* -------------------------------------------------- Interceptors -------------------------------------------------- */
    
    private final @Nonnull @NonNullableElements Map<AnnotationMirror, MethodInterceptor> interceptors;
    
    /**
     * Returns the interceptors that intercept the call to this method.
     */
    @Pure
    public @Nonnull @NonNullableElements Map<AnnotationMirror, MethodInterceptor> getInterceptors() {
        return interceptors;
    }
    
    /* -------------------------------------------------- Default Values -------------------------------------------------- */
    
    @Pure
    public @Nullable String getDefaultValue() {
        if (isGetter()) {
            final @Nonnull FiniteIterable<VariableElement> fields = FiniteIterable.of(ElementFilter.fieldsIn(StaticProcessingEnvironment.getElementUtils().getAllMembers((TypeElement) getContainingType().asElement())));
            final @Nullable VariableElement fieldElement = fields.findFirst(field -> field.getSimpleName().contentEquals(getFieldName()));
            if (fieldElement == null) {
                ProcessingLog.information("Found the method $, which looks like a getter, but does not have a corresponding field.", getName());
                return null;
            }
            final @Nullable Default defaultAnnotation = fieldElement.getAnnotation(Default.class);
            if (defaultAnnotation != null) {
                return defaultAnnotation.value();
            } else {
                if (getType().getKind().isPrimitive()) {
                    final @Nonnull String typeName;
                    if (getType() instanceof Type.AnnotatedType) {
                        Type.AnnotatedType annotatedType = (Type.AnnotatedType) getType();
                        final Type type = annotatedType.unannotatedType();
                        typeName = type.toString();
                    } else {
                        typeName = getType().toString();
                    }
                    if (typeName.equals("boolean")) {
                        return "false";
                    } else {
                        return "0";
                    }
                } else {
                    return "null";
                }
            }
        } else {
            return null;
        }
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected MethodInformation(@Nonnull ExecutableElement element, @Nonnull DeclaredType containingType) {
        super(element, containingType);
        
        Require.that(element.getKind() == ElementKind.METHOD).orThrow("The element $ has to be a method.", SourcePosition.of(element));
        
        this.interceptors = ValidatorProcessingUtility.getAnnotationHandlers(element, Interceptor.class, MethodInterceptor.class);
        
        if (isDeclaredInDigitalIDLibrary()) {
            if (isGetter() && !hasAnnotation(Pure.class)) { ProcessingLog.error("A getter has to be '@Pure':", SourcePosition.of(element)); }
            if (isSetter() && hasAnnotation(Pure.class)) { ProcessingLog.error("A setter may not be '@Pure':", SourcePosition.of(element)); }
        }
        
        if (isRecover()) {
            @Nullable String errorMessage = null;
            if (!isStatic()) { errorMessage = "The annotated method has to be static:"; }
            if (element.getReturnType().getKind() != TypeKind.DECLARED) { errorMessage = "The return type has to be a declared type:"; }
            final @Nonnull String qualifiedReturnTypeName = ((QualifiedNameable) ((DeclaredType) element.getReturnType()).asElement()).getQualifiedName().toString();
            final @Nonnull String qualifiedEnclosingClassName = ((QualifiedNameable) element.getEnclosingElement()).getQualifiedName().toString();
            if (!qualifiedReturnTypeName.equals(qualifiedEnclosingClassName)) { errorMessage = "The return type has to be the enclosing class:"; }
            if (errorMessage != null) { ProcessingLog.error(errorMessage, SourcePosition.of(element)); }
            ProcessingLog.verbose("Found the recover method", SourcePosition.of(element));
        }
        this.methodValidators = ValidatorProcessingUtility.getMethodValidators(this.getElement());
        this.returnValueValidators = ValidatorProcessingUtility.getValueValidators(this.getElement());
        ProcessingLog.debugging("Method validators for method $: $", this.getElement(), methodValidators);
        ProcessingLog.debugging("Returned value validators for method $: $", this.getElement(), returnValueValidators);
    }
    
    /**
     * Returns the method information of the given method element and containing type.
     * 
     * @require element.getKind() == ElementKind.METHOD : "The element has to be a method.";
     */
    @Pure
    public static @Nonnull MethodInformation of(@Nonnull ExecutableElement element, @Nonnull DeclaredType containingType) {
        return new MethodInformation(element, containingType);
    }
    
    /**
     * Returns all return type annotations of the method as a space-separated string.
     */
    public @Nonnull String getReturnTypeAnnotations(@Nonnull JavaFileGenerator javaFileGenerator) {
        final @Nonnull TypeMirror typeMirror = StaticProcessingEnvironment.getTypeUtils().asMemberOf(getContainingType(), getElement());
        final @Nonnull ExecutableType executableType = (ExecutableType) typeMirror;
        final @Nonnull TypeMirror returnType = executableType.getReturnType();
        final @Nonnull String returnTypeAnnotationsAsString;
        
        if (returnType instanceof Type.AnnotatedType) {
            final Type.@Nonnull AnnotatedType annotatedType = (Type.AnnotatedType) returnType;
            returnTypeAnnotationsAsString = ProcessingUtility.getAnnotationsAsString(annotationMirror -> javaFileGenerator.importIfPossible(annotationMirror.getAnnotationType()), annotatedType.getAnnotationMirrors());
        } else {
            returnTypeAnnotationsAsString = "";
        }
        return returnTypeAnnotationsAsString;
    }
    
    public @Nullable String getReturnType(@Nonnull JavaFileGenerator javaFileGenerator) {
        if (!hasReturnType()) {
            return null;
        }
        final @Nonnull TypeMirror typeMirror = StaticProcessingEnvironment.getTypeUtils().asMemberOf(getContainingType(), getElement());
        Require.that(typeMirror instanceof ExecutableType).orThrow("Expected ExecutableType, but got $", getContainingType());
        
        final @Nonnull ExecutableType executableType = (ExecutableType) typeMirror;
        final @Nonnull TypeMirror returnType = executableType.getReturnType();
    
        final @Nonnull StringBuilder returnTypeAsString = new StringBuilder();
        if (returnType instanceof Type.AnnotatedType) {
            final Type.AnnotatedType annotatedType = (Type.AnnotatedType) returnType;
            returnTypeAsString.append(getReturnTypeAnnotations(javaFileGenerator));
            returnTypeAsString.append(javaFileGenerator.importIfPossible(annotatedType.unannotatedType()));
        } else {
            returnTypeAsString.append(javaFileGenerator.importIfPossible(returnType));
        }
        ProcessingLog.debugging("Stringifying return type: $ = $", returnType.getClass(), returnTypeAsString);
        return returnTypeAsString.toString();
    }
}
