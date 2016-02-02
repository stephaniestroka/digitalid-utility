package net.digitalid.utility.logging.processing;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import net.digitalid.utility.configuration.Configuration;

/**
 * This class provides the environment for annotation processing.
 */
public class AnnotationProcessing {
    
    /* -------------------------------------------------- Environment -------------------------------------------------- */
    
    /**
     * Stores the processing environment of the current annotation processor.
     */
    public static final Configuration<ProcessingEnvironment> environment = Configuration.withUnknownProvider();
    
    /* -------------------------------------------------- Shortcuts -------------------------------------------------- */
    
    /**
     * Returns an implementation of some utility methods for operating on elements.
     */
    public static Elements getElementUtils() {
        return environment.get().getElementUtils();
    }
    
    /**
     * Returns an implementation of some utility methods for operating on types.
     */
    public static Types getTypeUtils() {
        return environment.get().getTypeUtils();
    }
    
    /* -------------------------------------------------- Binary Name -------------------------------------------------- */
    
    private static String getBinaryName(TypeElement typeElement, String className) {
        final Element enclosingElement = typeElement.getEnclosingElement();
        if (enclosingElement.getKind() == ElementKind.PACKAGE) {
            final PackageElement packageElement = (PackageElement) enclosingElement;
            if (packageElement.isUnnamed()) { return className; }
            return packageElement.getQualifiedName() + "." + className;
        } else {
            final TypeElement enclosingTypeElement = (TypeElement) enclosingElement;
            return getBinaryName(enclosingTypeElement, enclosingTypeElement.getSimpleName() + "$" + className);
        }
    }
    
    /**
     * Returns the fully-qualified binary name of the given type element (for example {@code net.digitalid.A$B} instead of {@code net.digitalid.A.B}).
     */
    public static String getBinaryName(TypeElement typeElement) {
        return getBinaryName(typeElement, typeElement.getSimpleName().toString());
    }
    
}
