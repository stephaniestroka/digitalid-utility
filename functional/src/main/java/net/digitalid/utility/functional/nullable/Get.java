package net.digitalid.utility.functional.nullable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * This utility class allows to replace nullable values with non-nullable default values.
 * 
 * @see Evaluate
 */
@Utility
public abstract class Get {
    
    /**
     * Returns the given nullable value if it is not null or the given default value otherwise.
     */
    @Pure
    public static <@Unspecifiable TYPE> @Nonnull TYPE valueOrDefault(@NonCaptured @Unmodified @Nullable TYPE nullableValue, @NonCaptured @Unmodified @Nonnull TYPE defaultValue) {
        return nullableValue != null ? nullableValue : defaultValue;
    }
    
}
