package com.cc.word.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

public class CacheUtils {
    private Cache<String,Object> tenMinuteCache = CacheBuilder.newBuilder()
            //设置缓存初始⼤⼩，应该合理设置，后续会扩容
            .initialCapacity(10)
            //最⼤值
            .maximumSize(100)
            //并发数设置
            .concurrencyLevel(5)
            //缓存过期时间，写⼊后10分钟过期
            .expireAfterWrite(600, TimeUnit.SECONDS)
            //统计缓存命中率
            .recordStats()
            .build();

    public Cache<String, Object> getTenMinuteCache() {
        return tenMinuteCache;
    }
    public void setTenMinuteCache(Cache<String, Object> tenMinuteCache) {
        this.tenMinuteCache = tenMinuteCache;
    }
}
