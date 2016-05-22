package net.digitalid.utility.testcases.builder;


import java.util.List;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.Ensure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.type.Mutable;

@Mutable
@SuppressWarnings("null")
@Generated(value = {"net.digitalid.utility.generator.processor.GeneratorProcessor"}, date = "2016-02-10T21:39:49.202+0100")
class ManuallyGeneratedStudent extends Student {
    
    /* -------------------------------------------------- Name -------------------------------------------------- */
    
    private @Nonnull @MaxSize(64) String name;
    
    @Pure
    @Override
    public @Nonnull @MaxSize(64) String getName() {
        Log.verbose("The method getName was called.");
        
        Ensure.that(name != null).orThrow("The name should be null!");
        
        return name;
    }
    
    @Impure
    @Override
    public void setName(@Nonnull @MaxSize(64) String name) {
        Require.that(name != null).orThrow("The name may not be null.");
        Require.that(name.length() <= 64).orThrow("The length of the name may be at most 64 character.");
        
        this.name = name;
    }
    
    /* -------------------------------------------------- ID -------------------------------------------------- */
    
    private final @Positive int ID;
    
    @Pure
    @Override
    public @Positive int getID() {
        Log.verbose("The method getID was called.");
        return ID;
    }
    
    /* -------------------------------------------------- Buddies -------------------------------------------------- */
    
    // Freezable instead of ReadOnly because of the annotation @NonFrozen.
    private final @Nonnull @NonNullableElements List<Student> buddies;
    
    @Pure
    @Override 
    public @Nonnull List<Student> getBuddies() {
        return buddies;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ManuallyGeneratedStudent(@Nonnull String name, int ID, @Nonnull @NonNullableElements List<Student> buddies) {
        this.name = name;
        this.ID = ID;
        this.buddies = buddies;
        
        // this.validate();
    }
    
    /* -------------------------------------------------- Convertible -------------------------------------------------- */
    
    @Pure
    public @Nonnull Object[] getFieldValues() {
        return new Object[] {name, ID};
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean equals(@Nullable Object object) {
        if (object == this) { return true; }
        if (object == null || !(object instanceof Student)) { return false; }
        final @Nonnull Student other = (Student) object;
        return this.getName().equals(other.getName()) && this.getID() == other.getID();
    }
    
    @Pure
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.name.hashCode();
        hash = 41 * hash + this.ID;
        return hash;
    }
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return "Student(name: " + getName() + ", ID: " + getID() + ")";
    }
    
}
