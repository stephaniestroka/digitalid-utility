package net.digitalid.utility.generator;

import java.math.BigInteger;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.functional.interfaces.Predicate;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.interfaces.BigIntegerNumerical;
import net.digitalid.utility.interfaces.Countable;
import net.digitalid.utility.interfaces.LongNumerical;
import net.digitalid.utility.rootclass.RootClass;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.elements.UniqueElements;
import net.digitalid.utility.validation.annotations.index.Index;
import net.digitalid.utility.validation.annotations.index.IndexForInsertion;
import net.digitalid.utility.validation.annotations.math.Negative;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.math.NonPositive;
import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.validation.annotations.math.modulo.Even;
import net.digitalid.utility.validation.annotations.math.modulo.MultipleOf;
import net.digitalid.utility.validation.annotations.math.modulo.Uneven;
import net.digitalid.utility.validation.annotations.math.relative.GreaterThan;
import net.digitalid.utility.validation.annotations.math.relative.GreaterThanOrEqualTo;
import net.digitalid.utility.validation.annotations.math.relative.LessThan;
import net.digitalid.utility.validation.annotations.math.relative.LessThanOrEqualTo;
import net.digitalid.utility.validation.annotations.order.Ascending;
import net.digitalid.utility.validation.annotations.order.Descending;
import net.digitalid.utility.validation.annotations.order.StrictlyAscending;
import net.digitalid.utility.validation.annotations.order.StrictlyDescending;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.annotations.value.Invariant;
import net.digitalid.utility.validation.annotations.value.Validated;

@Stateless
@GenerateSubclass
public abstract class Validation extends RootClass implements Countable, Validated.Value<String> {
    
    /* -------------------------------------------------- Interfaces -------------------------------------------------- */
    
    @Pure
    @Override
    public @NonNegative int size() {
        return 4;
    }
    
    @Pure
    @Override
    public @Nonnull Predicate<? super String> getValueValidator() {
        return a -> a.length() <= 5;
    }
    
    /* -------------------------------------------------- Reference -------------------------------------------------- */
    
    @Impure
    public void setNonnull(@Nonnull Object object) {}
    
    /* -------------------------------------------------- Elements -------------------------------------------------- */
    
    @Impure
    public void setNonNullableStringIterable(@NonNullableElements Iterable<String> iterable) {}
    
    @Impure
    public void setNonNullableStringArray(@NonNullableElements String[] array) {}
    
    @Impure
    public void setUniqueStringIterable(@UniqueElements Iterable<String> iterable) {}
    
    @Impure
    public void setUniqueStringArray(@UniqueElements String[] array) {}
    
    @Impure
    public void setUniqueIntArray(@UniqueElements int[] array) {}
    
    /* -------------------------------------------------- Index -------------------------------------------------- */
    
    @Impure
    public void setIndex(@Index int value) {}
    
    @Impure
    public void setIndexForInsertion(@IndexForInsertion int value) {}
    
    /* -------------------------------------------------- Math -------------------------------------------------- */
    
    @Impure
    public void setNegativeLong(@Negative long value) {}
    
    @Impure
    public void setNegativeBigInteger(@Negative BigInteger value) {}
    
    @Impure
    public void setNegativeLongNumerical(@Negative LongNumerical<?> value) {}
    
    @Impure
    public void setNegativeBigIntegerNumerical(@Negative BigIntegerNumerical<?> value) {}
    
    @Impure
    public void setNonNegativeLong(@NonNegative long value) {}
    
    @Impure
    public void setNonNegativeBigInteger(@NonNegative BigInteger value) {}
    
    @Impure
    public void setNonNegativeLongNumerical(@NonNegative LongNumerical<?> value) {}
    
    @Impure
    public void setNonNegativeBigIntegerNumerical(@NonNegative BigIntegerNumerical<?> value) {}
    
    @Impure
    public void setNonPositiveLong(@NonPositive long value) {}
    
    @Impure
    public void setNonPositiveBigInteger(@NonPositive BigInteger value) {}
    
    @Impure
    public void setNonPositiveLongNumerical(@NonPositive LongNumerical<?> value) {}
    
    @Impure
    public void setNonPositiveBigIntegerNumerical(@NonPositive BigIntegerNumerical<?> value) {}
    
    @Impure
    public void setPositiveLong(@Positive long value) {}
    
    @Impure
    public void setPositiveBigInteger(@Positive BigInteger value) {}
    
    @Impure
    public void setPositiveLongNumerical(@Positive LongNumerical<?> value) {}
    
    @Impure
    public void setPositiveBigIntegerNumerical(@Positive BigIntegerNumerical<?> value) {}
    
    /* -------------------------------------------------- Modulo -------------------------------------------------- */
    
    @Impure
    public void setEvenLong(@Even long value) {}
    
