package net.digitalid.utility.generator.validators;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;

import javax.annotation.Nonnull;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.NestingKind;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.contracts.exceptions.PreconditionException;
import net.digitalid.utility.file.Files;
import net.digitalid.utility.freezable.FreezableInterface;
import net.digitalid.utility.freezable.ReadOnlyInterface;
import net.digitalid.utility.freezable.annotations.Freezable;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.freezable.annotations.NonFrozenRecipient;
import net.digitalid.utility.functional.failable.FailableConsumer;
import net.digitalid.utility.functional.interfaces.Consumer;
import net.digitalid.utility.functional.interfaces.Predicate;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.interfaces.BigIntegerNumerical;
import net.digitalid.utility.interfaces.Countable;
import net.digitalid.utility.interfaces.LongNumerical;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.testing.ContractTest;
import net.digitalid.utility.threading.annotations.MainThread;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.elements.UniqueElements;
import net.digitalid.utility.validation.annotations.equality.Equal;
import net.digitalid.utility.validation.annotations.equality.Unequal;
import net.digitalid.utility.validation.annotations.file.existence.Existent;
import net.digitalid.utility.validation.annotations.file.existence.ExistentParent;
import net.digitalid.utility.validation.annotations.file.existence.NonExistent;
import net.digitalid.utility.validation.annotations.file.existence.NonExistentParent;
import net.digitalid.utility.validation.annotations.file.kind.Directory;
import net.digitalid.utility.validation.annotations.file.kind.Normal;
import net.digitalid.utility.validation.annotations.file.path.Absolute;
import net.digitalid.utility.validation.annotations.file.path.Relative;
import net.digitalid.utility.validation.annotations.file.permission.Executable;
import net.digitalid.utility.validation.annotations.file.permission.Readable;
import net.digitalid.utility.validation.annotations.file.permission.Unexecutable;
import net.digitalid.utility.validation.annotations.file.permission.Unreadable;
import net.digitalid.utility.validation.annotations.file.permission.Unwritable;
import net.digitalid.utility.validation.annotations.file.permission.Writable;
import net.digitalid.utility.validation.annotations.file.visibility.Hidden;
import net.digitalid.utility.validation.annotations.file.visibility.Visible;
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
import net.digitalid.utility.validation.annotations.method.Chainable;
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
import net.digitalid.utility.validation.annotations.string.DomainName;
import net.digitalid.utility.validation.annotations.string.JavaExpression;
import net.digitalid.utility.validation.annotations.substring.Infix;
import net.digitalid.utility.validation.annotations.substring.Prefix;
import net.digitalid.utility.validation.annotations.substring.Regex;
import net.digitalid.utility.validation.annotations.substring.Suffix;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.type.ReadOnly;
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
import net.digitalid.utility.validation.annotations.value.Valid;

import org.junit.Test;

@Immutable
@GenerateSubclass
interface LongValue extends LongNumerical<LongValue> {}

@Immutable
@GenerateSubclass
interface BigIntegerValue extends BigIntegerNumerical<BigIntegerValue> {}

@Mutable
@GenerateSubclass
public class ContractGenerationTest extends ContractTest implements Countable, Valid.Value<String>, FreezableInterface {
    
    /* -------------------------------------------------- Instance -------------------------------------------------- */
    
    public static final @Nonnull ContractGenerationTest INSTANCE = new ContractGenerationTestSubclass();
    
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
    
