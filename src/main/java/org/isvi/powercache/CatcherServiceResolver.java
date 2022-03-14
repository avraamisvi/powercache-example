package org.isvi.powercache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CatcherServiceResolver {

    private final ApplicationContext context;
    private final CatcherServiceFactory catcherServiceFactory;
    private final CacheableExtractor cacheableExtractor;

    public CatcherServiceResolver(final ApplicationContext context,
                                  final CatcherServiceFactory catcherServiceFactory,
                                  final CacheableExtractor cacheableExtractor) {
        this.context = context;
        this.catcherServiceFactory = catcherServiceFactory;
        this.cacheableExtractor = cacheableExtractor;
    }

    CatcherService getCatcherService(final ProceedingJoinPoint proceedingJoinPoint) {

        return getCatcherService(cacheableExtractor.getCacheableAnnotation(proceedingJoinPoint));
    }

    private CatcherService getCatcherService(PowerCacheable powerCacheable) {
        return catcherServiceFactory.of(context
                .getBean(getCatcherName(powerCacheable)));
    }

    private String getCatcherName(PowerCacheable powerCacheable) {
        return powerCacheable.value().isEmpty() ? powerCacheable.catcher() : powerCacheable.value();
    }

}
