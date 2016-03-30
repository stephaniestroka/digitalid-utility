package net.digitalid.utility.processor;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import net.digitalid.utility.processor.annotations.SupportedAnnotations;
import net.digitalid.utility.processor.generator.ServiceFileGenerator;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This annotation processor generates the service loader entry for other annotation processors.
 */
@SupportedAnnotations(SupportedAnnotations.class)
public class ProcessorProcessor extends CustomProcessor {
    
    /* -------------------------------------------------- Processing -------------------------------------------------- */
    
    @Override
    public void processFirstRound(@Nonnull @NonNullableElements Set<? extends TypeElement> annotations, @Nonnull RoundEnvironment roundEnvironment) {
        final @Nonnull ServiceFileGenerator serviceLoaderFile = ServiceFileGenerator.forService(Processor.class);
        for (@Nonnull Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(SupportedAnnotations.class)) {
            serviceLoaderFile.addProvider(annotatedElement);
        }
        serviceLoaderFile.write();
    }
    
}
