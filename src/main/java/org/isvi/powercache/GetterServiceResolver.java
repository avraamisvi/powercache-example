package org.isvi.powercache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class GetterServiceResolver {

    private final ApplicationContext context;
    private final GetterServiceFactory getterServiceFactory;
    private final CacheableExtractor cacheableExtractor;

    public GetterServiceResolver(final ApplicationContext context,
                                 final GetterServiceFactory getterServiceFactory,
                                 final CacheableExtractor cacheableExtractor) {
        this.context = context;
        this.getterServiceFactory = getterServiceFactory;
        this.cacheableExtractor = cacheableExtractor;
    }

    GetterService getGetterService(final ProceedingJoinPoint proceedingJoinPoint) {
        return getCatcherService(cacheableExtractor.getCacheableAnnotation(proceedingJoinPoint));
    }

    private GetterService getCatcherService(PowerCacheable powerCacheable) {
        return getterServiceFactory.of(context.getBean(getCacheableValue(powerCacheable)));
    }

    private String getCacheableValue(final PowerCacheable powerCacheable) {
        return powerCacheable.value().isEmpty() ? powerCacheable.catcher() : powerCacheable.value();
    }

}
