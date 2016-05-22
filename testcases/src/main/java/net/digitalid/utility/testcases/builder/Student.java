package net.digitalid.utility.testcases.builder;

import java.util.List;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.rootclass.RootClass;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class models a student.
 * 
 * Ideas:
 * - Instead of @Ignore or @Representation, interpret non-abstract methods as the former and abstract methods as the latter. What about mutable fields (due to setters)?
 */
@Mutable
public abstract class Student extends RootClass /* implements Convertible */ {
    
    public interface AnInterface {
    }
    
    /**
     * Returns the name of this student.
     */
    @Pure
    // @Logging
    // @Representation
    public abstract @Nonnull @MaxSize(64) String getName();
    
    @Impure
    public abstract void setName(@Nonnull @MaxSize(64) String name);
    
    /**
     * Returns the six digit ID of this student.
     */
    @Pure
    // @Logging
    // @Default("0)
    public abstract @Positive int getID();
    
    @Pure
    public abstract @Nonnull @NonNullableElements List<Student> getBuddies();
    
    @Pure
    // @Ignore
    public @Nonnull String getFormattedString() {
        return getName() + " (" + getID() + ")";
    }
    
    /* -------------------------------------------------- Convertible -------------------------------------------------- */
    
/*    @Pure
    public abstract @Nonnull Object[] getFieldValues();*/
    
}
