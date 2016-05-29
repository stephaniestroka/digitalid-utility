package net.digitalid.utility.validation.validator;

import net.digitalid.utility.validation.annotations.meta.TypeValidator;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * A type annotation validator validates the annotations on the members of the annotated type.
 * 
 * @see TypeValidator
 */
@Stateless
public interface TypeAnnotationValidator extends AnnotationHandler {}
