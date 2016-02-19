package net.digitalid.utility.validation.validator;

import javax.annotation.Nonnull;

import net.digitalid.utility.contracts.exceptions.ContractViolationException;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.string.JavaExpression;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class wraps a {@link #getCondition() condition} and {@link #getMessage() message} for annotation processing.
 * 
 * @see AnnotationValidator
 */
@Immutable
public class GeneratedContract {
    
    /* -------------------------------------------------- Condition -------------------------------------------------- */
    
    private final @Nonnull @JavaExpression String condition;
    
    /**
     * Returns the condition which is evaluated during runtime to determine whether the contract is fulfilled.
     */
    @Pure
    public @Nonnull @JavaExpression String getCondition() {
        return condition;
    }
    
    /* -------------------------------------------------- Message -------------------------------------------------- */
    
    private final @Nonnull String message;
    
    /**
     * Returns the message with which a {@link ContractViolationException} is thrown if the condition is violated.
     */
    @Pure
    public @Nonnull String getMessage() {
        return message;
    }
    
    @Pure
    public @Nonnull String getMessageInDoubleQuotes() {
        return "\"" + getMessage() + "\"";
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected GeneratedContract(@Nonnull @JavaExpression String condition, @Nonnull String message) {
        this.condition = condition;
        this.message = message;
    }
    
    /**
     * Returns an object that wraps the given {@link #getCondition() condition} and {@link #getMessage() message}.
     */
    @Pure
    public static @Nonnull GeneratedContract with(@Nonnull @JavaExpression String condition, @Nonnull String message) {
        return new GeneratedContract(condition, message);
    }
    
}
