package net.digitalid.utility.generator.information.type.filter;

import javax.annotation.Nonnull;

import net.digitalid.utility.generator.annotations.Recover;
import net.digitalid.utility.generator.information.filter.FilterCondition;
import net.digitalid.utility.generator.information.method.MethodInformation;

/**
 * Filters methods annotated with {@link Recover @Recover}.
 */
public class RecoverMethodMatcher implements FilterCondition<MethodInformation> {
    
    @Override 
    public boolean filter(@Nonnull MethodInformation elementInformation) {
        return elementInformation.hasAnnotation(Recover.class);
    }
    
    public static @Nonnull RecoverMethodMatcher get() {
        return new RecoverMethodMatcher();
    }
    
}
