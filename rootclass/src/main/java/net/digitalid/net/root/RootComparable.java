package net.digitalid.net.root;

import java.lang.reflect.Field;

import javax.annotation.Nonnull;

/**
 */
public abstract class RootComparable<C extends RootComparable> extends RootClass implements Comparable<C> {

    @SuppressWarnings("unchecked")
    @Override
    public int compareTo(C other) {
        int result = 0;
        try {
            for (@Nonnull Field field : classFields) {
                Class<?> fieldType = field.getType();
                if (Comparable.class.isAssignableFrom(fieldType)) {
                    Comparable<Object> comparableField = (Comparable<Object>) field.get(other);
                    result += comparableField.compareTo(field.get(this));
                    result = result << 1;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

}
