package net.digitalid.utility.generator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;

import net.digitalid.utility.annotations.state.Unmodifiable;
import net.digitalid.utility.conversion.annotations.Recover;
import net.digitalid.utility.functional.fixes.Brackets;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.generator.exceptions.FailedClassGenerationException;
import net.digitalid.utility.generator.information.ElementInformation;
import net.digitalid.utility.generator.information.method.ConstructorInformation;
import net.digitalid.utility.generator.information.type.ClassInformation;
import net.digitalid.utility.generator.information.type.TypeInformation;
import net.digitalid.utility.generator.information.variable.VariableElementInformation;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processor.generator.JavaFileGenerator;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Chainable;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * The Builder Generator generates a builder class for a given type (through the type information). 
 * For each required field, an interface is generated which holds the setter for the required field. 
 * The setter method is the field's name prefixed with "with".
 * An inner class, which represents the builder, implements all interfaces and therefore also implements
 * all setters of the required fields. Optional fields are added to the inner class only. 
 * A static method of one of the required fields, or an optional field if no required fields exist, is created
 * to provide an entrance method to the builder and sets the appropriate field in the builder. If neither required not optional fields exist, a static 
 * get() method is generated, which returns the builder without calling any further methods.
 */
@Utility
public class BuilderGenerator extends JavaFileGenerator {
    
    /* -------------------------------------------------- Type Information -------------------------------------------------- */
    
    /**
     * Holds information about the type.
     */
    private final @Nonnull TypeInformation typeInformation;
    
    /* -------------------------------------------------- Constructor Parameters -------------------------------------------------- */
    
    /**
     * Return parameters required for the construction of the class.
     */
    public @Nonnull FiniteIterable<VariableElementInformation> getConstructorParameters() {
        final @Nonnull FiniteIterable<VariableElementInformation> constructorParameters;
        if (typeInformation instanceof ClassInformation) {
            final ClassInformation classInformation = (ClassInformation) typeInformation;
            @Unmodifiable @Nonnull final FiniteIterable<@Nonnull ConstructorInformation> constructors = classInformation.getConstructors();
            @Nonnull ConstructorInformation constructorInformation;
            if (constructors.size() > 1) {
                try {
                    constructorInformation = constructors.findUnique(constructor -> constructor.hasAnnotation(Recover.class));
                } catch (NoSuchElementException e) {
                    throw FailedClassGenerationException.with("Multiple constructors found, but non is marked with @Recover.", SourcePosition.of(typeInformation.getElement()));
                }
            } else {
                constructorInformation = constructors.getFirst();
            }
            constructorParameters = constructorInformation.getParameters().map(parameter -> (VariableElementInformation) parameter).combine(typeInformation.generatedRepresentingFieldInformation);
        } else {
            constructorParameters = typeInformation.generatedRepresentingFieldInformation.map(field -> field);
        }
        return constructorParameters;
    }
    
    /* -------------------------------------------------- Required Fields Builder -------------------------------------------------- */
    
    /**
     * Returns the name of the field builder interface for a given field.
     */
    private @Nonnull String getNameOfFieldBuilder(@Nonnull ElementInformation field) {
        return Strings.capitalizeFirstLetters(field.getName()) + typeInformation.getSimpleNameOfGeneratedBuilder();
    }
    
    /**
     * Creates an interface for a given required field. The only declared method is a setter
     * for the field. The name is generated through the field name prefixed with "with".
     * 
     * @param nextInterface The field setter returns an object with this type, which is the interface
     *                      of the next required field. If no further required field must be set, the 
     *                      actual builder is returned. Thus, unless the caller casts the returned type
     *                      away, this method allows to traverse through all required fields before being
     *                      able to call "build()" on the builder.
     */
    private @Nonnull String createInterfaceForField(@Nonnull ElementInformation field, @Nullable String nextInterface) {
        final @Nonnull String interfaceName = getNameOfFieldBuilder(field);
        final @Nonnull String methodName = "with" + Strings.capitalizeFirstLetters(field.getName());
        beginInterface("public interface " + interfaceName + importWithBounds(typeInformation.getTypeArguments()));
        addAnnotation(Chainable.class);
        addMethodDeclaration("public @" + importIfPossible(Nonnull.class) + " " + nextInterface + " " + methodName + "(" + importIfPossible(field.getType()) + " " + field.getName() + ")");
        endInterface();
        return interfaceName;
    }
    
