package net.digitalid.utility.conversion.converter;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.immutable.ImmutableList;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 *
 */
@Immutable
public interface Converter<T> {

    @Pure
    public @Nonnull ImmutableList<@Nonnull CustomField> getFields();

    @Pure
    public void convert(@NonCaptured @Unmodified @Nonnull T object, @NonCaptured @Modified @Nonnull ValueCollector valueCollector);

//    @Pure
//    public @Capturable @Nonnull T recover(@Nonnull ResultSet resultSet);

}
