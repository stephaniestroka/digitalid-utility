/*
 * Copyright (C) 2017 Synacts GmbH, Switzerland (info@synacts.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.digitalid.utility.generator.generators;

import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.circumfixes.Brackets;
import net.digitalid.utility.circumfixes.Quotes;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.GeneratorProcessor;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.generator.information.ElementInformation;
import net.digitalid.utility.generator.information.ElementInformationImplementation;
import net.digitalid.utility.generator.information.field.DirectlyAccessibleFieldInformation;
import net.digitalid.utility.generator.information.field.FieldInformation;
import net.digitalid.utility.generator.information.field.GeneratedFieldInformation;
import net.digitalid.utility.generator.information.field.GeneratedRepresentingFieldInformation;
import net.digitalid.utility.generator.information.filter.MethodSignatureMatcher;
import net.digitalid.utility.generator.information.method.ConstructorInformation;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.generator.information.type.ClassInformation;
import net.digitalid.utility.generator.information.type.InterfaceInformation;
import net.digitalid.utility.generator.information.type.TypeInformation;
import net.digitalid.utility.generator.interceptor.MethodInterceptor;
import net.digitalid.utility.generator.interceptor.MethodUtility;
import net.digitalid.utility.generator.typevisitors.GenerateComparisonTypeVisitor;
import net.digitalid.utility.generator.typevisitors.GenerateHashCodeTypeVisitor;
import net.digitalid.utility.generator.typevisitors.GenerateToStringTypeVisitor;
import net.digitalid.utility.generator.utility.OrderOfAssignmentComparator;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processor.generator.JavaFileGenerator;
import net.digitalid.utility.rootclass.RootClassWithException;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.tuples.Quartet;
import net.digitalid.utility.tuples.Triplet;
import net.digitalid.utility.validation.annotations.generation.Derive;
import net.digitalid.utility.validation.annotations.generation.Normalize;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.processing.AnnotationHandlerUtility;
import net.digitalid.utility.validation.validator.MethodAnnotationValidator;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 * This class generates a subclass with the provided type information.
 * 
 * @see GeneratorProcessor
 * @see TypeInformation
 */
@Mutable
public class SubclassGenerator extends JavaFileGenerator {
    
    /* -------------------------------------------------- Type Information -------------------------------------------------- */
    
    /**
     * Stores the type information.
     */
    protected final @Nonnull TypeInformation typeInformation;
    
    /* -------------------------------------------------- Generating Methods -------------------------------------------------- */
    
    private void generateMethodWithStatement(@Nonnull MethodInformation method, @Nonnull String statement) {
        generateMethodWithStatement(method, statement, null, null);
    }
    
    private void generateMethodWithStatement(@Nonnull MethodInformation method, @Nonnull String statement, @Nullable String returnedValue, @Nullable String defaultValue) {
        Require.that(!method.hasReturnType() || returnedValue != null).orThrow("Trying to generate the method $ with return type, but a return variable was not given");
        final @Nonnull String firstMethodCall = implementCallToMethodInterceptors(method, statement, returnedValue, defaultValue);
        addAnnotation(Override.class);
        MethodUtility.generateBeginMethod(this, method, null);
        for (@Nonnull VariableElement parameter : method.getElement().getParameters()) {
            for (Map.@Nonnull Entry<AnnotationMirror, ValueAnnotationValidator> entry : AnnotationHandlerUtility.getValueValidators(parameter).entrySet()) {
                addPrecondition(entry.getValue().generateContract(parameter, entry.getKey(), this));
            }
        }
        for (Map.@Nonnull Entry<AnnotationMirror, MethodAnnotationValidator> entry : method.getMethodValidators().entrySet()) {
            addPrecondition(entry.getValue().generateContract(method.getElement(), entry.getKey(), this));
        }
        addStatement(firstMethodCall);
        for (Map.@Nonnull Entry<AnnotationMirror, ValueAnnotationValidator> entry : method.getReturnValueValidators().entrySet()) {
            addPostcondition(entry.getValue().generateContract(method.getElement(), entry.getKey(), this));
        }
        if (method.hasReturnType()) {
            addStatement("return " + returnedValue);
        }
        endMethod();
    }
    
    /* -------------------------------------------------- Generating Fields -------------------------------------------------- */
    
