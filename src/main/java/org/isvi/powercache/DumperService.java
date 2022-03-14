package org.isvi.powercache;

import org.aspectj.lang.ProceedingJoinPoint;

public interface DumperService {
    void evict(final ProceedingJoinPoint proceedingJoinPoint);
}
