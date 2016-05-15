package net.digitalid.utility.conversion.converter;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.immutable.ImmutableList;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 *
 */
@Immutable
public interface NodeConverter<T> extends LeafConverter<T> {
    
    @Pure
    public @Nonnull ImmutableList<@Nonnull CustomField> getFields();

}
