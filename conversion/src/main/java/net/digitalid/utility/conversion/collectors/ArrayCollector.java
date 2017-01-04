package net.digitalid.utility.conversion.collectors;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.conversion.exceptions.RecoveryException;
import net.digitalid.utility.conversion.exceptions.RecoveryExceptionBuilder;
import net.digitalid.utility.functional.failable.FailableCollector;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.type.Mutable;

@Mutable
public class ArrayCollector<@Specifiable TYPE> implements FailableCollector<TYPE, TYPE[], RecoveryException, RecoveryException> {
    
    /* -------------------------------------------------- Array -------------------------------------------------- */
    
    private final @Nonnull TYPE[] array;
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    @SuppressWarnings("unchecked")
    protected ArrayCollector(@NonNegative int size) {
        this.array = (TYPE[]) new Object[size];
    }
    
    @Pure
    public static <@Specifiable TYPE> @Nonnull ArrayCollector<TYPE> with(@NonNegative int size) {
        return new ArrayCollector<>(size);
    }
    
    /* -------------------------------------------------- Collector -------------------------------------------------- */
    
    private int nextIndex = 0;
    
    @Impure
    @Override
    public void consume(@Captured TYPE element) throws RecoveryException {
        if (nextIndex < array.length) {
            array[nextIndex] = element;
            nextIndex += 1;
        } else {
            throw RecoveryExceptionBuilder.withMessage("The array can take at most " + Strings.getCardinal(array.length) + " elements.").build();
        }
    }
    
    @Pure
    @Override
    @SuppressWarnings("ReturnOfCollectionOrArrayField")
    public @Capturable TYPE[] getResult() {
        return array;
    }
    
}
