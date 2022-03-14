package org.isvi.powercache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.isvi.powercache.exceptions.MethodInvocationException;
import org.isvi.powercache.exceptions.NoPutMethodFoundException;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

@Component
public class CatcherServiceFactory {
    CatcherService of(final Object bean) {

        final Method method = getPutMethod(bean);

        return (proceedingJoin, freshValue) -> {
            try {
                Object[] arguments = createArgumentsWithFreshValue(proceedingJoin, freshValue);
                method.invoke(bean, arguments);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new MethodInvocationException(e.getMessage(), e);
            }
        };
    }

    private Object[] createArgumentsWithFreshValue(ProceedingJoinPoint proceedingJoin, Object freshValue) {
        final Object[] arguments = Arrays.copyOf(proceedingJoin.getArgs(), getNewLength(proceedingJoin));
        arguments[getLastIndex(proceedingJoin)] = freshValue;
        return arguments;
    }

    private int getLastIndex(ProceedingJoinPoint proceedingJoin) {
        return proceedingJoin.getArgs().length;
    }

    private int getNewLength(ProceedingJoinPoint proceedingJoin) {
        return proceedingJoin.getArgs().length + 1;
    }

    private Method getPutMethod(final Object bean) {
       return Arrays.stream(bean.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(CachePut.class))
               .findFirst()
               .orElseThrow(()-> new NoPutMethodFoundException("The class "
                       + bean.getClass().getName()
                       + " does not have a put method"
                       + " please annotate a method with @CachePut"));
    }

}
