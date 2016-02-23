package net.digitalid.utility.generator;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.functional.string.IterableConverter;
import net.digitalid.utility.generator.information.field.FieldInformation;
import net.digitalid.utility.generator.information.type.TypeInformation;
import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.logging.processing.AnnotationProcessing;
import net.digitalid.utility.processor.generator.JavaFileGenerator;
import net.digitalid.utility.string.StringCase;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.reference.Chainable;
import net.digitalid.utility.validation.annotations.type.Utiliy;

/**
 * The Builder Generator generates a builder class for a given type (through the type information). 
 * Required fields of the type, which are those without a @DefaultValue annotation, are collected in the inner class "RequiredFields[name_of_the_type]Builder".
 * Once all required fields are set, an OptionalFields[name_of_the_type]Builder is returned, which allows to set optional fields and build an object of the type.
 */
@Utiliy
public class BuilderGenerator extends JavaFileGenerator {
    
    /* -------------------------------------------------- Type Information -------------------------------------------------- */
    
    private final @Nonnull TypeInformation typeInformation;
    
    /* -------------------------------------------------- Required Fields Builder -------------------------------------------------- */
    
    private @Nonnull String getNameOfFieldBuilder(@Nonnull FieldInformation field) {
        return StringCase.capitalizeFirstLetters(field.name) + typeInformation.getSimpleNameOfGeneratedBuilder();
    }
    
    private @Nonnull String createInterfaceForField(@Nonnull FieldInformation field, @Nullable String nextInterface) {
        final @Nonnull String interfaceName = getNameOfFieldBuilder(field);
        final @Nonnull String methodName = "with" + StringCase.capitalizeFirstLetters(field.name);
        beginClass("interface " + interfaceName + importingTypeVisitor.mapTypeVariablesWithBoundsToStrings(typeInformation.type.getTypeArguments()));
        addAnnotation("@" + importIfPossible(Chainable.class));
        addMethodDeclaration("public @" + importIfPossible(Nonnull.class) + " " + nextInterface + " " + methodName + "(" + field.type + " " + field.name + ")");
        endClass();
        return interfaceName;
    }
    
    private boolean isFieldRequired(@Nonnull FieldInformation fieldInformation) {
        // TODO: nullable fields are not required. Non-final fields are also not required (and probably not representing).
        return fieldInformation.defaultValue == null;
    }
    
    /**
     * Returns a list of field information objects for fields that are required.
     */
    private @Nonnull @NonNullableElements List<FieldInformation> getRequiredFields() {
        final @Nonnull @NonNullableElements List<FieldInformation> requiredFields = new ArrayList<>();
        for (@Nonnull FieldInformation fieldInformation : typeInformation.representingFields) {
            if (isFieldRequired(fieldInformation)) {
                requiredFields.add(fieldInformation);
            }
        }
        return requiredFields;
    }
    
    private void addSetterForField(@Nonnull FieldInformation field, @Nonnull String returnType, @Nonnull String returnedInstance) {
        final @Nonnull String methodName = "with" + StringCase.capitalizeFirstLetters(field.name);
        beginMethod("public static " + importingTypeVisitor.mapTypeVariablesWithBoundsToStrings(typeInformation.type.getTypeArguments()) + returnType + " " + methodName + "(" + field.type + " " + field.name + ")");
        addStatement("return new " + returnedInstance + "()." + methodName + "(" + field.name + ")");
        endMethod();
    }
    
