package net.digitalid.utility.conversion.converter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.immutable.ImmutableList;
import net.digitalid.utility.logging.exceptions.ExternalException;
import net.digitalid.utility.validation.annotations.string.CodeIdentifier;
import net.digitalid.utility.validation.auxiliary.None;

public interface Converter<T, E> {
    
    @Pure
    public @Nonnull ImmutableList<CustomField> getFields();
    
    @Pure
    public <X extends ExternalException> int convert(@Nullable @NonCaptured @Unmodified T object, @Nonnull @NonCaptured @Modified ValueCollector<X> valueCollector) throws X;
    
    @Pure
    public @Capturable <X extends ExternalException> @Nonnull T recover(@Nonnull @NonCaptured @Modified SelectionResult<X> selectionResult, E externallyProvided) throws X;
    
    @Pure
    public @Nonnull @CodeIdentifier String getName();
    
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
        public <X extends ExternalException> int convert(@Nullable @NonCaptured @Unmodified None object, @Nonnull @NonCaptured @Modified ValueCollector<X> valueCollector) throws X {
            return 0;
        }
        
        @Override
        public @Nonnull None recover(@Nonnull @NonCaptured @Modified SelectionResult selectionResult, Object externallyProvided) {
            return null;
        }
    }
    
}
