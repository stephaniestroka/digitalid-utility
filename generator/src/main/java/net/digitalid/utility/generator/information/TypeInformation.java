package net.digitalid.utility.generator.information;

import java.util.ArrayList;
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
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.logging.processing.AnnotationProcessing;
import net.digitalid.utility.logging.processing.SourcePosition;
import net.digitalid.utility.processor.ProcessingUtility;
import net.digitalid.utility.string.QuoteString;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.meta.Validator;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.validator.AnnotationValidator;

/**
 * This class collects the relevant information about a type for generating a subclass and builder.
 * 
 * @see SubclassGenerator
 * @see BuilderGenerator
 */
public class TypeInformation {
    
    /* -------------------------------------------------- Type -------------------------------------------------- */
    
    public final @Nonnull TypeElement element;
    
    public final @Nonnull DeclaredType type;
    
    public final @Nonnull String name;
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return QuoteString.inSingle(name);
    }
    
    /* -------------------------------------------------- Package -------------------------------------------------- */
    
    public final @Nonnull PackageElement packageElement;
    
    public final @Nonnull String packageName;
    
    /* -------------------------------------------------- Subclass -------------------------------------------------- */
    
    @Pure
    public @Nonnull String getSimpleNameOfGeneratedSubclass() {
        return "Generated" + name;
    }
    
    @Pure
    public @Nonnull String getQualifiedNameOfGeneratedSubclass() {
        return packageName + "." + getSimpleNameOfGeneratedSubclass();
    }
    
    /* -------------------------------------------------- Builder -------------------------------------------------- */
    
    @Pure
    public @Nonnull String getSimpleNameOfGeneratedBuilder() {
        return name + "Builder";
    }
    
    @Pure
    public @Nonnull String getQualifiedNameOfGeneratedBuilder() {
        return packageName + "." + getSimpleNameOfGeneratedBuilder();
    }
    
    /* -------------------------------------------------- Validators -------------------------------------------------- */
    
    /**
     * Stores the validators that validate the type.
     */
    public final @Nonnull @NonNullableElements Map<AnnotationMirror, AnnotationValidator> validators;
    
    /* -------------------------------------------------- Generatable -------------------------------------------------- */
    
    public final boolean generatable;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    public final @Nonnull @NonNullableElements List<ExecutableElement> constructors;
    
    /* -------------------------------------------------- Fields -------------------------------------------------- */
    
    /**
     * Stores the parameters of the constructor, which also represent the object.
     */
    public final @Nonnull @NonNullableElements List<FieldInformation> representingFields;
    
    /**
     * Stores the accessible fields, which can be validated by the generated subclass.
     */
    public final @Nonnull @NonNullableElements List<FieldInformation> accessibleFields;
    
    /* -------------------------------------------------- Overridden Methods -------------------------------------------------- */
    
    /**
     * Stores the non-static and non-final methods that are to be overridden.
     */
    public final @Nonnull @NonNullableElements List<MethodInformation> overriddenMethods;
    
    /* -------------------------------------------------- Generated Methods -------------------------------------------------- */
    
    public final @Nullable MethodInformation recoverMethod;
    
    public final @Nullable MethodInformation equalsMethod;
    
    public final @Nullable MethodInformation hashCodeMethod;
    
    public final @Nullable MethodInformation toStringMethod;
    
    public final @Nullable MethodInformation validateMethod;
    
    public final @Nullable MethodInformation compareToMethod;
    
    public final @Nullable MethodInformation cloneMethod;
    
    // TODO: Include the freezable methods here!
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected TypeInformation(@Nonnull TypeElement element) {
        this.element = element;
        this.type = (DeclaredType) element.asType();
        this.name = element.getSimpleName().toString();
        this.packageElement = (PackageElement) element.getEnclosingElement();
        this.packageName = packageElement.getQualifiedName().toString();
        this.validators = ProcessingUtility.getCodeGenerators(element, Validator.class, AnnotationValidator.class);
        
        boolean generatable = true;
        
        final @Nonnull @NonNullableElements List<ExecutableElement> constructors = new LinkedList<>();
        final @Nonnull @NonNullableElements List<MethodInformation> overriddenMethods = new LinkedList<>();
        final @Nonnull @NonNullableElements List<FieldInformation> representingFields = new LinkedList<>();
        final @Nonnull @NonNullableElements Map<String, FieldInformation> accessibleFields = new LinkedHashMap<>();
        
        @Nullable MethodInformation recoverMethod = null;
        @Nullable MethodInformation equalsMethod = null;
        @Nullable MethodInformation hashCodeMethod = null;
        @Nullable MethodInformation toStringMethod = null;
        @Nullable MethodInformation validateMethod = null;
        @Nullable MethodInformation compareToMethod = null;
        @Nullable MethodInformation cloneMethod = null;
        
        final @Nonnull @NonNullableElements Map<String, MethodInformation> abstractGetters = new LinkedHashMap<>();
        final @Nonnull @NonNullableElements Map<String, MethodInformation> abstractSetters = new LinkedHashMap<>();
        final @Nonnull @NonNullableElements Map<String, MethodInformation> implementedGetters = new LinkedHashMap<>();
        
        final @Nonnull @NonNullableElements List<? extends Element> members = AnnotationProcessing.getElementUtils().getAllMembers(element);
        for (@Nonnull Element member : members) {
            
            if (member.getKind() == ElementKind.CONSTRUCTOR) {
                final @Nonnull ExecutableElement constructor = (ExecutableElement) member;
                AnnotationLog.verbose("Found the constructor", SourcePosition.of(constructor));
                constructors.add(constructor);
                
            } else if (member.getKind() == ElementKind.METHOD) {
                final @Nonnull ExecutableElement method = (ExecutableElement) member;
                AnnotationLog.verbose("Found the method", SourcePosition.of(method));
                final @Nonnull String methodName = method.getSimpleName().toString();
                final @Nonnull MethodInformation methodInformation = MethodInformation.forMethod(type, method);
                if (methodInformation.isRecover()) {
                    if (recoverMethod != null) {
                        AnnotationLog.error("Only one method may be annotated with '@Recover':", SourcePosition.of(method));
                    }
                    recoverMethod = methodInformation;
                }
                if (!methodInformation.isStatic() && !methodInformation.isPrivate()) {
                    if (methodInformation.isFinal()) {
                        if (methodInformation.isGetter()) {
                            implementedGetters.put(methodInformation.getFieldName(), methodInformation);
                        }
                    } else {
                        if (methodInformation.isAbstract()) {
                            if (methodName.equals("equals") && methodInformation.hasSingleParameter(Object.class)) {
                                equalsMethod = methodInformation;
                            } else if (methodName.equals("hashCode") && !methodInformation.hasParameters()) {
                                hashCodeMethod = methodInformation;
                            } else if (methodName.equals("toString") && !methodInformation.hasParameters()) {
                                toStringMethod = methodInformation;
                            } else if (methodName.equals("validate") && !methodInformation.hasParameters() && !methodInformation.hasReturnType()) {
                                validateMethod = methodInformation;
                            } else if (methodName.equals("compareTo") && methodInformation.hasSingleParameter(element.getQualifiedName().toString())) {
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
                                implementedGetters.put(methodInformation.getFieldName(), methodInformation);
                            }
                            if (!methodInformation.isPartOfRuntimeEnvironment()) {
                                overriddenMethods.add(methodInformation);
                            }
                        }
                    }
                }
                
            } else if (member.getKind() == ElementKind.FIELD) {
                final @Nonnull VariableElement field = (VariableElement) member;
                AnnotationLog.verbose("Found the field", SourcePosition.of(field));
                final @Nonnull String fieldName = field.getSimpleName().toString();
                if (!field.getModifiers().contains(Modifier.PRIVATE)) {
                    accessibleFields.put(fieldName, FieldInformation.forField(type, field));
                }
            }
        }
        
        final @Nonnull @NonNullableElements List<? extends VariableElement> parameters;
        
        if (recoverMethod != null) {
            parameters = recoverMethod.element.getParameters();
        } else if (constructors.size() == 1) {
            parameters = constructors.get(0).getParameters();
        } else {
            AnnotationLog.information("Cannot determine the representing fields with several constructors and no recover method:", SourcePosition.of(element));
            parameters = new LinkedList<>();
            generatable = false;
        }
        
        for (@Nonnull VariableElement parameter : parameters) {
            final @Nonnull String parameterName = parameter.getSimpleName().toString();
            final @Nullable FieldInformation fieldInformation = accessibleFields.get(parameterName);
            if (fieldInformation != null) {
                final @Nullable VariableElement field = fieldInformation.field;
                assert field != null : "The value is set for all accessible fields.";
                representingFields.add(FieldInformation.forField(type, parameter, field));
            } else {
                final @Nullable MethodInformation getter = implementedGetters.get(parameterName);
                if (getter != null) {
                    representingFields.add(FieldInformation.forField(type, parameter, getter));
                } else if (recoverMethod == null || abstractGetters.get(parameterName) == null) {
                    AnnotationLog.information("Can neither access a field nor a getter for the required parameter", SourcePosition.of(parameter));
                    generatable = false;
                }
            }
        }
        
        for (@Nonnull Map.Entry<String, MethodInformation> entry : abstractGetters.entrySet()) {
            final @Nonnull String fieldName = entry.getKey();
            final @Nonnull MethodInformation getter = entry.getValue();
            final @Nullable MethodInformation setter = abstractSetters.get(fieldName);
            if (setter != null) { abstractSetters.remove(fieldName); }
            representingFields.add(FieldInformation.forField(type, fieldName, getter, setter));
        }
        
        for (@Nonnull MethodInformation setter : abstractSetters.values()) {
            AnnotationLog.information("There is no corresponding getter for", SourcePosition.of(setter.element));
            generatable = false;
        }
        
        this.generatable = generatable;
        
        this.constructors = Collections.unmodifiableList(constructors);
        this.overriddenMethods = Collections.unmodifiableList(overriddenMethods);
        this.representingFields = Collections.unmodifiableList(representingFields);
        this.accessibleFields = Collections.unmodifiableList(new ArrayList<>(accessibleFields.values()));
        
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
    public static @Nonnull TypeInformation forType(@Nonnull TypeElement element) {
        return new TypeInformation(element);
    }
    
}
