package net.digitalid.utility.generator;

import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
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

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.generator.information.TypeInformation;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.logging.processing.AnnotationProcessing;
import net.digitalid.utility.processor.ProcessingUtility;
import net.digitalid.utility.processor.files.JavaSourceFile;
import net.digitalid.utility.string.QuoteString;
import net.digitalid.utility.string.iterable.Brackets;
import net.digitalid.utility.string.iterable.IterableConverter;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.type.Utiliy;

/**
 * This class generates a subclass with the provided type information.
 * 
 * @see GeneratorProcessor
 * @see TypeInformation
 */
@Utiliy
public class SubclassGenerator {
    
    /**
     * Generates a subclass (the target) of the given class element (the source).
     */
    public static void generateSubclassOf(@Nonnull TypeInformation typeInformation) {
        Require.that(typeInformation.generatable).orThrow("No subclass can be generated for " + typeInformation);
        
        final @Nonnull String simpleTargetClassName = "Generated" + typeInformation.typeName;
        final @Nonnull String qualifiedTargetClassName = typeInformation.packageName + "." + simpleTargetClassName;
        final @Nonnull JavaSourceFile javaSourceFile = JavaSourceFile.forClass(qualifiedTargetClassName, typeInformation.typeElement);
        
        final @Nonnull @NonNullableElements List<? extends TypeMirror> sourceTypeArguments = typeInformation.typeMirror.getTypeArguments();
        for (@Nonnull TypeMirror typeArgument : sourceTypeArguments) {
            AnnotationLog.debugging("typeArgument.toString(): " + typeArgument.toString());
            final @Nonnull TypeVariable typeVariable = (TypeVariable) typeArgument;
            AnnotationLog.debugging("typeVariable.toString(): " + typeVariable.toString());
            AnnotationLog.debugging("typeVariable.getLowerBound(): " + typeVariable.getLowerBound());
            AnnotationLog.debugging("typeVariable.getUpperBound(): " + typeVariable.getUpperBound());
        }
        
        final @Nonnull String type = typeInformation.typeMirror.toString();
        final @Nonnull @NonNullableElements List<? extends TypeParameterElement> typeParameters = typeInformation.typeElement.getTypeParameters();
        javaSourceFile.beginClass((typeInformation.typeElement.getKind().isInterface() || typeInformation.typeElement.getModifiers().contains(Modifier.ABSTRACT) ? "abstract " : "") + "class " + simpleTargetClassName + (typeParameters.isEmpty() ? "" : IterableConverter.toString(typeParameters, ProcessingUtility.TYPE_CONVERTER, Brackets.POINTY)) + (typeInformation.typeElement.getKind() == ElementKind.CLASS ? " extends " : " implements ") + type);
        
        final @Nonnull @NonNullableElements List<ExecutableElement> constructors = ElementFilter.constructorsIn(typeInformation.typeElement.getEnclosedElements());
        for (@Nonnull ExecutableElement constructor : constructors) {
            final @Nonnull @NonNullableElements List<? extends VariableElement> parameters = constructor.getParameters();
            
            // TODO: Also do thrown types.
            
            javaSourceFile.beginConstructor(simpleTargetClassName + IterableConverter.toString(parameters, ProcessingUtility.DECLARATION_CONVERTER, Brackets.ROUND));
            javaSourceFile.addStatement("super" + IterableConverter.toString(parameters, ProcessingUtility.CALL_CONVERTER, Brackets.ROUND));
            javaSourceFile.endConstructor();
        }
        
        for (@Nonnull Element member : AnnotationProcessing.getElementUtils().getAllMembers(typeInformation.typeElement)) {
            if (member.getKind() == ElementKind.METHOD) {
                final @Nonnull ExecutableElement method = (ExecutableElement) member;
                final @Nonnull Set<Modifier> modifiers = method.getModifiers();
                // TODO: Maybe use Collections.disjoint(A, B) instead?
                if (!modifiers.contains(Modifier.ABSTRACT) && !modifiers.contains(Modifier.FINAL) && !modifiers.contains(Modifier.PRIVATE) && !modifiers.contains(Modifier.STATIC) && !modifiers.contains(Modifier.NATIVE)) {
//                    javaSourceFile.addComment("method.asType(): " + method.asType());
//                    javaSourceFile.addComment("method.toString(): " + method.toString());
//                    javaSourceFile.addComment("method.getReturnType(): " + method.getReturnType());
//                    javaSourceFile.addComment("method.asType().getKind(): " + method.asType().getKind());
//                    javaSourceFile.addComment("((ExecutableType) method.asType()).getTypeVariables(): " + ((ExecutableType) method.asType()).getTypeVariables());
                    
                    javaSourceFile.addAnnotation("@Override");
                    final @Nonnull ExecutableType executableType = (ExecutableType) method.asType();
                    javaSourceFile.addComment("executableType: " + executableType);
                    final @Nonnull @NonNullableElements List<? extends TypeVariable> typeVariables = executableType.getTypeVariables();
                    javaSourceFile.beginMethod(IterableConverter.toString(modifiers, " ") + (typeVariables.isEmpty() ? "" : " <" + IterableConverter.toString(typeVariables) + ">") + " " + executableType.getReturnType() + " " + method.getSimpleName() + IterableConverter.toString(method.getParameters(), ProcessingUtility.DECLARATION_CONVERTER, Brackets.ROUND) + (method.getThrownTypes().isEmpty() ? "" : " throws " + IterableConverter.toString(method.getThrownTypes())));
                    javaSourceFile.addStatement(javaSourceFile.importIfPossible(Log.class) + ".verbose(" + QuoteString.inDouble("The method " + method.getSimpleName() + " was called.") + ")");
                    javaSourceFile.addStatement((method.getReturnType().getKind() == TypeKind.VOID ? "" : "return ") + "super." + method.getSimpleName() + IterableConverter.toString(method.getParameters(), ProcessingUtility.CALL_CONVERTER, Brackets.ROUND));
                    javaSourceFile.endMethod();
                }
            }
        }
        
//        javaSourceFile.addAnnotation("@Override");
//        javaSourceFile.beginMethod("public " + subclassName + " clone()");
//        javaSourceFile.addStatement("return null");
//        javaSourceFile.endMethod();
        
        javaSourceFile.endClass();
        
        javaSourceFile.write();
    }
    
}
