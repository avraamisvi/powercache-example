package org.isvi.powercache;

import org.aspectj.lang.ProceedingJoinPoint;

public interface CatcherService {
    void put(final ProceedingJoinPoint proceedingJoinPoint, final Object freshResult);
}
