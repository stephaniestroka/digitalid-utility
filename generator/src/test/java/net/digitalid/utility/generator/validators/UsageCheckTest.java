/*
 * Copyright (C) 2017 Synacts GmbH, Switzerland (info@synacts.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.digitalid.utility.generator.validators;

import java.util.List;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.freezable.FreezableInterface;
import net.digitalid.utility.freezable.annotations.Freezable;
import net.digitalid.utility.freezable.annotations.NonFrozenRecipient;
import net.digitalid.utility.generator.annotations.generators.GenerateAnnotationValidator;
import net.digitalid.utility.rootclass.RootClass;
import net.digitalid.utility.validation.annotations.generation.Recover;
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
import net.digitalid.utility.validation.annotations.value.Valid;
import net.digitalid.utility.validation.validators.GenerationValidator;

/* -------------------------------------------------- Functional -------------------------------------------------- */

@IncorrectUsage(Functional.Validator.class)
abstract class IncorrectFunctionalUsageClass {}

@IncorrectUsage(Functional.Validator.class)
interface IncorrectFunctionalUsageInterfaceWithNoAbstractMethod {
    
    @Pure
    public default void method() {}
    
}

@IncorrectUsage(Functional.Validator.class)
interface IncorrectFunctionalUsageInterfaceWithSeveralAbstractMethods {
    
    @Pure
    public void method1();
    
    @Pure
    public void method2();
    
}

/* -------------------------------------------------- Immutable -------------------------------------------------- */

@IncorrectUsage(Immutable.Validator.class)
abstract class IncorrectImmutableUsageWithImpureMethod extends RootClass {
    
    @Impure
    public void method() {}
    
}

@IncorrectUsage(Immutable.Validator.class)
abstract class IncorrectImmutableUsageWithNonFinalField extends RootClass {
    
    private int field;
    
}

/* -------------------------------------------------- ReadOnly -------------------------------------------------- */

@IncorrectUsage(ReadOnly.Validator.class)
abstract class IncorrectReadOnlyUsageWithImpureMethod extends RootClass {
    
    @Impure
    public void method() {}
    
}

@IncorrectUsage(ReadOnly.Validator.class)
abstract class IncorrectReadOnlyUsageWithNonFinalField extends RootClass {
    
    private int field;
    
}

/* -------------------------------------------------- Stateless -------------------------------------------------- */

@IncorrectUsage(Stateless.Validator.class)
abstract class IncorrectStatelessUsage extends RootClass {
    
    private int value;
    
}

/* -------------------------------------------------- Utility -------------------------------------------------- */

@IncorrectUsage(Utility.Validator.class)
class IncorrectUtilityUsageNonAbstractClass {}

@IncorrectUsage(Utility.Validator.class)
abstract class IncorrectUtilityUsageNonStaticField extends RootClass {
    
    private int value;
    
}

@IncorrectUsage(Utility.Validator.class)
abstract class IncorrectUtilityUsageNonStaticMethod extends RootClass {
    
    @Pure
    public void method() {}
    
}

/* -------------------------------------------------- Target Type -------------------------------------------------- */

abstract class IncorrectTargetTypes extends RootClass {
    
    @Pure
    public @IncorrectUsage(Nonnull.Validator.class) int method(@IncorrectUsage(Ascending.Validator.class) Comparable<String> comparable) {
        return 0;
    }
    
}

/* -------------------------------------------------- Receiver Type -------------------------------------------------- */

abstract class IncorrectReceiverTypes extends RootClass {
    
    @Pure
    @IncorrectUsage(NonFrozenRecipient.Validator.class)
    public void method1() {}
    
    @Pure
    @IncorrectUsage(EmptyOrSingleRecipient.Validator.class)
    public void method2() {}
    
}

/* -------------------------------------------------- Generation -------------------------------------------------- */

class IncorrectGenerationUsage {
    
    IncorrectGenerationUsage(@IncorrectUsage(GenerationValidator.class) int value) {}
    
    IncorrectGenerationUsage() {}
    
    @Pure
    public void method(@IncorrectUsage(GenerationValidator.class) int value) {}
    
    @Pure
    @IncorrectUsage(GenerationValidator.class)
    public void method() {}
    
    @Pure
    @IncorrectUsage(GenerationValidator.class)
    public int getValue() {
        return 0;
    }
    
}

class IncorrectRecoverUsageWithNonStaticMethod {
    
    @Pure
    @IncorrectUsage(Recover.Validator.class)
    public IncorrectRecoverUsageWithNonStaticMethod create() {
        return null;
    }
    
}

class IncorrectRecoverUsageWithWrongReturnType {
    
    @Pure
    @IncorrectUsage(Recover.Validator.class)
    public static Object create() {
        return null;
    }
    
}

class IncorrectRecoverUsageWithSingleConstructor {
    
    @IncorrectUsage(Recover.Validator.class)
    protected IncorrectRecoverUsageWithSingleConstructor() {}
    
}

class IncorrectRecoverUsageWithOtherRecover {
    
    @Pure
    @IncorrectUsage(Recover.Validator.class)
    public static IncorrectRecoverUsageWithOtherRecover create() {
        return null;
    }
    
    @Pure
    @Recover
    public static IncorrectRecoverUsageWithOtherRecover other() {
        return null;
    }
    
}

/* -------------------------------------------------- Index -------------------------------------------------- */

abstract class IncorrectIndexUsage extends RootClass {
    
    @Pure
    private @NonNegative int size() {
        return 0;
    }
    
    @Pure
    public void method(@IncorrectUsage(Index.Validator.class) int index) {}
    
}

/* -------------------------------------------------- Ordering -------------------------------------------------- */

abstract class IncorrectOrderingUsage extends RootClass {
    
    @Pure
    public void method(@IncorrectUsage(Ascending.Validator.class) List<Object> argument) {}
    
}

/* -------------------------------------------------- Validated -------------------------------------------------- */

abstract class IncorrectValidatedUsage extends RootClass {
    
    @Pure
    public void method(@IncorrectUsage(Valid.Validator.class) String string) {}
    
}

/* -------------------------------------------------- Freezable -------------------------------------------------- */

@IncorrectUsage(Freezable.Validator.class)
abstract class IncorrectFreezableUsageNotImplementingFreezableInterface extends RootClass {}

@IncorrectUsage(Freezable.Validator.class)
interface IncorrectFreezableUsageImpureMethodWithoutNonFrozenRecipient extends FreezableInterface {
    
    @Impure
    public void method();
    
}

/* -------------------------------------------------- Generate Annotations -------------------------------------------------- */

abstract class IncorrectGenerateUsage extends RootClass {
    
    @IncorrectUsage(GenerateAnnotationValidator.class)
    static class NestedClass {}
    
}
