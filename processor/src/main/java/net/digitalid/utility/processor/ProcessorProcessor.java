/*
 * Copyright (C) 2017 Synacts GmbH, Switzerland (info@synacts.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.digitalid.utility.processor;


import javax.annotation.Nonnull;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.processor.annotations.SupportedAnnotations;
import net.digitalid.utility.processor.generator.ServiceFileGenerator;
import net.digitalid.utility.validation.annotations.type.Mutable;

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
