package net.digitalid.utility.conversion.converters;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.conversion.enumerations.Representation;
import net.digitalid.utility.conversion.exceptions.ConnectionException;
import net.digitalid.utility.conversion.interfaces.Converter;
import net.digitalid.utility.conversion.interfaces.Decoder;
import net.digitalid.utility.conversion.interfaces.Encoder;
import net.digitalid.utility.conversion.model.CustomField;
import net.digitalid.utility.conversion.model.CustomType;
import net.digitalid.utility.immutable.ImmutableList;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.string.CodeIdentifier;
import net.digitalid.utility.validation.annotations.string.DomainName;
import net.digitalid.utility.validation.annotations.type.Stateless;


/**
 * This class implements the conversion of integers.
 */
@Stateless
public class IntegerConverter implements Converter<Integer, Void> {
    
    /* -------------------------------------------------- Instance -------------------------------------------------- */
    
    public static final @Nonnull IntegerConverter INSTANCE = new IntegerConverter();
    
    /* -------------------------------------------------- Type -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull Class<Integer> getType() {
        return Integer.class;
    }
    
    /* -------------------------------------------------- Name -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull @CodeIdentifier @MaxSize(63) String getTypeName() {
        return "Integer";
    }
    
    /* -------------------------------------------------- Package -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull @DomainName String getTypePackage() {
        return "java.lang";
    }
    
    /* -------------------------------------------------- Fields -------------------------------------------------- */
    
    private static final @Nonnull ImmutableList<@Nonnull CustomField> fields = ImmutableList.withElements(CustomField.with(CustomType.INTEGER32, "value"));
    
    @Pure
    @Override
    public @Nonnull ImmutableList<@Nonnull CustomField> getFields(@Nonnull Representation representation) {
        return fields;
    }
    
    /* -------------------------------------------------- Convert -------------------------------------------------- */
    
    @Pure
    @Override
    public <EXCEPTION extends ConnectionException> int convert(@NonCaptured @Unmodified @Nonnull Integer integer, @NonCaptured @Modified @Nonnull Encoder<EXCEPTION> encoder) throws EXCEPTION {
        encoder.encodeInteger32(integer);
        return 1;
    }
    
    /* -------------------------------------------------- Recover -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable <EXCEPTION extends ConnectionException> @Nonnull Integer recover(@NonCaptured @Modified @Nonnull Decoder<EXCEPTION> decoder, Void provided) throws EXCEPTION {
        return decoder.decodeInteger32();
    }
    
}
