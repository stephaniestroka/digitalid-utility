package net.digitalid.utility.conversion.converters;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.conversion.converter.Converter;
import net.digitalid.utility.conversion.converter.CustomAnnotation;
import net.digitalid.utility.conversion.converter.CustomField;
import net.digitalid.utility.conversion.converter.SelectionResult;
import net.digitalid.utility.conversion.converter.ValueCollector;
import net.digitalid.utility.immutable.ImmutableList;
import net.digitalid.utility.logging.exceptions.ExternalException;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.string.CodeIdentifier;
import net.digitalid.utility.validation.annotations.type.Stateless;

import static net.digitalid.utility.conversion.converter.types.CustomType.STRING;

/**
 * This class implements the conversion of strings.
 */
@Stateless
public class StringConverter implements Converter<String, Void> {
    
    public static final @Nonnull StringConverter INSTANCE = new StringConverter();
    
    private static final @Nonnull ImmutableList<@Nonnull CustomField> fields;
    
    static {
        fields = ImmutableList.withElements(CustomField.with(STRING, "string", ImmutableList.withElements(CustomAnnotation.with(Nonnull.class))));
    }
    
    @Pure
    @Override
    public @Nonnull ImmutableList<@Nonnull CustomField> getFields() {
        return fields;
    }
    
    @Pure
    @Override
    public @Nonnull @CodeIdentifier @MaxSize(63) String getName() {
        return "String";
    }
    
    @Pure
    @Override
    public <X extends ExternalException> int convert(@NonCaptured @Unmodified @Nullable String string, @NonCaptured @Modified @Nonnull ValueCollector<X> valueCollector) throws X {
        int i = 1;
        i *= valueCollector.setNullableString(string);
        return i;
    }
    
    @Pure
    @Override
    public @Capturable <X extends ExternalException> @Nullable String recover(@NonCaptured @Modified @Nonnull SelectionResult<X> selectionResult, Void externallyProvided) throws X {
        return selectionResult.getString();
    }
    
}
