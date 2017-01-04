package net.digitalid.utility.exceptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

@Immutable
class CaseExceptionSubclass extends CaseException {
    
    /* -------------------------------------------------- Variable -------------------------------------------------- */
    
    private final @Nonnull String variable;
    
    @Pure
    @Override
    public @Nonnull String getVariable() {
        return this.variable;
    }
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    private final @Nullable Object value;
    
    @Pure
    @Override
    public @Nullable Object getValue() {
        return this.value;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    CaseExceptionSubclass(@Nonnull String variable, @Nullable Object value) {
        this.variable = variable;
        this.value = value;
    }
    
}
