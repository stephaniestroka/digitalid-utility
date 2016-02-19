package net.digitalid.utility.generator;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.ElementFilter;

import net.digitalid.utility.contracts.Ensure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.generator.information.FieldInformation;
import net.digitalid.utility.generator.information.MethodInformation;
import net.digitalid.utility.generator.information.TypeInformation;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.logging.processing.AnnotationProcessing;
import net.digitalid.utility.processor.ProcessingUtility;
import net.digitalid.utility.processor.files.JavaSourceFile;
import net.digitalid.utility.string.QuoteString;
import net.digitalid.utility.string.StringCase;
import net.digitalid.utility.string.iterable.Brackets;
import net.digitalid.utility.string.iterable.IterableConverter;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.validator.AnnotationValidator;
import net.digitalid.utility.validation.validator.GeneratedContract;

/**
 * This class generates a subclass with the provided type information.
 * 
 * @see GeneratorProcessor
 * @see TypeInformation
 */
@Mutable
public class SubclassGenerator extends JavaSourceFile {
    
    /* -------------------------------------------------- Type Information -------------------------------------------------- */
    
    protected final @Nonnull TypeInformation typeInformation;
    
    /* -------------------------------------------------- Generating Methods -------------------------------------------------- */
    
    protected void generateFields() {
        for (@Nonnull FieldInformation fieldInformation : typeInformation.representingFields) {
            if (fieldInformation.generated) {
                addSection(StringCase.capitalizeFirstLetters(StringCase.decamelize(fieldInformation.name)));
                addField("private " + (fieldInformation.isMutable() ? "" : "final ") + fieldInformation.type + " " + fieldInformation.name);
                final @Nullable MethodInformation getter = fieldInformation.getter;
                // TODO: Support method interceptors!
                if (getter != null) {
                    addAnnotation("@Override");
                    beginMethod(getter.getModifiersForOverridingMethod() + getter.methodType.getReturnType() + " " + getter.methodName + "()");
                    addStatement(importIfPossible(Log.class) + ".verbose(" + QuoteString.inDouble("The method " + getter + " was called.") + ")");
                    addStatement("final " + getter.methodType.getReturnType() + " result = " + fieldInformation.name);
                    for (@Nonnull Map.Entry<AnnotationMirror, AnnotationValidator> entry : getter.resultValidators.entrySet()) {
                        final @Nonnull GeneratedContract generatedContract = entry.getValue().generateContract(getter.methodElement, entry.getKey());
                        addStatement(importIfPossible(Ensure.class) + ".that(" + generatedContract.getCondition() + ").orThrow(" + generatedContract.getMessageInDoubleQuotes() + ")");
                    }
                    addStatement("return result");
                    endMethod();
                }
            }
        }
    }
    
    protected void generateConstructors() {
        addSection("Constructors");
        final @Nonnull @NonNullableElements List<ExecutableElement> constructors = ElementFilter.constructorsIn(typeInformation.typeElement.getEnclosedElements());
        for (@Nonnull ExecutableElement constructor : constructors) {
            final @Nonnull @NonNullableElements List<? extends VariableElement> parameters = constructor.getParameters();
            
            // TODO: Also do thrown types.
            
            beginConstructor(typeInformation.getSimpleNameOfGeneratedSubclass() + IterableConverter.toString(parameters, ProcessingUtility.DECLARATION_CONVERTER, Brackets.ROUND));
            addStatement("super" + IterableConverter.toString(parameters, ProcessingUtility.CALL_CONVERTER, Brackets.ROUND));
            endConstructor();
        }
    }
    
