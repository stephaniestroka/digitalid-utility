package net.digitalid.testing.conversion;

import javax.annotation.Nonnull;

import net.digitalid.utility.collections.annotations.size.MaxSize;
import net.digitalid.utility.collections.annotations.size.MinSize;
import net.digitalid.utility.conversion.annotations.IgnoreForConversion;
import net.digitalid.utility.validation.state.Mutable;

import com.google.j2objc.annotations.Property;

/**
 * The superclass with a name.
 */
@Mutable
public class Superclass {
    
    /**
     * Stores the name of this object.
     */
    @IgnoreForConversion
    @Property("readonly")
    public final @Nonnull @MinSize(6) @MaxSize(10) String name;
    
    /**
     * Creates a new object of this class.
     * 
     * @param name the name of the new object.
     */
    protected Superclass(@Nonnull @MinSize(6) @MaxSize(10) String name) {
        this.name = name;
    }
    
}
