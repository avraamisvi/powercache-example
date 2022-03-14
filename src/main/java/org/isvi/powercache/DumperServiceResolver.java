package org.isvi.powercache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class DumperServiceResolver {

    private final ApplicationContext context;
    private final DumperServiceFactory dumperServiceFactory;

    public DumperServiceResolver(final ApplicationContext context,
                                 final DumperServiceFactory dumperServiceFactory) {
        this.context = context;
        this.dumperServiceFactory = dumperServiceFactory;
    }

    DumperService getDumperService(PowerEvict cacheable) {
        return dumperServiceFactory.of(context.getBean(getEvictName(cacheable)));
    }

    PowerEvict getEvictAnnotation(ProceedingJoinPoint proceedingJoinPoint) {
        final Method method = getMethod(proceedingJoinPoint);
        return method.getDeclaredAnnotation(PowerEvict.class);
    }

    private String getEvictName(final PowerEvict powerEvict) {
        return powerEvict.value().isEmpty() ? powerEvict.dumper() : powerEvict.value();
    }

    private Method getMethod(final ProceedingJoinPoint proceedingJoinPoint) {
        final MethodSignature method = (MethodSignature) proceedingJoinPoint.getSignature();
        return method.getMethod();
    }
}
