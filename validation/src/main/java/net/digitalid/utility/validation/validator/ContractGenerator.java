package net.digitalid.utility.validation.validator;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.processing.utility.TypeImporter;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;

/**
 * A contract generator generates the contract for an annotation during annotation processing.
 * 
 * @see MethodAnnotationValidator
 * @see ValueAnnotationValidator
 */
@Stateless
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
