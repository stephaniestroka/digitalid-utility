package net.digitalid.utility.logging.processing;

import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.contracts.Require;

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
    
    /* -------------------------------------------------- Default Constructor -------------------------------------------------- */
    
    /**
     * Returns whether the given non-nullable element is a class with a public default constructor.
     * 
     * @require element != null : "The element may not be null.";
     */
    public static boolean hasPublicDefaultConstructor(Element element) {
        Require.that(element != null).orThrow("The element may not be null.");
        
        for (ExecutableElement constructor : ElementFilter.constructorsIn(element.getEnclosedElements())) {
            AnnotationLog.verbose("Found the constructor", SourcePosition.of(constructor));
            if (constructor.getParameters().isEmpty() && constructor.getModifiers().contains(Modifier.PUBLIC)) { return true; }
        }
        AnnotationLog.debugging("Found no public default constructor in", SourcePosition.of(element));
        return false;
    }
    
    /* -------------------------------------------------- Annotation Mirror -------------------------------------------------- */
    
    /**
     * Returns the annotation mirror corresponding to the given non-nullable annotation type of the given non-nullable element.
     * If no such annotation mirror is found, this method logs an error message and returns null.
     * 
     * @require element != null : "The element may not be null.";
     * @require annotation != null : "The annotation may not be null.";
     */
    public static AnnotationMirror getAnnotationMirror(Element element, Class<? extends Annotation> annotation) {
        Require.that(element != null).orThrow("The element may not be null.");
        Require.that(annotation != null).orThrow("The annotation may not be null.");
        
        for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            final TypeElement annotationElement = (TypeElement) annotationMirror.getAnnotationType().asElement();
            AnnotationLog.verbose("Found the annotation '@" + annotationElement.getSimpleName() + "' on", SourcePosition.of(element));
            if (annotationElement.getQualifiedName().contentEquals(annotation.getCanonicalName())) { return annotationMirror; }
        }
        AnnotationLog.error("Found no annotation '@" + annotation.getSimpleName() + "' on", SourcePosition.of(element));
        return null;
    }
    
    /* -------------------------------------------------- Fields of Type -------------------------------------------------- */
    
    /**
     * Returns a list of all the fields with the given non-nullable type in the given non-nullable class.
     * 
     * @require classElement != null : "The class element may not be null.";
     * @require fieldType != null : "The field type may not be null.";
     */
    public static List<VariableElement> getFieldsOfType(TypeElement classElement, Class<?> fieldType) {
        Require.that(classElement != null).orThrow("The class element may not be null.");
        Require.that(fieldType != null).orThrow("The field type may not be null.");
        
        final List<VariableElement> fields = new LinkedList<>();
        for (VariableElement field : ElementFilter.fieldsIn(classElement.getEnclosedElements())) {
            String fieldTypeName = field.asType().toString();
            if (fieldTypeName.contains("<")) { fieldTypeName = fieldTypeName.substring(0, fieldTypeName.indexOf('<')); }
            AnnotationLog.verbose("Found with the type '" + fieldTypeName + "' the field", SourcePosition.of(field));
            if (fieldTypeName.equals(fieldType.getCanonicalName())) { fields.add(field); }
        }
        return fields;
    }
    
    /**
     * Returns the unique, public and static field with the given non-nullable type in the given non-nullable class.
     * If no field fulfilling all these criteria is found, this method logs a warning message and returns null. 
     * 
     * @require classElement != null : "The class element may not be null.";
     * @require fieldType != null : "The field type may not be null.";
     */
    public static VariableElement getUniquePublicStaticFieldOfType(TypeElement classElement, Class<?> fieldType) {
        final List<VariableElement> fields = getFieldsOfType(classElement, fieldType);
        if (fields.size() != 1) {
            AnnotationLog.warning("There is not exactly one field of type '" + fieldType.getCanonicalName()+ "' in the class", SourcePosition.of(classElement));
        } else {
            final VariableElement field = fields.get(0);
            if (!fields.get(0).getModifiers().contains(Modifier.PUBLIC)) {
                AnnotationLog.warning("The field of type '" + fieldType.getCanonicalName()+ "' has to be public:", SourcePosition.of(field));
            } else if (!fields.get(0).getModifiers().contains(Modifier.STATIC)) {
                AnnotationLog.warning("The field of type '" + fieldType.getCanonicalName()+ "' has to be static:", SourcePosition.of(field));
            } else {
                return field;
            }
        }
        return null;
    }
    
}
