package net.digitalid.utility.storage;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.collaboration.annotations.TODO;
import net.digitalid.utility.collaboration.enumerations.Author;
import net.digitalid.utility.conversion.enumerations.Representation;
import net.digitalid.utility.conversion.interfaces.Converter;
import net.digitalid.utility.conversion.model.CustomField;
import net.digitalid.utility.conversion.model.CustomType;
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
public interface Table<@Unspecifiable ENTRY, @Specifiable PROVIDED> extends Storage, Converter<ENTRY, PROVIDED> {
    
    /* -------------------------------------------------- Name -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull @CodeIdentifier @MaxSize(63) String getName() {
        return getTypeName();
    }
    
    /* -------------------------------------------------- Reference -------------------------------------------------- */
    
    /**
     * Returns the schema on which this table was created.
     */
    @Pure
    @TODO(task = "Change the return type to SQLSchemaName once this artifact can be moved to the database project again due to a more flexible generator mechanism.", date = "2017-04-21", author = Author.KASPAR_ETTER)
    public default @Nonnull String getSchemaName(@Nonnull Unit unit) {
        return unit.getName();
    }
    
    /**
     * Returns the name of this table on the given unit.
     */
    @Pure
    @TODO(task = "Change the return type to SQLTableName once this artifact can be moved to the database project again due to a more flexible generator mechanism.", date = "2017-04-21", author = Author.KASPAR_ETTER)
    public default @Nonnull String getTableName(@Nonnull Unit unit) {
        return getFullNameWithUnderlines();
    }
    
    @Pure
    public static void addColumns(@Nonnull CustomField field, @Nullable String prefix, @Modified @NonCaptured @Nonnull @NonNullableElements List<String> columns) {
        final @Nonnull CustomType type = field.getCustomType();
        final @Nonnull String name = (prefix != null ? prefix + "_" : "") + field.getName();
        if (type instanceof CustomType.CustomConverterType) {
            final @Nonnull Converter<?, ?> converter = ((CustomType.CustomConverterType) type).getConverter();
            final @Nonnull @NonNullableElements ImmutableList<CustomField> fields = converter.getFields(Representation.INTERNAL);
            for (@Nonnull CustomField customField : fields) { addColumns(customField, name, columns); }
        } else {
            columns.add(name);
        }
    }
    
    /**
     * Returns the columns of the primary key of this table.
     */
    @Pure
    @TODO(task = "Change the return type to ImmutableList<SQLColumnName> once this artifact can be moved to the database project again due to a more flexible generator mechanism.", date = "2017-04-21", author = Author.KASPAR_ETTER)
    public default @Nonnull @NonNullableElements ImmutableList<String> getColumnNames(@Nonnull Unit unit) {
        @Nonnull @NonNullableElements List<String> columns = new LinkedList<>();
        for (@Nonnull CustomField field : getFields(Representation.INTERNAL)) { addColumns(field, null, columns); }
        return ImmutableList.withElementsOfCollection(columns);
    }
    
    /* -------------------------------------------------- Actions -------------------------------------------------- */
    
    /**
     * Returns the action that determines what happens with entries that have a foreign key constraint on this table when an entry of this table is deleted.
     */
    @Pure
    public default @Nonnull ForeignKeyAction getOnDeleteAction() {
        return ForeignKeyAction.CASCADE;
    }
    
    /**
     * Returns the action that determines what happens with entries that have a foreign key constraint on this table when an entry of this table is updated.
     */
    @Pure
    public default @Nonnull ForeignKeyAction getOnUpdateAction() {
        return ForeignKeyAction.CASCADE;
    }
    
}
