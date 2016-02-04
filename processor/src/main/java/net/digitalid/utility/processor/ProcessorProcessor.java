package net.digitalid.utility.processor;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import net.digitalid.utility.processor.files.ServiceLoaderFile;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;

/**
 * This annotation processor generates the service loader entry for other annotation processors.
 */
@SupportedAnnotationTypes("javax.annotation.processing.SupportedAnnotationTypes")
public class ProcessorProcessor extends CustomProcessor {
    
    /* -------------------------------------------------- Processing -------------------------------------------------- */
    
    @Override
    public void processFirstRound(@Nonnull @NonNullableElements Set<? extends TypeElement> annotations, @Nonnull RoundEnvironment roundEnvironment) {
        final @Nonnull ServiceLoaderFile serviceLoaderFile = ServiceLoaderFile.forService(Processor.class);
        for (@Nonnull Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(SupportedAnnotationTypes.class)) {
            serviceLoaderFile.addProvider(annotatedElement);
        }
        serviceLoaderFile.write();
    }
    
}
