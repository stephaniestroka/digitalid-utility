package net.digitalid.utility.string;

import java.lang.reflect.Field;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.Utiliy;

/**
 * This class provides useful operations to transform objects into strings through reflection.
 */
@Utiliy
public class ObjectToString {
    
    /**
     * An enumeration of styles which can be used for the string representation.
     */
    @Immutable
    public static enum RepresentationStyle {
        DEFAULT
    }
    
    /**
     * Returns the field value or a &lt;access failure&gt; error message if the field cannot be accessed.
     */
    @Pure
    private static @Nullable Object getFieldValueOrErrorMessage(@Nonnull Field field, @Nonnull Object object) {
        field.setAccessible(true);
        try {
            return field.get(object);
        } catch (@Nonnull IllegalAccessException exception) {
            return "<access failure>";
        }
    }
    
    /**
     * Returns a doubly-quoted string if the field value is a string. Otherwise, toString() is called on the object.
     */
    @Pure
    private static @Nonnull String transformFieldValue(@Nullable Object fieldValue) {
        if (fieldValue instanceof String) {
            return "\"" + fieldValue + "\"";
        } else if (fieldValue == null) {
            return "null";
        } else {
            return fieldValue.toString();
        }
    }
    
    /**
     * Appends a representation of the given object in the given {@link RepresentationStyle style} to the given string builder.
     */
    @Pure
    public static void toString(@Nonnull Object object, @Nonnull RepresentationStyle style, @Nonnull StringBuilder string) {
        final @Nonnull Class<?> type = object.getClass();
        final @Nonnull String typeName = type.getSimpleName();
        final @Nonnull Field[] fields = type.getDeclaredFields();
        switch (style) {
            case DEFAULT:
                string.append(typeName).append("(");
                for (int i = 0; i < fields.length; i++) {
                    final @Nonnull Field field = fields[i];
                    string.append(field.getName()).append(": ").append(transformFieldValue(getFieldValueOrErrorMessage(field, object)));
                    if (i > 0) { string.append(fields[i]).append(", "); }
                }
                string.append(")");
                break;
        }
    }
    
}
