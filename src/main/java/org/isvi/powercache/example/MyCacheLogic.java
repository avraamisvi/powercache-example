package org.isvi.powercache.example;

import org.isvi.powercache.CacheEvict;
import org.isvi.powercache.CacheGet;
import org.isvi.powercache.CachePut;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is just an example, it does not intend to be used as the best practice
 */
@Component
public class MyCacheLogic {

    public static final String MY_CACHE = "myCache";
    private final CacheManager cacheManager;

    public MyCacheLogic(final CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @CacheEvict
    public void evict(final String argument) {
        getChildren(argument).forEach( value -> {
            String key = generateKey(value);
            Cache cache = cacheManager.getCache(MY_CACHE);
            if(cache != null) {
                cache.evictIfPresent(key);
            }
        });
    }

    private List<String> getChildren(final String argument) {
        return Arrays.asList("melao", "abacate", "arroz");
    }

    private String generateKey(final String argument) {
        return argument+"SOME_COMPLEX_PROG_GENERATED_KEY";
    }

    @CacheGet
    public String get(final String argument) {
        String key = generateKey(argument);
        Cache cache = cacheManager.getCache(MY_CACHE);
        if(cache != null) {
            return cache.get(key, String.class);
        } else {
            return null;
        }
    }

    @CachePut
    public void put(final String argument, final String freshResult) {
        String key = generateKey(argument);
        Cache cache = cacheManager.getCache(MY_CACHE);
        if(cache != null) {
            cache.put(key, freshResult);
        }
    }
}
