package net.digitalid.utility.generator;

import java.math.BigInteger;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.exceptions.PreconditionViolationException;
import net.digitalid.utility.functional.interfaces.Consumer;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.interfaces.BigIntegerNumerical;
import net.digitalid.utility.interfaces.Countable;
import net.digitalid.utility.interfaces.LongNumerical;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.type.Immutable;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidationTest {
    
    /* -------------------------------------------------- Static -------------------------------------------------- */
    
    private static final @Nonnull Validation validation = new ValidationSubclass();
    
    private static <T> void test(@Nonnull Consumer<? super T> consumer, T positive, T negative) {
        try {
            consumer.consume(positive);
        } catch (@Nonnull PreconditionViolationException exception) { 
            fail("The positive sample should not fail.");
        }
        try {
            consumer.consume(negative); 
            fail("The negative sample should fail.");
        } catch (@Nonnull PreconditionViolationException exception) {}
    }
    
    private static void testStringIterableAndArray(@Nonnull Consumer<Iterable<String>> iterableConsumer, @Nonnull Consumer<String[]> arrayConsumer, @Nonnull String[] positive, @Nonnull String[] negative) {
        test(iterableConsumer, FiniteIterable.of(positive), FiniteIterable.of(negative));
        test(arrayConsumer, positive, negative);
    }
    
    private static void testNumerical(@Nonnull Consumer<Long> longConsumer, @Nonnull Consumer<BigInteger> bigIntegerConsumer, @Nonnull Consumer<LongNumerical<?>> longNumericalConsumer, @Nonnull Consumer<BigIntegerNumerical<?>> bigIntegerNumericalConsumer, long positive, long negative) {
        test(longConsumer, positive, negative);
        test(bigIntegerConsumer, BigInteger.valueOf(positive), BigInteger.valueOf(negative));
        test(longNumericalConsumer, new LongValue(positive), new LongValue(negative));
        test(bigIntegerNumericalConsumer, new BigIntegerValue(positive), new BigIntegerValue(negative));
    }
    
    private static void testSize(@Nonnull Consumer<String> stringConsumer, @Nonnull Consumer<Countable> countableConsumer, @Nonnull Consumer<String[]> stringArrayConsumer, @Nonnull Consumer<int[]> intArrayConsumer, @NonNegative int positiveSize, @NonNegative int negativeSize) {
        test(stringConsumer, Strings.repeat('a', positiveSize), Strings.repeat('a', negativeSize));
        test(countableConsumer, () -> positiveSize, () -> negativeSize);
        test(stringArrayConsumer, new String[positiveSize], new String[negativeSize]);
        test(intArrayConsumer, new int[positiveSize], new int[negativeSize]);
    }
    
    /* -------------------------------------------------- Reference -------------------------------------------------- */
    
    @Test
    public void testNonnull() {
        test(validation::setNonnull, new Object(), null);
    }
    
    /* -------------------------------------------------- Elements -------------------------------------------------- */
    
    @Test
    public void testNonNullableStrings() {
        testStringIterableAndArray(validation::setNonNullableStringIterable, validation::setNonNullableStringArray, new String[] {"hello", "world"}, new String[] {"hello", null});
    }
    
    @Test
    public void testUniqueStrings() {
        testStringIterableAndArray(validation::setUniqueStringIterable, validation::setUniqueStringArray, new String[] {"hello", "world"}, new String[] {"hello", "hello"});
    }
    
    @Test
    public void testUniqueInts() {
        test(validation::setUniqueIntArray, new int[] {1, 2}, new int[] {1, 1});
    }
    
    /* -------------------------------------------------- Index -------------------------------------------------- */
    
    @Test
    public void testIndex() {
        test(validation::setIndex, 2, 3);
    }
    
    @Test
    public void testIndexForInsertion() {
        test(validation::setIndexForInsertion, 3, 4);
    }
    
    /* -------------------------------------------------- Numericals -------------------------------------------------- */
    
    @Immutable
    private static class LongValue implements LongNumerical<LongValue> {
        
        private final long value;
        
        @Pure
        @Override
        public long getValue() {
            return value;
        }
        
        private LongValue(long value) {
            this.value = value;
        }
        
    }
    
    @Immutable
    private static class BigIntegerValue implements BigIntegerNumerical<BigIntegerValue> {
        
        private final @Nonnull BigInteger value;
        
        @Pure
        @Override
        public @Nonnull BigInteger getValue() {
            return value;
        }
        
        private BigIntegerValue(@Nonnull BigInteger value) {
            this.value = value;
        }
        
        private BigIntegerValue(long value) {
            this(BigInteger.valueOf(value));
        }
        
    }
    
    /* -------------------------------------------------- Math -------------------------------------------------- */
    
    @Test
    public void testNegative() {
        testNumerical(validation::setNegativeLong, validation::setNegativeBigInteger, validation::setNegativeLongNumerical, validation::setNegativeBigIntegerNumerical, -1, 0);
    }
    
    @Test
    public void testNonNegative() {
        testNumerical(validation::setNonNegativeLong, validation::setNonNegativeBigInteger, validation::setNonNegativeLongNumerical, validation::setNonNegativeBigIntegerNumerical, 0, -1);
    }
    
    @Test
    public void testNonPositive() {
        testNumerical(validation::setNonPositiveLong, validation::setNonPositiveBigInteger, validation::setNonPositiveLongNumerical, validation::setNonPositiveBigIntegerNumerical, 0, 1);
    }
    
    @Test
    public void testPositive() {
        testNumerical(validation::setPositiveLong, validation::setPositiveBigInteger, validation::setPositiveLongNumerical, validation::setPositiveBigIntegerNumerical, 1, 0);
    }
    
    /* -------------------------------------------------- Modulo -------------------------------------------------- */
    
    @Test
    public void testEven() {
        testNumerical(validation::setEvenLong, validation::setEvenBigInteger, validation::setEvenLongNumerical, validation::setEvenBigIntegerNumerical, 2, 3);
    }
    
    @Test
    public void testMultipleOf() {
        testNumerical(validation::setMultipleOfLong, validation::setMultipleOfBigInteger, validation::setMultipleOfLongNumerical, validation::setMultipleOfBigIntegerNumerical, 4, 5);
    }
    
    @Test
    public void testUneven() {
        testNumerical(validation::setUnevenLong, validation::setUnevenBigInteger, validation::setUnevenLongNumerical, validation::setUnevenBigIntegerNumerical, 3, 2);
    }
    
    /* -------------------------------------------------- Relative -------------------------------------------------- */
    
    @Test
    public void testGreaterThan() {
        testNumerical(validation::setGreaterThanLong, validation::setGreaterThanBigInteger, validation::setGreaterThanLongNumerical, validation::setGreaterThanBigIntegerNumerical, 3, 2);
    }
    
    @Test
    public void testGreaterThanOrEqualTo() {
        testNumerical(validation::setGreaterThanOrEqualToLong, validation::setGreaterThanOrEqualToBigInteger, validation::setGreaterThanOrEqualToLongNumerical, validation::setGreaterThanOrEqualToBigIntegerNumerical, 2, 1);
    }
    
    @Test
    public void testLessThan() {
        testNumerical(validation::setLessThanLong, validation::setLessThanBigInteger, validation::setLessThanLongNumerical, validation::setLessThanBigIntegerNumerical, 1, 2);
    }
    
    @Test
    public void testLessThanOrEqualTo() {
        testNumerical(validation::setLessThanOrEqualToLong, validation::setLessThanOrEqualToBigInteger, validation::setLessThanOrEqualToLongNumerical, validation::setLessThanOrEqualToBigIntegerNumerical, 2, 3);
    }
    
    /* -------------------------------------------------- Order -------------------------------------------------- */
    
    @Test
    public void testAscendingInts() {
        test(validation::setAscendingIntArray, new int[] {1, 1, 2, 3}, new int[] {1, 2, 3, 1});
    }
    
    @Test
    public void testDescendingInts() {
        test(validation::setDescendingIntArray, new int[] {3, 2, 1, 1}, new int[] {1, 3, 2, 1});
    }
    
    @Test
    public void testStrictlyAscendingInts() {
        test(validation::setStrictlyAscendingIntArray, new int[] {1, 2, 3}, new int[] {1, 1, 2});
    }
    
    @Test
    public void testStrictlyDescendingInts() {
        test(validation::setStrictlyDescendingIntArray, new int[] {3, 2, 1}, new int[] {2, 1, 1});
    }
    
    @Test
    public void testAscendingStrings() {
        testStringIterableAndArray(validation::setAscendingStringIterable, validation::setAscendingStringArray, new String[] {"hello", "hello", "world"}, new String[] {"world", "hello", "hello"});
    }
    
    @Test
    public void testDescendingStrings() {
        testStringIterableAndArray(validation::setDescendingStringIterable, validation::setDescendingStringArray, new String[] {"world", "hello", "hello"}, new String[] {"hello", "hello", "world"});
    }
    
    @Test
    public void testStrictlyAscendingStrings() {
        testStringIterableAndArray(validation::setStrictlyAscendingStringIterable, validation::setStrictlyAscendingStringArray, new String[] {"hello", "my", "world"}, new String[] {"hello", "hello", "world"});
    }
    
    @Test
    public void testStrictlyDescendingStrings() {
        testStringIterableAndArray(validation::setStrictlyDescendingStringIterable, validation::setStrictlyDescendingStringArray, new String[] {"world", "my", "hello"}, new String[] {"world", "hello", "hello"});
    }
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    @Test
    public void testEmpty() {
        testSize(validation::setEmptyString, validation::setEmptyCountable, validation::setEmptyStringArray, validation::setEmptyIntArray, 0, 1);
    }
    
    @Test
    public void testEmptyOrSingle() {
        testSize(validation::setEmptyOrSingleString, validation::setEmptyOrSingleCountable, validation::setEmptyOrSingleStringArray, validation::setEmptyOrSingleIntArray, 0, 2);
        testSize(validation::setEmptyOrSingleString, validation::setEmptyOrSingleCountable, validation::setEmptyOrSingleStringArray, validation::setEmptyOrSingleIntArray, 1, 2);
    }
    
    @Test
    public void testMaxSize() {
        testSize(validation::setMaxSizeString, validation::setMaxSizeCountable, validation::setMaxSizeStringArray, validation::setMaxSizeIntArray, 3, 4);
    }
    
    @Test
    public void testMinSize() {
        testSize(validation::setMinSizeString, validation::setMinSizeCountable, validation::setMinSizeStringArray, validation::setMinSizeIntArray, 3, 2);
    }
    
    @Test
    public void testNonEmpty() {
        testSize(validation::setNonEmptyString, validation::setNonEmptyCountable, validation::setNonEmptyStringArray, validation::setNonEmptyIntArray, 1, 0);
    }
    
    @Test
    public void testNonEmptyOrSingle() {
        testSize(validation::setNonEmptyOrSingleString, validation::setNonEmptyOrSingleCountable, validation::setNonEmptyOrSingleStringArray, validation::setNonEmptyOrSingleIntArray, 2, 0);
        testSize(validation::setNonEmptyOrSingleString, validation::setNonEmptyOrSingleCountable, validation::setNonEmptyOrSingleStringArray, validation::setNonEmptyOrSingleIntArray, 2, 1);
    }
    
    @Test
    public void testNonSingle() {
        testSize(validation::setNonSingleString, validation::setNonSingleCountable, validation::setNonSingleStringArray, validation::setNonSingleIntArray, 0, 1);
    }
    
    @Test
    public void testSingle() {
        testSize(validation::setSingleString, validation::setSingleCountable, validation::setSingleStringArray, validation::setSingleIntArray, 1, 0);
    }
    
    @Test
    public void testSize() {
        testSize(validation::setSizeString, validation::setSizeCountable, validation::setSizeStringArray, validation::setSizeIntArray, 3, 2);
    }
    
    /* -------------------------------------------------- String -------------------------------------------------- */
    
    /* -------------------------------------------------- Type Kind -------------------------------------------------- */
    
    /* -------------------------------------------------- Type Nesting -------------------------------------------------- */
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    @Test
    public void testInvariant() {
        test(validation::setInvariant, 3, 4);
    }
    
}
