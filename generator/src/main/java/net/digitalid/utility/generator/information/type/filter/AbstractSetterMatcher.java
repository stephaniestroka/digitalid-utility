package net.digitalid.utility.generator.information.type.filter;

import javax.annotation.Nonnull;

import net.digitalid.utility.generator.information.filter.FilterCondition;
import net.digitalid.utility.generator.information.method.MethodInformation;

/**
 * A filter condition that filters all method information objects that contain methods that
 * start with set and are abstract.
 */
public class AbstractSetterMatcher implements FilterCondition<MethodInformation> {
    
    private AbstractSetterMatcher() {
    }
    
    public static @Nonnull AbstractSetterMatcher get() {
        return new AbstractSetterMatcher();
    }
    
    @Override
    public boolean filter(@Nonnull MethodInformation methodInformation) {
        return methodInformation.isSetter() && !methodInformation.isAbstract();
    }
    
}