    @Pure
    @Override
    public boolean isFrozen() {
        return true;
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public @Chainable @Nonnull @Frozen ReadOnlyInterface freeze() {
        return this;
    }
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableInterface clone() {
        return new ContractGenerationTestSubclass();
    }
    
    /* -------------------------------------------------- Testing -------------------------------------------------- */
    
    @Pure
    public static void testStringIterableAndArray(@Nonnull Consumer<Iterable<String>> iterableConsumer, @Nonnull Consumer<String[]> arrayConsumer, @Nonnull String[] positive, @Nonnull String[] negative) {
        test(iterableConsumer, FiniteIterable.of(positive), FiniteIterable.of(negative));
        test(arrayConsumer, positive, negative);
    }
    
    @Pure
    public static void testNumerical(@Nonnull Consumer<Long> longConsumer, @Nonnull Consumer<Integer> integerConsumer, @Nonnull Consumer<BigInteger> bigIntegerConsumer, @Nonnull Consumer<LongNumerical<?>> longNumericalConsumer, @Nonnull Consumer<BigIntegerNumerical<?>> bigIntegerNumericalConsumer, long positive, long negative) {
        test(longConsumer, positive, negative);
        test(integerConsumer, (int) positive, (int) negative);
        test(bigIntegerConsumer, BigInteger.valueOf(positive), BigInteger.valueOf(negative));
        test(longNumericalConsumer, new LongValueSubclass(positive), new LongValueSubclass(negative));
        test(bigIntegerNumericalConsumer, new BigIntegerValueSubclass(BigInteger.valueOf(positive)), new BigIntegerValueSubclass(BigInteger.valueOf(negative)));
    }
    
    @Pure
    public static void testSize(@Nonnull Consumer<String> stringConsumer, @Nonnull Consumer<Countable> countableConsumer, @Nonnull Consumer<String[]> stringArrayConsumer, @Nonnull Consumer<int[]> intArrayConsumer, @NonNegative int positiveSize, @NonNegative int negativeSize) {
        test(stringConsumer, Strings.repeat('a', positiveSize), Strings.repeat('a', negativeSize));
        test(countableConsumer, () -> positiveSize, () -> negativeSize);
        test(stringArrayConsumer, new String[positiveSize], new String[negativeSize]);
        test(intArrayConsumer, new int[positiveSize], new int[negativeSize]);
    }
    
    /* -------------------------------------------------- Reference -------------------------------------------------- */
    
    @Impure
    public void setNonnull(@Nonnull Object object) {}
    
    @Test
    public void testNonnull() {
        test(INSTANCE::setNonnull, new Object(), null);
    }
    
    /* -------------------------------------------------- Elements -------------------------------------------------- */
    
    @Impure
    public void setNonNullableStringIterable(@NonNullableElements Iterable<String> iterable) {}
    
    @Impure
    public void setNonNullableStringArray(@NonNullableElements String[] array) {}
    
    @Test
    public void testNonNullableStrings() {
        testStringIterableAndArray(INSTANCE::setNonNullableStringIterable, INSTANCE::setNonNullableStringArray, new String[] {"hello", "world"}, new String[] {"hello", null});
    }
    
    @Impure
    public void setUniqueStringIterable(@UniqueElements Iterable<String> iterable) {}
    
    @Impure
    public void setUniqueStringArray(@UniqueElements String[] array) {}
    
    @Test
    public void testUniqueStrings() {
        testStringIterableAndArray(INSTANCE::setUniqueStringIterable, INSTANCE::setUniqueStringArray, new String[] {"hello", "world"}, new String[] {"hello", "hello"});
    }
    
    @Impure
    public void setUniqueIntArray(@UniqueElements int[] array) {}
    
    @Test
    public void testUniqueInts() {
        test(INSTANCE::setUniqueIntArray, new int[] {1, 2}, new int[] {1, 1});
    }
    
    /* -------------------------------------------------- Equality -------------------------------------------------- */
    
    @Impure
    public void setEqualString(@Equal("hello") String string) {}
    
    @Test
    public void testEqualString() {
        test(INSTANCE::setEqualString, "hello", "world");
    }
    
    @Impure
    public void setNonEqualString(@Unequal("world") String string) {}
    
    @Test
    public void testNonEqualString() {
        test(INSTANCE::setNonEqualString, "hello", "world");
    }
    
    @Impure
    public void setEqualInt(@Equal("8") int value) {}
    
    @Test
    public void testEqualInt() {
        test(INSTANCE::setEqualInt, 8, 9);
    }
    
    @Impure
    public void setNonEqualInt(@Unequal("9") int value) {}
    
    @Test
    public void testNonEqualInt() {
        test(INSTANCE::setNonEqualInt, 8, 9);
    }
    
    /* -------------------------------------------------- File -------------------------------------------------- */
    
    private static final @Nonnull File testFile = Files.relativeToWorkingDirectory("target/test-files/file.txt");
    
    private static final @Nonnull File testDirectory = testFile.getParentFile();
    
    private static final @Nonnull File nonExistentFile = new File("target/test-files/directory/file.txt");
    
    private static final @Nonnull File nonExistentDirectory = new File("target/test-files/directory/subdirectory/");
    
    private static final @Nonnull File fullPermissions = Files.relativeToWorkingDirectory("target/test-files/full.sh");
    
    private static final @Nonnull File noPermissions = Files.relativeToWorkingDirectory("target/test-files/none.txt");
    
    private static final @Nonnull File hiddenFile = Files.relativeToWorkingDirectory("target/test-files/.hidden.txt");
    
    private static final @Nonnull File hiddenDirectory = Files.relativeToWorkingDirectory("target/test-files/.hidden/");
    
    static {
        try {
            testFile.createNewFile();
            fullPermissions.createNewFile();
            fullPermissions.setReadable(true);
            fullPermissions.setWritable(true);
            fullPermissions.setExecutable(true);
            noPermissions.createNewFile();
            noPermissions.setReadable(false);
            noPermissions.setWritable(false);
            noPermissions.setExecutable(false);
            hiddenFile.createNewFile();
            hiddenDirectory.mkdir();
        } catch (@Nonnull IOException exception) {
            Log.error("Could not create a file.", exception);
        }
    }
    
    @Impure
    public void setExistent(@Existent File file) {}
    
    @Test
    public void testExistent() {
        testPositives(INSTANCE::setExistent, testFile, testDirectory);
        testNegatives(INSTANCE::setExistent, nonExistentFile, nonExistentDirectory);
    }
    
    @Impure
    public void setExistentParent(@ExistentParent File file) {}
    
    @Test
    public void testExistentParent() {
        testPositives(INSTANCE::setExistentParent, testFile, testDirectory);
        testNegatives(INSTANCE::setExistentParent, nonExistentFile, nonExistentDirectory);
    }
    
    @Impure
    public void setNonExistent(@NonExistent File file) {}
    
    @Test
    public void testNonExistent() {
        testPositives(INSTANCE::setNonExistent, nonExistentFile, nonExistentDirectory);
        testNegatives(INSTANCE::setNonExistent, testFile, testDirectory);
    }
    
    @Impure
    public void setNonExistentParent(@NonExistentParent File file) {}
    
    @Test
    public void testNonExistentParent() {
        testPositives(INSTANCE::setNonExistentParent, nonExistentFile, nonExistentDirectory);
        testNegatives(INSTANCE::setNonExistentParent, testFile, testDirectory);
    }
    
    @Impure
    public void setDirectory(@Directory File file) {}
    
    @Test
    public void testDirectory() {
        testPositives(INSTANCE::setDirectory, testDirectory, nonExistentDirectory);
        testNegatives(INSTANCE::setDirectory, testFile);
    }
    
    @Impure
    public void setNormal(@Normal File file) {}
    
    @Test
    public void testNormal() {
        testPositives(INSTANCE::setNormal, testFile, nonExistentFile);
        testNegatives(INSTANCE::setNormal, testDirectory);
    }
    
    @Impure
    public void setAbsolute(@Absolute File file) {}
    
    @Test
    public void testAbsolute() {
        testPositives(INSTANCE::setAbsolute, testFile, testDirectory);
        testNegatives(INSTANCE::setAbsolute, nonExistentFile, nonExistentDirectory);
    }
    
    @Impure
    public void setRelative(@Relative File file) {}
    
    @Test
    public void testRelative() {
        testPositives(INSTANCE::setRelative, nonExistentFile, nonExistentDirectory);
        testNegatives(INSTANCE::setRelative, testFile, testDirectory);
    }
    
    @Pure
    protected static <T, X extends Exception> void testWithNegativeSampleIgnoredOnWindows(@NonCaptured @Modified @Nonnull FailableConsumer<? super T, ? extends X> consumer, T positive, T negative) throws X {
        testPositives(consumer, positive);
        if (!System.getProperty("os.name").startsWith("Windows")) {
            testNegatives(consumer, negative);
        }
    }
    
    @Impure
    public void setExecutable(@Executable File file) {}
    
    @Test
    public void testExecutable() {
        testWithNegativeSampleIgnoredOnWindows(INSTANCE::setExecutable, fullPermissions, noPermissions);
    }
    
    @Impure
    public void setUnexecutable(@Unexecutable File file) {}
    
    @Test
    public void testUnexecutable() {
        testWithNegativeSampleIgnoredOnWindows(INSTANCE::setUnexecutable, noPermissions, fullPermissions);
    }
    
    @Impure
    public void setUnreadable(@Unreadable File file) {}
    
    @Test
    public void testUnreadable() {
        testWithNegativeSampleIgnoredOnWindows(INSTANCE::setUnreadable, noPermissions, fullPermissions);
    }
    
    @Impure
    public void setUnwritable(@Unwritable File file) {}
    
    @Test
    public void testUnwritable() {
        test(INSTANCE::setUnwritable, noPermissions, fullPermissions);
    }
    
    @Impure
    public void setReadable(@Readable File file) {}
    
    @Test
    public void testReadable() {
        testWithNegativeSampleIgnoredOnWindows(INSTANCE::setReadable, fullPermissions, noPermissions);
    }
    
    @Impure
    public void setWritable(@Writable File file) {}
    
    @Test
    public void testWritable() {
        test(INSTANCE::setWritable, fullPermissions, noPermissions);
    }
    
    @Impure
    public void setHidden(@Hidden File file) {}
    
    @Test
    public void testHidden() {
        testPositives(INSTANCE::setHidden, hiddenFile, hiddenDirectory);
        if (!System.getProperty("os.name").startsWith("Windows")) {
            testNegatives(INSTANCE::setHidden, testFile, testDirectory);
        }
    }
    
    @Impure
    public void setVisible(@Visible File file) {}
    
    @Test
    public void testVisible() {
        testPositives(INSTANCE::setVisible, testFile, testDirectory);
        if (!System.getProperty("os.name").startsWith("Windows")) {
            testNegatives(INSTANCE::setVisible, hiddenFile, hiddenDirectory);
        }
    }
    
    /* -------------------------------------------------- Index -------------------------------------------------- */
    
    @Impure
    public void setIndex(@Index int value) {}
    
    @Test
    public void testIndex() {
        test(INSTANCE::setIndex, 2, 3);
    }
    
    @Impure
    public void setIndexForInsertion(@IndexForInsertion int value) {}
    
    @Test
    public void testIndexForInsertion() {
        test(INSTANCE::setIndexForInsertion, 3, 4);
    }
    
    /* -------------------------------------------------- Math -------------------------------------------------- */
    
    @Impure
    public void setNegativeLong(@Negative long value) {}
    
    @Impure
    public void setNegativeInteger(@Negative Integer value) {}
    
    @Impure
    public void setNegativeBigInteger(@Negative BigInteger value) {}
    
    @Impure
    public void setNegativeLongNumerical(@Negative LongNumerical<?> value) {}
    
    @Impure
    public void setNegativeBigIntegerNumerical(@Negative BigIntegerNumerical<?> value) {}
    
    @Test
    public void testNegative() {
        testNumerical(INSTANCE::setNegativeLong, INSTANCE::setNegativeInteger, INSTANCE::setNegativeBigInteger, INSTANCE::setNegativeLongNumerical, INSTANCE::setNegativeBigIntegerNumerical, -1, 0);
    }
    
    @Impure
    public void setNonNegativeLong(@NonNegative long value) {}
    
    @Impure
    public void setNonNegativeInteger(@NonNegative Integer value) {}
    
    @Impure
    public void setNonNegativeBigInteger(@NonNegative BigInteger value) {}
    
    @Impure
    public void setNonNegativeLongNumerical(@NonNegative LongNumerical<?> value) {}
    
    @Impure
    public void setNonNegativeBigIntegerNumerical(@NonNegative BigIntegerNumerical<?> value) {}
    
    @Test
    public void testNonNegative() {
        testNumerical(INSTANCE::setNonNegativeLong, INSTANCE::setNonNegativeInteger, INSTANCE::setNonNegativeBigInteger, INSTANCE::setNonNegativeLongNumerical, INSTANCE::setNonNegativeBigIntegerNumerical, 0, -1);
    }
    
    @Impure
    public void setNonPositiveLong(@NonPositive long value) {}
    
    @Impure
    public void setNonPositiveInteger(@NonPositive Integer value) {}
    
    @Impure
    public void setNonPositiveBigInteger(@NonPositive BigInteger value) {}
    
    @Impure
    public void setNonPositiveLongNumerical(@NonPositive LongNumerical<?> value) {}
    
    @Impure
    public void setNonPositiveBigIntegerNumerical(@NonPositive BigIntegerNumerical<?> value) {}
    
    @Test
    public void testNonPositive() {
        testNumerical(INSTANCE::setNonPositiveLong, INSTANCE::setNonPositiveInteger, INSTANCE::setNonPositiveBigInteger, INSTANCE::setNonPositiveLongNumerical, INSTANCE::setNonPositiveBigIntegerNumerical, 0, 1);
    }
    
    @Impure
    public void setPositiveLong(@Positive long value) {}
    
    @Impure
    public void setPositiveInteger(@Positive Integer value) {}
    
    @Impure
    public void setPositiveBigInteger(@Positive BigInteger value) {}
    
    @Impure
    public void setPositiveLongNumerical(@Positive LongNumerical<?> value) {}
    
    @Impure
    public void setPositiveBigIntegerNumerical(@Positive BigIntegerNumerical<?> value) {}
    
    @Test
    public void testPositive() {
        testNumerical(INSTANCE::setPositiveLong, INSTANCE::setPositiveInteger, INSTANCE::setPositiveBigInteger, INSTANCE::setPositiveLongNumerical, INSTANCE::setPositiveBigIntegerNumerical, 1, 0);
    }
    
    /* -------------------------------------------------- Modulo -------------------------------------------------- */
    
    @Impure
    public void setEvenLong(@Even long value) {}
    
    @Impure
    public void setEvenInteger(@Even Integer value) {}
    
    @Impure
    public void setEvenBigInteger(@Even BigInteger value) {}
    
    @Impure
    public void setEvenLongNumerical(@Even LongNumerical<?> value) {}
    
    @Impure
    public void setEvenBigIntegerNumerical(@Even BigIntegerNumerical<?> value) {}
    
    @Test
    public void testEven() {
        testNumerical(INSTANCE::setEvenLong, INSTANCE::setEvenInteger, INSTANCE::setEvenBigInteger, INSTANCE::setEvenLongNumerical, INSTANCE::setEvenBigIntegerNumerical, 2, 3);
    }
    
    @Impure
    public void setMultipleOfLong(@MultipleOf(2) long value) {}
    
    @Impure
    public void setMultipleOfInteger(@MultipleOf(2) Integer value) {}
    
    @Impure
    public void setMultipleOfBigInteger(@MultipleOf(2) BigInteger value) {}
    
    @Impure
    public void setMultipleOfLongNumerical(@MultipleOf(2) LongNumerical<?> value) {}
    
    @Impure
    public void setMultipleOfBigIntegerNumerical(@MultipleOf(2) BigIntegerNumerical<?> value) {}
    
    @Test
    public void testMultipleOf() {
        testNumerical(INSTANCE::setMultipleOfLong, INSTANCE::setMultipleOfInteger, INSTANCE::setMultipleOfBigInteger, INSTANCE::setMultipleOfLongNumerical, INSTANCE::setMultipleOfBigIntegerNumerical, 4, 5);
    }
    
    @Impure
    public void setUnevenLong(@Uneven long value) {}
    
    @Impure
    public void setUnevenInteger(@Uneven Integer value) {}
    
    @Impure
    public void setUnevenBigInteger(@Uneven BigInteger value) {}
    
    @Impure
    public void setUnevenLongNumerical(@Uneven LongNumerical<?> value) {}
    
    @Impure
    public void setUnevenBigIntegerNumerical(@Uneven BigIntegerNumerical<?> value) {}
    
    @Test
    public void testUneven() {
        testNumerical(INSTANCE::setUnevenLong, INSTANCE::setUnevenInteger, INSTANCE::setUnevenBigInteger, INSTANCE::setUnevenLongNumerical, INSTANCE::setUnevenBigIntegerNumerical, 3, 2);
    }
    
    /* -------------------------------------------------- Relative -------------------------------------------------- */
    
    @Impure
    public void setGreaterThanLong(@GreaterThan(2) long value) {}
    
    @Impure
    public void setGreaterThanInteger(@GreaterThan(2) Integer value) {}
    
    @Impure
    public void setGreaterThanBigInteger(@GreaterThan(2) BigInteger value) {}
    
    @Impure
    public void setGreaterThanLongNumerical(@GreaterThan(2) LongNumerical<?> value) {}
    
    @Impure
    public void setGreaterThanBigIntegerNumerical(@GreaterThan(2) BigIntegerNumerical<?> value) {}
    
    @Test
    public void testGreaterThan() {
        testNumerical(INSTANCE::setGreaterThanLong, INSTANCE::setGreaterThanInteger, INSTANCE::setGreaterThanBigInteger, INSTANCE::setGreaterThanLongNumerical, INSTANCE::setGreaterThanBigIntegerNumerical, 3, 2);
    }
    
    @Impure
    public void setGreaterThanOrEqualToLong(@GreaterThanOrEqualTo(2) long value) {}
    
    @Impure
    public void setGreaterThanOrEqualToInteger(@GreaterThanOrEqualTo(2) Integer value) {}
    
    @Impure
    public void setGreaterThanOrEqualToBigInteger(@GreaterThanOrEqualTo(2) BigInteger value) {}
    
    @Impure
    public void setGreaterThanOrEqualToLongNumerical(@GreaterThanOrEqualTo(2) LongNumerical<?> value) {}
    
    @Impure
    public void setGreaterThanOrEqualToBigIntegerNumerical(@GreaterThanOrEqualTo(2) BigIntegerNumerical<?> value) {}
    
    @Test
    public void testGreaterThanOrEqualTo() {
        testNumerical(INSTANCE::setGreaterThanOrEqualToLong, INSTANCE::setGreaterThanOrEqualToInteger, INSTANCE::setGreaterThanOrEqualToBigInteger, INSTANCE::setGreaterThanOrEqualToLongNumerical, INSTANCE::setGreaterThanOrEqualToBigIntegerNumerical, 2, 1);
    }
    
    @Impure
    public void setLessThanLong(@LessThan(2) long value) {}
    
    @Impure
    public void setLessThanInteger(@LessThan(2) Integer value) {}
    
    @Impure
    public void setLessThanBigInteger(@LessThan(2) BigInteger value) {}
    
    @Impure
    public void setLessThanLongNumerical(@LessThan(2) LongNumerical<?> value) {}
    
    @Impure
    public void setLessThanBigIntegerNumerical(@LessThan(2) BigIntegerNumerical<?> value) {}
    
    @Test
    public void testLessThan() {
        testNumerical(INSTANCE::setLessThanLong, INSTANCE::setLessThanInteger, INSTANCE::setLessThanBigInteger, INSTANCE::setLessThanLongNumerical, INSTANCE::setLessThanBigIntegerNumerical, 1, 2);
    }
    
    @Impure
    public void setLessThanOrEqualToLong(@LessThanOrEqualTo(2) long value) {}
    
    @Impure
    public void setLessThanOrEqualToInteger(@LessThanOrEqualTo(2) Integer value) {}
    
    @Impure
    public void setLessThanOrEqualToBigInteger(@LessThanOrEqualTo(2) BigInteger value) {}
    
    @Impure
    public void setLessThanOrEqualToLongNumerical(@LessThanOrEqualTo(2) LongNumerical<?> value) {}
    
    @Impure
    public void setLessThanOrEqualToBigIntegerNumerical(@LessThanOrEqualTo(2) BigIntegerNumerical<?> value) {}
    
    @Test
    public void testLessThanOrEqualTo() {
        testNumerical(INSTANCE::setLessThanOrEqualToLong, INSTANCE::setLessThanOrEqualToInteger, INSTANCE::setLessThanOrEqualToBigInteger, INSTANCE::setLessThanOrEqualToLongNumerical, INSTANCE::setLessThanOrEqualToBigIntegerNumerical, 2, 3);
    }
    
    /* -------------------------------------------------- Order -------------------------------------------------- */
    
    @Impure
    public void setAscendingIntArray(@Ascending int[] array) {}
    
    @Test
    public void testAscendingInts() {
        test(INSTANCE::setAscendingIntArray, new int[] {1, 1, 2, 3}, new int[] {1, 2, 3, 1});
    }
    
    @Impure
    public void setDescendingIntArray(@Descending int[] array) {}
    
    @Test
    public void testDescendingInts() {
        test(INSTANCE::setDescendingIntArray, new int[] {3, 2, 1, 1}, new int[] {1, 3, 2, 1});
    }
    
    @Impure
    public void setStrictlyAscendingIntArray(@StrictlyAscending int[] array) {}
    
    @Test
    public void testStrictlyAscendingInts() {
        test(INSTANCE::setStrictlyAscendingIntArray, new int[] {1, 2, 3}, new int[] {1, 1, 2});
    }
    
    @Impure
    public void setStrictlyDescendingIntArray(@StrictlyDescending int[] array) {}
    
    @Test
    public void testStrictlyDescendingInts() {
        test(INSTANCE::setStrictlyDescendingIntArray, new int[] {3, 2, 1}, new int[] {2, 1, 1});
    }
    
    @Impure
    public void setAscendingStringIterable(@Ascending Iterable<String> iterable) {}
    
    @Impure
    public void setAscendingStringArray(@Ascending String[] array) {}
    
    @Test
    public void testAscendingStrings() {
        testStringIterableAndArray(INSTANCE::setAscendingStringIterable, INSTANCE::setAscendingStringArray, new String[] {"hello", "hello", "world"}, new String[] {"world", "hello", "hello"});
    }
    
    @Impure
    public void setDescendingStringIterable(@Descending Iterable<String> iterable) {}
    
    @Test
    public void testDescendingStrings() {
        testStringIterableAndArray(INSTANCE::setDescendingStringIterable, INSTANCE::setDescendingStringArray, new String[] {"world", "hello", "hello"}, new String[] {"hello", "hello", "world"});
    }
    
    @Impure
    public void setDescendingStringArray(@Descending String[] array) {}
    
    @Impure
    public void setStrictlyAscendingStringIterable(@StrictlyAscending Iterable<String> iterable) {}
    
    @Impure
    public void setStrictlyAscendingStringArray(@StrictlyAscending String[] array) {}
    
    @Test
    public void testStrictlyAscendingStrings() {
        testStringIterableAndArray(INSTANCE::setStrictlyAscendingStringIterable, INSTANCE::setStrictlyAscendingStringArray, new String[] {"hello", "my", "world"}, new String[] {"hello", "hello", "world"});
    }
    
    @Impure
    public void setStrictlyDescendingStringIterable(@StrictlyDescending Iterable<String> iterable) {}
    
    @Impure
    public void setStrictlyDescendingStringArray(@StrictlyDescending String[] array) {}
    
    @Test
    public void testStrictlyDescendingStrings() {
        testStringIterableAndArray(INSTANCE::setStrictlyDescendingStringIterable, INSTANCE::setStrictlyDescendingStringArray, new String[] {"world", "my", "hello"}, new String[] {"world", "hello", "hello"});
    }
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    @Impure
    public void setEmptyString(@Empty String string) {}
    
    @Impure
    public void setEmptyCountable(@Empty Countable countable) {}
    
    @Impure
    public void setEmptyStringArray(@Empty String[] array) {}
    
    @Impure
    public void setEmptyIntArray(@Empty int[] array) {}
    
    @Test
    public void testEmpty() {
        testSize(INSTANCE::setEmptyString, INSTANCE::setEmptyCountable, INSTANCE::setEmptyStringArray, INSTANCE::setEmptyIntArray, 0, 1);
    }
    
    @Impure
    public void setEmptyOrSingleString(@EmptyOrSingle String string) {}
    
    @Impure
    public void setEmptyOrSingleCountable(@EmptyOrSingle Countable countable) {}
    
    @Impure
    public void setEmptyOrSingleStringArray(@EmptyOrSingle String[] array) {}
    
    @Impure
    public void setEmptyOrSingleIntArray(@EmptyOrSingle int[] array) {}
    
    @Test
    public void testEmptyOrSingle() {
        testSize(INSTANCE::setEmptyOrSingleString, INSTANCE::setEmptyOrSingleCountable, INSTANCE::setEmptyOrSingleStringArray, INSTANCE::setEmptyOrSingleIntArray, 0, 2);
        testSize(INSTANCE::setEmptyOrSingleString, INSTANCE::setEmptyOrSingleCountable, INSTANCE::setEmptyOrSingleStringArray, INSTANCE::setEmptyOrSingleIntArray, 1, 2);
    }
    
    @Impure
    public void setMaxSizeString(@MaxSize(3) String string) {}
    
    @Impure
    public void setMaxSizeCountable(@MaxSize(3) Countable countable) {}
    
    @Impure
    public void setMaxSizeStringArray(@MaxSize(3) String[] array) {}
    
    @Impure
    public void setMaxSizeIntArray(@MaxSize(3) int[] array) {}
    
    @Test
    public void testMaxSize() {
        testSize(INSTANCE::setMaxSizeString, INSTANCE::setMaxSizeCountable, INSTANCE::setMaxSizeStringArray, INSTANCE::setMaxSizeIntArray, 3, 4);
    }
    
    @Impure
    public void setMinSizeString(@MinSize(3) String string) {}
    
    @Impure
    public void setMinSizeCountable(@MinSize(3) Countable countable) {}
    
    @Impure
    public void setMinSizeStringArray(@MinSize(3) String[] array) {}
    
    @Impure
    public void setMinSizeIntArray(@MinSize(3) int[] array) {}
    
    @Test
    public void testMinSize() {
        testSize(INSTANCE::setMinSizeString, INSTANCE::setMinSizeCountable, INSTANCE::setMinSizeStringArray, INSTANCE::setMinSizeIntArray, 3, 2);
    }
    
    @Impure
    public void setNonEmptyString(@NonEmpty String string) {}
    
    @Impure
    public void setNonEmptyCountable(@NonEmpty Countable countable) {}
    
    @Impure
    public void setNonEmptyStringArray(@NonEmpty String[] array) {}
    
    @Impure
    public void setNonEmptyIntArray(@NonEmpty int[] array) {}
    
    @Test
    public void testNonEmpty() {
        testSize(INSTANCE::setNonEmptyString, INSTANCE::setNonEmptyCountable, INSTANCE::setNonEmptyStringArray, INSTANCE::setNonEmptyIntArray, 1, 0);
    }
    
    @Impure
    public void setNonEmptyOrSingleString(@NonEmptyOrSingle String string) {}
    
    @Impure
    public void setNonEmptyOrSingleCountable(@NonEmptyOrSingle Countable countable) {}
    
    @Impure
    public void setNonEmptyOrSingleStringArray(@NonEmptyOrSingle String[] array) {}
    
    @Impure
    public void setNonEmptyOrSingleIntArray(@NonEmptyOrSingle int[] array) {}
    
    @Test
    public void testNonEmptyOrSingle() {
        testSize(INSTANCE::setNonEmptyOrSingleString, INSTANCE::setNonEmptyOrSingleCountable, INSTANCE::setNonEmptyOrSingleStringArray, INSTANCE::setNonEmptyOrSingleIntArray, 2, 0);
        testSize(INSTANCE::setNonEmptyOrSingleString, INSTANCE::setNonEmptyOrSingleCountable, INSTANCE::setNonEmptyOrSingleStringArray, INSTANCE::setNonEmptyOrSingleIntArray, 2, 1);
    }
    
    @Impure
    public void setNonSingleString(@NonSingle String string) {}
    
    @Impure
    public void setNonSingleCountable(@NonSingle Countable countable) {}
    
    @Impure
    public void setNonSingleStringArray(@NonSingle String[] array) {}
    
    @Impure
    public void setNonSingleIntArray(@NonSingle int[] array) {}
    
    @Test
    public void testNonSingle() {
        testSize(INSTANCE::setNonSingleString, INSTANCE::setNonSingleCountable, INSTANCE::setNonSingleStringArray, INSTANCE::setNonSingleIntArray, 0, 1);
    }
    
    @Impure
    public void setSingleString(@Single String string) {}
    
    @Impure
    public void setSingleCountable(@Single Countable countable) {}
    
    @Impure
    public void setSingleStringArray(@Single String[] array) {}
    
    @Impure
    public void setSingleIntArray(@Single int[] array) {}
    
    @Test
    public void testSingle() {
        testSize(INSTANCE::setSingleString, INSTANCE::setSingleCountable, INSTANCE::setSingleStringArray, INSTANCE::setSingleIntArray, 1, 0);
    }
    
    @Impure
    public void setSizeString(@Size(3) String string) {}
    
    @Impure
    public void setSizeCountable(@Size(3) Countable countable) {}
    
    @Impure
    public void setSizeStringArray(@Size(3) String[] array) {}
    
    @Impure
    public void setSizeIntArray(@Size(3) int[] array) {}
    
    @Test
    public void testSize() {
        testSize(INSTANCE::setSizeString, INSTANCE::setSizeCountable, INSTANCE::setSizeStringArray, INSTANCE::setSizeIntArray, 3, 2);
    }
    
    @Impure
    @EmptyOrSingleRecipient
    public void setEmptyOrSingleRecipient() {}
    
    @Test
    public void testEmptyOrSingleRecipient() {
        try {
            INSTANCE.setEmptyOrSingleRecipient();
            fail("The recipient is not empty or single.");
        } catch (@Nonnull PreconditionException exception) {}
    }
    
    /* -------------------------------------------------- String -------------------------------------------------- */
    
    @Impure
    public void setCodeIdentifier(@CodeIdentifier String identifier) {}
    
    @Test
    public void testCodeIdentifier() {
        testPositives(INSTANCE::setCodeIdentifier, null, "_", "$", "hi", "hi5", "_h$i5");
        testNegatives(INSTANCE::setCodeIdentifier, "", "5", "5hi", "hi 5", "hi#5");
    }
    
    @Impure
    public void setDomainName(@DomainName String identifier) {}
    
    @Test
    public void testDomainName() {
        testPositives(INSTANCE::setDomainName, null, "a.ch", "test.com", "subdomain.domain.tld", "hello-world.org");
        testNegatives(INSTANCE::setDomainName, "", "a.c", "com", ".com", "test..com", "hello--world.org");
    }
    
    @Impure
    public void setJavaExpression(@JavaExpression String expression) {}
    
    @Test
    public void testJavaExpression() {
        testPositives(INSTANCE::setJavaExpression, null, "", "3 + 4", "2 * (3 + 4)", "new String(\":-)\")");
        testNegatives(INSTANCE::setJavaExpression, "return 0;", "2 * ((3 + 4)", "while (true) {}");
    }
    
    /* -------------------------------------------------- Substring -------------------------------------------------- */
    
    @Impure
    public void setInfix(@Infix("ab") String string) {}
    
    @Test
    public void testInfix() {
        testPositives(INSTANCE::setInfix, null, "aba", "bab");
        testNegatives(INSTANCE::setInfix, "", "a", "b", "ba");
    }
    
    @Impure
    public void setPrefix(@Prefix("ab") String string) {}
    
    @Test
    public void testPrefix() {
        testPositives(INSTANCE::setPrefix, null, "ab", "aba", "abab");
        testNegatives(INSTANCE::setPrefix, "", "a", "b", "ba", "bab");
    }
    
    @Impure
    public void setRegex(@Regex("a*bab*") String string) {}
    
    @Test
    public void testRegex() {
        testPositives(INSTANCE::setRegex, null, "ba", "aba", "bab", "abab", "aababb");
        testNegatives(INSTANCE::setRegex, "", "a", "b", "ab", "cba");
    }
    
    @Impure
    public void setSuffix(@Suffix("ab") String string) {}
    
    @Test
    public void testSuffix() {
        testPositives(INSTANCE::setSuffix, null, "ab", "bab", "abab");
        testNegatives(INSTANCE::setSuffix, "", "a", "b", "ba", "aba");
    }
    
    /* -------------------------------------------------- Type Kind -------------------------------------------------- */
    
    public static @interface NestedAnnotationType {}
    
    public static class NestedClassType {}
    
    public static enum NestedEnumType {}
    
    public static interface NestedInterfaceType {}
    
    @Impure
    public void setAnnotationType(@AnnotationType Class<?> type) {}
    
    @Test
    public void testAnnotationType() {
        testPositives(INSTANCE::setAnnotationType, null, NestedAnnotationType.class);
        testNegatives(INSTANCE::setAnnotationType, NestedClassType.class, NestedEnumType.class, NestedInterfaceType.class);
    }
    
    @Impure
    public void setClassType(@ClassType Class<?> type) {}
    
    @Test
    public void testClassType() {
        testPositives(INSTANCE::setClassType, null, NestedClassType.class);
        testNegatives(INSTANCE::setClassType, NestedAnnotationType.class, NestedEnumType.class, NestedInterfaceType.class);
    }
    
    @Impure
    public void setEnumType(@EnumType Class<?> type) {}
    
    @Test
    public void testEnumType() {
        testPositives(INSTANCE::setEnumType, null, NestedEnumType.class);
        testNegatives(INSTANCE::setEnumType, NestedAnnotationType.class, NestedClassType.class, NestedInterfaceType.class);
    }
    
    @Impure
    public void setInterfaceType(@InterfaceType Class<?> type) {}
    
    @Test
    public void testInterfaceType() {
        testPositives(INSTANCE::setInterfaceType, null, NestedInterfaceType.class);
        testNegatives(INSTANCE::setInterfaceType, NestedAnnotationType.class, NestedClassType.class, NestedEnumType.class);
    }
    
    @Impure
    public void setTypeOf(@TypeOf({ElementKind.CLASS, ElementKind.INTERFACE}) Class<?> type) {}
    
    @Test
    public void testTypeOf() {
        testPositives(INSTANCE::setTypeOf, null, NestedClassType.class, NestedInterfaceType.class);
        testNegatives(INSTANCE::setTypeOf, NestedAnnotationType.class, NestedEnumType.class);
    }
    
    /* -------------------------------------------------- Type Nesting -------------------------------------------------- */
    
    public static final @Nonnull Class<?> ANONYMOUS_TYPE = new Serializable(){}.getClass();
    
    public static final @Nonnull Class<?> LOCAL_TYPE;
    
    static {
        class LocalClass {}
        LOCAL_TYPE = LocalClass.class;
    }
    
    @Impure
    public void setAnonymousType(@AnonymousType Class<?> type) {}
    
    @Test
    public void testAnonymousType() {
        testPositives(INSTANCE::setAnonymousType, null, ANONYMOUS_TYPE);
        testNegatives(INSTANCE::setAnonymousType, LOCAL_TYPE, ContractGenerationTest.class, NestedAnnotationType.class, NestedClassType.class, NestedEnumType.class, NestedInterfaceType.class);
    }
    
    @Impure
    public void setLocalType(@LocalType Class<?> type) {}
    
    @Test
    public void testLocalType() {
        testPositives(INSTANCE::setLocalType, null, LOCAL_TYPE);
        testNegatives(INSTANCE::setLocalType, ANONYMOUS_TYPE, ContractGenerationTest.class, NestedAnnotationType.class, NestedClassType.class, NestedEnumType.class, NestedInterfaceType.class);
    }
    
    @Impure
    public void setMemberType(@MemberType Class<?> type) {}
    
    @Test
    public void testMemberType() {
        testPositives(INSTANCE::setMemberType, null, NestedAnnotationType.class, NestedClassType.class, NestedEnumType.class, NestedInterfaceType.class);
        testNegatives(INSTANCE::setMemberType, ANONYMOUS_TYPE, LOCAL_TYPE, ContractGenerationTest.class);
    }
    
    @Impure
    public void setNestingOf(@NestingOf({NestingKind.TOP_LEVEL, NestingKind.MEMBER}) Class<?> type) {}
    
    @Test
    public void testNestingOf() {
        testPositives(INSTANCE::setNestingOf, null, ContractGenerationTest.class, NestedAnnotationType.class, NestedClassType.class, NestedEnumType.class, NestedInterfaceType.class);
        testNegatives(INSTANCE::setNestingOf, ANONYMOUS_TYPE, LOCAL_TYPE);
    }
    
    @Impure
    public void setTopLevelType(@TopLevelType Class<?> type) {}
    
    @Test
    public void testTopLevelType() {
        testPositives(INSTANCE::setTopLevelType, null, ContractGenerationTest.class);
        testNegatives(INSTANCE::setTopLevelType, ANONYMOUS_TYPE, LOCAL_TYPE, NestedAnnotationType.class, NestedClassType.class, NestedEnumType.class, NestedInterfaceType.class);
    }
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    @Impure
    public void setInvariant(@Invariant(condition = "value % 3 == 0", message = "The value has to be a multiple of 3 but was $.") int value) {}
    
    @Test
    public void testInvariant() {
        test(INSTANCE::setInvariant, 3, 4);
    }
    
    @Impure
    public void setValidated(@Valid String string) {}
    
    @Test
    public void testValidated() {
        test(INSTANCE::setValidated, "12345", "123456");
    }
    
    @Pure
    public boolean isValidValue(int value) {
        return value < 3;
    }
    
    @Impure
    public void setValidatedNamed(@Valid("value") int value) {}
    
    @Test
    public void testValidatedNamed() {
        test(INSTANCE::setValidatedNamed, 2, 4);
    }
    
    /* -------------------------------------------------- Threading -------------------------------------------------- */
    
    @Impure
    @MainThread
    public void setMainThread() {}
    
    @Test
    public void testMainThread() {
        try {
            INSTANCE.setMainThread();
        } catch (@Nonnull PreconditionException exception) {
            fail("The test is running on the main thread.");
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    INSTANCE.setMainThread();
                    fail("This is not the main thread.");
                } catch (@Nonnull PreconditionException exception) {}
            }
        }.start();
    }
    
    /* -------------------------------------------------- Freezable -------------------------------------------------- */
    
    @ReadOnly(FreezableObject.class)
    public static interface ReadOnlyObject extends ReadOnlyInterface {
        
        @Pure
        @Override
        public @Capturable @Nonnull @NonFrozen FreezableObject clone();
        
    }
    
    @Freezable(ReadOnlyObject.class)
    public static class FreezableObject implements ReadOnlyObject, FreezableInterface {
        
        private boolean frozen = false;
        
        @Pure
        @Override
        public boolean isFrozen() {
            return frozen;
        }
        
        @Impure
        @Override
        @NonFrozenRecipient
        public @Chainable @Nonnull @Frozen ReadOnlyObject freeze() {
            this.frozen = true;
            return this;
        }
        
        @Pure
        @Override
        public @Capturable @Nonnull @NonFrozen FreezableObject clone() {
            return new FreezableObject();
        }
        
    }
    
    public static final @Nonnull ReadOnlyObject FROZEN_OBJECT = new FreezableObject().freeze();
    
    public static final @Nonnull FreezableObject NON_FROZEN_OBJECT = new FreezableObject();
    
    @Impure
    public void setFrozen(@Frozen ReadOnlyInterface freezable) {}
    
    @Test
    public void testFrozen() {
        test(INSTANCE::setFrozen, FROZEN_OBJECT, NON_FROZEN_OBJECT);
    }
    
    @Impure
    public void setNonFrozen(@NonFrozen ReadOnlyInterface freezable) {}
    
    @Test
    public void testNonFrozen() {
        test(INSTANCE::setNonFrozen, NON_FROZEN_OBJECT, FROZEN_OBJECT);
    }
    
    @Impure
    @NonFrozenRecipient
    public void setNonFrozenRecipient() {}
    
    @Test
    public void testNonFrozenRecipient() {
        try {
            INSTANCE.setNonFrozenRecipient();
            fail("The recipient is frozen.");
        } catch (@Nonnull PreconditionException exception) {}
    }
    
}
