package net.digitalid.utility.validation.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.validation.state.Pure;
import net.digitalid.utility.validation.validator.annotation.ValidateWith;
import net.digitalid.utility.validation.validator.exceptions.ValidationFailedException;

/**
 * Validates the correctness of an object. 
 * The validation annotations provided in the {@link net.digitalid.utility.validation package} can be used ensure that an annotated field
 * holds a value that complies with the statement of the annotation.
 * Additionally, a class might also want to ensure a class invariant. Therefore, the validator checks whether a 'classInvariant' method is
 * available for the checked type. If so, the method is invoked.
 * Upon unsuccessful validation, a {@link ValidationFailedException} is thrown, otherwise the validation completes without errors.
 */
public abstract class Validator<A extends Annotation> {
    
    /* -------------------------------------------------- Annotation validation -------------------------------------------------- */
    
    /**
     * Checks a fieldValue against a given validation annotation.
     */
    @Pure
    @SuppressWarnings("unchecked")
    private void validateAnnotation(@Nullable Object fieldValue, @Nonnull Annotation annotation) throws ValidationFailedException {
        validate(fieldValue, (A) annotation);
    }
    
    /**
     * Checks a fieldValue against a given validation annotation.
     */
    @Pure
    public abstract void validate(@Nullable Object fieldValue, @Nonnull A annotation) throws ValidationFailedException;
    
    /* -------------------------------------------------- Helper methods -------------------------------------------------- */
    
    /**
     * Helper method that throws a validation-failed exception if the given expression is false.
     */
    protected void assertTrue(boolean expression, @Nonnull String message) throws ValidationFailedException {
        if (!expression) {
            throw ValidationFailedException.get(message);
        }
    }
    
    /* -------------------------------------------------- Validator lookup -------------------------------------------------- */
    
    /**
     * The map of validators registered for validator annotations.
     */
    private static @Nonnull Map<Class<? extends Annotation>, Validator<?>> validators = new HashMap<>();
    
    public static @Nonnull Validator<? extends Annotation> getOrCreateValidator(@Nonnull Class<? extends Annotation> annotationClass) throws ValidationFailedException {
        @Nullable Validator<? extends Annotation> validator = validators.get(annotationClass);
        if (validator == null) {
            synchronized (Validator.class) {
                validator = validators.get(annotationClass);
                if (validator == null) {
                    final @Nonnull ValidateWith validateWith = annotationClass.getAnnotation(ValidateWith.class);
                    final @Nonnull Class<? extends Validator<? extends Annotation>> validatorClass = validateWith.value();
                    try {
                        final @Nonnull Method validatorGetter = validatorClass.getMethod("get");
                        validator = (Validator<? extends Annotation>) validatorGetter.invoke(null);
                        validators.put(annotationClass, validator);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        throw ValidationFailedException.get("Failed to initiate validator for annotation '" + annotationClass.getSimpleName() + "'", e);
                    }
                }
            } 
        }
        return validator;
    }
    
    /**
     * Checks whether the given annotation can be validated.
     */
    private static boolean canBeValidated(Class<? extends Annotation> annotationClass) {
        return annotationClass.isAnnotationPresent(ValidateWith.class);
    }
    
    /* -------------------------------------------------- Field value validation -------------------------------------------------- */
    
    /**
     * Checks whether the field values of the given object comply with the given validation annotation(s).
     */
    @Pure
    private static void validateFieldValues(@Nonnull Object object) throws ValidationFailedException {
        final @Nonnull Field[] fields = object.getClass().getFields();
        for (@Nonnull Field field : fields) {
            final @Nonnull Annotation[] annotations = field.getAnnotations();
            for (@Nonnull Annotation annotation : annotations) {
                final @Nonnull Class<? extends Annotation> annotationClass = annotation.annotationType();
                if (canBeValidated(annotationClass)) {
                    final @Nonnull Validator<? extends Annotation> validator = getOrCreateValidator(annotationClass);
                    @Nullable Object fieldValue;
                    try {
                        field.setAccessible(true);
                        fieldValue = field.get(object);
                    } catch (IllegalAccessException e) {
                        fieldValue = null;
                    }
                    validator.validateAnnotation(fieldValue, field.getAnnotation(annotationClass));
                }
            }
        }
    }
    
    /* -------------------------------------------------- Class invariant validation -------------------------------------------------- */
    
    /**
     * Validates the class invariant by invoking the method 'classInvariant' of the given object.
     */
    private static void validateClassInvariant(@Nonnull Object object) throws ValidationFailedException {
        try {
            final @Nonnull Method method = object.getClass().getMethod("classInvariant");
            try {
                method.invoke(object);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw ValidationFailedException.get("Failed to validate class invariant for type '" + object.getClass().getSimpleName() + "'.", e);
            }
        } catch (NoSuchMethodException e) {
            // No classInvariant declared.
        }
    }
    
    /* -------------------------------------------------- Public API -------------------------------------------------- */
    
    /**
     * Validates a given object by checking whether the field values comply with the field annotations and whether the class invariant holds.
     * A validation-failed-exception is thrown in case the validation fails.
     */
    @Pure
    public static void validate(@Nonnull Object object) throws ValidationFailedException {
        validateFieldValues(object);
        validateClassInvariant(object);
    }
    
    /**
     * Validates that the type of a given field is either in the array of accepted types or is a subtype of the types in the accepted types array.
     * A validation-failed-exception is thrown in case the field type is not an accepted type.
     */
    @Pure
    public static void checkType(@Nonnull Field field, @Nonnull Class<?> acceptedTypes[]) throws AssertionError {
        final @Nonnull Class<?> actualType = field.getType();
        for (@Nonnull Class<?> acceptedType : acceptedTypes) {
            if (acceptedType.isAssignableFrom(actualType)) {
                return;
            }
        }
        throw new AssertionError("The type '" + actualType + "' of the given field is not in the list of accepted types: " + Arrays.toString(acceptedTypes));
    }
}
