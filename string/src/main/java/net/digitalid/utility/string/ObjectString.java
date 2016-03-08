package net.digitalid.utility.string;

import java.lang.reflect.Field;
import java.util.Objects;


/**
 * This class provides useful operations to transform objects into strings through reflection.
 */
public class ObjectString {
    
    /**
     * An enumeration of styles which can be used for the string representation.
     */
    public static enum RepresentationStyle {
        DEFAULT
    }
    
    /**
     * Returns the nullable field value or a &lt;access failure&gt; error message if the field cannot be accessed.
     * 
     * @require field != null : "The field may not be null.;
     * @require object != null : "The object may not be null.;
     */
    private static Object getFieldValueOrErrorMessage(Field field, Object object) {
        field.setAccessible(true);
        try {
            return field.get(Objects.requireNonNull(object));
        } catch (IllegalAccessException exception) {
            return "<access failure>";
        }
    }
    
    /**
     * Returns a non-nullable doubly-quoted string if the field value is a string. Otherwise, toString() is called on the object.
     */
    private static String transformFieldValue(Object fieldValue) {
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
     * 
     * @require object != null : "The object may not be null.;
     * @require string != null : "The string may not be null.;
     * @require style != null : "The style may not be null.;
     */
    public static void toString(Object object, StringBuilder string, RepresentationStyle style) {
        final Class<?> type = object.getClass();
        final String typeName = type.getSimpleName();
        final Field[] fields = type.getDeclaredFields();
        switch (Objects.requireNonNull(style)) {
            case DEFAULT:
                string.append(typeName).append("(");
                for (int i = 0; i < fields.length; i++) {
                    final Field field = fields[i];
                    string.append(field.getName()).append(": ").append(transformFieldValue(getFieldValueOrErrorMessage(field, object)));
                    if (i > 0) { string.append(fields[i]).append(", "); }
                }
                string.append(")");
                break;
        }
    }
    
}
