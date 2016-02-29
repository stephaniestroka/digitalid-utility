package net.digitalid.utility.generator.information.type.filter;

import javax.annotation.Nonnull;

import net.digitalid.utility.generator.information.filter.FilterCondition;
import net.digitalid.utility.generator.information.method.MethodInformation;

/**
 *
 */
public class SetterMatcher implements FilterCondition<MethodInformation> {
    
    protected SetterMatcher() {
    }
    
    public static @Nonnull SetterMatcher get() {
        return new SetterMatcher();
    }
    
    @Override 
    public boolean filter(@Nonnull MethodInformation methodInformation) {
        return methodInformation.isSetter();
    }
    
}
