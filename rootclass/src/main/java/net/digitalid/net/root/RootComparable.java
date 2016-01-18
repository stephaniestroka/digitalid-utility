package net.digitalid.net.root;

import java.lang.reflect.Field;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.validation.state.Pure;
import net.digitalid.utility.validation.state.Stateless;

/**
 * The RootComparable class provides a useful default compareTo() method which compares the (distinguishing) class fields
 * of two object against each other.
 */
@Stateless
public abstract class RootComparable<C extends RootComparable> extends RootClass implements Comparable<C> {
    
    /**
     * Returns a positive value, if this object is greater than the specified object, a negative value, if this object is lesser than the specified object, or zero, if this object is equal to the specified object.
     */
    @Pure
    @Override
    @SuppressWarnings("unchecked")
    public int compareTo(@Nullable C other) {
        if (other == null) {
            return 1;
        }
        try {
            for (@Nonnull Field field : classFields) {
                Class<?> fieldType = field.getType();
                if (Comparable.class.isAssignableFrom(fieldType)) {
                    Comparable<Object> comparableField = (Comparable<Object>) field.get(other);
                    int result = comparableField.compareTo(field.get(this));
                    if (result != 0) {
                        return result;
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
}
