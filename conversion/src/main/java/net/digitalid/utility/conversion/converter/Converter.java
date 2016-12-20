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
import net.digitalid.utility.collaboration.annotations.TODO;
import net.digitalid.utility.collaboration.enumerations.Author;
import net.digitalid.utility.immutable.ImmutableList;
import net.digitalid.utility.logging.exceptions.ExternalException;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.string.CodeIdentifier;
import net.digitalid.utility.validation.annotations.type.Immutable;

@Immutable
public interface Converter<@Unspecifiable TYPE, @Specifiable PROVIDED> {
    
    @Pure
    @TODO(task = "Rename to getSimpleName() and introduce getQualifiedName() (both of which can also be dynamic, which is important for synchronized property actions). Alternatively (and probably better), getName() and getPackage()?", date = "2016-12-20", author = Author.KASPAR_ETTER)
    public @Nonnull @CodeIdentifier @MaxSize(63) String getName();
    
    @Pure
    public @Nonnull ImmutableList<@Nonnull CustomField> getFields();
    
    @Pure
    @TODO(task = "Rename method to 'encode'.", date = "2016-12-20", author = Author.KASPAR_ETTER)
    public <EXCEPTION extends ExternalException> int convert(@NonCaptured @Unmodified @Nullable TYPE object, @NonCaptured @Modified @Nonnull ValueCollector<EXCEPTION> valueCollector) throws EXCEPTION;
    
    @Pure
    @TODO(task = "Rename method to 'decode'.", date = "2016-12-20", author = Author.KASPAR_ETTER)
    public @Capturable <EXCEPTION extends ExternalException> @Nullable TYPE recover(@NonCaptured @Modified @Nonnull SelectionResult<EXCEPTION> selectionResult, PROVIDED provided) throws EXCEPTION;
    
}
