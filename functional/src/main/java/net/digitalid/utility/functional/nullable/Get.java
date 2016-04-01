package net.digitalid.utility.functional.nullable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * This utility class allows to replace nullable values with non-nullable default values.
 */
@Utility
public class Get {
    
    /**
     * Returns the given nullable value if it is not null or the given default value otherwise.
     */
    @Pure
    public static <T> @Nonnull T valueOrDefault(@Nullable T nullableValue, @Nonnull T defaultValue) {
        return nullableValue != null ? nullableValue : defaultValue;
    }
    
}
