package net.digitalid.utility.generator;

import java.util.List;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.freezable.FreezableInterface;
import net.digitalid.utility.freezable.annotations.Freezable;
import net.digitalid.utility.freezable.annotations.NonFrozenRecipient;
import net.digitalid.utility.rootclass.RootClass;
import net.digitalid.utility.validation.annotations.index.Index;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.order.Ascending;
import net.digitalid.utility.validation.annotations.size.EmptyOrSingleRecipient;
import net.digitalid.utility.validation.annotations.testing.IncorrectUsage;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.ReadOnly;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.annotations.type.Utility;
import net.digitalid.utility.validation.annotations.value.Validated;

/* -------------------------------------------------- Functional -------------------------------------------------- */

@IncorrectUsage(Functional.Validator.class)
abstract class IncorrectFunctionalUsageClass {}

@IncorrectUsage(Functional.Validator.class)
@SuppressWarnings("MultipleTopLevelClassesInFile")
interface IncorrectFunctionalUsageInterfaceWithNoAbstractMethod {
    
    @Pure
    public default void method() {}
    
}

@IncorrectUsage(Functional.Validator.class)
@SuppressWarnings("MultipleTopLevelClassesInFile")
interface IncorrectFunctionalUsageInterfaceWithSeveralAbstractMethods {
    
    @Pure
    public void method1();
    
    @Pure
    public void method2();
    
}

/* -------------------------------------------------- Immutable -------------------------------------------------- */

@IncorrectUsage(Immutable.Validator.class)
@SuppressWarnings("MultipleTopLevelClassesInFile")
abstract class IncorrectImmutableUsage extends RootClass {
    
    @Impure
    public void method() {}
    
}

/* -------------------------------------------------- ReadOnly -------------------------------------------------- */

@IncorrectUsage(ReadOnly.Validator.class)
@SuppressWarnings("MultipleTopLevelClassesInFile")
abstract class IncorrectReadOnlyUsage extends RootClass {
    
    @Impure
    public void method() {}
    
}

/* -------------------------------------------------- Stateless -------------------------------------------------- */

@IncorrectUsage(Stateless.Validator.class)
@SuppressWarnings("MultipleTopLevelClassesInFile")
abstract class IncorrectStatelessUsage extends RootClass {
    
    private int value;
    
}

/* -------------------------------------------------- Utility -------------------------------------------------- */

@IncorrectUsage(Utility.Validator.class)
@SuppressWarnings("MultipleTopLevelClassesInFile")
class IncorrectUtilityUsageNonAbstractClass {}

@IncorrectUsage(Utility.Validator.class)
@SuppressWarnings("MultipleTopLevelClassesInFile")
abstract class IncorrectUtilityUsageNonStaticField extends RootClass {
    
    private int value;
    
}

@IncorrectUsage(Utility.Validator.class)
@SuppressWarnings("MultipleTopLevelClassesInFile")
abstract class IncorrectUtilityUsageNonStaticMethod extends RootClass {
    
    @Pure
    public void method() {}
    
}

/* -------------------------------------------------- Target Type -------------------------------------------------- */

@SuppressWarnings("MultipleTopLevelClassesInFile")
abstract class IncorrectTargetTypes extends RootClass {
    
    @Pure
    public @IncorrectUsage(Nonnull.Validator.class) int method(@IncorrectUsage(Ascending.Validator.class) Comparable<String> comparable) {
        return 0;
    }
    
}

/* -------------------------------------------------- Receiver Type -------------------------------------------------- */

@SuppressWarnings("MultipleTopLevelClassesInFile")
abstract class IncorrectReceiverTypes extends RootClass {
    
    @Pure
    @IncorrectUsage(NonFrozenRecipient.Validator.class)
    public void method1() {}
    
    @Pure
    @IncorrectUsage(EmptyOrSingleRecipient.Validator.class)
    public void method2() {}
    
}

/* -------------------------------------------------- Index -------------------------------------------------- */

@SuppressWarnings("MultipleTopLevelClassesInFile")
abstract class IncorrectIndexUsage extends RootClass {
    
    @Pure
    private @NonNegative int size() {
        return 0;
    }
    
    @Pure
    public void method(@IncorrectUsage(Index.Validator.class) int index) {}
    
}

/* -------------------------------------------------- Ordering -------------------------------------------------- */

@SuppressWarnings("MultipleTopLevelClassesInFile")
abstract class IncorrectOrderingUsage extends RootClass {
    
    @Pure
    public void method(@IncorrectUsage(Ascending.Validator.class) List<Object> argument) {}
    
}

/* -------------------------------------------------- Validated -------------------------------------------------- */

@SuppressWarnings("MultipleTopLevelClassesInFile")
abstract class IncorrectValidatedUsage extends RootClass {
    
    @Pure
    public void method(@IncorrectUsage(Validated.Validator.class) String string) {}
    
}

/* -------------------------------------------------- Freezable -------------------------------------------------- */

@IncorrectUsage(Freezable.Validator.class)
@SuppressWarnings("MultipleTopLevelClassesInFile")
abstract class IncorrectFreezableUsageNotImplementingFreezableInterface extends RootClass {}

@IncorrectUsage(Freezable.Validator.class)
@SuppressWarnings("MultipleTopLevelClassesInFile")
interface IncorrectFreezableUsageImpureMethodWithoutNonFrozenRecipient extends FreezableInterface {
    
    @Impure
    public void method();
    
}