    @Impure
    public void setEvenBigInteger(@Even BigInteger value) {}
    
    @Impure
    public void setEvenLongNumerical(@Even LongNumerical<?> value) {}
    
    @Impure
    public void setEvenBigIntegerNumerical(@Even BigIntegerNumerical<?> value) {}
    
    @Impure
    public void setMultipleOfLong(@MultipleOf(2) long value) {}
    
    @Impure
    public void setMultipleOfBigInteger(@MultipleOf(2) BigInteger value) {}
    
    @Impure
    public void setMultipleOfLongNumerical(@MultipleOf(2) LongNumerical<?> value) {}
    
    @Impure
    public void setMultipleOfBigIntegerNumerical(@MultipleOf(2) BigIntegerNumerical<?> value) {}
    
    @Impure
    public void setUnevenLong(@Uneven long value) {}
    
    @Impure
    public void setUnevenBigInteger(@Uneven BigInteger value) {}
    
    @Impure
    public void setUnevenLongNumerical(@Uneven LongNumerical<?> value) {}
    
    @Impure
    public void setUnevenBigIntegerNumerical(@Uneven BigIntegerNumerical<?> value) {}
    
    /* -------------------------------------------------- Relative -------------------------------------------------- */
    
    @Impure
    public void setGreaterThanLong(@GreaterThan(2) long value) {}
    
    @Impure
    public void setGreaterThanBigInteger(@GreaterThan(2) BigInteger value) {}
    
    @Impure
    public void setGreaterThanLongNumerical(@GreaterThan(2) LongNumerical<?> value) {}
    
    @Impure
    public void setGreaterThanBigIntegerNumerical(@GreaterThan(2) BigIntegerNumerical<?> value) {}
    
    @Impure
    public void setGreaterThanOrEqualToLong(@GreaterThanOrEqualTo(2) long value) {}
    
    @Impure
    public void setGreaterThanOrEqualToBigInteger(@GreaterThanOrEqualTo(2) BigInteger value) {}
    
    @Impure
    public void setGreaterThanOrEqualToLongNumerical(@GreaterThanOrEqualTo(2) LongNumerical<?> value) {}
    
    @Impure
    public void setGreaterThanOrEqualToBigIntegerNumerical(@GreaterThanOrEqualTo(2) BigIntegerNumerical<?> value) {}
    
    @Impure
    public void setLessThanLong(@LessThan(2) long value) {}
    
    @Impure
    public void setLessThanBigInteger(@LessThan(2) BigInteger value) {}
    
    @Impure
    public void setLessThanLongNumerical(@LessThan(2) LongNumerical<?> value) {}
    
    @Impure
    public void setLessThanBigIntegerNumerical(@LessThan(2) BigIntegerNumerical<?> value) {}
    
    @Impure
    public void setLessThanOrEqualToLong(@LessThanOrEqualTo(2) long value) {}
    
    @Impure
    public void setLessThanOrEqualToBigInteger(@LessThanOrEqualTo(2) BigInteger value) {}
    
    @Impure
    public void setLessThanOrEqualToLongNumerical(@LessThanOrEqualTo(2) LongNumerical<?> value) {}
    
    @Impure
    public void setLessThanOrEqualToBigIntegerNumerical(@LessThanOrEqualTo(2) BigIntegerNumerical<?> value) {}
    
    /* -------------------------------------------------- Order -------------------------------------------------- */
    
    @Impure
    public void setAscendingIntArray(@Ascending int[] array) {}
    
    @Impure
    public void setDescendingIntArray(@Descending int[] array) {}
    
    @Impure
    public void setStrictlyAscendingIntArray(@StrictlyAscending int[] array) {}
    
    @Impure
    public void setStrictlyDescendingIntArray(@StrictlyDescending int[] array) {}
    
    @Impure
    public void setAscendingStringIterable(@Ascending Iterable<String> iterable) {}
    
    @Impure
    public void setAscendingStringArray(@Ascending String[] array) {}
    
    @Impure
    public void setDescendingStringIterable(@Descending Iterable<String> iterable) {}
    
    @Impure
    public void setDescendingStringArray(@Descending String[] array) {}
    
    @Impure
    public void setStrictlyAscendingStringIterable(@StrictlyAscending Iterable<String> iterable) {}
    
    @Impure
    public void setStrictlyAscendingStringArray(@StrictlyAscending String[] array) {}
    
    @Impure
    public void setStrictlyDescendingStringIterable(@StrictlyDescending Iterable<String> iterable) {}
    
    @Impure
    public void setStrictlyDescendingStringArray(@StrictlyDescending String[] array) {}
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    @Impure
    public void setInvariant(@Invariant(condition = "value % 3 == 0", message = "The value has to be a multiple of 3 but was $.") int value) {}
    
}
