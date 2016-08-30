package net.digitalid.utility.conversion.converter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
import net.digitalid.utility.validation.auxiliary.None;

public interface Converter<T, E> {
    
    @Pure
    public @Nonnull ImmutableList<@Nonnull CustomField> getFields();
    
    @Pure
    public <X extends ExternalException> int convert(@Nullable @NonCaptured @Unmodified T object, @Nonnull @NonCaptured @Modified ValueCollector<X> valueCollector) throws ExternalException;
    
    @Pure
    public @Capturable <X extends ExternalException> @Nonnull T recover(@Nonnull @NonCaptured @Modified SelectionResult<X> selectionResult, E externallyProvided) throws ExternalException;
    
    @Pure
    public @Nonnull @CodeIdentifier @MaxSize(63) String getName();
    
    @TODO(task = "Move this class to some other place!", date = "2016-08-30", author = Author.KASPAR_ETTER, assignee = Author.STEPHANIE_STROKA)
    public class NoneConverter implements Converter<None, Object> {
        
        public static @Nonnull NoneConverter INSTANCE = new NoneConverter();
        
        @Override
        public @Nonnull ImmutableList<@Nonnull CustomField> getFields() {
            return ImmutableList.withElements();
        }
        
        @Override
        public @Nonnull @CodeIdentifier String getName() {
            return "None";
        }
        
        @Override
        public <X extends ExternalException> int convert(@Nullable @NonCaptured @Unmodified None object, @Nonnull @NonCaptured @Modified ValueCollector<X> valueCollector) throws ExternalException {
            return 0;
        }
        
        @Override
        public @Nonnull None recover(@Nonnull @NonCaptured @Modified SelectionResult selectionResult, Object externallyProvided) throws ExternalException {
            return null;
        }
    }
    
}