    /**
     * Generates fields from abstract getters.
     */
    private void generateFields() {
        @Nonnull FiniteIterable<GeneratedFieldInformation> generatedFieldInformation = typeInformation.generatedRepresentingFieldInformation.map(f -> (GeneratedFieldInformation) f).combine(typeInformation.derivedFieldInformation);
        for (@Nonnull GeneratedFieldInformation field : generatedFieldInformation) {
            ProcessingLog.verbose("Generating the field $.", field.getName());
            addSection(Strings.capitalizeFirstLetters(Strings.decamelize(field.getName())));
            addField("private " + (field.isMutable() ? "" : "final ") + importIfPossible(field.getType()) + " " + field.getName());
            
            @Nullable Normalize normalize = null;
            {
                final @Nonnull MethodInformation getter = field.getGetter();
                ProcessingLog.verbose("Implementing the getter $.", getter.getName());
                generateMethodWithStatement(getter, "this." + field.getName(), "result", field.getDefaultValue());
                if (getter.hasAnnotation(Normalize.class)) {
                    normalize = getter.getAnnotation(Normalize.class);
                }
            }
            
            if (field.hasSetter()) {
                final @Nonnull MethodInformation setter = field.getNonNullSetter();
                ProcessingLog.verbose("Implementing the setter " + Quotes.inSingle(setter.getName()));
                if (normalize != null && setter.hasAnnotation(Normalize.class)) {
                    ProcessingLog.warning("Found @Normalize annotation on getter and on setter. To avoid inconsistencies we are using the getter annotation. Please remove @Normalize from the setter.");
                } else if (normalize == null) {
                    if (setter.hasAnnotation(Normalize.class)) {
                        normalize = setter.getAnnotation(Normalize.class);
                        ProcessingLog.warning("Found @Normalize annotation on setter. To avoid inconsistencies, please move it from the setter to the getter.");
                    }
                }
                final @Nonnull List<? extends VariableElement> parameters = setter.getElement().getParameters();
                Require.that(parameters.size() == 1).orThrow("Found a setter with " + (parameters.isEmpty() ? "zero " : "more than one ") + "parameters.");
                final @Nonnull VariableElement parameter = parameters.get(0);
                final @Nonnull String statement;
                if (normalize != null) {
                    statement = ("this." + field.getName() + " = " + normalize.value());
                } else {
                    statement = ("this." + field.getName() + " = " + parameter.getSimpleName());
                }
                generateMethodWithStatement(setter, statement);
            }
        }
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    private boolean shouldGenerateValidateCall() {
        return (typeInformation instanceof ClassInformation && ((ClassInformation) typeInformation).validateMethod != null);
    }
    
    private boolean shouldGenerateInitializeCall() {
        return ProcessingUtility.isRawlyAssignable(typeInformation.getElement(), RootClassWithException.class);
    }
    
    /**
     * Generates a constructor, calls the super constructor if available and initializes generated fields.
     */
    private void generateConstructor(@Nonnull String constructorDeclaration, @Nullable String superStatement) {
        @Nonnull String modifier = "";
        if (typeInformation.getAnnotation(GenerateSubclass.class).makePublic()) {
            modifier = "public ";
        }
        beginConstructor(modifier + constructorDeclaration);
        if (superStatement != null) {
            addStatement(superStatement);
            addEmptyLine();
        }
        for (@Nonnull GeneratedRepresentingFieldInformation field : typeInformation.generatedRepresentingFieldInformation) {
            if (field.hasAnnotation(Normalize.class)) {
                addStatement("this." + field.getName() + " = " + field.getAnnotation(Normalize.class).value());
            } else if (field.hasSetter() && field.getSetter().hasAnnotation(Normalize.class)) {
                addStatement("this." + field.getName() + " = " + field.getSetter().getAnnotation(Normalize.class).value());
            } else {
                addStatement("this." + field.getName() + " = " + field.getName());
            }
        }
        for (@Nonnull FieldInformation field : typeInformation.derivedFieldInformation.sorted(OrderOfAssignmentComparator.INSTANCE)) {
            addStatement("this." + field.getName() + " = " + field.getAnnotation(Derive.class).value());
        }
        if (shouldGenerateInitializeCall()) {
            addStatement("initialize()");
        }
        if (shouldGenerateValidateCall()) {
            addStatement("validate()");
        }
        endConstructor();
    }
    
    private @Nonnull FiniteIterable<@Nonnull ? extends TypeMirror> getThrownTypesOfInitializeMethod() {
        return typeInformation.getOverriddenMethods().findUnique(MethodSignatureMatcher.of("initialize")).getThrownTypes();
    }
    
    /**
     * Generates a subclass constructor from a given constructor information object;
     */
    private void generateConstructor(@Nonnull ConstructorInformation constructorInformation) {
        final @Nullable List<@Nonnull ? extends TypeMirror> thrownTypes = constructorInformation.getElement().getThrownTypes();
        @Nonnull FiniteIterable<@Nonnull TypeMirror> throwTypes = thrownTypes == null ? FiniteIterable.of() : FiniteIterable.of(thrownTypes);
        if (shouldGenerateInitializeCall()) {
            throwTypes = throwTypes.combine(getThrownTypesOfInitializeMethod());
        }
        
        final @Nonnull FiniteIterable<@Nonnull ElementInformation> constructorParameter = constructorInformation.getParameters().map(parameter -> (ElementInformation) parameter).combine(typeInformation.generatedRepresentingFieldInformation);
        
        final @Nonnull String superStatement = "super" + constructorInformation.getParameters().map(ElementInformationImplementation::getName).join(Brackets.ROUND);
        
        generateConstructor(typeInformation.getSimpleNameOfGeneratedSubclass() + constructorParameter.map(element -> this.importIfPossible(element.getType()) + " " + element.getName()).join(Brackets.ROUND) + (throwTypes.isEmpty() ? "" : " throws " + throwTypes.map(this::importIfPossible).join()), superStatement);
    }
    
    /**
     * Generates all subclass constructors.
     */
    protected void generateConstructors() {
        addSection("Constructors");
        if (typeInformation instanceof ClassInformation) {
            for (@Nonnull ConstructorInformation constructor : typeInformation.getConstructors()) {
                generateConstructor(constructor);
            }
        } else if (typeInformation instanceof InterfaceInformation) {
            generateConstructor(typeInformation.getSimpleNameOfGeneratedSubclass() + typeInformation.generatedRepresentingFieldInformation.map(element -> this.importIfPossible(element.getType()) + " " + element.getName()).join(Brackets.ROUND), null);
        }
    }
    
    /* -------------------------------------------------- Overridden Methods -------------------------------------------------- */
    
    /**
     * Generates interceptor methods for all method interceptor annotations of a given method.
     * The first method interceptor calls the original method, which is handed as the parameter methodCall. Further interceptors call the immediate predecessor. The call to the last interceptor is returned.
     */
    private @Nonnull String implementCallToMethodInterceptors(@Nonnull MethodInformation method, @Nonnull String methodCall, @Nullable String returnedValue, @Nullable String defaultValue) {
        for (Map.@Nonnull Entry<AnnotationMirror, MethodInterceptor> annotationMirrorMethodInterceptorEntry : method.getMethodInterceptors().entrySet()) {
            final @Nonnull MethodInterceptor methodInterceptor = annotationMirrorMethodInterceptorEntry.getValue();
            methodInterceptor.generateFieldsRequiredByMethod(this, method, typeInformation);
            methodCall = methodInterceptor.generateInterceptorMethod(this, method, methodCall, returnedValue, defaultValue);
        }
        if (method.hasReturnType()) {
            methodCall = method.getReturnType(this) + " " + returnedValue + " = " + methodCall;
        }
        return methodCall;
    }
    
    /**
     * Overrides the methods of the superclass and generates contracts and/or method interceptors according to the method annotations.
     */
    private void overrideMethods() {
        if (!typeInformation.getOverriddenMethods().isEmpty()) { addSection("Overridden Methods"); }
        for (final @Nonnull MethodInformation method : typeInformation.getOverriddenMethods()) {
            if (ProcessingUtility.getSurroundingType(method.getElement()).getQualifiedName().toString().startsWith("net.digitalid")) {
                final @Nonnull String callToSuperMethod = MethodUtility.createSuperCall(method);
                generateMethodWithStatement(method, callToSuperMethod, "result", method.getDefaultValue());
            }
        }
    }
    
    /**
     * Implements the methods by adding code via the method interceptor with which the methods were annotated.
     */
    private void implementMethods() {
        if (!typeInformation.getOverriddenMethods().isEmpty()) { addSection("Implement Methods"); }
        for (final @Nonnull MethodInformation method : typeInformation.generatedMethods) {
            generateMethodWithStatement(method, "", "result", method.getDefaultValue());
        }
    }
    
    /* -------------------------------------------------- Generate RootInterface Methods -------------------------------------------------- */
    
    /**
     * A type visitor that surrounds character sequences in double quotes and prints arrays correctly.
     */
    private static final @Nonnull GenerateToStringTypeVisitor generateToStringTypeVisitor = new GenerateToStringTypeVisitor();
    
    /**
     * Generates a toString method from all representing fields.
     */
    private void generateToStringMethod() {
        if (typeInformation instanceof ClassInformation && ((ClassInformation) typeInformation).toStringMethod != null) {
            return;
        }
        addAnnotation(Pure.class);
        addAnnotation(Override.class);
        beginMethod("public String toString()");
        addStatement("return \"" + typeInformation.getName() + typeInformation.getRepresentingFieldInformation().map(field -> field.getName() + ": \" + " + generateToStringTypeVisitor.visit(field.getType(), Pair.of(field.getAccessCode(), this)) + " + \"").join(Brackets.ROUND) + "\"");
        endMethod();
    }
    
    /**
     * Generates a validate method that validates invariants of the type.
     */
    private void generateValidateMethod() {
        ProcessingLog.debugging("generateValidateMethod()");
        if (typeInformation instanceof ClassInformation) {
            final @Nonnull ClassInformation classInformation = (ClassInformation) typeInformation;
            if(classInformation.validateMethod != null) {
                addAnnotation(Pure.class);
                addAnnotation(Override.class);
                beginMethod("public void validate()");
                addStatement(MethodUtility.createSuperCall(classInformation.validateMethod));
                for (@Nonnull DirectlyAccessibleFieldInformation field : classInformation.writableAccessibleFields) {
                    for (Map.@Nonnull Entry<AnnotationMirror, ValueAnnotationValidator> entry : AnnotationHandlerUtility.getValueValidators(field.getElement()).entrySet()) {
                        addInvariant(entry.getValue().generateContract(field.getElement(), entry.getKey(), this));
                    }
                }
                endMethod();
            }
        }
    }
    
    /**
     * A hashCode type visitor.
     */
    private final @Nonnull GenerateHashCodeTypeVisitor generateHashCodeTypeVisitor = new GenerateHashCodeTypeVisitor();
    
    /**
     * Generates a hashCode method that generate a hashCode from all representing fields.
     */
    protected void generateHashCodeMethod() {
        if (typeInformation instanceof ClassInformation && ((ClassInformation) typeInformation).hashCodeMethod != null) {
            return;
        }
        beginJavadoc();
        addJavadoc("Computes and returns the hash code of this object, using the fields of the class which are handed to the class via the recovery method or the constructor.");
        endJavadoc();
        addAnnotation(Pure.class);
        addAnnotation(Override.class);
        beginMethod("public int hashCode()");
        addStatement("int prime = 92_821");
        addStatement("int result = 46_411");
        // TODO: Primitive types are auto-boxed right now. Is the hash of an auto-boxed value always the same?
        final @Nonnull FiniteIterable<FieldInformation> representingFieldInformation = typeInformation.getRepresentingFieldInformation();
        for (@Nonnull FieldInformation field : representingFieldInformation) {
            generateHashCodeTypeVisitor.visit(field.getType(), Triplet.of(field.getAccessCode(), this, "result"));
        }
        addStatement("return result");
        endMethod();
    }
    
    /**
     * A comparison type visitor.
     */
    private static final @Nonnull GenerateComparisonTypeVisitor generateComparisonTypeVisitor = new GenerateComparisonTypeVisitor();
    
    /**
     * Generates an equal method that compares all representing fields.
     */
    protected void generateEqualsMethod() {
        if (typeInformation instanceof ClassInformation && ((ClassInformation) typeInformation).equalsMethod != null) {
            return;
        }
        beginJavadoc();
        addJavadoc("Returns false if the given object is not equal to the current object, using the heuristics that fields of a class, which are handed to the class via the recovery method or the constructor, must be equal to each other, so that the objects are considered equal.");
        endJavadoc();
        addAnnotation(Pure.class);
        addAnnotation(Override.class);
        beginMethod("public boolean equals(@" + importIfPossible(Nullable.class) + " Object object)");
        beginIf("object == this");
        addStatement("return true");
        endIf();
        beginIf("object == null || !(object instanceof " + typeInformation.getName() + ")");
        addStatement("return false");
        endIf();
        addStatement("final @" + importIfPossible(Nonnull.class)+ " " + typeInformation.getName() + " that = (" + typeInformation.getName() + ") object");
        addStatement("boolean result = true");
        final @Nonnull FiniteIterable<FieldInformation> representingFieldInformation = typeInformation.getRepresentingFieldInformation();
        for (@Nonnull FieldInformation field : representingFieldInformation) {
            final @Nonnull String accessCode = field.getAccessCode();
            // TODO: Maybe we should compare primitive values directly instead of boxing them.
            generateComparisonTypeVisitor.visit(field.getType(), Quartet.of("this." + accessCode, "that." + accessCode, this, "result"));
        }
        addStatement("return result");
        endMethod();
    }
    
    /**
     * Generates a compare-to method. This method should be implemented by the super class if the
     * comparison algorithm is more complex and if the fields of the type do not implement Comparable.
     */
    protected void generateCompareToMethod() {
        if (ProcessingUtility.isRawSubtype(typeInformation.getElement(), Comparable.class)) {
            if (typeInformation instanceof ClassInformation && ((ClassInformation) typeInformation).compareToMethod != null) {
                return;
            }
            // only generated if the compareTo method is defined somewhere, but not yet implemented
            addAnnotation(Pure.class);
            addAnnotation(Override.class);
            beginMethod("public int compareTo(@" + importIfPossible(Nonnull.class) + " " + typeInformation.getName() + " other)");
            final @Nonnull FiniteIterable<FieldInformation> representingFieldInformation = typeInformation.getRepresentingFieldInformation();
            addStatement("int result = 0");
            for (int i = 0; i < representingFieldInformation.size(); i++) {
                final @Nonnull FieldInformation field = representingFieldInformation.get(i);
                long magnitude = (10 * (representingFieldInformation.size() - i)) / 10;
                if (ProcessingUtility.isPrimitive(field.getType())) {
                    addStatement("result += " + Brackets.inRound(field.getAccessCode() + " - other." + field.getAccessCode()) + " * " + magnitude);
                } else {
                    addStatement("result += " + field.getAccessCode() + ".compareTo(other." + field.getAccessCode() + ") * " + magnitude);
                }
            }
            addStatement("return result");
            endMethod();
        }
    }
    
    /**
     * Generates all root interface methods
     */
    private void generateMethods() {
        addSection("Generated Methods");
        generateEqualsMethod();
        generateHashCodeMethod();
        generateToStringMethod();
        generateValidateMethod();
        generateCompareToMethod();
    }
    
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Generates a subclass of the given type.
     */
    protected SubclassGenerator(@Nonnull TypeInformation typeInformation) {
        super(typeInformation.getQualifiedNameOfGeneratedSubclass(), typeInformation.getElement());
        
        this.typeInformation = typeInformation;
        Require.that(typeInformation.getAnnotation(GenerateSubclass.class) != null).orThrow("The SubclassGenerator should not have been called if the annotation @GenerateSubclass is missing.");
        @Nonnull String modifier = "";
        if (typeInformation.getAnnotation(GenerateSubclass.class).makePublic()) {
            modifier = "public ";
        }
        beginClass(modifier + "class " + typeInformation.getSimpleNameOfGeneratedSubclass() + importWithBounds(typeInformation.getTypeArguments()) + (typeInformation.getElement().getKind() == ElementKind.CLASS ? " extends " : " implements ") + importIfPossible(typeInformation.getType()));

        generateFields();
        generateConstructors();
        overrideMethods();
        implementMethods();
        generateMethods();

        endClass();
    }
    
    /**
     * Generates a subclass of the given type and writes it to a file.
     */
    @Pure
    public static void generateSubclassOf(@Nonnull TypeInformation typeInformation) {
        new SubclassGenerator(typeInformation).write();
    }
    
}

/*
// TODO: Generate this implementation instead.
public abstract class FreezableObject implements FreezableInterface {
    
    private boolean frozen = false;
    
    @Pure
    @Override
    public final boolean isFrozen() {
        return frozen;
    }
    
    @Override
    public @Nonnull @Frozen ReadOnlyInterface freeze() {
        frozen = true;
        return this;
    }
    
    @Pure
    @Override
    public abstract @Capturable @Nonnull @NonFrozen FreezableObject clone();
    
}
*/
