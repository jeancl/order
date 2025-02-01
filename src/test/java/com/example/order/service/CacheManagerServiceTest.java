package com.example.order.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;

@ExtendWith(MockitoExtension.class)
class CacheManagerServiceTest {

	private SimpleCacheManager cacheManager = new SimpleCacheManager();
	private CacheManagerService cacheManagerService = new CacheManagerService(cacheManager);
	
	@Test
	void evictCachesTest() {
		Cache productInfo = Mockito.spy(new ConcurrentMapCache("ProductInfo"));
		
		productInfo.put("test", "test");

		cacheManager.setCaches(Lists.newArrayList(productInfo,productInfo));
		cacheManager.initializeCaches();
		
		cacheManagerService.evictCaches();
		
		verify(productInfo, times(1)).clear();
	}
}
