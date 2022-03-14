package org.isvi.powercache;

import org.aspectj.lang.ProceedingJoinPoint;

public interface GetterService {
    Object get(final ProceedingJoinPoint proceedingJoinPoint);
}
