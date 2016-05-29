package net.digitalid.utility.validation.contract;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.contracts.exceptions.ContractViolationException;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.string.JavaExpression;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.validator.ContractGenerator;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 * This class wraps a {@link #getCondition() condition} and {@link #getMessage() message} for annotation processing.
 * 
 * @see ContractGenerator
 */
@Immutable
public class Contract {
    
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
    
    private final @Nonnull FiniteIterable<@Nonnull String> arguments;
    
    /**
     * Returns the arguments with which the {@link #getMessage() message} is {@link Strings#format(java.lang.CharSequence, java.lang.Object...) formatted}.
     */
    @Pure
    public @Nonnull FiniteIterable<@Nonnull String> getArguments() {
        return arguments;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected Contract(@Nonnull @JavaExpression String condition, @Nonnull String message, @Captured @Nonnull @NonNullableElements String... arguments) {
        this.condition = condition;
        this.message = message;
        this.arguments = FiniteIterable.of(arguments);
    }
    
    /**
     * Returns an object that wraps the given {@link #getCondition() condition}, {@link #getMessage() message} and {@link #getArguments() arguments}.
     */
    @Pure
    public static @Nonnull Contract with(@Nonnull @JavaExpression String condition, @Nonnull String message, @Captured @Nonnull @NonNullableElements String... arguments) {
        return new Contract(condition, message, arguments);
    }
    
    /* -------------------------------------------------- Constructors for Contracts that use the Element -------------------------------------------------- */
    
    /**
     * Returns an object that wraps the given {@link #getCondition() condition} and {@link #getMessage() message} with the element name as {@link #getArguments() argument}.
     * Each number sign in the condition and the message is replaced with the {@link ValueAnnotationValidator#getName(javax.lang.model.element.Element) name} of the element.
     */
    @Pure
    public static @Nonnull Contract with(@Nonnull String condition, @Nonnull String message, @Nonnull Element element, @Nonnull String suffix) {
        final @Nonnull String name = ValueAnnotationValidator.getName(element);
        return new Contract(condition.replace("#", name), message.replace("#", Strings.decamelize(name)), name + (suffix.isEmpty() ? "" : " == null ? null : " + name + suffix));
    }
    
    /**
     * Returns an object that wraps the given {@link #getCondition() condition} and {@link #getMessage() message} with the element name as {@link #getArguments() argument}.
     * Each number sign in the condition and the message is replaced with the {@link ValueAnnotationValidator#getName(javax.lang.model.element.Element) name} of the element.
     */
    @Pure
    public static @Nonnull Contract with(@Nonnull String condition, @Nonnull String message, @Nonnull Element element) {
        return with(condition, message, element, "");
    }
    
    /* -------------------------------------------------- Constructors for Contracts that use the Element and Annotation Value -------------------------------------------------- */
    
    /**
     * Returns an object that wraps the given {@link #getCondition() condition} and {@link #getMessage() message} with the element name as {@link #getArguments() argument}.
     * Each number sign in the condition and the message is replaced with the {@link ValueAnnotationValidator#getName(javax.lang.model.element.Element) name} of the element.
     * Each at sign in the condition and the message is replaced with the {@link AnnotationValue#getValue() value} of the given annotation mirror.
     */
    @Pure
    public static @Nonnull Contract with(@Nonnull String condition, @Nonnull String message, @Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull String suffix) {
        final @Nonnull String name = ValueAnnotationValidator.getName(element);
        final @Nullable AnnotationValue annotationValue = ProcessingUtility.getAnnotationValue(annotationMirror);
        final @Nonnull String value = String.valueOf(annotationValue != null ? annotationValue.getValue() : null);
        return new Contract(condition.replace("#", name).replace("@", value), message.replace("#", Strings.decamelize(name)).replace("@", value), name + (suffix.isEmpty() ? "" : " == null ? null : " + name + suffix));
    }
    
    /**
     * Returns an object that wraps the given {@link #getCondition() condition} and {@link #getMessage() message} with the element name as {@link #getArguments() argument}.
     * Each number sign in the condition and the message is replaced with the {@link ValueAnnotationValidator#getName(javax.lang.model.element.Element) name} of the element.
     * Each at sign in the condition and the message is replaced with the {@link AnnotationValue#getValue() value} of the given annotation mirror.
     */
    @Pure
    public static @Nonnull Contract with(@Nonnull String condition, @Nonnull String message, @Nonnull Element element, @Nonnull AnnotationMirror annotationMirror) {
        return with(condition, message, element, annotationMirror, "");
    }
    
}
