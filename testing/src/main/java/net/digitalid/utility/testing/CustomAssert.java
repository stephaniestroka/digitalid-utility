package net.digitalid.utility.testing;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.functional.fixes.Quotes;
import net.digitalid.utility.functional.interfaces.UnaryFunction;

import org.junit.Assert;

import static org.hamcrest.core.IsEqual.*;
import static org.hamcrest.core.IsNot.*;
import static org.hamcrest.core.IsNull.*;

/**
 * Provides a set of custom assert methods that make testing easier and produces clear error messages.
 */
public abstract class CustomAssert {
    
    /* -------------------------------------------------- ExpectationEvaluator -------------------------------------------------- */
    
    /**
     * Helper interface that only allows access to the {@link ExpectationEvaluator#of(Object)} method.
     */
    public interface ExpectationEvaluatorInterface1<T> {
        
        /**
         * Defines that the test case is conducted on the given object.
         */
        public ExpectationEvaluatorInterface2 of(@Nullable T object);
        
    }
    
    /**
     * Helper interface that only allows access to the {@link ExpectationEvaluator#toBe(Object)} method.
     */
    public interface ExpectationEvaluatorInterface2 {
        
        /**
         * Sets the expected value and executes the test case. A detailed error message is printed if the test case failed.
         */
        public void toBe(@Nullable Object value);
        
        /**
         * Executes the test case and evaluates whether the tested object results in a null value after applying the function. A detailed error message is printed if the test case failed.
         */
        public void toBeNull();
    
        /**
         * Executes the test case and evaluates whether the tested object results in a non-null value after applying the function. A detailed error message is printed if the test case failed.
         */
        public void toBeNonNull();
    
    }
    
    /**
     * Collects and evaluates test cases that verify that a function applied to a given object results in an expected value.
     */
    public static class ExpectationEvaluator<T> implements ExpectationEvaluatorInterface1<T>, ExpectationEvaluatorInterface2 {
    
        /**
         * The subject of the test case.
         */
        protected final @Nonnull String testSubject;
    
        /**
         * The function applied to the object.
         */
        protected final @Nonnull UnaryFunction<@Nullable T, @Nullable ?> function;
    
        /**
         * The object that is tested.
         */
        protected @Nullable T object;
    
        /**
         * Creates an expectation evaluator for testing a given function. The subject of the test case is defined in the parameter testSubject.
         */
        ExpectationEvaluator(@Nonnull String testSubject, @Nonnull UnaryFunction<@Nullable T, @Nullable ?> function) {
            this.testSubject = testSubject;
            this.function = function;
        }
    
        @Override
        public ExpectationEvaluatorInterface2 of(@Nullable T object) {
            this.object = object;
            return this;
        }
        
        private @Nonnull String getErrorMessage(@Nullable Object value) {
            if (object == null) {
                return "Expected " + testSubject + " to be " + Quotes.inSingle(value) + " but it was 'null'.";
            } else {
                return "Expected " + testSubject + " of " + object.getClass().getSimpleName() + " to be " + Quotes.inSingle(value) + " but it was " + Quotes.inSingle(function.evaluate(object)) + ".";
            }
        }
        
        @Override
        public void toBe(@Nullable Object value) {
            Assert.assertThat(getErrorMessage(value), function.evaluate(object), equalTo(value));
        }
    
        @Override
        public void toBeNull() {
            Assert.assertThat(getErrorMessage(null), function.evaluate(object), nullValue());
        }
    
        @Override
        public void toBeNonNull() {
            Assert.assertThat(getErrorMessage(null), function.evaluate(object), not(nullValue()));
        }
    
    }
    
    /**
     * Returns a new expectation evaluator object for a given test subject. The test is conducted by evaluating the function on a certain object.
     */
    public static <T> ExpectationEvaluator<T> expecting(@Nonnull String testSubject, @Nonnull UnaryFunction<T, Object> unaryFunction) {
        return new ExpectationEvaluator<>(testSubject, unaryFunction);
    }
    
