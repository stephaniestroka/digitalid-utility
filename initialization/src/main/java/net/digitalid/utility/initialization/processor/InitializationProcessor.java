package net.digitalid.utility.initialization.processor;

import javax.annotation.processing.SupportedOptions;

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
@SupportedOptions({"dependency"})
public class InitializationProcessor {
    
//    @Override
//    public synchronized void init(ProcessingEnvironment env) {
//        
//    }
    
//    @Override
//    public boolean process(@Nonnull Set<? extends TypeElement> annotations, @Nonnull RoundEnvironment roundEnvironment) {
//        
//        // TODO: Clean up!
//        
//        /*Map<String, String> options = processingEnv.getOptions();
//        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Number of options: " + options.keySet().size());
//        for (String option : options.keySet()) {
//            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, option + ": " + options.get(option));
//        }
//        
//        try {
//            Class<?> logger = Class.forName("net.digitalid.utility.logging.DefaultLogger");
//            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Class found: " + logger.getCanonicalName());
//        } catch (ClassNotFoundException ex) {
//            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Class not found: " + ex);
//        }*/
//        
//        for (final @Nonnull Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(Initialize.class)) {
////            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Help! (This is the encapsulated annotation processor.)", annotatedElement);
//            
//            if (annotatedElement.getKind() == ElementKind.METHOD && annotatedElement.getModifiers().contains(Modifier.STATIC)) {
//                final @Nonnull Name methodName = annotatedElement.getSimpleName();
//                final @Nonnull Element enclosingElement = annotatedElement.getEnclosingElement();
//                if (enclosingElement.getKind() == ElementKind.CLASS) {
//                    final @Nonnull TypeElement typeElement = (TypeElement) enclosingElement;
//                    final @Nonnull Name className = typeElement.getQualifiedName();
//                    try {
//                        final @Nonnull JavaFileObject f = processingEnv.getFiler().createSourceFile("net.digitalid.utility.initialization.initializer.Initializer");
//                        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Creating " + f.toUri());
//                        try (final @Nonnull Writer w = f.openWriter()) {
//                            final @Nonnull PrintWriter pw = new PrintWriter(w);
//                            pw.println("package net.digitalid.utility.initialization.initializer;");
//                            pw.println("/** The initializer initializes the library. */");
//                            pw.println("public final class Initializer {");
//                            pw.println("    /** Initializes the library. */");
//                            pw.println("    public static void initialize() {");
//                            pw.println("        " + className + "." + methodName + "();");
//                            pw.println("    }");
////                            TypeMirror type = e.asType();
////                            pw.println("    /** Handle something. */");
////                            pw.println("    protected final void handle" + name  + "(" + type + " value) {");
////                            pw.println("        System.out.println(value);");
////                            pw.println("    }");
//                            pw.println("}");
//                            pw.flush();
//                        }
//                    } catch (IOException x) {
//                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, x.toString());
//                    }
////                    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Call " + className + "." + methodName + "() in the initializer.", annotatedElement);
//                    
//                } else {
//                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "The annotation @Initialize can only be used on static methods in non-nested classes.", annotatedElement);
//                }
//            } else {
//                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "The annotation @Initialize can only be used on static methods.", annotatedElement);
//            }
//        }
//        
//        return true;
//    }
//    
//    @Pure
//    @Override
//    public @Nonnull Set<String> getSupportedAnnotationTypes() {
//        final @Nonnull Set<String> supportedAnnotationTypes = new HashSet<>();
//        supportedAnnotationTypes.add(Initialize.class.getCanonicalName());
//        supportedAnnotationTypes.add(EnsureInitialization.class.getCanonicalName());
//        return supportedAnnotationTypes;
//    }
//    
//    @Pure
//    @Override
//    public @Nonnull SourceVersion getSupportedSourceVersion() {
//        return SourceVersion.latestSupported();
//    }
//    
}
