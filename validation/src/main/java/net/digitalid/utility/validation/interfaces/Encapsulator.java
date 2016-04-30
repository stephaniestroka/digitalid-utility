package net.digitalid.utility.validation.interfaces;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.functional.interfaces.Predicate;
import net.digitalid.utility.validation.annotations.getter.Default;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.value.Validated;

/**
 * An encapsulator encapsulates a value which is validated by the given validator.
 * 
 * @see Validated
 */
@Functional
public interface Encapsulator<V> {
    
    /* -------------------------------------------------- Value Validator -------------------------------------------------- */
    
    /**
     * Returns the validator which validates the value(s) of this encapsulator.
     */
    @Pure
    @Default("object -> true")
    public @Nonnull Predicate<? super V> getValueValidator();
    
}
