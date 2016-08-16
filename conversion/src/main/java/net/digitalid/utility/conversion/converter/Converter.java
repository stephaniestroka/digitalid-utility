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
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.string.CodeIdentifier;
import net.digitalid.utility.validation.auxiliary.None;

public interface Converter<T, E> {
    
    @Pure
    public @Nonnull ImmutableList<@Nonnull CustomField> getFields();
    
    @Pure
    public int convert(@Nullable @NonCaptured @Unmodified T object, @Nonnull @NonCaptured @Modified ValueCollector valueCollector);
    
    @Pure
    @TODO(task = "Add a checked exception? If yes, an ExternalException?", date = "2016-08-08", author = Author.KASPAR_ETTER)
    public @Nonnull @Capturable T recover(@Nonnull @NonCaptured @Modified SelectionResult selectionResult, E externallyProvided);
    
    @Pure
    public @Nonnull @CodeIdentifier @MaxSize(63) String getName();
    
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
        public int convert(@NonCaptured @Unmodified None object, @NonCaptured @Modified ValueCollector valueCollector) {
            return 0;
        }
        
        @Override
        public @Nonnull None recover(@NonCaptured @Modified SelectionResult selectionResult, Object externallyProvided) {
            return null;
        }
    }
    
}
