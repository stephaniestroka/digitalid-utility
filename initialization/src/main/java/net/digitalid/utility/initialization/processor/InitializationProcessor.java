package net.digitalid.utility.initialization.processor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Modifier;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import net.digitalid.utility.initialization.annotations.Initialize;
import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.logging.processing.AnnotationProcessing;
import net.digitalid.utility.logging.processing.SourcePosition;
import net.digitalid.utility.processor.CustomProcessor;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;

/**
 * Description.
 * 
 * @Initialize can take a target class (e.g. IdentityResolver.class or SQLDialect.class), which is initialized, or depend on the initialization of a certain class. (The target defaults to null, which is considered as initializing the surrounding class itself.)
 * @Initialize(target = Directory.class, dependencies = {Logger.class})
 * 
 * @Configurable for classes that have static configure(...) and isConfigured() methods, the latter of which is/can be checked as a precondition for methods annotated with @Configured. What about default configurations (e.g. in the case of the logger or the directory)?
 * @Configurable(required = false) [defaults to true] – or no such parameter, simply check whether an isConfigured() method exists. Add 'dependencies' as a potential parameter?
 * Introduce a Configuration interface [or rather abstract class] for a service loader with methods void add(Initializer), [void addDependency(Configuration, Initializer), boolean hasDependency(Configuration), Class<?> getSource()], boolean isConfigured(), void configure() [runs all associated initializers after ensuring that all dependencies are configured].
 */
@SupportedAnnotationTypes("net.digitalid.utility.initialization.annotations.Initialize")
public class InitializationProcessor extends CustomProcessor {
    
    /* -------------------------------------------------- Processing -------------------------------------------------- */
    
    @Override
    public void processFirstRound(@Nonnull @NonNullableElements Set<? extends TypeElement> annotations, @Nonnull RoundEnvironment roundEnvironment) {
        for (@Nonnull Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(Initialize.class)) {
            AnnotationLog.debugging("Kind: " + annotatedElement.getKind() + ", Modifiers: " + annotatedElement.getModifiers(), SourcePosition.of(annotatedElement));
            if (annotatedElement.getKind() == ElementKind.METHOD && annotatedElement.getModifiers().contains(Modifier.STATIC)) {
                final @Nonnull Name methodName = annotatedElement.getSimpleName();
                final @Nonnull Element enclosingElement = annotatedElement.getEnclosingElement();
                if (enclosingElement.getKind() == ElementKind.CLASS) {
                    final @Nonnull TypeElement typeElement = (TypeElement) enclosingElement;
                    final @Nonnull Name className = typeElement.getQualifiedName();
                    try {
                        final @Nonnull JavaFileObject f = AnnotationProcessing.environment.get().getFiler().createSourceFile("net.digitalid.utility.initialization.initializer.Initializer");
                        AnnotationLog.information("Creating " + f.toUri());
                        try (final @Nonnull Writer w = f.openWriter()) {
                            final @Nonnull PrintWriter pw = new PrintWriter(w);
                            pw.println("package net.digitalid.utility.initialization.initializer;");
                            pw.println("/** The initializer initializes the library. */");
                            pw.println("public final class Initializer {");
                            pw.println("    /** Initializes the library. */");
                            pw.println("    public static void initialize() {");
                            pw.println("        " + className + "." + methodName + "();");
                            pw.println("    }");
//                            TypeMirror type = e.asType();
//                            pw.println("    /** Handle something. */");
//                            pw.println("    protected final void handle" + name  + "(" + type + " value) {");
//                            pw.println("        System.out.println(value);");
//                            pw.println("    }");
                            pw.println("}");
                            pw.flush();
                        }
                    } catch (IOException x) {
                        AnnotationLog.error(x.toString());
                    }
//                    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Call " + className + "." + methodName + "() in the initializer.", annotatedElement);
                    
                } else {
                    AnnotationLog.information("The annotation @Initialize can only be used on static methods in non-nested classes.", SourcePosition.of(annotatedElement));
                }
            } else {
                AnnotationLog.information("The annotation @Initialize can only be used on static methods.", SourcePosition.of(annotatedElement));
            }
        }
    }
    
}
