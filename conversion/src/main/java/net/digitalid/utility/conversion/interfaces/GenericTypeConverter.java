package net.digitalid.utility.conversion.interfaces;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * A converter for a type with a generic parameter has to be constructed with a converter for objects of that parameter.
 */
@Immutable
public interface GenericTypeConverter<@Unspecifiable OBJECT, @Unspecifiable TYPE, @Specifiable PROVIDED> extends Converter<TYPE, PROVIDED> {
    
    /* -------------------------------------------------- Object Converter -------------------------------------------------- */
    
    /**
     * Returns the converter of the wrapped object.
     */
    @Pure
    public abstract @Nonnull Converter<OBJECT, Void> getObjectConverter();
    
}
