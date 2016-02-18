package net.digitalid.utility.generator.information;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.logging.processing.AnnotationProcessing;
import net.digitalid.utility.logging.processing.SourcePosition;
import net.digitalid.utility.processor.ProcessingUtility;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.meta.Validator;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.validator.AnnotationValidator;

/**
 * Description.
 */
public class TypeInformation {
    
    /* -------------------------------------------------- Type -------------------------------------------------- */
    
    public final @Nonnull TypeElement typeElement;
    
    public final @Nonnull DeclaredType typeMirror;
    
    /* -------------------------------------------------- Validators -------------------------------------------------- */
    
    /**
     * Stores the validators that validate the type.
     */
    public final @Nonnull @NonNullableElements Map<AnnotationMirror, AnnotationValidator> typeValidators;
    
    /* -------------------------------------------------- Generatable -------------------------------------------------- */
    
    public final boolean generatable;
    
//    public final @Nonnull @NonNullableElements List<AnnotationValidator> typeValidators;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    public final @Nonnull @NonNullableElements List<ExecutableElement> constructors;
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    /**
     * Stores the non-static and non-final methods that are to be overridden.
     */
    public final @Nonnull @NonNullableElements List<MethodInformation> methods;
    
    /* -------------------------------------------------- Fields -------------------------------------------------- */
    
    /**
     * Stores the parameters of the constructor, which also represent the object.
     */
    public final @Nonnull @NonNullableElements List<FieldInformation> fields;
    
    /* -------------------------------------------------- Generation -------------------------------------------------- */
    
    /**
     * There may only be once constructor. If there are several, one of them or a static method has to be annotated with '@Recover'.
     * This is the constructor or static method that the builder calls. No, rather it calls the 'overriding' constructor.
     */
    public final @Nullable MethodInformation recoverMethod;
    
    public final @Nullable MethodInformation equalsMethod;
    
    public final @Nullable MethodInformation hashCodeMethod;
    
    public final @Nullable MethodInformation toStringMethod;
    
    public final @Nullable MethodInformation validateMethod;
    
    public final @Nullable MethodInformation compareToMethod;
    
