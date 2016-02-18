package net.digitalid.utility.generator.interceptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class MethodInvocation {
    
    public abstract @Nonnull Method getMethod();
    
    public abstract @Nonnull Object getRecipient();
    
    public abstract @Nonnull Object[] getArguments();
    
    public @Nullable Object proceed() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return getMethod().invoke(getRecipient(), getArguments());
    }
    
}
