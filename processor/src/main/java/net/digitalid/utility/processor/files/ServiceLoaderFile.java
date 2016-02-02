package net.digitalid.utility.processor.files;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.logging.processing.AnnotationProcessing;
import net.digitalid.utility.logging.processing.SourcePosition;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class makes it easier to write files in the "META-INF/services" directory.
 * 
 * @see ServiceLoader
 */
@Mutable
public class ServiceLoaderFile {
    
    /* -------------------------------------------------- Service -------------------------------------------------- */
    
    private final @Nonnull Class<?> service;
    
    /**
     * Returns the service that the providers implement.
     */
    @Pure
    public @Nonnull Class<?> getService() {
        return service;
    }
    
    /**
     * Returns the path of this service loader file within the JAR.
     */
    @Pure
    public @Nonnull String getJarRelativeFilePath() {
        return "META-INF/services/" + service.getName();
    }
    
    private final @Nonnull TypeMirror serviceMirror;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ServiceLoaderFile(@Nonnull Class<?> service) {
        this.service = service;
        this.serviceMirror = AnnotationProcessing.getElementUtils().getTypeElement(service.getCanonicalName()).asType();
    }
    
    /**
     * Returns a service loader file for the given service.
     */
    @Pure
    public static @Nonnull ServiceLoaderFile forService(@Nonnull Class<?> service) {
        return new ServiceLoaderFile(service);
    }
    
    /* -------------------------------------------------- Providers -------------------------------------------------- */
    
    /**
     * Stores whether this service loader file has already been written.
     */
    private boolean written = false;
    
    /**
     * Stores the qualified binary names of the providers for the specified service.
     */
    private final @Nonnull List<String> qualifiedProviderNames = new LinkedList<>();
    
    /**
     * Adds the provider with the given qualified binary name to the list of providers for the specified service.
     * 
     * @param qualifiedProviderName the name has to be in binary form (i.e. with a dollar sign for inner classes).
     */
    public void addProvider(@Nonnull String qualifiedProviderName) {
        if (written) {
            AnnotationLog.error("The file '" + getJarRelativeFilePath() + "' has already been written and the provider '" + qualifiedProviderName + "' cannot be added anymore.");
        } else {
            qualifiedProviderNames.add(qualifiedProviderName);
            AnnotationLog.information("Added the provider '" + qualifiedProviderName + "' for the service '" + service.getName() + "'.");
        }
    }
    
    /**
     * Adds the given element as a provider for the specified service after performing numerous checks.
     */
    public void addProvider(@Nonnull Element providerElement) {
        // TODO: Also check that the class is not abstract and has a default constructor!
        @Nullable String errorMessage = null;
        if (providerElement.getKind() != ElementKind.CLASS) { errorMessage = "Only a class can implement a service."; }
        else if (providerElement.getEnclosingElement().getKind() != ElementKind.PACKAGE) { errorMessage = "Generating the services entry is only supported for non-nested processors."; }
        else if (!AnnotationProcessing.getTypeUtils().isSubtype(providerElement.asType(), serviceMirror)) { errorMessage = "The annotated class does not implement the processor interface."; }
        
        if (errorMessage != null) {
            AnnotationLog.error(errorMessage, SourcePosition.of(providerElement));
        } else {
            addProvider(AnnotationProcessing.getBinaryName((TypeElement) providerElement));
        }
    }
    
    /* -------------------------------------------------- Writing -------------------------------------------------- */
    
    /**
     * Writes this service loader file to the file system.
     */
    public void write() {
        if (written) {
            AnnotationLog.error("The file '" + getJarRelativeFilePath() + "' has already been written and can only be written once.");
        } else {
            written = true;
            Collections.sort(qualifiedProviderNames);
            try {
                final @Nonnull FileObject fileObject = AnnotationProcessing.environment.get().getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", getJarRelativeFilePath());
                try (@Nonnull Writer writer = fileObject.openWriter()) {
                    for (@Nonnull String qualifiedProviderName : qualifiedProviderNames) {
                        writer.write(qualifiedProviderName + "\n");
                    }
                }
                AnnotationLog.information("Wrote the service providers to the file '" + getJarRelativeFilePath() + "'.");
            } catch (@Nonnull IOException exception) {
                AnnotationLog.error("An exception occurred while writing the service providers to the file '" + getJarRelativeFilePath() + "': " + exception);
            }
        }
    }
    
}
