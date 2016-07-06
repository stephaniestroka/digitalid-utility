package net.digitalid.utility.generator.information.method;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
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

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.collaboration.annotations.TODO;
import net.digitalid.utility.collaboration.enumerations.Author;
import net.digitalid.utility.collaboration.enumerations.Priority;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.annotations.meta.Interceptor;
import net.digitalid.utility.generator.generators.BuilderGenerator;
import net.digitalid.utility.generator.generators.SubclassGenerator;
import net.digitalid.utility.generator.interceptor.MethodInterceptor;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processing.utility.StaticProcessingEnvironment;
import net.digitalid.utility.processing.utility.TypeImporter;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.generation.Default;
import net.digitalid.utility.validation.annotations.generation.Recover;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.processing.AnnotationHandlerUtility;
import net.digitalid.utility.validation.validator.MethodAnnotationValidator;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

import com.sun.tools.javac.code.Type;

/**
 * This type collects the relevant information about a method for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 */
@Immutable
public class MethodInformation extends ExecutableInformation {
    
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
        // TODO: Remove the static check (see ProcessingUtility#isGetter)?
        return !isStatic() && !isGeneric() && !throwsExceptions() && !hasParameters() && hasReturnType() && (getName().startsWith("get") || (getName().startsWith("is") || getName().startsWith("has") || getName().startsWith("can")) && hasReturnType(boolean.class));
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
    
    /* -------------------------------------------------- Annotations -------------------------------------------------- */
    
    /**
     * Returns whether the method is annotated with '@Pure'.
     */
    @Pure
    public boolean isPure() {
        return hasAnnotation(Pure.class);
    }
    
    /**
     * Returns whether the method is annotated with '@Impure'.
     */
    @Pure
    public boolean isImpure() {
        return hasAnnotation(Impure.class);
    }
    
    /**
     * Returns whether the method is annotated with '@Recover'.
     */
    @Pure
    public boolean isRecover() {
        return hasAnnotation(Recover.class);
    }
    
    /**
     * Returns whether the method is annotated with '@Test'.
     */
    @Pure
    public boolean isTest() {
        return hasAnnotation("org.junit.Test");
    }
    
    /* -------------------------------------------------- Return Value Validators -------------------------------------------------- */
    
    private final @Nonnull Map<@Nonnull AnnotationMirror, @Nonnull ValueAnnotationValidator> returnValueValidators;
    
    @Pure
    public @Nonnull Map<@Nonnull AnnotationMirror, @Nonnull ValueAnnotationValidator> getReturnValueValidators() {
        return returnValueValidators;
    }
    
    /* -------------------------------------------------- Method Validators -------------------------------------------------- */
    
    private final @Nonnull Map<@Nonnull AnnotationMirror, @Nonnull MethodAnnotationValidator> methodValidators;
    
    @Pure
    public @Nonnull Map<@Nonnull AnnotationMirror, @Nonnull MethodAnnotationValidator> getMethodValidators() {
        return methodValidators;
    }
    
    /* -------------------------------------------------- Method Interceptors -------------------------------------------------- */
    
    private final @Nonnull Map<@Nonnull AnnotationMirror, @Nonnull MethodInterceptor> methodInterceptors;
    
