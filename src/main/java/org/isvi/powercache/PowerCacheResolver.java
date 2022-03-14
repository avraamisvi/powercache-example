package org.isvi.powercache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PowerCacheResolver {

    private final CatcherServiceResolver catcherServiceResolver;
    private final GetterServiceResolver getterServiceResolver;
    private final DumperServiceResolver dumperServiceResolver;

    public PowerCacheResolver(final CatcherServiceResolver catcherServiceResolver,
                              final DumperServiceResolver dumperServiceResolver,
                              final GetterServiceResolver getterServiceResolver) {
        this.catcherServiceResolver = catcherServiceResolver;
        this.dumperServiceResolver = dumperServiceResolver;
        this.getterServiceResolver = getterServiceResolver;
    }

    @Around("@annotation(PowerCacheable)")
    public Object whenCaching(final ProceedingJoinPoint proceedingJoinPoint) {
        final CatcherService catcherService = catcherServiceResolver.getCatcherService(proceedingJoinPoint);
        final GetterService getterService = getterServiceResolver.getGetterService(proceedingJoinPoint);

        final Object result = getterService.get(proceedingJoinPoint);
        if (result == null) {
            final Object freshResult = proceed(proceedingJoinPoint);
            catcherService.put(proceedingJoinPoint, freshResult);

            return freshResult;
        }

        return result;
    }

    @Around("@annotation(PowerEvict)")
    public void whenUpdating(final ProceedingJoinPoint proceedingJoinPoint) {
        final PowerEvict powerEvict = dumperServiceResolver.getEvictAnnotation(proceedingJoinPoint);
        final DumperService dumperService = dumperServiceResolver.getDumperService(powerEvict);

        if(powerEvict.beforeExecution()) {
            dumperService.evict(proceedingJoinPoint);
        }

        proceed(proceedingJoinPoint);

        if(!powerEvict.beforeExecution()) {
            dumperService.evict(proceedingJoinPoint);
        }
    }

    private Object proceed(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
