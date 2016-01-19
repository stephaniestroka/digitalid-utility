package net.digitalid.utility.initialization.processor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import net.digitalid.utility.initialization.annotations.EnsureInitialization;
import net.digitalid.utility.initialization.annotations.Initialize;
import net.digitalid.utility.validation.state.Pure;

/**
 * Description.
 */
@SupportedOptions({"dependency"})
public class Processor extends AbstractProcessor {
    
//    @Override
//    public synchronized void init(ProcessingEnvironment env) {
//        
//    }
    
    @Override
    public boolean process(@Nonnull Set<? extends TypeElement> annotations, @Nonnull RoundEnvironment roundEnvironment) {
        
        // TODO: Clean up!
        
        /*Map<String, String> options = processingEnv.getOptions();
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Number of options: " + options.keySet().size());
        for (String option : options.keySet()) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, option + ": " + options.get(option));
        }
        
        try {
            Class<?> logger = Class.forName("net.digitalid.utility.logging.DefaultLogger");
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Class found: " + logger.getCanonicalName());
        } catch (ClassNotFoundException ex) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Class not found: " + ex);
        }*/
        
        for (final @Nonnull Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(Initialize.class)) {
//            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Help! (This is the encapsulated annotation processor.)", annotatedElement);
            
            if (annotatedElement.getKind() == ElementKind.METHOD && annotatedElement.getModifiers().contains(Modifier.STATIC)) {
                final @Nonnull Name methodName = annotatedElement.getSimpleName();
                final @Nonnull Element enclosingElement = annotatedElement.getEnclosingElement();
                if (enclosingElement.getKind() == ElementKind.CLASS) {
                    final @Nonnull TypeElement typeElement = (TypeElement) enclosingElement;
                    final @Nonnull Name className = typeElement.getQualifiedName();
                    try {
                        final @Nonnull JavaFileObject f = processingEnv.getFiler().createSourceFile("net.digitalid.utility.initialization.initializer.Initializer");
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Creating " + f.toUri());
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
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, x.toString());
                    }
//                    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Call " + className + "." + methodName + "() in the initializer.", annotatedElement);
                    
                } else {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "The annotation @Initialize can only be used on static methods in non-nested classes.", annotatedElement);
                }
            } else {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "The annotation @Initialize can only be used on static methods.", annotatedElement);
            }
        }
        
        return true;
    }
    
    @Pure
    @Override
    public @Nonnull Set<String> getSupportedAnnotationTypes() {
        final @Nonnull Set<String> supportedAnnotationTypes = new HashSet<>();
        supportedAnnotationTypes.add(Initialize.class.getCanonicalName());
        supportedAnnotationTypes.add(EnsureInitialization.class.getCanonicalName());
        return supportedAnnotationTypes;
    }
    
    @Pure
    @Override
    public @Nonnull SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
    
}
