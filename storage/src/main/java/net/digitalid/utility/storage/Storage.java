package net.digitalid.utility.storage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.CallSuper;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.method.PureWithSideEffects;
import net.digitalid.utility.rootclass.RootClass;
import net.digitalid.utility.validation.annotations.generation.Default;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.string.CodeIdentifier;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This type models a node in the storage tree.
 * 
 * @see Module
 * @see Table
 */
@Mutable
public abstract class Storage extends RootClass {
    
    /* -------------------------------------------------- Parent Module -------------------------------------------------- */
    
    /**
     * Returns the parent module to which this storage belongs.
     */
    @Pure
    @Default("null") // TODO: This should not be necessary but it still is.
    public abstract @Nullable Module getParentModule();
    
    @Pure
    @Override
    @CallSuper
    protected void initialize() {
        super.initialize();
        
        final @Nullable Module parentModule = getParentModule();
        if (parentModule != null) { parentModule.addChildStorage(this); }
    }
    
    /* -------------------------------------------------- Name -------------------------------------------------- */
    
    /**
     * Returns the name of this storage.
     */
    @Pure
    public abstract @Nonnull @CodeIdentifier @MaxSize(63) String getName();
    
    /**
     * Returns the full name of this storage with the given delimiter between the names of the parent modules in the given direction.
     */
    @Pure
    public @Nonnull String getFullName(@Nonnull String delimiter, boolean rootToLeaf) {
        final @Nullable Module parentModule = getParentModule();
        if (parentModule != null) {
            final @Nonnull String parentName = parentModule.getFullName(delimiter, rootToLeaf);
            return rootToLeaf ? parentName + delimiter + getName() : getName() + delimiter + parentName;
        } else {
            return getName();
        }
    }
    
    /**
     * Returns the full name of this storage with periods between the names of the parent modules from leaf to root.
     */
    @Pure
    // TODO: @Cached
    public @Nonnull String getFullNameWithPeriods() {
        return getFullName(".", false);
    }
    
    /**
     * Returns the full name of this storage with underlines between the names of the parent modules from root to leaf.
     */
    @Pure
    // TODO: @Cached
    public @Nonnull @CodeIdentifier String getFullNameWithUnderlines() {
        return getFullName("_", true);
    }
    
    /* -------------------------------------------------- Visitor -------------------------------------------------- */
    
    /**
     * Accepts the given visitor with the given parameter and returns the result of the visitor.
     */
    @PureWithSideEffects
    public abstract <@Specifiable RESULT, @Specifiable PARAMETER> RESULT accept(@Nonnull StorageVisitor<RESULT, PARAMETER> visitor, PARAMETER parameter);
    
}
