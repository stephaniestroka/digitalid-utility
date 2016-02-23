package net.digitalid.utility.generator;

import java.util.List;

import javax.annotation.Nonnull;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.ElementFilter;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.generator.information.FieldInformation;
import net.digitalid.utility.generator.information.TypeInformation;
import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.processor.ProcessingUtility;
import net.digitalid.utility.processor.generator.JavaFileGenerator;
import net.digitalid.utility.string.StringCase;
import net.digitalid.utility.functional.string.Brackets;
import net.digitalid.utility.functional.string.IterableConverter;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
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
    
    /**
     * Creates an inner class for required fields that returns an OptionalFields_Builder once all required fields are set.
     */
    protected void createInnerClassForRequiredFields() {
        beginClass("class RequiredFields" + typeInformation.getSimpleNameOfGeneratedBuilder() + " extends " + typeInformation.getSimpleNameOfGeneratedBuilder());
    
        addSection(StringCase.capitalizeFirstLetters("Check Required Fields"));
        addAnnotation("@Chainable");
        // TODO: the nullable is really stupid here. There must be a better way to signal that not all fields have been set.
        beginMethod("public @Nullable OptionalFields" + typeInformation.getSimpleNameOfGeneratedBuilder() + " check()");
        final @Nonnull StringBuilder condition = new StringBuilder();
        boolean firstRound = true;
        for (@Nonnull FieldInformation fieldInformation : typeInformation.representingFields) {
            if (fieldInformation.defaultValue == null) {
                if (!firstRound) {
                    condition.append(" && ");
                }
                condition.append("this.").append(fieldInformation).append(" != null");
                firstRound = false;
            }
        }
         if (condition.toString().isEmpty()) {
             condition.append("true");
         }
        
        beginIf(condition.toString());
        addStatement("return new OptionalFields" + typeInformation.getSimpleNameOfGeneratedBuilder() + "(this)");
        beginElse();
        // TODO: Think of something more clever.
        addStatement("return null");
        endIf();
        endMethod();
        
        for (@Nonnull FieldInformation fieldInformation : typeInformation.representingFields) {
            // If the default value is not set, the field is required.
            if (fieldInformation.defaultValue == null) {
                addSection(StringCase.capitalizeFirstLetters(StringCase.decamelize(fieldInformation.name)));
    
                final @Nonnull String type;
                if (fieldInformation.type.getKind().isPrimitive()) {
                    // Boxing the type into an object, so that we can check whether it has been set in the allRequiredFieldsAreSet() method.
                    if (fieldInformation.type.toString().equals("byte")) {
                        type = "Byte";
                    } else if (fieldInformation.type.toString().equals("char")) {
                        type = "Character";
                    } else if (fieldInformation.type.toString().equals("short")) {
                        type = "Short";
                    } else if (fieldInformation.type.toString().equals("int")) {
                        type = "Integer";
                    } else if (fieldInformation.type.toString().equals("long")) {
                        type = "Long";
                    } else if (fieldInformation.type.toString().equals("float")) {
                        type = "Float";
                    } else if (fieldInformation.type.toString().equals("double")) {
                        type = "Double";
                    } else {
                        AnnotationLog.error("primitive type '" + fieldInformation.type.toString() + "' cannot be boxed.");
                        return;
                    }
                } else {
                    type = fieldInformation.type.toString();
                }
                addField("protected " + type + " " + fieldInformation.name);
                final @Nonnull String methodName = "with" + StringCase.capitalizeFirstLetters(fieldInformation.name);
                addAnnotation("@Chainable");
                beginMethod("public @Nonnull RequiredFields" + typeInformation.getSimpleNameOfGeneratedBuilder() + " " + methodName + "(" + fieldInformation.type + " " + fieldInformation.name + ")");
                addStatement("this." + fieldInformation.name + " = " + fieldInformation.name);
                addStatement("return this");
                endMethod();
            }
        }
        endClass();
    }
    
    /* -------------------------------------------------- Optional Fields Builder -------------------------------------------------- */
    
    /**
     * Creates an inner class for optional fields that returns an OptionalFields_Builder once all required fields are set.
     */
    protected void createInnerClassForOptionalFields() {
        beginClass("class OptionalFields" + typeInformation.getSimpleNameOfGeneratedBuilder());
    
        addSection(StringCase.capitalizeFirstLetters("Constructor"));
        beginConstructor("OptionalFields" + typeInformation.getSimpleNameOfGeneratedBuilder() + "(this)");
        for (@Nonnull FieldInformation fieldInformation : typeInformation.representingFields) {
            if (fieldInformation.defaultValue == null) {
                addStatement("this." + fieldInformation.name + " = " + fieldInformation.name);
            } else {
                addStatement("this." + fieldInformation.name + " = " + fieldInformation.defaultValue);
            }
        }
        endConstructor();
        
        addSection(StringCase.capitalizeFirstLetters("Build"));
        // TODO: check if typeName is correct here
        beginMethod("public " + typeInformation.name + " build()");
    
        final @Nonnull @NonNullableElements List<ExecutableElement> constructors = ElementFilter.constructorsIn(typeInformation.element.getEnclosedElements());
        if (constructors.size() != 1) {
            AnnotationLog.error("Expected one constructor in generated type:");
        }
        final @Nonnull ExecutableElement constructor = constructors.get(0);
        
        addStatement("return new " + typeInformation.getSimpleNameOfGeneratedBuilder() + IterableConverter.toString(constructor.getParameters(), ProcessingUtility.DECLARATION_CONVERTER, Brackets.ROUND));
        endMethod();
        
        for (@Nonnull FieldInformation fieldInformation : typeInformation.representingFields) {
            if (fieldInformation.defaultValue != null) {
                addSection(StringCase.capitalizeFirstLetters(StringCase.decamelize(fieldInformation.name)));
    
                addField("private " + fieldInformation.type + " " + fieldInformation.name);
                final @Nonnull String methodName = "with" + StringCase.capitalizeFirstLetters(fieldInformation.name);
                addAnnotation("@Chainable");
                beginMethod("public @Nonnull OptionalFields" + typeInformation.getSimpleNameOfGeneratedBuilder() + " " + methodName + "(" + fieldInformation.type + " " + fieldInformation.name + ")");
                addStatement("this." + fieldInformation.name + " = " + fieldInformation.name);
                addStatement("return this");
                endMethod();
            }
        }
        endClass();
    }
    
    /* -------------------------------------------------- Static Entry Methods -------------------------------------------------- */
    
    /**
     * Creates static entry methods for all required fields. If no fields are required, creates a "get" method that returns an OptionalFields_Builder.
     */
    protected void createStaticEntryMethods() {
        // TODO: sort out only the required fields.
        boolean atLeastOneMethodCreated = false;
        for (@Nonnull FieldInformation fieldInformation : typeInformation.representingFields) {
            if (fieldInformation.defaultValue == null) {
                atLeastOneMethodCreated = true;
                addSection(StringCase.capitalizeFirstLetters(StringCase.decamelize(fieldInformation.name)));
                final @Nonnull String methodName = "with" + StringCase.capitalizeFirstLetters(fieldInformation.name);
                beginMethod("public static void " + methodName + "(" + fieldInformation.type + " " + fieldInformation.name + ")");
                addStatement("return new RequiredFields" + typeInformation.getSimpleNameOfGeneratedBuilder() + "()." + methodName + "(" + fieldInformation.name + ")");
                endMethod();
            }
        }
        if (!atLeastOneMethodCreated) {
            addSection(StringCase.capitalizeFirstLetters(StringCase.decamelize("Construct Builder")));
            beginMethod("public static void get" + "()");
            addStatement("return new OptionalFields" + typeInformation.getSimpleNameOfGeneratedBuilder() + "()");
            endMethod();
        }
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Constructs a new builder generator for the given type information. The builder generator prepares the Java source file that 
     * is generated 
     */
    protected BuilderGenerator(@Nonnull TypeInformation typeInformation) {
        super(typeInformation.getQualifiedNameOfGeneratedSubclass(), typeInformation.element);
    
        this.typeInformation = typeInformation;
        
        beginClass("class " + typeInformation.getSimpleNameOfGeneratedBuilder() + importingTypeVisitor.reduceTypeVariablesWithBoundsToString(typeInformation.type.getTypeArguments()));
        
        createInnerClassForRequiredFields();
        createInnerClassForOptionalFields();
        createStaticEntryMethods();
        
        endClass();
    }
    
    /**
     * Generates a new builder class for the given type information.
     */
    public static void generateBuilderFor(@Nonnull TypeInformation typeInformation) {
        Require.that(typeInformation.generatable).orThrow("No subclass can be generated for " + typeInformation);
        
        new BuilderGenerator(typeInformation).write();
    }
    
}
