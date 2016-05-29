package net.digitalid.utility.processor.generator;

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
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.fixes.Quotes;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processing.utility.StaticProcessingEnvironment;
import net.digitalid.utility.processor.generator.annotations.NonWrittenRecipient;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class generates files in the "META-INF/services" directory.
 * 
 * @see ServiceLoader
 */
@Mutable
public class ServiceFileGenerator extends FileGenerator {
    
    /* -------------------------------------------------- Service -------------------------------------------------- */
    
    private final @Nonnull Class<?> service;
    
    private final @Nonnull TypeMirror serviceMirror;
    
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
    
    @Pure
    @Override
    public @Nonnull String getName() {
        return getJarRelativeFilePath();
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ServiceFileGenerator(@Nonnull Class<?> service) {
        this.service = service;
        this.serviceMirror = StaticProcessingEnvironment.getElementUtils().getTypeElement(service.getCanonicalName()).asType();
        ProcessingLog.verbose("Generating the service loader file for the service $.", service.getName());
    }
    
    /**
     * Returns a service file generator for the given service.
     */
    @Pure
    public static @Capturable @Nonnull ServiceFileGenerator forService(@Nonnull Class<?> service) {
        return new ServiceFileGenerator(service);
    }
    
    /* -------------------------------------------------- Providers -------------------------------------------------- */
    
    /**
     * Stores the qualified binary names of the providers for the specified service.
     */
    private final @Nonnull List<@Nonnull String> qualifiedProviderNames = new LinkedList<>();
    
    /**
     * Adds the provider with the given qualified binary name to the list of providers for the specified service.
     * 
     * @param qualifiedProviderName the name has to be in binary form (i.e. with a dollar sign for inner classes).
     */
    @Impure
    @NonWrittenRecipient
    public void addProvider(@Nonnull String qualifiedProviderName) {
        requireNotWritten();
        
        qualifiedProviderNames.add(qualifiedProviderName);
        ProcessingLog.information("Added the provider " + Quotes.inSingle(qualifiedProviderName) + " for the service " + Quotes.inSingle(service.getName()));
    }
    
    /**
     * Adds the given element as a provider for the specified service after performing numerous checks.
     */
    @Impure
    @NonWrittenRecipient
    public void addProvider(@Nonnull Element providerElement) {
        @Nullable String errorMessage = null;
        if (providerElement.getKind() != ElementKind.CLASS) { errorMessage = "Only a class can implement a service:"; }
        else if (providerElement.getModifiers().contains(Modifier.ABSTRACT)) { errorMessage = "Only a non-abstract class can implement a service:"; }
        else if (!ProcessingUtility.hasPublicDefaultConstructor((TypeElement) providerElement)) { errorMessage = "The annotated class does not have a public default constructor:"; }
        else if (!StaticProcessingEnvironment.getTypeUtils().isSubtype(providerElement.asType(), serviceMirror)) { errorMessage = "The annotated class does not implement the specified service:"; }
        
        if (errorMessage == null) {
            addProvider(StaticProcessingEnvironment.getElementUtils().getBinaryName((TypeElement) providerElement).toString());
        } else {
            ProcessingLog.error(errorMessage, SourcePosition.of(providerElement));
        }
    }
    
    /* -------------------------------------------------- Writing -------------------------------------------------- */
    
    @Impure
    @Override
    @NonWrittenRecipient
    protected void writeOnce() throws IOException {
        Collections.sort(qualifiedProviderNames);
        final @Nonnull FileObject fileObject = StaticProcessingEnvironment.environment.get().getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", getJarRelativeFilePath());
        try (@Nonnull Writer writer = fileObject.openWriter()) {
            for (@Nonnull String qualifiedProviderName : qualifiedProviderNames) {
                writer.write(qualifiedProviderName + "\n");
            }
        }
    }
    
}