    /**
     * Returns the interceptors that intercept the call to this method.
     */
    @Pure
    public @Nonnull Map<@Nonnull AnnotationMirror, @Nonnull MethodInterceptor> getMethodInterceptors() {
        return methodInterceptors;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected MethodInformation(@Nonnull ExecutableElement element, @Nonnull DeclaredType containingType) {
        super(element, containingType);
        
        Require.that(element.getKind() == ElementKind.METHOD).orThrow("The element $ has to be a method.", SourcePosition.of(element));
        
        this.methodInterceptors = AnnotationHandlerUtility.getAnnotationHandlers(element, Interceptor.class, MethodInterceptor.class);
        
        if (isDeclaredInDigitalIDLibrary()) {
            // TODO: Make sure that this doesn't apply for tests!
            if (!isTest() && isPure() == isImpure()) { ProcessingLog.error("A method has to be either '@Pure' or '@Impure':", SourcePosition.of(element)); }
            if (isSetter() && isPure()) { ProcessingLog.error("A setter may not be '@Pure':", SourcePosition.of(element)); }
        }
        
        // TODO: Shouldn't this rather be the usage check of the recover annotation?
        if (isRecover()) {
            @Nullable String errorMessage = null;
            if (!isStatic()) { errorMessage = "The annotated method has to be static:"; }
            if (element.getReturnType().getKind() != TypeKind.DECLARED) { errorMessage = "The return type has to be a declared type:"; }
            final @Nonnull String qualifiedReturnTypeName = ((QualifiedNameable) ((DeclaredType) element.getReturnType()).asElement()).getQualifiedName().toString();
            final @Nonnull String qualifiedEnclosingClassName = ((QualifiedNameable) element.getEnclosingElement()).getQualifiedName().toString();
            if (!qualifiedReturnTypeName.equals(qualifiedEnclosingClassName)) { errorMessage = "The return type has to be the enclosing class:"; } // TODO: Subtype is probably enough.
            if (errorMessage != null) { ProcessingLog.error(errorMessage, SourcePosition.of(element)); }
            ProcessingLog.verbose("Found the recover method", SourcePosition.of(element));
        }
        
        this.methodValidators = AnnotationHandlerUtility.getMethodValidators(this.getElement());
        this.returnValueValidators = AnnotationHandlerUtility.getValueValidators(this.getElement());
        ProcessingLog.debugging("Method validators for method $: $", this.getElement(), methodValidators);
        ProcessingLog.debugging("Return value validators for method $: $", this.getElement(), returnValueValidators);
        
        // TODO: This is just a temporary hack to ensure that the annotations on the parameters are checked in any case.
        for (@Nonnull VariableElement parameter : element.getParameters()) {
            AnnotationHandlerUtility.getValueValidators(parameter);
        }
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
    
    /* -------------------------------------------------- Default Values -------------------------------------------------- */
    
    @Pure
    @TODO(task = "Please document public methods.", date = "2015-05-16", author = Author.KASPAR_ETTER, assignee = Author.STEPHANIE_STROKA, priority = Priority.LOW)
    public @Nullable String getDefaultValue() {
        if (isGetter()) {
            final @Nonnull FiniteIterable<@Nonnull VariableElement> fields = ProcessingUtility.getAllFields((TypeElement) getContainingType().asElement());
            final @Nullable VariableElement fieldElement = fields.findFirst(field -> field.getSimpleName().contentEquals(getFieldName()));
            if (fieldElement == null) {
                ProcessingLog.debugging("Found the method $, which looks like a getter, but does not have a corresponding field.", getName());
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
    
    /* -------------------------------------------------- Annotations to String -------------------------------------------------- */
    
    // TODO: Review and simplify the following methods!
    
    /**
     * Returns all return type annotations of the method as a space-separated string.
     */
    public @Nonnull String getReturnTypeAnnotations(@Nonnull TypeImporter typeImporter) {
        final @Nonnull TypeMirror typeMirror = StaticProcessingEnvironment.getTypeUtils().asMemberOf(getContainingType(), getElement());
        final @Nonnull ExecutableType executableType = (ExecutableType) typeMirror;
        final @Nonnull TypeMirror returnType = executableType.getReturnType();
        final @Nonnull String returnTypeAnnotationsAsString;
        
        if (returnType instanceof Type.AnnotatedType) {
            final Type.@Nonnull AnnotatedType annotatedType = (Type.AnnotatedType) returnType;
            returnTypeAnnotationsAsString = ProcessingUtility.getAnnotationsAsString(FiniteIterable.of(annotatedType.getAnnotationMirrors()), typeImporter);
        } else {
            returnTypeAnnotationsAsString = "";
        }
        return returnTypeAnnotationsAsString;
    }
    
    public @Nullable TypeMirror getReturnType() {
        if (!hasReturnType()) {
            return null;
        }
        final @Nonnull TypeMirror typeMirror = StaticProcessingEnvironment.getTypeUtils().asMemberOf(getContainingType(), getElement());
        Require.that(typeMirror instanceof ExecutableType).orThrow("Expected ExecutableType, but got $", getContainingType());
    
        final @Nonnull ExecutableType executableType = (ExecutableType) typeMirror;
        return executableType.getReturnType();
    }
    
    public @Nullable String getReturnType(@Nonnull TypeImporter typeImporter) {
        final @Nullable TypeMirror returnType = getReturnType();
        if (returnType == null) {
            return null;
        }
        final @Nonnull StringBuilder returnTypeAsString = new StringBuilder();
        if (returnType instanceof Type.AnnotatedType) {
            final Type.AnnotatedType annotatedType = (Type.AnnotatedType) returnType;
            returnTypeAsString.append(getReturnTypeAnnotations(typeImporter));
            returnTypeAsString.append(typeImporter.importIfPossible(annotatedType.unannotatedType()));
        } else {
            returnTypeAsString.append(typeImporter.importIfPossible(returnType));
        }
        ProcessingLog.debugging("Stringifying return type: $ = $", returnType.getClass(), returnTypeAsString);
        return returnTypeAsString.toString();
    }
    
}
