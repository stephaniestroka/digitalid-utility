package net.digitalid.utility.generator;

import java.math.BigInteger;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.exceptions.PreconditionViolationException;
import net.digitalid.utility.functional.interfaces.Consumer;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.interfaces.BigIntegerNumerical;
import net.digitalid.utility.interfaces.LongNumerical;
import net.digitalid.utility.validation.annotations.type.Immutable;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidationTest {
    
    /* -------------------------------------------------- Static -------------------------------------------------- */
    
    private static final @Nonnull Validation validation = new ValidationSubclass();
    
    private static <T> void test(@Nonnull Consumer<? super T> consumer, T positive, T negative) {
        try {
            consumer.consume(positive);
        } catch (PreconditionViolationException exception) { 
            // TODO: is "sample" the right word?
            fail("The positive sample should not fail.");
        }
        try {
            consumer.consume(negative); 
            fail("The negative sample should fail.");
        } catch (PreconditionViolationException exception) {}
    }
    
    private static void testIterable(@Nonnull Consumer<Iterable<String>> iterableConsumer, @Nonnull Consumer<String[]> arrayConsumer, @Nonnull String[] positive, @Nonnull String[] negative) {
        test(iterableConsumer, FiniteIterable.of(positive), FiniteIterable.of(negative));
        test(arrayConsumer, positive, negative);
    }
    
    private static void testNumerical(@Nonnull Consumer<Long> longConsumer, @Nonnull Consumer<BigInteger> bigIntegerConsumer, @Nonnull Consumer<LongNumerical<?>> longNumericalConsumer, @Nonnull Consumer<BigIntegerNumerical<?>> bigIntegerNumericalConsumer, long positive, long negative) {
        test(longConsumer, positive, negative);
        test(bigIntegerConsumer, BigInteger.valueOf(positive), BigInteger.valueOf(negative));
        test(longNumericalConsumer, new LongValue(positive), new LongValue(negative));
        test(bigIntegerNumericalConsumer, new BigIntegerValue(positive), new BigIntegerValue(negative));
    }
    
    /* -------------------------------------------------- Reference -------------------------------------------------- */
    
    @Test
    public void testNonnull() {
        test(validation::setNonnull, new Object(), null);
    }
    
    /* -------------------------------------------------- Elements -------------------------------------------------- */
    
    @Test
    public void testNonNullableElements() {
        testIterable(validation::setNonNullableElementsIterable, validation::setNonNullableElementsArray, new String[] {"hello", "world"}, new String[] {"hello", null});
    }
    
    @Test
    public void testUniqueElements() {
        testIterable(validation::setUniqueElementsIterable, validation::setUniqueElementsArray, new String[] {"hello", "world"}, new String[] {"hello", "hello"});
    }
    
    /* -------------------------------------------------- Index -------------------------------------------------- */
    
    @Test
    public void testIndex() {
        test(validation::setIndex, 3, 4);
    }
    
    @Test
    public void testIndexForInsertion() {
        test(validation::setIndexForInsertion, 4, 5);
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
    public void testAscending() {
        testIterable(validation::setAscendingIterable, validation::setAscendingArray, new String[] {"hello", "hello", "world"}, new String[] {"world", "hello", "hello"});
    }
    
    @Test
    public void testDescending() {
        testIterable(validation::setDescendingIterable, validation::setDescendingArray, new String[] {"world", "hello", "hello"}, new String[] {"hello", "hello", "world"});
    }
    
    @Test
    public void testStrictlyAscending() {
        testIterable(validation::setStrictlyAscendingIterable, validation::setStrictlyAscendingArray, new String[] {"hello", "world"}, new String[] {"hello", "hello", "world"});
    }
    
    @Test
    public void testStrictlyDescending() {
        testIterable(validation::setStrictlyDescendingIterable, validation::setStrictlyDescendingArray, new String[] {"world", "hello"}, new String[] {"world", "hello", "hello"});
    }
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    @Test
    public void testInvariant() {
        test(validation::setInvariant, 3, 4);
    }
    
}