    protected void overrideMethods() {
        for (@Nonnull Element member : AnnotationProcessing.getElementUtils().getAllMembers(typeInformation.typeElement)) {
            if (member.getKind() == ElementKind.METHOD) {
                final @Nonnull ExecutableElement method = (ExecutableElement) member;
                final @Nonnull Set<Modifier> modifiers = method.getModifiers();
                // TODO: Maybe use Collections.disjoint(A, B) instead?
                if (!modifiers.contains(Modifier.ABSTRACT) && !modifiers.contains(Modifier.FINAL) && !modifiers.contains(Modifier.PRIVATE) && !modifiers.contains(Modifier.STATIC) && !modifiers.contains(Modifier.NATIVE)) {
//                    addComment("method.asType(): " + method.asType());
//                    addComment("method.toString(): " + method.toString());
//                    addComment("method.getReturnType(): " + method.getReturnType());
//                    addComment("method.asType().getKind(): " + method.asType().getKind());
//                    addComment("((ExecutableType) method.asType()).getTypeVariables(): " + ((ExecutableType) method.asType()).getTypeVariables());
                    
                    addAnnotation("@Override");
                    final @Nonnull ExecutableType executableType = (ExecutableType) method.asType();
                    addComment("executableType: " + executableType);
                    final @Nonnull @NonNullableElements List<? extends TypeVariable> typeVariables = executableType.getTypeVariables();
                    beginMethod(IterableConverter.toString(modifiers, " ") + (typeVariables.isEmpty() ? "" : " <" + IterableConverter.toString(typeVariables) + ">") + " " + executableType.getReturnType() + " " + method.getSimpleName() + IterableConverter.toString(method.getParameters(), ProcessingUtility.DECLARATION_CONVERTER, Brackets.ROUND) + (method.getThrownTypes().isEmpty() ? "" : " throws " + IterableConverter.toString(method.getThrownTypes())));
                    addStatement(importIfPossible(Log.class) + ".verbose(" + QuoteString.inDouble("The method " + method.getSimpleName() + " was called.") + ")");
                    addStatement((method.getReturnType().getKind() == TypeKind.VOID ? "" : "return ") + "super." + method.getSimpleName() + IterableConverter.toString(method.getParameters(), ProcessingUtility.CALL_CONVERTER, Brackets.ROUND));
                    endMethod();
                }
            }
        }
    }
    
    protected void generateMethods() {
        
    }
    
    protected void deleteThisMethod(@Nonnull TypeInformation typeInformation) {
        final @Nonnull @NonNullableElements List<? extends TypeMirror> sourceTypeArguments = typeInformation.typeMirror.getTypeArguments();
        for (@Nonnull TypeMirror typeArgument : sourceTypeArguments) {
            AnnotationLog.debugging("typeArgument.toString(): " + typeArgument.toString());
            final @Nonnull TypeVariable typeVariable = (TypeVariable) typeArgument;
            AnnotationLog.debugging("typeVariable.toString(): " + typeVariable.toString());
            AnnotationLog.debugging("typeVariable.getLowerBound(): " + typeVariable.getLowerBound());
            AnnotationLog.debugging("typeVariable.getUpperBound(): " + typeVariable.getUpperBound());
        }
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected SubclassGenerator(@Nonnull TypeInformation typeInformation) {
        super(typeInformation.getQualifiedNameOfGeneratedSubclass(), typeInformation.typeElement);
        
        this.typeInformation = typeInformation;
        
        final @Nonnull String type = typeInformation.typeMirror.toString();
        final @Nonnull @NonNullableElements List<? extends TypeParameterElement> typeParameters = typeInformation.typeElement.getTypeParameters();
        beginClass("class " + typeInformation.getSimpleNameOfGeneratedSubclass() + (typeParameters.isEmpty() ? "" : IterableConverter.toString(typeParameters, ProcessingUtility.TYPE_CONVERTER, Brackets.POINTY)) + (typeInformation.typeElement.getKind() == ElementKind.CLASS ? " extends " : " implements ") + type);
        
        generateFields();
        generateConstructors();
        overrideMethods();
        generateMethods();
        
        endClass();
    }
    
    /**
     * Generates a subclass (the target) of the given type (the source).
     */
    @Pure
    public static void generateSubclassOf(@Nonnull TypeInformation typeInformation) {
        Require.that(typeInformation.generatable).orThrow("No subclass can be generated for " + typeInformation);
        
        new SubclassGenerator(typeInformation).write();
    }
    
}
