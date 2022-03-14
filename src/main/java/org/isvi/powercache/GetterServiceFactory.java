package org.isvi.powercache;

import org.isvi.powercache.exceptions.MethodInvocationException;
import org.isvi.powercache.exceptions.NoGetMethodFoundException;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

@Component
public class GetterServiceFactory {
    GetterService of(final Object bean) {

        final Method method = getGetMethod(bean);

        return (proceedingJoin) -> {
            try {
                return method.invoke(bean, proceedingJoin.getArgs());
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new MethodInvocationException(e.getMessage(), e);
            }
        };
    }

    private Method getGetMethod(final Object bean) {
       return Arrays.stream(bean.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(CacheGet.class))
               .findFirst()
               .orElseThrow(()-> new NoGetMethodFoundException("The class "
                       + bean.getClass().getName()
                       + " does not have a get method"
                       + " please annotate a method with @CacheGet"));
    }

}
