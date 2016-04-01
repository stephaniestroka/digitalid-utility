package net.digitalid.utility.testcases;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.processing.Processor;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.rootclass.RootClass;

/**
 * Description.
 */
public abstract class SimpleGenericClass<P extends Processor> extends RootClass {
    
    @Pure
    public abstract @Nonnull P getProcessor();
    
    public SimpleGenericClass() {
        
    }
    
    public void addTo(List<? super Processor> list) {
        list.add(getProcessor());
    }
    
}
