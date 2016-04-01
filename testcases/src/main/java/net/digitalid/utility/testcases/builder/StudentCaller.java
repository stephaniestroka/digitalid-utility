package net.digitalid.utility.testcases.builder;

import javax.annotation.Nonnull;

import net.digitalid.utility.rootclass.RootClass;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * Description.
 */
@Utility
public abstract class StudentCaller extends RootClass {
    
    public static void main(String[] args) {
        @Nonnull Student student = ManuallyCreatedStudentBuilder.withName("Stephanie Stroka").withID(007).build();
    }
    
}
