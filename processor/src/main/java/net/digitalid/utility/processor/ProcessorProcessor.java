package net.digitalid.utility.processor;

import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * Description.
 */
@SupportedAnnotationTypes("net.digitalid.utility.processor.AnnotationProcessor")
public class ProcessorProcessor extends CustomProcessor {
    
    /* -------------------------------------------------- Processing -------------------------------------------------- */
    
    @Override
    public boolean processAll(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        for (final Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(AnnotationProcessor.class)) {
            warning("The processor processor was triggered for " + annotatedElement.getSimpleName() + ".", SourcePosition.of(annotatedElement));
        }
        return true;
    }
    
}
