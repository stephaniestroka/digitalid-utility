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
package net.digitalid.utility.testing;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.contracts.exceptions.PreconditionException;
import net.digitalid.utility.functional.failable.FailableConsumer;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * This class makes it easier to test (generated) contracts.
 */
@Stateless
public abstract class ContractTest extends UtilityTest {
    
    @Pure
    protected static <@Specifiable TYPE, @Unspecifiable EXCEPTION extends Exception> void test(@NonCaptured @Modified @Nonnull FailableConsumer<? super TYPE, ? extends EXCEPTION> consumer, TYPE positive, TYPE negative) throws EXCEPTION {
        try {
            consumer.consume(positive);
        } catch (@Nonnull PreconditionException exception) {
            fail("The positive sample '%s' should not fail.", positive);
        }
        try {
            consumer.consume(negative); 
            fail("The negative sample '%s' should fail.", negative);
        } catch (@Nonnull PreconditionException exception) {}
    }
    
    @Pure
    @SafeVarargs
    protected static <@Specifiable TYPE, @Unspecifiable EXCEPTION extends Exception> void testPositives(@NonCaptured @Modified @Nonnull FailableConsumer<? super TYPE, ? extends EXCEPTION> consumer, @Nonnull TYPE... positives) throws EXCEPTION {
        for (TYPE positive : positives) {
            try {
                consumer.consume(positive);
            } catch (@Nonnull PreconditionException exception) {
                fail("The positive sample '%s' should not fail.", positive);
            }
        }
    }
    
    @Pure
    @SafeVarargs
    protected static <@Specifiable TYPE, @Unspecifiable EXCEPTION extends Exception> void testNegatives(@NonCaptured @Modified @Nonnull FailableConsumer<? super TYPE, ? extends EXCEPTION> consumer, @Nonnull TYPE... negatives) throws EXCEPTION {
        for (TYPE negative : negatives) {
            try {
                consumer.consume(negative);
                fail("The negative sample '%s' should fail.", negative);
            } catch (@Nonnull PreconditionException exception) {}
        }
    }
    
}
