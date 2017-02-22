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
