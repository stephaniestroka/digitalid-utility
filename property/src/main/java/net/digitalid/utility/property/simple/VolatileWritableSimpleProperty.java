package net.digitalid.utility.property.simple;

import java.util.Objects;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.collaboration.annotations.TODO;
import net.digitalid.utility.collaboration.enumerations.Author;
import net.digitalid.utility.collaboration.enumerations.Priority;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This writable property stores a simple value in volatile memory.
 */
@Mutable
@GenerateBuilder
@GenerateSubclass
public abstract class VolatileWritableSimpleProperty<V> extends WritableSimpleProperty<V, RuntimeException> {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    private @Valid V value;
    
    @Pure
    @Override
    public @NonCapturable @Valid V get() {
        return value;
    }
    
    @Impure
    @Override
    public @Capturable @Valid V set(@Captured @Valid V newValue) {
        final V oldValue = this.value;
        this.value = newValue;
        
        if (!Objects.equals(newValue, oldValue)) { notifyObservers(oldValue, newValue); }
        
        return oldValue;
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    protected VolatileWritableSimpleProperty(@Captured @Valid V value) {
        this.value = value;
    }
    
    /* -------------------------------------------------- Temporary -------------------------------------------------- */
    
    @Impure
    @Override
    @TODO(task = "Remove this overriding once the subclass generator can handle generic exception types.", date = "2016-08-05", author = Author.KASPAR_ETTER, assignee = Author.STEPHANIE_STROKA, priority = Priority.MIDDLE)
    protected void notifyObservers(@NonCaptured @Unmodified @Valid V oldValue, @NonCaptured @Unmodified @Valid V newValue) throws RuntimeException {
        super.notifyObservers(oldValue, newValue);
    }
    
}
