package net.digitalid.utility.testing;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.contracts.exceptions.PreconditionViolationException;
import net.digitalid.utility.fixes.Quotes;
import net.digitalid.utility.functional.failable.FailableConsumer;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * This class makes it easier to test (generated) contracts.
 */
@Stateless
public abstract class ContractTest extends CustomTest {
    
    @Pure
    protected static <T, X extends Exception> void test(@NonCaptured @Modified @Nonnull FailableConsumer<? super T, ? extends X> consumer, T positive, T negative) throws X {
        try {
            consumer.consume(positive);
        } catch (@Nonnull PreconditionViolationException exception) {
            fail("The positive sample " + Quotes.inSingle(positive) + " should not fail.");
        }
        try {
            consumer.consume(negative); 
            fail("The negative sample " + Quotes.inSingle(negative) + " should fail.");
        } catch (@Nonnull PreconditionViolationException exception) {}
    }
    
    @Pure
    @SafeVarargs
    protected static <T, X extends Exception> void testPositives(@NonCaptured @Modified @Nonnull FailableConsumer<? super T, ? extends X> consumer, @Nonnull T... positives) throws X {
        for (T positive : positives) {
            try {
                consumer.consume(positive);
            } catch (@Nonnull PreconditionViolationException exception) {
                fail("The positive sample " + Quotes.inSingle(positive) + " should not fail.");
            }
        }
    }
    
    @Pure
    @SafeVarargs
    protected static <T, X extends Exception> void testNegatives(@NonCaptured @Modified @Nonnull FailableConsumer<? super T, ? extends X> consumer, @Nonnull T... negatives) throws X {
        for (T negative : negatives) {
            try {
                consumer.consume(negative);
                fail("The negative sample " + Quotes.inSingle(negative) + " should fail.");
            } catch (@Nonnull PreconditionViolationException exception) {}
        }
    }
    
}
