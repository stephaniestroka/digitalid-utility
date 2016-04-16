package net.digitalid.utility.validation.validators;

import java.math.BigInteger;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.immutable.ImmutableSet;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.interfaces.BigIntegerNumerical;
import net.digitalid.utility.validation.interfaces.LongNumerical;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 * This class declares the target types for modulo annotations.
 * 
 * @see net.digitalid.utility.validation.annotations.math.modulo
 */
@Stateless
public abstract class ModuloValidator extends ValueAnnotationValidator {
    
    /* -------------------------------------------------- Target Types -------------------------------------------------- */
    
    private static final @Nonnull ImmutableSet<@Nonnull Class<?>> targetTypes = ImmutableSet.with(byte.class, short.class, int.class, long.class, BigInteger.class, LongNumerical.class, BigIntegerNumerical.class);
    
    @Pure
    @Override
    public @Nonnull ImmutableSet<@Nonnull Class<?>> getTargetTypes() {
        return targetTypes;
    }
    
}
