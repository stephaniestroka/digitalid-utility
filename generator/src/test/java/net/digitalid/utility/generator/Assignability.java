package net.digitalid.utility.generator;

import java.util.List;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.testing.AssignableTo;
import net.digitalid.utility.validation.annotations.testing.UnassignableTo;
import net.digitalid.utility.validation.annotations.type.Mutable;

@Mutable
@GenerateSubclass
@SuppressWarnings("PublicField")
public abstract class Assignability {
    
    @Pure
    public abstract @AssignableTo(CharSequence.class) String getStringAssignableToCharSequence();
    
    @Pure
    public abstract @AssignableTo(Comparable.class) String getStringAssignableToComparable();
    
    @Pure
    public abstract @UnassignableTo(Integer.class) String getStringUnassignableToInteger();
    
    @Pure
    public abstract @UnassignableTo(List.class) String getStringUnassignableToList();
    
}
