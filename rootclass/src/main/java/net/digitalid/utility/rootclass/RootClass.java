package net.digitalid.utility.rootclass;

import net.digitalid.utility.annotations.method.CallSuper;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.collaboration.annotations.TODO;
import net.digitalid.utility.collaboration.enumerations.Author;
import net.digitalid.utility.collaboration.enumerations.Priority;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * The root class for all custom classes whose initialization does not throw a checked exception.
 */
@Mutable
public abstract class RootClass extends RootClassWithException<RuntimeException> {
    
//    @Pure
//    @Override
//    @CallSuper
//    @TODO(task = "Remove this overriding once the subclass generator can handle generic exception types.", date = "2016-08-04", author = Author.KASPAR_ETTER, assignee = Author.STEPHANIE_STROKA, priority = Priority.MIDDLE)
//    protected void initialize() throws RuntimeException {
//        super.initialize();
//    }
    
}
