package net.digitalid.utility.generator.information.field;

import net.digitalid.utility.generator.generators.BuilderGenerator;
import net.digitalid.utility.generator.generators.SubclassGenerator;

/**
 * This type collects the relevant information about a potentially inherited field for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 * 
 * @see GeneratedRepresentingFieldInformation
 * @see DirectlyAccessibleDeclaredFieldInformation
 */
public interface PotentiallyInheritedFieldInformation extends FieldInformation {}
