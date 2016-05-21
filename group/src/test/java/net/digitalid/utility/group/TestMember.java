package net.digitalid.utility.group;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.group.annotations.InGroup;
import net.digitalid.utility.group.annotations.InSameGroup;
import net.digitalid.utility.rootclass.RootClass;
import net.digitalid.utility.validation.annotations.type.Immutable;

@Immutable
@GenerateSubclass
public abstract class TestMember extends RootClass implements GroupMember {
    
    @Pure
    public @Nonnull GroupInterface getSpecificGroup() {
        return getGroup();
    }
    
    @Impure
    public void setInGroup(@InGroup("getSpecificGroup()") GroupMember member) {}
    
    @Impure
    public void setInSameGroup(@InSameGroup GroupMember member) {}
    
}
