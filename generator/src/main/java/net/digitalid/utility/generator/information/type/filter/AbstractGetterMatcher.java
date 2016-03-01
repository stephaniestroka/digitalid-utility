package net.digitalid.utility.generator.information.type.filter;

import javax.annotation.Nonnull;

import net.digitalid.utility.generator.information.method.MethodInformation;

/**
 * A filter condition that filters all method information objects that contain methods that
 * start with get, has or is and are abstract.
 */
public class AbstractGetterMatcher extends GetterMatcher {
    
    private AbstractGetterMatcher() {
    }
    
    public static @Nonnull AbstractGetterMatcher get() {
        return new AbstractGetterMatcher();
    }
    
    @Override
    public boolean filter(@Nonnull MethodInformation methodInformation) {
        return super.filter(methodInformation) && methodInformation.isAbstract();
    }
    
}
