package net.digitalid.utility.processor;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.logging.processing.AnnotationProcessing;
import net.digitalid.utility.logging.processing.SourcePosition;

/**
 * This annotation processor generates the "META-INF.services" entry for other annotation processors.
 */
@SupportedAnnotationTypes("javax.annotation.processing.SupportedAnnotationTypes")
public class ProcessorProcessor extends CustomProcessor {
    
    /* -------------------------------------------------- Processing -------------------------------------------------- */
    
    @Override
    public boolean processFirstRound(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        final @Nonnull ProcessingEnvironment processingEnvironment = AnnotationProcessing.environment.get();
        final @Nonnull TypeMirror processorMirror = processingEnvironment.getElementUtils().getTypeElement("javax.annotation.processing.Processor").asType();
        
        // Collect all processors:
        final @Nonnull List<String> processors = new LinkedList<>();
        for (@Nonnull Element element : roundEnvironment.getElementsAnnotatedWith(SupportedAnnotationTypes.class)) {
            if (element.getKind() != ElementKind.CLASS) {
                AnnotationLog.error("Only a class can process annotations.", SourcePosition.of(element));
                return false;
            }
            
            if (element.getEnclosingElement().getKind() != ElementKind.PACKAGE) {
                AnnotationLog.error("Generating the services entry is only supported for non-nested processors.", SourcePosition.of(element));
                return false;
            }
            
            if (!processingEnvironment.getTypeUtils().isSubtype(element.asType(), processorMirror)) {
                AnnotationLog.error("The annotated class does not implement the processor interface.", SourcePosition.of(element));
                return false;
            }
            
            final @Nonnull TypeElement typeElement = (TypeElement) element;
            final @Nonnull String qualifiedName = typeElement.getQualifiedName().toString();
            AnnotationLog.debugging("Found the annotation processor '" + qualifiedName + "'.");
            processors.add(qualifiedName);
        }
        
        // Generate the file:
        try {
            final @Nonnull FileObject fileObject = processingEnvironment.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", "META-INF/services/javax.annotation.processing.Processor");
            try (@Nonnull Writer writer = fileObject.openWriter()) {
                for (@Nonnull String processor : processors) {
                    writer.write(processor + "\n");
                }
            }
            AnnotationLog.debugging("Wrote the found annotation processors to '" + fileObject.toUri() + "'.");
        } catch (@Nonnull IOException exception) {
            AnnotationLog.error("An IOException occurred while writing the services file: " + exception);
        }
        
        return false;
    }
    
}
