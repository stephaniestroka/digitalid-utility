package net.digitalid.utility.testcases.builder;

import java.util.List;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.type.Mutable;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.rootclass.RootClass;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.validation.annotations.method.Chainable;
import net.digitalid.utility.validation.annotations.size.MaxSize;

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
@SuppressWarnings("null")
@Generated(value = {"net.digitalid.utility.generator.processor.GeneratorProcessor"}, date = "2016-02-10T21:39:49.202+0100")
public abstract class ManuallyCreatedStudentBuilder extends RootClass {
    
    static class NameManuallyCreatedStudentBuilder {
    
        NameManuallyCreatedStudentBuilder() {
        }
        
        NameManuallyCreatedStudentBuilder(@Nonnull NameManuallyCreatedStudentBuilder builder) {
            this.name = builder.name;
        }
        
        /* -------------------------------------------------- Name -------------------------------------------------- */
    
        @Nonnull
        @MaxSize(64)
        String name;
    
        @Pure
        @Chainable
        public
        @Nonnull
        IdManuallyCreatedStudentBuilder withName(@Nonnull @MaxSize(64) String name) {
            Require.that(name != null).orThrow("The name may not be null.");
            Require.that(name.length() <= 64).orThrow("The length of the name may be at most 64 character.");
        
            this.name = name;
            return new IdManuallyCreatedStudentBuilder(this);
        }
    
    }
    
    static class IdManuallyCreatedStudentBuilder extends NameManuallyCreatedStudentBuilder {
        
        IdManuallyCreatedStudentBuilder(@Nonnull NameManuallyCreatedStudentBuilder builder) {
            super(builder);
        }
    
        IdManuallyCreatedStudentBuilder(@Nonnull IdManuallyCreatedStudentBuilder builder) {
            super(builder);
            this.ID = builder.ID;
        }
        
        /* -------------------------------------------------- ID -------------------------------------------------- */
        
        @Positive Integer ID;
        
        @Pure
        @Chainable
        public @Nonnull OptionalFieldsManuallyCreatedStudentBuilder withID(@Positive int ID) {
            this.ID = ID;
            return new OptionalFieldsManuallyCreatedStudentBuilder(this);
        }
    
    }
    
    static class OptionalFieldsManuallyCreatedStudentBuilder extends IdManuallyCreatedStudentBuilder {
        
        OptionalFieldsManuallyCreatedStudentBuilder(@Nonnull IdManuallyCreatedStudentBuilder builder) {
            super(builder);
        }
        
        /* -------------------------------------------------- Buddies -------------------------------------------------- */
    
        @Nonnull @NonNullableElements @NonFrozen List<Student> buddies;
        
        @Pure
        @Chainable
        // With @Captured annotation!
        public @Nonnull OptionalFieldsManuallyCreatedStudentBuilder withBuddies(@Captured @Nonnull @NonNullableElements @NonFrozen List<Student> buddies) {
            this.buddies = buddies;
            return this;
        }
        
        /* -------------------------------------------------- Build -------------------------------------------------- */
        
        public @Nonnull Student build() {
            return new ManuallyGeneratedStudent(name, ID, buddies);
        }
        
    }
   
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    @Pure
    public static @Nonnull IdManuallyCreatedStudentBuilder withName(@Nonnull String name) {
        return new NameManuallyCreatedStudentBuilder().withName(name);
    }
    
    public static void main(String[] args) {
        //ManuallyCreatedStudentBuilder.withName("bla").withID(1).withBuddies(buddies).build();
        ManuallyCreatedStudentBuilder.withName("bla").withID(1).build();
    }
}
