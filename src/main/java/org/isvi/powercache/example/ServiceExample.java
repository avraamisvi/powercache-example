package org.isvi.powercache.example;

import org.isvi.powercache.PowerCacheable;
import org.isvi.powercache.PowerEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ServiceExample {

    @PowerEvict("myCacheLogic")
    public void saveWithEvict(String item) {
        //DO NOTHING
    }

    @PowerCacheable("myCacheLogic")
    public String getWithCache(String byItem) {
        return UUID.randomUUID().toString();
    }
}
