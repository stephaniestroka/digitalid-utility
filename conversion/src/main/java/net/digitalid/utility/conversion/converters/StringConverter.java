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
import net.digitalid.utility.conversion.converter.Decoder;
import net.digitalid.utility.conversion.converter.Encoder;
import net.digitalid.utility.conversion.converter.Representation;
import net.digitalid.utility.immutable.ImmutableList;
import net.digitalid.utility.logging.exceptions.ExternalException;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.string.CodeIdentifier;
import net.digitalid.utility.validation.annotations.string.DomainName;
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
    public @Nonnull ImmutableList<@Nonnull CustomField> getFields(@Nonnull Representation representation) {
        return fields;
    }
    
    @Pure
    @Override
    public @Nonnull @CodeIdentifier @MaxSize(63) String getName() {
        return "String";
    }
    
    @Pure
    @Override
    public @Nonnull @DomainName String getPackage() {
        return "java.lang";
    }
    
    @Pure
    @Override
    public <EXCEPTION extends ExternalException> int convert(@NonCaptured @Unmodified @Nullable String string, @NonCaptured @Modified @Nonnull Encoder<EXCEPTION> encoder) throws EXCEPTION {
        int i = 1;
        i *= encoder.setNullableString(string);
        return i;
    }
    
    @Pure
    @Override
    public @Capturable <EXCEPTION extends ExternalException> @Nullable String recover(@NonCaptured @Modified @Nonnull Decoder<EXCEPTION> decoder, Void externallyProvided) throws EXCEPTION {
        return decoder.getString();
    }
    
}