    public final @Nullable MethodInformation cloneMethod;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected TypeInformation(@Nonnull TypeElement typeElement) {
        this.typeElement = typeElement;
        this.typeMirror = (DeclaredType) typeElement.asType();
        this.typeValidators = ProcessingUtility.getCodeGenerators(typeElement, Validator.class, AnnotationValidator.class);
        
        boolean generatable = true;
        
        final @Nonnull @NonNullableElements List<ExecutableElement> constructors = new LinkedList<>();
        final @Nonnull @NonNullableElements List<MethodInformation> methods = new LinkedList<>();
        final @Nonnull @NonNullableElements List<FieldInformation> fields = new LinkedList<>();
        
        @Nullable MethodInformation recoverMethod = null;
        @Nullable MethodInformation equalsMethod = null;
        @Nullable MethodInformation hashCodeMethod = null;
        @Nullable MethodInformation toStringMethod = null;
        @Nullable MethodInformation validateMethod = null;
        @Nullable MethodInformation compareToMethod = null;
        @Nullable MethodInformation cloneMethod = null;
        
        final @Nonnull @NonNullableElements Map<String, MethodInformation> abstractGetters = new LinkedHashMap<>();
        final @Nonnull @NonNullableElements Map<String, MethodInformation> abstractSetters = new LinkedHashMap<>();
        final @Nonnull @NonNullableElements Map<String, MethodInformation> getters = new LinkedHashMap<>();
        
        final @Nonnull @NonNullableElements Map<String, VariableElement> nonPrivateFields = new LinkedHashMap<>();
        
        final @Nonnull @NonNullableElements List<? extends Element> members = AnnotationProcessing.getElementUtils().getAllMembers(typeElement);
        for (@Nonnull Element member : members) {
            if (member.getKind() == ElementKind.CONSTRUCTOR) {
                final @Nonnull ExecutableElement constructor = (ExecutableElement) member;
                AnnotationLog.verbose("Found the constructor", SourcePosition.of(constructor));
                constructors.add(constructor);
            } else if (member.getKind() == ElementKind.METHOD) {
                final @Nonnull ExecutableElement method = (ExecutableElement) member;
                AnnotationLog.verbose("Found the method", SourcePosition.of(method));
                final @Nonnull String methodName = method.getSimpleName().toString();
                
                final @Nonnull MethodInformation methodInformation = MethodInformation.forMethod(method);
                if (methodInformation.recover) {
                    if (recoverMethod != null) {
                        AnnotationLog.error("Only one method may be annotated with '@Recover':", SourcePosition.of(method));
                    }
                    recoverMethod = methodInformation;
                }
                
                if (!methodInformation.isStatic()) {
                    if (methodInformation.isFinal()) {
                        if (methodInformation.isGetter()) {
                            getters.put(methodInformation.getFieldName(), methodInformation);
                        }
                    } else {
                        if (methodInformation.isAbstract()) {
                            if (methodName.equals("equals") && methodInformation.hasSingleParameter(Object.class)) {
                                equalsMethod = methodInformation;
                            } else if (methodName.equals("hashCode") && !methodInformation.hasParameters()) {
                                hashCodeMethod = methodInformation;
                            } else if (methodName.equals("toString") && !methodInformation.hasParameters()) {
                                toStringMethod = methodInformation;
                            } else if (methodName.equals("validate") && !methodInformation.hasParameters() && methodInformation.hasNoReturnType()) {
                                validateMethod = methodInformation;
                            } else if (methodName.equals("compareTo") && methodInformation.hasSingleParameter(typeElement.getQualifiedName().toString())) {
                                compareToMethod = methodInformation;
                            } else if (methodName.equals("clone") && !methodInformation.hasParameters()) {
                                cloneMethod = methodInformation;
                            } else {
                                if (methodInformation.isGetter()) {
                                    abstractGetters.put(methodInformation.getFieldName(), methodInformation);
                                } else if (methodInformation.isSetter()) {
                                    abstractSetters.put(methodInformation.getFieldName(), methodInformation);
                                } else {
                                    AnnotationLog.information("Cannot generate the abstract method", SourcePosition.of(method));
                                    generatable = false;
                                }
                            }
                        } else {
                            if (methodInformation.isGetter()) {
                                getters.put(methodInformation.getFieldName(), methodInformation);
                            }
                            methods.add(methodInformation);
                        }
                    }
                }
            } else if (member.getKind() == ElementKind.FIELD) {
                final @Nonnull VariableElement field = (VariableElement) member;
                AnnotationLog.verbose("Found the field", SourcePosition.of(field));
                final @Nonnull String fieldName = field.getSimpleName().toString();
                if (!field.getModifiers().contains(Modifier.PRIVATE)) {
                    nonPrivateFields.put(fieldName, field);
                }
            }
        }
        
        // TODO: Rather store the ExecutableElement so that the source position can be more accurate.
        final @Nonnull @NonNullableElements List<? extends VariableElement> parameters;
        
        if (recoverMethod != null) {
            parameters = recoverMethod.method.getParameters();
        } else if (constructors.size() == 1) {
            parameters = constructors.get(0).getParameters();
        } else {
            AnnotationLog.information("Cannot determine the fields with several constructors and no recover method:", SourcePosition.of(typeElement));
            parameters = new LinkedList<>();
            generatable = false;
        }
        
        for (@Nonnull VariableElement parameter : parameters) {
            final @Nonnull String parameterName = parameter.getSimpleName().toString();
            final @Nullable VariableElement field = nonPrivateFields.get(parameterName);
            if (field != null) {
//                fields.add(FieldInformation.forField(parameter));
            } else {
                final @Nullable MethodInformation getter = getters.get(parameterName);
                if (getter != null) {
//                    fields.add(FieldInformation.forField(parameter, getter));
                } else if (recoverMethod != null && abstractGetters.get(parameterName) == null) { // TODO: Check the condition.
                    AnnotationLog.information("Cannot access the field:", SourcePosition.of(typeElement));
                    generatable = false;
                }
            }
        }
        
        for (@Nonnull Map.Entry<String, MethodInformation> entry : abstractGetters.entrySet()) {
            final @Nonnull String getterName = entry.getKey();
            final @Nonnull MethodInformation getter = entry.getValue();
            // TODO: Add the generated fields to the fields.
        }
        
        this.generatable = generatable;
        
        this.constructors = Collections.unmodifiableList(constructors);
        this.methods = Collections.unmodifiableList(methods);
        this.fields = Collections.unmodifiableList(fields);
        
        this.recoverMethod = recoverMethod;
        this.equalsMethod = equalsMethod;
        this.hashCodeMethod = hashCodeMethod;
        this.toStringMethod = toStringMethod;
        this.validateMethod = validateMethod;
        this.compareToMethod = compareToMethod;
        this.cloneMethod = cloneMethod;
    }
    
    /**
     * Returns the type information for the given type element.
     */
    @Pure
    public static @Nonnull TypeInformation forType(@Nonnull TypeElement typeElement) {
        return new TypeInformation(typeElement);
    }
    
}
