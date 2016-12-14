package net.digitalid.utility.property.set;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.collaboration.annotations.TODO;
import net.digitalid.utility.collaboration.enumerations.Author;
import net.digitalid.utility.collaboration.enumerations.Priority;
import net.digitalid.utility.collections.set.ReadOnlySet;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.property.Property;
import net.digitalid.utility.validation.annotations.type.ReadOnly;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This read-only property stores a set of values.
 * 
 * @see WritableSetProperty
 * @see ReadOnlyVolatileSetProperty
 * @see ReadOnlySetPropertyImplementation
 */
@ThreadSafe
@ReadOnly(WritableSetProperty.class)
@TODO(task = "Restrict the ReadOnlySet to a SynchronizedReadOnlySet.", date = "2016-09-27", author = Author.KASPAR_ETTER, assignee = Author.KASPAR_ETTER, priority = Priority.MIDDLE)
public interface ReadOnlySetProperty<VALUE, READONLY_SET extends ReadOnlySet<@Nonnull @Valid VALUE>, EXCEPTION extends Exception, OBSERVER extends SetObserver<VALUE, READONLY_SET, EXCEPTION, OBSERVER, PROPERTY>, PROPERTY extends ReadOnlySetProperty<VALUE, READONLY_SET, EXCEPTION, OBSERVER, PROPERTY>> extends Property<OBSERVER>, Valid.Value<VALUE> {
    
    /* -------------------------------------------------- Getter -------------------------------------------------- */
    
    /**
     * Returns a read-only set with the values of this property.
     */
    @Pure
    public @Nonnull @NonFrozen READONLY_SET get() throws EXCEPTION;
    
}
