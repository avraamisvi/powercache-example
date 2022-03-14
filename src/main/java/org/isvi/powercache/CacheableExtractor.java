package org.isvi.powercache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class CacheableExtractor {

    PowerCacheable getCacheableAnnotation(ProceedingJoinPoint proceedingJoinPoint) {
        final Method method = getMethod(proceedingJoinPoint);
        return method.getDeclaredAnnotation(PowerCacheable.class);
    }

    private Method getMethod(final ProceedingJoinPoint proceedingJoinPoint) {
        final MethodSignature method = (MethodSignature) proceedingJoinPoint.getSignature();
        return method.getMethod();
    }
}
