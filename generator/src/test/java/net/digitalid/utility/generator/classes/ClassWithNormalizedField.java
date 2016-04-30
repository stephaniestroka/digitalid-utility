package net.digitalid.utility.generator.classes;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.rootclass.RootInterface;
import net.digitalid.utility.validation.annotations.getter.Normalize;

@GenerateBuilder
@GenerateSubclass
public abstract class ClassWithNormalizedField implements RootInterface {
    
    @Pure
    @Normalize("value.substring(0, 20)")
    public abstract @Nonnull String getValue();
    
    public abstract void setValue(@Nonnull String value);
    
}
