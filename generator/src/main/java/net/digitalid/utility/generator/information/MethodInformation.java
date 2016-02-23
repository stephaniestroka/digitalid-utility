package net.digitalid.utility.generator.information;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.QualifiedNameable;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.generator.SubclassGenerator;
import net.digitalid.utility.generator.annotations.Interceptor;
import net.digitalid.utility.generator.annotations.Recover;
import net.digitalid.utility.generator.interceptor.MethodInterceptor;
import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.logging.processing.AnnotationProcessing;
import net.digitalid.utility.logging.processing.SourcePosition;
import net.digitalid.utility.processor.ProcessingUtility;
import net.digitalid.utility.string.QuoteString;
import net.digitalid.utility.string.StringCase;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
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
    public final @Nonnull ExecutableElement element;
    
    /**
     * Stores the name of the underlying method.
     */
    public final @Nonnull String name;
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return QuoteString.inSingle(name);
    }
    
    /* -------------------------------------------------- Type -------------------------------------------------- */
    
    /**
     * Stores the type of the underlying method.
     */
    public final @Nonnull ExecutableType type;
    
    @Pure
    public boolean isGeneric() {
        return !type.getTypeVariables().isEmpty();
    }
    
    /* -------------------------------------------------- Exceptions -------------------------------------------------- */
    
    @Pure
    public boolean throwsExceptions() {
        return !element.getThrownTypes().isEmpty();
    }
    
    /* -------------------------------------------------- Package -------------------------------------------------- */
    
    /**
     * Stores the package in which the method is declared.
     */
    public final @Nonnull PackageElement packageElement;
    
    /**
     * Stores the name of the package in which the method is declared.
     */
    public final @Nonnull String packageName;
    
    @Pure
    public boolean isPartOfRuntimeEnvironment() {
        return packageName.startsWith("java");
    }
    
    @Pure
    public boolean isPartOfDigitalIDLibrary() {
        return packageName.startsWith("net.digitalid.");
    }
    
    /* -------------------------------------------------- Parameters -------------------------------------------------- */
    
    @Pure
    public boolean hasParameters() {
        return !element.getParameters().isEmpty();
    }
    
    @Pure
    public boolean hasSingleParameter() {
        return element.getParameters().size() == 1;
    }
    
    @Pure
    public boolean hasSingleParameter(@Nonnull String desiredTypeName) {
        if (hasSingleParameter()) {
            final @Nonnull String parameterTypeName = element.getParameters().get(0).asType().toString();
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
        final @Nonnull String returnTypeName = element.getReturnType().toString();
        AnnotationLog.verbose("Return type: " + QuoteString.inSingle(returnTypeName) + ", desired type:" + QuoteString.inSingle(desiredTypeName));
        return returnTypeName.equals(desiredTypeName);
    }
    
    @Pure
    public boolean hasReturnType(@Nonnull Class<?> type) {
        return hasReturnType(type.getCanonicalName());
    }
    
    @Pure
    public boolean hasReturnType() {
        return element.getReturnType().getKind() != TypeKind.VOID;
    }
    
    /* -------------------------------------------------- Getters and Setters -------------------------------------------------- */
    
    @Pure
    public boolean isGetter() {
        return !isGeneric() && !throwsExceptions() && !hasParameters() && hasReturnType() && (name.startsWith("get") || (name.startsWith("is") || name.startsWith("has")) && hasReturnType(boolean.class));
    }
    
    @Pure
    public boolean isSetter() {
        return !isGeneric() && !throwsExceptions() && hasSingleParameter() && !hasReturnType() && name.startsWith("set");
    }
    
    /**
     * @require isGetter() || isSetter() : "The method is neither a getter nor a setter.";
     */
    @Pure
    protected @Nonnull String getFieldName() {
        Require.that(isGetter() || isSetter()).orThrow("The method " + QuoteString.inSingle(name) + " is neither a getter nor a setter.");
        
        return StringCase.lowerCaseFirstCharacter(name.substring(name.startsWith("is") ? 2 : 3));
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
    
    @Pure
    public boolean isSynchronized() {
        return modifiers.contains(Modifier.SYNCHRONIZED);
    }
    
    @Pure
    public @Nonnull String getModifiersForOverridingMethod() {
        final @Nonnull StringBuilder result = new StringBuilder();
        if (isPublic()) { result.append("public "); }
        if (isProtected()) { result.append("protected "); }
        if (isSynchronized()) { result.append("synchronized "); }
        return result.toString();
    }
    
    /* -------------------------------------------------- Annotations -------------------------------------------------- */
    
    @Pure
    public boolean hasAnnotation(@Nonnull Class<? extends Annotation> annotationType) {
        return ProcessingUtility.getAnnotationMirror(element, annotationType) != null;
    }
    
    /**
     * Returns whether the method is annotated with '@Recover'.
     */
    @Pure
    public boolean isRecover() {
        return hasAnnotation(Recover.class);
    }
    
    /* -------------------------------------------------- Validators -------------------------------------------------- */
    
    /**
     * Stores the validators that validate the result of the method.
     */
    public final @Nonnull @NonNullableElements Map<AnnotationMirror, AnnotationValidator> validators;
    
    /* -------------------------------------------------- Interceptors -------------------------------------------------- */
    
    /**
     * Stores the interceptors which intercept the call to this method.
     */
    public final @Nonnull @NonNullableElements Map<AnnotationMirror, MethodInterceptor> interceptors;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected MethodInformation(@Nonnull DeclaredType type, @Nonnull ExecutableElement element) {
        this.element = element;
        this.name = element.getSimpleName().toString();
        this.type = (ExecutableType) AnnotationProcessing.getTypeUtils().asMemberOf(type, element);
        this.modifiers = Collections.unmodifiableSet(element.getModifiers());
        this.validators = ProcessingUtility.getAnnotationValidators(element);
        this.interceptors = ProcessingUtility.getCodeGenerators(element, Interceptor.class, MethodInterceptor.class);
        this.packageElement = (PackageElement) element.getEnclosingElement().getEnclosingElement();
        this.packageName = packageElement.getQualifiedName().toString();
        
        if (isPartOfDigitalIDLibrary()) {
            if (isGetter() && !hasAnnotation(Pure.class)) { AnnotationLog.error("A getter has to be '@Pure':", SourcePosition.of(element)); }
            if (isSetter() && hasAnnotation(Pure.class)) { AnnotationLog.error("A setter may not be '@Pure':", SourcePosition.of(element)); }
        }
        
        if (isRecover()) {
            @Nullable String errorMessage = null;
            if (!isStatic()) { errorMessage = "The annotated method has to be static:"; }
            if (element.getReturnType().getKind() != TypeKind.DECLARED) { errorMessage = "The return type has to be a declared type:"; }
            final @Nonnull String qualifiedReturnTypeName = ((QualifiedNameable) ((DeclaredType) element.getReturnType()).asElement()).getQualifiedName().toString();
            final @Nonnull String qualifiedEnclosingClassName = ((QualifiedNameable) element.getEnclosingElement()).getQualifiedName().toString();
            if (!qualifiedReturnTypeName.equals(qualifiedEnclosingClassName)) { errorMessage = "The return type has to be the enclosing class:"; }
            if (errorMessage != null) { AnnotationLog.error(errorMessage, SourcePosition.of(element)); }
            AnnotationLog.verbose("Found the recover method", SourcePosition.of(element));
        }
    }
    
    /**
     * Returns the method information for the given method element.
     */
    @Pure
    public static @Nonnull MethodInformation forMethod(@Nonnull DeclaredType type, @Nonnull ExecutableElement element) {
        return new MethodInformation(type, element);
    }
    
}
