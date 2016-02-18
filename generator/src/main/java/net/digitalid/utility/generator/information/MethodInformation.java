package net.digitalid.utility.generator.information;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.QualifiedNameable;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.generator.SubclassGenerator;
import net.digitalid.utility.generator.annotations.Interceptor;
import net.digitalid.utility.generator.annotations.Recover;
import net.digitalid.utility.generator.interceptor.MethodInterceptor;
import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.logging.processing.SourcePosition;
import net.digitalid.utility.processor.ProcessingUtility;
import net.digitalid.utility.string.QuoteString;
import net.digitalid.utility.string.StringCase;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.meta.Validator;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.validator.AnnotationValidator;

/**
 * This class collects the relevant information about a method for generating a subclass.
 * 
 * @see SubclassGenerator
 */
public class MethodInformation {
    
    /* -------------------------------------------------- Method -------------------------------------------------- */
    
    /**
     * Stores the underlying method.
     */
    public final @Nonnull ExecutableElement method;
    
    /**
     * Stores the name of the underlying method.
     */
    public final @Nonnull String methodName;
    
    /* -------------------------------------------------- Parameters -------------------------------------------------- */
    
    @Pure
    public boolean hasParameters() {
        return !method.getParameters().isEmpty();
    }
    
    @Pure
    public boolean hasSingleParameter() {
        return method.getParameters().size() == 1;
    }
    
    @Pure
    public boolean hasSingleParameter(@Nonnull String desiredTypeName) {
        if (hasSingleParameter()) {
            final @Nonnull String parameterTypeName = method.getParameters().get(0).asType().toString();
            AnnotationLog.verbose("Parameter type: " + QuoteString.inSingle(parameterTypeName) + ", desired type:" + QuoteString.inSingle(desiredTypeName));
            return parameterTypeName.equals(desiredTypeName);
        } else {
            return false;
        }
    }
    
    @Pure
    public boolean hasSingleParameter(@Nonnull Class<?> type) {
        return hasSingleParameter(type.getCanonicalName());
    }
    
    @Pure
    public boolean hasReturnType(@Nonnull String desiredTypeName) {
        final @Nonnull String returnTypeName = method.getReturnType().toString();
        AnnotationLog.verbose("Return type: " + QuoteString.inSingle(returnTypeName) + ", desired type:" + QuoteString.inSingle(desiredTypeName));
        return returnTypeName.equals(desiredTypeName);
    }
    
    @Pure
    public boolean hasReturnType(@Nonnull Class<?> type) {
        return hasReturnType(type.getCanonicalName());
    }
    
    @Pure
    public boolean hasNoReturnType() {
        return hasReturnType("void");
    }
    
    @Pure
    public boolean hasReturnType() {
        return !hasNoReturnType();
    }
    
    /* -------------------------------------------------- Getters and Setters -------------------------------------------------- */
    
    @Pure
    public boolean isGetter() {
        return !hasParameters() && hasReturnType() && (methodName.startsWith("get") || (methodName.startsWith("is") || methodName.startsWith("has")) && hasReturnType(boolean.class));
    }
    
    @Pure
    public boolean isSetter() {
        return hasSingleParameter() && hasNoReturnType() && methodName.startsWith("set");
    }
    
    /**
     * @require isGetter() || isSetter() : "The method is neither a getter nor a setter.";
     */
    @Pure
    protected @Nonnull String getFieldName() {
        Require.that(isGetter() || isSetter()).orThrow("The method " + QuoteString.inSingle(methodName) + " is neither a getter nor a setter.");
        
        return StringCase.lowerCaseFirstCharacter(methodName.substring(methodName.startsWith("is") ? 2 : 3));
    }
    
    /* -------------------------------------------------- Modifiers -------------------------------------------------- */
    
    /**
     * Stores the modifiers of the underlying method.
     */
    public final @Nonnull @NonNullableElements Set<Modifier> modifiers;
    
    @Pure
    public boolean isAbstract() {
        return modifiers.contains(Modifier.ABSTRACT);
    }
    
    @Pure
    public boolean isPrivate() {
        return modifiers.contains(Modifier.PRIVATE);
    }
    
    @Pure
    public boolean isProtected() {
        return modifiers.contains(Modifier.PROTECTED);
    }
    
    @Pure
    public boolean isPublic() {
        return modifiers.contains(Modifier.PUBLIC);
    }
    
    @Pure
    public boolean isStatic() {
        return modifiers.contains(Modifier.STATIC);
    }
    
    @Pure
    public boolean isFinal() {
        return modifiers.contains(Modifier.FINAL);
    }
    
    /* -------------------------------------------------- Annotations -------------------------------------------------- */
    
    @Pure
    public boolean hasAnnotation(@Nonnull Class<? extends Annotation> annotationType) {
        return ProcessingUtility.getAnnotationMirror(method, annotationType) != null;
    }
    
    /* -------------------------------------------------- Validators -------------------------------------------------- */
    
    /**
     * Stores the validators that validate the result of the method.
     */
    public final @Nonnull @NonNullableElements Map<AnnotationMirror, AnnotationValidator> resultValidators;
    
    /* -------------------------------------------------- Interceptors -------------------------------------------------- */
    
    /**
     * Stores the interceptors which intercept the call to this method.
     */
    public final @Nonnull @NonNullableElements Map<AnnotationMirror, MethodInterceptor> methodInterceptors;
    
    /* -------------------------------------------------- Recover -------------------------------------------------- */
    
    /**
     * Stores whether this method is annotated with '@Recover'.
     */
    public final boolean recover;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected MethodInformation(@Nonnull ExecutableElement method) {
        this.method = method;
        this.methodName = method.getSimpleName().toString();
        this.modifiers = method.getModifiers();
        this.resultValidators = ProcessingUtility.getCodeGenerators(method, Validator.class, AnnotationValidator.class);
        this.methodInterceptors = ProcessingUtility.getCodeGenerators(method, Interceptor.class, MethodInterceptor.class);
        
        if (isGetter() && !hasAnnotation(Pure.class)) { AnnotationLog.error("A getter has to be '@Pure':", SourcePosition.of(method)); }
        if (isSetter() && hasAnnotation(Pure.class)) { AnnotationLog.error("A setter may not be '@Pure':", SourcePosition.of(method)); }
        
        final @Nullable AnnotationMirror recoverAnnotationMirror = ProcessingUtility.getAnnotationMirror(method, Recover.class);
        if (recoverAnnotationMirror != null) {
            @Nullable String errorMessage = null;
            if (!isStatic()) { errorMessage = "The annotated method has to be static:"; }
            if (method.getReturnType().getKind() != TypeKind.DECLARED) { errorMessage = "The return type has to be a declared type:"; }
            final @Nonnull String qualifiedReturnTypeName = ((QualifiedNameable) ((DeclaredType) method.getReturnType()).asElement()).getQualifiedName().toString();
            final @Nonnull String qualifiedEnclosingClassName = ((QualifiedNameable) method.getEnclosingElement()).getQualifiedName().toString();
            if (!qualifiedReturnTypeName.equals(qualifiedEnclosingClassName)) { errorMessage = "The return type has to be the enclosing class:"; }
            if (errorMessage != null) { AnnotationLog.error(errorMessage, SourcePosition.of(method)); }
            AnnotationLog.verbose("Found the recover method", SourcePosition.of(method));
            this.recover = true;
        } else {
            this.recover = false;
        }
    }
    
    /**
     * Returns the method information for the given method element.
     */
    @Pure
    public static @Nonnull MethodInformation forMethod(@Nonnull ExecutableElement method) {
        return new MethodInformation(method);
    }
    
}
