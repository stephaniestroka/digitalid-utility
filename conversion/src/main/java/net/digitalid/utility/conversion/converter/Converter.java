package net.digitalid.utility.conversion.converter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.validation.auxiliary.None;

public interface Converter<T, E> {
    
    @Pure
    public void declare(@Nonnull @NonCaptured @Modified Declaration declaration);
    
    @Pure
    public <R> void convert(@Nullable @NonCaptured @Unmodified T object, @Nonnull @NonCaptured @Modified ValueCollector<R> valueCollector);
    
    @Pure
    public @Nonnull @Capturable T recover(@Nonnull @NonCaptured @Modified SelectionResult selectionResult, E externallyProvided);
    
    @Pure
    public @Nonnull String getName();
    
    public class NoneConverter implements Converter<None, Object> {
        
        public static @Nonnull NoneConverter INSTANCE = new NoneConverter();
    
        @Override
        public void declare(@NonCaptured @Modified Declaration declaration) {
            
        }
        
        @Override
        public @Nonnull String getName() {
            return "None";
        }
        
        @Override
        public <R> void convert(@NonCaptured @Unmodified None object, @NonCaptured @Modified ValueCollector<R> valueCollector) {
            
        }
        
        @Override
        public @Nonnull None recover(@NonCaptured @Modified SelectionResult selectionResult, Object externallyProvided) {
            return null;
        }
    }
    
}
