package net.digitalid.net.root;

import net.digitalid.utility.collections.annotations.elements.NonNullableElements;
import net.digitalid.utility.collections.freezable.FreezableArrayList;
import net.digitalid.utility.collections.readonly.ReadOnlyList;
import net.digitalid.utility.reflection.ReflectonUtility;
import net.digitalid.utility.reflection.exceptions.StructureException;
import net.digitalid.utility.string.StringUtility;
import net.digitalid.utility.validation.state.Pure;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;

/**
 * The root of all objects in the digital id library.
 * This class overrides toString() to provide a convenient, general representation of a digital id object as a string,
 * it overrides equals() using a more useful comparison method than java.lang.Object and
 * it implements object validation.
 */
public abstract class RootClass {

    /* -------------------------------------------------- Fields Cache -------------------------------------------------- */

    protected @Nonnull @NonNullableElements ReadOnlyList<Field> classFields;

    protected RootClass() {
        try {
            classFields = ReflectonUtility.getReconstructionFields(this.getClass());
        } catch (StructureException e) {
            classFields = FreezableArrayList.get(this.getClass().getDeclaredFields());
        }
    }

    /**
     * Returns a general representation of this object using the default style, containing the type name, the fields of the object and it's field values.
     * Example: Person(name: "Stephanie Stroka", age: "28")
     */
    @Pure
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        StringUtility.toString(this, StringUtility.RepresentationStyle.DEFAULT, string);

        return string.toString();
    }

    /**
     * Returns false if the given object is not equal to the current object, using the heuristics that fields of a class,
     * which are handed to the class via the recovery method or the constructor, must be equal to each other so that the objects are considered equal.
     */
    @Override
    public boolean equals(@Nullable Object other) {
        if (other == null || !other.getClass().equals(this.getClass())) {
            return false;
        }
        try {
            for (@Nonnull Field field : classFields) {
                field.setAccessible(true);
                @Nullable Object fieldValueThis = field.get(this);
                @Nullable Object fieldValueOther = field.get(other);
                if (fieldValueThis == null && fieldValueOther == null) {
                    continue;
                } else if (fieldValueThis == null || fieldValueOther == null) {
                    return false;
                } else if (!fieldValueThis.equals(fieldValueOther)) {
                    return false;
                }
            }
            return true;
        } catch (IllegalAccessException e) {
            return super.equals(other);
        }
    }

    /**
     * Computes and returns the hash code of this object, using the fields of the class which are handed to the class via the recovery method or the constructor.
     */
    @Override
    public int hashCode() {
        try {
            int prime = 92821;
            int result = 46411;
            for (@Nonnull Field field : classFields) {
                     field.setAccessible(true);
                int c = 0;
                @Nullable Object fieldValue = field.get(this);
                if (fieldValue != null) {
                    c = fieldValue.hashCode();
                }
                result = prime * result + c;
            }
            return result;
        } catch (IllegalAccessException e) {
            return super.hashCode();
        }
    }

}
