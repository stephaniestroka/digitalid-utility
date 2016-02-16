package net.digitalid.utility.testcases.builder;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.reference.Chainable;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * Advantages of builder pattern:
 * - Better handling of optional and default values.
 * - Callers fail if the order of getters with same return type is changed in the source code.
 * - The builder class serves as a meta-information-object about the fields (and their names) of the source class. (This can be used to generate @GenericType(Student.class) annotations.)
 * 
 * Questions:
 * - Enforce certain parameters with intermediary classes (e.g. StudentWithoutID) that miss the create/build-method? Disadvantage of such an approach: The order of parameters would be enforced.
 *   (Either create a new object for each provided parameter or generate an interface for each intermediary step and return the same object as an instance of the particular interface.)
 * - What if the type of the getter and the field are not exactly the same (for example, expose only ReadOnlyType but store a FreezableType for internal operations).
 */
@Mutable
@Generated(value = {"net.digitalid.utility.generator.processor.GeneratorProcessor"}, date = "2016-02-10T21:39:49.202+0100")
public class StudentBuilder {
    
    /* -------------------------------------------------- Name -------------------------------------------------- */
    
    @Nonnull @MaxSize(64) String name;
    
    @Pure
    @Chainable
    public @Nonnull StudentBuilder withName(@Nonnull @MaxSize(64) String name) {
        Require.that(name != null).orThrow("The name may not be null.");
        Require.that(name.length() <= 64).orThrow("The length of the name may be at most 64 character.");
        
        this.name = name;
        return this;
    }
    
    /* -------------------------------------------------- ID -------------------------------------------------- */
    
    @Positive int ID;
    
    @Pure
    @Chainable
    public @Nonnull StudentBuilder withID(@Positive int ID) {
        this.ID = ID;
        return this;
    }
    
    /* -------------------------------------------------- Buddies -------------------------------------------------- */
    
//    @Nonnull @NonNullableElements @NonFrozen FreezableList<Student> buddies;
//    
//    @Pure
//    @Chainable
//    // With @Captured annotation!
//    public @Nonnull StudentBuilder withBuddies(@Captured @Nonnull @NonNullableElements @NonFrozen FreezableList<Student> buddies) {
//        this.buddies = buddies;
//        return this;
//    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected StudentBuilder() {}
    
    // Rather have a public constructor with all the fields as parameters for conversion recovery? Or make it private so that no one else calls it?
    public StudentBuilder(@Nonnull Object[] fieldValues) {
        // TODO: Cast and assign the elements to the fields.
    }
    
    @Pure
    public static @Nonnull StudentBuilder get() {
        return new StudentBuilder();
    }
    
    /* -------------------------------------------------- Builder -------------------------------------------------- */
    
    @Pure
    public @Nonnull Student build() {
        return new ManuallyGeneratedStudent(this);
    }
    
}
