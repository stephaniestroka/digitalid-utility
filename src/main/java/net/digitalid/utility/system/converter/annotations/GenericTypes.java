package net.digitalid.utility.system.converter.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.annotation.Nonnull;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenericTypes {
    
    @Nonnull Class<?>[] types();
    
}
