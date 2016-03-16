package net.digitalid.utility.generator.interceptor;

import javax.annotation.Nonnull;

public interface MethodInterceptor {
    
    public abstract Object invoke(@Nonnull MethodInvocation methodInvocation) throws Throwable;
    
}
