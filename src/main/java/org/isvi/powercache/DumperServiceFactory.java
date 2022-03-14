package org.isvi.powercache;

import org.isvi.powercache.exceptions.MethodInvocationException;
import org.isvi.powercache.exceptions.NoEvictMethodFoundException;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

@Component
public class DumperServiceFactory {
    DumperService of(final Object bean) {

        final Method evictMethod = getEvictMethod(bean);

        return (proceedingJoin) -> {
            try {
                evictMethod.invoke(bean, proceedingJoin.getArgs());
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new MethodInvocationException(e.getMessage(), e);
            }
        };
    }

    private Method getEvictMethod(final Object bean) {
       return Arrays.stream(bean.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(CacheEvict.class))
               .findFirst()
               .orElseThrow(()-> new NoEvictMethodFoundException("The class "
                       + bean.getClass().getName()
                       + " does not have an evict method"
                       + " please annotate a method with @CacheEvict"));
    }

}
