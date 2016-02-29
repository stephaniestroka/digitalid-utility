package net.digitalid.utility.generator.information.type.filter;

import javax.annotation.Nonnull;

import net.digitalid.utility.generator.information.filter.KeyExtractor;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.string.StringCase;

/**
 *
 */
public class FieldNameExtractor implements KeyExtractor<String, MethodInformation> {
    
    private FieldNameExtractor() {}
    
    public static @Nonnull FieldNameExtractor get() {
        return new FieldNameExtractor();
    }
    
    @Nonnull @Override public String getKey(@Nonnull MethodInformation input) {
        return StringCase.lowerCaseFirstCharacter(input.getFieldName());
    }
    
}
