package net.digitalid.utility.processor;


import javax.annotation.Nonnull;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.type.Mutable;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.processor.annotations.SupportedAnnotations;
import net.digitalid.utility.processor.generator.ServiceFileGenerator;

/**
 * This annotation processor generates the service loader entry for other annotation processors.
 */
@Mutable
@SupportedAnnotations(SupportedAnnotations.class)
public class ProcessorProcessor extends CustomProcessor {
    
    /* -------------------------------------------------- Processing -------------------------------------------------- */
    
    @Impure
    @Override
    public void processFirstRound(@Nonnull FiniteIterable<@Nonnull ? extends TypeElement> annotations, @Nonnull RoundEnvironment roundEnvironment) {
        final @Nonnull ServiceFileGenerator serviceLoaderFile = ServiceFileGenerator.forService(Processor.class);
        for (@Nonnull Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(SupportedAnnotations.class)) {
            serviceLoaderFile.addProvider(annotatedElement);
        }
        serviceLoaderFile.write();
    }
    
}