    /**
     * Returns true if the field is required, false otherwise.
     */
    // TODO: Why is this method not declared in the field information class?
    private boolean isFieldRequired(@Nonnull VariableElementInformation fieldInformation) {
        // TODO: nullable fields are not required. Non-final fields are also not required (and probably not representing).
        return !fieldInformation.hasDefaultValue();
    }
    
    /**
     * Returns a list of field information objects for fields that are required.
     */
    // TODO: improve exception handling
    private @Nonnull @NonNullableElements FiniteIterable<VariableElementInformation> getRequiredFields() {
        return getConstructorParameters().filter(this::isFieldRequired);
    }
    
    /**
     * Declares and implements the setter for the given field with the given return type and the given returned instance.
     */
    private void addSetterForField(@Nonnull ElementInformation field, @Nonnull String returnType, @Nonnull String returnedInstance) {
        final @Nonnull String methodName = "with" + Strings.capitalizeFirstLetters(field.getName());
        beginMethod("public static " + importWithBounds(typeInformation.getTypeArguments()) + returnType + " " + methodName + "(" + importIfPossible(field.getType()) + " " + field.getName() + ")");
        addStatement("return new " + returnedInstance + "()." + methodName + "(" + field.getName() + ")");
        endMethod();
    }
    
    protected final @Nonnull @NonNullableElements List<String> createInterfacesForRequiredFields(@Nonnull @NonNullableElements FiniteIterable<VariableElementInformation> requiredFields, @Nonnull String nameOfInnerClass) {
        final @Nonnull List<String> listOfInterfaces = new ArrayList<>();
        
        for (int i = 0; i < requiredFields.size(); i++) {
            final @Nonnull ElementInformation requiredField = requiredFields.get(i);
            @Nonnull String nextInterface = nameOfInnerClass;
            if ((i + 1) < requiredFields.size()) {
                final @Nonnull ElementInformation nextField = requiredFields.get(i + 1);
                nextInterface = getNameOfFieldBuilder(nextField);
            }
            listOfInterfaces.add(createInterfaceForField(requiredField, nextInterface));
        }
        return listOfInterfaces;
    }
    
    /**
     * Creates a builder that collects all fields and provides a build() method, which returns an instance of the type that the builder builds.
     */
    // TODO: improve exception handling
    protected void createInnerClassForFields(@Nonnull String nameOfBuilder, @Nonnull @NonNullableElements List<String> interfacesForRequiredFields) throws FailedClassGenerationException {
        ProcessingLog.debugging("createInnerClassForFields()");
        
        final @Nonnull FiniteIterable<@Nonnull TypeVariable> typeArguments = typeInformation.getTypeArguments();
        beginClass("public static class " + nameOfBuilder + importWithBounds(typeArguments) + (interfacesForRequiredFields.isEmpty() ? "" : " implements " + FiniteIterable.of(interfacesForRequiredFields).join() + typeArguments.join(Brackets.POINTY, "")));
        
        for (@Nonnull VariableElementInformation field : getConstructorParameters()) {
            field.getAnnotations();
            addSection(Strings.capitalizeFirstLetters(Strings.decamelize(field.getName())));
            // TODO: Add annotations.
            addField("private " + importIfPossible(field.getType()) + " " + field.getName() + " = " + field.getDefaultValue());
            final @Nonnull String methodName = "with" + Strings.capitalizeFirstLetters(field.getName());
            if (isFieldRequired(field)) {
                addAnnotation(Override.class);
            }
            addAnnotation(Chainable.class);
            beginMethod("public @" + importIfPossible(Nonnull.class) + " " + nameOfBuilder + " " + methodName + "(" + importIfPossible(field.getType()) + " " + field.getName() + ")");
            addStatement("this." + field.getName() + " = " + field.getName());
            addStatement("return this");
            endMethod();
        }
        
        addSection("Build");
    
        @Nonnull final FiniteIterable<ConstructorInformation> constructors = typeInformation.getConstructors();
        if (constructors.size() > 1) {
            throw FailedClassGenerationException.with("Cannot handle multiple constructors in builder.", SourcePosition.of(typeInformation.getElement()));
        }
        @Nonnull Set<TypeMirror> throwTypes = new HashSet<>();
        for (@Nonnull ConstructorInformation constructorInformation : constructors) {
            if (constructorInformation.throwsExceptions()) {
                throwTypes.addAll(constructorInformation.getElement().getThrownTypes());
            }
        }
        beginMethod("public " + typeInformation.getName() + " build()" + (throwTypes.isEmpty() ? "" : " throws " + FiniteIterable.of(throwTypes).map(this::importIfPossible).join()));
        
        final @Nonnull FiniteIterable<VariableElementInformation> constructorParameters = getConstructorParameters();
        
        final @Nonnull String nameOfConstructor;
        if (typeInformation.hasAnnotation(GenerateSubclass.class)) {
            nameOfConstructor = typeInformation.getSimpleNameOfGeneratedSubclass();
        } else {
            nameOfConstructor = typeInformation.getName();
        }
        addStatement("return new " + nameOfConstructor + constructorParameters.map(ElementInformation::getName).join(Brackets.ROUND));
        
        endMethod();
        
        endClass();
    }
    
