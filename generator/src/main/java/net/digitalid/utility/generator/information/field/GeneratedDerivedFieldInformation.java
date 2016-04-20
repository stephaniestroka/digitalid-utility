package net.digitalid.utility.generator.information.field;

import javax.annotation.Nonnull;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.generator.information.method.MethodInformation;

/**
 *
 */
public class GeneratedDerivedFieldInformation extends GeneratedFieldInformation {
    
    private GeneratedDerivedFieldInformation(@Nonnull DeclaredType containingType, @Nonnull MethodInformation getter) {
        super(containingType, getter, null);
    }
    
    public static @Nonnull GeneratedDerivedFieldInformation of(@Nonnull DeclaredType containingType, @Nonnull MethodInformation getter) {
        return new GeneratedDerivedFieldInformation(containingType, getter);
    }
    
}
