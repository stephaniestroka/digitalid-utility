package net.digitalid.utility.processing.processor;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.processing.utility.StaticProcessingEnvironment;

/**
 *
 */
public class TestProcessor extends AbstractProcessor {
    
    @Impure
    @Override
    public void init(@Nonnull ProcessingEnvironment processingEnvironment) throws IllegalStateException {
        StaticProcessingEnvironment.environment.set(processingEnvironment);
    }
    
    @Pure
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        return true;
    }
    
}
