package net.digitalid.utility.storage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.method.PureWithSideEffects;
import net.digitalid.utility.collaboration.annotations.TODO;
import net.digitalid.utility.collaboration.enumerations.Author;
import net.digitalid.utility.conversion.interfaces.Converter;
import net.digitalid.utility.immutable.ImmutableList;
import net.digitalid.utility.storage.enumerations.ForeignKeyAction;
import net.digitalid.utility.storage.interfaces.Unit;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.string.CodeIdentifier;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This type models a database table that is described by the given converter.
 */
@Immutable
public abstract class Table<@Unspecifiable ENTRY, @Specifiable PROVIDED> extends Storage implements Converter<ENTRY, PROVIDED> {
    
    /* -------------------------------------------------- Parent Module -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nullable Module getParentModule() {
        return null;
    }
    
    /* -------------------------------------------------- Name -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull @CodeIdentifier @MaxSize(63) String getName() {
        return getTypeName();
    }
    
    /* -------------------------------------------------- Reference -------------------------------------------------- */
    
    /**
     * Returns the schema on which this table was created.
     */
    @Pure
    @TODO(task = "Change the return type to SQLSchemaName once this artifact can be moved to the database project again due to a more flexible generator mechanism.", date = "2017-04-21", author = Author.KASPAR_ETTER)
    public @Nonnull String getSchemaName(@Nonnull Unit unit) {
        return unit.getName();
    }
    
    /**
     * Returns the name of this table on the given unit.
     */
    @Pure
    @TODO(task = "Change the return type to SQLTableName once this artifact can be moved to the database project again due to a more flexible generator mechanism.", date = "2017-04-21", author = Author.KASPAR_ETTER)
    public @Nonnull String getTableName(@Nonnull Unit unit) {
        return getFullNameWithUnderlines();
    }
    
    /**
     * Returns the columns of the primary key of this table.
     */
    @Pure
    @TODO(task = "Change the return type to ImmutableList<SQLColumnName> once this artifact can be moved to the database project again due to a more flexible generator mechanism.", date = "2017-04-21", author = Author.KASPAR_ETTER)
    public abstract @Nonnull @NonNullableElements ImmutableList<String> getColumnNames(@Nonnull Unit unit);
    
    /* -------------------------------------------------- Actions -------------------------------------------------- */
    
    /**
     * Returns the action that determines what happens with entries that have a foreign key constraint on this table when an entry of this table is deleted.
     */
    @Pure
    public @Nonnull ForeignKeyAction getOnDeleteAction() {
        return ForeignKeyAction.CASCADE;
    }
    
    /**
     * Returns the action that determines what happens with entries that have a foreign key constraint on this table when an entry of this table is updated.
     */
    @Pure
    public @Nonnull ForeignKeyAction getOnUpdateAction() {
        return ForeignKeyAction.CASCADE;
    }
    
    /* -------------------------------------------------- Visitor -------------------------------------------------- */
    
    @Override
    @PureWithSideEffects
    public <@Specifiable RESULT, @Specifiable PARAMETER> RESULT accept(@Nonnull StorageVisitor<RESULT, PARAMETER> visitor, PARAMETER parameter) {
        return visitor.visit(this, parameter);
    }
    
}
