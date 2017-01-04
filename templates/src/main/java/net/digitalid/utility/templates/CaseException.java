package net.digitalid.utility.templates;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.exceptions.InternalException;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * A case exception indicates that a case is not covered because it was added after the control flow had been implemented.
 * For example, a case exception is thrown if a switch statement over an enumeration needs a default case.
 */
@Immutable
@GenerateBuilder
@GenerateSubclass
public abstract class CaseException extends InternalException {
    
    /* -------------------------------------------------- Variable -------------------------------------------------- */
    
    /**
     * Returns the name of the variable with the unexpected value.
     */
    @Pure
    public abstract @Nonnull String getVariable();
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the value of the given variable that was not expected.
     */
    @Pure
    public abstract @Nullable Object getValue();
    
    /* -------------------------------------------------- Message -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String getMessage() {
        return "The case where '" + getVariable() + "' is '" + getValue() + "' is not covered.";
    }
    
}
