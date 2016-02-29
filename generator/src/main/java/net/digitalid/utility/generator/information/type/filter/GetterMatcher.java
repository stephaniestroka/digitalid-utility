package net.digitalid.utility.generator.information.type.filter;

import javax.annotation.Nonnull;

import net.digitalid.utility.generator.information.filter.FilterCondition;
import net.digitalid.utility.generator.information.method.MethodInformation;

/**
 *
 */
public class GetterMatcher implements FilterCondition<MethodInformation> {
    
    protected GetterMatcher() {
    }
    
    public static @Nonnull GetterMatcher get() {
        return new GetterMatcher();
    }
    
    @Override 
    public boolean filter(@Nonnull MethodInformation methodInformation) {
        return methodInformation.isGetter();
    }
    
}
