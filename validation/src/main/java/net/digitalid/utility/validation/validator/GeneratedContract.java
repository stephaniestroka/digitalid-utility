package net.digitalid.utility.validation.validator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.lang.model.element.Element;

import net.digitalid.utility.contracts.exceptions.ContractViolationException;
import net.digitalid.utility.string.FormatString;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.state.Unmodifiable;
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
    
    /* -------------------------------------------------- Arguments -------------------------------------------------- */
    
    private final @Unmodifiable @Nonnull @NonNullableElements List<String> arguments;
    
    /**
     * Returns the arguments with which the {@link #getMessage() message} is {@link FormatString#format(java.lang.CharSequence, java.lang.Object...) formatted}.
     */
    @Pure
    public @Unmodifiable @Nonnull @NonNullableElements List<String> getArguments() {
        return arguments;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected GeneratedContract(@Nonnull @JavaExpression String condition, @Nonnull String message, @Nonnull String... arguments) {
        this.condition = condition;
        this.message = message;
        this.arguments = Collections.unmodifiableList(Arrays.asList(arguments));
    }
    
    /**
     * Returns an object that wraps the given {@link #getCondition() condition}, {@link #getMessage() message} and {@link #getArguments() arguments}.
     */
    @Pure
    public static @Nonnull GeneratedContract with(@Nonnull @JavaExpression String condition, @Nonnull String message, @Nonnull String... arguments) {
        return new GeneratedContract(condition, message, arguments);
    }
    
    /**
     * Returns an object that wraps the given {@link #getCondition() condition} and {@link #getMessage() message} and {@link #getArguments() arguments}.
     * Each number sign in the condition and the message is replaced with the {@link AnnotationValidator#getName(javax.lang.model.element.Element) name} of the element.
     */
    @Pure
    public static @Nonnull GeneratedContract with(@Nonnull String condition, @Nonnull String message, @Nonnull Element element) {
        final @Nonnull String name = AnnotationValidator.getName(element);
        return new GeneratedContract(condition.replace("#", name), message.replace("#", name), name);
    }
    
}
