package net.digitalid.utility.generator.information.type.filter;

import javax.annotation.Nonnull;

import net.digitalid.utility.generator.information.method.MethodInformation;

/**
 *
 */
public class ImplementedGetterMatcher extends GetterMatcher {
    
    private ImplementedGetterMatcher() {}
    
    public static @Nonnull ImplementedGetterMatcher get() {
        return new ImplementedGetterMatcher();
    }
    
    @Override
    public boolean filter(@Nonnull MethodInformation methodInformation) {
        return super.filter(methodInformation) && !methodInformation.isAbstract();
    }
    
}
