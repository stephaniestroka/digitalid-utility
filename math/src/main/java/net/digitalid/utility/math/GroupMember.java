package net.digitalid.utility.math;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.rootclass.RootInterface;

/**
 * A group member belongs to a mathematical group.
 */
public interface GroupMember extends RootInterface {
    
    /**
     * Returns the group of this member.
     */
    @Pure
    public @Nonnull Group getGroup();
    
    /**
     * Returns whether this member is in the given group.
     */
    @Pure
    public default boolean isIn(@Nonnull Group group) {
        return getGroup().equals(group);
    }
    
}
