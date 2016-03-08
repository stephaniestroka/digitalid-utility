package net.digitalid.utility.validation.validator;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.processing.TypeImporter;

/**
 * A contract generator generates the contract for an annotation during annotation processing.
 * 
 * @see MethodAnnotationValidator
 * @see ValueAnnotationValidator
 */
public interface ContractGenerator {
    
    /* -------------------------------------------------- Contract Generation -------------------------------------------------- */
    
    /**
     * Generates the contract for the given element which is annotated with the given annotation mirror.
     * The annotation mirror can be used to retrieve possible annotation values.
     * The type importer can be used to import referenced types.
     */
    @Pure
    public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull TypeImporter typeImporter);
    
}