    /**
     * Creates an inner class for required fields that returns an OptionalFields_Builder once all required fields are set.
     */
    protected void createInnerClassForFields() {
        
        AnnotationLog.debugging("createInnerClassForFields()");
        
        @Nonnull @NonNullableElements List<FieldInformation> requiredFields = getRequiredFields();
        final @Nonnull String nameOfInnerClass = "Inner" + typeInformation.getSimpleNameOfGeneratedBuilder();
        final @Nonnull List<String> listOfInterfaces = new ArrayList<>();
        
        for (int i = 0; i < requiredFields.size(); i++) {
            final @Nonnull FieldInformation requiredField = requiredFields.get(i);
            @Nonnull String nextInterface = nameOfInnerClass;
            if ((i + 1) < requiredFields.size()) {
                final @Nonnull FieldInformation nextField = requiredFields.get(i + 1);
                nextInterface = getNameOfFieldBuilder(nextField);
            }
            listOfInterfaces.add(createInterfaceForField(requiredField, nextInterface));
        }
        
        final List<? extends TypeMirror> typeArguments = typeInformation.type.getTypeArguments();
        
        beginClass("static class " + nameOfInnerClass + importingTypeVisitor.mapTypeVariablesWithBoundsToStrings(typeArguments) + (listOfInterfaces.size() == 0 ? "" : " implements " + IterableConverter.toString(listOfInterfaces) + importingTypeVisitor.getTypeVariablesWithoutBounds(typeArguments, false)));
        
        for (@Nonnull FieldInformation field : typeInformation.representingFields) {
            field.getAnnotations();
            addSection(StringCase.capitalizeFirstLetters(StringCase.decamelize(field.name)));
            // TODO: Add annotations.
            addField("private " + field.type + " " + field.name);
            final @Nonnull String methodName = "with" + StringCase.capitalizeFirstLetters(field.name);
            if (isFieldRequired(field)) {
                addAnnotation("@" + importIfPossible(Override.class));
            }
            addAnnotation("@" + importIfPossible(Chainable.class));
            beginMethod("public @" + importIfPossible(Nonnull.class) + " " + nameOfInnerClass + " " + methodName + "(" + field.type + " " + field.name + ")");
            addStatement("this." + field.name + " = " + field.name);
            addStatement("return this");
            endMethod();
        }
        
        addSection("Build");
        beginMethod("public " + typeInformation.name + " build()");
        
        final @Nonnull @NonNullableElements List<ExecutableElement> constructors = ElementFilter.constructorsIn(typeInformation.element.getEnclosedElements());
        if (constructors.size() != 1) {
            AnnotationLog.error("Expected one constructor in generated type:");
        }
        final @Nonnull ExecutableElement constructor = constructors.get(0);
        
        final @Nonnull ExecutableType type = (ExecutableType) AnnotationProcessing.getTypeUtils().asMemberOf(typeInformation.type, constructor);
        addStatement("return new " + typeInformation.getQualifiedNameOfGeneratedSubclass() + importingTypeVisitor.reduceParametersDeclarationToString(type, constructor));
        
        endMethod();
        
        endClass();
        
        if (listOfInterfaces.size() > 0) {
            final @Nonnull String secondInterface;
            if (listOfInterfaces.size() > 1) {
                secondInterface = listOfInterfaces.get(1);
            } else {
                secondInterface = nameOfInnerClass;
            }
            final @Nonnull FieldInformation field = requiredFields.get(0);
            addSetterForField(field, secondInterface, nameOfInnerClass);
        } else {
            if (typeInformation.representingFields.size() > 0) {
                final @Nonnull FieldInformation field = typeInformation.representingFields.get(0);
                addSetterForField(field, nameOfInnerClass, nameOfInnerClass);
            }
            beginMethod("public static " + importingTypeVisitor.mapTypeVariablesWithBoundsToStrings(typeArguments) + nameOfInnerClass + " get()");
            addStatement("return new " + nameOfInnerClass + "()");
            endMethod();
        }
        
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Constructs a new builder generator for the given type information. The builder generator prepares the Java source file that 
     * is generated 
     */
    protected BuilderGenerator(@Nonnull TypeInformation typeInformation) {
        super(typeInformation.getQualifiedNameOfGeneratedBuilder(), typeInformation.element);
        AnnotationLog.debugging("BuilderGenerator(" + typeInformation + ")");
    
        this.typeInformation = typeInformation;
    
        beginClass("public class " + typeInformation.getSimpleNameOfGeneratedBuilder() + importingTypeVisitor.reduceTypeVariablesWithBoundsToString(typeInformation.type.getTypeArguments()));
        
        createInnerClassForFields();
        
        endClass();
        AnnotationLog.debugging("endClass()");
    }
    
    /**
     * Generates a new builder class for the given type information.
     */
    public static void generateBuilderFor(@Nonnull TypeInformation typeInformation) {
        Require.that(typeInformation.generatable).orThrow("No subclass can be generated for " + typeInformation);
        
        AnnotationLog.debugging("generateBuilderFor(" + typeInformation + ")");
        new BuilderGenerator(typeInformation).write();
    }
    
}
