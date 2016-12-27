package net.digitalid.utility.conversion.converter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.immutable.ImmutableList;
import net.digitalid.utility.logging.exceptions.ExternalException;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.size.NonEmpty;
import net.digitalid.utility.validation.annotations.string.CodeIdentifier;
import net.digitalid.utility.validation.annotations.string.DomainName;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * A converter stores type-specific information and allows to convert and recover objects from that type without reflection.
 */
@Immutable
public interface Converter<@Unspecifiable TYPE, @Specifiable PROVIDED> {
    
    /* -------------------------------------------------- Type -------------------------------------------------- */
    
    /**
     * Returns the type which is modeled and whose instances are converted.
     */
    @Pure
    public @Nonnull Class<? super TYPE> getType();
    
    /* -------------------------------------------------- Name -------------------------------------------------- */
    
    /**
     * Returns the name of the type which is modeled and converted.
     */
    @Pure
    public @Nonnull @CodeIdentifier @MaxSize(63) String getTypeName();
    
    /* -------------------------------------------------- Package -------------------------------------------------- */
    
    /**
     * Returns the name of the package in which the type is declared.
     */
    @Pure
    public @Nonnull @DomainName String getTypePackage();
    
    /* -------------------------------------------------- Fields -------------------------------------------------- */
    
    /**
     * Returns the fields of the modeled type for the given representation.
     */
    @Pure
    public @Nonnull ImmutableList<@Nonnull CustomField> getFields(@Nonnull Representation representation);
    
    /* -------------------------------------------------- Inheritance -------------------------------------------------- */
    
    /**
     * Returns the converter of the supertype or null if the modeled type has no convertible supertype.
     */
    @Pure
    public default @Nullable Converter<? super TYPE, PROVIDED> getSupertypeConverter() { return null; }
    
    /**
     * Returns the converters of the subtypes or null if the modeled type has no convertible subtypes.
     */
    @Pure
    public default @Nullable @NonEmpty ImmutableList<@Nonnull Converter<? extends TYPE, PROVIDED>> getSubtypeConverters() { return null; }
    
    /* -------------------------------------------------- Convert -------------------------------------------------- */
    
    /**
     * Converts the given object of the modeled type and returns the number of rows created.
     */
    @Pure
    public <EXCEPTION extends ExternalException> int convert(@NonCaptured @Unmodified @Nullable TYPE object, @NonCaptured @Modified @Nonnull Encoder<EXCEPTION> encoder) throws EXCEPTION;
    
    /* -------------------------------------------------- Recover -------------------------------------------------- */
    
    /**
     * Recovers an object of the modeled type with the provided object.
     */
    @Pure
    public @Capturable <EXCEPTION extends ExternalException> @Nullable TYPE recover(@NonCaptured @Modified @Nonnull Decoder<EXCEPTION> decoder, PROVIDED provided) throws EXCEPTION;
    
}
