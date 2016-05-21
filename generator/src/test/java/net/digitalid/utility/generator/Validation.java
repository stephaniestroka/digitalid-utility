package net.digitalid.utility.generator;

import java.math.BigInteger;

import javax.annotation.Nonnull;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.NestingKind;

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
import net.digitalid.utility.validation.annotations.size.Empty;
import net.digitalid.utility.validation.annotations.size.EmptyOrSingle;
import net.digitalid.utility.validation.annotations.size.EmptyOrSingleRecipient;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.size.MinSize;
import net.digitalid.utility.validation.annotations.size.NonEmpty;
import net.digitalid.utility.validation.annotations.size.NonEmptyOrSingle;
import net.digitalid.utility.validation.annotations.size.NonSingle;
import net.digitalid.utility.validation.annotations.size.Single;
import net.digitalid.utility.validation.annotations.size.Size;
import net.digitalid.utility.validation.annotations.string.CodeIdentifier;
import net.digitalid.utility.validation.annotations.string.JavaExpression;
import net.digitalid.utility.validation.annotations.string.Regex;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.annotations.type.kind.AnnotationType;
import net.digitalid.utility.validation.annotations.type.kind.ClassType;
import net.digitalid.utility.validation.annotations.type.kind.EnumType;
import net.digitalid.utility.validation.annotations.type.kind.InterfaceType;
import net.digitalid.utility.validation.annotations.type.kind.TypeOf;
import net.digitalid.utility.validation.annotations.type.nesting.AnonymousType;
import net.digitalid.utility.validation.annotations.type.nesting.LocalType;
import net.digitalid.utility.validation.annotations.type.nesting.MemberType;
import net.digitalid.utility.validation.annotations.type.nesting.NestingOf;
import net.digitalid.utility.validation.annotations.type.nesting.TopLevelType;
import net.digitalid.utility.validation.annotations.value.Invariant;
import net.digitalid.utility.validation.annotations.value.Validated;

@Stateless
@GenerateSubclass
public abstract class Validation extends RootClass implements Countable, Validated.Value<String> {
    
    /* -------------------------------------------------- Interfaces -------------------------------------------------- */
    
    @Pure
    @Override
    public @NonNegative int size() {
        return 3;
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
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    @Impure
    public void setEmptyString(@Empty String string) {}
    
    @Impure
    public void setEmptyCountable(@Empty Countable countable) {}
    
    @Impure
    public void setEmptyStringArray(@Empty String[] array) {}
    
    @Impure
    public void setEmptyIntArray(@Empty int[] array) {}
    
    @Impure
    public void setEmptyOrSingleString(@EmptyOrSingle String string) {}
    
    @Impure
    public void setEmptyOrSingleCountable(@EmptyOrSingle Countable countable) {}
    
    @Impure
    public void setEmptyOrSingleStringArray(@EmptyOrSingle String[] array) {}
    
    @Impure
    public void setEmptyOrSingleIntArray(@EmptyOrSingle int[] array) {}
    
    @Impure
    public void setMaxSizeString(@MaxSize(3) String string) {}
    
    @Impure
    public void setMaxSizeCountable(@MaxSize(3) Countable countable) {}
    
    @Impure
    public void setMaxSizeStringArray(@MaxSize(3) String[] array) {}
    
    @Impure
    public void setMaxSizeIntArray(@MaxSize(3) int[] array) {}
    
    @Impure
    public void setMinSizeString(@MinSize(3) String string) {}
    
    @Impure
    public void setMinSizeCountable(@MinSize(3) Countable countable) {}
    
    @Impure
    public void setMinSizeStringArray(@MinSize(3) String[] array) {}
    
    @Impure
    public void setMinSizeIntArray(@MinSize(3) int[] array) {}
    
    @Impure
    public void setNonEmptyString(@NonEmpty String string) {}
    
    @Impure
    public void setNonEmptyCountable(@NonEmpty Countable countable) {}
    
    @Impure
    public void setNonEmptyStringArray(@NonEmpty String[] array) {}
    
    @Impure
    public void setNonEmptyIntArray(@NonEmpty int[] array) {}
    
    @Impure
    public void setNonEmptyOrSingleString(@NonEmptyOrSingle String string) {}
    
    @Impure
    public void setNonEmptyOrSingleCountable(@NonEmptyOrSingle Countable countable) {}
    
    @Impure
    public void setNonEmptyOrSingleStringArray(@NonEmptyOrSingle String[] array) {}
    
    @Impure
    public void setNonEmptyOrSingleIntArray(@NonEmptyOrSingle int[] array) {}
    
    @Impure
    public void setNonSingleString(@NonSingle String string) {}
    
    @Impure
    public void setNonSingleCountable(@NonSingle Countable countable) {}
    
    @Impure
    public void setNonSingleStringArray(@NonSingle String[] array) {}
    
    @Impure
    public void setNonSingleIntArray(@NonSingle int[] array) {}
    
    @Impure
    public void setSingleString(@Single String string) {}
    
    @Impure
    public void setSingleCountable(@Single Countable countable) {}
    
    @Impure
    public void setSingleStringArray(@Single String[] array) {}
    
    @Impure
    public void setSingleIntArray(@Single int[] array) {}
    
    @Impure
    public void setSizeString(@Size(3) String string) {}
    
    @Impure
    public void setSizeCountable(@Size(3) Countable countable) {}
    
    @Impure
    public void setSizeStringArray(@Size(3) String[] array) {}
    
    @Impure
    public void setSizeIntArray(@Size(3) int[] array) {}
    
    @Impure
    @EmptyOrSingleRecipient
    public void setEmptyOrSingleRecipient() {}
    
    /* -------------------------------------------------- String -------------------------------------------------- */
    
    @Impure
    public void setCodeIdentifier(@CodeIdentifier String identifier) {}
    
    @Impure
    public void setJavaExpression(@JavaExpression String expression) {}
    
    @Impure
    public void setRegex(@Regex("a*bab*") String string) {}
    
    /* -------------------------------------------------- Type Kind -------------------------------------------------- */
    
    @Impure
    public void setAnnotationType(@AnnotationType Class<?> type) {}
    
    @Impure
    public void setClassType(@ClassType Class<?> type) {}
    
    @Impure
    public void setEnumType(@EnumType Class<?> type) {}
    
    @Impure
    public void setInterfaceType(@InterfaceType Class<?> type) {}
    
    @Impure
    public void setTypeOf(@TypeOf({ElementKind.CLASS, ElementKind.INTERFACE}) Class<?> type) {}
    
    /* -------------------------------------------------- Type Nesting -------------------------------------------------- */
    
    @Impure
    public void setAnonymousType(@AnonymousType Class<?> type) {}
    
    @Impure
    public void setLocalType(@LocalType Class<?> type) {}
    
    @Impure
    public void setMemberType(@MemberType Class<?> type) {}
    
    @Impure
    public void setNestingOf(@NestingOf({NestingKind.TOP_LEVEL, NestingKind.MEMBER}) Class<?> type) {}
    
    @Impure
    public void setTopLevelType(@TopLevelType Class<?> type) {}
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    @Impure
    public void setInvariant(@Invariant(condition = "value % 3 == 0", message = "The value has to be a multiple of 3 but was $.") int value) {}
    
    // TODO
    
    /* -------------------------------------------------- Threading -------------------------------------------------- */
    
    // TODO: @MainThread
    
    /* -------------------------------------------------- Freezable -------------------------------------------------- */
    
    // TODO: @Frozen, @NonFrozen and @NonFrozenRecipient
    
    // TODO: Test @InGroup and @InSameGroup in the group project.
    
}
