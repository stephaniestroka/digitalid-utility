package net.digitalid.utility.property;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.GenerateNoBuilder;
import net.digitalid.utility.generator.annotations.GenerateNoSubclass;

/**
 * A value validator checks whether a value is valid.
 */
@GenerateNoBuilder
@GenerateNoSubclass
public interface ValueValidator<V> {
    
    public final static ValueValidator<Object> DEFAULT = new ValueValidator<Object>() {
        @Pure
        @Override
        public boolean isValid(@Nonnull Object value) {
            return true;
        }
    };
    
    @Pure
    public boolean isValid(@Nonnull V value);
    
}