    /**
     * Creates a static method that serves as an entry to the builder. If required fields exist,
     * the static method returns the builder and sets the required field. It returns the interface
     * of the next required field, if another one exists. If it is the only required field, the 
     * return type is the builder, which makes calling "build()" possible.
     * If no required fields exits, static entry methods for all optional fields are created. Additionally,
     * a static "get()" method is created, which returns the new builder instance without calling any additional builder setters.
     */
    // TODO: improve exception handling
    protected void createStaticEntryMethod(@Nonnull String nameOfBuilder, @Nonnull @NonNullableElements FiniteIterable<VariableElementInformation> requiredFields, @Nonnull @NonNullableElements List<String> interfacesForRequiredFields) {
        if (requiredFields.size() > 0) {
            final @Nonnull ElementInformation entryField = requiredFields.get(0);
            final @Nonnull String secondInterface;
            if (interfacesForRequiredFields.size() > 1) {
                secondInterface = interfacesForRequiredFields.get(1);
            } else {
                secondInterface = nameOfBuilder;
            }
            addSetterForField(entryField, secondInterface, nameOfBuilder);
        } else {
            for (@Nonnull ElementInformation optionalField : getConstructorParameters()) {
                addSetterForField(optionalField, nameOfBuilder, nameOfBuilder);
            }
            beginMethod("public static " + importWithBounds(typeInformation.getTypeArguments()) + nameOfBuilder + " get()");
            addStatement("return new " + nameOfBuilder + "()");
            endMethod();
        }
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Constructs a new builder generator for the given type information. The builder generator prepares the Java source file that 
     * is generated.
     */
    protected BuilderGenerator(@Nonnull TypeInformation typeInformation) {
        super(typeInformation.getQualifiedNameOfGeneratedBuilder(), typeInformation.getElement());
        ProcessingLog.debugging("BuilderGenerator(" + typeInformation + ")");
        
        this.typeInformation = typeInformation;
        
        beginClass("public class " + typeInformation.getSimpleNameOfGeneratedBuilder() + importWithBounds(typeInformation.getTypeArguments()));
        
        final @Nonnull @NonNullableElements FiniteIterable<VariableElementInformation> requiredFields = getRequiredFields();
        final @Nonnull String nameOfBuilder = "Inner" + typeInformation.getSimpleNameOfGeneratedBuilder();

        final @Nonnull @NonNullableElements List<String> interfacesForRequiredFields = createInterfacesForRequiredFields(requiredFields, nameOfBuilder);
        createInnerClassForFields(nameOfBuilder, interfacesForRequiredFields);
        createStaticEntryMethod(nameOfBuilder, requiredFields, interfacesForRequiredFields);
        
        endClass();
    }
    
    /**
     * Generates a new builder class for the given type information.
     */
    public static void generateBuilderFor(@Nonnull TypeInformation typeInformation) {
        ProcessingLog.debugging("generateBuilderFor(" + typeInformation + ")");
        new BuilderGenerator(typeInformation).write();
    }
    
}
