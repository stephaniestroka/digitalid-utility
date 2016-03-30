package net.digitalid.utility.testcases;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.processing.Processor;

import net.digitalid.utility.tuples.annotations.Pure;

/**
 * Description.
 */
public abstract class SimpleGenericClass<P extends Processor> {
    
    @Pure
    public abstract @Nonnull P getProcessor();
    
    public SimpleGenericClass() {
        
    }
    
    public void addTo(List<? super Processor> list) {
        list.add(getProcessor());
    }
    
}
