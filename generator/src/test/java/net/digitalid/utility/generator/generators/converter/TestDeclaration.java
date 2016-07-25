package net.digitalid.utility.generator.generators.converter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.conversion.converter.CustomField;
import net.digitalid.utility.conversion.converter.Declaration;

/**
 * The declaration that is used to test the functionality of the converter.
 */
class TestDeclaration implements Declaration {
    
    List<@Nonnull CustomField> collectedFields = new ArrayList<>();
    
    @Impure
    @Override
    public void setField(@Nonnull CustomField customField) {
        collectedFields.add(customField);
    }
    
}