    /**
     * Returns a new expectation evaludator object for a given test subject. The test is conducted on the given object.
     */
    public static <T> ExpectationEvaluatorInterface2 expecting(@Nonnull String testSubject, @Nonnull T object) {
        return new ExpectationEvaluator<>(testSubject, o -> o).of(object);
    }
    
    /* -------------------------------------------------- Expectation Equality Evaluator -------------------------------------------------- */
    
    /**
     * Helper interface that only allows access to the {@link ExpectationEqualityEvaluator#of(Object)} method.
     */
    public interface ExpectationEqualityEvaluatorInterface1<T> extends ExpectationEvaluatorInterface1<T> {
        
        public @Nonnull ExpectationEqualityEvaluatorInterface2<T> of(@Nullable T object);
        
    }
    
    /**
     * Helper interface that only allows access to the {@link ExpectationEqualityEvaluator#and(Object)} method.
     */
    public interface ExpectationEqualityEvaluatorInterface2<T> extends ExpectationEvaluatorInterface2 {
        
        public void and(@Nullable T object);
        
    }
    
    /**
     * Collects and evaluates test cases that verify that a function applied to two given objects results in an equal or non-equal value.
     */
    static class ExpectationEqualityEvaluator<T> extends ExpectationEvaluator<T> implements ExpectationEqualityEvaluatorInterface1<T>, ExpectationEqualityEvaluatorInterface2<T> {
    
        /**
         * The first object that is going to be evaluated.
         */
        private @Nullable T first;
    
        /**
         * If set to true, the class checks whether the result of the function applied on the two objects is equal. Otherwise, the class checks whether the result is unequal.
         */
        private final boolean equal;
        
        /**
         * Creates a new expectation equality evaluator that tests whether 
         */
        ExpectationEqualityEvaluator(@Nonnull String testSubject, @Nonnull UnaryFunction<@Nullable T, @Nullable ?> function, boolean equal) {
            super(testSubject, function);
            this.equal = equal;
        }
    
        /**
         * Sets the first object that is going to be evaluated.
         */
        public @Nonnull ExpectationEqualityEvaluatorInterface2<T> of(@Nullable T object) {
            this.first = object;
            return this;
        }
    
        /**
         * Sets the second object and evaluates the equality of the result of the given objects evaluated with the given function.
         */
        public void and(@Nullable T second) {
            if (equal) {
                Assert.assertThat("Expected same " + testSubject + " for objects " + Quotes.inSingle(first) + " and " + Quotes.inSingle(second) + ".", function.evaluate(first), equalTo(function.evaluate(second)));
            } else {
                Assert.assertThat("Expected different " + testSubject + " for objects " + Quotes.inSingle(first) + " and " + Quotes.inSingle(second) + ".", function.evaluate(first), not(equalTo(function.evaluate(second))));
            }
        }
    
    }
    
    /**
     * Returns a new expectation equality evaluator object for a given test subject. The test is evaluates the equality of two objects.
     */
    public static <T> ExpectationEqualityEvaluatorInterface1<T> expectingEqual(@Nonnull String testSubject) {
        return expectingEqual(testSubject, object -> object);
    }
    
    /**
     * Returns a new expectation equality evaluator object for a given test subject. The test is evaluates the equality of the results of a function applied on two objects.
     */
    public static <T> ExpectationEqualityEvaluatorInterface1<T> expectingEqual(@Nonnull String testSubject, @Nonnull UnaryFunction<T, ?> function) {
        return new ExpectationEqualityEvaluator<>(testSubject, function, true);
    }
    
    /**
     * Returns a new expectation equality evaluator object for a given test subject. The test is evaluates whether two objects are different.
     */
    public static <T> ExpectationEqualityEvaluatorInterface1<T> expectingDifferent(@Nonnull String testSubject) {
        return expectingDifferent(testSubject, object -> object);
    }
    
    /**
     * Returns a new expectation equality evaluator object for a given test subject. The test is evaluates whether the results of a function applied on two objects are different.
     */
    public static <T> ExpectationEqualityEvaluatorInterface1<T> expectingDifferent(@Nonnull String testSubject, @Nonnull UnaryFunction<T, ?> function) {
        return new ExpectationEqualityEvaluator<>(testSubject, function, false);
    }
    
}
